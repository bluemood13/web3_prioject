<template>
  <CusCard :show-title="false">
    <el-table
      :data="tableData"
      ref="tableRef"
      stripe
      style="width: 100%"
      max-height="580"
      :border="true"
      default-expand-all
      row-key="id"
      @selection-change="selectionChangeHandler"
      :tree-props="treeProps"
      v-loading="tableLoading"
      element-loading-text="加载数据中">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="id" label="编号" width="80" />
      <slot></slot>
    </el-table>
  </CusCard>
  <CusPage ref="CusPageRef" @refresh="initData"></CusPage>
</template>
<script setup lang="ts">
import { tt } from '@/utils/tool';
import { api } from '@/utils/api';

import { getCurrentInstance, onMounted } from 'vue';
const app = getCurrentInstance()?.appContext.config.globalProperties;
const props = defineProps({
  init: {
    type: Function,
    default: () => {
      return async () => {};
    },
  },
  initParams: {
    type: Object,
    default: () => {
      return {};
    },
  },
});
const emits = defineEmits(['selectionChangeHandler']);

const tableData = ref([]);
const tableRef = ref();
const treeProps = ref({
  hasChildren: 'hasChildren',
  children: 'children',
});
const CusPageRef = ref();
const tableLoading = ref();
const selectionChangeHandler = (data: any) => {
  emits('selectionChangeHandler', data);
};
const initData = async (searchData = {}, resetPage = false) => {
  if (Object.keys(searchData).length === 0) {
    searchData = props.initParams;
  }
  CusPageRef.value.search = searchData;
  tableLoading.value = true;
  if (resetPage) {
    CusPageRef.value.resetPage();
  }
  try {
    const result = await props.init({
      ...searchData,
      ...CusPageRef.value.pageDto,
    });
    if (result?.data != null) {
      CusPageRef.value.pageDto.pageSize = result.data.page;
      CusPageRef.value.pageDto.pageNum = result.data.size;
      CusPageRef.value.pageDto.total = result.data.total;
      tableData.value = result.data.list;
    }
  } catch (e) {
    console.log(e);
  }
  tableLoading.value = false;
};
defineExpose({ tableData, initData, tableLoading });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss">
.cus-table-option {
  div {
    padding: 5px;
  }
}
</style>
