import { post } from '@/api/index';

/** 获取菜单树形数据 */
export const menuListTree = (data: any = {}): Promise<Res> => {
  return post('/sys/menu/list/tree', data);
};

// 获取用户对应的菜单列表
export const getUserMenu = (data: any = {}): Promise<Res> => {
  return post('/sys/menu/getUserMenu', data);
};

/** 获取对应角色的菜单信息 */
export const getRoleMenu = (data: any = {}): Promise<Res> => {
  return post('/sys/menu/getRoleMenu', data);
};

/** 获取菜单下的对应角色id列表 */
export const getMenuRole = (data: any = {}): Promise<Res> => {
  return post('/sys/menu/getMenuRole', data);
};

export const addMenu = (data: any = {}): Promise<Res> => {
  return post('/sys/menu/add', data);
};
export const delMenu = (data: any = {}): Promise<Res> => {
  return post('/sys/menu/del', data);
};
export const updMenu = (data: any = {}): Promise<Res> => {
  return post('/sys/menu/upd', data);
};
export const getMenuListPage = (data: any = {}): Promise<Res> => {
  return post('/sys/menu/list/page', data);
};
export const getMenuListAll = (data: any = {}): Promise<Res> => {
  return post('/sys/menu/list/all', data);
};
