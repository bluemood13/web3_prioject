<template>
  <div class="flex h-[700px] w-full items-center justify-start p-10">
    <div
      class="cus-border-color mr-10 flex h-full w-1/4 flex-col items-center justify-start rounded-xl border-1 p-6 shadow-xl"
    >
      <div class="relative">
        <!-- 头像 -->
        <img
          :src="hooks.commonHooks.getHeadImgHook.value"
          alt="头像"
          class="h-32 w-32 rounded-full border-4 border-blue-200 object-cover shadow-md transition duration-300 hover:scale-105"
        />
      </div>
      <!-- 用户名 -->
      <div class="mt-4 flex w-full items-center justify-between">
        <span class="cus-label-color">用户名：</span>
        <span class="cus-text-color">{{ formData.username }}</span>
      </div>
      <!-- 余额 -->
      <div
        class="mt-4 flex w-full items-center justify-between"
        v-if="store.global.showUserMoney"
      >
        <span class="cus-label-color">余额：</span>
        <span class="cus-text-color">{{ formData.money }}</span>
      </div>
      <!-- 角色信息 -->
      <div class="mt-4 flex w-full items-center justify-between">
        <span class="cus-label-color">角色：</span>
        <div class="flex flex-wrap items-center gap-2">
          <el-tag
            v-for="item in formData.roles"
            :key="item.id"
            class="rounded border-none bg-blue-100 px-2 py-1 text-blue-700"
          >
            {{ item.roleName }}
          </el-tag>
        </div>
      </div>
    </div>
    <!-- 表单部分 -->
    <div class="w-3/4">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-suffix=":"
        label-width="auto"
      >
        <el-form-item label="头像" prop="avatar">
          <CusUploadFile v-if="uploadShow" v-model="imgFileList" :limit="1">
            <template #default="scope">
              <img
                class="h-[100px] w-[100px] object-cover"
                v-if="tt.common.isImg(scope.file.name)"
                :src="scope.file.url"
              />
            </template>
          </CusUploadFile>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formData.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="formData.sex" placeholder="请选择性别">
            <el-option label="男" :value="1" />
            <el-option label="女" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="formData.phone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="formData.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="介绍" prop="content">
          <el-input
            type="textarea"
            v-model="formData.content"
            placeholder="请输入介绍"
          />
        </el-form-item>
        <el-form-item
          label="余额"
          prop="money"
          v-if="store.global.showUserMoney"
        >
          <el-input v-model="formData.money" disabled />
        </el-form-item>

        <el-form-item>
          <div class="flex w-full items-center justify-end">
            <CusBtn type="danger" @click="changePasswordHandler"
              >修改密码</CusBtn
            >
            <CusBtn
              type="warning"
              @click="chongzhiHan"
              v-if="store.global.showUserMoney"
              >充值</CusBtn
            >

            <CusBtn type="primary" @click="updHandler">更新</CusBtn>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <!-- 修改密码 -->
    <ChangePasswordDialogCom
      ref="changePasswordDialogRef"
    ></ChangePasswordDialogCom>
    <!-- 充值 -->
    <MoneyCom ref="moneyRef" @refresh="initData"> </MoneyCom>
  </div>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { store } from "@/utils/store";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import ChangePasswordDialogCom from "@/bus-components/user-info/components/changePassword/index.vue";
import MoneyCom from "@/bus-components/user-info/components/money/index.vue";
import { hooks } from "@/hooks";
import { useUserInfoHook } from "../../hooks/useUserInfoHook";
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{}>();

const {
  formData,
  formRules,
  formRef,
  moneyRef,
  changePasswordDialogRef,
  chongzhiHan,
  changePasswordHandler,
  imgFileList,
  uploadShow,
  initUpload,
} = useUserInfoHook();

// 更新事件
const updHandler = async () => {
  console.log(imgFileList.value);
  if (!formRef.value) return;
  await formRef.value.validate(async (valid: any, fields: any) => {
    if (valid) {
      if (imgFileList.value.length > 0) {
        formData.value.avatar = imgFileList.value[0].url;
      }
      console.log(formData.value);
      const result = await api.sys.user.updUser({
        ...formData.value,
        roleIds: formData.value.roles.map((item: any) => item.id),
      });

      // 修改store的user部分
      const res = await api.sys.user.getUserInfo({});
      console.log(res.data);
      console.log(111222);
      store.user.userInfo.avatar = res.data.avatar;
      store.user.userInfo.name = res.data.name;
      store.user.userInfo.content = res.data.content;
      store.user.userInfo.email = res.data.email;
      store.user.userInfo.phone = res.data.phone;
      store.user.userInfo.sex = res.data.sex;

      tt.msg.success(result.message);
      await initData();
    }
  });
};

const initData = async () => {
  const res = await api.sys.user.getUserInfo({});
  formData.value = res.data;
  //初始化上传的图片
  initUpload();
};
defineExpose({});
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
