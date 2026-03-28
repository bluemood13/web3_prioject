<template>
  <el-aside class="cus-border-color border-r-[1px]">
    <div
      class="aside-box"
      :class="{ 'shrinking-box': isCollapse, 'expand-box': !isCollapse }"
      :style="{ width: isCollapse ? '65px' : asideWidth + 'px' }"
    >
      <div
        class="logo-zone"
        :style="'height:' + headerHeight + 'px'"
      >
        <div class="logo-inner">
          <div class="logo-icon-wrap">
            <img class="logo-img" src="@/assets/logo.svg" alt="logo" />
          </div>
          <span v-show="!isCollapse" class="logo-text">{{ title }}</span>
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

const isCollapse = computed(() => store.global.isColl);
watch(isCollapse, async (n, o) => {});

const asideWidth = computed(() => store.global.asideWidth);
const headerHeight = computed(() => store.global.headerHeight);

const title = ref(import.meta.env.VITE_TITLE);
const activeMenu = ref(hooks.useClearTabListHook());
const menuList = computed(() => store.auth.routeMenuList);

const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>

<style lang="scss" scoped>
/* Collapse animations */
@keyframes shrinkWidth {
  to { width: 65px; }
}
@keyframes expendWidth {
  from { width: 150px; }
}
.shrinking-box { animation: shrinkWidth 0.5s ease forwards; }
.expand-box    { animation: expendWidth 0.5s ease forwards; }

/* Logo zone */
.logo-zone {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 12px;
  border-bottom: 1px solid color-mix(in srgb, var(--el-border-color) 40%, transparent);
  flex-shrink: 0;
}

.logo-inner {
  display: flex;
  align-items: center;
  gap: 10px;
  overflow: hidden;
}

.logo-icon-wrap {
  flex-shrink: 0;
  width: 36px;
  height: 36px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(
    135deg,
    color-mix(in srgb, var(--el-color-primary) 22%, #fff),
    color-mix(in srgb, var(--el-color-primary) 10%, #fff)
  );
  box-shadow: 0 4px 12px -2px color-mix(in srgb, var(--el-color-primary) 35%, transparent);
}

.logo-img {
  width: 22px;
  height: 22px;
  object-fit: contain;
}

.logo-text {
  font-size: 16px;
  font-weight: 800;
  letter-spacing: -0.03em;
  white-space: nowrap;
  overflow: hidden;
  background: linear-gradient(
    135deg,
    var(--el-color-primary),
    color-mix(in srgb, var(--el-color-primary) 55%, #6366f1)
  );
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}
</style>
