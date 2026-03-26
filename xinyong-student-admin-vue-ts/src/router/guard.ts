import {
  NavigationGuardNext,
  RouteLocationNormalized,
  RouteLocationNormalizedLoaded,
  Router,
} from "vue-router";
// 导入进度条组件
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import { useUserStore } from "@/stores/modules/user";
import { useAuthStore } from "@/stores/modules/auth";
import { ConfigData } from "@/config";
import { getUserMenu } from "@/api/sys/menu";
import { addRoutes, setRouteTitle } from "@/router/utils";

// 路由白名单，可以直接访问
const WHITE_PATH_LIST = [
  ConfigData.ROUTE_CONST.LOGIN_ROUTE.PATH,
  ConfigData.ROUTE_CONST.NOT_FOUND_ROUTE.PATH,
  ConfigData.ROUTE_CONST.FRONT_ROUTE.PATH,
];

/**
 * 创建路由守卫
 */
export const createGuard = (router: Router) => {
  //全局导航进入守卫
  router.beforeEach(
    async (
      to: RouteLocationNormalized,
      from: RouteLocationNormalizedLoaded,
      next: NavigationGuardNext,
    ) => {
      // 开启浏览器进度条
      NProgress.start();
      // 设置动态标题
      setRouteTitle(to);
      console.log(to);
      console.log(2222);
      if (WHITE_PATH_LIST.includes(to.fullPath)) {
        return next();
      }
      // 用户数据
      const userStore = useUserStore();
      // 菜单权限数据
      const authStore = useAuthStore();
      const token = userStore.token;
      // 如果token存在
      if (token) {
        // 判断是否拥有菜单
        if (authStore.menuList?.length == 0) {
          // 请求后端菜单列表
          const menuRes = await getUserMenu({});
          authStore.menuList = menuRes.data;
          console.log(authStore.menuList);
          // 将菜单数据加到路由中
          addRoutes(router, authStore.routeMenuList);
          // 重新加载路由
          return next({ ...to, replace: true });
        }
      } else {
        // 清除用户信息
        userStore.reset();
        // 清除菜单信息
        authStore.reset();
        // 跳转登录页面
        return next({
          path: ConfigData.ROUTE_CONST.LOGIN_ROUTE.PATH,
          replace: true,
        });
      }
      console.log(to);
      console.log(from);
      // 最终执行访问页面
      next();
    },
  );
  //全局导航退出守卫
  router.afterEach(
    (to: RouteLocationNormalized, from: RouteLocationNormalizedLoaded) => {
      // 结束浏览器进度条
      NProgress.done();
    },
  );
};
