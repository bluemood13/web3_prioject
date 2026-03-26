<template>
  <el-transfer
    ref="tranRef"
    v-model="modelValue"
    :data="data"
    :filterable="props.isFilter"
    :titles="['未选', '已选']"
    :filter-method="props.filterFunc"
    filter-placeholder="搜索"
    @change="props.changeFunc"
  />
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import { TransferDataItem, TransferDirection, TransferKey } from "element-plus";
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {
  // 初始化数据
  initFunc: Function;
  //   搜索方法
  filterFunc?: (query: string, item: TransferDataItem) => boolean;
  //   是否开启搜索
  isFilter?: boolean;
  //   改变方法
  changeFunc?: (
    value: TransferKey[],
    direction: TransferDirection,
    movedKeys: TransferKey[],
  ) => void;
  // 是否禁止点击
  disabled?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  initFunc: async () => {
    return [];
  },
  filterFunc: (query: string, item: TransferDataItem) => {
    return true;
  },
  isFilter: false,
  changeFunc: (
    value: TransferKey[],
    direction: TransferDirection,
    movedKeys: TransferKey[],
  ) => {},
  disabled: false,
});
const emits = defineEmits<{}>();

const tranRef = ref();
const data = ref<TransferDataItem[]>([]);

const modelValue = defineModel<TransferKey[]>({
  default: () => [],
});

const initData = async () => {
  const res = await props.initFunc();
  if (res) {
    if (props.disabled) {
      res.forEach((item: any) => {
        item.disabled = true;
      });
    } else {
      res.forEach((item: any) => {
        item.disabled = false;
      });
    }
    data.value = res;
  }
};
defineExpose({ tranRef, data, modelValue });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
