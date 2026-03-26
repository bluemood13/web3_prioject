<template>
  <CusForm ref="cusFormRef" :data="cusFormData">
    <div class="pt-2 w-full">
      <CusBtn type="primary" class="w-full" @click="registerHan">注册</CusBtn>
    </div>
    <div class="pt-2 w-full">
      <CusBtn
        class="w-full"
        type="danger"
        :plain="true"
        @click="emits('toLogin')"
        >返回登录</CusBtn
      >
    </div>
  </CusForm>
</template>
<script setup lang="ts">
import {
  getCurrentInstance,
  onMounted,
  ref,
  computed,
  watch,
  ComponentCustomProperties,
} from 'vue';
import { tt } from '@/utils/tool';
import { api } from '@/utils/api';

const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{
  (event: 'toLogin'): void;
}>();
const cusFormRef = ref();
const cusFormData = ref<any>([
  // 用户名
  {
    type: 'input',
    name: 'username',
    label: '用户名',
    default: '',
    disabled: false,
    rules: true,
  } as Input,
  // 密码
  {
    type: 'password',
    name: 'password',
    label: '密码',
    default: '',
    disabled: false,
    rules: true,
  } as Password,
  {
    type: 'select',
    // 是否多选
    more: true,
    //   下拉框改变的事件
    changeEvent: async (data: string) => {},
    // 异步加载数据
    initFunc: async () => {
      const res = await api.sys.role.getRoleListAll();
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

/**
 * 注册事件
 */
const registerHan = () => {
  cusFormRef.value?.formRef?.validate(async (flag: any) => {
    if (flag) {
      try {
        console.log(cusFormRef.value?.formData);
        // 注册请求
        const res = await api.login.register({
          username: cusFormRef.value?.formData.username,
          password: cusFormRef.value?.formData.password,
          roleIds: cusFormRef.value?.formData.roleIds,
        });
        // 消息提示
        tt.msg.success(res.message);
        // 清空表单数据
        cusFormRef.value?.resetForm();
        // 切换登录页面
        emits('toLogin');
      } catch (error) {
        console.error(error);
      }
    }
  });
};

// 按下回车键时执行登录
const handleKeydown = (event: KeyboardEvent) => {
  if (event.key === 'Enter') {
    registerHan(); // 按下回车时调用登录函数
  }
};

// 监听键盘事件
onMounted(() => {
  window.addEventListener('keydown', handleKeydown);
});

// 移除监听器，防止内存泄漏
onBeforeUnmount(() => {
  window.removeEventListener('keydown', handleKeydown);
});
const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
