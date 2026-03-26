<template>
  <!-- 标签页 -->
  <TabListCom></TabListCom>
  <!-- 页面  -->
  <el-main class="w-full">
    <router-view v-slot="{ Component, route }">
      <transition
        appear
        :name="store.global.animation"
        @enter="enter"
        mode="out-in"
      >
        <keep-alive :include="store.keep.keepList">
          <component
            :is="createComponentWrapper(Component, route)"
            v-if="store.global.isRefresh"
            :key="route.fullPath"
          />
        </keep-alive>
      </transition>
    </router-view>
  </el-main>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { store } from "@/utils/store";
// import TabListCom from "@/components/tab-list/components/1111.vue";
import TabListCom from "@/components/tab-list/index.vue";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

// 动画效果
const animationName = computed(() => {
  return store.global.animation;
});
// 刷新动画效果
watch(animationName, async () => {
  await nextTick();
});
const enter = (el: any, done: any) => {
  const originalWidth = el.style.width;
  const originalHeight = el.style.height;
  el.classList.add("animate__animated");
  el.classList.add("animate__faster");
  el.classList.add(animationName.value);
  el.addEventListener("animationend", () => {
    el.style.width = originalWidth;
    el.style.height = originalHeight;
    done();
  });
};

const leave = (el: any, done: any) => {
  const originalWidth = el.style.width;
  const originalHeight = el.style.height;
  el.classList.add("animate__animated");
  el.classList.add("animate__faster");
  el.classList.add(animationName.value.replace("In", "Out"));
  el.addEventListener("animationend", () => {
    el.style.width = originalWidth;
    el.style.height = originalHeight;
    done();
  });
};

// 解决详情页 keep-alive 问题, 比如详情跟着参数的话，不同参数的缓存就会加载的不一样
const wrapperMap = new Map();
const createComponentWrapper = (component: any, route: any) => {
  if (!component) return;
  const wrapperName = route.fullPath;
  let wrapper = wrapperMap.get(wrapperName);
  if (!wrapper) {
    wrapper = { name: wrapperName, render: () => h(component) };
    wrapperMap.set(wrapperName, wrapper);
  }
  return h(wrapper);
};
const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
