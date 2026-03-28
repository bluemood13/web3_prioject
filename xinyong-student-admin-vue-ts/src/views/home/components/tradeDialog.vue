<template>
  <el-dialog
    v-model="visible"
    :title="title"
    width="440px"
    destroy-on-close
    align-center
    append-to-body
    @close="handleClose"
    class="trade-dialog"
  >
    <div class="td-body">
      <!-- 当前价格横幅 -->
      <div class="td-price-banner" :class="bannerClass">
        <span class="td-price-banner__label">当前价格</span>
        <span class="td-price-banner__value">{{ formatPrice(priceData?.currentPrice) }}</span>
      </div>

      <!-- 账户信息 -->
      <div class="td-account-grid">
        <div class="td-account-item">
          <div class="td-account-item__label">账户余额 (CNY)</div>
          <div class="td-account-item__value">{{ userBalance.toFixed(2) }}</div>
        </div>
        <div class="td-account-item">
          <div class="td-account-item__label">持有数量</div>
          <div class="td-account-item__value">{{ userTokenAmount }}</div>
        </div>
      </div>

      <!-- 数量输入 -->
      <div class="td-field">
        <label class="td-field__label">{{ type === 'buy' ? '买入数量' : '卖出数量' }}</label>
        <el-input-number
          v-model="amount"
          class="td-input-number"
          :min="0"
          :precision="2"
          :step="0.1"
          placeholder="请输入数量"
          controls-position="right"
        />
      </div>

      <!-- 预计总额 -->
      <div class="td-total">
        <span class="td-total__label">预计总额</span>
        <span class="td-total__value">{{ total }}</span>
      </div>

      <!-- 操作按钮 -->
      <div class="td-actions">
        <el-button class="td-actions__cancel" size="large" @click="handleClose">取消</el-button>
        <button
          class="td-actions__confirm"
          :class="type === 'buy' ? 'td-actions__confirm--buy' : 'td-actions__confirm--sell'"
          @click="handleSubmit"
        >
          {{ type === 'buy' ? '确认买入' : '确认卖出' }}
        </button>
      </div>
    </div>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import { api } from '@/utils/api';
import { store } from '@/utils/store';

const visible = ref(false);
const type = ref('buy');
const priceData = ref<any>({});
const userBalance = ref(0);
const userTokenAmount = ref(0);

const fetchAccountBalance = async () => {
  const res = await api.sys.user.getUserInfo({ id: store.user.userInfo.id });
  userBalance.value = res.data.money;
};

const fetchTokenAmount = async () => {
  const res = await api.xinyong.qianbao.getQianbaoListAll({ userId: store.user.userInfo.id });
  userTokenAmount.value = res.data?.length > 0 ? res.data[0].coin : 0;
};

const title = computed(() => (type.value === 'buy' ? '买入' : '卖出'));

const bannerClass = computed(() =>
  Number(priceData.value?.hourRate) >= 0 ? 'td-price-banner--up' : 'td-price-banner--down'
);

const formatPrice = (val: any) => {
  if (val === null || val === undefined) return '¥0.00';
  return '¥' + Number(val).toFixed(2);
};

const amount = ref(0);
const total = computed(() =>
  '¥' + (amount.value * (Number(priceData.value?.currentPrice) || 0)).toFixed(2)
);

const open = (tradeType: string, data: any) => {
  type.value = tradeType;
  priceData.value = data;
  amount.value = 0;
  visible.value = true;
  fetchAccountBalance();
  fetchTokenAmount();
};

const handleClose = () => {
  visible.value = false;
};

const executeBuy = async () => {
  await api.xinyong.qianbao.buy({
    userId: store.user.userInfo.id,
    coin: amount.value,
    price: priceData.value?.currentPrice,
  });
  ElMessage.success('买入成功');
  handleClose();
};

const executeSell = async () => {
  await api.xinyong.qianbao.sell({
    userId: store.user.userInfo.id,
    coin: amount.value,
    price: priceData.value?.currentPrice,
  });
  ElMessage.success('卖出成功');
  handleClose();
};

const handleSubmit = () => {
  if (amount.value <= 0) {
    ElMessage.warning('请输入有效的数量');
    return;
  }
  const cost = amount.value * (Number(priceData.value?.currentPrice) || 0);
  if (type.value === 'buy') {
    if (userBalance.value < cost) { ElMessage.error('账户余额不足，无法买入'); return; }
    executeBuy();
  } else {
    if (Number(userTokenAmount.value) < amount.value) { ElMessage.error('持有数量不足，无法卖出'); return; }
    executeSell();
  }
};

defineExpose({ open });
</script>

<style lang="scss" scoped>
/* ─── Dialog override ─── */
:deep(.el-dialog) {
  border-radius: 20px !important;
  overflow: hidden;
}

:deep(.el-dialog__header) {
  padding: 20px 24px 16px;
  background: linear-gradient(
    135deg,
    color-mix(in srgb, var(--el-color-primary) 6%, var(--el-fill-color-blank)),
    var(--el-fill-color-blank)
  );
  border-bottom: 1px solid color-mix(in srgb, var(--el-border-color) 40%, transparent);

  .el-dialog__title {
    font-weight: 800;
    font-size: 18px;
    letter-spacing: -0.02em;
    color: var(--el-text-color-primary);
  }
}

:deep(.el-dialog__body) {
  padding: 0;
}

/* ─── Body ─── */
.td-body {
  display: flex;
  flex-direction: column;
  gap: 16px;
  padding: 20px 24px 24px;
}

/* ─── Price banner ─── */
.td-price-banner {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 18px;
  border-radius: 14px;
  border: 1px solid transparent;

  &--up {
    background: linear-gradient(135deg, #dcfce7, #f0fdf4);
    border-color: #bbf7d0;
    .td-price-banner__value { color: #16a34a; }

    :global(html.dark) & {
      background: rgba(22, 163, 74, 0.15);
      border-color: rgba(22, 163, 74, 0.3);
      .td-price-banner__value { color: #4ade80; }
    }
  }

  &--down {
    background: linear-gradient(135deg, #fee2e2, #fff1f2);
    border-color: #fecaca;
    .td-price-banner__value { color: #dc2626; }

    :global(html.dark) & {
      background: rgba(220, 38, 38, 0.15);
      border-color: rgba(220, 38, 38, 0.3);
      .td-price-banner__value { color: #f87171; }
    }
  }
}

.td-price-banner__label {
  font-size: 12px;
  font-weight: 600;
  letter-spacing: 0.06em;
  text-transform: uppercase;
  color: var(--el-text-color-secondary);
}

.td-price-banner__value {
  font-size: 22px;
  font-weight: 800;
  letter-spacing: -0.03em;
}

/* ─── Account grid ─── */
.td-account-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
}

.td-account-item {
  background: color-mix(in srgb, var(--el-fill-color-light) 80%, transparent);
  border: 1px solid color-mix(in srgb, var(--el-border-color) 40%, transparent);
  border-radius: 12px;
  padding: 12px 14px;
  text-align: center;

  :global(html.dark) & {
    background: rgba(255, 255, 255, 0.05);
    border-color: rgba(255, 255, 255, 0.08);
  }
}

.td-account-item__label {
  font-size: 11px;
  font-weight: 600;
  letter-spacing: 0.05em;
  text-transform: uppercase;
  color: var(--el-text-color-secondary);
  margin-bottom: 6px;
}

.td-account-item__value {
  font-size: 17px;
  font-weight: 800;
  color: var(--el-text-color-primary);
  letter-spacing: -0.02em;

  :global(html.dark) & { color: #f1f5f9; }
}

/* ─── Field ─── */
.td-field {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.td-field__label {
  font-size: 13px;
  font-weight: 600;
  color: var(--el-text-color-regular);
}

.td-input-number {
  width: 100% !important;
}

/* ─── Total ─── */
.td-total {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 14px 18px;
  background: color-mix(in srgb, var(--el-fill-color-light) 70%, transparent);
  border-radius: 12px;
  border-top: 1px solid color-mix(in srgb, var(--el-border-color) 35%, transparent);

  :global(html.dark) & {
    background: rgba(255, 255, 255, 0.04);
    border-color: rgba(255, 255, 255, 0.07);
  }
}

.td-total__label {
  font-size: 13px;
  font-weight: 600;
  color: var(--el-text-color-secondary);
}

.td-total__value {
  font-size: 20px;
  font-weight: 800;
  color: var(--el-text-color-primary);
  letter-spacing: -0.02em;

  :global(html.dark) & { color: #f1f5f9; }
}

/* ─── Actions ─── */
.td-actions {
  display: flex;
  gap: 12px;

  .td-actions__cancel {
    flex: 1;
    height: 46px;
    border-radius: 12px !important;
    font-weight: 600 !important;
  }
}

.td-actions__confirm {
  flex: 1;
  height: 46px;
  border-radius: 12px;
  font-size: 15px;
  font-weight: 800;
  color: #fff;
  border: none;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: transform 0.18s, box-shadow 0.18s, filter 0.18s;
  letter-spacing: 0.02em;

  &::after {
    content: '';
    position: absolute;
    inset: 0;
    background: linear-gradient(180deg, rgba(255,255,255,0.15) 0%, transparent 100%);
    pointer-events: none;
  }

  &:hover  { transform: translateY(-2px); filter: brightness(1.08); }
  &:active { transform: translateY(0);    filter: brightness(0.96); }

  &--buy {
    background: linear-gradient(135deg, #22c55e, #16a34a);
    box-shadow: 0 6px 18px -4px rgba(22, 163, 74, 0.45);
    &:hover { box-shadow: 0 10px 24px -4px rgba(22, 163, 74, 0.55); }
  }

  &--sell {
    background: linear-gradient(135deg, #f87171, #dc2626);
    box-shadow: 0 6px 18px -4px rgba(220, 38, 38, 0.40);
    &:hover { box-shadow: 0 10px 24px -4px rgba(220, 38, 38, 0.52); }
  }
}
</style>
