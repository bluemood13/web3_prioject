<template>
  <el-tree
    class="tree-scoll"
    v-loading="loading"
    ref="treeRef"
    :data="treeData"
    :props="props.initProps"
    node-key="id"
    default-expand-all
    highlight-current
    @check="checkChangeHan"
    :expand-on-click-node="false"
    :current-node-key="props.currentKey"
    @node-click="(data, node) => props.currentChoose(data, node)"
    :filter-node-method="filterNode" />
</template>
<script setup lang="ts">
import { tt } from '@/utils/tool';
import { api } from '@/utils/api';

import {
  getCurrentInstance,
  onMounted,
  ref,
  computed,
  watch,
  ComponentCustomProperties,
} from 'vue';
const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {
  modelValue?: Array<any>;
  // 初始化的方法
  initFunc: Function;
  //   当前选择的事件
  currentChoose: Function;
  // 字段映射
  initProps: {
    label: string;
    children: string;
  };
  currentKey?: string | number;
}

const props = withDefaults(defineProps<Props>(), {
  modelValue: () => {
    return [];
  },
  initFunc: () => {
    return async () => {};
  },
  currentChoose: () => {
    return async () => {};
  },
  initProps: () => {
    return {
      children: 'children',
      label: 'name',
    };
  },
  currentKey: '1',
});
const emits = defineEmits<{
  (event: 'update:modelValue', menuList: string): void;
}>();

const loading = ref(false);
const treeRef = ref();
const treeData = ref([]);
console.log(props.modelValue);
// 将节点赋值给对应的数据
watch(
  () => {
    return props.modelValue;
  },
  (n, o) => {
    // await nextTick();
    treeRef.value.setCheckedKeys(props.modelValue);
  }
);

const initData = async () => {
  loading.value = true;
  const res: Res = await props.initFunc();
  treeData.value = res.data;
  loading.value = false;
};

/**
 * 复选框点击事件
 * @param data
 * @param flag
 * @param hasNode
 */
const checkChangeHan = (data: any, allData: any) => {
  emits('update:modelValue', allData['checkedKeys']);
};
/**
 * 过滤节点
 * @param value
 * @param treeData
 */
const filterNode = (value: any, treeData: any) => {
  if (!value) return true;
  return treeData[props.initProps.label]
    .toLowerCase()
    .includes(value.toLowerCase());
};

/**
 * 获取当前选中的节点
 */
const getCurrentNode = () => {
  return treeRef.value.getCurrentNode();
};

/**
 * 根据key值获取当前的节点
 * @param key
 */
const getNode = (key: string) => {
  return treeRef.value.getNode(key);
};

defineExpose({ initData, treeRef, getCurrentNode, getNode });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped>
.tree-scoll {
  height: 100%;
  width: 100%;
  display: block;
  overflow-y: scroll;
  padding-bottom: 10%;
}
</style>
