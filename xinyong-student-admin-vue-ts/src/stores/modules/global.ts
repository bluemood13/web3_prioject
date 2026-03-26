import { defineStore } from "pinia";
import { tt } from "@/utils/tool";

const id: string = import.meta.env.VITE_KEY + "-global";

/**
 * 全局配置
 */
export const useGlobalStore = defineStore({
  id: id,
  state: (): GlobalState => ({
    // 布局模式 (纵向：vertical | 横向：transverse | 分栏：columns)
    layout: "vertical",
    // 深色模式
    isDark: false,
    // 全局刷新
    isRefresh: true,
    // 登录模板
    loginTemplate: "1",
    // 个人资料模板
    userInfoTemplate: "1",
    // 首页样式显示
    HomeDataShow: "table",
    // 首页头部显示
    HomeHeadShow: "img",
    // 区块链显示模板
    linkShowTemplate: "table",
    // 主题
    theme: "default",
    // 主题颜色
    primary: "#009688",
    // 菜单是否收缩
    isColl: false,
    // 页面动画
    animation: "animate__fadeInLeft",
    // 侧边宽度
    asideWidth: 300,
    // 头部高度
    headerHeight: 60,
    // 是否显示页脚
    showFooter: true,
    // 显示用户金额
    showUserMoney: true,
  }),
  getters: {},
  actions: {
    /**
     * 初始化数据信息
     */
    reset() {
      this.$reset();
    },
  },
  persist: tt.store.persistSave(id, "local"),
});
