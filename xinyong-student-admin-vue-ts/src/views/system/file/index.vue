<template>
  <div>
    <!-- 搜索组件 -->
    <SearchCom ref="searchRef" @search="initData"></SearchCom>
    <!-- 按钮组组件 -->
    <CusBtnGroup
      @add-handler="addHan"
      @refresh-handler="initData"
    ></CusBtnGroup>
    <!-- 表格组件 -->
    <CusTable ref="tableRef" :init="api.sys.file.getFileListPage">
      <el-table-column label="文件预览">
        <template #default="scope">
          <el-image
            v-if="tt.common.isImg(scope.row.fileName)"
            style="width: 100px; height: 100px"
            :src="scope.row.fileUrl"
            fit="cover"
          />
          <el-link
            v-else
            type="primary"
            :href="scope.row.fileUrl"
            :underline="false"
          >
            点击下载
          </el-link>
        </template>
      </el-table-column>

      <el-table-column prop="fileName" label="文件名" />
      <el-table-column prop="fileOriginName" label="原始文件名" />
      <el-table-column prop="fileSize" label="文件大小字节" />
      <el-table-column prop="filePath" label="文件目录" />
      <el-table-column prop="createTime" label="创建时间" sortable />
      <el-table-column label="操作" :fixed="false" width="200">
        <template #default="scope">
          <div class="flex flex-wrap items-center justify-around">
            <div class="flex-1 p-1">
              <CusBtn type="danger" size="small" @click="delHan(scope.row)"
                >删除</CusBtn
              >
            </div>
          </div>
        </template>
      </el-table-column>
    </CusTable>
    <!-- 添加组件 -->
    <AddCom ref="addRef" @refresh="initData"></AddCom>
  </div>
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
  nextTick,
  ComponentCustomProperties,
} from "vue";
import AddCom from "./components/add.vue";
import SearchCom from "./components/search.vue";
const app: any = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

const tableRef = ref();
const searchRef = ref();
const addRef = ref();
const action = import.meta.env.VITE_API;

// 添加事件
const addHan = async () => {
  addRef.value.vis = true;
};
// 删除事件
const delHan = async (row: any) => {
  const res: Res = await api.sys.file.delFile({ id: row.id });
  tt.msg.success(res.message);
  await initData();
};
// 初始化事件
const initData = async (searchParams: any = {}) => {
  await tableRef.value.initData(searchParams);
};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
