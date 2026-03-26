<template>
    <el-dialog v-model="vis" title="更新" append-to-body>
        <CusForm v-if="vis" ref="cusFormRef" :data="data" @clickEvent="updHandler"></CusForm>
        <template #footer>
            <CusBtn type="primary" @click="updHandler">确定</CusBtn>
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
            type: 'id',
            name: 'id',
        } as Id,
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
    // 更新事件
    const updHandler = async () => {
        if (!cusFormRef.value.formRef) return;
        await cusFormRef.value.formRef.validate(async (valid: any, fields: any) => {
            if (valid) {
                console.log(cusFormRef.value.formData);
                const result: Res = await  api.xinyong.solana.updSolana({
                    ...cusFormRef.value.formData,
                });
                tt.msg.success(result.message);
                vis.value = false;
                cusFormRef.value.resetForm();
                emits('refresh');
            }
        });
    };
    /**
     * 重新赋值表单
     * @param {*} data
     */
    const setForm = async (data: any) => {
        await nextTick();
        for (const item in data) {
            for (const x in cusFormRef.value.formData) {
                // 如果字段相同
                if (item == x) {
                    const formItem = cusFormRef.value.formItemData.find((t: any) => t.name === x);
                    if (formItem?.label?.includes('【区块】')) {
                        cusFormRef.value.formData[x] = data['solanaBlockBase'][item];
                    } else {
                        cusFormRef.value.formData[x] = data[item];
                    }
                }
            }
        }
    };
    const initData = async () => {};
    defineExpose({ vis, setForm });
    onMounted(async () => {
        await initData();
    });
</script>
<style lang="scss" scoped></style>
