package cn.happain.module.sys.module.file.mapper;
import cn.happain.module.sys.module.file.domain.model.FileModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface FileMapper extends BaseMapper<FileModel> {

}