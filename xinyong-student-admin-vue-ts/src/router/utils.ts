import { RouteLocationNormalized, Router } from "vue-router";
import { ConfigData } from "@/config";
/**
 * 添加路由
 * @param {*} data
 */
export const addRoutes = (router: Router, data: any) => {
  for (const item of data) {
    router.addRoute(ConfigData.ROUTE_CONST.LAYOUT_ROUTE.NAME, item);
  }
};

/**
 * 设置动态标题
 * @param to
 */
export const setRouteTitle = (to: RouteLocationNormalized) => {
  if (to.name === "front") {
    to.meta.title = "用户端";
  }
  const title = import.meta.env.VITE_TITLE;
  document.title = to.meta.title ? `${to.meta.title} - ${title}` : title;
};
