<template>
  <el-dialog v-model="vis" title="" append-to-body>
    <el-form
      ref="formRef"
      @submit.prevent="false"
      :model="formData"
      :rules="formRules"
      label-suffix=":"
      :inline="false"
      label-width="auto"
      @keyup.enter.native="formHandler"
    >
      <el-form-item label="名称" prop="name">
        <el-input v-model="formData.name" disabled />
      </el-form-item>
      <el-form-item label="数量" prop="num">
        <el-input-number
          v-model="formData.num"
          :min="1"
          @change="numChangeHan"
        />
      </el-form-item>
      <el-form-item label="金额总数" prop="totalMoney">
        <el-input disabled v-model="formData.totalMoney" />
      </el-form-item>
    </el-form>
    <template #footer>
      <CusBtn type="primary" @click="formHandler">{{ props.btnTitle }}</CusBtn>
    </template>
  </el-dialog>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import {
  getCurrentInstance,
  onMounted,
  ref,
  computed,
  watch,
  ComponentCustomProperties,
} from "vue";
const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {
  btnTitle?: string;
}
const props = withDefaults(defineProps<Props>(), {
  btnTitle: "",
});
const emits = defineEmits<{
  (event: "refresh"): void;
}>();

const item = ref();
const numChangeHan = (data: any) => {
  formData.value.totalMoney = data * item.value.price;
};

const data = {
  name: "",
  num: 1,
  totalMoney: 0,
};
const formRef = ref();
const formData = ref({ ...data });
const formRules = ref({
  num: [{ required: true, message: "数量不能为空", trigger: "blur" }],
});

// 添加事件
const formHandler = async () => {
  await formRef.value.validate(async (valid: any, fields: any) => {
    if (valid) {
      console.log(formData.value);
      const result: Res = await api.dianshang.order.addOrder({
        ...formData.value,
      });
      tt.msg.success(result.message);
      vis.value = false;
      formRef.value.resetForm();
      emits("refresh");
    }
  });
};
const vis = ref(false);
const setForm = async (data: any) => {
  await nextTick();
  item.value = data;
  formData.value = {
    ...data,
    num: 1,
    totalMoney: data.price,
  };
};
const initData = async () => {};
defineExpose({ vis, setForm });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
