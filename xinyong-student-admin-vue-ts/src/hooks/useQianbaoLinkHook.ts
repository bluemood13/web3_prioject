import { tt } from "@/utils/tool";
export const useQianbaoLinkHook = () => {
    // 定义远程变量
    const userIdDataList = ref<any>([]);
    // 获取远程下拉框数据
    const getRemoteSelData = (colLowName:string, key: string) => {
    if(colLowName == 'userId') {
        const findItem = userIdDataList.value.find((item:any) => item.id == key);
        return findItem
      ? findItem?.name !== undefined
        ? findItem?.name
        : findItem.id
      : "";
    }
    }
    // 获取静态下拉框数据
    const getStaticSelData = (colLowName:string, key: string) => {
    }
    

  return {
    userIdDataList,
    getRemoteSelData,
    getStaticSelData,
  };
};
