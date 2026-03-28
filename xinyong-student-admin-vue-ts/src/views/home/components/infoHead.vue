<template>
  <div class="info-head-grid">
    <!-- 钱包地址 -->
    <div class="stat-card stat-card--blue">
      <div class="stat-icon-wrap">
        <el-icon class="stat-icon"><Wallet /></el-icon>
      </div>
      <div class="stat-body">
        <div class="stat-label">钱包地址</div>
        <div class="stat-value stat-value--address" :title="blockInfo?.account">
          {{ blockInfo?.account || 'Loading...' }}
        </div>
      </div>
    </div>

    <!-- 区块数量 -->
    <div class="stat-card stat-card--green">
      <div class="stat-icon-wrap">
        <el-icon class="stat-icon"><Box /></el-icon>
      </div>
      <div class="stat-body">
        <div class="stat-label">区块数量</div>
        <div class="stat-value">{{ blockInfo?.blockNum || 0 }}</div>
      </div>
    </div>

    <!-- Gas 价格 -->
    <div class="stat-card stat-card--orange">
      <div class="stat-icon-wrap">
        <el-icon class="stat-icon"><Money /></el-icon>
      </div>
      <div class="stat-body">
        <div class="stat-label">Gas 价格</div>
        <div class="stat-value">{{ blockInfo?.gasPrice || 0 }}</div>
      </div>
    </div>

    <!-- Gas 上限 -->
    <div class="stat-card stat-card--purple">
      <div class="stat-icon-wrap">
        <el-icon class="stat-icon"><Odometer /></el-icon>
      </div>
      <div class="stat-body">
        <div class="stat-label">Gas 上限</div>
        <div class="stat-value">{{ blockInfo?.gasLimit || 0 }}</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { getCurrentInstance, onMounted, ref } from 'vue';
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();
const blockInfo = ref<any>({});
const initData = async () => {};
defineExpose({ blockInfo });
onMounted(async () => { await initData(); });
</script>

<style lang="scss" scoped>
/* ─── Grid ─── */
.info-head-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
  width: 100%;
  margin-bottom: 20px;

  @media (max-width: 1024px) {
    grid-template-columns: repeat(2, 1fr);
  }
  @media (max-width: 640px) {
    grid-template-columns: 1fr;
  }
}

/* ─── Card base ─── */
.stat-card {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px 20px;
  border-radius: 16px;
  border: 1px solid transparent;
  transition: transform 0.22s, box-shadow 0.22s;
  position: relative;
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    inset: 0;
    border-radius: inherit;
    opacity: 0;
    transition: opacity 0.22s;
    background: radial-gradient(ellipse 80% 60% at 80% 20%, rgba(255,255,255,0.22), transparent 65%);
    pointer-events: none;
  }

  &:hover {
    transform: translateY(-3px);
    box-shadow: 0 12px 32px -6px rgba(0,0,0,0.14);
    &::before { opacity: 1; }
  }
}

/* colour variants */
.stat-card--blue {
  background: linear-gradient(135deg, #dbeafe 0%, #eff6ff 100%);
  border-color: #bfdbfe;
  .stat-icon { color: #2563eb; }
  .stat-icon-wrap { background: rgba(37,99,235,0.12); }
  :global(html.dark) & {
    background: linear-gradient(135deg, rgba(37,99,235,0.18) 0%, rgba(37,99,235,0.08) 100%);
    border-color: rgba(37,99,235,0.25);
  }
}
.stat-card--green {
  background: linear-gradient(135deg, #dcfce7 0%, #f0fdf4 100%);
  border-color: #bbf7d0;
  .stat-icon { color: #16a34a; }
  .stat-icon-wrap { background: rgba(22,163,74,0.12); }
  :global(html.dark) & {
    background: linear-gradient(135deg, rgba(22,163,74,0.18) 0%, rgba(22,163,74,0.08) 100%);
    border-color: rgba(22,163,74,0.25);
  }
}
.stat-card--orange {
  background: linear-gradient(135deg, #ffedd5 0%, #fff7ed 100%);
  border-color: #fed7aa;
  .stat-icon { color: #ea580c; }
  .stat-icon-wrap { background: rgba(234,88,12,0.12); }
  :global(html.dark) & {
    background: linear-gradient(135deg, rgba(234,88,12,0.18) 0%, rgba(234,88,12,0.08) 100%);
    border-color: rgba(234,88,12,0.25);
  }
}
.stat-card--purple {
  background: linear-gradient(135deg, #ede9fe 0%, #faf5ff 100%);
  border-color: #ddd6fe;
  .stat-icon { color: #7c3aed; }
  .stat-icon-wrap { background: rgba(124,58,237,0.12); }
  :global(html.dark) & {
    background: linear-gradient(135deg, rgba(124,58,237,0.18) 0%, rgba(124,58,237,0.08) 100%);
    border-color: rgba(124,58,237,0.25);
  }
}

/* ─── Icon wrapper ─── */
.stat-icon-wrap {
  flex-shrink: 0;
  width: 52px;
  height: 52px;
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.stat-icon {
  font-size: 26px !important;
}

/* ─── Body ─── */
.stat-body {
  flex: 1;
  min-width: 0;
}

.stat-label {
  font-size: 12px;
  font-weight: 600;
  letter-spacing: 0.06em;
  text-transform: uppercase;
  color: var(--el-text-color-secondary);
  margin-bottom: 6px;
}

.stat-value {
  font-size: 20px;
  font-weight: 800;
  color: var(--el-text-color-primary);
  line-height: 1.2;
  letter-spacing: -0.02em;

  :global(html.dark) & {
    color: #f1f5f9;
  }
}

.stat-value--address {
  font-size: 13px;
  font-weight: 600;
  word-break: break-all;
  line-height: 1.4;
  font-family: 'Fira Code', 'JetBrains Mono', monospace;
  letter-spacing: 0;
}
</style>
