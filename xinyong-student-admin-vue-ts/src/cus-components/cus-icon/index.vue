<template>
  <component
    :is="props.icon"
    :style="
      'width:' + props.size + ' px;height:' + props.size + 'px'
    "></component>
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

// 定义 props 的类型
interface IconProps {
  // 图标字符串
  icon?: string;
  // 图标大小
  size?: number;
}

const props = withDefaults(defineProps<IconProps>(), {
  icon: 'Sugar',
  size: 20,
});
const emits = defineEmits<{}>();
const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
