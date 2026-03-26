<template>
  <div>
    <div
      class="fixed right-8 bottom-8 z-50 cursor-pointer opacity-50 transition-transform duration-500 hover:opacity-100"
      :class="{ 'animate-spin-slow': isHover }"
      @click="handleClick"
      @mouseenter="isHover = true"
      @mouseleave="isHover = false"
    >
      <div
        class="flex items-center justify-center rounded-3xl border border-gray-200 bg-white p-3 shadow-lg dark:border-gray-700 dark:bg-gray-800"
      >
        <el-icon :size="20">
          <Setting />
        </el-icon>
      </div>
    </div>

    <SystemSetting ref="systemSettingRef"></SystemSetting>
  </div>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import { Setting } from "@element-plus/icons-vue";
import SystemSetting from "@/components/system-setting/index.vue";
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

const isHover = ref(false);

const systemSettingRef = ref();
const handleClick = () => {
  systemSettingRef.value.vis = true;
  //   tt.msg.info("打开系统设置");
};

const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped>
@keyframes spin-slow {
  100% {
    transform: rotate(360deg);
  }
}
.animate-spin-slow {
  animation: spin-slow 1s linear infinite;
}
</style>
