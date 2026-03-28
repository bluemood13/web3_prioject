<template>
  <div class="lf-root">
    <!-- Logo + 标题 -->
    <div class="lf-header">
      <div class="lf-logo-wrap">
        <img src="@/assets/logo.svg" class="lf-logo" alt="logo" />
      </div>
      <h1 class="lf-title">{{ title }}</h1>
      <p class="lf-sub">{{ comTitle }}</p>
    </div>

    <!-- 暗黑切换 -->
    <div class="lf-dark-toggle">
      <SwitchDark />
    </div>

    <!-- 登录 / 注册表单 -->
    <CusCard :show-title="false" class="lf-card">
      <LoginForm @toRegister="changeHan(false)" v-if="isLogin" />
      <RegisterForm @toLogin="changeHan(true)" v-else />
    </CusCard>
  </div>
</template>

<script setup lang="ts">
import {
  getCurrentInstance,
  onMounted,
  ref,
  ComponentCustomProperties,
} from "vue";
import LoginForm from "./components/login-form.vue";
import RegisterForm from "./components/register-form.vue";

const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{ (event: "toRegister"): void }>();

const title = import.meta.env.VITE_TITLE;
const comTitle = ref("欢迎回来，请登录");
const isLogin = ref(true);

const changeHan = (data: boolean) => {
  isLogin.value = data;
  comTitle.value = data ? "欢迎回来，请登录" : "创建新账户";
};

const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>

<style lang="scss" scoped>
.lf-root {
  position: relative;
}

/* 暗黑切换按钮 - 右上角 */
.lf-dark-toggle {
  position: absolute;
  top: -8px;
  right: 0;
  z-index: 1;
}

/* ─── Header ─── */
.lf-header {
  text-align: center;
  margin-bottom: 28px;
}

.lf-logo-wrap {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 64px;
  height: 64px;
  border-radius: 18px;
  background: linear-gradient(
    135deg,
    color-mix(in srgb, var(--el-color-primary) 20%, #fff),
    color-mix(in srgb, var(--el-color-primary) 10%, #fff)
  );
  box-shadow: 0 8px 24px -4px color-mix(in srgb, var(--el-color-primary) 35%, transparent);
  margin-bottom: 16px;

  :global(html.dark) & {
    background: linear-gradient(
      135deg,
      color-mix(in srgb, var(--el-color-primary) 25%, #1e293b),
      color-mix(in srgb, var(--el-color-primary) 12%, #0f172a)
    );
  }
}

.lf-logo {
  width: 36px;
  height: 36px;
  object-fit: contain;
}

.lf-title {
  font-size: 22px;
  font-weight: 800;
  letter-spacing: -0.03em;
  color: var(--el-text-color-primary);
  margin: 0 0 6px;
  background: linear-gradient(
    135deg,
    var(--el-color-primary),
    color-mix(in srgb, var(--el-color-primary) 55%, #6366f1)
  );
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.lf-sub {
  font-size: 13.5px;
  color: var(--el-text-color-secondary);
  margin: 0;
  font-weight: 400;
}

/* ─── Card ─── */
.lf-card {
  :deep(.el-card__body) {
    padding: 0;
  }
}
</style>
