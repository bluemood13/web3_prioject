<template>
  <el-button
    :size="props.size"
    :plain="props.plain"
    :type="props.type"
    @click="clickHandler"
  >
    <slot></slot>
  </el-button>
</template>
<script setup lang="ts">
import type { ButtonType } from "element-plus";
import { tt } from "@/utils/tool";

const app: any = getCurrentInstance()?.appContext.config.globalProperties;

interface CusBtnProps {
  // 按钮类型
  type?: ButtonType;
  // 按钮大小
  size?: "large" | "default" | "small";
  // 是否模糊显示
  plain?: boolean;
  // 是否需要弹窗
  showTip?: boolean;
  // 提示信息
  tip?: string;
  // 是否有图标
  icon?: string;
}

// 定义 emit 事件类型
interface CusBtnEmitEvents {
  (event: "click"): void;
}

const props = withDefaults(defineProps<CusBtnProps>(), {
  type: "primary",
  size: "default",
  plain: false,
  showTip: false,
  tip: "是否删除",
  icon: "",
});

// 定义 emit
const emits = defineEmits<CusBtnEmitEvents>();

const clickHandler = tt.common.debounce(() => {
  // 是否需要弹窗提示
  if (props.showTip) {
    tt.msg.confirm(props.tip, () => {
      emits("click");
    });
  } else {
    emits("click");
  }
});

defineExpose({});
onMounted(() => {});
</script>
<style lang="scss" scoped></style>
./data
