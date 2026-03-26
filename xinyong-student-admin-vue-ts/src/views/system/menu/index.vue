<template>
  <div class="flex items-start justify-start" style="height: 720px">
    <!-- 左侧菜单列表 -->
    <CusCard class="mr-3 h-full w-1/4" title="菜单列表">
      <!-- 输入框 -->
      <el-input placeholder="请输入菜单名称" v-model="menuSearch"></el-input>
      <!-- 按钮组 -->
      <CusBtnGroup
        @add-handler="addHan"
        @refresh-handler="initData"
      ></CusBtnGroup>
      <!-- 树形节点 -->
      <CusTree
        style="height: 400px"
        ref="cusTreeRef"
        :initFunc="api.sys.menu.menuListTree"
        :currentChoose="currentChoose"
        :initProps="{
          children: 'children',
          label: 'title',
        }"
      ></CusTree>
    </CusCard>
    <!-- 右侧更新菜单 -->
    <div class="h-full flex-1">
      <!-- 基本信息 -->
      <UpdCom class="h-full" ref="updRef" @refresh="initData"></UpdCom>
    </div>

    <!-- 添加菜单 -->
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
import UpdCom from "./components/upd.vue";
const app: any = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();
const action = import.meta.env.VITE_API;
const tableRef = ref();
const addRef = ref();
const updRef = ref();
const cusTreeRef = ref();

// 搜索的菜单
const menuSearch = ref();

// 检测菜单搜索
watch(menuSearch, (nv, ov) => {
  cusTreeRef.value.treeRef?.filter(nv);
});

/**
 * 当前节点的选择事件
 * @param data
 */
const currentChoose = async (data: any) => {
  // 将当前选择的节点赋值给详情组件
  await updRef.value.setForm(data);
};
// 添加事件
const addHan = async () => {
  addRef.value.vis = true;
  await addRef.value.setForm(cusTreeRef.value.getCurrentNode());
};

// 初始化事件
const initData = async (currentId: any = undefined) => {
  console.log(currentId);
  //获取数据设置默认节点
  await cusTreeRef.value.initData();
  const nodeData = cusTreeRef.value.treeRef.data;
  console.log(nodeData);
  // 设置树形组件的当前节点
  if (nodeData && nodeData.length > 0) {
    if (currentId) {
      cusTreeRef.value.treeRef.setCurrentKey(currentId);
      await currentChoose(cusTreeRef.value.treeRef.getNode(currentId).data);
    } else {
      cusTreeRef.value.treeRef.setCurrentKey(nodeData[0].id);
      await currentChoose(nodeData[0]);
    }
  }
};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
