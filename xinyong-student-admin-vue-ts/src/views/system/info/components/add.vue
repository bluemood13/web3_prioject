<template>
  <el-dialog v-model="vis" title="新增" append-to-body>
    <CusForm ref="cusFormRef" :data="data"></CusForm>
    <template #footer>
      <CusBtn type="primary" @click="addHandler">确定</CusBtn>
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
  nextTick,
  ComponentCustomProperties,
} from 'vue';
const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{
  (event: 'refresh'): void;
}>();
const vis = ref(false);
const data = ref([
  {
    type: 'textarea',
    name: 'info',
    default: '',
    label: '系统公告',
    rules: true,
  } as Textarea,
]);
const cusFormRef = ref();

// 添加事件
const addHandler = async () => {
  if (!cusFormRef.value.formRef) return;
  await cusFormRef.value.formRef.validate(async (valid: any, fields: any) => {
    if (valid) {
      console.log(cusFormRef.value.formData);
      const result: Res = await api.sys.info.addInfo({
        ...cusFormRef.value.formData,
      });
      tt.msg.success(result.message);
      vis.value = false;
      cusFormRef.value.resetForm();
      emits('refresh');
    }
  });
};

// 设置表懂啊
const setForm = async (data: any) => {
  await nextTick();
};
const initData = async () => {};
defineExpose({ vis, setForm });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
