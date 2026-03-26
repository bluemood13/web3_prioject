<template>
  <el-container class="el-container">
    <!-- 侧边栏 -->
    <Aside></Aside>
    <el-container direction="vertical">
      <Headers></Headers>
      <Main></Main>
      <Footer></Footer>
    </el-container>
  </el-container>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";

import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import Headers from "./components/headers/index.vue";
import Main from "./components/main/index.vue";
import Footer from "./components/footer/index.vue";
import Aside from "./components/aside/index.vue";

const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

const asideWidth = ref();
const headerHeight = ref();
const footerHeight = ref();

const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
