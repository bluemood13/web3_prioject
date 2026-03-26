package cn.happain.module.sys.module.login.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.happain.common.domain.Response;
import cn.happain.module.other.userRole.domain.model.UserRoleModel;
import cn.happain.module.other.userRole.service.impl.UserRoleServiceImpl;
import cn.happain.module.sys.module.login.service.LoginService;
import cn.happain.module.sys.module.role.domain.model.RoleModel;
import cn.happain.module.sys.module.role.domain.model.vo.RoleVo;
import cn.happain.module.sys.module.role.mapper.RoleMapper;
import cn.happain.module.sys.module.role.service.impl.RoleServiceImpl;
import cn.happain.module.sys.module.user.domain.UserModel;
import cn.happain.module.sys.module.user.domain.dto.UserDto;
import cn.happain.module.sys.module.user.domain.vo.UserVo;
import cn.happain.module.sys.module.user.mapper.UserMapper;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author happain
 * @date 2023/2/12
 * @desc
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class LoginServiceImpl implements LoginService, StpInterface {

    @Resource
    UserMapper userMapper;
    @Resource
    UserRoleServiceImpl userRoleService;
    @Resource
    RoleMapper roleMapper;
    @Value("${sa-token.token-name}")
    String saTokenPrefix;
    @Resource
    RoleServiceImpl roleService;


    /**
     * 设置用户基础数据
     *
     * @param userModel 用户模型
     */
    private UserModel updateUser(UserModel userModel) {
        // TODO 赋值登陆时间
        if (userModel.getLoginTime() == null) {
            userModel.setLastLoginTime(LocalDateTime.now());
            userModel.setLoginTime(userModel.getLastLoginTime());
        }else {
            userModel.setLastLoginTime(userModel.getLoginTime());
            userModel.setLoginTime(LocalDateTime.now());
        }
        if(userModel.getId()!=null) {
            userMapper.updateById(userModel);
        }
        return userModel;
    }

    /**
     * 注销
     *
     * @return {@link Response}
     */
    public Response logout() {
        StpUtil.logout();
        return Response.successDefault();
    }

    /**
     * 登录检查
     *
     * @return {@link Response}<{@link UserVo}>
     */
    public Response<UserVo> login(UserDto userDto)  {
        LambdaQueryWrapper<UserModel> userModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userModelLambdaQueryWrapper.eq(UserModel::getUsername,userDto.getUsername());
        userModelLambdaQueryWrapper.eq(UserModel::getPassword,userDto.getPassword());
        UserModel userModel = userMapper.selectOne(userModelLambdaQueryWrapper);
        if(userModel == null) {
            return Response.failDefault("账号或密码错误，登录失败");
        }
        // TODO: 2023/10/5 更新登陆时间
        userModel = updateUser(userModel);
        userMapper.updateById(userModel);
        UserVo userVo = BeanUtil.copyProperties(userModel, UserVo.class);
        // TODO: 2023/3/31 设置token保存到redis
        StpUtil.login(userVo.getId());
        userVo.setToken(StpUtil.getTokenValue());
        // TODO: 2023/10/29 设置用户角色
        List<RoleModel> roleList = userMapper.getRoleList(userVo.getId());
        userVo.setRoles(BeanUtil.copyToList(roleList, RoleVo.class));
        return Response.successDataMessage(userVo,"登陆成功");
    }

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String s) {
        List<RoleVo> roleVos = roleMapper.selectRoleListByUserId(Integer.parseInt(loginId.toString()));
        if(CollUtil.isEmpty(roleVos)) {
            return null;
        }
        return roleVos.stream().map(RoleVo::getRoleValue).collect(Collectors.toList());
    }


    /**
     * @param userDto
     * @return {@link Response}
     */
    public Response register(UserDto userDto) {
        LambdaQueryWrapper<UserModel> userModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userModelLambdaQueryWrapper.eq(UserModel::getUsername,userDto.getUsername());
        Long aLong = userMapper.selectCount(userModelLambdaQueryWrapper);
        if(aLong>0) {
            return Response.failDefault("账号已存在");
        }else {
            UserModel userModel = BeanUtil.copyProperties(userDto, UserModel.class);
            userMapper.insert(userModel);
            // TODO: 2023/12/5 存入默认角色
            List<Integer> roleIds = userDto.getRoleIds();
            LambdaQueryWrapper<RoleModel> roleModelLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roleModelLambdaQueryWrapper.eq(RoleModel::getRoleValue, "default");
            RoleModel roleModel = roleMapper.selectOne(roleModelLambdaQueryWrapper);
            if(!roleIds.contains(roleModel.getId())) {
                roleIds.add(roleModel.getId());
            }
            for (Integer roleId : roleIds) {
                UserRoleModel userRoleModel = new UserRoleModel();
                userRoleModel.setRoleId(roleId);
                userRoleModel.setUserId(userModel.getId());
                userRoleService.save(userRoleModel);
            }
        }
        return Response.successMessage("注册成功");
    }

    /**
     * 检查令牌
     * 检测token是否有效
     *
     * @param userDto 用户 dto
     * @return {@link Response}<{@link UserVo}>
     */
    public Response<UserVo> checkToken(UserDto userDto) {
        String token = userDto.getToken();
        if(StrUtil.isEmpty(token)) {
            return Response.successDefault();
        }
        Object o = StpUtil.getLoginIdByToken(token);

        if(o==null) {
            return Response.successDefault();
        }
        int userId = Integer.parseInt(o.toString());
        UserModel userModel = userMapper.selectById(userId);
        UserVo userVo = BeanUtil.copyProperties(userModel, UserVo.class);
        List<RoleVo> roleVos = roleMapper.selectRoleListByUserId(userId);
        userVo.setToken(token);
        userVo.setRoles(roleVos);
        return Response.successDataDefault(userVo);
    }
}
