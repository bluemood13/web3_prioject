import { Component } from 'vue';

declare global {
  /**
   * 路由菜单数据
   */
  declare interface MenuRoute {
    path: string;
    name: string;
    component?: Component;
    redirect?: string;
    meta: MenuRouteMeta;
    children?: MenuRoute[];
  }

  /**
   * 路由元信息
   */
  declare interface MenuRouteMeta {
    // 编号
    id: number;
    // 父编号
    parentId: number;
    // 排序
    rank: number;
    // 标题
    title: string;
    // 图标
    icon: string;
    // 是否是外部链接
    isFrame: boolean;
    // 是否隐藏菜单
    isHide: boolean;
    // 是否固定
    isAffix: boolean;
    // 是否缓存
    isCache: boolean;
  }
}
export {};
