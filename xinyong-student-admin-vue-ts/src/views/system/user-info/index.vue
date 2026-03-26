<template>
  <div>
    <component :is="userInfoComponent"></component>
  </div>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { store } from "@/utils/store";
import {
  getCurrentInstance,
  onMounted,
  ref,
  computed,
  watch,
  ComponentCustomProperties,
} from "vue";
import { useUserInfoTemplateHook } from "@/hooks/useUserInfoTemplateHook";

const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {
  //   demo?: string;
}
const props = withDefaults(defineProps<Props | {}>(), {
  //   demo: '',
});
const emits = defineEmits<{
  //   (event: 'refresh'): void;
}>();

const userInfoTemplateData = useUserInfoTemplateHook();

const userInfoComponent = computed(() => {
  return userInfoTemplateData[store.global.userInfoTemplate];
});

const initData = async () => {};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
