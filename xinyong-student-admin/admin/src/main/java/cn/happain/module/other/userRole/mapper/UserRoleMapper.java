package cn.happain.module.other.userRole.mapper;
import cn.happain.module.other.userRole.domain.model.UserRoleModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* @author happain
* @since 2023-10-05
*/
@Mapper
@Component
public interface UserRoleMapper extends BaseMapper<UserRoleModel> {

}