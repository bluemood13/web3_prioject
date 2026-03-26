<template>
  <template v-for="subItem in props.menuList" :key="subItem.path">
    <el-sub-menu v-if="subItem.children?.length" :index="subItem.path">
      <template #title>
        <!-- 加载图标 -->
        <template v-if="subItem.meta.icon">
          <div class="ml-1 pr-2">
            <CusIcon :icon="subItem.meta.icon" :size="iconSize"></CusIcon>
          </div>
        </template>
        <!-- 标题 -->
        <span>{{ subItem.meta.title }}</span>
      </template>
      <!-- 嵌套菜单 -->
      <MenuCom :menu-list="subItem.children" />
    </el-sub-menu>
    <el-menu-item
      v-else
      :index="subItem.path"
      @click="handleClickMenu(subItem)"
    >
      <!-- 加载图标 -->
      <template v-if="subItem.meta.icon">
        <div class="ml-1 pr-2">
          <CusIcon :icon="subItem.meta.icon" :size="iconSize"></CusIcon>
        </div>
      </template>
      <!-- 标题 -->
      <template #title>
        <span>{{ subItem.meta.title }}</span>
      </template>
    </el-menu-item>
  </template>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { ConfigData } from "@/config";

import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import MenuCom from "./index.vue";

const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {
  menuList: MenuRoute[];
}
const props = withDefaults(defineProps<Props>(), {
  menuList: () => {
    return [];
  },
});
const emits = defineEmits<{}>();
const iconSize = ref(ConfigData.ICON_SIZE);
/**
 * 点击菜单
 * @param subItem
 */
const handleClickMenu = (subItem: MenuRoute) => {
  if (subItem.meta.isFrame) {
    return window.open(subItem.path, "_blank");
  }
  app.$router.push(subItem.path);
};
const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped>
.el-sub-menu .el-sub-menu__title:hover {
  color: var(--el-menu-hover-text-color) !important;
  background-color: transparent !important;
}
.el-menu--collapse {
  .is-active {
    .el-sub-menu__title {
      color: #ffffff !important;
      background-color: var(--el-color-primary) !important;
    }
  }
}
.el-menu-item {
  &:hover {
    color: var(--el-menu-hover-text-color);
  }
  &.is-active {
    color: var(--el-menu-active-color) !important;
    background-color: var(--el-menu-active-bg-color) !important;
    &::before {
      position: absolute;
      top: 0;
      bottom: 0;
      left: 0;
      width: 4px;
      content: "";
      background-color: var(--el-color-primary);
    }
  }
}
.vertical,
.classic,
.transverse {
  .el-menu-item {
    &.is-active {
      &::before {
        left: 0;
      }
    }
  }
}
.columns {
  .el-menu-item {
    &.is-active {
      &::before {
        right: 0;
      }
    }
  }
}
</style>
