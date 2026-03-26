<template>
  <el-switch
    v-model="store.global.isDark"
    inline-prompt
    :active-icon="Sunny"
    :inactive-icon="Moon"
    @change="switchDark"
  />
</template>
<script setup lang="ts">
import { getCurrentInstance, ref, watch } from "vue";
import { store } from "@/utils/store";
import { Sunny, Moon } from "@element-plus/icons-vue";

const app: any = getCurrentInstance()?.appContext.config.globalProperties;

// 切换暗黑模式
const switchDark = () => {
  const html = document.documentElement as HTMLElement;
  // 先移除所有主题类，触发重绘
  html.classList.remove("dark");
  html.removeAttribute("hap-theme");

  // 延迟10ms重新应用主题
  setTimeout(() => {
    darkElementPlus();
    darkSass();
  }, 10);
};

// 修改element-plus的暗黑模式（与Tailwind共用dark类）
const darkElementPlus = () => {
  const html = document.documentElement as HTMLElement;
  store.global.isDark
    ? html.classList.add("dark")
    : html.classList.remove("dark");
};

// 修改sass自定义的暗黑模式
const darkSass = () => {
  const html = document.documentElement as HTMLElement;
  if (store.global.isDark) {
    html.setAttribute("hap-theme", "dark");
  } else {
    html.setAttribute("hap-theme", store.global.theme || "light");
  }
};

// 监听isDark变化，初始化和切换时都触发
watch(
  () => store.global.isDark,
  () => {
    switchDark();
  },
  { immediate: true },
);
</script>
<style lang="scss" scoped></style>
