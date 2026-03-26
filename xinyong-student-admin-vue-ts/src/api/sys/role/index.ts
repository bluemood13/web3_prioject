import { post } from "@/api/index";

export const addRole = (data: any = {}): Promise<Res> => {
  return post("/sys/role/add", data);
};
export const delRole = (data: any = {}): Promise<Res> => {
  return post("/sys/role/del", data);
};
export const delForceRole = (data: any = {}): Promise<Res> => {
  return post("/sys/role/delForce", data);
};
export const updRole = (data: any = {}): Promise<Res> => {
  return post("/sys/role/upd", data);
};
export const getRoleListPage = (data: any = {}): Promise<Res> => {
  return post("/sys/role/list/page", data);
};
export const getRoleListAll = (data: any = {}): Promise<Res> => {
  return post("/sys/role/list/all", data);
};
