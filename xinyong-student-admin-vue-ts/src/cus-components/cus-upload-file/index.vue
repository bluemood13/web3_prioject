<template>
  <el-upload
    ref="uploadRef"
    v-model:file-list="fileList"
    name="files"
    :headers="header"
    :show-file-list="true"
    :before-upload="beforeAvatarUpload"
    :limit="props.limit"
    :multiple="props.limit > 1"
    :on-exceed="onExceedHan"
    :on-remove="onRemoveHan"
    :disabled="props.disabled"
    :http-request="(options: any) => uploadSuccessCusHandler(options)"
  >
    <div class="flex items-center justify-start">
      <div class="flex-1 pr-3" v-for="(file, index) in fileList" :key="index">
        <!-- <img
          class="h-[100px] w-[100px] object-cover"
          v-if="tt.common.isImg(file.name)"
          :src="file.url"
        /> -->
        <slot :file="file" :index="index"></slot>
      </div>
    </div>
    <el-button
      v-if="fileList?.length < props.limit"
      class="inline"
      type="primary"
      :disabled="props.disabled"
      >点击上传</el-button
    >
  </el-upload>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { useUserStore } from "@/stores/modules/user";
import { getCurrentInstance, onMounted, ref, computed, watch } from "vue";
import {
  genFileId,
  UploadFile,
  UploadFiles,
  UploadUserFile,
} from "element-plus";
const app: any = getCurrentInstance()?.appContext.config.globalProperties;
interface Props {
  limit: number;
  disabled?: boolean;
  modelValue?: any;
}
const props = withDefaults(defineProps<Props>(), {
  limit: 1,
  disabled: false,
  modelValue: [],
});
const emits = defineEmits<{
  (event: "update:modelValue", value: any): void;
}>();
const userStore = useUserStore();

const fileList = ref(props.modelValue);

console.log("初始文件列表：", fileList.value);
// 请求头
const header = ref({
  Authorization: userStore.token,
});
// 文件数据
const item = ref([]);
// 上传组件引用
const uploadRef = ref();
// 后台请求接口前缀
const action = ref(import.meta.env.VITE_API);

// 结果
const result = ref([]);

// 判断上传是否是图片
const beforeAvatarUpload = (rawFile: File) => {
  // 允许的图片格式（后缀）
  const allowedExtensions = ["jpg", "jpeg", "png", "gif", "bmp", "webp"];
  // 允许的图片 MIME 类型
  const allowedMimeTypes = [
    "image/jpeg",
    "image/png",
    "image/gif",
    "image/bmp",
    "image/webp",
  ];

  // 获取文件后缀（转为小写）
  const fileExtension = rawFile.name.split(".").pop()?.toLowerCase();
  // 检查后缀是否在允许列表中
  const isExtensionValid =
    fileExtension && allowedExtensions.includes(fileExtension);

  // 检查 MIME 类型是否在允许列表中
  const isMimeTypeValid = allowedMimeTypes.includes(rawFile.type);

  // 格式校验不通过
  if (!isExtensionValid || !isMimeTypeValid) {
    tt.msg.error(`请上传以下格式的图片：${allowedExtensions.join("、")}`);
    return false;
  }

  // 大小校验（20MB = 20 * 1024 * 1024 字节）
  const maxSize = 20 * 1024 * 1024; // 20MB
  if (rawFile.size > maxSize) {
    tt.msg.error("图片大小不能超过 20MB");
    return false;
  }

  // 校验通过
  return true;
};

const onExceedHan = async (files: File[], uploadFiles: UploadUserFile[]) => {
  const uploadInstance = uploadRef.value;
  if (!uploadInstance) return;
  files.forEach((file: any) => {
    file.uid = genFileId(); // 生成唯一 ID
    uploadInstance.handleStart(file); // 开始上传
  });
  // 5. 上传完成后更新表单数据
  try {
    const res = await api.sys.file.uploadFile(files); // 批量上传接口
    result.value = res.data.map((file: any) => ({
      url: file.fileUrl,
      name: file.fileName,
      id: file.id,
    }));
    console.log("上传结果：", result.value);
    emits("update:modelValue", result.value);
    fileList.value = result.value;
  } catch (e) {
    console.error("文件上传失败", e);
    tt.msg.error("文件上传失败，请重试");
  }
};

const onRemoveHan = (uploadFile: UploadFile, uploadFiles: UploadFiles) => {
  console.log("移除文件：", uploadFile, uploadFiles);
  result.value = result.value.filter(
    (item: any) => item.name !== uploadFile.name,
  );
  emits("update:modelValue", result.value);
  fileList.value = result.value;
};

/**
 * 自定义的文件上传
 * @param options
 * @param item
 */
const uploadSuccessCusHandler = async (options: any) => {
  const { file, onSuccess, onError } = options;
  try {
    const res = await api.sys.file.uploadFile([file]);
    const d = {
      url: res.data[0].fileUrl,
      name: res.data[0].fileName,
      id: res.data[0].id,
    };
    result.value.push(d);
    console.log("上传结果11：", result.value);
    emits("update:modelValue", result.value);
    fileList.value = result.value;
    onSuccess(d);
  } catch (e) {
    onError(e);
  }
};
const initData = async () => {};
defineExpose({ result, item });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
