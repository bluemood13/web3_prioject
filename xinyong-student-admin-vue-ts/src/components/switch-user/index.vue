<template>
  <el-dialog v-model="vis" title="切换用户" width="60%" :append-to-body="true">
    <el-table
      :data="data"
      ref="tableRef"
      style="width: 100%"
      max-height="480"
      stripe
    >
      <el-table-column label="id" prop="id"> </el-table-column>
      <el-table-column prop="username" label="账号" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="sex" label="性别">
        <template #default="scope">
          <el-tag>{{ scope.row.sex == "1" ? "男" : "女" }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="content" label="介绍" />
      <el-table-column label="角色列表">
        <template #default="scope">
          <div
            class="p-1"
            v-for="(item, index) in scope.row?.roles"
            :key="index"
          >
            <el-tag>{{ item?.roleName }}</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template #default="scope">
          <CusBtn type="primary" @click="qiehuanHan(scope.row)">切换</CusBtn>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>
<script setup lang="ts">
import { tt } from "@/utils/tool";
import { api } from "@/utils/api";
import { store, storeClear } from "@/utils/store";

import { addRoutes } from "@/router/utils";

import {
  getCurrentInstance,
  onMounted,
  ref,
  computed,
  watch,
  ComponentCustomProperties,
} from "vue";
import { ConfigData } from "@/config";
const app = getCurrentInstance()?.appContext.config
  .globalProperties as ComponentCustomProperties;
interface Props {
  //   demo?: string;
}
const props = withDefaults(defineProps<Props | {}>(), {
  //   demo: '',
});
const emits = defineEmits<{
  //   (event: 'refresh'): void;
}>();
const vis = ref(false);
const data = ref([]);
const setForm = async (data: any) => {};

const qiehuanHan = async (row: any) => {
  /**
   * 退出登录
   */
  await api.login.logout();
  // 清空store的信息
  storeClear();

  /**
   * 登录
   */

  const res = await api.login.login({
    username: row.username,
    password: row.password,
  });
  // 赋值用户的信息
  store.user.token = res.data.token;
  store.user.userInfo = res.data;
  // 请求用户菜单数据
  const menuRes = await api.sys.menu.getUserMenu({});
  // 赋值用户的菜单信息
  store.auth.menuList = menuRes.data;
  // 添加动态路由
  // 将菜单数据加到路由中
  addRoutes(app.$router, store.auth.routeMenuList);
  // 清空tab和keepAlive
  store.tab.reset();
  store.keep.reset();
  // 消息提醒
  tt.msg.success(res.message);
  // 跳转首页
  vis.value = false;
  const { href } = app.$router.resolve({
    path: ConfigData.ROUTE_CONST.HOME_ROUTE.PATH,
  });
  window.location.href = href;
};
const initData = async () => {
  const res: Res = await api.sys.user.getUserListAll();
  data.value = res.data;
  console.log(data.value);
};
defineExpose({ vis, setForm });
onMounted(async () => {
  await initData();
});
</script>
<style lang="scss" scoped></style>
