<template>
  <div></div>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
