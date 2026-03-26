import { defineStore } from "pinia";
import { tt } from "@/utils/tool";
import { useKeepStore } from "./keep";
import router from "@/router";

const id: string = import.meta.env.VITE_KEY + "-tab";

/**
 * tab相关信息
 */
export const useTabStore = defineStore({
  id: id,
  state: (): TabState => ({
    tabList: [],
  }),
  getters: {},
  actions: {
    /**
     * 根据下标进行互换元素
     * @param oldIndex
     * @param newIndex
     */
    swapTab(oldIndex: number, newIndex: number) {
      tt.common.swapArr(this.tabList, oldIndex, newIndex);
    },
    /**
     * 添加tab进来
     * @param tabItem
     */
    addTab(tabItem: TabVo) {
      // 如果列表里面没有对应的tab就添加进来
      if (this.tabList.every((item) => item.path !== tabItem.path)) {
        this.tabList.push(tabItem);
      }
      // 加入菜单保持
      if (tabItem.isCache) {
        const keepStore = useKeepStore();
        keepStore.addItem(tabItem.path);
      }
    },
    /**
     * 移除tab
     * @param tabPath
     * @param isCurrent
     */
    removeTab(tabPath: string, isCurrent: boolean) {
      // 移除keepAlive里面的数据
      const keepStore = useKeepStore();
      const tabItem = this.tabList.find((item) => item.path === tabPath);
      keepStore.removeItem(tabItem?.path);
      // 判断是否是当前页面，需要跳转到另外的页面
      if (isCurrent) {
        for (let index = 0; index < this.tabList.length; index++) {
          if (this.tabList[index].path == tabPath) {
            const nextTab = this.tabList[index + 1] || this.tabList[index - 1];
            if (nextTab) {
              // 跳转页面
              router.push(nextTab.path);
              break;
            }
          }
        }
      }
      // 去除tabList的值
      this.tabList = this.tabList.filter((item) => item.path !== tabPath);
    },
    /**
     * 关闭一侧的tab
     * @param path
     * @param type
     */
    closeTabsOnSide(path: string, type: "left" | "right") {
      const keepStore = useKeepStore();
      const currentIndex = this.tabList.findIndex((item) => item.path === path);
      if (currentIndex !== -1) {
        const range =
          type === "left"
            ? [0, currentIndex]
            : [currentIndex + 1, this.tabList.length];
        // 过滤出不属于非关闭的
        this.tabList = this.tabList.filter((item, index) => {
          return index < range[0] || index >= range[1] || item.isAffix;
        });
      }
      // 重新赋值Keep
      const KeepAliveList = this.tabList.filter((item) => item.isCache);
      keepStore.keepList = KeepAliveList.map((item) => item.path);
    },
    /**
     * 关闭其他的tab
     * @param tabsMenuValue
     */
    closeOtherTab(tabsMenuValue?: string) {
      const keepStore = useKeepStore();
      this.tabList = this.tabList.filter((item) => {
        return item.path === tabsMenuValue || item.isAffix;
      });
      // 设置keepAlive
      const KeepAliveList = this.tabList.filter((item) => item.isCache);
      keepStore.keepList = KeepAliveList.map((item) => item.path);
    },

    /**
     * 关闭所有tab
     */
    closeAllTab() {
      const keepStore = useKeepStore();
      this.tabList = this.tabList.filter((item) => {
        return item.isAffix;
      });
      const KeepAliveList = this.tabList.filter((item) => item.isCache);
      keepStore.keepList = KeepAliveList.map((item) => item.path);
    },
    /**
     * 初始化数据信息
     */
    reset() {
      this.$reset();
    },
  },
  persist: tt.store.persistSave(id, "session"),
});
