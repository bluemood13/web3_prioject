<template>
  <div>
    <div class="my-6 px-2 text-center text-2xl font-bold tracking-wider">
      <img src="@/assets/logo.svg" class="inline w-[50px] pr-[15px]" />
      <span>{{ title }} - {{ comTitle }}</span>
    </div>
    <div>
      <CusCard :show-title="false" class="px-8 py-4">
        <div class="mb-4 flex w-full items-center justify-end">
          <SwitchDark></SwitchDark>
        </div>
        <!-- 登录表单 -->
        <LoginForm
          @toRegister="changeHan(false)"
          v-if="isLogin"
          @close-dialog="closeDialogEmit"
        ></LoginForm>
        <!-- 注册表单 -->
        <RegisterForm @toLogin="changeHan(true)" v-else></RegisterForm>
      </CusCard>
    </div>
  </div>
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
import LoginForm from "./components/login-form.vue";
import RegisterForm from "./components/register-form.vue";

const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{
  (event: "toRegister"): void;
  (event: "closeDialog"): void;
}>();

const title = import.meta.env.VITE_TITLE;
const comTitle = ref("登录");
const isLogin = ref(true);

const closeDialogEmit = () => {
  emits("closeDialog");
};
const changeHan = (data: boolean) => {
  isLogin.value = data;
  data ? (comTitle.value = "登录") : (comTitle.value = "注册");
};

const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
