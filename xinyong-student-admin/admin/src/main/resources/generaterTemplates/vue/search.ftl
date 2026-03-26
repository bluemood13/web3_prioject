<template>
    <CusCard :showTitle="false">
        <CusForm ref="cusFormRef" :data="data" :inline="true">
            <CusBtn type="primary" :plain="false" @click="searchHandler">搜索</CusBtn>
        </CusForm>
    </CusCard>
</template>
<script setup lang="ts">
    import { tt } from '@/utils/tool';
    import { api } from '@/utils/api';
    import { store } from '@/utils/store';
    import { hooks } from '@/hooks';
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
    (event: 'search', formData: any): void;
    }>();
    const cusFormRef = ref();
    const data = ref([
        <#list tableFields as item>
            <#if item.columnName != 'update_time' && item.columnName != 'update_user' && item.columnName != 'create_user' && item.columnName != 'create_time' && item.columnName != 'create_time' && item.columnName != 'id' && item.dataType == 'varchar'>
            {
                type: 'input',
                name: '${item.javaName}',
                default: '',
                disabled: false,
                label: '${item.columnComment}',
                rules: false
            } as Input,
            </#if>
        </#list>
    ]);
    // 搜索事件
    const searchHandler = async () => {
        await nextTick();
        emits('search', cusFormRef.value.formData);
    };
    const initData = async () => {};
    defineExpose({});
    onMounted(async () => {
        await initData();
    });
</script>
<style lang="scss" scoped></style>
