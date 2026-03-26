package cn.happain.module.xinyong.module.jiaoyiLink.controller;
import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.common.domain.dto.valid.BaseGroup;
import cn.happain.module.xinyong.module.jiaoyiLink.domain.dto.JiaoyiLinkDto;
import cn.happain.module.xinyong.module.jiaoyiLink.domain.vo.JiaoyiLinkVo;
import cn.happain.module.xinyong.module.jiaoyiLink.service.impl.JiaoyiLinkServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.Resource;
import cn.happain.common.domain.vo.BlockVo;
import java.util.List;

@RestController
@RequestMapping("/xinyong/jiaoyiLink")
public class JiaoyiLinkController {
    @Resource
    JiaoyiLinkServiceImpl jiaoyiLinkService;

    @PostMapping("/add")
    public Response add(@Validated({BaseGroup.add.class}) @RequestBody JiaoyiLinkDto jiaoyiLinkDto) {
        return jiaoyiLinkService.add(jiaoyiLinkDto);
    }


    @PostMapping("/upd")
    public Response upd(@Validated({BaseGroup.upd.class}) @RequestBody JiaoyiLinkDto jiaoyiLinkDto) {
       return jiaoyiLinkService.upd(jiaoyiLinkDto);
    }


    @PostMapping("/del")
    public Response del(@Validated({BaseGroup.del.class}) @RequestBody JiaoyiLinkDto jiaoyiLinkDto) {
      return jiaoyiLinkService.del(jiaoyiLinkDto);
    }


    @PostMapping("/detail")
    public Response<JiaoyiLinkVo> detail(@Validated({BaseGroup.detail.class}) @RequestBody JiaoyiLinkDto jiaoyiLinkDto) {
        return jiaoyiLinkService.detail(jiaoyiLinkDto);
    }


    @PostMapping("/list/page")
    public Response<PageVo<JiaoyiLinkVo>> listPage(@Validated({BaseGroup.page.class}) @RequestBody JiaoyiLinkDto jiaoyiLinkDto) {
        return jiaoyiLinkService.listPage(jiaoyiLinkDto);
    }


    @PostMapping("/list/all")
    public Response<List<JiaoyiLinkVo>> listAll(@Validated({BaseGroup.all.class}) @RequestBody JiaoyiLinkDto jiaoyiLinkDto) {
       return jiaoyiLinkService.listAll(jiaoyiLinkDto);
    }
}