package cn.happain.module.sys.module.user.domain.vo;

import cn.happain.module.sys.module.role.domain.model.vo.RoleVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author happain
 * @date 2023/2/12
 * @desc
 */
@Data
@Accessors(chain = true)
public class UserVo {

    private Integer id;
    private String token;
    private String username;

    private String password;
    private Double money;

    private String name;

    private String avatar;


    private Integer sex;

    private String phone;


    private String email;

    private String content;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    private List<Integer> roleIds;

    private List<RoleVo> roles;
}
