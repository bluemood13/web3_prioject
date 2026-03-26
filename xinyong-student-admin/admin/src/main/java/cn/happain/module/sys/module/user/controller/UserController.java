package cn.happain.module.sys.module.user.controller;

import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.module.sys.module.user.domain.dto.UserDto;
import cn.happain.module.sys.module.user.domain.vo.UserVo;
import cn.happain.module.sys.module.user.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/sys/user")
public class UserController {
    @Resource
    UserServiceImpl userService;
    @PostMapping("/list/page")
    public Response<PageVo<UserVo>> listPage( @RequestBody UserDto userDto) {
        return userService.listPage(userDto);
    }
    @PostMapping("/list/all")
    public Response<List<UserVo>> listAll( @RequestBody UserDto userDto) {
        return userService.listAll(userDto);
    }
    @PostMapping("/detail")
    public Response<UserVo> detail( @RequestBody UserDto userDto) {
        return userService.detail(userDto);
    }
    @PostMapping("/upd")
    public Response upd(@RequestBody UserDto userDto) {
        return userService.upd(userDto);
    }


    @PostMapping("/del")
    public Response del(@RequestBody UserDto userDto) {
        return userService.del(userDto);
    }

    @PostMapping("/getUserInfo")
    public Response<UserVo> getUserInfo(@RequestBody UserDto userDto) {
        return userService.getUserInfo(userDto);
    }

    @PostMapping("/changePassword")
    public Response changePassword(@RequestBody UserDto userDto) {
        return userService.changePassword(userDto);
    }


    @PostMapping("/add")
    public Response add(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }


    @PostMapping("/chongzhi")
    public Response chongzhi(@RequestBody UserDto userDto) {
        return userService.chongzhi(userDto);
    }
}
