<template>
  <CusCard :show-title="false">
    <div class="flex justify-around items-center flex-wrap">
      <div class="w-1/2 p-5" v-for="item in infoData">
        <CusCard :show-title="false">
          <template v-for="ite in formatItem(item)">
            <div class="p-1" v-if="ite.type == 'img'">
              <el-image
                style="width: 100%; height: 180px"
                :src="ite.value"
                fit="cover" />
            </div>
            <div class="p-1" v-else-if="ite.type == 'str'">
              <el-tag>{{ ite.label }}:</el-tag>
              <span class="px-2 text-sm">{{ ite.value }}</span>
            </div>
            <div class="p-1" v-else-if="ite.type == 'arr'">
              <el-tag>{{ ite.label }}:</el-tag>
              <span class="px-2 text-sm">
                <el-tag class="px-1" v-for="it in ite.value">{{ it }}</el-tag>
              </span>
            </div>
          </template>
          <div class="flex justify-end items-center pt-2">
            <div class="flex justify-around items-center flex-wrap">
              <div class="flex-1 p-1">
                <CusBtn :plain="true" @click="detailHan(item)">{{
                  btnName
                }}</CusBtn>
              </div>
              <div class="flex-1 p-1">
                <slot></slot>
              </div>
            </div>
          </div>
        </CusCard>
      </div>
    </div>
  </CusCard>
</template>
<script setup lang="ts">
import { tt } from '@/utils/tool';
import { api } from '@/utils/api';
import {
  getCurrentInstance,
  onMounted,
  ref,
  computed,
  watch,
  ComponentCustomProperties,
} from 'vue';
const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {
  btnName?: string;
}
const props = withDefaults(defineProps<Props>(), {
  btnName: '查看区块链',
});
const emits = defineEmits<{
  (event: 'detailEmit', row: any): void;
}>();

const infoData = ref<any>([]);

const keys = ref<any>([]);
const detailHan = async (row: any) => {
  emits('detailEmit', row);
};

/**
 * 格式化
 * @param item
 */
const formatItem = (item: any) => {
  console.log(item);
  const a: Array<HomeItem> = [];
  for (const i of keys.value) {
    if (i?.type == 'str') {
      a.push({
        label: i?.label,
        value: tt.common.getValue(item, i?.value),
        type: 'str',
      });
    } else if (i?.type == 'img') {
      a.push({
        label: i?.label,
        value: tt.common.getValue(item, i?.value),
        type: 'img',
      });
    } else if (i?.type == 'arr') {
      a.push({
        label: i?.label,
        value: i?.value(item),
        type: 'arr',
      });
    }
    // 根据 type 进行排序
    a.sort((firstItem: HomeItem, secondItem: HomeItem) => {
      const typeOrder: { [key in 'img' | 'arr' | 'str']: number } = {
        img: 0,
        arr: 1,
        str: 2,
      };
      return typeOrder[firstItem.type] - typeOrder[secondItem.type];
    });
  }
  return a;
};
/**
 * 设置值
 * @param data 区块链数据
 * @param keyMap 映射数据
 */
const setForm = async (data: Array<any>, keyMap: Array<any>) => {
  await nextTick();
  keys.value = keyMap;
  infoData.value = data;
};
const initData = async () => {};
defineExpose({ setForm });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
