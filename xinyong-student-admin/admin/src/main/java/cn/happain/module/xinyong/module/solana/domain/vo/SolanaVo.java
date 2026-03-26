package cn.happain.module.xinyong.module.solana.domain.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;





@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SolanaVo {
    
    private Integer id;
    private Double currentPrice;
    private Double hourMaxPrice;
    private Double hourMinPrice;
    private Double hourRate;
    private Double dayRate;
    private Double monthRate;
    private Double shizhi;
    private Double chengjiaoliang;
    private Double total;
    private Integer createUser;
    private String createTime;
    private Integer updateUser;
    private String updateTime;
}
