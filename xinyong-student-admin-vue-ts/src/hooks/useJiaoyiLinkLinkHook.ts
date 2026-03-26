import { tt } from "@/utils/tool";
export const useJiaoyiLinkLinkHook = () => {
    // 定义远程变量
    const jiaoyiIdDataList = ref<any>([]);
    // 获取远程下拉框数据
    const getRemoteSelData = (colLowName:string, key: string) => {
    if(colLowName == 'jiaoyiId') {
        const findItem = jiaoyiIdDataList.value.find((item:any) => item.id == key);
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
    jiaoyiIdDataList,
    getRemoteSelData,
    getStaticSelData,
  };
};
