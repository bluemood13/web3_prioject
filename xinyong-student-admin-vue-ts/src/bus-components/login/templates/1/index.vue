<template>
  <div class="back-images"></div>
  <div class="flex h-screen w-screen items-center justify-center">
    <div class="w-1/3">
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
.back-images {
  background-image: url(@/assets/logo.svg);
  background-size: 100% 100%;
  height: 80vh; /* 设置高度为 80% 的视口高度 */
  width: 80vw; /* 设置宽度为 80% 的视口宽度 */
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%); /* 使元素水平和垂直居中 */
  z-index: -1;
  opacity: 0.5;
}
</style>
