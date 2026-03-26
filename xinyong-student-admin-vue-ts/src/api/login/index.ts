import { post } from '@/api/index';

// 登录
export const login = (data: any = {}): Promise<Res> => {
  return post('/login', data);
};
// 退出登录
export const logout = (): Promise<Res> => {
  return post('/logout');
};

// 注册
export const register = (data: any = {}): Promise<Res> => {
  return post('/register', data);
};
