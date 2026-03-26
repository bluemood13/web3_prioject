package cn.happain.module.xinyong.module.jiaoyi.controller;
import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.common.domain.dto.valid.BaseGroup;
import cn.happain.module.xinyong.module.jiaoyi.domain.dto.JiaoyiDto;
import cn.happain.module.xinyong.module.jiaoyi.domain.vo.JiaoyiVo;
import cn.happain.module.xinyong.module.jiaoyi.service.impl.JiaoyiServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.Resource;
import cn.happain.common.domain.vo.BlockVo;
import java.util.List;

@RestController
@RequestMapping("/xinyong/jiaoyi")
public class JiaoyiController {
    @Resource
    JiaoyiServiceImpl jiaoyiService;

    @PostMapping("/add")
    public Response<BlockVo> add(@Validated({BaseGroup.add.class}) @RequestBody JiaoyiDto jiaoyiDto) {
        return jiaoyiService.add(jiaoyiDto);
    }


    @PostMapping("/upd")
    public Response<BlockVo> upd(@Validated({BaseGroup.upd.class}) @RequestBody JiaoyiDto jiaoyiDto) {
       return jiaoyiService.upd(jiaoyiDto);
    }


    @PostMapping("/del")
    public Response del(@Validated({BaseGroup.del.class}) @RequestBody JiaoyiDto jiaoyiDto) {
      return jiaoyiService.del(jiaoyiDto);
    }


    @PostMapping("/detail")
    public Response<JiaoyiVo> detail(@Validated({BaseGroup.detail.class}) @RequestBody JiaoyiDto jiaoyiDto) {
        return jiaoyiService.detail(jiaoyiDto);
    }


    @PostMapping("/list/page")
    public Response<PageVo<JiaoyiVo>> listPage(@Validated({BaseGroup.page.class}) @RequestBody JiaoyiDto jiaoyiDto) {
        return jiaoyiService.listPage(jiaoyiDto);
    }


    @PostMapping("/list/all")
    public Response<List<JiaoyiVo>> listAll(@Validated({BaseGroup.all.class}) @RequestBody JiaoyiDto jiaoyiDto) {
       return jiaoyiService.listAll(jiaoyiDto);
    }
}