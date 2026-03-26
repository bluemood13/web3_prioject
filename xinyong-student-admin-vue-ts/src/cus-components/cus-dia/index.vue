<template>
  <el-dialog v-model="vis" :title="props.title" append-to-body width="70%">
    <CusForm ref="cusFormRef" :data="data"></CusForm>
    <template #footer>
      <CusBtn type="primary" @click="clickHan">确定</CusBtn>
    </template>
  </el-dialog>
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
  title: string;
  data: any[];
  func: (params: any) => Promise<any>; // 修改这里，func 为一个函数类型
}
const props = withDefaults(defineProps<Props>(), {
  title: '',
  data: undefined,
  func: () => Promise.resolve({}), // 给 func 一个默认值
});
const emits = defineEmits<{
  (event: 'refresh'): void;
}>();

const vis = ref(false);

const cusFormRef = ref();

const clickHan = async () => {
  if (!cusFormRef.value.formRef) return;
  await cusFormRef.value.formRef.validate(async (valid: any, fields: any) => {
    if (valid) {
      console.log(cusFormRef.value.formData);
      const result: Res = await props.func({
        ...cusFormRef.value.formData,
      });
      tt.msg.success(result.message);
      vis.value = false;
      cusFormRef.value.resetForm();
      emits('refresh');
    }
  });
};
const setForm = async (data: any) => {
  await nextTick();
};
const initData = async () => {};
defineExpose({ vis, setForm, cusFormRef });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
