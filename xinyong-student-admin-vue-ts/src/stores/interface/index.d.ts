declare global {
  // 用户信息仓库
  declare interface UserState {
    token: string | undefined;
    userInfo: UserVo;
  }
  //   用户菜单信息仓库
  declare interface AuthState {
    menuList: MenuVo[];
  }
  // 布局，默认纵向，横向
  type LayoutType = "vertical" | "transverse";
  // 全局配置信息q
  declare interface GlobalState {
    // 布局模板
    layout: LayoutType;
    // 深色模式
    isDark: boolean;
    // 全局刷新
    isRefresh: boolean;
    // 登录模板
    loginTemplate: string;
    // 首页样式显示
    HomeDataShow: string;
    // 首页头部显示
    HomeHeadShow: string;
    // 个人资料模板
    userInfoTemplate: string;
    // 区块链显示模板
    linkShowTemplate: string;
    // 主题
    theme: string;
    // 主题颜色
    primary: DEFAULT_PRIMARY;
    // 侧边宽度
    asideWidth: number;
    // 动画
    animation: string;
    // 头部高度
    headerHeight: number;
    // 菜单是否收缩
    isColl: boolean;
    // 页脚
    showFooter: boolean;
    // 显示用户金额
    showUserMoney: boolean;
  }
  // tab配置信息
  declare interface TabVo {
    // tab图标
    icon: string;
    // tab名称
    title: string;
    // tab路径
    path: string;
    // tab名称
    name: string;
    // 是否固定tab
    isAffix: boolean;
    // 是否缓存tab
    isCache: boolean;
  }
  // tab标签信息
  declare interface TabState {
    tabList: TabVo[];
  }
  // keepAlive配置信息
  declare interface KeepState {
    keepList: string[];
  }
  // 公告信息
  declare interface InfoState {
    info: string;
  }
}

export {};
