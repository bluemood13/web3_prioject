package cn.happain.module.sys.module.menu.controller;

import cn.happain.common.domain.Response;
import cn.happain.common.domain.dto.valid.BaseGroup;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.module.sys.module.menu.domain.dto.MenuDto;
import cn.happain.module.sys.module.menu.domain.vo.MenuVo;
import cn.happain.module.sys.module.menu.service.impl.MenuServiceImpl;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/sys/menu")
public class MenuController {

    @Resource
     MenuServiceImpl menuService;


    @PostMapping("/list/page")
    public Response<PageVo<MenuVo>> listPage(@Validated({BaseGroup.page.class}) @RequestBody MenuDto menuDto) {
        return menuService.listPage(menuDto);
    }


    @PostMapping("/list/all")
    public Response<List<MenuVo>> listAll(@Validated({BaseGroup.all.class}) @RequestBody MenuDto menuDto) {
        return menuService.listAll(menuDto);
    }

    @PostMapping("/list/tree")
    public Response<List<MenuVo>> listTree( @RequestBody MenuDto menuDto) {
        return menuService.listTree(menuDto);
    }


    @PostMapping("/detail")
    public Response<MenuVo> detail( MenuDto menuDto) {
        return menuService.detail(menuDto);
    }


    @PostMapping("/add")
    public Response add( @RequestBody MenuDto menuDto) {
        return menuService.add(menuDto);
    }

    @PostMapping("/upd")
    public Response upd( @RequestBody MenuDto menuDto) {
        return menuService.upd(menuDto);
    }

    @PostMapping("/del")
    public Response del( @RequestBody MenuDto menuDto) {
        return menuService.del(menuDto);
    }


    /**
     * 获取用户菜单列表
     *
     * @return {@link Response}<{@link List}<{@link MenuVo}>>
     */
    @PostMapping("/getUserMenu")
    public Response<List<MenuVo>> getUserMenu() {
        return menuService.getUserMenu();
    }


    /**
     * 获取角色菜单列表
     *
     * @param menuDto 菜单 DTO
     * @return {@link Response}<{@link List}<{@link Integer}>>
     */
    @PostMapping("/getRoleMenu")
    public Response<List<Integer>> getRoleMenu( @RequestBody MenuDto menuDto) {
        return menuService.getRoleMenu(menuDto);
    }

    @PostMapping("/getMenuRole")
    public Response<List<Integer>> getMenuRole( @RequestBody MenuDto menuDto) {
        return menuService.getMenuRole(menuDto);
    }

}