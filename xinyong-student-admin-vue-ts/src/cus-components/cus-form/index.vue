<template>
  <el-form
    ref="formRef"
    @submit.prevent="false"
    :model="formData"
    :rules="formRules"
    label-suffix=":"
    :inline="props.inline"
    label-width="auto"
    @keyup.enter.native="props.clickEvent"
  >
    <el-form-item
      :label="item.label"
      :prop="item.name"
      v-for="(item, index) in formItemData"
      :key="index"
    >
      <!-- 基本输入框 -->
      <template v-if="item.type == 'input'">
        <el-input
          v-model="formData[item.name]"
          :placeholder="'请输入' + item.label"
          clearable
          class="w-full"
          :disabled="item.disabled === true"
        />
      </template>
      <!-- 富文本 -->
      <template v-if="item.type == 'futext'">
        <CusFutext
          v-model="formData[item.name]"
          ref="futextRef"
          :disabled="item.disabled === true"
        ></CusFutext>
      </template>
      <!-- 密码框 -->
      <template v-if="item.type == 'password'">
        <el-input
          type="password"
          v-model="formData[item.name]"
          :placeholder="'请输入' + item.label"
          show-password
          clearable
          class="w-full"
          :disabled="item.disabled === true"
        />
      </template>
      <!-- 文本框 -->
      <template v-if="item.type == 'textarea'">
        <el-input
          type="textarea"
          v-model="formData[item.name]"
          :placeholder="'请输入' + item.label"
          :rows="3"
          clearable
          class="w-full"
          :disabled="item.disabled === true"
        />
      </template>
      <!-- 开关 -->
      <template v-if="item.type == 'bit'">
        <el-switch
          v-model="formData[item.name]"
          :disabled="item.disabled === true"
          inline-prompt
          :active-text="item.bitY ?? '是'"
          :inactive-text="item.bitN ?? '否'"
        />
      </template>
      <!-- 整数数字框 -->
      <template v-if="item.type == 'int'">
        <el-input-number
          class="w-full"
          v-model="formData[item.name]"
          :min="item.min"
          :disabled="item.disabled === true"
        />
      </template>
      <!-- 浮点数数字框 -->
      <template v-if="item.type == 'double'">
        <el-input-number
          class="w-full"
          v-model="formData[item.name]"
          :min="item.min"
          :precision="1"
          :disabled="item.disabled === true"
          :step="0.1"
        />
      </template>
      <!-- 下拉框 -->
      <template v-if="item.type == 'select'">
        <el-select
          :style="{ width: props.inline ? '180px' : '100%' }"
          v-model="formData[item.name]"
          :multiple="item?.more"
          :placeholder="'请选择' + item.label"
          @change="item.changeEvent"
          :disabled="item.disabled === true"
          clearable
          filterable
        >
          <el-option
            :key="index"
            v-for="(x, index) in item.data"
            :label="x[item.itemLabel]"
            :value="x[item.itemValue]"
          />
        </el-select>
      </template>
      <!-- 静态下拉框 -->
      <template v-if="item.type == 'select-base'">
        <el-select
          v-model="formData[item.name]"
          :style="{ width: props.inline ? '180px' : '100%' }"
          :multiple="item.more ?? false"
          :placeholder="'请选择' + item.label"
          :disabled="item.disabled === true"
          clearable
          filterable
        >
          <el-option
            :key="index"
            v-for="(x, index) in item.options"
            :label="x['label']"
            :value="x['value']"
          />
        </el-select>
      </template>
      <!-- 调色盘 -->
      <template v-if="item.type == 'color'">
        <el-color-picker
          :disabled="item.disabled === true"
          v-model="formData[item.name]"
          show-alpha
        />
      </template>

      <!-- 日期 -->
      <template v-if="item.type == 'date'">
        <el-date-picker
          class="w-full"
          v-model="formData[item.name]"
          type="date"
          :placeholder="'请选择' + item.label"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
          :disabled="item.disabled === true"
        />
      </template>
      <!-- 日期时间 -->
      <template v-if="item.type == 'datetime'">
        <el-date-picker
          class="w-full"
          v-model="formData[item.name]"
          type="datetime"
          :placeholder="'请选择' + item.label"
          format="YYYY-MM-DD HH:mm:ss"
          value-format="YYYY-MM-DD HH:mm:ss"
          :disabled="item.disabled === true"
        />
      </template>
      <!-- 时间 -->
      <template v-if="item.type == 'time'">
        <el-time-select
          class="w-full"
          v-model="formData[item.name]"
          step="00:15"
          :placeholder="'请选择' + item.label"
          format="HH:mm:ss"
          value-format="HH:mm:ss"
          :disabled="item.disabled === true"
        />
      </template>

      <!-- 星级 -->
      <template v-if="item.type == 'star'">
        <el-rate
          :disabled="item.disabled === true"
          v-model="formData[item.name]"
          :max="5"
          show-score
        />
      </template>
      <!-- 验证码 -->
      <template v-if="item.type == 'verify-code'">
        <div class="flex w-full items-center justify-start">
          <div class="flex-1">
            <el-input
              v-model="formData[item.name]"
              :placeholder="'请输入' + item.label"
              clearable
              :disabled="item.disabled === true"
            >
              <template #prefix></template>
            </el-input>
          </div>
          <CusVerifyCode
            class="pl-2"
            ref="verifyCodeRef"
            :num="item.num"
            :content-height="item.contentHeight"
            :content-width="item.contentWidth"
          ></CusVerifyCode>
        </div>
      </template>
      <!-- 穿梭框 -->
      <template v-if="item.type == 'tran'">
        <CusTran
          ref="tranRef"
          v-model="formData[item.name]"
          :init-func="item.initFunc"
          :change-func="item?.changeFunc"
          :filter-func="item?.filterFunc"
          :disabled="item?.disabled === true"
        ></CusTran>
      </template>
      <!-- 文件上传 -->
      <template v-if="item.type == 'file'">
        <el-upload
          ref="uploadRef"
          v-model:file-list="item.item"
          name="files"
          :headers="header"
          :show-file-list="true"
          :before-upload="beforeAvatarUpload"
          :limit="1"
          :on-exceed="(files: any) => handleExceed(files, item)"
          :disabled="item.disabled === true"
          :http-request="
            (options: any) => uploadSuccessCusHandler(options, item)
          "
        >
          <div v-if="!item.disabled">
            <img
              class="h-[100px] w-[100px]"
              v-if="formData[item.name] && tt.common.isImg(formData[item.name])"
              :src="formData[item.name]"
            />
            <el-link
              v-else-if="formData[item.name]"
              type="primary"
              @click.prevent
              target="_blank"
              >替换</el-link
            >
            <el-button type="primary" v-else>点击上传</el-button>
          </div>
        </el-upload>
      </template>
      <!-- 图标 -->
      <template v-if="item.type == 'icon'">
        <CusIconList
          v-model="formData[item.name]"
          :disabled="item.disabled"
        ></CusIconList>
      </template>
      <!-- 树形下拉 -->
      <template v-if="item.type == 'tree-select'">
        <el-tree-select
          ref="treeSelectRef"
          v-model="formData[item.name]"
          :data="item.data"
          :props="item.props"
          :check-strictly="item.checkStrictly"
          :disabled="item.disabled === true"
          :placeholder="'请选择' + item.label"
          class="w-full"
          clearable
          filterable
        />
      </template>
      <!-- 树形菜单 -->
      <template v-if="item.type == 'tree'">
        <CusTree
          v-model="formData[item.name]"
          style="min-height: 200px"
          :init-func="item.initFunc"
          :initProps="item.initProps"
          :current-choose="item.currentChoose"
          :current-key="item.currentKey"
        ></CusTree>
      </template>
      <!-- sku选择 -->
      <template v-if="item.type == 'sku'">
        <CusSku
          v-model="formData[item.name]"
          :init-func="item.initFunc"
          :item-label="item.itemLabel"
          :item-value="item.itemValue"
          :spec-value-func="item.specValueFunc"
          :spec-value-label="item.specValueLabel"
          :spec-value-value="item.specValueValue"
        ></CusSku>
      </template>
    </el-form-item>
    <!-- 按钮 -->
    <el-form-item>
      <slot></slot>
    </el-form-item>
  </el-form>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { useUserStore } from "@/stores/modules/user";

import type { FormItemRule, FormRules } from "element-plus";
import { genFileId } from "element-plus";
import { api } from "@/utils/api";
const app: any = getCurrentInstance()?.appContext.config.globalProperties;

const userStore = useUserStore();
interface CusFormProps {
  // 是否是内联表单
  inline?: boolean;
  // 表单内部组件
  data: Array<
    | Id
    | Input
    | Futext
    | Password
    | Textarea
    | Bit
    | Int
    | Double
    | Select
    | BaseSelect
    | Color
    | Date
    | DateTime
    | Time
    | Star
    | File
    | VerifyCode
    | Icon
    | Tree
    | Tran
    | TreeSelect
    | Sku
  >;
  // 点击事件
  clickEvent?: any;
}

const props = withDefaults(defineProps<CusFormProps>(), {
  clickEvent: () => {
    return () => {};
  },
  inline: false,
  data: () => {
    return [];
  },
});
// 文件上传组件
const uploadRef = ref();
// 富文本组件
const futextRef = ref();
// 验证码组件
const verifyCodeRef = ref();
// 穿梭框实例
const tranRef = ref();
//  树形下拉组件实例
const treeSelectRef = ref();
const emits = defineEmits([]);
// 请求头
const header = ref({
  Authorization: userStore.token,
});

// 判断上传是否是图片
const beforeAvatarUpload = (rawFile: File) => {
  // if (!tt.common.isImg(rawFile.name)) {
  //   tt.msg.error('不是图片格式');
  //   return false;
  // } else if (rawFile.size / 1024 / 1024 > 20) {
  //   tt.msg.error('图片不能超过20M');
  //   return false;
  // }
  return true;
};

/**
 * 自定义的文件上传
 * @param options
 * @param item
 */
const uploadSuccessCusHandler = async (options: any, item: any) => {
  const { file, onSuccess, onError } = options;
  console.log("自定义文件上传成功");
  try {
    const res = await api.sys.file.uploadFile([file]);
    formData.value[item.name] = res.data[0].fileUrl;
    onSuccess(res.data);
  } catch (e) {
    onError(e);
  }
};
/**
 * 文件上传覆盖前一个文件
 * @param files
 * @param item
 */
const handleExceed = async (files: any, item: any) => {
  console.log("覆盖文件成功");
  // 覆盖前一个文件
  uploadRef.value[0]!.clearFiles();
  const file = files[0];
  file.uid = genFileId();
  uploadRef.value[0]!.handleStart(file);

  // 手动继续上传文件
  try {
    const res = await api.sys.file.uploadFile(files);
    formData.value[item.name] = res.data[0].fileUrl;
  } catch (e) {}
};

// 表单组件数据
const formItemData = ref<any>(props.data);

/**
 * 初始化表单异步的数据
 */
const initFormData = async () => {
  for (const item of formItemData.value) {
    // 如果是下拉框类型
    if (item.type == "select") {
      if (item.initFunc) {
        item.data = await item.initFunc();
      }
    } else if (item.type == "tree-select") {
      if (item.initFunc) {
        item.data = await item.initFunc();
      }
    }
  }
};

/**
 * 赋值表单的默认值
 */
const formatData = (): Record<string, any> => {
  let t: Record<string, any> = {};
  for (const item of formItemData.value) {
    t[item.name] = item.default ?? "";
  }
  return t;
};

/**
 * 验证码自定义验证
 * @param rule
 * @param value
 * @param callback
 */
const validatorVerifyCode = (rule: any, value: string, callback: any) => {
  if (!value) {
    return callback(new Error("验证码不能为空"));
  }
  if (
    value.toLocaleLowerCase() !==
    verifyCodeRef.value[0].identifyCode.toLocaleLowerCase()
  ) {
    return callback(new Error("验证码不正确"));
  }
  return callback();
};
/**
 * 赋值表单规则
 */
const formatRules = (): Record<string, FormItemRule[]> => {
  let t: Record<string, any> = {};
  for (const item of formItemData.value) {
    // 默认规则
    if (item.rules === true) {
      t[item.name] = [];
      // 验证码组件的特定规则
      if (item.type == "verify-code") {
        t[item.name].push({
          trigger: "blur",
          validator: validatorVerifyCode,
        });
      }
      // 默认规则是不能为空
      t[item.name].push({
        required: true,
        message: `${item.label}不能为空`,
        trigger: "blur",
      });
    }
    // 自定义规则
    else {
      t[item.name] = item.rules;
    }
  }
  return t;
};

const formData = ref(formatData());
const formRef = ref();
const formRules = ref(formatRules());
// 重置表单
const resetForm = () => {
  formData.value = formatData();
};

const initData = async () => {
  await initFormData();
};
defineExpose({
  resetForm,
  formRef,
  formData,
  formItemData,
  futextRef,
  uploadRef,
  verifyCodeRef,
  tranRef,
  treeSelectRef,
});

onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
