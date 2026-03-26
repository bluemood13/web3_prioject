package cn.happain.module.sys.module.user.service.impl;

import cn.happain.common.consts.GlobalConst;
import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.common.utils.HappainGlobalUtil;
import cn.happain.module.other.userRole.domain.model.UserRoleModel;
import cn.happain.module.other.userRole.mapper.UserRoleMapper;
import cn.happain.module.other.userRole.service.impl.UserRoleServiceImpl;
import cn.happain.module.sys.module.menu.mapper.MenuMapper;
import cn.happain.module.sys.module.role.domain.model.RoleModel;
import cn.happain.module.sys.module.role.domain.model.vo.RoleVo;
import cn.happain.module.sys.module.role.mapper.RoleMapper;
import cn.happain.module.sys.module.user.domain.UserModel;
import cn.happain.module.sys.module.user.domain.dto.UserDto;
import cn.happain.module.sys.module.user.domain.vo.UserVo;
import cn.happain.module.sys.module.user.mapper.UserMapper;
import cn.happain.module.sys.module.user.service.IUserService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, UserModel> implements IUserService {


    @Resource
    MenuMapper menuMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    RoleMapper roleMapper;
    @Resource
    UserRoleMapper userRoleMapper;
    @Resource
    UserRoleServiceImpl userRoleService;



    /**
     * 额外补充数据
     *
     * @return {@link List}<{@link UserVo}>
     */
    private List<UserVo> fillVo(List<UserVo> other) {
        if(CollUtil.isNotEmpty(other)) {
            for (UserVo userVo : other) {
                fillVoOne(userVo);
            }
        }
        return other;
    }

    public UserVo fillVoOne(UserVo userVo)  {
        LambdaQueryWrapper<UserRoleModel> userRoleModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleModelLambdaQueryWrapper.eq(UserRoleModel::getUserId, userVo.getId());
        List<UserRoleModel> userRoleModels = userRoleMapper.selectList(userRoleModelLambdaQueryWrapper);
        List<Integer> roleIds = userRoleModels.stream().map(UserRoleModel::getRoleId).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(roleIds)) {
            List<RoleModel> roleModels = roleMapper.selectByIds(roleIds);
            userVo.setRoles(BeanUtil.copyToList(roleModels, RoleVo.class));
            userVo.setRoleIds(roleModels.stream().map(RoleModel::getId).toList());
        }
        return userVo;
    }


    public Response<List<UserVo>> listAll(UserDto userDto) {
        LambdaQueryWrapper<UserModel> search = search(userDto);
        List<UserModel> list = list(search);
        List<UserVo> userVos = BeanUtil.copyToList(list, UserVo.class);
        return Response.successDataDefault(fillVo(userVos));
    }
    /**
     * 搜索
     * @param userDto
     * @return {@link LambdaQueryWrapper}<{@link UserModel}>
     */
    private LambdaQueryWrapper<UserModel> search(UserDto userDto) {
        LambdaQueryWrapper<UserModel> userModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotEmpty(userDto.getUsername())) {
            userModelLambdaQueryWrapper.like(UserModel::getUsername, userDto.getUsername());
        }
        return userModelLambdaQueryWrapper;
    }
    /**
     * 页面列表
     *
     * @param userDto 用户dto
     * @return {@link Response}<{@link PageVo}<{@link UserVo}>>
     */
    public Response<PageVo<UserVo>> listPage(UserDto userDto) {
        LambdaQueryWrapper<UserModel> search = search(userDto);
        Page<UserModel> page = page(userDto.buildPageVo(UserModel.class), search);
        PageVo<UserVo> pageVo = PageVo.PageModelToVo(page, UserVo.class);
        pageVo.setList(fillVo(pageVo.getList()));
        return Response.successDataDefault(pageVo);
    }


    /**
     * 添加用户
     *
     * @param userDto 用户dto
     * @return {@link Response}<{@link UserVo}>
     */
    public Response<UserVo> addUser(UserDto userDto) {
        LambdaQueryWrapper<UserModel> userModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userModelLambdaQueryWrapper.eq(UserModel::getUsername,userDto.getUsername());
        Long aLong = userMapper.selectCount(userModelLambdaQueryWrapper);
        if(aLong>0) {
            return Response.failDefault("账号已存在");
        }else {
            UserModel userModel = BeanUtil.copyProperties(userDto, UserModel.class);
            userMapper.insert(userModel);
            List<Integer> roleIds = userDto.getRoleIds();
            if(CollUtil.isEmpty(roleIds)) {
                roleIds = ListUtil.of(0);
            }
            for (Integer roleId : roleIds) {
                // TODO: 2023/12/5 存入默认角色
                UserRoleModel userRoleModel = new UserRoleModel();
                userRoleModel.setRoleId(roleId);
                userRoleModel.setUserId(userModel.getId());
                userRoleService.save(userRoleModel);
            }
            return Response.successDefault();
        }
    }


    /**
     * 查询用户详情
     *
     * @param userDto 用户dto
     * @return {@link Response}<{@link UserVo}>
     */
    public Response<UserVo> detail(UserDto userDto) {
        UserModel model = getById(userDto.getId());
        if(model == null) {
            return Response.failNoData();
        }
        UserVo userVo = BeanUtil.copyProperties(model, UserVo.class);
        // TODO: 2023/10/6 查询角色列表
        List<RoleVo> roleVos = roleMapper.selectRoleListByUserId(userDto.getId());
        userVo.setRoles(roleVos);
        return Response.successDataDefault(userVo);
    }


    public Response upd(UserDto userDto) {
        UserModel userModel = BeanUtil.copyProperties(userDto, UserModel.class);
        updateById(userModel);
        // TODO: 2023/12/5 修改角色
        LambdaQueryWrapper<UserRoleModel> userRoleModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleModelLambdaQueryWrapper.eq(UserRoleModel::getUserId,userModel.getId());
        userRoleMapper.delete(userRoleModelLambdaQueryWrapper);
        List<Integer> roleIds = userDto.getRoleIds();
        for (Integer roleId : roleIds) {
            UserRoleModel userRoleModel = new UserRoleModel().setRoleId(roleId).setUserId(userModel.getId());
            userRoleMapper.insert(userRoleModel);
        }
        return Response.successDefault();
    }

    public Response<UserVo> getUserInfo(UserDto userDto) {
        UserModel userModel = userMapper.selectById(HappainGlobalUtil.getUserId());
        UserVo userVo = BeanUtil.copyProperties(userModel, UserVo.class);

        List<RoleModel> roleList = userMapper.getRoleList(userVo.getId());
        List<RoleVo> roleVos = BeanUtil.copyToList(roleList, RoleVo.class);
        userVo.setRoles(roleVos);

        return Response.successDataDefault(userVo);
    }

    public Response changePassword(UserDto userDto) {
        UserModel userModel = userMapper.selectById(HappainGlobalUtil.getUserId());
        if(!userModel.getPassword().equals(userDto.getPassword())){
            return Response.failDefault("密码错误");
        }
        userModel.setPassword(userDto.getNewPassword());
        userMapper.updateById(userModel);
        return Response.successDefault();
    }

    public Response chongzhi(UserDto userDto) {
        UserModel model = getById(userDto.getId());
        model.setMoney(model.getMoney() + userDto.getMoney());
        updateById(model);
        return Response.successDefault();
    }

    public Response del(UserDto userDto) {
        UserModel userModel = getById(userDto.getId());
        // TODO: 判断用户名是不是管理员
        if(GlobalConst.ADMIN_USERNAME.equals(userModel.getUsername())) {
            return Response.failDefault("管理员不能删除");
        }
        LambdaQueryWrapper<UserRoleModel> userRoleModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleModelLambdaQueryWrapper.eq(UserRoleModel::getUserId,userDto.getId());
        List<UserRoleModel> roleModels = userRoleService.list(userRoleModelLambdaQueryWrapper);
        if(CollUtil.isNotEmpty(roleModels)) {
            List<Integer> roleIds = roleModels.stream().map(UserRoleModel::getRoleId).toList();
            // TODO: 查询管理员角色信息
            LambdaQueryWrapper<RoleModel> roleModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roleModelLambdaQueryWrapper.eq(RoleModel::getRoleValue,"admin");
            RoleModel roleModel = roleMapper.selectOne(roleModelLambdaQueryWrapper);
            Integer id = roleModel.getId();
            // TODO: 如果用户不是管理员
            if(!roleIds.contains(id)) {
                // TODO: 删除用户
                removeById(userModel);
                return Response.successDefault();
            }
        }
        return Response.failDefault("管理员不能删除");
    }
}
