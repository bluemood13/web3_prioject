<template>
  <div class="flex justify-between items-center my-5 px-1">
    <div class="flex-1">
      <template v-for="(item, index) in props.left" :key="index">
        <template v-if="item.type == null">
          <el-button
            v-if="item.name == 'add'"
            @click="addHandler"
            type="primary"
            >新增</el-button
          >
        </template>
        <template v-else>
          <el-button
            v-if="item.type == 'add'"
            @click="addHandler"
            type="primary"
            >{{ item.name }}</el-button
          >
          <el-button
            v-if="item.type == 'delBatch'"
            @click="delBatchHandler"
            type="danger"
            :disabled="delBatchDis"
            >{{ item.name }}</el-button
          >
        </template>
      </template>
      <slot name="left"></slot>
    </div>
    <div>
      <slot name="right"></slot>
      <template v-for="(item, index) in props.right" :key="index"> </template>
      <el-button @click="refreshHandler">刷新</el-button>
    </div>
  </div>
</template>
<script setup lang="ts">
import { tt } from '@/utils/tool';

const app: any = getCurrentInstance()?.appContext.config.globalProperties;

interface ButtonItem {
  // 按钮类型
  type: 'add' | 'delBatch' | string;
  // 按钮名称
  name: string;
}

interface CusBtnGroupProps {
  // 左边布局
  left?: ButtonItem[];
  // 右侧布局
  right?: ButtonItem[];
}

// 定义 emit 事件类型
interface EmitEvents {
  // 新增按钮的点击事件
  (event: 'addHandler'): void;
  // 批量删除按钮的点击事件
  (event: 'delBatchHandler'): void;
  // 刷新按钮的点击事件
  (event: 'refreshHandler', payload?: any, isPreventDefault?: boolean): void;
}

const props = withDefaults(defineProps<CusBtnGroupProps>(), {
  left: () => {
    // 左边默认新增按钮
    return [{ type: 'add', name: '新增' }];
  },
  right: () => {
    return [];
  },
});

const emits = defineEmits<EmitEvents>();

const addHandler = () => {
  emits('addHandler');
};
const delBatchDis = ref(true);
const delBatchHandler = () => {
  emits('delBatchHandler');
};

const refreshHandler = () => {
  emits('refreshHandler', {}, true);
};
const initData = async () => {};
defineExpose({});
onMounted(() => {
  initData();
});
</script>
<style lang="scss" scoped></style>
