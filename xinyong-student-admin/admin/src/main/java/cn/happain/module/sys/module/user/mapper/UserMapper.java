package cn.happain.module.sys.module.user.mapper;

import cn.happain.module.sys.module.role.domain.model.RoleModel;
import cn.happain.module.sys.module.user.domain.UserModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author happain
 * @since 2023-02-15
 */
@Mapper
@Component
public interface UserMapper extends BaseMapper<UserModel> {

    @Select("select * from sys_role where id in (select role_id from sys_user_role where user_id = #{userId})")
    List<RoleModel> getRoleList(@Param("userId") Integer userId);
}
