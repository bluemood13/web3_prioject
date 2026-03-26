package cn.happain.common.utils;

import cn.happain.module.solana.domain.SolanaCoreData;
import cn.happain.module.solana.domain.TickerData;
import cn.happain.module.xinyong.module.solana.domain.SolanaModel;
import cn.happain.module.xinyong.module.solana.domain.dto.SolanaDto;
import cn.happain.module.xinyong.module.solana.service.impl.SolanaServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;

/**
 * Solana数据加工工具类
 * 提取核心有用的加密货币数据
 */
@Component
public class SolanaDataProcessor {


    // JSON解析器
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    // 日期格式化器
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    @Resource
    SolanaServiceImpl solanaService;


    /**
     * 提取Solana核心数据
     */
    public SolanaCoreData extractCoreData(String jsonData) throws JsonProcessingException {
        JsonNode rootNode = OBJECT_MAPPER.readTree(jsonData);

        SolanaCoreData coreData = new SolanaCoreData();

        // ========== 基础信息 ==========
        coreData.setId(rootNode.get("id").asText());
        coreData.setSymbol(rootNode.get("symbol").asText());
        coreData.setName(rootNode.get("name").asText());
        coreData.setMarketCapRank(rootNode.get("market_cap_rank").asInt());
        coreData.setWatchlistUsers(rootNode.get("watchlist_portfolio_users").asLong());
        coreData.setHomepage(rootNode.get("links").get("homepage").get(0).asText());
        coreData.setWhitepaperUrl(rootNode.get("links").get("whitepaper").asText());

        // ========== 核心价格数据 ==========
        JsonNode marketData = rootNode.get("market_data");
        coreData.setCurrentPriceUsd(toBigDecimal(marketData.get("current_price").get("usd")));
        coreData.setCurrentPriceCny(toBigDecimal(marketData.get("current_price").get("cny")));
        coreData.setAthUsd(toBigDecimal(marketData.get("ath").get("usd"))); // 历史最高价(USD)
        coreData.setAthCny(toBigDecimal(marketData.get("ath").get("cny"))); // 历史最高价(CNY)
        coreData.setAtlUsd(toBigDecimal(marketData.get("atl").get("usd"))); // 历史最低价(USD)
        coreData.setAtlCny(toBigDecimal(marketData.get("atl").get("cny"))); // 历史最低价(CNY)

        // 历史最高价日期转换
        String athDateStr = marketData.get("ath_date").get("usd").asText();
        coreData.setAthDate(formatDateTime(athDateStr));
        // 历史最低价日期转换
        String atlDateStr = marketData.get("atl_date").get("usd").asText();
        coreData.setAtlDate(formatDateTime(atlDateStr));

        // ========== 市场数据 ==========
        coreData.setMarketCapUsd(toBigDecimal(marketData.get("market_cap").get("usd")));
        coreData.setTotalVolumeUsd(toBigDecimal(marketData.get("total_volume").get("usd")));
        coreData.setHigh24hUsd(toBigDecimal(marketData.get("high_24h").get("usd")));
        coreData.setLow24hUsd(toBigDecimal(marketData.get("low_24h").get("usd")));

        // 价格涨跌幅
        coreData.setPriceChange24hPercent(toBigDecimal(marketData.get("price_change_percentage_24h")));
        coreData.setPriceChange7dPercent(toBigDecimal(marketData.get("price_change_percentage_7d")));
        coreData.setPriceChange30dPercent(toBigDecimal(marketData.get("price_change_percentage_30d")));

        // ========== 供应数据 ==========
        coreData.setCirculatingSupply(toBigDecimal(marketData.get("circulating_supply")));
        coreData.setTotalSupply(toBigDecimal(marketData.get("total_supply")));
        coreData.setMaxSupplyInfinite(marketData.get("max_supply_infinite").asBoolean());

        // ========== 交易对数据（提取前5个主流交易所） ==========
        JsonNode tickers = rootNode.get("tickers");
        if (tickers.isArray() && tickers.size() > 0) {
            int count = 0;
            Iterator<JsonNode> iterator = tickers.iterator();
            while (iterator.hasNext() && count < 5) {
                JsonNode ticker = iterator.next();
                String marketName = ticker.get("market").get("name").asText();
                String base = ticker.get("base").asText();
                String target = ticker.get("target").asText();
                BigDecimal lastPrice = toBigDecimal(ticker.get("last"));
                BigDecimal volume = toBigDecimal(ticker.get("volume"));

                coreData.getTopTickers().put(
                        marketName + " (" + base + "/" + target + ")",
                        new TickerData(lastPrice, volume)
                );
                count++;
            }
        }

        return coreData;
    }

    public  BigDecimal toBigDecimal(JsonNode node) {
        if (node == null || node.isNull() || node.isMissingNode()) {
            return BigDecimal.ZERO;
        }
        if (node.isNumber()) {
            return node.decimalValue();
        }
        String text = node.asText();
        if (text == null || text.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(text.trim());
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 格式化日期字符串（ISO 8601 -> 本地时间）
     */
    public  String formatDateTime(String isoDateStr) {
        try {
            Instant instant = Instant.parse(isoDateStr);
            return DATE_FORMATTER.format(instant);
        } catch (Exception e) {
            return isoDateStr; // 解析失败返回原始值
        }
    }

    /**
     * 打印加工后的核心数据
     */
    public void saveCoreData(SolanaCoreData coreData) {

        System.out.println("==================== Solana 核心数据 ====================");
        System.out.println("1. 基础信息");
        System.out.println("   ID: " + coreData.getId());
        System.out.println("   代码: " + coreData.getSymbol());
        System.out.println("   名称: " + coreData.getName());

        System.out.println("\n2. 价格数据");
        System.out.println("   当前价格(USD): " + coreData.getCurrentPriceUsd());
        System.out.println("   24小时最高价(USD): " + coreData.getHigh24hUsd());
        System.out.println("   24小时最低价(USD): " + coreData.getLow24hUsd());

        System.out.println("\n3. 涨跌幅");
        System.out.println("   24小时涨跌幅: " + coreData.getPriceChange24hPercent() + "%");
        System.out.println("   7天涨跌幅: " + coreData.getPriceChange7dPercent() + "%");
        System.out.println("   30天涨跌幅: " + coreData.getPriceChange30dPercent() + "%");

        System.out.println("\n4. 市场数据");
        System.out.println("   市值(USD): " + coreData.getMarketCapUsd());
        System.out.println("   24小时成交量(USD): " + coreData.getTotalVolumeUsd());
        System.out.println("   总供应量: " + coreData.getTotalSupply());

        System.out.println("========================================================");

        SolanaDto solanaDto = new SolanaDto();
        solanaDto.setCurrentPrice(coreData.getCurrentPriceUsd().doubleValue());
        solanaDto.setHourMaxPrice(coreData.getHigh24hUsd().doubleValue());
        solanaDto.setHourMinPrice(coreData.getLow24hUsd().doubleValue());
        solanaDto.setHourRate(coreData.getPriceChange24hPercent().doubleValue());
        solanaDto.setDayRate(coreData.getPriceChange7dPercent().doubleValue());
        solanaDto.setMonthRate(coreData.getPriceChange30dPercent().doubleValue());
        solanaDto.setShizhi(coreData.getMarketCapUsd().doubleValue());
        solanaDto.setChengjiaoliang(coreData.getTotalVolumeUsd().doubleValue());
        solanaDto.setTotal(coreData.getTotalSupply().doubleValue());
        solanaService.add(solanaDto);
    }
}