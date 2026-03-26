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
            type: 'select',
            // 是否多选
            more: false,
            //   下拉框改变的事件
            changeEvent: async(data: string) => {
            },
            // 异步加载数据
            initFunc: async () => {
                const res = await api.xinyong.jiaoyi.getJiaoyiListAll({});
                return res.data
            },
            // 异步加载的数据集合
            data: [],
            // 下拉框中的label的字段名称
            itemLabel: 'name',
            // 下拉框中的value的字段名称
            itemValue: 'id',
            name: 'jiaoyiId',
            default: '',
            disabled: true,
            label: '关联交易',
            rules: true
        } as Select,
    ]);
    const cusFormRef = ref();
    // 更新事件
    const updHandler = async () => {
        if (!cusFormRef.value.formRef) return;
        await cusFormRef.value.formRef.validate(async (valid: any, fields: any) => {
            if (valid) {
                console.log(cusFormRef.value.formData);
                const result: Res = await  api.xinyong.jiaoyiLink.updJiaoyiLink({
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
                        cusFormRef.value.formData[x] = data['jiaoyiLinkBlockBase'][item];
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
