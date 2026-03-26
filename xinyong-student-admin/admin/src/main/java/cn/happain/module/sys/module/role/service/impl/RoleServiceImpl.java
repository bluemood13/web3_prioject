package cn.happain.module.sys.module.role.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.happain.common.consts.GlobalConst;
import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.common.utils.HappainGlobalUtil;
import cn.happain.module.other.roleMenu.domain.model.RoleMenuModel;
import cn.happain.module.other.roleMenu.service.impl.RoleMenuServiceImpl;
import cn.happain.module.other.userRole.domain.model.UserRoleModel;
import cn.happain.module.other.userRole.service.impl.UserRoleServiceImpl;
import cn.happain.module.sys.module.role.domain.model.RoleModel;
import cn.happain.module.sys.module.role.domain.model.dto.RoleDto;
import cn.happain.module.sys.module.role.domain.model.vo.RoleVo;
import cn.happain.module.sys.module.role.mapper.RoleMapper;
import cn.happain.module.sys.module.role.service.IRoleService;
import cn.happain.module.sys.module.user.domain.UserModel;
import cn.happain.module.sys.module.user.domain.vo.UserVo;
import cn.happain.module.sys.module.user.service.impl.UserServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author happain
 * @since 2023-02-15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleModel> implements IRoleService {


    @Resource
    RoleMenuServiceImpl roleMenuService;
    @Resource
    UserRoleServiceImpl userRoleService;
    @Resource
    UserServiceImpl userService;

    /**
     * 分页查询
     *
     * @param roleDto dto
     * @return {@link Response}<{@link PageVo}<{@link RoleVo}>>
     */
    public Response<PageVo<RoleVo>> listPage(RoleDto roleDto) {
        LambdaQueryWrapper<RoleModel> search = search(roleDto);
        Page<RoleModel> roleModelPage = roleDto.buildPageVo(RoleModel.class);
         Page<RoleModel> page = page(roleModelPage, search);
         PageVo<RoleVo> roleVoPageVo = PageVo.PageModelToVo(page, RoleVo.class);
         return Response.successDataDefault(roleVoPageVo);
     }

    public LambdaQueryWrapper<RoleModel> search(RoleDto roleDto) {
        LambdaQueryWrapper<RoleModel> roleModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(roleDto.getRoleName())) {
            roleModelLambdaQueryWrapper.like(RoleModel::getRoleName, roleDto.getRoleName());
        }
        if (StrUtil.isNotEmpty(roleDto.getRoleValue())) {
            roleModelLambdaQueryWrapper.like(RoleModel::getRoleValue, roleDto.getRoleValue());
        }
        return roleModelLambdaQueryWrapper;

    }

    /**
     * 查询所有
     *
     * @param roleDto dto
     * @return {@link Response}<{@link List}<{@link RoleVo}>>
     */
    public Response<List<RoleVo>> listAll(RoleDto roleDto) {
        LambdaQueryWrapper<RoleModel> lambda = new QueryWrapper<RoleModel>().lambda();
        List<RoleModel> list = list(lambda);
          List<RoleVo> roleVos = BeanUtil.copyToList(list, RoleVo.class);
          return Response.successDataDefault(roleVos);
      }


     /**
      * 添加
      *
      * @param roleDto dto
      * @return {@link Response}
      */
     public Response add(RoleDto roleDto) {
         if (hasData(roleDto)) {
             return Response.failExitData();
         }
         RoleModel roleModel = BeanUtil.copyProperties(roleDto, RoleModel.class);
         save(roleModel);
         // TODO: 2023/6/14 新增角色关联菜单
         if (CollUtil.isNotEmpty(roleDto.getMenuIds())) {
             ArrayList<RoleMenuModel> roleMenuModels = new ArrayList<>();
             for (Integer menuId : roleDto.getMenuIds()) {
                 RoleMenuModel roleMenuModel = new RoleMenuModel().setRoleId(roleModel.getId()).setMenuId(menuId);
                 roleMenuModels.add(roleMenuModel);
             }
             roleMenuService.saveBatch(roleMenuModels);
         }
         return Response.successDefault();
     }

     /**
      * 修改
      *
      * @param roleDto dto
      * @return {@link Response}
      */
     public Response upd(RoleDto roleDto) {
         if (hasData(roleDto)) {
             return Response.failExitData();
         }
         RoleModel roleModel = BeanUtil.copyProperties(roleDto, RoleModel.class);
         updateById(roleModel);
         // TODO: 2023/6/14 修改角色关联的菜单信息
         LambdaQueryWrapper<RoleMenuModel> roleMenuModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
         roleMenuModelLambdaQueryWrapper.eq(RoleMenuModel::getRoleId, roleModel.getId());
         List<RoleMenuModel> list = roleMenuService.list(roleMenuModelLambdaQueryWrapper);
         List<Integer> menuIds = list.stream().map(RoleMenuModel::getMenuId).collect(Collectors.toList());
         //如果传入的值不一样，说明要更新
         if (!CollUtil.isEqualList(menuIds, roleDto.getMenuIds())) {
             //处理删除的
             List<Integer> delMenuIds = CollUtil.subtractToList(menuIds, roleDto.getMenuIds());
             if (CollUtil.isNotEmpty(delMenuIds)) {
                 LambdaQueryWrapper<RoleMenuModel> roleMenuModelLambdaQueryWrapper1 = new LambdaQueryWrapper<>();
                 roleMenuModelLambdaQueryWrapper1.eq(RoleMenuModel::getRoleId, roleModel.getId());
                 roleMenuModelLambdaQueryWrapper1.in(RoleMenuModel::getMenuId, delMenuIds);
                 roleMenuService.remove(roleMenuModelLambdaQueryWrapper1);
             }
             //处理新增的
             List<Integer> addIds = CollUtil.subtractToList(roleDto.getMenuIds(), menuIds);
             ArrayList<RoleMenuModel> roleMenuModels = new ArrayList<>();
             for (Integer menuId : addIds) {
                 RoleMenuModel roleMenuModel = new RoleMenuModel().setRoleId(roleModel.getId()).setMenuId(menuId);
                 roleMenuModels.add(roleMenuModel);
             }
             roleMenuService.saveBatch(roleMenuModels);
         }
         return Response.successDefault();
     }


    /**
      * 删除
      *
      * @param roleDto dto
      * @return {@link Response}
      */
     public Response del(RoleDto roleDto) {
         // TODO: 2023/8/1 查询用户角色关联数据
         LambdaQueryWrapper<UserRoleModel> userRoleModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
         userRoleModelLambdaQueryWrapper.eq(UserRoleModel::getRoleId, roleDto.getId());
         List<UserRoleModel> roleModels = userRoleService.list(userRoleModelLambdaQueryWrapper);
         if (CollUtil.isNotEmpty(roleModels)) {
             return Response.failExitAssoData();
         }
         removeById(roleDto.getId());
         return Response.successDefault();
     }


    /**
     * 查询详情
     *
     * @param roleDto dto
     * @return {@link Response}
     */
    public Response<RoleVo> detail(RoleDto roleDto) {
        RoleModel model = getById(roleDto.getId());
        if (model == null) {
            return Response.failNoData();
        }
        RoleVo roleVo = BeanUtil.copyProperties(model, RoleVo.class);
        return Response.successDataDefault(roleVo);
    }

     /**
      * 有数据
      *
      * @param roleDto dto
      * @return boolean
      */
     public boolean hasData(RoleDto roleDto) {
         LambdaQueryWrapper<RoleModel> lambda = new QueryWrapper<RoleModel>().lambda();
         lambda.eq(RoleModel::getRoleName, roleDto.getRoleName());
         lambda.eq(RoleModel::getRoleValue, roleDto.getRoleValue());
         if (roleDto.getId() != null) {
             lambda.ne(RoleModel::getId, roleDto.getId());
         }
         return getOne(lambda) != null;
     }

    public Response delForce(RoleDto roleDto) {
        RoleModel roleModel = getById(roleDto.getId());
        if(GlobalConst.ROLE_WHITE.contains(roleDto.getRoleValue())) {
           return Response.failDefault("系统内置角色，不允许删除");
        }
        removeById(roleDto.getId());
        return Response.successDefault();
    }

    public Response<RoleVo> roleMember(RoleDto roleDto) {
        LambdaQueryWrapper<UserRoleModel> userRoleModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleModelLambdaQueryWrapper.eq(UserRoleModel::getRoleId, roleDto.getId());
        List<UserRoleModel> roleModels = userRoleService.list(userRoleModelLambdaQueryWrapper);
        List<Integer> userId = roleModels.stream().map(UserRoleModel::getUserId).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(userId)) {
            LambdaQueryWrapper<UserModel> userModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userModelLambdaQueryWrapper.in(UserModel::getId, userId);
            List<UserModel> userModels = userService.list(userModelLambdaQueryWrapper);
            List<UserVo> userVos = BeanUtil.copyToList(userModels, UserVo.class);
            RoleVo roleVo = new RoleVo();
            roleVo.setUserVos(userVos);
            return Response.successDataDefault(roleVo);
        }
        return Response.successDefault();

    }

    public Response<List<String>> getRoleListByUserId(RoleDto roleDto) {
        List<String> roleList = StpUtil.getRoleList(HappainGlobalUtil.getUserId());
        return Response.successDataDefault(roleList);
    }
}
