<template>
  <div class="mr-2 flex items-center justify-start">
    <div class="flex flex-1 items-center justify-center">
      <!-- 主题颜色 -->
      <div class="flex-1 px-2">
        <ThemeColor />
      </div>
      <!-- 暗黑模式 -->
      <div class="flex-1 px-2">
        <SwitchDark />
      </div>
      <!-- 跳转前端页面 -->
      <!-- <div class="flex-1 px-2" @click="toHomeHan">
        <el-tooltip effect="dark" content="跳转首页" placement="bottom">
          <div
            class="cus-bg-hover-color cus-text-color flex h-[36px] w-[36px] items-center justify-center rounded p-2"
          >
            <i-ep-promotion class="cursor-pointer"> </i-ep-promotion>
          </div>
        </el-tooltip>
      </div> -->
      <!-- 系统公告 -->
      <div class="flex-1 px-2" @click="showInfoHan">
        <el-tooltip effect="dark" content="查看公告" placement="bottom">
          <div
            class="cus-bg-hover-color cus-text-color flex h-[36px] w-[36px] items-center justify-center rounded p-2"
          >
            <i-ep-message class="cursor-pointer"> </i-ep-message>
          </div>
        </el-tooltip>
      </div>
      <!-- 切换用户 -->
      <div class="flex-1 px-2" @click="qiehuanHan">
        <el-tooltip effect="dark" content="切换用户" placement="bottom">
          <div
            class="cus-bg-hover-color cus-text-color flex h-[36px] w-[36px] items-center justify-center rounded p-2"
          >
            <i-ep-switch class="cursor-pointer"> </i-ep-switch>
          </div>
        </el-tooltip>
      </div>
      <!-- 系统设置 -->
      <div class="flex-1 px-2" @click="settingHan">
        <el-tooltip effect="dark" content="系统设置" placement="bottom">
          <div
            class="cus-bg-hover-color cus-text-color flex h-[36px] w-[36px] items-center justify-center rounded p-2"
          >
            <i-ep-setting class="cursor-pointer"> </i-ep-setting>
          </div>
        </el-tooltip>
      </div>
    </div>
    <!-- 用户 -->
    <div class="flex-1 pl-2">
      <UserInfoHeader></UserInfoHeader>
    </div>
    <!-- 切换用户 -->
    <SwitchUser ref="switchUserRef"></SwitchUser>
    <!-- 系统设置 -->
    <SystemSetting ref="systemSettingRef"></SystemSetting>
  </div>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { store, storeClear } from "@/utils/store";
import {
  getCurrentInstance,
  onMounted,
  ref,
  computed,
  watch,
  ComponentCustomProperties,
} from "vue";
import SwitchDark from "@/components/switch-dark/index.vue";
import SwitchUser from "@/components/switch-user/index.vue";
import ThemeColor from "@/components/theme-color/index.vue";
import SystemSetting from "@/components/system-setting/index.vue";
import UserInfoHeader from "@/components/user-info-header/index.vue";
import router from "@/router";
import { ConfigData } from "@/config";

const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

const username = computed(() => store.user.userInfo.username);

const switchUserRef = ref();
const systemSettingRef = ref();
/**
 * 跳转前端首页
 */
const toHomeHan = () => {
  // window.open(ConfigData.ROUTE_CONST.FRONT_ROUTE.NAME, "_blank");
  // 解析路由，获取完整的 URL
  const routeData = router.resolve({
    path: ConfigData.ROUTE_CONST.FRONT_ROUTE.PATH, // 目标路径
  });
  // 在新标签页打开
  window.open(routeData.href, "_blank");
};
/**
 * 显示公告
 */
const showInfoHan = () => {
  tt.notif.success(store.info.info, "最新公告", "top-left");
};

/**
 * 系统设置
 */
const settingHan = () => {
  systemSettingRef.value.vis = true;
};

/**
 * 切换用户
 */
const qiehuanHan = async () => {
  switchUserRef.value.vis = true;
};

const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped>
.tool-bar-ri {
  display: flex;
  align-items: center;
  justify-content: center;
  .header-icon {
    display: flex;
    align-items: center;
    & > * {
      margin-left: 21px;
      color: var(--el-header-text-color);
    }
  }
  .username {
    margin: 0 20px;
    font-size: 15px;
    color: var(--el-header-text-color);
  }
}
</style>
