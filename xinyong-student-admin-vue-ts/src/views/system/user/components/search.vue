<template>
  <CusCard :showTitle="false">
    <CusForm ref="cusFormRef" :data="data" :inline="true">
      <CusBtn type="primary" :plain="false" @click="searchHandler">搜索</CusBtn>
    </CusForm>
  </CusCard>
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
const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{
  (event: "search", formData: any): void;
}>();
const cusFormRef = ref();
const data = ref([
  {
    type: "input",
    name: "username",
    default: "",
    disabled: false,
    label: "账号",
    rules: false,
  } as Input,
  {
    type: "input",
    name: "name",
    default: "",
    disabled: false,
    label: "姓名",
    rules: false,
  } as Input,
]);
// 搜索事件
const searchHandler = async () => {
  await nextTick();
  emits("search", cusFormRef.value.formData);
};
const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
