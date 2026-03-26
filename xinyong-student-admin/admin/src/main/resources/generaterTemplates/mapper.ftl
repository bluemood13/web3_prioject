package ${packageName}.mapper;
import ${packageName}.domain.model.${modelName}Model;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface ${modelName}Mapper extends BaseMapper<${modelName}Model> {

}