package cn.happain.module.sys.module.file.service.impl;

import cn.happain.common.domain.Response;
import cn.happain.common.domain.vo.PageVo;
import cn.happain.common.utils.HappainGlobalUtil;
import cn.happain.module.sys.module.file.domain.model.FileModel;
import cn.happain.module.sys.module.file.domain.model.dto.FileDto;
import cn.happain.module.sys.module.file.domain.model.vo.FileVo;
import cn.happain.module.sys.module.file.mapper.FileMapper;
import cn.happain.module.sys.module.file.service.IFileService;
import cn.happain.module.sys.module.user.domain.vo.UserVo;
import cn.happain.module.sys.module.user.mapper.UserMapper;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FileServiceImpl extends ServiceImpl<FileMapper, FileModel> implements IFileService {


    @Resource
    UserMapper userMapper;
    @Value("${server.port}")
    String port;


     private List<FileVo> fillVo(List<FileVo> fileVo) {
         for (FileVo vo : fileVo) {
             vo.setCreateUserVo(BeanUtil.copyProperties(userMapper.selectById(vo.getCreateUser()), UserVo.class));
         }
        return fileVo;
     }


     /**
      * 分页查询
      *
      * @param fileDto dto
      * @return {@link Response}<{@link PageVo}<{@link FileVo}>>
      */
     public Response<PageVo<FileVo>> listPage(FileDto fileDto) {
         LambdaQueryWrapper<FileModel> lambda = search(fileDto);
         Page<FileModel> fileModelPage =  fileDto.buildPageVo(FileModel.class);
         Page<FileModel> page = page(fileModelPage, lambda);
         PageVo<FileVo> fileVoPageVo = PageVo.PageModelToVo(page,FileVo.class);
         fileVoPageVo.setList(fillVo(fileVoPageVo.getList()));
         return Response.successDataDefault(fileVoPageVo);
     }
      /**
       * 查询所有
       *
       * @param fileDto dto
       * @return {@link Response}<{@link List}<{@link FileVo}>>
       */
      public Response<List<FileVo>> listAll(FileDto fileDto) {
         LambdaQueryWrapper<FileModel> lambda = search(fileDto);
          List<FileModel> list = list(lambda);
          List<FileVo> fileVos = BeanUtil.copyToList(list, FileVo.class);
          return Response.successDataDefault(fileVos);
      }


     /**
      * 添加
      *
      * @param fileDto dto
      * @return {@link Response}
      */
     public Response add(FileDto fileDto) {
         if(hasData(fileDto)) {
             return Response.failExitData();
         }
         FileModel fileModel = BeanUtil.copyProperties(fileDto, FileModel.class);
         save(fileModel);
         return Response.successDefault();
     }

     /**
      * 修改
      *
      * @param fileDto dto
      * @return {@link Response}
      */
     public Response upd(FileDto fileDto) {
         if(hasData(fileDto)) {
             return Response.failExitData();
         }
         FileModel fileModel = BeanUtil.copyProperties(fileDto, FileModel.class);
         updateById(fileModel);
         return Response.successDefault();
     }

     /**
      * 删除
      *
      * @param fileDto dto
      * @return {@link Response}
      */
     public Response del(FileDto fileDto) {
         removeById(fileDto.getId());
         return Response.successDefault();
     }


     /**
      * 查询详情
      *
      * @param fileDto dto
      * @return {@link Response}
      */
     public Response<FileVo> detail(FileDto fileDto) {
         FileModel model = getById(fileDto.getId());
         if(model == null) {
             return Response.failNoData();
         }
         FileVo fileVo = BeanUtil.copyProperties(model, FileVo.class);
         return Response.successDataDefault(fileVo);
     }

     /**
      * 有数据
      *
      * @param fileDto dto
      * @return boolean
      */
     public boolean hasData(FileDto fileDto) {
         LambdaQueryWrapper<FileModel> lambda = new QueryWrapper<FileModel>().lambda();
         if(fileDto.getId()!=null) {
             lambda.ne(FileModel::getId,fileDto.getId());
         }
         return getOne(lambda)!=null;
     }

     /**
     * 搜索条件
     *
     * @param fileDto dto
     * @return {@link LambdaQueryWrapper}<{@link FileModel}>
     */
     public LambdaQueryWrapper<FileModel> search(FileDto fileDto) {
         LambdaQueryWrapper<FileModel> lambda = new LambdaQueryWrapper<FileModel>();
         if(StrUtil.isNotEmpty(fileDto.getFileName())) {
             lambda.like(FileModel::getFileName,fileDto.getFileName());
         }
         return lambda;
      }


    public Response<List<FileVo>> upload(MultipartFile[] files) throws IOException {
        ArrayList<FileVo> fileVos = new ArrayList<>();
        for (MultipartFile file : files) {
            String[] split = file.getOriginalFilename().split("\\.");
            String fileName = HappainGlobalUtil.getTimestamp() +"." +  split[split.length-1];
            // 获取static目录的绝对路径
            String s = Paths.get("").toAbsolutePath().toString();
            String path =  s +"\\file\\"+ fileName;
            FileUtil.writeFromStream(file.getInputStream(), new File(path));
            FileModel fileModel = new FileModel();
            fileModel.setFileName(fileName);
            fileModel.setFileOriginName(file.getOriginalFilename());
            fileModel.setFileSize((int) file.getSize());
            fileModel.setFilePath(path);
            fileModel.setFileUrl(StrUtil.format("http://localhost:{}/static/{}",port,fileName));

            save(fileModel);
            fileVos.add(BeanUtil.copyProperties(fileModel, FileVo.class));
        }
        return Response.successDataDefault(fileVos);
    }
}
