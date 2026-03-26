package cn.happain.module.solana.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TickerData {
    private BigDecimal lastPrice; // 最新价
    private BigDecimal volume;    // 成交量
}
