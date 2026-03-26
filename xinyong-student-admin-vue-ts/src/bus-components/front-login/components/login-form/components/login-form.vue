<template>
  <CusForm ref="cusFormRef" :data="cusFormData">
    <div class="w-full pt-2">
      <CusBtn class="w-full" @click="loginHan">登录</CusBtn>
    </div>
    <div class="w-full pt-2">
      <CusBtn
        type="danger"
        class="w-full"
        :plain="true"
        @click="emits('toRegister')"
        >点击注册</CusBtn
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
} from "vue";
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { store } from "@/utils/store";
import { ConfigData } from "@/config";
import { addRoutes } from "@/router/utils";

const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{
  (event: "toRegister"): void;
  (event: "closeDialog"): void;
}>();

const cusFormRef = ref();
const cusFormData = ref<any>([
  // 用户名
  {
    type: "input",
    name: "username",
    label: "用户名",
    default: "",
    disabled: false,
    rules: true,
  } as Input,
  // 密码
  {
    type: "password",
    name: "password",
    label: "密码",
    default: "",
    disabled: false,
    rules: true,
  } as Password,
  // 验证码
  {
    type: "verify-code",
    name: "code",
    label: "验证码",
    default: "",
    num: 1,
    contentHeight: 32,
    contentWidth: 180,
    disabled: false,
    rules: true,
  } as VerifyCode,
]);

/**
 * 表单登录组件
 */
const loginHan = () => {
  cusFormRef.value?.formRef?.validate(async (flag: any) => {
    // 如果表单验证通过
    if (flag) {
      try {
        console.log(cusFormRef.value?.formData);
        const res = await api.login.login({
          username: cusFormRef.value?.formData.username,
          password: cusFormRef.value?.formData.password,
        });
        // 赋值用户的信息
        store.user.token = res.data.token;
        store.user.userInfo = res.data;
        // 请求用户菜单数据
        const menuRes = await api.sys.menu.getUserMenu({});
        // 赋值用户的菜单信息
        store.auth.menuList = menuRes.data;
        // 将菜单数据加到路由中
        addRoutes(app.$router, store.auth.routeMenuList);
        // 清空tab和keepAlive
        store.tab.reset();
        store.keep.reset();
        // 消息提醒
        tt.msg.success(res.message);
        emits("closeDialog");
        // 跳转首页
        await app.$router.push(ConfigData.ROUTE_CONST.FRONT_ROUTE.PATH);
        cusFormRef.value?.resetForm();
      } catch (error) {
        console.error(error);
      }
    }
  });
};

// 按下回车键时执行登录
const handleKeydown = (event: KeyboardEvent) => {
  if (event.key === "Enter") {
    loginHan(); // 按下回车时调用登录函数
  }
};

// 监听键盘事件
onMounted(() => {
  window.addEventListener("keydown", handleKeydown);
});

// 移除监听器，防止内存泄漏
onBeforeUnmount(() => {
  window.removeEventListener("keydown", handleKeydown);
});
const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
