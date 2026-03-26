import { tt } from "@/utils/tool";
export const useJiaoyiLinkHook = () => {
    const cusLinkRef = ref();
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
    const typesDataList = [
  {
    "label": "买入",
    "value": "buy"
  },
   {
    "label": "卖出",
    "value": "sell"
  }
]
    if(colLowName == 'types') {
        return typesDataList.find((item:any) => item.value == key)?.label
    }
    }
    
    const linkHan = async (row: any) => {
        cusLinkRef.value.vis = true;
        let r = tt.common.deepCopy(row?.jiaoyiBlockBaseList);
        r?.forEach((item: any) => {
            item.userId = getRemoteSelData('userId', item.userId);
            item.types = getStaticSelData('types', item.types);
        });
        await cusLinkRef.value.setForm(r, row.hash, [
            {
                label: '交易数量【区块】',
                prop: 'num',
                dataType: 'double',
            },
            {
                label: '交易类型【区块】',
                prop: 'types',
                dataType: 'static-sel',
            },
            {
                label: '交易单价【区块】',
                prop: 'price',
                dataType: 'double',
            },
        ]);
    };

  return {
    userIdDataList,
    getRemoteSelData,
    getStaticSelData,
    cusLinkRef,
    linkHan
  };
};
