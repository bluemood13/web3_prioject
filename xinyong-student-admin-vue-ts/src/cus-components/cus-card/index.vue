<template>
  <el-card style="border-radius: 10px">
    <template #header v-if="props.showTitle">
      <span class="font-bold tracking-wider">{{ props.title }}</span>
    </template>
    <slot></slot>
  </el-card>
</template>
<script setup lang="ts">
import { tt } from '@/utils/tool';

// 定义实例全局属性的类型
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
// 定义 props 的类型
interface CardProps {
  // 标题
  title?: string;
  //   是否展示标题
  showTitle?: boolean;
}

// 使用 defineProps 来定义 props，withDefaults 用于定义默认值
const props = withDefaults(defineProps<CardProps>(), {
  title: '',
  showTitle: true,
});

// 没有 emit 事件，可以保留空数组
const emits = defineEmits<{}>();

const initData = async () => {
  // 初始化数据的逻辑
};

defineExpose({});

onMounted(async () => {
  await initData();
});
</script>

<style lang="scss" scoped></style>
