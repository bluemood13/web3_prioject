import { defineStore } from 'pinia';

const id: string = import.meta.env.VITE_KEY + "-info";
  
/**
 * 公告
 */
export const useInfoStore = defineStore({
  id: id,
  state: (): InfoState => ({
    info: '',
  }),
  getters: {},
  actions: {
    /**
     * 初始化数据信息
     */
    reset() {
      this.$reset();
    },
  },
});
