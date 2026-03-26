<template>
  <div class="flex w-full flex-1 items-center justify-start">
    <div class="flex-1 px-10">
      <el-carousel height="300px" :interval="3000" type="card">
        <el-carousel-item v-for="(item, index) in carouselImages" :key="index">
          <el-image
            :src="item.url"
            fit="cover"
            class="h-full w-full rounded-lg"
            :alt="item.title"
          />
        </el-carousel-item>
      </el-carousel>
    </div>
    <CusCard title="系统区块链信息">
      <div class="flex flex-col">
        <p class="my-1 text-sm text-gray-700">
          <el-tag class="mr-4 font-bold">钱包地址:</el-tag>
          <span>{{ blockInfo?.account }}</span>
        </p>
        <p class="my-1 text-sm text-gray-700">
          <el-tag class="mr-4 font-bold">区块数量:</el-tag>
          <span>{{ blockInfo?.blockNum }}</span>
        </p>
        <p class="my-1 text-sm text-gray-700">
          <el-tag class="mr-4 font-bold">Gas价格:</el-tag>
          <span>{{ blockInfo?.gasPrice }}</span>
        </p>
        <p class="my-1 text-sm text-gray-700">
          <el-tag class="mr-4 font-bold">Gas上限:</el-tag>
          <span>{{ blockInfo?.gasLimit }}</span>
        </p>
      </div>
    </CusCard>
  </div>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import img1 from "@/assets/img/img1.png";
import img2 from "@/assets/img/img2.png";
import img3 from "@/assets/img/img3.png";
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();
const carouselImages = ref([
  { url: img1, title: "轮播图1" },
  { url: img2, title: "轮播图2" },
  { url: img3, title: "轮播图3" },
]);
const blockInfo = ref<any>({});

const initData = async () => {};
defineExpose({ blockInfo });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
