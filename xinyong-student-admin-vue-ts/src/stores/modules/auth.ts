import { defineStore } from "pinia";
import { tt } from "@/utils/tool";
import {
  getRouteMenuList,
  getLastMenuList,
  getFirstMenuList,
} from "@/stores/utils";
const id: string = import.meta.env.VITE_KEY + "-auth";

/**
 * 菜单与权限相关信息
 */
export const useAuthStore = defineStore({
  id: id,
  state: (): AuthState => ({
    // 菜单数据列表
    menuList: [],
  }),
  getters: {
    /**
     * 获取一级前端菜单列表
     */
    firstMenuList: (state) => {
      return getFirstMenuList(state.menuList);
    },
    /**
     * 获取前端路由数据
     */
    routeMenuList: (state) => {
      return getRouteMenuList(state.menuList);
    },
    /**
     * 获取最后一层的路由数据
     */
    lastMenuList: (state) => {
      return getLastMenuList(state.menuList);
    },
  },
  actions: {
    /**
     * 初始化数据信息
     */
    reset() {
      this.$reset();
    },
  },
});
