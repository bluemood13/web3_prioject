import { hash } from 'crypto';
<template>
  <el-dialog v-model="vis" title="区块链" append-to-body width="75%">
    <component :is="linkTemplateMap[linkTemplate]" ref="linkRef"></component>
  </el-dialog>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { useGlobalStore } from "@/stores/modules/global";
import {
  getCurrentInstance,
  onMounted,
  ref,
  computed,
  watch,
  ComponentCustomProperties,
} from "vue";
import LinkTableCom from "./components/link-table.vue";
import LinkLineCom from "./components/link-line.vue";
const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {
  //   demo?: string;
}
const props = withDefaults(defineProps<Props | {}>(), {
  //   demo: '',
});
const emits = defineEmits<{
  //   (event: 'refresh'): void;
}>();
const globalStore = useGlobalStore();

const vis = ref(false);
const linkTemplate = computed(() => {
  return globalStore.linkShowTemplate;
});
// 模板类型
const linkTemplateMap = {
  table: LinkTableCom,
  line: LinkLineCom,
};

const linkRef = ref();
/**
 * 重新赋值表单
 * @param {*} linkDataList 区块链数据
 * @param {*} hash 当前区块链的hash
 * @param {*} col 表格对应的字段和prop
 */
const setForm = async (
  linkDataList: Array<any>,
  hash: string,
  col: Array<any>,
) => {
  await nextTick();
  linkRef.value.setForm(linkDataList, hash, col);
};
const initData = async () => {};
defineExpose({ vis, setForm });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
