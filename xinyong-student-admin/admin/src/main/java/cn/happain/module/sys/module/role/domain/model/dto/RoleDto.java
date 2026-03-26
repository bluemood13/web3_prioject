package cn.happain.module.sys.module.role.domain.model.dto;

import cn.happain.common.domain.dto.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
* @author happain
* @since 2023-05-25
*/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RoleDto extends PageDto  {
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
     * 菜单id列表
     */
    private List<Integer> menuIds;


}
