<template>
  <div>
    <!-- 登录组件 -->
    <component :is="loginComponent"></component>
    <!-- 浮动的设置按钮 -->
    <FloatConfig></FloatConfig>
  </div>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { store } from "@/utils/store";
import { useLoginTemplateHook } from "@/hooks/useLoginTemplateHook";
import FloatConfig from "@/components/float-config/index.vue";

const app = getCurrentInstance()?.appContext.config.globalProperties;
const props = defineProps({});
const emits = defineEmits([]);

const loginTemplateData = useLoginTemplateHook();

const loginComponent = computed(() => {
  return loginTemplateData[store.global.loginTemplate];
});

const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
