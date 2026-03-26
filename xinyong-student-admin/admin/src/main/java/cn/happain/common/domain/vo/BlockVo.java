package cn.happain.common.domain.vo;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BlockVo {
    // 区块hash
    private String hash;
    // 交易hash
    private String tx;
    // 区块高度
    private Integer number;
    // 合约地址
    private String contractAddress;
}
