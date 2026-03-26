package cn.happain.module.sys.module.file.controller;

import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.module.sys.module.file.domain.model.dto.FileDto;
import cn.happain.module.sys.module.file.domain.model.vo.FileVo;
import cn.happain.module.sys.module.file.service.impl.FileServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sys/file")
public class FileController {
    @Resource
    FileServiceImpl fileService;


    @PostMapping("/upload")
    public Response<List<FileVo>> upload(@RequestParam("files") MultipartFile[] files) throws Exception {
        return fileService.upload(files);
    }

    @PostMapping("/add")
    public Response add(@RequestBody FileDto fileDto) {
        return fileService.add(fileDto);
    }


    @PostMapping("/upd")
    public Response upd(@RequestBody FileDto fileDto) {
       return fileService.upd(fileDto);
    }


    @PostMapping("/del")
    public Response del( @RequestBody FileDto fileDto) {
      return fileService.del(fileDto);
    }


    @PostMapping("/detail")
    public Response<FileVo> detail( @RequestBody FileDto fileDto) {
        return fileService.detail(fileDto);
    }


    @PostMapping("/list/page")
    public Response<PageVo<FileVo>> listPage( @RequestBody FileDto fileDto) {
        return fileService.listPage(fileDto);
    }


    @PostMapping("/list/all")
    public Response<List<FileVo>> listAll( @RequestBody FileDto fileDto) {
       return fileService.listAll(fileDto);
    }



}