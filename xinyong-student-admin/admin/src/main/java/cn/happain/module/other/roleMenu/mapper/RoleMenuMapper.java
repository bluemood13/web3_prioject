package cn.happain.module.other.roleMenu.mapper;
import cn.happain.module.other.roleMenu.domain.model.RoleMenuModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* @author happain
* @since 2023-10-19
*/
@Mapper
@Component
public interface RoleMenuMapper extends BaseMapper<RoleMenuModel> {

}