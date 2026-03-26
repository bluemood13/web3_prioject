<template>
  <el-button
    :icon="props.modelValue"
    @click="vis = true"
    :disabled="props.disabled"></el-button>
  <div class="px-5" v-if="!props.disabled">
    <CusBtn type="danger" plain @click="clearIconHandler">清除图标</CusBtn>
  </div>
  <el-dialog v-model="vis" title="图标选择" width="700" append-to-body>
    <div class="pb-5">
      <el-input
        v-model="search"
        placeholder="请输入图标"
        clearable
        @input="changeHandler"></el-input>
    </div>
    <div class="grid grid-cols-4 gap-4 overflow-y-scroll h-80">
      <div
        class="p-5 m-2 flex justify-center items-center flex-col border-2 hover:border-yellow-300 rounded-xl"
        v-for="(item, index) in iconList"
        :key="index"
        @click="chooseHandler(item)">
        <div>
          <CusIcon :icon="item" :size="21"></CusIcon>
        </div>
        <div class="text-xs mt-3">{{ item }}</div>
      </div>
    </div>
  </el-dialog>
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
// 图标库
import * as ElementPlusIconsVue from '@element-plus/icons-vue';

const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {
  //图标字符串
  modelValue?: string;
  // 是否禁止点击
  disabled?: boolean;
}
const props = withDefaults(defineProps<Props>(), {
  modelValue: '',
  disabled: false,
});
const emits = defineEmits<{
  (event: 'update:modelValue', icon: string): void;
}>();

const vis = ref(false);
// 搜索图标
const search = ref();
// 图标列表
const iconList = ref<any>([]);
const searchData = ref([]);

/**
 * 清除图标字符串
 */
const clearIconHandler = () => {
  emits('update:modelValue', '');
};
/**
 * 图标选择事件
 * @param item
 */
const chooseHandler = (item: string) => {
  emits('update:modelValue', item);
  vis.value = false;
};

/**
 * 搜索事件
 */
const changeHandler = () => {
  if (search.value == '') {
    initData();
  } else {
    iconList.value = searchData.value.filter((x: string) => {
      return x.toLowerCase().indexOf(search.value.toLowerCase()) != -1;
    });
  }
};
const setForm = async (data: any) => {};
// 加载所有图标
const initData = async () => {
  iconList.value = [];
  searchData.value = [];
  Object.entries(ElementPlusIconsVue).map(([x, component]) => {
    iconList.value.push(x);
  });
  searchData.value = iconList.value;
};
defineExpose({ vis, setForm });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
