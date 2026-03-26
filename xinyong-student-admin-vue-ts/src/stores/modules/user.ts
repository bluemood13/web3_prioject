import { defineStore } from "pinia";
import { tt } from "@/utils/tool";

const id: string = import.meta.env.VITE_KEY + "-user";

/**
 * 用户相关信息
 */
export const useUserStore = defineStore({
  id: id,
  state: (): UserState => ({
    token: undefined,
    userInfo: {
      id: 0,
      username: "",
      avatar: "",
      money: 0,
      name: "",
      sex: 1,
      phone: "",
      email: "",
      content: "",
      password: "",
      roles: [],
    },
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
  persist: tt.store.persistSave(id, "local"),
});
