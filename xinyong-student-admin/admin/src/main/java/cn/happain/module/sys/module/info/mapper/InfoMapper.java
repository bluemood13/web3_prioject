package cn.happain.module.sys.module.info.mapper;
import cn.happain.module.sys.module.info.domain.model.InfoModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface InfoMapper extends BaseMapper<InfoModel> {

}