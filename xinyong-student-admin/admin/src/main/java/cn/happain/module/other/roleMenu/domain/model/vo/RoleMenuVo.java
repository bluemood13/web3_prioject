package cn.happain.module.other.roleMenu.domain.model.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
* @author happain
* @since 2023-10-19
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RoleMenuVo {
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
