<template>
  <div>
    <el-dropdown trigger="hover" @visible-change="toggleDropdown">
      <!-- 头像与昵称的位置 -->
      <!-- hover的时候有背景颜色 -->
      <div
        class="cus-bg-hover-color flex items-center justify-center rounded-md p-2"
      >
        <img
          :src="hooks.commonHooks.getHeadImgHook.value"
          alt="头像"
          class="h-8 w-8 cursor-pointer rounded-full object-cover"
        />

        <el-tag class="ml-2">
          {{ store.user.userInfo?.username }}
        </el-tag>

        <!-- 下拉箭头 -->
        <div
          class="arrow-icon ml-2 transition-transform duration-200"
          :class="{ 'rotate-180': isDropdownOpen }"
        >
          <svg
            class="h-4 w-4"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M19 9l-7 7-7-7"
            ></path>
          </svg>
        </div>
      </div>

      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="userInfoHandler">个人资料</el-dropdown-item>
          <el-dropdown-item @click="logoutHandler">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import { ConfigData } from "@/config";
import { store, storeClear } from "@/utils/store";
import { hooks } from "@/hooks";
const app: any = getCurrentInstance()?.appContext.config.globalProperties;

interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

// 下拉菜单状态
const isDropdownOpen = ref(false);

// 切换下拉菜单
const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value;
};

// 关闭下拉菜单
const closeDropdown = () => {
  isDropdownOpen.value = false;
};

/**
 * 跳转个人资料页面
 */
const userInfoHandler = () => {
  closeDropdown();
  app.$router.push(ConfigData.ROUTE_CONST.USER_INFO_ROUTE.PATH);
};

/**
 * 退出登录
 */
const logoutHandler = async () => {
  closeDropdown();
  await api.login.logout();
  // 清空store的信息
  storeClear();
  // 消息提示
  tt.msg.success("退出登录");
  // 跳转登录页面
  app.$router.push(ConfigData.ROUTE_CONST.LOGIN_ROUTE.PATH);
};

const initData = async () => {};

defineExpose({});

onMounted(async () => {
  await initData();
});
</script>

<style scoped lang="scss"></style>
