<template>
    <div>
        <!-- 搜索组件 -->
        <SearchCom ref="searchRef" @search="initData"></SearchCom>
        <!-- 按钮组组件 -->
        <CusBtnGroup
                @add-handler="addHan"
                @refresh-handler="initData"></CusBtnGroup>
        <!-- 表格组件 -->
        <CusTable ref="tableRef" :init="api.xinyong.jiaoyiLink.getJiaoyiLinkListPage">
        <el-table-column prop="hash" label="区块hash" ></el-table-column>
        <el-table-column prop="tx" label="交易hash" ></el-table-column>
        <el-table-column prop="jiaoyiId" label="关联交易" >
            <template #default="scope">
                <el-tag>{{ getRemoteSelData('jiaoyiId',scope.row.jiaoyiId) }}</el-tag>
            </template>
        </el-table-column>
            <el-table-column prop="createTime" label="创建时间" sortable/>
            <el-table-column label="操作" :fixed="false" width="200">
                <template #default="scope">
                    <div class="flex justify-around items-center flex-wrap">
                        <div class="flex-1 p-1">
                            <CusBtn type="primary" size="small" @click="updHan(scope.row)">更新</CusBtn>
                        </div>
                        <div class="flex-1 p-1">
                            <CusBtn type="danger" :showTip="true" size="small" @click="delHan(scope.row)">删除</CusBtn>
                        </div>
                    </div>
                </template>
            </el-table-column>
        </CusTable>
        <!-- 添加组件 -->
        <AddCom ref="addRef" @refresh="initData"></AddCom>
        <!-- 更新组件 -->
        <UpdCom ref="updRef" @refresh="initData"></UpdCom>

    </div>
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
    import AddCom from './components/add.vue';
    import UpdCom from './components/upd.vue';
    import SearchCom from './components/search.vue';
    const app: any = getCurrentInstance()?.appContext.config
        .globalProperties as ComponentCustomProperties;
    import { useJiaoyiLinkLinkHook } from "@/hooks/useJiaoyiLinkLinkHook";
    interface Props {}
    const props = withDefaults(defineProps<Props | {}>(), {});
    const emits = defineEmits<{}>();
    const tableRef = ref();
    const searchRef = ref();
    const addRef = ref();
    const updRef = ref();

    const {
        jiaoyiIdDataList,
    getRemoteSelData,
    getStaticSelData,
    } = useJiaoyiLinkLinkHook();
    

    // 添加事件
    const addHan = async () => {
        addRef.value.vis = true;
        await addRef.value.setForm();
    };
    // 更新事件
    const updHan = async (row: any) => {
        updRef.value.vis = true;
        await updRef.value.setForm(row);
    };
    // 删除事件
    const delHan = async (row: any) => {
        const res:Res = await api.xinyong.jiaoyiLink.delJiaoyiLink({ id: row.id });
        tt.msg.success(res.message);
        await initData();
    };
    // 初始化事件
    const initData = async (searchParams: any = {}) => {
       await tableRef.value.initData(searchParams);
        const resJiaoyiId = await api.xinyong.jiaoyi.getJiaoyiListAll({});
        jiaoyiIdDataList.value = resJiaoyiId.data;
    };
    defineExpose({});
    onMounted(async () => {
        await initData();
    });
</script>
<style lang="scss" scoped></style>
