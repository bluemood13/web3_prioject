package cn.happain.module.solana.domain;

import cn.happain.common.utils.SolanaDataProcessor;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SolanaCoreData {

    // 基础信息
    private String id;
    private String symbol;
    private String name;
    private int marketCapRank;
    private long watchlistUsers;
    private String homepage;
    private String whitepaperUrl;

    // 价格数据
    private BigDecimal currentPriceUsd;
    private BigDecimal currentPriceCny;
    private BigDecimal athUsd; // 历史最高价
    private BigDecimal athCny;
    private String athDate;    // 历史最高价日期
    private BigDecimal atlUsd; // 历史最低价
    private BigDecimal atlCny;
    private String atlDate;    // 历史最低价日期

    // 市场数据
    private BigDecimal marketCapUsd;
    private BigDecimal totalVolumeUsd;
    private BigDecimal high24hUsd;
    private BigDecimal low24hUsd;
    private BigDecimal priceChange24hPercent;
    private BigDecimal priceChange7dPercent;
    private BigDecimal priceChange30dPercent;

    // 供应数据
    private BigDecimal circulatingSupply;
    private BigDecimal totalSupply;
    private boolean maxSupplyInfinite;

    // 交易对数据
    private Map<String, TickerData> topTickers = new java.util.HashMap<>();

}
