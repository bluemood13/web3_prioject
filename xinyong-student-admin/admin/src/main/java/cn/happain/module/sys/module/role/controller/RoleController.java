package cn.happain.module.sys.module.role.controller;

import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.module.sys.module.role.domain.model.dto.RoleDto;
import cn.happain.module.sys.module.role.domain.model.vo.RoleVo;
import cn.happain.module.sys.module.role.service.impl.RoleServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* @author happain
* @since 2023-02-15
*/
@RestController
@RequestMapping("/sys/role")
public class RoleController {
    @Resource
    RoleServiceImpl roleService;


    @PostMapping("/add")
    public Response add( @RequestBody RoleDto roleDto) {
        return roleService.add(roleDto);
    }


    @PostMapping("/upd")
    public Response upd( @RequestBody RoleDto roleDto) {
        return roleService.upd(roleDto);
    }


    @PostMapping("/del")
    public Response del( @RequestBody RoleDto roleDto) {

        return roleService.del(roleDto);
    }


    @PostMapping("/delForce")
    public Response delForce( @RequestBody RoleDto roleDto) {
        return roleService.delForce(roleDto);
    }

    @PostMapping("/detail")
    public Response<RoleVo> detail(@RequestBody RoleDto roleDto) {
        return roleService.detail(roleDto);
    }


    @PostMapping("/list/page")
    public Response<PageVo<RoleVo>> listPage( @RequestBody RoleDto roleDto) {
        return roleService.listPage(roleDto);
    }


    @PostMapping("/list/all")
    public Response<List<RoleVo>> listAll( @RequestBody RoleDto roleDto) {
        return roleService.listAll(roleDto);
    }


    /**
     * 查看角色关联的用户列表
     *
     * @param roleDto 角色dto
     * @return {@link Response}<{@link List}<{@link RoleVo}>>
     */
    @PostMapping("/member")
    public Response<RoleVo> roleMember( @RequestBody RoleDto roleDto) {
        return roleService.roleMember(roleDto);
    }


    /**
     * 获取角色的列表通过用户id
     *
     * @param roleDto 角色dto
     * @return {@link Response}<{@link RoleVo}>
     */
    @PostMapping("/getRoleListByUserId")
    public Response<List<String>> getRoleListByUserId(@RequestBody RoleDto roleDto) {
        return roleService.getRoleListByUserId(roleDto);
    }


}