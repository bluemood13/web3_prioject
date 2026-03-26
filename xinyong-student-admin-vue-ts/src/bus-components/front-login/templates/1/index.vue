<template>
  <el-dialog v-model="vis" title="" width="65%" :append-to-body="true">
    <div class="flex h-full w-full items-center justify-center p-10">
      <div class="cus-border-color w-1/2 border-r-[1px]">
        <img src="@/assets/logo.svg" class="h-full w-full p-10" />
      </div>
      <div class="w-1/2 px-10">
        <LoginForm @close-dialog="closeDialogEmit"></LoginForm>
      </div>
    </div>
  </el-dialog>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import {
  getCurrentInstance,
  onMounted,
  ref,
  computed,
  watch,
  ComponentCustomProperties,
} from "vue";
import LoginForm from "@/bus-components/front-login/components/login-form/index.vue";

const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {
  //   demo?: string;
}
const props = withDefaults(defineProps<Props | {}>(), {
  //   demo: '',
});
const emits = defineEmits<{
  //   (event: 'refresh'): void;
}>();

const closeDialogEmit = () => {
  vis.value = false;
};
const vis = ref(false);
const setForm = async (data: any) => {};

const initData = async () => {};
defineExpose({ vis, setForm });
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
