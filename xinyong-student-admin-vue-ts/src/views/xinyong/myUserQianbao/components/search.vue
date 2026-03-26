<template>
    <CusCard :showTitle="false">
        <CusForm ref="cusFormRef" :data="data" :inline="true">
            <CusBtn type="danger" :plain="false" @click="resetHan">重置</CusBtn>
            <CusBtn type="primary" :plain="false" @click="searchHan">搜索</CusBtn>
        </CusForm>
    </CusCard>
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
    (event: 'search', formData: any): void;
    }>();
    const cusFormRef = ref();
    const data = ref([
                {
                type: 'select',
                // 是否多选
                more: false,
                //   下拉框改变的事件
                changeEvent: async (data: string) => {},
                // 异步加载数据
                initFunc: async () => {
                    const res = await api.sys.user.getUserListAll({});
                    return res.data
                },
                // 异步加载的数据集合
                data: [],
                // 下拉框中的label的字段名称
                itemLabel: 'name',
                // 下拉框中的value的字段名称
                itemValue: 'id',
                name: 'userId',
                default: null,
                disabled: false,
                label: '关联用户',
                rules: false,
            } as Select,
    ]);
    // 重置事件
    const resetHan = async () => {
        await nextTick();
        cusFormRef.value.resetForm();
    };
    // 搜索事件
    const searchHan = async () => {
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
