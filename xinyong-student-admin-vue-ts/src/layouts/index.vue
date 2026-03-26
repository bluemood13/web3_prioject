<template>
  <component :is="LayoutComponents[layout]" />
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { store } from "@/utils/store";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import LayoutVertical from "./layout-vertical/index.vue";
import LayoutTransverse from "./layout-transverse/index.vue";
import LayoutCol from "./layout-col/index.vue";
const route = useRoute();
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();
// 系统布局
const LayoutComponents = {
  vertical: LayoutVertical,
  transverse: LayoutTransverse,
  col: LayoutCol,
};
// 动态的进行变化布局
const layout = computed(() => store.global.layout);

const initData = async () => {
  // 清除tab页面
  store.tab.closeOtherTab(route.fullPath);
  // 显示公告
  const res = await api.sys.info.getNew({});
  tt.notif.success(res.data.info, "最新公告", "top-left");
  store.info.info = res.data.info;
};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
