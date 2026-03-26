import { post } from "@/api/index";

export const addSolana = (data: any = {}): Promise<Res> => {
  return post("/xinyong/solana/add", data);
};
export const delSolana = (data: any = {}): Promise<Res> => {
  return post("/xinyong/solana/del", data);
};
export const updSolana = (data: any = {}): Promise<Res> => {
  return post("/xinyong/solana/upd", data);
};
export const getSolanaListPage = (data: any = {}): Promise<Res> => {
  return post("/xinyong/solana/list/page", data);
};
export const getSolanaListAll = (data: any = {}): Promise<Res> => {
  return post("/xinyong/solana/list/all", data);
};
export const getSolanaDetail = (data: any = {}): Promise<Res> => {
  return post("/xinyong/solana/detail", data);
};
export const random = (data: any = {}): Promise<Res> => {
  return post("/xinyong/solana/random", data);
};
