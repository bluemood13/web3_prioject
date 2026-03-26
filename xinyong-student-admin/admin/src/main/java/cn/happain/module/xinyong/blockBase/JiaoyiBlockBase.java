package cn.happain.module.xinyong.blockBase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class JiaoyiBlockBase implements Serializable {
    private String hash;
    private String tx;
    /**
     * 交易数量【区块】
     */
    private Double num;
    /**
     * 交易类型【区块】
     */
    private String types;
    /**
     * 交易单价【区块】
     */
    private Double price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
