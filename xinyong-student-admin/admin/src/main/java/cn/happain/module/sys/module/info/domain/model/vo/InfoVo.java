package cn.happain.module.sys.module.info.domain.model.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class InfoVo {
    /**
    * 
    */
    private Integer id;

    /**
    * 
    */
    private String info;

    /**
    * 
    */
    private LocalDateTime createTime;

    /**
    * 
    */
    private Integer createUser;

    /**
    * 
    */
    private LocalDateTime updateTime;

    /**
    * 
    */
    private Integer updateUser;

}
