import { post } from '@/api/index';

export const addJiaoyi = (data: any = {}): Promise<Res> => {
    return post('/xinyong/jiaoyi/add', data);
};
export const delJiaoyi = (data: any = {}): Promise<Res> => {
    return post('/xinyong/jiaoyi/del', data);
};
export const updJiaoyi = (data: any = {}): Promise<Res> => {
    return post('/xinyong/jiaoyi/upd', data);
};
export const getJiaoyiListPage = (data: any = {}): Promise<Res> => {
    return post('/xinyong/jiaoyi/list/page', data);
};
export const getJiaoyiListAll = (data: any = {}): Promise<Res> => {
    return post('/xinyong/jiaoyi/list/all', data);
};
export const getJiaoyiDetail = (data: any = {}): Promise<Res> => {
    return post('/xinyong/jiaoyi/detail', data);
};
