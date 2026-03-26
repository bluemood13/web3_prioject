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
      :init="api.xinyong.jiaoyi.getJiaoyiListPage"
      v-if="tableShow"
      :init-params="{
        userId: userId,
      }"
    >
      <el-table-column prop="hash" label="区块hash"></el-table-column>
      <el-table-column prop="tx" label="交易hash"></el-table-column>
      <el-table-column prop="userId" label="关联用户">
        <template #default="scope">
          <el-tag>{{ getRemoteSelData("userId", scope.row.userId) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="jiaoyiBlockBase.num"
        label="交易数量【区块】"
      ></el-table-column>
      <el-table-column prop="jiaoyiBlockBase.types" label="交易类型【区块】">
        <template #default="scope">
          {{ getStaticSelData("types", scope.row.jiaoyiBlockBase.types) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="jiaoyiBlockBase.price"
        label="交易单价【区块】"
      ></el-table-column>
      <el-table-column prop="createTime" label="创建时间" sortable />
      <el-table-column label="操作" :fixed="false" width="200">
        <template #default="scope">
          <div class="flex flex-wrap items-center justify-around">
            <div class="flex-1 p-1">
              <CusBtn type="primary" size="small" @click="linkHan(scope.row)"
                >查看区块链</CusBtn
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
    <!-- 区块链 -->
    <CusLink ref="cusLinkRef"></CusLink>
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
const app: any = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
import { useJiaoyiLinkHook } from "@/hooks/useJiaoyiLinkHook";
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();
const tableRef = ref();
const searchRef = ref();
const userId = ref(-1);
const tableShow = ref(false);
const addRef = ref();
const updRef = ref();

const {
  userIdDataList,
  getRemoteSelData,
  getStaticSelData,
  linkHan,
  cusLinkRef,
} = useJiaoyiLinkHook();

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
  const res: Res = await api.xinyong.jiaoyi.delJiaoyi({ id: row.id });
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
