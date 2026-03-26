<template>
  <div class="mb-6 flex w-full items-stretch justify-between gap-6">
    <!-- 钱包地址 -->
    <div
      class="flex flex-1 items-center rounded-xl bg-blue-50 p-6 shadow-sm transition-shadow hover:shadow-md"
    >
      <el-icon class="mr-8 text-[80px] text-blue-500" size="25"
        ><Wallet
      /></el-icon>
      <div class="flex-1">
        <div class="mb-2 text-base font-medium text-gray-500">钱包地址</div>
        <div
          class="text-lg leading-snug font-bold break-all text-gray-800"
          :title="blockInfo?.account"
        >
          {{ blockInfo?.account || "Loading..." }}
        </div>
      </div>
    </div>

    <!-- 区块数量 -->
    <div
      class="flex flex-1 items-center rounded-xl bg-green-50 p-6 shadow-sm transition-shadow hover:shadow-md"
    >
      <el-icon class="mr-8 text-[80px] text-green-500" size="25"
        ><Box
      /></el-icon>
      <div>
        <div class="mb-2 text-base font-medium text-gray-500">区块数量</div>
        <div class="text-2xl font-bold text-gray-800">
          {{ blockInfo?.blockNum || 0 }}
        </div>
      </div>
    </div>

    <!-- Gas价格 -->
    <div
      class="flex flex-1 items-center rounded-xl bg-orange-50 p-6 shadow-sm transition-shadow hover:shadow-md"
    >
      <el-icon class="mr-8 text-[80px] text-orange-500" size="25"
        ><Money
      /></el-icon>
      <div>
        <div class="mb-2 text-base font-medium text-gray-500">Gas价格</div>
        <div class="text-2xl font-bold text-gray-800">
          {{ blockInfo?.gasPrice || 0 }}
        </div>
      </div>
    </div>

    <!-- Gas上限 -->
    <div
      class="flex flex-1 items-center rounded-xl bg-purple-50 p-6 shadow-sm transition-shadow hover:shadow-md"
    >
      <el-icon class="mr-8 text-[80px] text-purple-500" size="25"
        ><Odometer
      /></el-icon>
      <div>
        <div class="mb-2 text-base font-medium text-gray-500">Gas上限</div>
        <div class="text-2xl font-bold text-gray-800">
          {{ blockInfo?.gasLimit || 0 }}
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();
const blockInfo = ref<any>({});
const initData = async () => {};
defineExpose({ blockInfo });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
