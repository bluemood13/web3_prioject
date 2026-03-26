package cn.happain.module.sys.module.user.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author happain
 * @since 2023-02-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_user")
public class UserModel  {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @TableField("username")
    private String username;


    @TableField("password")
    private String password;


    @TableField("money")
    private Double money;


    @TableField("avatar")
    private String avatar;

    @TableField("name")
    private String name;


    @TableField("sex")
    private Integer sex;

    @TableField("phone")
    private String phone;


    @TableField("email")
    private String email;

    @TableField("content")
    private String content;


    @TableField("login_time")
    private LocalDateTime loginTime;

    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
