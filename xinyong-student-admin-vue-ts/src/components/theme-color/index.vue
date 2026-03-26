<template>
  <div>
    <el-color-picker
      v-model="primary"
      :predefine="ConfigData.THEME_COLOR"
      @change="changePrimary"
    />
  </div>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { store } from "@/utils/store";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import { ConfigData } from "@/config";
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();
/**
 * 修改主题颜色
 */
const primary = computed(() => store.global.primary);

const changePrimary = (val: string | null) => {
  if (!val) {
    val = ConfigData.DEFAULT_PRIMARY;
    tt.msg.success(`主题颜色已重置为 ${ConfigData.DEFAULT_PRIMARY}`);
  }
  // 计算主题颜色变化
  document.documentElement.style.setProperty("--el-color-primary", val);
  document.documentElement.style.setProperty(
    "--el-color-primary-dark-2",
    store.global.isDark
      ? `${tt.common.getLightColor(val, 0.2)}`
      : `${tt.common.getDarkColor(val, 0.3)}`,
  );
  for (let i = 1; i <= 9; i++) {
    const primaryColor = store.global.isDark
      ? `${tt.common.getDarkColor(val, i / 10)}`
      : `${tt.common.getLightColor(val, i / 10)}`;
    document.documentElement.style.setProperty(
      `--el-color-primary-light-${i}`,
      primaryColor,
    );
  }
  store.global.primary = val;
};
const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
