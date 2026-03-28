<template>
  <div class="login-bg"></div>
  <!-- 装饰粒子 -->
  <div class="login-orb login-orb-1"></div>
  <div class="login-orb login-orb-2"></div>
  <div class="login-orb login-orb-3"></div>

  <div class="flex h-screen w-screen items-center justify-center">
    <div class="login-panel">
      <login-form></login-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import LoginForm from "@/bus-components/login/components/login-form/index.vue";

const app = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>

<style lang="scss" scoped>
/* ─── Background ─── */
.login-bg {
  position: fixed;
  inset: 0;
  z-index: -2;
  background:
    radial-gradient(ellipse 140% 90% at 50% -10%,
      color-mix(in srgb, var(--el-color-primary) 28%, transparent),
      transparent 55%),
    radial-gradient(ellipse 70% 60% at 100% 0%,
      rgb(99 102 241 / 0.18),
      transparent 50%),
    radial-gradient(ellipse 50% 40% at 0% 100%,
      color-mix(in srgb, var(--el-color-primary) 10%, transparent),
      transparent 50%),
    linear-gradient(155deg, #f0f4ff 0%, #eef2ff 40%, #f8faff 100%);
}

:global(html.dark) .login-bg {
  background:
    radial-gradient(ellipse 120% 80% at 50% -15%,
      color-mix(in srgb, var(--el-color-primary) 22%, transparent),
      transparent 55%),
    radial-gradient(ellipse 60% 50% at 100% 0%,
      rgb(99 102 241 / 0.15),
      transparent 50%),
    linear-gradient(155deg, #080e1f 0%, #0f1a35 50%, #080e1f 100%);
}

/* ─── Floating orbs ─── */
.login-orb {
  position: fixed;
  z-index: -1;
  border-radius: 50%;
  filter: blur(60px);
  animation: orbFloat 8s ease-in-out infinite;
  pointer-events: none;
}

.login-orb-1 {
  width: 400px;
  height: 400px;
  top: -120px;
  right: 10%;
  background: color-mix(in srgb, var(--el-color-primary) 15%, transparent);
  animation-delay: 0s;
}

.login-orb-2 {
  width: 280px;
  height: 280px;
  bottom: 5%;
  left: 5%;
  background: rgb(99 102 241 / 0.12);
  animation-delay: -3s;
  animation-duration: 10s;
}

.login-orb-3 {
  width: 200px;
  height: 200px;
  top: 40%;
  right: 5%;
  background: color-mix(in srgb, var(--el-color-primary) 10%, transparent);
  animation-delay: -5s;
  animation-duration: 12s;
}

@keyframes orbFloat {
  0%, 100% { transform: translateY(0) scale(1); }
  33%       { transform: translateY(-24px) scale(1.04); }
  66%       { transform: translateY(16px) scale(0.97); }
}

/* ─── Login panel ─── */
.login-panel {
  width: 420px;
  max-width: 95vw;
  background: color-mix(in srgb, #ffffff 85%, transparent);
  backdrop-filter: blur(24px) saturate(180%);
  -webkit-backdrop-filter: blur(24px) saturate(180%);
  border: 1px solid rgba(255, 255, 255, 0.7);
  border-radius: 24px;
  box-shadow:
    0 32px 80px -12px rgb(15 23 42 / 0.18),
    0 8px 24px -4px rgb(15 23 42 / 0.10),
    inset 0 1px 0 rgba(255,255,255,0.9);
  padding: 40px 36px 36px;
  animation: panelIn 0.6s cubic-bezier(0.22, 1, 0.36, 1) both;
}

:global(html.dark) .login-panel {
  background: color-mix(in srgb, #111827 82%, transparent);
  border-color: rgba(255, 255, 255, 0.08);
  box-shadow:
    0 32px 80px -12px rgb(0 0 0 / 0.6),
    0 8px 24px -4px rgb(0 0 0 / 0.4),
    inset 0 1px 0 rgba(255,255,255,0.05);
}

@keyframes panelIn {
  from {
    opacity: 0;
    transform: translateY(32px) scale(0.96);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}
</style>
