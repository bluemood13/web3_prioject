<template>
  <el-dialog v-model="vis" title="更新" append-to-body>
    <CusForm ref="cusFormRef" :data="data"></CusForm>
    <template #footer>
      <CusBtn type="primary" @click="updHandler">确定</CusBtn>
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
    type: 'id',
    name: 'id',
  } as Id,
  {
    type: 'textarea',
    name: 'info',
    default: '',
    label: '系统公告',
    rules: true,
  } as Textarea,
]);
const cusFormRef = ref();
// 更新事件
const updHandler = async () => {
  if (!cusFormRef.value.formRef) return;
  await cusFormRef.value.formRef.validate(async (valid: any, fields: any) => {
    if (valid) {
      console.log(cusFormRef.value.formData);
      const result: Res = await api.sys.info.updInfo({
        ...cusFormRef.value.formData,
      });
      tt.msg.success(result.message);
      vis.value = false;
      cusFormRef.value.resetForm();
      emits('refresh');
    }
  });
};
/**
 * 重新赋值表单
 * @param {*} data
 */
const setForm = async (data: any) => {
  await nextTick();
  for (const item in data) {
    for (const x in cusFormRef.value.formData) {
      // 如果字段相同
      if (item == x) {
        cusFormRef.value.formData[x] = data[item];
      }
    }
  }
};
const initData = async () => {};
defineExpose({ vis, setForm });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
