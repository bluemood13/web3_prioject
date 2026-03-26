<template>
  <div style="border: 1px solid #ccc">
    <Toolbar
      style="border-bottom: 1px solid #ccc"
      :editor="editorRef"
      :defaultConfig="toolbarConfig"
    />
    <Editor
      style="height: 500px; overflow-y: hidden"
      v-model="computedValue"
      :defaultConfig="editorConfig"
      @onCreated="handleCreated"
    />
  </div>
</template>

<script setup lang="ts">
import { tt } from "@/utils/tool";
import { useUserStore } from "@/stores/modules/user";

import {
  ref,
  shallowRef,
  computed,
  onMounted,
  onBeforeUnmount,
  nextTick,
  getCurrentInstance,
} from "vue";
import "@wangeditor/editor/dist/css/style.css";
import { Editor, Toolbar } from "@wangeditor/editor-for-vue";
import { ConfigData } from "@/config";

const userStore = useUserStore();
interface Props {
  // 双向绑定的值
  modelValue: string;
  // 是否禁用
  disabled: boolean;
}

// 定义 emit 事件类型
interface CusfutextEmitEvents {
  (event: "update:modelValue", data: string): void;
}

// 获取 Vue 实例上下文
const app = getCurrentInstance()?.appContext.config.globalProperties;
// 定义 Props 和 Emits
const props = withDefaults(defineProps<Props>(), {
  // 默认是非禁用的
  disabled: false,
});
const emits = defineEmits<CusfutextEmitEvents>();
// 引用 Editor 实例和 HTML 内容
const editorRef = shallowRef();
// 具体的文本
const valueHtml = ref<string>(props.modelValue);
// 工具栏配置
const toolbarConfig: Record<string, unknown> = {};
// 编辑器配置，包括图片上传配置
const editorConfig = {
  placeholder: "请输入内容...",
  MENU_CONF: {
    uploadImage: {
      server: ConfigData.UPLOAD_SERVER,
      fieldName: "files",
      maxFileSize: 20 * 1024 * 1024, // 20M
      maxNumberOfFiles: 10,
      allowedFileTypes: ["image/*"],
      headers: {
        Authorization: userStore.token as string,
      },
      customInsert(res: any, insertFn: (url: string) => void) {
        insertFn(res.data[0].fileUrl);
      },
    },
  },
};

// 处理编辑器创建事件
const handleCreated = (editor: any) => {
  editorRef.value = editor;
  // 设置禁用
  setDisabled(props.disabled);
};

// 使用 computed 来双向绑定 v-model
const computedValue = computed({
  get: () => valueHtml.value,
  set: (newValue: string) => {
    valueHtml.value = newValue;
    emits("update:modelValue", newValue);
  },
});

// 设置文本内容
const setText = async (data: string) => {
  await nextTick();
  editorRef.value?.setHtml(data);
};

// 获取文本内容
const getText = async (): Promise<string | undefined> => {
  await nextTick();
  return editorRef.value?.getHtml();
};

// 设置编辑器的禁用状态
const setDisabled = (isDisabled: boolean) => {
  if (isDisabled) {
    editorRef.value?.disable();
  } else {
    editorRef.value?.enable();
  }
};

// 初始化数据函数
const initData = async () => {
  // 在此处添加初始化逻辑
};

// 对外暴露的方法和变量
defineExpose({ editorRef, setText, getText, setDisabled });

// 清理编辑器实例
onBeforeUnmount(() => {
  const editor = editorRef.value;
  if (editor == null) return;
  editor.destroy();
});

// 挂载时的初始化逻辑
onMounted(async () => {
  await initData();
});
</script>

<style lang="scss" scoped></style>
