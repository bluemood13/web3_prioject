<template>
  <el-footer v-show="store.global.showFooter" style="padding: 0">
    <div
      class="cus-border-color cus-label-color fixed bottom-0 mt-[40px] flex h-[40px] w-full items-center justify-center border-t-[1px] text-sm tracking-wider"
    >
      {{ currentDate }} © {{ title }}
    </div>
  </el-footer>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { store } from "@/utils/store";

import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});

const title = ref(import.meta.env.VITE_TITLE);

const currentDate = ref(new Date());

const emits = defineEmits<{}>();
const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
