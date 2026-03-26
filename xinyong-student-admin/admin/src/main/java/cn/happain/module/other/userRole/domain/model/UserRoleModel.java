package cn.happain.module.other.userRole.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
* @author happain
* @since 2023-10-05
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "sys_user_role")
public class UserRoleModel  {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer roleId;

}
