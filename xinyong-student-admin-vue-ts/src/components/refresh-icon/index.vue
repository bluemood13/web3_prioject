<template>
  <el-tooltip effect="dark" content="点击刷新" placement="top-start">
    <div
      class="flex h-[28px] w-[28px] items-center justify-center rounded p-1 hover:bg-black/10 hover:shadow-md dark:hover:bg-white/10"
    >
      <i-ep-refresh
        @click="refreshHan"
        class="h-full w-full cursor-pointer"
      ></i-ep-refresh>
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

/**
 * 刷新页面
 */
const refreshHan = () => {
  setTimeout(() => {
    // 清除keep的缓存，防止刷新到缓存
    app.$route.meta.isCache && store.keep.removeItem(app.$route.fullPath);
    // 页面重新加载
    store.global.isRefresh = false;
    nextTick(() => {
      app.$route.meta.isCache && store.keep.addItem(app.$route.fullPath);
      store.global.isRefresh = true;
    });
  }, 0);
};

const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
