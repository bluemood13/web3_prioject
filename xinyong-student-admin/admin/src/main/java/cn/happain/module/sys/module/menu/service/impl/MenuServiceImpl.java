package cn.happain.module.sys.module.menu.service.impl;

import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.common.utils.HappainGlobalUtil;
import cn.happain.common.utils.HappainTreeUtil;
import cn.happain.module.other.roleMenu.domain.model.RoleMenuModel;
import cn.happain.module.other.roleMenu.mapper.RoleMenuMapper;
import cn.happain.module.other.roleMenu.service.impl.RoleMenuServiceImpl;
import cn.happain.module.other.userRole.domain.model.UserRoleModel;
import cn.happain.module.other.userRole.mapper.UserRoleMapper;
import cn.happain.module.sys.module.menu.domain.MenuModel;
import cn.happain.module.sys.module.menu.domain.dto.MenuDto;
import cn.happain.module.sys.module.menu.domain.vo.MenuVo;
import cn.happain.module.sys.module.menu.mapper.MenuMapper;
import cn.happain.module.sys.module.menu.service.IMenuService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author happain
 * @since 2023-02-15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuModel> implements IMenuService {


    @Resource
    RoleMenuMapper roleMenuMapper;
    @Resource
    RoleMenuServiceImpl roleMenuService;
    @Resource
    UserRoleMapper userRoleMapper;
    @Resource
    MenuMapper menuMapper;
    public List<MenuVo> fillVo(List<MenuVo> menuVo) {
        for (MenuVo vo : menuVo) {
            fillVoOne(vo);
        }
        return menuVo;
    }

    public MenuVo fillVoOne(MenuVo vo)  {
        LambdaQueryWrapper<RoleMenuModel> roleMenuModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuModelLambdaQueryWrapper.eq(RoleMenuModel::getMenuId,vo.getId());
        List<RoleMenuModel> list = roleMenuService.list(roleMenuModelLambdaQueryWrapper);
        if(CollUtil.isNotEmpty(list)) {
            List<Integer> list1 = list.stream().map(RoleMenuModel::getRoleId).toList();
            vo.setRoleIds(list1);
        }
        return vo;
    }
    /**
     * 搜索条件
     *
     * @param menuDto dto
     * @return {@link LambdaQueryWrapper}<{@link MenuModel}>
     */
    public LambdaQueryWrapper<MenuModel> search(MenuDto menuDto) {
        LambdaQueryWrapper<MenuModel> lambda = new LambdaQueryWrapper<MenuModel>();
        return lambda;
    }

    /**
     * 细节
     *
     * @param menuDto 菜单dto
     * @return {@link Response}<{@link MenuVo}>
     */
    public Response<MenuVo> detail(MenuDto menuDto) {
        MenuModel model = getById(menuDto.getId());
        if (model == null) {
            return Response.failNoData();
        }
        MenuVo menuVo = BeanUtil.copyProperties(model, MenuVo.class);
        return Response.successDataDefault(menuVo);
    }


    private void addRole(Integer id, Integer parentId, Integer roleId) {
        // TODO: 2023/6/15 查看是否已经存在该角色记录,没有就添加
        LambdaQueryWrapper<RoleMenuModel> roleMenuModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuModelLambdaQueryWrapper.eq(RoleMenuModel::getRoleId, roleId);
        roleMenuModelLambdaQueryWrapper.eq(RoleMenuModel::getMenuId, id);
        RoleMenuModel one = roleMenuService.getOne(roleMenuModelLambdaQueryWrapper);
        if (one == null) {
            RoleMenuModel roleMenuModel = new RoleMenuModel().setMenuId(id).setRoleId(roleId);
            roleMenuService.save(roleMenuModel);
        }
        // TODO: 2023/6/15 不是顶级节点的话往上查找
        if (parentId != 0) {
            MenuModel model = getById(parentId);
            addRole(model.getId(), model.getParentId(), roleId);
        }
    }

    /**
     * 菜单添加
     *
     * @param menuDto 菜单dto
     * @return {@link Response}<{@link Void}>
     */
    public Response add(MenuDto menuDto) {
        if (hasData(menuDto)) {
            return Response.failExitData();
        }else {
            MenuModel menuModel = BeanUtil.copyProperties(menuDto, MenuModel.class);
            save(menuModel);
            // TODO: 2023/6/15 添加角色信息
            if (CollUtil.isNotEmpty(menuDto.getRoleIds())) {
                for (Integer roleId : menuDto.getRoleIds()) {
                    addRole(menuModel.getId(), menuModel.getParentId(), roleId);
                }
            }
            return Response.successDefault();
        }
    }


    /**
     * 乌利希期刊指南
     *
     * @param menuDto 菜单dto
     * @return {@link Response}
     */
    public Response upd(MenuDto menuDto) {
        if(hasData(menuDto)){
            return Response.failExitData();
        }else {
            MenuModel menuModel = BeanUtil.copyProperties(menuDto, MenuModel.class);
            updateById(menuModel);
            // TODO: 2023/6/15 更新角色信息
            LambdaQueryWrapper<RoleMenuModel> roleMenuModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roleMenuModelLambdaQueryWrapper.eq(RoleMenuModel::getMenuId, menuDto.getId());
            List<RoleMenuModel> list = roleMenuService.list(roleMenuModelLambdaQueryWrapper);
            List<Integer> roleIds = list.stream().map(RoleMenuModel::getRoleId).collect(Collectors.toList());
            if (!CollUtil.isEqualList(roleIds, menuDto.getRoleIds())) {
                // TODO: 2023/6/15 删除去除掉的角色
                List<Integer> delIds = CollUtil.subtractToList(roleIds, menuDto.getRoleIds());
                if (CollUtil.isNotEmpty(delIds)) {
                    LambdaQueryWrapper<RoleMenuModel> roleMenuModelLambdaQueryWrapper1 = new LambdaQueryWrapper<>();
                    roleMenuModelLambdaQueryWrapper1.eq(RoleMenuModel::getMenuId, menuDto.getId());
                    roleMenuModelLambdaQueryWrapper1.in(RoleMenuModel::getRoleId, delIds);
                    roleMenuService.remove(roleMenuModelLambdaQueryWrapper1);
                }
                // TODO: 2023/6/15 添加新增的角色
                List<Integer> addIds = CollUtil.subtractToList(menuDto.getRoleIds(), roleIds);
                if (CollUtil.isNotEmpty(addIds)) {
                    for (Integer roleId : addIds) {
                        addRole(menuModel.getId(), menuModel.getParentId(), roleId);
                    }
                }
            }

            return Response.successDefault();
        }
    }

    /**
     * 删除菜单
     *
     * @param menuDto 菜单dto
     * @return {@link Response}<{@link Void}>
     */
    public Response del(MenuDto menuDto) {
        // TODO: 2023/6/15 查询是否有子菜单
        LambdaQueryWrapper<MenuModel> menuModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        menuModelLambdaQueryWrapper.eq(MenuModel::getParentId, menuDto.getId());
        List<MenuModel> menuModels = menuMapper.selectList(menuModelLambdaQueryWrapper);
        if (CollUtil.isNotEmpty(menuModels)) {
            return Response.failExitAssoData();
        }
        // TODO: 2023/6/15 查询是否有关联的角色
        LambdaQueryWrapper<RoleMenuModel> roleMenuModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuModelLambdaQueryWrapper.eq(RoleMenuModel::getMenuId, menuDto.getId());
        List<RoleMenuModel> list = roleMenuService.list(roleMenuModelLambdaQueryWrapper);
        if (CollUtil.isNotEmpty(list)) {
            return Response.failExitAssoData();
        }
        removeById(menuDto.getId());
        return Response.successDefault();
    }
    public Response<PageVo<MenuVo>> listPage(MenuDto menuDto) {
        LambdaQueryWrapper<MenuModel> lambda = search(menuDto);
        Page<MenuModel> menuModelPage =  menuDto.buildPageVo(MenuModel.class);
        Page<MenuModel> page = page(menuModelPage, lambda);
        PageVo<MenuVo> menuVoPageVo = PageVo.PageModelToVo(page,MenuVo.class);
        menuVoPageVo.setList(fillVo(menuVoPageVo.getList()));
        return Response.successDataDefault(menuVoPageVo);
    }
    /**
     * 查询所有
     *
     * @param menuDto dto
     * @return {@link Response}<{@link List}<{@link MenuVo}>>
     */
    public Response<List<MenuVo>> listAll(MenuDto menuDto) {
        LambdaQueryWrapper<MenuModel> lambda = search(menuDto);
        List<MenuModel> list = list(lambda);
        List<MenuVo> menuVos = BeanUtil.copyToList(list, MenuVo.class);
        return Response.successDataDefault(fillVo(menuVos));
    }


    /**
     * 列表树
     *
     * @param menuDto 菜单dto
     * @return {@link Response}<{@link List}<{@link MenuVo}>>
     */
    public Response<List<MenuVo>> listTree(MenuDto menuDto) {
        LambdaQueryWrapper<MenuModel> lambda = new QueryWrapper<MenuModel>().lambda();
        List<MenuModel> list = list(lambda);
        List<MenuVo> menuVos1 = fillVo(BeanUtil.copyToList(list, MenuVo.class));
        List<MenuVo> menuVos = HappainTreeUtil.listToTree(menuVos1, 0, MenuVo::getId, MenuVo::getRanks, MenuVo::getParentId, MenuVo::setChildren);
        return Response.successDataDefault(menuVos);
    }


    /**
     * 是否又数据
     *
     * @param menuDto 菜单dto
     * @return boolean
     */
    public boolean hasData(MenuDto menuDto) {
        LambdaQueryWrapper<MenuModel> lambda = new QueryWrapper<MenuModel>().lambda();
        lambda.eq(MenuModel::getName, menuDto.getName());
        lambda.eq(MenuModel::getPath, menuDto.getPath());
        lambda.eq(MenuModel::getTitle, menuDto.getTitle());
        lambda.eq(MenuModel::getParentId, menuDto.getParentId());
        if (menuDto.getId() != null) {
            lambda.ne(MenuModel::getId, menuDto.getId());
        }
        return getOne(lambda) != null;
    }

    public Response<List<MenuVo>> getUserMenu() {
        // TODO: 2023/6/8 获取用户的角色
        Integer userId = HappainGlobalUtil.getUserId();
        LambdaQueryWrapper<UserRoleModel> userRoleModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleModelLambdaQueryWrapper.eq(UserRoleModel::getUserId, userId);
        List<UserRoleModel> userRoleModels = userRoleMapper.selectList(userRoleModelLambdaQueryWrapper);
        List<Integer> roleIds = userRoleModels.stream().map(UserRoleModel::getRoleId).collect(Collectors.toList());
        // TODO: 2023/6/8 查询菜单列表
        if (CollUtil.isNotEmpty(roleIds)) {
            LambdaQueryWrapper<RoleMenuModel> roleMenuModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roleMenuModelLambdaQueryWrapper.in(RoleMenuModel::getRoleId, roleIds);
            List<RoleMenuModel> roleMenuModels = roleMenuMapper.selectList(roleMenuModelLambdaQueryWrapper);
            List<Integer> menuIds = roleMenuModels.stream().map(RoleMenuModel::getMenuId).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(menuIds)) {
                List<MenuModel> menuModels = menuMapper.selectByIds(menuIds);
                List<MenuVo> menuVos1 = BeanUtil.copyToList(menuModels, MenuVo.class);
                List<MenuVo> menuVos = HappainTreeUtil.listToTree(menuVos1, 0, MenuVo::getId, MenuVo::getRanks, MenuVo::getParentId, MenuVo::setChildren);
                return Response.successDataDefault(menuVos);
            }
        }
        return Response.successDefault();
    }

    public Response<List<Integer>> getRoleMenu(MenuDto menuDto) {
        LambdaQueryWrapper<RoleMenuModel> roleMenuModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuModelLambdaQueryWrapper.eq(RoleMenuModel::getRoleId, menuDto.getRoleId());
        List<RoleMenuModel> roleMenuModels = roleMenuMapper.selectList(roleMenuModelLambdaQueryWrapper);
        List<Integer> menuIds = roleMenuModels.stream().map(RoleMenuModel::getMenuId).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(menuIds)) {
            List<MenuModel> menuModels = listByIds(menuIds);
            List<Integer> collect = menuModels.stream().map(MenuModel::getId).collect(Collectors.toList());
            return Response.successDataDefault(collect);
        }
        return Response.successDefault();
    }

    public Response<List<Integer>> getMenuRole(MenuDto menuDto) {
        LambdaQueryWrapper<RoleMenuModel> roleMenuModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuModelLambdaQueryWrapper.eq(RoleMenuModel::getMenuId, menuDto.getId());
        List<RoleMenuModel> list = roleMenuService.list(roleMenuModelLambdaQueryWrapper);
        return Response.successDataDefault(list.stream().map(RoleMenuModel::getRoleId).collect(Collectors.toList()));
    }
}
