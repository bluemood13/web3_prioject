<template>
  <!-- 全局配置 -->
  <el-config-provider
    :size="config.size"
    :button="config.button"
    :message="config.message"
    :locale="config.locale"
  >
    <RouterView></RouterView>
  </el-config-provider>
</template>

<script setup>
import { RouterView } from "vue-router";
import zhCn from "element-plus/dist/locale/zh-cn";
import "dayjs/locale/zh-cn";
const config = ref({
  // 大小默认
  size: "default",
  button: {
    // 自动填充空格在按钮字符之间
    autoInsertSpace: true,
  },
  // 消息最多5个
  message: {
    max: 5,
  },
  // 中文主题，主要是日期组件
  locale: zhCn,
});
</script>

<style scoped lang="scss"></style>
