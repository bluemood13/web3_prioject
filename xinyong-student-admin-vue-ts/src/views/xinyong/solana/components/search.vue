<template>
    <CusCard :showTitle="false">
        <CusForm ref="cusFormRef" :data="data" :inline="true"  @clickEvent="searchHan">
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
