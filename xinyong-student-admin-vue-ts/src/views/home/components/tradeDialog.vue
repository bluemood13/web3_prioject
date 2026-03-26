<template>
  <el-dialog
    v-model="visible"
    :title="title"
    width="400px"
    destroy-on-close
    align-center
    append-to-body
    @close="handleClose"
  >
    <div class="flex flex-col gap-4 py-2">
      <!-- 价格信息演示 -->
      <div
        class="flex items-center justify-between rounded bg-gray-50 p-3 dark:bg-[#262727]"
      >
        <span class="text-sm text-gray-500">当前价格</span>
        <span
          class="text-lg font-bold"
          :class="
            Number(priceData?.hourRate) > 0
              ? 'text-green-600 dark:text-green-400'
              : 'text-red-600 dark:text-red-400'
          "
        >
          {{ formatPrice(priceData?.currentPrice) }}
        </span>
      </div>

      <!-- 账户信息 -->
      <div class="grid grid-cols-2 gap-3">
        <div
          class="rounded border border-gray-100 bg-white p-2 text-center shadow-sm dark:border-gray-700 dark:bg-gray-800"
        >
          <div class="text-xs text-gray-500">账户余额 (CNY)</div>
          <div class="mt-1 font-bold text-gray-900 dark:text-gray-100">
            ¥{{ userBalance.toFixed(2) }}
          </div>
        </div>
        <div
          class="rounded border border-gray-100 bg-white p-2 text-center shadow-sm dark:border-gray-700 dark:bg-gray-800"
        >
          <div class="text-xs text-gray-500">持有数量</div>
          <div class="mt-1 font-bold text-gray-900 dark:text-gray-100">
            {{ userTokenAmount }}
          </div>
        </div>
      </div>

      <!-- 交易表单 -->
      <div class="flex flex-col gap-3">
        <label class="text-sm font-medium text-gray-700 dark:text-gray-300">
          {{ type === "buy" ? "买入数量" : "卖出数量" }}
        </label>
        <el-input-number
          v-model="amount"
          class="!w-full"
          :min="0"
          :precision="2"
          :step="0.1"
          placeholder="请输入数量"
          controls-position="right"
        />

        <div
          class="mt-2 flex items-center justify-between border-t pt-4 dark:border-gray-700"
        >
          <span class="text-sm text-gray-500">预计总额</span>
          <span class="text-lg font-bold text-gray-900 dark:text-gray-100">
            ¥{{ total }}
          </span>
        </div>
      </div>

      <!-- 底部按钮 -->
      <div class="mt-4 flex gap-3">
        <el-button class="flex-1" size="large" @click="handleClose"
          >取消</el-button
        >
        <el-button
          class="flex-1 text-white"
          :color="type === 'buy' ? '#10b981' : '#ef4444'"
          size="large"
          @click="handleSubmit"
        >
          {{ type === "buy" ? "确认买入" : "确认卖出" }}
        </el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, computed } from "vue";
import { api } from "@/utils/api";
import { tt } from "@/utils/tool";
import { store } from "@/utils/store";

const visible = ref(false);
const type = ref("buy");
const priceData = ref<any>({});
const userBalance = ref(0);
const userTokenAmount = ref(0); // 初始化为0，等待接口获取

// 获取账户余额 (TODO: 用户实现具体接口逻辑)
const fetchAccountBalance = async () => {
  // 模拟接口调用
  const res = await api.sys.user.getUserInfo({ id: store.user.userInfo.id });
  userBalance.value = res.data.money;
};

// 获取代币数量 (TODO: 用户实现具体接口逻辑)
const fetchTokenAmount = async () => {
  // 模拟接口调用
  const res = await api.xinyong.qianbao.getQianbaoListAll({
    userId: store.user.userInfo.id,
  });
  if (res.data && res.data.length > 0) {
    userTokenAmount.value = res.data[0].coin; // 假设第一个是我们需要的代币
  } else {
    userTokenAmount.value = 0; // 没有数据时设置为0
  }
  console.log("Fetching token amount...");
};

const title = computed(() => {
  return type.value === "buy" ? "买入" : "卖出";
});

const formatPrice = (val: any) => {
  if (val === null || val === undefined) return "¥0.00";
  return "¥" + Number(val).toFixed(2);
};

const amount = ref(0);
const total = computed(() => {
  return (amount.value * (Number(priceData.value?.currentPrice) || 0)).toFixed(
    2,
  );
});

const open = (tradeType: string, data: any) => {
  type.value = tradeType;
  priceData.value = data;
  amount.value = 0; // 重置数量
  visible.value = true;
  // 打开弹窗时获取最新数据
  fetchAccountBalance();
  fetchTokenAmount();
};

const handleClose = () => {
  visible.value = false;
};

// 买入请求 (TODO: 用户实现具体接口请求)
const executeBuy = async () => {
  // 这里写买入接口调用
  console.log("执行买入请求...");
  const res = await api.xinyong.qianbao.buy({
    userId: store.user.userInfo.id,
    coin: amount.value,
    // 价格
    price: priceData.value?.currentPrice,
  });
  ElMessage.success("买入成功");
  handleClose();
};

// 卖出请求 (TODO: 用户实现具体接口请求)
const executeSell = async () => {
  // 这里写卖出接口调用
  console.log("执行卖出请求...");
  const res = await api.xinyong.qianbao.sell({
    userId: store.user.userInfo.id,
    coin: amount.value,
    price: priceData.value?.currentPrice,
  });
  ElMessage.success("卖出成功");
  handleClose();
};

const handleSubmit = () => {
  if (amount.value <= 0) {
    ElMessage.warning("请输入有效的数量");
    return;
  }

  const cost = Number(total.value);

  if (type.value === "buy") {
    // 买入：检查余额
    if (userBalance.value < cost) {
      ElMessage.error("账户余额不足，无法买入");
      return;
    }
    executeBuy();
  } else {
    // 卖出：检查持有数量
    if (Number(userTokenAmount.value) < amount.value) {
      ElMessage.error("持有数量不足，无法卖出");
      return;
    }
    executeSell();
  }
};

defineExpose({
  open,
});
</script>

<style scoped></style>
