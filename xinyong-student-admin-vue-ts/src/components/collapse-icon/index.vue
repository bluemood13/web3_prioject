<template>
  <el-tooltip effect="dark" content="折叠菜单" placement="top-start">
    <div
      class="cus-bg-hover-color cus-text-color h-[36px] w-[36px] rounded p-2"
      @click="change"
    >
      <i-ep-fold
        class="h-full w-full cursor-pointer"
        v-if="store.global.isColl"
      ></i-ep-fold>
      <i-ep-expand v-else class="h-full w-full cursor-pointer"></i-ep-expand>
    </div>
  </el-tooltip>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { store } from "@/utils/store";

import {
  getCurrentInstance,
  onMounted,
  ref,
  computed,
  watch,
  ComponentCustomProperties,
} from "vue";
const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

const change = () => {
  store.global.isColl = !store.global.isColl;
};
const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
