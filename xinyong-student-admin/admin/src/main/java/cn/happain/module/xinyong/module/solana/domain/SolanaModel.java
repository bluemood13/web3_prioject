package cn.happain.module.xinyong.module.solana.domain;
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
@TableName(value = "xinyong_solana")
public class SolanaModel  {
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "current_price")
    private Double currentPrice;
    @TableField(value = "hour_max_price")
    private Double hourMaxPrice;
    @TableField(value = "hour_min_price")
    private Double hourMinPrice;
    @TableField(value = "hour_rate")
    private Double hourRate;
    @TableField(value = "day_rate")
    private Double dayRate;
    @TableField(value = "month_rate")
    private Double monthRate;
    @TableField(value = "shizhi")
    private Double shizhi;
    @TableField(value = "chengjiaoliang")
    private Double chengjiaoliang;
    @TableField(value = "total")
    private Double total;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Integer createUser;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private Integer updateUser;
}
