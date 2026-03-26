import { post, upload } from '@/api/index';

export const addFile = (data: any = {}): Promise<Res> => {
  return post('/sys/file/add', data);
};
export const delFile = (data: any = {}): Promise<Res> => {
  return post('/sys/file/del', data);
};
export const updFile = (data: any = {}): Promise<Res> => {
  return post('/sys/file/upd', data);
};
export const getFileListPage = (data: any = {}): Promise<Res> => {
  return post('/sys/file/list/page', data);
};
export const getFileListAll = (data: any = {}): Promise<Res> => {
  return post('/sys/file/list/all', data);
};

export const uploadFile = (file: [File], data: any = {}): Promise<Res> => {
  return upload('/sys/file/upload', file, data);
};
