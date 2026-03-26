<template>
  <el-timeline style="max-height: 640px; overflow-y: auto">
    <el-timeline-item
      v-for="(activity, index) in lineData"
      :key="index"
      :timestamp="activity.createTime"
      placement="top"
    >
      <el-card>
        <div class="pb-3" v-if="activity.hash == currentHash">
          <el-tag type="danger"> 当前节点 </el-tag>
        </div>
        <el-descriptions :column="4" border>
          <el-descriptions-item>
            <template #label>
              <div>交易hash</div>
            </template>
            <div style="width: 100px; word-break: break-all">
              {{ activity.tx }}
            </div>
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div>区块hash</div>
            </template>
            <div style="width: 100px; word-break: break-all">
              {{ activity.hash }}
            </div>
          </el-descriptions-item>
          <el-descriptions-item v-for="(item, index) in colData" :key="index">
            <template #label>
              <div>{{ item.label }}</div>
            </template>
            <el-image
              v-if="
                item.dataType == 'file' && tt.common.isImg(activity[item.prop])
              "
              style="width: 100%; height: 100px"
              :src="activity[item.prop]"
              fit="contain"
            />
            <el-link
              v-else-if="
                item.dataType == 'file' && !tt.common.isImg(activity[item.prop])
              "
              :href="activity[item.prop]"
              type="primary"
              :underline="false"
              >下载</el-link
            >
            <div v-else>
              {{ activity[item.prop] }}
            </div>
          </el-descriptions-item>
        </el-descriptions>
      </el-card>
    </el-timeline-item>
  </el-timeline>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

const lineData = ref([]);
const currentHash = ref("");
const colData = ref([]);

const setForm = (linkDataList: Array<any>, hash: string, col: Array<any>) => {
  lineData.value = linkDataList;
  currentHash.value = hash;
  colData.value = col;
};
const initData = async () => {};
defineExpose({ setForm });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
