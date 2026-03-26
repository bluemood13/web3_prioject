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
    <CusTable ref="tableRef" :init="api.sys.user.getUserListPage">
      <el-table-column prop="username" label="账号" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="sex" label="性别">
        <template #default="scope">
          <el-tag>{{ scope.row.sex == "1" ? "男" : "女" }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="content" label="介绍" />
      <el-table-column prop="money" label="金币" />
      <el-table-column label="角色列表">
        <template #default="scope">
          <div
            class="p-1"
            v-for="(item, index) in scope.row?.roles"
            :key="index"
          >
            <el-tag>{{ item?.roleName }}</el-tag>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="操作" :fixed="false" width="200">
        <template #default="scope">
          <div class="flex flex-wrap items-center justify-around">
            <div class="flex-1 p-1">
              <CusBtn type="primary" size="small" @click="updHan(scope.row)"
                >更新</CusBtn
              >
            </div>
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
    <!-- 更新组件 -->
    <UpdCom ref="updRef" @refresh="initData"></UpdCom>
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
import UpdCom from "./components/upd.vue";
import SearchCom from "./components/search.vue";
const app: any = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

const tableRef = ref();
const searchRef = ref();
const addRef = ref();
const updRef = ref();

// 添加事件
const addHan = async () => {
  addRef.value.vis = true;
};
// 更新事件
const updHan = async (row: any) => {
  updRef.value.vis = true;
  updRef.value.setForm(row);
};
// 删除事件
const delHan = async (row: any) => {
  const res: Res = await api.sys.user.delUser({ id: row.id });
  tt.msg.success(res.message);
  await initData();
};
// 初始化事件
const initData = async (searchParams: any = {}) => {
  tableRef.value.initData(searchParams);
};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
