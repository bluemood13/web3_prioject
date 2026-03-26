<template>
  <el-container direction="vertical">
    <HeaderCom></HeaderCom>
    <MainCom></MainCom>
    <FooterCom></FooterCom>
  </el-container>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import MainCom from "./components/main/index.vue";
import FooterCom from "./components/footer/index.vue";
import HeaderCom from "./components/headers/index.vue";

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
