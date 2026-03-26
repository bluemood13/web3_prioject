import { defineStore } from "pinia";
import { tt } from "@/utils/tool";

const id: string = import.meta.env.VITE_KEY + "-keep";

/**
 * keepAlive相关信息，保持页面
 */
export const useKeepStore = defineStore({
  id: id,
  state: (): KeepState => ({
    keepList: [],
  }),
  getters: {},
  actions: {
    /**
     * 添加组件的完整路径
     * @param fullName
     */
    addItem(fullName: any) {
      if (!this.keepList.includes(fullName)) {
        this.keepList.push(fullName);
      }
    },
    /**
     * 去除keepList里面某个数据
     */
    removeItem(fullName: any) {
      this.keepList = this.keepList.filter((item) => item !== fullName);
    },
    /**
     * 初始化数据信息
     */
    reset() {
      this.$reset();
    },
  },
  persist: tt.store.persistSave(id, "local"),
});
