<template>
  <el-dialog v-model="vis" title="密码修改" append-to-body>
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-suffix=":"
      label-width="auto"
    >
      <el-form-item label="旧密码" prop="password">
        <el-input
          v-model="formData.password"
          type="password"
          placeholder="请输入旧密码"
        >
          <template #append></template>
        </el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input
          v-model="formData.newPassword"
          type="password"
          placeholder="请输入新密码"
        />
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input
          v-model="formData.confirmPassword"
          type="password"
          placeholder="请再次输入新密码"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="updHandler(formRef)">修改</el-button>
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
const data = {
  id: "",
  password: "",
  newPassword: "",
  confirmPassword: "",
};
const formData = ref({ ...data });
const formRef = ref();
const formRules = ref({
  password: [{ required: true, message: "旧密码不能为空", trigger: "blur" }],
  newPassword: [{ required: true, message: "新密码不能为空", trigger: "blur" }],
  confirmPassword: [
    { required: true, message: "确认密码不能为空", trigger: "blur" },
    {
      validator(rule, value, callback) {
        if (!value) return callback(new Error("请再次输入新密码"));
        if (value !== formData.value.newPassword)
          return callback(new Error("两次输入的密码不一致"));
        callback();
      },
    },
  ],
});
const resetForm = () => {
  formData.value = { ...data };
};
const setForm = (data) => {
  formData.value.id = data.id;
};
const updHandler = async (formEl) => {
  if (!formEl) return;
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      console.log(formData.value);
      const result = await api.sys.user.changePassword({ ...formData.value });
      tt.msg.success(result.message);
      vis.value = false;
      resetForm();
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
