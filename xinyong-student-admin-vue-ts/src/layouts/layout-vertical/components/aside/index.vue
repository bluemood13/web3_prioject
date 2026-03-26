<template>
  <el-aside class="cus-border-color border-r-[1px]">
    <div
      class="aside-box"
      :class="{ 'shrinking-box': isCollapse, 'expand-box': !isCollapse }"
      :style="{ width: isCollapse ? '65px' : asideWidth + 'px' }"
    >
      <div
        class="flex items-center justify-center"
        :style="'height:' + headerHeight + 'px'"
      >
        <div>
          <img
            class="inline w-[28px] object-contain"
            src="@/assets/logo.svg"
            alt="logo"
          />
          <span
            v-show="!isCollapse"
            class="whitespace-wrap pl-2 text-[18.5px] font-bold tracking-wider"
            >{{ title }}</span
          >
        </div>
      </div>
      <el-scrollbar>
        <el-menu
          :router="false"
          :default-active="activeMenu"
          :collapse="isCollapse"
          :collapse-transition="false"
        >
          <MenuCom :menu-list="menuList" />
        </el-menu>
      </el-scrollbar>
    </div>
  </el-aside>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { store } from "@/utils/store";

import MenuCom from "@/components/menu/index.vue";
import {
  getCurrentInstance,
  onMounted,
  ref,
  computed,
  watch,
  ComponentCustomProperties,
} from "vue";
import { ConfigData } from "@/config";
import { hooks } from "@/hooks";

const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
const route = useRoute();
const router = useRouter();
interface Props {}
const props = withDefaults(defineProps<Props>(), {});
const emits = defineEmits<{}>();

// 菜单栏折叠
const isCollapse = computed(() => {
  return store.global.isColl;
});
watch(isCollapse, async (n, o) => {});
// 侧边栏宽度
const asideWidth = computed(() => {
  return store.global.asideWidth;
});

const headerHeight = computed(() => {
  return store.global.headerHeight;
});
// 标题
const title = ref(import.meta.env.VITE_TITLE);
// 激活的菜单
const activeMenu = ref(hooks.useClearTabListHook());
console.log("初始化一次");
// 菜单列表
const menuList = computed(() => store.auth.routeMenuList);
console.log("菜单" + menuList);
const initData = async () => {};

defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped>
/* 定义宽度缩小的动画 */
@keyframes shrinkWidth {
  to {
    width: 65px; /* 动画结束宽度 */
  }
}

@keyframes expendWidth {
  from {
    width: 150px; /* 动画起始宽度 */
  }
}

/* 应用动画的元素样式 */
.shrinking-box {
  animation: shrinkWidth 0.5s ease forwards;
}

.expand-box {
  animation: expendWidth 0.5s ease forwards;
}
</style>
