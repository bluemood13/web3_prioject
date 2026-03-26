<template>
  <div class="flex w-full flex-col items-start justify-center">
    <component :is="headTemplateList[headType]" ref="headComRef"> </component>

    <!-- 行情展示与交易 -->
    <div class="w-full pt-4">
      <div
        class="w-full rounded-lg bg-white p-6 shadow-sm dark:border dark:border-gray-800 dark:bg-[#1d1e1f]"
      >
        <!-- 当前价格 -->
        <div class="mb-6 text-center">
          <div class="mb-2 text-sm text-gray-500">当前价格 (CNY)</div>
          <div
            class="text-5xl font-bold"
            :class="getPriceColor(priceItem?.hourRate)"
          >
            ¥{{ formatPrice(priceItem?.currentPrice) }}
          </div>
        </div>

        <!-- 统计指标 -->
        <div class="mb-8 grid grid-cols-2 gap-4 md:grid-cols-5">
          <div
            class="flex flex-col items-center rounded bg-gray-50 p-3 dark:bg-[#262727]"
          >
            <span class="mb-1 text-xs text-gray-500">24H 最高</span>
            <span class="font-medium dark:text-gray-200"
              >¥{{ formatPrice(priceItem?.hourMaxPrice) }}</span
            >
          </div>
          <div
            class="flex flex-col items-center rounded bg-gray-50 p-3 dark:bg-[#262727]"
          >
            <span class="mb-1 text-xs text-gray-500">24H 最低</span>
            <span class="font-medium dark:text-gray-200"
              >¥{{ formatPrice(priceItem?.hourMinPrice) }}</span
            >
          </div>
          <div
            class="flex flex-col items-center rounded bg-gray-50 p-3 dark:bg-[#262727]"
          >
            <span class="mb-1 text-xs text-gray-500">24H 涨跌</span>
            <span
              class="font-medium"
              :class="getRateColor(priceItem?.hourRate)"
            >
              {{ formatRate(priceItem?.hourRate) }}
            </span>
          </div>
          <div
            class="flex flex-col items-center rounded bg-gray-50 p-3 dark:bg-[#262727]"
          >
            <span class="mb-1 text-xs text-gray-500">7D 涨跌</span>
            <span class="font-medium" :class="getRateColor(priceItem?.dayRate)">
              {{ formatRate(priceItem?.dayRate) }}
            </span>
          </div>
          <div
            class="flex flex-col items-center rounded bg-gray-50 p-3 dark:bg-[#262727]"
          >
            <span class="mb-1 text-xs text-gray-500">月涨跌</span>
            <span
              class="font-medium"
              :class="getRateColor(priceItem?.monthRate)"
            >
              {{ formatRate(priceItem?.monthRate) }}
            </span>
          </div>
        </div>

        <!-- 交易按钮 -->
        <div class="flex items-center justify-center gap-8">
          <div
            class="flex h-14 w-40 cursor-pointer items-center justify-center rounded-lg bg-green-500 text-xl font-bold text-white transition hover:bg-green-600"
            @click="handleTrade('buy')"
          >
            买入
          </div>
          <div
            class="flex h-14 w-40 cursor-pointer items-center justify-center rounded-lg bg-red-500 text-xl font-bold text-white transition hover:bg-red-600"
            @click="handleTrade('sell')"
          >
            卖出
          </div>
        </div>
      </div>

      <!-- 交易弹窗 (封装为一个组件) -->
      <TradeDialog ref="tradeDialogRef" />
    </div>
    <!-- 详情 -->
    <DetailCom ref="detailComRef"></DetailCom>
  </div>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { store } from "@/utils/store";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import TableData from "./components/templates/tableData.vue";
import CardData from "./components/templates/cardData.vue";
import DetailCom from "./components/detail.vue";
import ImgHeadCom from "./components/imgHead.vue";
import InfoHeadCom from "./components/infoHead.vue";
import TradeDialog from "./components/tradeDialog.vue";

const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

const activeTab = ref("first");
const headComRef = ref();
// 头部模板
const headTemplateList = {
  img: ImgHeadCom,
  info: InfoHeadCom,
};
// 模板组件列表
const templateList = {
  table: TableData,
  card: CardData,
};
// 切换模板类型
const type = computed(() => {
  return store.global.HomeDataShow;
});
// 头部模板类型
const headType = computed(() => {
  return store.global.HomeHeadShow;
});

watch(
  () => store.global.HomeDataShow,
  async (newVal) => {
    await initComData();
  },
);

watch(
  () => store.global.HomeHeadShow,
  async (newVal) => {
    await initBlockInfo();
  },
);
// 区块链信息
const blockInfo = ref<any>({});
// 组件引用
const comRef = ref();
// 价格
const priceItem = ref();
// 详情部分
const detailComRef = ref();
const tradeDialogRef = ref();
const detailHan = async (item: any) => {
  detailComRef.value.vis = true;
  await detailComRef.value.setForm(item);
};

// 交易弹窗逻辑
const handleTrade = async (type: string) => {
  await getSolanaData();
  if (tradeDialogRef.value) {
    tradeDialogRef.value.open(type, priceItem.value);
  }
};

// 格式化与样式
const formatPrice = (val: any) => {
  if (val === null || val === undefined) return "0.00";
  return Number(val).toFixed(2);
};

const formatRate = (val: any) => {
  if (val === null || val === undefined) return "0.00%";
  const num = Number(val);
  return (num > 0 ? "+" : "") + num.toFixed(2) + "%";
};

const getRateColor = (val: any) => {
  const num = Number(val);
  if (num > 0) return "text-green-600 dark:text-green-400";
  if (num < 0) return "text-red-600 dark:text-red-400";
  return "text-gray-500";
};

// 按照涨跌幅给当前价格着色
const getPriceColor = (rate: any) => {
  return getRateColor(rate);
};

const initComData = async () => {};

const initBlockInfo = async () => {
  const result = await api.sys.block.getBlockInfo({});
  blockInfo.value = result.data;
  console.log(blockInfo.value);
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
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
