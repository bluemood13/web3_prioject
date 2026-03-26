package cn.happain.module.other.roleMenu.domain.model.dto;

import cn.happain.common.domain.dto.PageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
* @author happain
* @since 2023-10-19
*/
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RoleMenuDto extends PageDto  {
    /**
    * 
    */
    private Integer id;

    /**
    * 
    */
    private Integer roleId;

    /**
    * 
    */
    private Integer menuId;

}
