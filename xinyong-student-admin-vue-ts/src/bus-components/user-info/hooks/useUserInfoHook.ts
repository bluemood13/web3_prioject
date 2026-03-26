import {
  ref,
  onMounted,
  ComponentCustomProperties,
  getCurrentInstance,
} from "vue";
import { api } from "@/utils/api";
import { tt } from "@/utils/tool";
import { store } from "@/utils/store";

/**
 * 表单相关逻级
 * @returns
 */
const userInfoForm = () => {
  // 全局属性
  const app = getCurrentInstance()?.appContext.config
    .globalProperties as ComponentCustomProperties;
  const data: any = {
    id: "",
    username: "",
    password: "",
    name: "",
    sex: 1,
    avatar: "",
    money: 0,
    email: "",
    phone: "",
    content: "",
    roles: [],
  };
  // 表单数据
  const formData = ref<UserVo>({ ...data });
  // 表单验证规则
  const formRules = ref({
    name: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
    sex: [{ required: true, message: "性别不能为空", trigger: "blur" }],
  });
  const formRef = ref();

  return {
    formData,
    formRules,
    formRef,
  };
};

/**
 * 修改密码和充值相关逻辑
 * @returns
 */
const changePasswordAndMoney = () => {
  const { formData } = userInfoForm();
  const moneyRef = ref();
  const changePasswordDialogRef = ref();
  const chongzhiHan = () => {
    moneyRef.value.vis = true;
    moneyRef.value.setForm(formData.value);
  };
  const changePasswordHandler = () => {
    changePasswordDialogRef.value.vis = true;
    changePasswordDialogRef.value.setForm(formData.value);
  };
  return {
    moneyRef,
    changePasswordDialogRef,
    chongzhiHan,
    changePasswordHandler,
  };
};

/**
 * 图片上传相关
 */
const uploadFile = () => {
  const uploadShow = ref(false);
  const imgFileList = ref<any>([]);

  const initUpload = () => {
    uploadShow.value = false;
    if (!!store.user.userInfo.avatar) {
      imgFileList.value = [
        {
          url: store.user.userInfo.avatar,
          name: store.user.userInfo.avatar.split("/").pop(),
        },
      ];
    }
    uploadShow.value = true;
  };
  return {
    uploadShow,
    imgFileList,
    initUpload,
  };
};

// 最终返回数据
export const useUserInfoHook = () => {
  return {
    ...userInfoForm(),
    ...changePasswordAndMoney(),
    ...uploadFile(),
  };
};
