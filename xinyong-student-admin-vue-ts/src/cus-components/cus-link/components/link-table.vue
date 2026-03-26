<template>
  <el-table
    :data="tableData"
    ref="tableRef"
    :style="{ width: '100%' }"
    max-height="480"
    stripe
  >
    <el-table-column type="selection" width="55" />
    <el-table-column label="标识">
      <template #default="scope">
        <el-tag type="danger" v-if="scope.row.hash == currentHash">
          当前节点
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column label="交易Hash" prop="tx"> </el-table-column>
    <el-table-column label="区块Hash" prop="hash"> </el-table-column>
    <el-table-column
      v-for="item in tableCol"
      :label="item.label"
      :prop="item.prop"
    >
      <template #default="scope">
        <el-image
          v-if="
            item.dataType == 'file' && tt.common.isImg(scope.row[item.prop])
          "
          style="width: 100%; height: 100px"
          :src="scope.row[item.prop]"
          fit="contain"
        />
        <el-link
          v-else-if="
            item.dataType == 'file' && !tt.common.isImg(scope.row[item.prop])
          "
          :href="scope.row[item.prop]"
          type="primary"
          :underline="false"
          >下载</el-link
        >
        <el-tag v-else>{{ scope.row[item.prop] }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column label="创建时间" prop="createTime" />
  </el-table>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();
const tableRef = ref();
const tableData = ref<any>([]);
const currentHash = ref("");
const tableCol = ref<any>([]);
const setForm = (linkDataList: Array<any>, hash: string, col: Array<any>) => {
  tableData.value = linkDataList;
  currentHash.value = hash;
  tableCol.value = col;
};
const initData = async () => {};
defineExpose({ setForm });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
