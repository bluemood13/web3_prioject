import { post } from '@/api/index';

export const addUser = (data: any = {}): Promise<Res> => {
  return post('/sys/user/add', data);
};
export const delUser = (data: any = {}): Promise<Res> => {
  return post('/sys/user/del', data);
};
export const updUser = (data: any = {}): Promise<Res> => {
  return post('/sys/user/upd', data);
};
export const getUserListPage = (data: any = {}): Promise<Res> => {
  return post('/sys/user/list/page', data);
};
export const getUserListAll = (data: any = {}): Promise<Res> => {
  return post('/sys/user/list/all', data);
};

export const getUserInfo = (data: any = {}): Promise<Res> => {
  return post('/sys/user/getUserInfo', data);
};

export const chongzhi = (data: any = {}): Promise<Res> => {
  return post('/sys/user/chongzhi', data);
};

export const changePassword = (data: any = {}): Promise<Res> => {
  return post('/sys/user/changePassword', data);
};
