<template>
  <el-dialog v-model="vis" title="更新" append-to-body>
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-suffix=":"
      label-width="auto">
      <el-form-item label="角色名称" prop="roleName">
        <el-input v-model="formData.roleName"></el-input>
      </el-form-item>

      <el-form-item label="角色值" prop="roleValue">
        <el-input v-model="formData.roleValue"></el-input>
      </el-form-item>

      <el-form-item label="菜单列表" prop="menuIds">
        <el-tree
          class="tree-scoll"
          ref="treeRef"
          :data="treeData"
          :props="{
            children: 'children',
            label: 'title',
            disabled: 'disabled',
          }"
          :default-checked-keys="defaultCheck"
          node-key="id"
          default-expand-all
          highlight-current
          show-checkbox
          :expand-on-click-node="false"
          :current-node-key="1"
          @check="checkHan" />
      </el-form-item>
    </el-form>
    <template #footer>
      <CusBtn @click="updHandler">确定</CusBtn>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { tt } from '@/utils/tool';
import { api } from '@/utils/api';

import {
  getCurrentInstance,
  onMounted,
  ref,
  computed,
  watch,
  nextTick,
  ComponentCustomProperties,
} from 'vue';
const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {}
const props = withDefaults(defineProps<Props | {}>(), {});
const emits = defineEmits<{
  (event: 'refresh'): void;
}>();
const vis = ref(false);

const data = {
  id: '',
  roleName: '',
  roleValue: '',
  menuIds: [],
};

const formData = ref<any>({ ...data });
const formRef = ref();
const formRules = ref({
  roleName: [{ required: true, message: '角色名称不能为空', trigger: 'blur' }],
  roleValue: [{ required: true, message: '角色值不能为空', trigger: 'blur' }],
  menuIds: [{ required: true, message: '角色菜单不能为空', trigger: 'blur' }],
});
const treeRef = ref();
const treeData = ref([]);
const defaultCheck = ref<any>([]);

const resetForm = () => {
  formData.value = { ...data };
};

/**
 * 复选框点击事件
 * @param choose
 * @param all
 */
const checkHan = (choose: any, all: any) => {
  defaultCheck.value = all.checkedKeys;
  formData.value.menuIds = defaultCheck.value;
};

const updHandler = async () => {
  console.log(formData.value);
  if (!formRef.value) return;
  await formRef.value.validate(async (valid: any, fields: any) => {
    if (valid) {
      console.log(formData.value);
      const result: Res = await api.sys.role.updRole({
        ...formData.value,
      });
      tt.msg.success(result.message);
      vis.value = false;
      resetForm();
      emits('refresh');
    }
  });
};

// 设置表懂啊
const setForm = async (data: any) => {
  await nextTick();
  formData.value.roleName = data.roleName;
  formData.value.roleValue = data.roleValue;
  formData.value.id = data.id;
  // 查询选中的菜单
  const res = await api.sys.menu.getRoleMenu({ roleId: data.id });
  formData.value.menuIds = res.data;
  treeRef.value.setCheckedKeys(formData.value.menuIds);
};
/**
 * 设置默认选中的菜单
 * @param data
 */
const initDefaultMenu = (data: any) => {
  for (const item of data) {
    if (item.name == 'home' || item.name == 'userInfo') {
      item.disabled = true;
      defaultCheck.value.push(item.id);
    }
    if (item.children) {
      initDefaultMenu(item.children);
    }
  }
};
const initData = async () => {
  const res: Res = await api.sys.menu.menuListTree();
  // 设置默认选择的菜单
  initDefaultMenu(res.data);
  formData.value.menuIds = defaultCheck.value;
  treeData.value = res.data;
};
defineExpose({ vis, setForm });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped>
.tree-scoll {
  height: 100%;
  width: 100%;
  display: block;
  overflow-y: scroll;
  padding-bottom: 10%;
}
</style>
