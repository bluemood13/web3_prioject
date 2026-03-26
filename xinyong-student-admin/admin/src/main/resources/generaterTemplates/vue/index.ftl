<template>
    <div>
        <!-- 搜索组件 -->
        <SearchCom ref="searchRef" @search="initData"></SearchCom>
        <!-- 按钮组组件 -->
        <CusBtnGroup
                @add-handler="addHan"
                @refresh-handler="initData"></CusBtnGroup>
        <!-- 表格组件 -->
        <CusTable ref="tableRef" :init="api.${tablePrefix}.${lowerModelName}.get${modelName}ListPage">
            <#list tableFields as item>
                <#if item.columnName != 'update_time' &&  item.columnName != 'update_user' && item.columnName !='id' && item.columnName !='create_user'>
                    <#--如果是bool-->
                    <#if item.dataType == 'bit'>
                        <el-table-column prop="${item.javaName}" label="${item.columnComment}" >
                            <template #default="scope">
                                <el-tag :type="scope.row.${item.javaName} ? 'success' : 'danger'">{{
                                    scope.row.${item.javaName} ? '是' : '否'
                                    }}</el-tag>
                            </template>
                        </el-table-column>
                    <#--如果是图片-->
                    <#elseif item.columnName?contains('_img')>
                        <el-table-column prop="${item.javaName}" label="${item.columnComment}" >
                            <template #default="scope">
                                <el-image
                                        style="width: 100px; height: 100px"
                                        :src="action + '/static/' + scope.row.${item.javaName}"
                                        fit="cover" />
                            </template>
                        </el-table-column>
                    <#--创建时间-->
                    <#elseif item.columnName == 'create_time'>
                        <el-table-column prop="${item.javaName}" label="创建时间" sortable/>
                    <#else>
                        <el-table-column prop="${item.javaName}" label="${item.columnComment}" />
                    </#if>
                </#if>
            </#list>
            <el-table-column label="操作" :fixed="false" width="200">
                <template #default="scope">
                    <CusBtn type="primary" size="small" @click="updHan(scope.row)"
                    >更新</CusBtn
                    >
                    <CusBtn type="danger" size="small" @click="delHan(scope.row)"
                    >删除</CusBtn
                    >
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
    import { store } from '@/utils/store';

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
    interface Props {}
    const props = withDefaults(defineProps<Props | {}>(), {});
    const emits = defineEmits<{}>();
    const action = import.meta.env.VITE_API;
    const tableRef = ref();
    const searchRef = ref();
    const addRef = ref();
    const updRef = ref();

    // 添加事件
    const addHan = async () => {
        addRef.value.vis = true;
    };
    // 更新事件
    const updHan = async (row: any) => {
        updRef.value.vis = true;
        await updRef.value.setForm(row);
    };
    // 删除事件
    const delHan = async (row: any) => {
        const res:Res = await api.${tablePrefix}.${lowerModelName}.del${modelName}({ id: row.id });
        tt.msg.success(res.message);
        await initData();
    };
    // 初始化事件
    const initData = async (searchParams: any = {}) => {
       await tableRef.value.initData(searchParams);
    };
    defineExpose({});
    onMounted(async () => {
        await initData();
    });
</script>
<style lang="scss" scoped></style>
