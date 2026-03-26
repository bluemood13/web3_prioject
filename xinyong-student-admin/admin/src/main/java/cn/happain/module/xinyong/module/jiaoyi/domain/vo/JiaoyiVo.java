package cn.happain.module.xinyong.module.jiaoyi.domain.vo;
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

import cn.happain.module.xinyong.blockBase.JiaoyiBlockBase;




@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class JiaoyiVo {
    private JiaoyiBlockBase jiaoyiBlockBase;
    private List<JiaoyiBlockBase> jiaoyiBlockBaseList;
    
    private Integer id;
    private String hash;
    private String tx;
    private UserVo userIdVo;
    private Integer userId;
    private Double num;
    private String types;
    private Double price;
    private Integer createUser;
    private String createTime;
    private Integer updateUser;
    private String updateTime;
}
