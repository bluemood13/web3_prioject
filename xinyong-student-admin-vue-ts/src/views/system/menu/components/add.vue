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
    type: 'select',
    // 是否多选
    more: false,
    //   下拉框改变的事件
    changeEvent: async (data: string) => {
      if (data == '0') {
        cusFormRef.value.formData['path'] = '';
        cusFormRef.value.formData['component'] = '';
      }
    },
    // 异步加载数据
    initFunc: async () => {
      const res: Res = await api.sys.menu.getMenuListAll();
      res.data.unshift({
        id: 0,
        title: '顶级菜单',
      });
      return res.data;
    },
    // 异步加载的数据集合
    data: [],
    // 下拉框中的label的字段名称
    itemLabel: 'title',
    // 下拉框中的value的字段名称
    itemValue: 'id',
    name: 'parentId',
    default: '',
    disabled: false,
    label: '父级菜单',
    rules: true,
  } as Select,
  {
    type: 'input',
    name: 'name',
    default: '',
    disabled: false,
    label: '路由名称',
    rules: true,
  } as Input,
  {
    type: 'input',
    name: 'title',
    default: '',
    disabled: false,
    label: '菜单名称',
    rules: true,
  } as Input,
  {
    type: 'input',
    name: 'path',
    default: '',
    disabled: false,
    label: '路由地址',
    rules: true,
  } as Input,
  {
    type: 'input',
    name: 'component',
    default: '',
    disabled: false,
    label: '组件路径',
    rules: false,
  } as Input,
  {
    type: 'icon',
    name: 'icon',
    default: '',
    disabled: false,
    label: '路由图标',
    rules: false,
  } as Icon,

  {
    type: 'int',
    min: 0,
    name: 'ranks',
    default: 0,
    disabled: false,
    label: '菜单排序',
    rules: true,
  } as Int,

  {
    type: 'bit',
    name: 'isFrame',
    default: false,
    bitY: '是',
    bitN: '否',
    disabled: false,
    label: '外部链接',
    rules: true,
  } as Bit,
  {
    type: 'bit',
    name: 'isHide',
    default: false,
    bitY: '是',
    bitN: '否',
    disabled: false,
    label: '隐藏菜单',
    rules: true,
  } as Bit,
  {
    type: 'bit',
    name: 'isAffix',
    default: false,
    bitY: '是',
    bitN: '否',
    disabled: false,
    label: '固定标签',
    rules: true,
  } as Bit,
  {
    type: 'bit',
    name: 'isCache',
    default: false,
    bitY: '是',
    bitN: '否',
    disabled: false,
    label: '缓存页面',
    rules: true,
  } as Bit,
  {
    type: 'select',
    // 是否多选
    more: true,
    //   下拉框改变的事件
    changeEvent: async (data: string) => {},
    // 异步加载数据
    initFunc: async () => {
      const res: Res = await api.sys.role.getRoleListAll();
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
    label: '角色列表',
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
      const result: Res = await api.sys.menu.addMenu({
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
  for (const x in cusFormRef.value.formData) {
    // 如果字段相同
    if (x == 'parentId') {
      cusFormRef.value.formData[x] = data['id'];
    }
    if (x == 'path') {
      cusFormRef.value.formData[x] = data['path'];
    }
    if (x == 'component') {
      cusFormRef.value.formData[x] = data['path'];
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
