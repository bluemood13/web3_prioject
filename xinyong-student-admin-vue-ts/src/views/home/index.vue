<template>
  <div class="home-wrap">
    <!-- 顶部头部组件 -->
    <component :is="headTemplateList[headType]" ref="headComRef" />

    <!-- 行情 + 交易 -->
    <div class="market-card">
      <!-- 标题栏 -->
      <div class="market-card__header">
        <div class="market-card__title">
          <span class="market-card__dot" />
          实时行情
        </div>
        <div class="market-card__badge" :class="badgeClass">
          {{ formatRate(priceItem?.hourRate) }}
        </div>
      </div>

      <!-- 当前价格 -->
      <div class="price-hero">
        <div class="price-hero__label">当前价格（CNY）</div>
        <div class="price-hero__value" :class="getPriceColor(priceItem?.hourRate)">
          ¥{{ formatPrice(priceItem?.currentPrice) }}
        </div>
      </div>

      <!-- 统计指标网格 -->
      <div class="stats-grid">
        <div class="stats-grid__item" v-for="s in statsItems" :key="s.label">
          <span class="stats-grid__label">{{ s.label }}</span>
          <span class="stats-grid__value" :class="s.colorClass">{{ s.value }}</span>
        </div>
      </div>

      <!-- 交易按钮 -->
      <div class="trade-actions">
        <button class="trade-btn trade-btn--buy" @click="handleTrade('buy')">
          <span class="trade-btn__arrow">▲</span> 买入
        </button>
        <button class="trade-btn trade-btn--sell" @click="handleTrade('sell')">
          <span class="trade-btn__arrow">▼</span> 卖出
        </button>
      </div>
    </div>

    <!-- 交易弹窗 -->
    <TradeDialog ref="tradeDialogRef" />

    <!-- 详情 -->
    <DetailCom ref="detailComRef" />
  </div>
</template>

<script setup lang="ts">
import { tt } from '@/utils/tool';
import { api } from '@/utils/api';
import { store } from '@/utils/store';
import { getCurrentInstance, onMounted, ref, computed, watch } from 'vue';
import TableData from './components/templates/tableData.vue';
import CardData from './components/templates/cardData.vue';
import DetailCom from './components/detail.vue';
import ImgHeadCom from './components/imgHead.vue';
import InfoHeadCom from './components/infoHead.vue';
import TradeDialog from './components/tradeDialog.vue';

const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

const headComRef = ref();
const headTemplateList = { img: ImgHeadCom, info: InfoHeadCom };
const templateList = { table: TableData, card: CardData };

const type = computed(() => store.global.HomeDataShow);
const headType = computed(() => store.global.HomeHeadShow);

watch(() => store.global.HomeDataShow, async () => { await initComData(); });
watch(() => store.global.HomeHeadShow, async () => { await initBlockInfo(); });

const blockInfo = ref<any>({});
const priceItem = ref<any>({});
const detailComRef = ref();
const tradeDialogRef = ref();

const handleTrade = async (tradeType: string) => {
  await getSolanaData();
  tradeDialogRef.value?.open(tradeType, priceItem.value);
};

const formatPrice = (val: any) => {
  if (val === null || val === undefined) return '0.00';
  return Number(val).toFixed(2);
};

const formatRate = (val: any) => {
  if (val === null || val === undefined) return '0.00%';
  const n = Number(val);
  return (n > 0 ? '+' : '') + n.toFixed(2) + '%';
};

const getRateColor = (val: any) => {
  const n = Number(val);
  if (n > 0) return 'is-up';
  if (n < 0) return 'is-down';
  return 'is-flat';
};

const getPriceColor = (rate: any) => getRateColor(rate);

const badgeClass = computed(() => getRateColor(priceItem.value?.hourRate));

const statsItems = computed(() => [
  { label: '24H 最高', value: '¥' + formatPrice(priceItem.value?.hourMaxPrice), colorClass: '' },
  { label: '24H 最低', value: '¥' + formatPrice(priceItem.value?.hourMinPrice), colorClass: '' },
  { label: '24H 涨跌', value: formatRate(priceItem.value?.hourRate), colorClass: getRateColor(priceItem.value?.hourRate) },
  { label: '7D 涨跌',  value: formatRate(priceItem.value?.dayRate),  colorClass: getRateColor(priceItem.value?.dayRate) },
  { label: '月涨跌',   value: formatRate(priceItem.value?.monthRate), colorClass: getRateColor(priceItem.value?.monthRate) },
]);

const initComData = async () => {};
const initBlockInfo = async () => {
  const result = await api.sys.block.getBlockInfo({});
  blockInfo.value = result.data;
  headComRef.value.blockInfo = blockInfo.value;
};
const getSolanaData = async () => {
  const result = await api.xinyong.solana.random({});
  priceItem.value = result.data;
};
const initData = async () => {
  await initBlockInfo();
  await initComData();
  await getSolanaData();
};
defineExpose({});
onMounted(async () => { await initData(); });
</script>

<style lang="scss" scoped>
.home-wrap {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  gap: 20px;
}

/* ─── Market Card ─── */
.market-card {
  width: 100%;
  background: var(--el-fill-color-blank);
  border-radius: 20px;
  border: 1px solid color-mix(in srgb, var(--el-border-color) 50%, transparent);
  box-shadow: var(--el-box-shadow-light);
  padding: 28px 32px;
  transition: box-shadow 0.25s;

  :global(html.dark) & {
    background: #111827;
    border-color: rgba(255,255,255,0.07);
  }

  &:hover {
    box-shadow: var(--el-box-shadow);
  }
}

/* Header row */
.market-card__header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 24px;
}

.market-card__title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 15px;
  font-weight: 700;
  color: var(--el-text-color-primary);
  letter-spacing: -0.01em;
}

.market-card__dot {
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: var(--el-color-primary);
  box-shadow: 0 0 0 3px color-mix(in srgb, var(--el-color-primary) 25%, transparent);
  animation: pulse 2s ease-in-out infinite;
}

@keyframes pulse {
  0%, 100% { box-shadow: 0 0 0 3px color-mix(in srgb, var(--el-color-primary) 25%, transparent); }
  50%       { box-shadow: 0 0 0 6px color-mix(in srgb, var(--el-color-primary) 10%, transparent); }
}

.market-card__badge {
  font-size: 13px;
  font-weight: 700;
  padding: 4px 12px;
  border-radius: 999px;

  &.is-up   { background: #dcfce7; color: #16a34a; }
  &.is-down { background: #fee2e2; color: #dc2626; }
  &.is-flat { background: #f1f5f9; color: #64748b; }

  :global(html.dark) &.is-up   { background: rgba(22,163,74,0.2);  color: #4ade80; }
  :global(html.dark) &.is-down { background: rgba(220,38,38,0.2);  color: #f87171; }
  :global(html.dark) &.is-flat { background: rgba(100,116,139,0.2); color: #94a3b8; }
}

/* ─── Price hero ─── */
.price-hero {
  text-align: center;
  margin-bottom: 28px;
}

.price-hero__label {
  font-size: 12px;
  font-weight: 600;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  color: var(--el-text-color-secondary);
  margin-bottom: 8px;
}

.price-hero__value {
  font-size: 56px;
  font-weight: 900;
  letter-spacing: -0.04em;
  line-height: 1;
  transition: color 0.3s;

  &.is-up   { color: #16a34a; }
  &.is-down { color: #dc2626; }
  &.is-flat { color: var(--el-text-color-primary); }

  :global(html.dark) &.is-up   { color: #4ade80; }
  :global(html.dark) &.is-down { color: #f87171; }
}

/* ─── Stats grid ─── */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 12px;
  margin-bottom: 28px;

  @media (max-width: 768px) {
    grid-template-columns: repeat(3, 1fr);
  }
}

.stats-grid__item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 6px;
  background: color-mix(in srgb, var(--el-fill-color-light) 80%, transparent);
  border-radius: 12px;
  padding: 14px 10px;
  border: 1px solid color-mix(in srgb, var(--el-border-color) 40%, transparent);
  transition: transform 0.18s, box-shadow 0.18s;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px -4px rgba(0,0,0,0.1);
  }

  :global(html.dark) & {
    background: rgba(255,255,255,0.04);
    border-color: rgba(255,255,255,0.07);
  }
}

.stats-grid__label {
  font-size: 11px;
  font-weight: 600;
  letter-spacing: 0.05em;
  text-transform: uppercase;
  color: var(--el-text-color-secondary);
}

.stats-grid__value {
  font-size: 15px;
  font-weight: 700;
  color: var(--el-text-color-primary);

  &.is-up   { color: #16a34a; }
  &.is-down { color: #dc2626; }

  :global(html.dark) &.is-up   { color: #4ade80; }
  :global(html.dark) &.is-down { color: #f87171; }
}

/* ─── Trade actions ─── */
.trade-actions {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
}

.trade-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  width: 168px;
  height: 52px;
  justify-content: center;
  border-radius: 14px;
  font-size: 17px;
  font-weight: 800;
  color: #fff;
  border: none;
  cursor: pointer;
  letter-spacing: 0.02em;
  transition: transform 0.18s, box-shadow 0.18s, filter 0.18s;
  position: relative;
  overflow: hidden;

  &::after {
    content: '';
    position: absolute;
    inset: 0;
    background: linear-gradient(180deg, rgba(255,255,255,0.18) 0%, transparent 100%);
    pointer-events: none;
  }

  &:hover {
    transform: translateY(-3px);
    filter: brightness(1.08);
  }
  &:active {
    transform: translateY(0);
    filter: brightness(0.96);
  }
}

.trade-btn--buy {
  background: linear-gradient(135deg, #22c55e, #16a34a);
  box-shadow: 0 6px 20px -4px rgba(22,163,74,0.45);
  &:hover { box-shadow: 0 10px 28px -4px rgba(22,163,74,0.55); }
}

.trade-btn--sell {
  background: linear-gradient(135deg, #f87171, #dc2626);
  box-shadow: 0 6px 20px -4px rgba(220,38,38,0.40);
  &:hover { box-shadow: 0 10px 28px -4px rgba(220,38,38,0.52); }
}

.trade-btn__arrow {
  font-size: 12px;
  opacity: 0.85;
}
</style>
