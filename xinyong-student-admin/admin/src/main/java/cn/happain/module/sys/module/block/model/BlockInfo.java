package cn.happain.module.sys.module.block.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author happain
 * @desc
 * @since 2024/6/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BlockInfo {

    /**
     * 帐户
     */
    private String account;


    /**
     * 区块数量
     */
    private Integer blockNum;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 合约地址
     */
    private String contractAddress;


    private Long gasPrice;

    private Long gasLimit;

}
