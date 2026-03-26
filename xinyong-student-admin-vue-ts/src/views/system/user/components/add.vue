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
    type: 'input',
    name: 'username',
    default: '',
    disabled: false,
    label: '账号',
    rules: true,
  } as Input,
  {
    type: 'password',
    name: 'password',
    default: '',
    disabled: false,
    label: '密码',
    rules: true,
  } as Password,
  {
    type: 'input',
    name: 'name',
    default: '',
    disabled: false,
    label: '姓名',
    rules: true,
  } as Input,
  {
    type: 'select-base',
    // 是否多选
    more: false,
    //   下拉框改变的事件
    changeEvent: async (data: string) => {},
    options: [
      {
        label: '男',
        value: 1,
      },
      {
        label: '女',
        value: 0,
      },
    ],
    name: 'sex',
    default: 1,
    disabled: false,
    label: '性别',
    rules: true,
  } as BaseSelect,
  {
    type: 'input',
    name: 'phone',
    default: '',
    disabled: false,
    label: '手机号',
    rules: true,
  } as Input,
  {
    type: 'input',
    name: 'email',
    default: '',
    disabled: false,
    label: '邮箱',
    rules: true,
  } as Input,
  {
    type: 'textarea',
    name: 'content',
    default: '',
    disabled: false,
    label: '介绍',
    rules: true,
  } as Textarea,
  {
    type: 'double',
    min: 0,
    name: 'money',
    default: 0,
    disabled: false,
    label: '金币',
    rules: true,
  } as Double,
  {
    type: 'select',
    // 是否多选
    more: true,
    //   下拉框改变的事件
    changeEvent: async (data: string) => {},
    // 异步加载数据
    initFunc: async () => {
      const res = await api.sys.role.getRoleListAll({});
      console.log(res.data);
      return res.data;
    },
    // 异步加载的数据集合
    data: [],
    // 下拉框中的label的字段名称
    itemLabel: 'roleName',
    // 下拉框中的value的字段名称
    itemValue: 'id',
    name: 'roleIds',
    default: [],
    disabled: false,
    label: '角色',
    rules: true,
  } as Select,
]);
const cusFormRef = ref();

// 添加事件
const addHandler = async () => {
  if (!cusFormRef.value.formRef) return;
  await cusFormRef.value.formRef.validate(async (valid: any, fields: any) => {
    if (valid) {
      console.log(cusFormRef.value.formData);
      const result: Res = await api.sys.user.addUser({
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
