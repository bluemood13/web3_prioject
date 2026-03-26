<template>
  <div class="back-images"></div>
  <div class="h-screen w-screen flex justify-center items-center">
    <div
      class="w-[80%] h-[80%] flex justify-center items-center flex-1 flex-col">
      <p class="text-9xl font-bold">404</p>
      <p class="text-xl font-bold">抱歉，您访问的页面不存在~🤷‍♂️🤷‍♀️</p>
      <div class="pt-4 ">
        <CusBtn @click="app?.$router.back()">点击返回上一页</CusBtn>
        <CusBtn type="danger" @click="app?.$router.push({ path: '/' })"
          >返回首页</CusBtn
        >
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { getCurrentInstance, onMounted } from 'vue';
const app = getCurrentInstance()?.appContext.config.globalProperties;

const props = defineProps({});
const emits = defineEmits([]);

const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped>
.back-images {
  background-image: url(@/assets/logo.svg);
  background-size: 100% 100%;
  height: 80vh; /* 设置高度为 80% 的视口高度 */
  width: 80vw; /* 设置宽度为 80% 的视口宽度 */
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%); /* 使元素水平和垂直居中 */
  z-index: -1;
  opacity: 0.5;
}
</style>
