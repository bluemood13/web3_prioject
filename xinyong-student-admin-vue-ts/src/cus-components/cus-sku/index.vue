<template>
  <div class="w-full">
    <div v-for="(item, index) in kvList" :key="index" class="mb-2 flex">
      <el-select
        v-model="item.key"
        placeholder="规格"
        class="mr-2"
        filterable
        allow-create
        default-first-option
        @change="(val: any) => changeHan(val, item)"
      >
        <el-option
          v-for="(spec, specIndex) in specList"
          :key="spec.id || specIndex"
          :label="spec[props.itemLabel]"
          :value="spec[props.itemValue]"
        ></el-option>
      </el-select>
      <el-select
        v-model="item.value"
        placeholder="规格值"
        class="mr-2"
        filterable
        allow-create
        default-first-option
        @change="(val: any) => changeValueHan(val, item)"
      >
        <el-option
          v-for="(opt, optIndex) in item.options"
          :key="optIndex"
          :label="opt[props.specValueLabel!]"
          :value="opt[props.specValueValue!]"
        ></el-option>
      </el-select>
      <el-button type="danger" circle @click="removeKv(index)"
        ><el-icon><Delete /></el-icon
      ></el-button>
    </div>
    <el-button type="primary" size="small" @click="addKv">添加规格</el-button>
    <el-input
      type="textarea"
      v-model="skuJson"
      class="mt-2"
      disabled
      :rows="5"
      placeholder="预览JSON"
    ></el-input>
  </div>
</template>
<script setup lang="ts">
import { api } from "@/utils/api";
import { onMounted, ref, watch } from "vue";

const props = withDefaults(
  defineProps<{
    modelValue?: string;
    initFunc?: Function;
    itemLabel?: string;
    itemValue?: string;
    specValueFunc?: Function;
    specValueLabel?: string;
    specValueValue?: string;
  }>(),
  {
    itemLabel: "name",
    itemValue: "name",
    specValueLabel: "name",
    specValueValue: "name",
  },
);

const emits = defineEmits<{
  (e: "update:modelValue", value: string): void;
}>();

const specList = ref<any[]>([]);
const kvList = ref<
  {
    key: string;
    value: string;
    options: any[];
    keyLabel?: string;
    valueLabel?: string;
  }[]
>([]);
const skuJson = ref("[]");

// 下拉框改变事件
const changeHan = async (val: any, item: any) => {
  // 查找左侧 label
  const spec = specList.value.find((s) => s[props.itemValue] == val);
  if (spec) {
    item.keyLabel = spec[props.itemLabel];
  } else {
    item.keyLabel = val;
  }

  // 清空右侧
  item.value = "";
  item.valueLabel = "";
  item.options = [];

  if (props.specValueFunc) {
    const data = await props.specValueFunc(val);
    const list = data ?? [];
    if (Array.isArray(list)) {
      item.options = list;
    }
  }
};

const changeValueHan = (val: any, item: any) => {
  const opt = item.options.find((o: any) => o[props.specValueValue!] == val);
  if (opt) {
    item.valueLabel = opt[props.specValueLabel!];
  } else {
    item.valueLabel = val;
  }
};

const addKv = () => {
  kvList.value.push({ key: "", value: "", options: [] });
};

const removeKv = (index: number) => {
  kvList.value.splice(index, 1);
};

// 解析JSON字符串并更新kvList
const parseAndSetKvList = async (jsonStr: string) => {
  try {
    // 只有当解析出来的结果和当前kvList逻辑上不一致时才覆盖，防止输入中断
    if (jsonStr !== skuJson.value) {
      if (!jsonStr || jsonStr === "{}" || jsonStr === "[]") {
        kvList.value = [];
        skuJson.value = "[]";
        return;
      }
      let obj: any;
      try {
        obj = JSON.parse(jsonStr);
      } catch (e) {
        return;
      }

      const list: {
        key: string;
        value: string;
        options: any[];
        keyLabel?: string;
        valueLabel?: string;
      }[] = [];

      // 兼容数组格式
      if (Array.isArray(obj)) {
        for (const item of obj) {
          const newItem = {
            key: item.id,
            keyLabel: item.name,
            value: item.value?.id || "",
            valueLabel: item.value?.name || "",
            options: [],
          };
          list.push(newItem);
          if (props.specValueFunc && newItem.key) {
            props
              .specValueFunc(newItem.key)
              .then((res: any) => {
                const data = res.data ?? res;
                if (Array.isArray(data)) {
                  newItem.options = data;
                }
              })
              .catch((e: any) => console.warn("获取规格值失败", e));
          }
        }
      } else {
        // 兼容旧对象格式
        for (const key in obj) {
          const item = {
            key,
            keyLabel: key, // 旧格式无法区分 id/name
            value: String(obj[key]),
            valueLabel: String(obj[key]), // 旧格式无法区分 id/name
            options: [],
          };
          list.push(item);
          if (props.specValueFunc) {
            props
              .specValueFunc(key)
              .then((res: any) => {
                const data = res.data ?? res;
                if (Array.isArray(data)) {
                  item.options = data;
                }
              })
              .catch((e: any) => console.warn("获取规格值失败", e));
          }
        }
      }

      kvList.value = list;
      skuJson.value = jsonStr;
    }
  } catch (e) {
    console.warn("JSON格式错误", e);
  }
};

// 监听 props.modelValue 变化
watch(
  () => props.modelValue,
  (val) => {
    parseAndSetKvList(val || "[]");
  },
  { immediate: true },
);

// 监听kvList变化
watch(
  kvList,
  (val) => {
    // 构造成数组结构: [{id, name, value: {id, name}}]
    const list = val
      .filter((item) => item.key && item.value)
      .map((item) => {
        return {
          id: item.key,
          name: item.keyLabel || item.key,
          value: {
            id: item.value,
            name: item.valueLabel || item.value,
          },
        };
      });

    const newJson = JSON.stringify(list, null, 2);
    if (newJson !== skuJson.value) {
      skuJson.value = newJson;
      emits("update:modelValue", newJson);
    }
  },
  { deep: true },
);

const initData = async () => {
  if (props.initFunc) {
    try {
      const res = await props.initFunc();
      const list = res.data ?? res;
      if (Array.isArray(list)) {
        // 过滤无效数据，防止渲染报错
        specList.value = list.filter(
          (item: any) => item && item[props.itemLabel],
        );
      }
    } catch (error) {
      console.error("获取规格列表失败", error);
    }
  }
};

onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
