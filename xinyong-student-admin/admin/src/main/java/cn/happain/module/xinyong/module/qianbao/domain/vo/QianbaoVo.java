package cn.happain.module.xinyong.module.qianbao.domain.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.happain.module.sys.module.user.domain.vo.UserVo;





@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class QianbaoVo {
    
    private Integer id;
    private UserVo userIdVo;
    private Integer userId;
    private Double coin;
    private Integer createUser;
    private String createTime;
    private Integer updateUser;
    private String updateTime;
}
