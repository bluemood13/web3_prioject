<template>
  <div class="tabs-box">
    <div class="tabs-menu">
      <el-tabs
        v-model="tabsMenuValue"
        type="card"
        @tab-click="tabClick"
        @tab-remove="tabRemove"
      >
        <el-tab-pane
          v-for="item in tabsMenuList"
          :key="item.path"
          :label="item.title"
          :name="item.path"
          :closable="!item.isAffix"
        >
          <template #label>
            {{ item.title }}
          </template>
        </el-tab-pane>
      </el-tabs>
      <!-- tab操作菜单 -->
      <div
        class="cus-border-color absolute top-0 right-0 bottom-0 flex h-full items-center justify-center border-l-[1px] px-4"
      >
        <MoreButtonCom />
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { store } from "@/utils/store";

import {
  getCurrentInstance,
  onMounted,
  ref,
  computed,
  watch,
  ComponentCustomProperties,
} from "vue";
// 拖拽库
import Sortable from "sortablejs";
import MoreButtonCom from "./components/moreButton.vue";

const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

const tabsMenuValue = ref(app.$route.fullPath);

// tab列表
const tabsMenuList = computed(() => store.tab.tabList);

// 监听路由的变化（防止浏览器后退/前进不变化 tabsMenuValue）
watch(
  // 监听路由数据
  () => app.$route.fullPath,
  (value, oldValue) => {
    // 如果是全屏的菜单跳过
    if (app.$route.meta.isFull) return;
    // 赋值当前的路由路径
    tabsMenuValue.value = app.$route.fullPath;

    const tabsParams: TabVo = {
      icon: app.$route.meta.icon as string,
      title: app.$route.meta.title as string,
      path: app.$route.fullPath,
      name: app.$route.name as string,
      isAffix: app.$route.meta.isAffix as boolean,
      isCache: app.$route.meta.isCache as boolean,
    };
    store.tab.addTab(tabsParams);
  },
  { immediate: true },
);
/**
 * tabs 拖拽排序
 */
const tabsDrop = () => {
  Sortable.create(document.querySelector(".el-tabs__nav") as HTMLElement, {
    // 要拖拽的元素
    draggable: ".el-tabs__item",
    // 动画
    animation: 300,
    onEnd({ newIndex, oldIndex }) {
      store.tab.swapTab(oldIndex as number, newIndex as number);
    },
  });
};
/**
 * tab点击事件
 * @param tabItem
 */
const tabClick = (tabItem: any) => {
  const fullPath = tabItem.props.name as string;
  app.$router.push(fullPath);
};

/**
 * 关闭tab页面
 * @param fullPath
 */
const tabRemove = (fullPath: any) => {
  store.tab.removeTab(fullPath, fullPath == app.$route.fullPath);
};
/**
 * 初始化需要固定的tab
 */
const initTabs = () => {
  store.auth.lastMenuList.forEach((item) => {
    if (item.meta.isAffix && !item.meta.isHide && !item.meta.isFrame) {
      const tabsParams: TabVo = {
        icon: item.meta.icon,
        title: item.meta.title,
        path: item.path,
        name: item.name,
        isAffix: item.meta.isAffix,
        isCache: item.meta.isCache,
      };
      store.tab.addTab(tabsParams);
    }
  });
};
const initData = async () => {
  initTabs();
  tabsDrop();
};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped>
.tabs-box {
  background-color: var(--el-bg-color);
  .tabs-menu {
    position: relative;
    width: 100%;
    :deep(.el-tabs) {
      .el-tabs__header {
        box-sizing: border-box;
        height: 40px;
        padding: 0 10px;
        margin: 0;
        .el-tabs__nav-wrap {
          position: absolute;
          width: calc(100% - 70px);
          .el-tabs__nav {
            display: flex;
            border: none;
            .el-tabs__item {
              display: flex;
              align-items: center;
              justify-content: center;
              color: #afafaf;
              border: none;
              .tabs-icon {
                margin: 1.5px 4px 0 0;
                font-size: 15px;
              }
              .is-icon-close {
                margin-top: 1px;
              }
              &.is-active {
                color: var(--el-color-primary);
                &::before {
                  position: absolute;
                  bottom: 0;
                  width: 100%;
                  height: 0;
                  content: "";
                  border-bottom: 2px solid var(--el-color-primary) !important;
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>
