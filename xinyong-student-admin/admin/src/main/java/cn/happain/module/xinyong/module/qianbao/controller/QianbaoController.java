package cn.happain.module.xinyong.module.qianbao.controller;
import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.common.domain.dto.valid.BaseGroup;
import cn.happain.module.xinyong.module.qianbao.domain.dto.QianbaoDto;
import cn.happain.module.xinyong.module.qianbao.domain.vo.QianbaoVo;
import cn.happain.module.xinyong.module.qianbao.service.impl.QianbaoServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.Resource;
import cn.happain.common.domain.vo.BlockVo;
import java.util.List;

@RestController
@RequestMapping("/xinyong/qianbao")
public class QianbaoController {
    @Resource
    QianbaoServiceImpl qianbaoService;

    @PostMapping("/add")
    public Response add(@Validated({BaseGroup.add.class}) @RequestBody QianbaoDto qianbaoDto) {
        return qianbaoService.add(qianbaoDto);
    }


    @PostMapping("/upd")
    public Response upd(@Validated({BaseGroup.upd.class}) @RequestBody QianbaoDto qianbaoDto) {
       return qianbaoService.upd(qianbaoDto);
    }


    @PostMapping("/del")
    public Response del(@Validated({BaseGroup.del.class}) @RequestBody QianbaoDto qianbaoDto) {
      return qianbaoService.del(qianbaoDto);
    }


    @PostMapping("/detail")
    public Response<QianbaoVo> detail(@Validated({BaseGroup.detail.class}) @RequestBody QianbaoDto qianbaoDto) {
        return qianbaoService.detail(qianbaoDto);
    }


    @PostMapping("/list/page")
    public Response<PageVo<QianbaoVo>> listPage(@Validated({BaseGroup.page.class}) @RequestBody QianbaoDto qianbaoDto) {
        return qianbaoService.listPage(qianbaoDto);
    }


    @PostMapping("/list/all")
    public Response<List<QianbaoVo>> listAll(@Validated({BaseGroup.all.class}) @RequestBody QianbaoDto qianbaoDto) {
       return qianbaoService.listAll(qianbaoDto);
    }

    @PostMapping("/buy")
    public Response buy(@Validated({BaseGroup.all.class}) @RequestBody QianbaoDto qianbaoDto) {
        return qianbaoService.buy(qianbaoDto);
    }

    @PostMapping("/sell")
    public Response sell(@Validated({BaseGroup.all.class}) @RequestBody QianbaoDto qianbaoDto) {
        return qianbaoService.sell(qianbaoDto);
    }
}