<template>
  <div class="flex justify-start items-center my-3">
    <el-pagination
      background
      layout="prev, pager, next"
      :page-size="pageDto.pageNum"
      :current-page="pageDto.pageSize"
      :page-count="Math.ceil(pageDto.total / pageDto.pageNum)"
      @size-change="sizeChangeHandler"
      @current-change="currnetChangeHandler" />
    <div class="text-sm px-4">
      <span>总数:</span>
      {{ pageDto.total }}
    </div>
  </div>
</template>

<script setup lang="ts">
import { tt } from '@/utils/tool';

import { getCurrentInstance, onMounted, ref } from 'vue';
import { ConfigData } from '@/config';

interface PageDto {
  pageNum: number;
  pageSize: number;
  total: number;
}

// 获取应用实例上下文
const app = getCurrentInstance()?.appContext.config.globalProperties;

// 定义组件的 props
const props = defineProps<{}>();

// 定义分页数据的结构
const pageDto = ref<PageDto>({
  ...ConfigData.PAGE_CONST,
});

// 定义 emits 事件
const emits = defineEmits<{
  (event: 'refresh', search: Record<string, any>): void;
}>();

// 搜索条件
const search = ref<Record<string, any>>({});

// 当前页码变化的处理函数
const currnetChangeHandler = (data: number) => {
  pageDto.value.pageSize = data;
  emits('refresh', search.value);
};
// 页面大小变化的处理函数（留空函数）
const sizeChangeHandler = (data: number) => {
  // 这里可以根据需要处理页面大小变化的逻辑
};

// 重置分页
const resetPage = () => {
  pageDto.value = { ...ConfigData.PAGE_CONST };
};

// 暴露 pageDto 和 resetPage 给父组件
defineExpose({ pageDto, resetPage, search });

// 初始化数据
const initData = async () => {
  // 初始化逻辑
};

// 组件挂载时执行初始化
onMounted(() => {
  initData();
});
</script>

<style lang="scss" scoped></style>
