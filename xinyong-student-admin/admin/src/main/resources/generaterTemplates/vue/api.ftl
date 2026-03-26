import { post } from '@/api/index';

export const add${modelName} = (data: any = {}): Promise<Res> => {
    return post('/${tablePrefix}/${lowerModelName}/add', data);
};
export const del${modelName} = (data: any = {}): Promise<Res> => {
    return post('/${tablePrefix}/${lowerModelName}/del', data);
};
export const upd${modelName} = (data: any = {}): Promise<Res> => {
    return post('/${tablePrefix}/${lowerModelName}/upd', data);
};
export const get${modelName}ListPage = (data: any = {}): Promise<Res> => {
    return post('/${tablePrefix}/${lowerModelName}/list/page', data);
};
export const get${modelName}ListAll = (data: any = {}): Promise<Res> => {
    return post('/${tablePrefix}/${lowerModelName}/list/all', data);
};