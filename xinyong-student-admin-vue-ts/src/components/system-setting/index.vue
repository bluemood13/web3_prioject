<template>
  <el-drawer
    v-model="vis"
    title="系统设置"
    direction="rtl"
    size="380px"
    :append-to-body="true"
    :z-index="999"
  >
    <!-- 系统设置 -->
    <el-divider :content-position="contentPosition"> 布局设置 </el-divider>
    <div class="flex flex-col items-start justify-start">
      <!-- 设置头部高度 -->
      <div class="my-2 flex w-full flex-1 items-center justify-between">
        <div class="pr-2 text-sm text-gray-500">布局选择:</div>
        <div class="flex w-1/2 items-center justify-center">
          <el-select
            v-model="globalStore.layout"
            placeholder="请选择布局方式"
            filterable
          >
            <el-option label="横向布局" value="vertical" />
            <el-option label="纵向布局" value="transverse" />
            <el-option label="多列布局" value="col" />
          </el-select>
        </div>
      </div>
    </div>
    <!-- 系统设置 -->
    <el-divider :content-position="contentPosition"> 基础设置 </el-divider>

    <div class="flex flex-col items-start justify-start">
      <!-- 设置头部高度 -->
      <div class="my-2 flex w-full flex-1 items-center justify-between">
        <div class="pr-2 text-sm text-gray-500">头部高度:</div>
        <div class="flex w-1/2 items-center justify-center">
          <el-input-number
            v-model="globalStore.headerHeight"
            :min="60"
            :max="100"
            :step="1"
          />
        </div>
      </div>
      <!-- 侧边宽度 -->
      <div class="my-2 flex w-full flex-1 items-center justify-between">
        <div class="pr-2 text-sm text-gray-500">侧边宽度:</div>
        <div class="flex w-1/2 items-center justify-center">
          <el-input-number
            v-model="globalStore.asideWidth"
            :min="300"
            :max="500"
            :step="1"
          />
        </div>
      </div>

      <!-- 是否显示底部 -->
      <div class="my-2 flex w-full flex-1 items-center justify-between">
        <div class="pr-2 text-sm text-gray-500">底部显示:</div>
        <div class="flex w-1/2 items-center justify-center">
          <el-switch
            v-model="globalStore.showFooter"
            inline-prompt
            active-text="是"
            inactive-text="否"
          />
        </div>
      </div>

      <!-- 是否显示用户金额 -->
      <div class="my-2 flex w-full flex-1 items-center justify-between">
        <div class="pr-2 text-sm text-gray-500">用户金额显示:</div>
        <div class="flex w-1/2 items-center justify-center">
          <el-switch
            v-model="globalStore.showUserMoney"
            inline-prompt
            active-text="是"
            inactive-text="否"
          />
        </div>
      </div>

      <!-- 首页数据显示 -->
      <div class="my-2 flex w-full flex-1 items-center justify-between">
        <div class="pr-2 text-sm text-gray-500">首页数据显示:</div>
        <div class="flex w-1/2 items-center justify-center">
          <el-select
            v-model="globalStore.HomeDataShow"
            placeholder="请选择显示方式"
            filterable
          >
            <el-option label="卡片" value="card" />
            <el-option label="表格" value="table" />
          </el-select>
        </div>
      </div>

      <!-- 首页头部显示 -->
      <div class="my-2 flex w-full flex-1 items-center justify-between">
        <div class="pr-2 text-sm text-gray-500">首页头部显示:</div>
        <div class="flex w-1/2 items-center justify-center">
          <el-select
            v-model="globalStore.HomeHeadShow"
            placeholder="请选择显示方式"
            filterable
          >
            <el-option label="图片" value="img" />
            <el-option label="标签" value="info" />
          </el-select>
        </div>
      </div>
      <!-- 模板设置 -->
      <el-divider :content-position="contentPosition"> 模板设置 </el-divider>

      <!-- 登录模板 -->
      <div class="my-2 flex w-full flex-1 items-center justify-between">
        <div class="pr-2 text-sm text-gray-500">登录模板:</div>
        <div class="flex w-1/2 items-center justify-center">
          <el-select
            v-model="globalStore.loginTemplate"
            placeholder="请选择登录模板"
            filterable
          >
            <el-option
              v-for="item in loginOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
      </div>
      <!-- 个人资料模板 -->
      <div class="my-2 flex w-full flex-1 items-center justify-between">
        <div class="pr-2 text-sm text-gray-500">个人资料模板:</div>
        <div class="flex w-1/2 items-center justify-center">
          <el-select
            v-model="globalStore.userInfoTemplate"
            placeholder="请选择个人资料模板"
            filterable
          >
            <el-option
              v-for="item in userInfoOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </div>
      </div>
      <!-- 区块链模板 -->
      <div class="my-2 flex w-full flex-1 items-center justify-between">
        <div class="pr-2 text-sm text-gray-500">区块链模板:</div>
        <div class="flex w-1/2 items-center justify-center">
          <el-select
            v-model="globalStore.linkShowTemplate"
            placeholder="请选择区块链模板"
            filterable
          >
            <el-option label="表格" value="table"> </el-option>
            <el-option label="时间线" value="line"> </el-option>
          </el-select>
        </div>
      </div>
    </div>
  </el-drawer>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import { useGlobalStore } from "@/stores/modules/global";

const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const globalStore = useGlobalStore();

const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();
const vis = ref(false);

const contentPosition = ref<any>("left");

// 登录下拉选项
const loginOptions = computed(() => {
  const modules: any = import.meta.glob(
    "@/bus-components/login/templates/**/index.vue",
    {
      eager: false,
    },
  );
  console.log("登录模板模块：", modules);
  const res = [];
  for (const path in modules) {
    const match: any =
      /bus-components\/login\/templates\/([^/]+)\/index\.vue/.exec(path);
    if (match.length > 1) {
      const word = match[1];
      res.push({
        label: `模板${word}`,
        value: word,
      });
    }
  }
  return res;
});

// 个人资料下拉选项
const userInfoOptions = computed(() => {
  const modules: any = import.meta.glob(
    "@/bus-components/user-info/templates/**/index.vue",
    {
      eager: false,
    },
  );
  const res = [];
  for (const path in modules) {
    const match: any =
      /bus-components\/user-info\/templates\/([^/]+)\/index\.vue/.exec(path);
    if (match.length > 1) {
      const word = match[1];
      res.push({
        label: `模板${word}`,
        value: word,
      });
    }
  }
  return res;
});

const initData = async () => {};
defineExpose({ vis });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
