package cn.happain.module.sys.module.info.controller;

import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.module.sys.module.info.domain.model.dto.InfoDto;
import cn.happain.module.sys.module.info.domain.model.vo.InfoVo;
import cn.happain.module.sys.module.info.service.impl.InfoServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sys/info")
public class InfoController {
    @Resource
    InfoServiceImpl infoService;


    @PostMapping("/add")
    public Response add( @RequestBody InfoDto infoDto) {
        return infoService.add(infoDto);
    }


    @PostMapping("/upd")
    public Response upd( @RequestBody InfoDto infoDto) {
       return infoService.upd(infoDto);
    }


    @PostMapping("/del")
    public Response del( @RequestBody InfoDto infoDto) {
      return infoService.del(infoDto);
    }


    @PostMapping("/detail")
    public Response<InfoVo> detail( @RequestBody InfoDto infoDto) {
        return infoService.detail(infoDto);
    }


    @PostMapping("/list/page")
    public Response<PageVo<InfoVo>> listPage( @RequestBody InfoDto infoDto) {
        return infoService.listPage(infoDto);
    }


    @PostMapping("/list/all")
    public Response<List<InfoVo>> listAll( @RequestBody InfoDto infoDto) {
       return infoService.listAll(infoDto);
    }

    @PostMapping("/getNew")
    public Response<InfoVo> getNew( @RequestBody InfoDto infoDto) {
        return infoService.getNew(infoDto);
    }



}