<template>
    <el-dialog v-model="vis" title="新增" append-to-body>
        <CusForm v-if="vis" ref="cusFormRef" :data="data"  @clickEvent="addHandler"></CusForm>
        <template #footer>
            <CusBtn type="primary" @click="addHandler">确定</CusBtn>
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
    const data = ref([
        {
            type: 'double',
            min: 0,
            name: 'currentPrice',
            default: 0,
            disabled: false,
            label: '当前价格',
            rules: true
        } as Double,
        {
            type: 'double',
            min: 0,
            name: 'hourMaxPrice',
            default: 0,
            disabled: false,
            label: '24小时最高价',
            rules: true
        } as Double,
        {
            type: 'double',
            min: 0,
            name: 'hourMinPrice',
            default: 0,
            disabled: false,
            label: '24小时最低价',
            rules: true
        } as Double,
        {
            type: 'double',
            min: 0,
            name: 'hourRate',
            default: 0,
            disabled: false,
            label: '24小时涨跌幅',
            rules: true
        } as Double,
        {
            type: 'double',
            min: 0,
            name: 'dayRate',
            default: 0,
            disabled: false,
            label: '7天涨跌幅',
            rules: true
        } as Double,
        {
            type: 'double',
            min: 0,
            name: 'monthRate',
            default: 0,
            disabled: false,
            label: '月涨跌幅',
            rules: true
        } as Double,
        {
            type: 'double',
            min: 0,
            name: 'shizhi',
            default: 0,
            disabled: false,
            label: '市值',
            rules: true
        } as Double,
        {
            type: 'double',
            min: 0,
            name: 'chengjiaoliang',
            default: 0,
            disabled: false,
            label: '成交量',
            rules: true
        } as Double,
        {
            type: 'double',
            min: 0,
            name: 'total',
            default: 0,
            disabled: false,
            label: '总量',
            rules: true
        } as Double,
    ]);
    const cusFormRef = ref();

    // 添加事件
    const addHandler = async () => {
        if (!cusFormRef.value.formRef) return;
        await cusFormRef.value.formRef.validate(async (valid: any, fields: any) => {
            if (valid) {
                console.log(cusFormRef.value.formData);
                const result: Res = await api.xinyong.solana.addSolana({
                    ...cusFormRef.value.formData,
                });
                tt.msg.success(result.message);
                vis.value = false;
                cusFormRef.value.resetForm();
                emits('refresh');
            }
        });
    };

    // 设置表懂啊
    const setForm = async (data: any) => {
        await nextTick();
    };
    const initData = async () => {};
    defineExpose({ vis, setForm });
    onMounted(async () => {
        await initData();
    });
</script>
<style lang="scss" scoped></style>
