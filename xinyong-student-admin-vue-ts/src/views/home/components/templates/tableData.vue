<template>
  <div>
    <el-table
      :data="infoData"
      ref="tableRef"
      stripe
      :style="{ width: '100%' }"
      max-height="580"
      element-loading-text="加载数据中"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="id" label="编号" width="80" />

      <template v-for="(item, index) in configData" :key="index">
        <template v-if="tt.common.haveImgStr(item.prop)">
          <el-table-column :prop="item.prop" :label="item.label">
            <template #default="scope">
              <el-image
                style="width: 100%; height: 180px"
                :src="tt.common.getValue(scope.row, item.prop)"
                fit="contain"
              >
              </el-image>
            </template>
          </el-table-column>
        </template>
        <template v-else>
          <el-table-column :prop="item.prop" :label="item.label">
            <template #default="scope">
              {{ tt.common.getValue(scope.row, item.prop) }}
            </template>
          </el-table-column>
        </template>
      </template>
      <el-table-column label="操作" :fixed="false" width="200">
        <template #default="scope">
          <div class="flex flex-wrap items-center justify-around">
            <!-- 使用 v-for 遍历插槽的每个子元素，分别包裹容器 -->
            <template
              v-for="(child, index) in $slots.default({ scope })"
              :key="index"
            >
              <div class="flex-1 p-1">
                <!-- 渲染插槽的每个子元素 -->
                <component :is="child" />
              </div>
            </template>
          </div>
        </template>
      </el-table-column>
    </el-table>
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
