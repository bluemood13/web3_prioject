<template>
  <el-aside
    class="cus-border-color border-r-[1px]"
    :width="asideWidth + minWidth + 'px'"
  >
    <div
      class="flex h-full w-full items-start justify-start"
      :style="{ 'min-width': asideWidth + minWidth + 'px' }"
    >
      <!-- 主菜单 -->
      <div
        class="cus-border-color flex h-full flex-col items-center justify-start border-r-[1px] py-4 transition-all duration-300"
        :style="{
          width: isCollapse ? maxWidth + 2 + 'px' : minWidth + 2 + 'px',
        }"
      >
        <!-- Logo 区域 -->
        <div class="fixed top-4 z-10">
          <img
            class="h-[30px] w-[30px] cursor-pointer object-contain transition-transform hover:scale-110"
            src="@/assets/logo.svg"
            alt="logo"
          />
        </div>
        <!-- 菜单容器 - 适配头部高度 -->
        <div
          class="w-full flex-1 pt-2 transition-all duration-300"
          :style="{
            marginTop: headerHeight - 15 + 'px',
          }"
        >
          <el-scrollbar class="w-full">
            <el-menu
              style="border-right: none !important"
              :router="false"
              :default-active="activePath"
              :collapse="isCollapse"
              :collapse-transition="false"
              @select="handleMenuSelect"
              :style="{ width: isCollapse ? maxWidth + 'px' : minWidth + 'px' }"
            >
              <el-menu-item
                v-for="subItem in mainMenuList"
                :key="subItem.path"
                :index="subItem.path"
                @click="handleClickMenu(subItem)"
                class="relative flex !h-auto justify-center overflow-x-hidden !p-0"
              >
                <template #default v-if="subItem.meta?.icon">
                  <div
                    class="my-1 flex h-full w-full flex-col items-center justify-center py-2"
                  >
                    <!-- 菜单图标 -->

                    <CusIcon :icon="subItem.meta.icon" :size="iconSize" />
                    <!-- 菜单文本 - 优化显示效果 -->
                    <div
                      class="mt-2 w-full px-1 text-center text-xs leading-tight whitespace-normal"
                      v-if="isCollapse"
                    >
                      {{ subItem.meta?.title || subItem.name }}
                    </div>
                    <!-- 选中指示器 -->
                    <div
                      v-if="activeMenu === subItem.path"
                      class="absolute top-0 bottom-0 left-0 border-l-[4px]"
                    ></div>
                  </div>
                </template>
              </el-menu-item>
            </el-menu>
          </el-scrollbar>
        </div>

        <!-- 折叠/展开按钮 -->
        <div class="fixed bottom-6 flex items-center justify-center">
          <CollapseIcon></CollapseIcon>
        </div>
      </div>

      <!-- 第二列 -->
      <div class="h-full w-full">
        <!-- 标题区域 -->
        <div
          class="ml-4 flex items-center justify-start p-4"
          :style="{ height: headerHeight + 'px' }"
        >
          <span
            class="whitespace-wrap pl-2 text-[18.5px] font-bold tracking-wider"
            >{{ title }}</span
          >
        </div>
        <!-- 子菜单区域 -->
        <el-menu
          :default-active="activeSubPath"
          class="w-full"
          style="border-right: none !important"
          @select="handleSubMenuSelect"
        >
          <el-menu-item
            v-for="subMenu in subMenuList"
            :key="subMenu.path"
            :index="subMenu.path"
            class="mx-4 rounded-lg"
            @click="handleClickMenu(subMenu)"
            :class="{
              'menu-active-color': activeSubPath === subMenu.path,
            }"
          >
            <template #default v-if="subMenu.meta?.icon">
              <CusIcon :icon="subMenu.meta.icon" :size="iconSize" />
            </template>
            <template #title>
              <div class="pl-2">
                {{ subMenu.meta?.title || subMenu.name }}
              </div>
            </template>
          </el-menu-item>
        </el-menu>
      </div>
    </div>
  </el-aside>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { store } from "@/utils/store";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import { ConfigData } from "@/config";
import CollapseIcon from "@/components/collapse-icon/index.vue";
import { pad } from "lodash";
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();
const router = useRouter();

const activePath = ref<string>();
const activeMenu = ref();
const activeSubPath = ref<string>();
const activeSubMenu = ref();

const isCollapse = computed(() => !store.global.isColl);
const iconSize = computed(() => ConfigData.ICON_SIZE);
const maxWidth = ref(80);
const minWidth = ref(65);

// 标题
const title = ref(import.meta.env.VITE_TITLE);
// 头部高度
const headerHeight = computed(() => {
  return store.global.headerHeight;
});
// 侧边栏宽度
const asideWidth = computed(() => {
  return store.global.asideWidth;
});
// 一级菜单列表
const mainMenuList = computed(() => store.auth.routeMenuList);
console.log("mainMenuList:", mainMenuList);
// 子菜单列表
const subMenuList = ref([]);

/**
 * 菜单选中事件（Element Plus内置事件）
 */
const handleMenuSelect = (index: string) => {
  activePath.value = index;
  activeMenu.value = store.auth.routeMenuList.find(
    (menu) => menu.path === index,
  );
  // 设置子菜单列表
  if (activeMenu.value.children && activeMenu.value.children.length > 0) {
    subMenuList.value = activeMenu.value.children;
  } else {
    subMenuList.value = [activeMenu.value];
    activeSubPath.value = activePath.value;
  }
};

/**
 * 子菜单选择事件
 * @param index
 */
const handleSubMenuSelect = (index: string) => {
  activeSubPath.value = index;
  activeSubMenu.value = subMenuList.value.find((menu) => menu.path === index);
};

/**
 * 点击菜单
 * @param subItem
 */
const handleClickMenu = (menu: MenuRoute) => {
  console.log(activeMenu.value);

  if (menu.meta.isFrame) {
    return window.open(menu.path, "_blank");
  } else if (menu.children && menu.children.length > 0) {
    return;
  }
  app.$router.push(menu.path);
};
/**
 * 初始化数据
 */
const initData = () => {
  // 初始化时默认选中第一个菜单（如果有）
  if (mainMenuList.value.length > 0 && !activeMenu.value) {
    activeMenu.value = mainMenuList.value[0].path;
  }
  // 监听路由变化，同步菜单选中状态
  watch(
    () => router.currentRoute.value.path,
    (newPath) => {
      const matchedMenu = mainMenuList.value.find(
        (menu) => menu.path === newPath,
      );
      if (matchedMenu) {
        activePath.value = newPath;
        activeMenu.value = matchedMenu;
        // 设置子菜单列表
        if (activeMenu.value.children && activeMenu.value.children.length > 0) {
          subMenuList.value = activeMenu.value.children;
        } else {
          subMenuList.value = [activeMenu.value];
          activeSubPath.value = activePath.value;
        }
      }
    },
    { immediate: true },
  );
};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped>
.menu-active-color {
  background-color: var(--el-menu-hover-bg-color);
  color: var(--el-menu-active-color); /* 文字保持原激活色 */
}
</style>
