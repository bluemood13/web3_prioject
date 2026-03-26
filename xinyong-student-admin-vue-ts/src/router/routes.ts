import { RouteRecordRaw } from "vue-router";
import { ConfigData } from "@/config";

export const staticRouter: RouteRecordRaw[] = [
  // 根路由
  {
    path: "/",
    redirect: ConfigData.ROUTE_CONST.HOME_ROUTE.PATH,
  },
  // 登录路由
  {
    path: ConfigData.ROUTE_CONST.LOGIN_ROUTE.PATH,
    name: ConfigData.ROUTE_CONST.LOGIN_ROUTE.NAME,
    component: () => import("@/views/login/index.vue"),
  },
  // 前端首页路由
  {
    path: ConfigData.ROUTE_CONST.FRONT_ROUTE.PATH,
    name: ConfigData.ROUTE_CONST.FRONT_ROUTE.NAME,
    component: () => import("@/views/front/index.vue"),
  },
  // 布局路由
  {
    path: ConfigData.ROUTE_CONST.LAYOUT_ROUTE.PATH,
    name: ConfigData.ROUTE_CONST.LAYOUT_ROUTE.NAME,
    component: () => import("@/layouts/index.vue"),
    redirect: ConfigData.ROUTE_CONST.HOME_ROUTE.PATH,
    children: [],
  },
  // 404错误页面路由
  {
    path: ConfigData.ROUTE_CONST.NOT_FOUND_ROUTE.PATH,
    name: ConfigData.ROUTE_CONST.NOT_FOUND_ROUTE.NAME,
    component: () => import("@/views/404/index.vue"),
  },
  // 未匹配路由
  {
    path: "/:pathMatch(.*)*",
    component: () => import("@/views/404/index.vue"),
  },
];
