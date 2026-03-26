<template>
  <div
    class="bg-color flex min-h-screen items-center justify-center overflow-hidden p-4"
  >
    <!-- 主容器卡片 -->
    <div class="w-2/3">
      <div class="card-container flex overflow-hidden rounded-2xl shadow-2xl">
        <!-- 左侧图片区域 -->
        <div
          class="cus-border-color flex w-1/2 items-center justify-center border-r-1 p-8"
        >
          <!-- Logo 容器 -->
          <div class="flex h-2/3 w-2/3 items-center justify-center">
            <img src="@/assets/logo.svg" class="h-full w-full" />
          </div>
        </div>
        <!-- 右侧表单区域 -->
        <div class="cus-bg-color w-1/2 px-16 py-8">
          <login-form></login-form>
        </div>
      </div>
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
.card-container {
  transition: all 0.3s ease;
  box-shadow:
    0 25px 50px rgba(0, 0, 0, 0.2),
    0 0 100px rgba(255, 255, 255, 0.1) inset;
  position: relative;
  overflow: hidden;

  &:hover {
    transform: translateY(-5px);
    box-shadow:
      0 35px 60px rgba(0, 0, 0, 0.3),
      0 0 120px rgba(255, 255, 255, 0.15) inset,
      0 0 0 2px rgba(255, 255, 255, 0.2),
      /* 外层白色阴影边框 */ 0 0 15px rgba(255, 255, 0, 0.5),
      /* 黄色辉光 */ 0 0 30px rgba(255, 0, 255, 0.3),
      /* 粉色辉光 */ 0 0 45px rgba(0, 255, 255, 0.2); /* 蓝色辉光 */
  }
}
</style>
