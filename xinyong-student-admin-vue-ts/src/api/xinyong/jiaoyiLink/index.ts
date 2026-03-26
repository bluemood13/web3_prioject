import { post } from '@/api/index';

export const addJiaoyiLink = (data: any = {}): Promise<Res> => {
    return post('/xinyong/jiaoyiLink/add', data);
};
export const delJiaoyiLink = (data: any = {}): Promise<Res> => {
    return post('/xinyong/jiaoyiLink/del', data);
};
export const updJiaoyiLink = (data: any = {}): Promise<Res> => {
    return post('/xinyong/jiaoyiLink/upd', data);
};
export const getJiaoyiLinkListPage = (data: any = {}): Promise<Res> => {
    return post('/xinyong/jiaoyiLink/list/page', data);
};
export const getJiaoyiLinkListAll = (data: any = {}): Promise<Res> => {
    return post('/xinyong/jiaoyiLink/list/all', data);
};
export const getJiaoyiLinkDetail = (data: any = {}): Promise<Res> => {
    return post('/xinyong/jiaoyiLink/detail', data);
};
