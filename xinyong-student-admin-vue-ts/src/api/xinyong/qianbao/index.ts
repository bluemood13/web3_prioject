import { post } from "@/api/index";

export const addQianbao = (data: any = {}): Promise<Res> => {
  return post("/xinyong/qianbao/add", data);
};
export const delQianbao = (data: any = {}): Promise<Res> => {
  return post("/xinyong/qianbao/del", data);
};
export const updQianbao = (data: any = {}): Promise<Res> => {
  return post("/xinyong/qianbao/upd", data);
};
export const getQianbaoListPage = (data: any = {}): Promise<Res> => {
  return post("/xinyong/qianbao/list/page", data);
};
export const getQianbaoListAll = (data: any = {}): Promise<Res> => {
  return post("/xinyong/qianbao/list/all", data);
};
export const getQianbaoDetail = (data: any = {}): Promise<Res> => {
  return post("/xinyong/qianbao/detail", data);
};

export const buy = (data: any = {}): Promise<Res> => {
  return post("/xinyong/qianbao/buy", data);
};
export const sell = (data: any = {}): Promise<Res> => {
  return post("/xinyong/qianbao/sell", data);
};
