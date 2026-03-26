<template>
  <el-dialog v-model="vis" title="余额" append-to-body>
    <CusForm :data="data" ref="formRef"> </CusForm>
    <template #footer>
      <el-button type="primary" @click="addHandler">充值</el-button>
    </template>
  </el-dialog>
</template>
<script setup>
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";

import { getCurrentInstance, onMounted } from "vue";
const app = getCurrentInstance().appContext.config.globalProperties;
const props = defineProps({});
const emits = defineEmits([]);
const vis = ref(false);
const formRef = ref();
const data = [
  {
    name: "id",
    default: "",
    rules: false,
  },
  {
    type: "double",
    name: "money",
    default: 1,
    min: 1,
    label: "充值金额",
    rules: true,
  },
];
const setForm = async (row) => {
  await nextTick();
  formRef.value.formData["id"] = row["id"];
};

const addHandler = async () => {
  if (!formRef.value.formRef) return;
  await formRef.value.formRef.validate(async (valid, fields) => {
    if (valid) {
      console.log(formRef.value.formData);
      const result = await api.sys.user.chongzhi({
        ...formRef.value.formData,
      });
      tt.msg.success(result.message);
      vis.value = false;
      formRef.value.resetForm();
      emits("refresh");
    }
  });
};
const initData = async () => {};
defineExpose({ vis, setForm });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
