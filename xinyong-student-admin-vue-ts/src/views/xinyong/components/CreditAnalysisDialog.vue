<template>
  <el-dialog
    v-model="visible"
    title="用户信用分析"
    width="500px"
    append-to-body
    @close="handleClose"
  >
    <div v-loading="analyzing" class="flex flex-col gap-4 py-4">
      <!-- 信用分展示 -->
      <div class="text-center">
        <div class="mb-2 text-sm text-gray-500">信用评分</div>
        <div class="text-6xl font-bold" :class="getCreditColor(creditScore)">
          {{ creditScore }}
        </div>
        <div
          class="mt-2 text-sm font-medium"
          :class="getCreditColor(creditScore)"
        >
          {{ getCreditLevel(creditScore) }}
        </div>
      </div>

      <!-- 详细分析数据 -->
      <div
        class="grid grid-cols-2 gap-4 rounded-lg bg-gray-50 p-4 dark:bg-[#262727]"
      >
        <div class="flex flex-col items-center">
          <span class="text-xs text-gray-500">交易总次数</span>
          <span class="font-bold">{{ analysisData.totalTrades }}</span>
        </div>
        <div class="flex flex-col items-center">
          <span class="text-xs text-gray-500">盈利状况</span>
          <span
            class="font-bold"
            :class="analysisData.isProfit ? 'text-green-500' : 'text-red-500'"
          >
            {{ analysisData.isProfit ? "盈利" : "亏损" }}
          </span>
        </div>
        <div class="flex flex-col items-center">
          <span class="text-xs text-gray-500">买入均价</span>
          <span class="font-bold">¥{{ analysisData.avgBuyPrice }}</span>
        </div>
        <div class="flex flex-col items-center">
          <span class="text-xs text-gray-500">卖出均价</span>
          <span class="font-bold">¥{{ analysisData.avgSellPrice }}</span>
        </div>
      </div>

      <div class="text-xs text-gray-400">
        * 分析基于用户的历史买卖记录计算，包含活跃度、盈利能力和资产规模等维度。
      </div>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { api } from "@/utils/api";
import { tt } from "@/utils/tool";

const visible = ref(false);
const analyzing = ref(false);
const creditScore = ref(0);
const analysisData = ref({
  totalTrades: 0,
  isProfit: false,
  avgBuyPrice: "0.00",
  avgSellPrice: "0.00",
});

const handleClose = () => {
  visible.value = false;
};

// 信用分析函数
const analyzeCredit = async (row: any) => {
  analyzing.value = true;
  creditScore.value = 0; // 重置分数

  try {
    // 1. 获取用户交易记录
    const res = await api.xinyong.jiaoyi.getJiaoyiListAll({
      userId: row.userId,
    });
    const trades = res.data || [];

    console.log("用户交易记录:", trades);

    // 2. 初始化统计变量
    let totalBuyCost = 0; // 总买入花费
    let totalBuyNum = 0; // 总买入数量
    let totalSellRevenue = 0; // 总卖出收入
    let totalSellNum = 0; // 总卖出数量
    let tradeCount = trades.length;

    // 3. 遍历交易记录进行统计
    trades.forEach((trade: any) => {
      // 获取嵌套的交易基础信息
      const baseInfo = trade.jiaoyiBlockBase || {};

      const price = Number(baseInfo.price) || 0;
      const amount = Number(baseInfo.num) || 0;
      const type = baseInfo.types; // 'buy' or 'sell'

      if (amount > 0) {
        if (type === "buy") {
          totalBuyCost += price * amount;
          totalBuyNum += amount;
        } else if (type === "sell") {
          totalSellRevenue += price * amount;
          totalSellNum += amount;
        }
      }
    });

    // 调试日志，方便排查数据问题
    console.log("信用分析统计:", {
      totalBuyCost,
      totalBuyNum,
      totalSellRevenue,
      totalSellNum,
    });

    // 4. 计算均价
    const avgBuyPrice = totalBuyNum > 0 ? totalBuyCost / totalBuyNum : 0;
    const avgSellPrice = totalSellNum > 0 ? totalSellRevenue / totalSellNum : 0;

    // 5. 计算信用分
    let score = 400; // 基础分

    // 维度1: 交易活跃度 (每笔交易+5分，上限200)
    const activeScore = Math.min(tradeCount * 5, 200);
    score += activeScore;

    // 维度2: 资产规模 (持有代币数量，假设 row.coin 是持有量，每币+10分，上限150)
    const holdingScore = Math.min((Number(row.coin) || 0) * 10, 150);
    score += holdingScore;

    // 维度3: 盈利能力
    // 策略A: 卖出均价 > 买入均价 (说明低买高卖能力强) -> +100分
    if (avgSellPrice > avgBuyPrice && avgSellPrice > 0) {
      score += 100;
    }
    // 策略B: 已经实现的总盈利 > 0 -> +50分
    if (totalSellRevenue > totalBuyCost && totalBuyCost > 0) {
      score += 50;
    }

    // 限制最高分
    if (score > 950) score = 950;

    // 6. 更新状态
    creditScore.value = Math.floor(score);
    analysisData.value = {
      totalTrades: tradeCount,
      isProfit: avgSellPrice > avgBuyPrice, // 简单判定：均价盈利
      avgBuyPrice: avgBuyPrice.toFixed(2),
      avgSellPrice: avgSellPrice.toFixed(2),
    };
  } catch (error) {
    console.error("分析信用失败", error);
    tt.msg.error("分析失败，请稍后重试");
  } finally {
    analyzing.value = false;
  }
};

// 获取分数颜色
const getCreditColor = (score: number) => {
  if (score >= 700) return "text-green-500";
  if (score >= 600) return "text-blue-500";
  if (score >= 500) return "text-yellow-500";
  return "text-red-500";
};

// 获取信用等级
const getCreditLevel = (score: number) => {
  if (score >= 800) return "信用极好";
  if (score >= 700) return "信用优秀";
  if (score >= 600) return "信用良好";
  if (score >= 500) return "信用一般";
  return "信用较差";
};

const open = (row: any) => {
  visible.value = true;
  analyzeCredit(row);
};

defineExpose({
  open,
});
</script>

<style scoped></style>
