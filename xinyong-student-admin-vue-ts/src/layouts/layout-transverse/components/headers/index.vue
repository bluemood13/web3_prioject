<template>
  <el-header
    class="cus-border-color flex w-full items-center justify-start border-b-[1px]"
    :height="headerHeight + 'px'"
  >
    <!-- 左侧的标题 -->
    <div
      class="flex items-center justify-center"
      :style="{ width: asideWidth + 'px' }"
    >
      <img
        class="inline w-[28px] object-contain"
        src="@/assets/logo.svg"
        alt="logo"
      />
      <span
        class="whitespace-wrap pl-2 text-[18.5px] font-bold tracking-wider"
        >{{ title }}</span
      >
    </div>
    <!-- 菜单显示 -->
    <el-menu mode="horizontal" :router="false" :default-active="activeMenu">
      <!-- 不能直接使用 SubMenu 组件，无法触发 el-menu 隐藏省略功能 -->
      <template v-for="subItem in menuList" :key="subItem.path">
        <el-sub-menu
          v-if="subItem.children?.length"
          :key="subItem.path"
          :index="subItem.path + 'el-sub-menu'"
        >
          <template #title>
            <!-- 加载图标 -->
            <template v-if="subItem.meta.icon">
              <component :is="subItem.meta.icon"></component>
            </template>
            <!-- 加载标题 -->
            <span>{{ subItem.meta.title }}</span>
          </template>
          <!--  -->
          <MenuCom :menu-list="subItem.children" />
        </el-sub-menu>
        <el-menu-item
          v-else
          :key="subItem.path + 'el-menu-item'"
          :index="subItem.path"
          @click="handleClickMenu(subItem)"
        >
          <el-icon>
            <component :is="subItem.meta.icon"></component>
          </el-icon>
          <template #title>
            <span>{{ subItem.meta.title }}</span>
          </template>
        </el-menu-item>
      </template>
    </el-menu>
    <!-- 右侧组件 -->
    <ToolBarCom class="right"></ToolBarCom>
  </el-header>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { store } from "@/utils/store";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import MenuCom from "@/components/menu/index.vue";
import ToolBarCom from "@/components/tool-bar/index.vue";

const app: any = getCurrentInstance()?.appContext.config
  .globalProperties as any;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();
// 系统标题
const title = ref(import.meta.env.VITE_TITLE);
// 默认激活的菜单
const activeMenu = computed(() => app.$route.path);
// 菜单列表
const menuList = computed(() => store.auth.routeMenuList);
console.log("菜单" + menuList);
// 侧边栏宽度
const asideWidth = computed(() => {
  return store.global.asideWidth;
});
/**
 * 菜单点击
 * @param subItem
 */
const handleClickMenu = (subItem: MenuRoute) => {
  if (subItem.meta.isFrame) {
    return window.open(subItem.path, "_blank");
  }
  app.$router.push(subItem.path);
};

const headerHeight = computed(() => {
  return store.global.headerHeight;
});

const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped>
.el-menu {
  flex: 1;
  height: 100%;
  overflow: hidden;
  border-bottom: none;
  .el-sub-menu__hide-arrow {
    width: 65px;
    height: 55px;
  }
  .el-menu-item.is-active {
    color: var(--el-color-primary) !important;
    border-bottom-color: var(--el-color-primary) !important;
  }
  .is-active {
    color: var(--el-color-primary) !important;
    &::before {
      width: 0;
    }
    .el-sub-menu__title {
      color: var(--el-color-primary) !important;
      border-bottom-color: var(--el-color-primary) !important;
    }
  }
}
@media screen and (width <= 730px) {
  .logo-text {
    display: none !important;
  }
}
</style>
