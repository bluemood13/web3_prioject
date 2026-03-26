<template>
  <el-header
    class="cus-border-color flex w-full items-center justify-between border-b-[1px]"
    :height="headerHeight + 'px'"
  >
    <div class="flex h-full w-full flex-1 items-center justify-start">
      <!-- 菜单收缩 -->
      <CollapseIcon></CollapseIcon>
    </div>
    <!-- 右侧菜单 -->
    <ToolBarCom class="right-0 h-full"></ToolBarCom>
  </el-header>
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
import CollapseIcon from "@/components/collapse-icon/index.vue";
import ToolBarCom from "@/components/tool-bar/index.vue";

const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

const headerHeight = computed(() => {
  return store.global.headerHeight;
});

const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
