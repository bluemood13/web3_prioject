<template>
  <div class="flex flex-wrap items-center justify-around">
    <div class="w-1/2 p-5" v-for="item in infoData">
      <CusCard :show-title="false">
        <template v-for="(configItem, index) in configData" :key="index">
          <template v-if="tt.common.haveImgStr(configItem.prop)">
            <div class="p-1">
              <el-image
                style="width: 100%; height: 180px"
                :src="tt.common.getValue(item, configItem.prop)"
                fit="contain"
              >
              </el-image>
            </div>
          </template>
          <template v-else>
            <div class="flex items-start p-1">
              <el-tag class="w-24 text-center">{{ configItem.label }}:</el-tag>
              <span class="px-2 text-sm">{{
                tt.common.getValue(item, configItem.prop)
              }}</span>
            </div>
          </template>
        </template>
        <div class="flex items-start justify-end pt-2">
          <div class="flex flex-wrap items-start justify-around">
            <!-- 使用 v-for 遍历插槽的每个子元素，分别包裹容器 -->
            <template
              v-for="(child, index) in $slots.default({ scope: { row: item } })"
              :key="index"
            >
              <div class="flex-1 p-1">
                <!-- 渲染插槽的每个子元素 -->
                <component :is="child" />
              </div>
            </template>
          </div>
        </div>
      </CusCard>
    </div>
  </div>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";

const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props>(), {});
const emits = defineEmits<{
  // (event: "refresh"): void;
}>();

const infoData = ref<any>([]);
const configData = ref<any>([]);

const setData = async (data: any) => {
  await nextTick();
  infoData.value = data.data;
  configData.value = data.config;
};
const initData = async () => {};
defineExpose({ setData });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
