package cn.happain.module.xinyong.module.solana.domain.dto;
import cn.happain.common.domain.dto.PageDto;
import cn.happain.common.domain.dto.valid.BaseGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import java.sql.Date;
import java.sql.Time;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SolanaDto extends PageDto implements Serializable  {
    @NotNull(message = "编号不能为空",groups = {BaseGroup.del.class,BaseGroup.detail.class,BaseGroup.upd.class})
    private Integer id;
    @NotNull(message="当前价格不能为空",groups = {})
    private Double currentPrice;
    @NotNull(message="24小时最高价不能为空",groups = {})
    private Double hourMaxPrice;
    @NotNull(message="24小时最低价不能为空",groups = {})
    private Double hourMinPrice;
    @NotNull(message="24小时涨跌幅不能为空",groups = {})
    private Double hourRate;
    @NotNull(message="7天涨跌幅不能为空",groups = {})
    private Double dayRate;
    @NotNull(message="月涨跌幅不能为空",groups = {})
    private Double monthRate;
    @NotNull(message="市值不能为空",groups = {})
    private Double shizhi;
    @NotNull(message="成交量不能为空",groups = {})
    private Double chengjiaoliang;
    @NotNull(message="总量不能为空",groups = {})
    private Double total;
    private Integer createUser;
    private String createTime;
    private Integer updateUser;
    private String updateTime;
}
