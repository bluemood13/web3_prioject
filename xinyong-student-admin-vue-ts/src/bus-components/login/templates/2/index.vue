<template>
  <div class="flex min-h-screen">
    <!-- 左侧图片区域 -->
    <div class="relative flex w-1/2">
      <!-- 背景渐变，暗黑模式兼容 -->
      <div
        class="absolute inset-0 bg-gradient-to-br from-blue-600 via-purple-600 to-indigo-800 transition-colors duration-500 dark:from-gray-900 dark:via-gray-800 dark:to-gray-900"
      ></div>
      <!-- 浮动装饰元素，暗黑模式兼容 -->
      <div
        class="absolute top-10 left-10 h-20 w-20 animate-pulse rounded-full bg-white/10 dark:bg-white/5"
      ></div>
      <div
        class="absolute top-32 right-20 h-16 w-16 animate-bounce rounded-full bg-white/5 delay-1000 dark:bg-white/10"
      ></div>
      <div
        class="absolute bottom-20 left-20 h-24 w-24 animate-pulse rounded-full bg-white/5 delay-500 dark:bg-white/10"
      ></div>
      <div
        class="absolute top-1/2 left-1/4 h-12 w-12 animate-ping rounded-full bg-white/5 delay-300 dark:bg-white/10"
      ></div>

      <!-- 内容区域 -->
      <div
        class="relative z-10 flex w-full flex-col items-center justify-center p-12 text-white dark:text-gray-200"
      >
        <div class="flex h-full w-full items-center justify-center">
          <!-- Logo -->
          <div
            class="transform transition-transform duration-300 hover:scale-105"
          >
            <div
              class="logo-container group relative mx-auto flex h-90 w-90 items-center justify-center rounded-full bg-white/5 dark:bg-gray-900/40"
            >
              <!-- Logo图片 -->
              <img
                src="@/assets/logo.svg"
                class="logo-image h-64 w-64 transform transition-all duration-500 group-hover:scale-110 group-hover:rotate-12"
              />
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 右侧表单区域 -->
    <div
      class="bg-color flex w-1/2 items-center justify-center overflow-hidden"
    >
      <LoginForm class="w-2/3"></LoginForm>
    </div>
  </div>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import LoginForm from "@/bus-components/login/components/login-form/index.vue";

const app: any = getCurrentInstance()?.appContext.config.globalProperties;

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
/* 背景渐变动画 */
.bg-gradient-to-br {
  background-size: 400% 400%;
  animation: gradient-shift 15s ease infinite;
}

/* Logo容器特效 */
.logo-container {
  position: relative;
  overflow: visible;
  transition: all 0.5s cubic-bezier(0.4, 0, 0.2, 1);
  /* 为更大的Logo增加更明显的呼吸光效 */
  box-shadow:
    0 0 60px rgba(59, 130, 246, 0.2),
    inset 0 0 60px rgba(255, 255, 255, 0.1);
  animation: breathing-light 4s ease-in-out infinite;
}

.logo-container:hover {
  transform: translateY(-10px) scale(1.05);
  box-shadow:
    0 40px 80px rgba(255, 255, 255, 0.2),
    0 0 60px rgba(59, 130, 246, 0.5),
    inset 0 0 80px rgba(255, 255, 255, 0.25);
}

/* Logo图片特效 */
.logo-image {
  filter: drop-shadow(0 12px 24px rgba(0, 0, 0, 0.25));
  transition: all 0.5s cubic-bezier(0.4, 0, 0.2, 1);
}

.group:hover .logo-image {
  filter: drop-shadow(0 16px 32px rgba(0, 0, 0, 0.4))
    drop-shadow(0 0 40px rgba(255, 255, 255, 0.7));
}
</style>
