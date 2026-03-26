<template>
  <CusCard title="菜单信息" class="overflow-y-scroll" :show-title="false">
    <CusForm ref="cusFormRef" :data="data"></CusForm>
    <div class="float-right">
      <CusBtn type="primary" @click="updHandler">更新</CusBtn>
      <CusBtn type="danger" @click="delHandler" v-if="canDel">删除</CusBtn>
    </div>
  </CusCard>
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
import { ConfigData } from '@/config';
import { tr } from 'element-plus/es/locale/index.mjs';
const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {
  //   demo?: string;
}
const props = withDefaults(defineProps<Props | {}>(), {
  //   demo: '',
});
const emits = defineEmits<{
  (event: 'refresh', id: any): void;
}>();

const data = ref([
  {
    type: 'id',
    name: 'id',
  } as Id,
  {
    type: 'select',
    // 是否多选
    more: false,
    //   下拉框改变的事件
    changeEvent: async (data: string) => {},
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
    rules: false,
  } as Select,
]);
const cusFormRef = ref();
//
const canDel = ref(true);
//
const formId = ref();
/**
 * 更新菜单
 */
const updHandler = async () => {
  if (!cusFormRef.value.formRef) return;
  await cusFormRef.value.formRef.validate(async (valid: any, fields: any) => {
    if (valid) {
      console.log(cusFormRef.value.formData);
      const result: Res = await api.sys.menu.updMenu({
        ...cusFormRef.value.formData,
      });
      tt.msg.success(result.message);
      cusFormRef.value.resetForm();
      emits('refresh', formId.value);
    }
  });
};

/**
 * 删除菜单
 */
const delHandler = async () => {
  const res: Res = await api.sys.menu.delMenu({
    id: cusFormRef.value.formData['id'],
  });
  tt.msg.success(res.message);
  emits('refresh', undefined);
};

/**
 * 重新赋值表单
 * @param {*} data
 */
const setForm = async (data: any) => {
  await nextTick();
  formId.value = data.id;
  // 判断是否可以删除菜单
  canDel.value = !ConfigData.NO_DEL_MENU.includes(data.id);
  console.log('data', data);
  // 赋值操作
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
defineExpose({ setForm });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
