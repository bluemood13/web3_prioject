import { useUserStore } from "@/stores/modules/user";
import { useAuthStore } from "@/stores/modules/auth";
import { useGlobalStore } from "@/stores/modules/global";
import { useTabStore } from "@/stores/modules/tab";
import { useKeepStore } from "@/stores/modules/keep";
import { useInfoStore } from "@/stores/modules/info";
/**
 * 全局存储工具
 */
export const store = {
  user: useUserStore(),
  auth: useAuthStore(),
  global: useGlobalStore(),
  tab: useTabStore(),
  keep: useKeepStore(),
  info: useInfoStore(),
};

/**
 * 清理store里面的数据
 */
export const storeClear = () => {
  store.auth.reset();
  store.user.reset();
  store.tab.reset();
  store.keep.reset();
  store.info.reset();
};
