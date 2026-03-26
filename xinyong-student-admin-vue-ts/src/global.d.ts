import type { ButtonType } from "element-plus";

declare global {
  // 对应后端用户的数据结构
  declare interface UserVo {
    id: number;
    username: string;
    password: string;
    name: string;
    sex: number;
    avatar: string;
    money: number;
    email: string;
    phone: string;
    content: string;
    roles: Array<{ id: string; roleName: string }>;
  }

  // 对应后端菜单的数据结构
  declare interface MenuVo {
    // id
    id: number;
    // 路由的名称
    name: string;
    // 路由路径
    path: string;
    // 路由的显示标题
    title: string;
    // 路由的图标
    icon: string;
    // 父路由id
    parentId: number;
    // 路由排序
    rank: number;
    // 组件路径
    component: string;
    // 是否是外部链接
    isFrame?: boolean;
    // 是否隐藏菜单
    isHide?: boolean;
    // 是否固定
    isAffix?: boolean;
    // 是否缓存
    isCache?: boolean;
    // 子路由
    children: MenuVo[];
  }
  // 后端响应的基本数据结构
  declare interface Res {
    code: number;
    message: string;
    data: any;
  }
  // 首页映射元素
  interface HomeItem {
    label: string;
    value: any;
    type: "img" | "arr" | "str";
  }
}

export {};
