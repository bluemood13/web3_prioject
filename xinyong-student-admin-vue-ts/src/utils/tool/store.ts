/**
 * 全局的本地存储工具
 */
export const StoreTool = {
  /**
   * 配置store的持久化
   * @param key 持久化的key值
   * @param storage 对应保存的地方
   * @param paths 对应要保存的字段
   * @returns
   */
  persistSave: (
    key: string,
    storage: 'local' | 'session' = 'local',
    paths?: string[]
  ) => {
    if (storage == 'local') {
      return {
        key,
        storage: localStorage,
        paths,
      };
    } else {
      return {
        key,
        storage: sessionStorage,
        paths,
      };
    }
  },
};
