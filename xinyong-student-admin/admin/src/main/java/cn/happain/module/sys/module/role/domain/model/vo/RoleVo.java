package cn.happain.module.sys.module.role.domain.model.vo;

import cn.happain.module.sys.module.user.domain.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author happain
* @since 2023-05-25
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RoleVo {
    /**
    * 
    */
    private Integer id;


    /**
     * 角色标识
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleValue;


    /**
     * 关联角色的用户列表
     */
    private List<UserVo> userVos;

    private LocalDateTime createTime;


}
