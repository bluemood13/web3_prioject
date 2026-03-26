package cn.happain.module.sys.module.role.mapper;
import cn.happain.module.sys.module.role.domain.model.RoleModel;
import cn.happain.module.sys.module.role.domain.model.vo.RoleVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
* @author happain
* @since 2023-05-25
*/
@Mapper
@Component
public interface RoleMapper extends BaseMapper<RoleModel> {

    @Select("""
    select * from sys_role sr left join sys_user_role sur on sr.id = sur.role_id left join sys_user su on sur.user_id = su.id where su.id = #{userId}   
    """)
    List<RoleVo> selectRoleListByUserId(@Param("userId") Integer id);

}