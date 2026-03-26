package cn.happain.module.xinyong.module.jiaoyiLink.domain.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.happain.module.xinyong.module.jiaoyi.domain.vo.JiaoyiVo;





@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class JiaoyiLinkVo {
    
    private Integer id;
    private String hash;
    private String tx;
    private JiaoyiVo jiaoyiIdVo;
    private Integer jiaoyiId;
    private Integer createUser;
    private String createTime;
    private Integer updateUser;
    private String updateTime;
}
