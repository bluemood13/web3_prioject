package cn.happain.module.other.roleMenu.domain.model;
import com.baomidou.mybatisplus.annotation.*;
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
@TableName(value = "sys_role_menu")
public class RoleMenuModel  {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "role_id")
    private Integer roleId;

    @TableField(value = "menu_id")
    private Integer menuId;

}
