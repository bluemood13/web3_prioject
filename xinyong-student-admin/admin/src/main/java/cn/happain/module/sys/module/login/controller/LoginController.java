package cn.happain.module.sys.module.login.controller;

import cn.happain.common.domain.Response;
import cn.happain.module.sys.module.login.service.impl.LoginServiceImpl;
import cn.happain.module.sys.module.user.domain.dto.UserDto;
import cn.happain.module.sys.module.user.domain.vo.UserVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class LoginController {

    @Resource
    private LoginServiceImpl loginService;


    @PostMapping("/login")
    public Response<UserVo> login( @RequestBody UserDto userDto) {
        return loginService.login(userDto);
    }

    @PostMapping("/logout")
    public Response logout() {
        return loginService.logout();
    }


    @PostMapping("/register")
    public Response register( @RequestBody UserDto userDto) {
        return loginService.register(userDto);
    }


    @PostMapping("/checkToken")
    public Response<UserVo> checkToken( @RequestBody UserDto userDto) {
        return loginService.checkToken(userDto);
    }

    @PostMapping("/test")
    public Response test( @RequestBody UserDto userDto) {
        return Response.successDefault();
    }

}
