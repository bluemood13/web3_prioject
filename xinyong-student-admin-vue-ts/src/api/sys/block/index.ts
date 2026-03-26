import { post, upload } from '@/api/index';

export const getBlockInfo = (data: any = {}): Promise<Res> => {
  return post('/sys/block/info', data);
};
