/**
 * 系统常量配置
 */
export const ConfigData = {
  // icon大小
  ICON_SIZE: 18,
  // header中的token标识
  AUTH_HEADER: "Authorization",
  // 主题色
  DEFAULT_PRIMARY: "#009688",
  // 预定义主题颜色
  THEME_COLOR: [
    "#009688",
    "#daa96e",
    "#0c819f",
    "#409eff",
    "#27ae60",
    "#ff5c93",
    "#e74c3c",
    "#fd726d",
    "#f39c12",
    "#9b59b6",
  ],
  // 默认激活的菜单
  DEFAULT_ACTIVE_MENU: "/home",
  // 上传文件的地址
  UPLOAD_SERVER: "/api/sys/file/upload",
  // 禁止删除的菜单
  NO_DEL_MENU: [8, 9, 10, 11, 12, 21, 22, 23],
  /**
   * 路由
   */
  ROUTE_CONST: {
    // 登录路由
    LOGIN_ROUTE: {
      NAME: "login",
      PATH: "/login",
    },
    // 首页路由
    HOME_ROUTE: {
      NAME: "home",
      PATH: "/home",
    },
    // 用户前端路由
    FRONT_ROUTE: {
      NAME: "front",
      PATH: "/front",
    },
    // 404路由
    NOT_FOUND_ROUTE: {
      NAME: "404",
      PATH: "/404",
    },
    // 布局路由
    LAYOUT_ROUTE: {
      NAME: "layout",
      PATH: "/layout",
    },
    // 个人信息路由
    USER_INFO_ROUTE: {
      NAME: "user-info",
      PATH: "/system/user-info",
    },
  },
  // 菜单主题的样式表
  MENU_THEME: {
    light: {
      "--el-menu-bg-color": "#ffffff",
      "--el-menu-hover-bg-color": "#cccccc",
      "--el-menu-active-bg-color": "var(--el-color-primary-light-9)",
      "--el-menu-text-color": "#333333",
      "--el-menu-active-color": "var(--el-color-primary)",
      "--el-menu-hover-text-color": "#333333",
      "--el-menu-horizontal-sub-item-height": "50px",
    },
    inverted: {
      "--el-menu-bg-color": "#191a20",
      "--el-menu-hover-bg-color": "#000000",
      "--el-menu-active-bg-color": "#000000",
      "--el-menu-text-color": "#bdbdc0",
      "--el-menu-active-color": "#ffffff",
      "--el-menu-hover-text-color": "#ffffff",
      "--el-menu-horizontal-sub-item-height": "50px",
    },
    dark: {
      "--el-menu-bg-color": "#141414",
      "--el-menu-hover-bg-color": "#000000",
      "--el-menu-active-bg-color": "#000000",
      "--el-menu-text-color": "#bdbdc0",
      "--el-menu-active-color": "#ffffff",
      "--el-menu-hover-text-color": "#ffffff",
      "--el-menu-horizontal-sub-item-height": "50px",
    },
  },
  /**
   * 头部的样式
   */
  HEADER_THEME: {
    light: {
      "--el-header-logo-text-color": "#303133",
      "--el-header-bg-color": "#ffffff",
      "--el-header-text-color": "#303133",
      "--el-header-text-color-regular": "#606266",
      "--el-header-border-color": "#e4e7ed",
    },
    inverted: {
      "--el-header-logo-text-color": "#dadada",
      "--el-header-bg-color": "#191a20",
      "--el-header-text-color": "#e5eaf3",
      "--el-header-text-color-regular": "#cfd3dc",
      "--el-header-border-color": "#414243",
    },
    dark: {
      "--el-header-logo-text-color": "#dadada",
      "--el-header-bg-color": "#141414",
      "--el-header-text-color": "#e5eaf3",
      "--el-header-text-color-regular": "#cfd3dc",
      "--el-header-border-color": "#414243",
    },
  },

  /**
   * 侧边主题的样式
   */
  ASIDE_THEME: {
    light: {
      "--el-aside-logo-text-color": "#303133",
      "--el-aside-border-color": "#e4e7ed",
    },
    inverted: {
      "--el-aside-logo-text-color": "#dadada",
      "--el-aside-border-color": "#414243",
    },
    dark: {
      "--el-aside-logo-text-color": "#dadada",
      "--el-aside-border-color": "#414243",
    },
  },

  /**
   * 分页
   */
  PAGE_CONST: {
    pageSize: 1,
    pageNum: 10,
    total: 0,
  },
};
