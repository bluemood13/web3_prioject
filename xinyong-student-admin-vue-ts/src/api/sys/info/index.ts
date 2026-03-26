import { post } from '@/api/index';

export const addInfo = (data: any = {}): Promise<Res> => {
  return post('/sys/info/add', data);
};

export const updInfo = (data: any = {}): Promise<Res> => {
  return post('/sys/info/upd', data);
};

export const getInfoListPage = (data: any = {}): Promise<Res> => {
  return post('/sys/info/list/page', data);
};

export const getInfoListAll = (data: any = {}): Promise<Res> => {
  return post('/sys/info/list/all', data);
};

export const delInfo = (data: any = {}): Promise<Res> => {
  return post('/sys/info/del', data);
};

export const getNew = (data: any = {}): Promise<Res> => {
  return post('/sys/info/getNew', data);
};
