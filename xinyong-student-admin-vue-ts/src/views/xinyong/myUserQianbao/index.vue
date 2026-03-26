<template>
  <div>
    <!-- 搜索组件 -->
    <SearchCom ref="searchRef" @search="initData"></SearchCom>
    <!-- 按钮组组件 -->
    <CusBtnGroup
      :left="[]"
      @add-handler="addHan"
      @refresh-handler="initData"
    ></CusBtnGroup>
    <!-- 表格组件 -->
    <CusTable
      ref="tableRef"
      :init="api.xinyong.qianbao.getQianbaoListPage"
      v-if="tableShow"
      :init-params="{
        userId: userId,
      }"
    >
      <el-table-column prop="userId" label="关联用户">
        <template #default="scope">
          <el-tag>{{ getRemoteSelData("userId", scope.row.userId) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="coin" label="代币数"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" sortable />
      <el-table-column label="操作" :fixed="false" width="200">
        <template #default="scope">
          <div class="flex flex-wrap items-center justify-around">
            <div class="flex-1 p-1">
              <CusBtn
                type="primary"
                size="small"
                @click="analyzeCredit(scope.row)"
                >分析信用</CusBtn
              >
            </div>
            <!-- <div class="flex-1 p-1">
              <CusBtn type="primary" size="small" @click="updHan(scope.row)"
                >更新</CusBtn
              >
            </div>
            <div class="flex-1 p-1">
              <CusBtn
                type="danger"
                :showTip="true"
                size="small"
                @click="delHan(scope.row)"
                >删除</CusBtn
              >
            </div> -->
          </div>
        </template>
      </el-table-column>
    </CusTable>
    <!-- 添加组件 -->
    <AddCom ref="addRef" @refresh="initData"></AddCom>
    <!-- 更新组件 -->
    <UpdCom ref="updRef" @refresh="initData"></UpdCom>

    <!-- 信用分析组件 -->
    <CreditAnalysisDialog ref="creditAnalysisRef" />
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
  nextTick,
  ComponentCustomProperties,
} from "vue";
import AddCom from "./components/add.vue";
import UpdCom from "./components/upd.vue";
import SearchCom from "./components/search.vue";
import CreditAnalysisDialog from "../components/CreditAnalysisDialog.vue";

const app: any = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
import { useQianbaoLinkHook } from "@/hooks/useQianbaoLinkHook";
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();
const tableRef = ref();
const searchRef = ref();
const userId = ref(-1);
const tableShow = ref(false);
const addRef = ref();
const updRef = ref();
const creditAnalysisRef = ref();

const { userIdDataList, getRemoteSelData, getStaticSelData } =
  useQianbaoLinkHook();

// 信用分析函数
const analyzeCredit = (row: any) => {
  creditAnalysisRef.value.open(row);
};

// 添加事件
const addHan = async () => {
  addRef.value.vis = true;
  await addRef.value.setForm();
};
// 更新事件
const updHan = async (row: any) => {
  updRef.value.vis = true;
  await updRef.value.setForm(row);
};
// 删除事件
const delHan = async (row: any) => {
  const res: Res = await api.xinyong.qianbao.delQianbao({ id: row.id });
  tt.msg.success(res.message);
  await initData();
};

const mySelfInit = async () => {
  userId.value = store.user.userInfo.id;
  tableShow.value = true;
  await nextTick();
};
// 初始化事件
const initData = async (searchParams: any = {}) => {
  await mySelfInit();
  await tableRef.value.initData(searchParams);
  const resUserId = await api.sys.user.getUserListAll({});
  userIdDataList.value = resUserId.data;
};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
