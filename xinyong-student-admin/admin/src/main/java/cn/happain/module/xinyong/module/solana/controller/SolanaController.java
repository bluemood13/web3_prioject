package cn.happain.module.xinyong.module.solana.controller;
import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.common.domain.dto.valid.BaseGroup;
import cn.happain.module.xinyong.module.solana.domain.dto.SolanaDto;
import cn.happain.module.xinyong.module.solana.domain.vo.SolanaVo;
import cn.happain.module.xinyong.module.solana.service.impl.SolanaServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.annotation.Resource;
import cn.happain.common.domain.vo.BlockVo;
import java.util.List;

@RestController
@RequestMapping("/xinyong/solana")
public class SolanaController {
    @Resource
    SolanaServiceImpl solanaService;

    @PostMapping("/add")
    public Response add(@Validated({BaseGroup.add.class}) @RequestBody SolanaDto solanaDto) {
        return solanaService.add(solanaDto);
    }


    @PostMapping("/upd")
    public Response upd(@Validated({BaseGroup.upd.class}) @RequestBody SolanaDto solanaDto) {
       return solanaService.upd(solanaDto);
    }


    @PostMapping("/del")
    public Response del(@Validated({BaseGroup.del.class}) @RequestBody SolanaDto solanaDto) {
      return solanaService.del(solanaDto);
    }


    @PostMapping("/detail")
    public Response<SolanaVo> detail(@Validated({BaseGroup.detail.class}) @RequestBody SolanaDto solanaDto) {
        return solanaService.detail(solanaDto);
    }


    @PostMapping("/list/page")
    public Response<PageVo<SolanaVo>> listPage(@Validated({BaseGroup.page.class}) @RequestBody SolanaDto solanaDto) {
        return solanaService.listPage(solanaDto);
    }


    @PostMapping("/list/all")
    public Response<List<SolanaVo>> listAll(@Validated({BaseGroup.all.class}) @RequestBody SolanaDto solanaDto) {
       return solanaService.listAll(solanaDto);
    }

    @PostMapping("/random")
    public Response<SolanaVo> SolanaVo(@Validated({BaseGroup.all.class}) @RequestBody SolanaDto solanaDto) {
        return solanaService.SolanaVo(solanaDto);
    }


}