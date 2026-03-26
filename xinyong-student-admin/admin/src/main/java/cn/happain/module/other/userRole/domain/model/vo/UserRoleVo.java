package cn.happain.module.other.userRole.domain.model.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
* @author happain
* @since 2023-10-05
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserRoleVo {
    /**
    * 
    */
    private Integer id;

    /**
    * 
    */
    private Integer userId;

    /**
    * 
    */
    private Integer roleId;

}
