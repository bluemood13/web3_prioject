package cn.happain.module.xinyong.module.qianbao.domain.dto;
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
public class QianbaoDto extends PageDto implements Serializable  {
    @NotNull(message = "编号不能为空",groups = {BaseGroup.del.class,BaseGroup.detail.class,BaseGroup.upd.class})
    private Integer id;
    @NotNull(message="关联用户不能为空",groups = {})
    private Integer userId;
    @NotNull(message="代币数不能为空",groups = {})
    private Double coin;

    private Double price;

    private String types;
    private Integer createUser;
    private String createTime;
    private Integer updateUser;
    private String updateTime;
}
