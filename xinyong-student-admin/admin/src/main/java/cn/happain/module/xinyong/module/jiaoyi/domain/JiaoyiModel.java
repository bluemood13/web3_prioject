package cn.happain.module.xinyong.module.jiaoyi.domain;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "xinyong_jiaoyi")
public class JiaoyiModel  {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "hash")
    private String hash;
    @TableField(value = "tx")
    private String tx;
    @TableField(value = "user_id")
    private Integer userId;
    @TableField(value = "num")
    private Double num;
    @TableField(value = "types")
    private String types;
    @TableField(value = "price")
    private Double price;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Integer createUser;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private Integer updateUser;
}
