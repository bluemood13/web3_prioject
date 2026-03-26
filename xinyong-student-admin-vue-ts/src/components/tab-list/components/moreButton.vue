<template>
  <div class="flex items-center justify-start">
    <el-tooltip effect="dark" content="刷新页面" placement="bottom">
      <div
        class="cus-bg-hover-color cus-text-color mr-2 h-full cursor-pointer rounded p-2"
        @click="refreshHan"
      >
        <i-ep-refresh></i-ep-refresh>
      </div>
    </el-tooltip>
    <el-dropdown trigger="click" :teleported="false">
      <div>
        <el-tooltip effect="dark" content="更多操作" placement="bottom">
          <div
            class="cus-bg-hover-color cus-text-color h-full cursor-pointer rounded p-2"
          >
            <i-ep-more-filled></i-ep-more-filled>
          </div>
        </el-tooltip>
      </div>
      <template #dropdown>
        <el-dropdown-menu>
          <!-- 刷新 -->
          <el-dropdown-item @click="refreshHan"> 刷新 </el-dropdown-item>
          <!-- 关闭当前 -->
          <el-dropdown-item
            divided
            @click="store.tab.removeTab(app.$route.fullPath, true)"
          >
            关闭当前
          </el-dropdown-item>
          <!-- 关闭左侧 -->
          <el-dropdown-item
            @click="store.tab.closeTabsOnSide(app.$route.fullPath, 'left')"
          >
            关闭左侧
          </el-dropdown-item>
          <!-- 关闭右侧 -->
          <el-dropdown-item
            @click="store.tab.closeTabsOnSide(app.$route.fullPath, 'right')"
          >
            关闭右侧
          </el-dropdown-item>
          <!-- 关闭其他 -->
          <el-dropdown-item
            divided
            @click="store.tab.closeOtherTab(app.$route.fullPath)"
          >
            关闭其他
          </el-dropdown-item>
          <!-- 关闭所有 -->
          <el-dropdown-item @click="store.tab.closeAllTab">
            关闭所有
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
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
import { hooks } from "@/hooks";
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
<style lang="scss" scoped>
.more-button {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 43px;
  &:hover {
    background-color: var(--el-color-info-light-9);
  }
}
</style>
