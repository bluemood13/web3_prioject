<template>
    <el-dialog v-model="vis" title="更新">
        <CusForm ref="cusFormRef" :data="data"></CusForm>
        <template #footer>
            <CusBtn type="primary" @click="updHandler">确定</CusBtn>
        </template>
    </el-dialog>
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
    (event: 'refresh'): void;
    }>();

    const vis = ref(false);
    const data = ref([
        <#list tableFields as item>
        <#if item.columnName != 'update_time' && item.columnName != 'update_user' && item.columnName != 'create_user' && item.columnName != 'create_time'>
        <#if item.columnName == 'id'>
        {
            type: 'id',
            name: 'id',
        } as Id,
        <#-- 文本框       -->
        <#elseif item.columnName?contains('content')  && item.dataType == 'text'>
        {
            type: 'textarea',
            name: '${item.javaName}',
            default: '',
            disabled: false,
            label: '${item.columnComment}',
            rules: true
        } as Textarea,
        <#--密码框      -->
        <#elseif item.columnName == 'password' && item.dataType == 'varchar'>
        {
            type: 'password',
            name: '${item.javaName}',
            default: '',
            disabled: false,
            label: '${item.columnComment}',
            rules: true
        } as Password,
        <#--bit选择      -->
        <#elseif  item.dataType == 'bit'>
        {
            type: 'bit',
            name: '${item.javaName}',
            default: false,
            bitY: '是',
            bitN: '否',
            disabled: false,
            label: '${item.columnComment}',
            rules: true
        } as Bit,
        <#--整数      -->
        <#elseif item.dataType == 'int'>
        {
            type: 'int',
            min: 1,
            name: '${item.javaName}',
            default: 1,
            disabled: false,
            label: '${item.columnComment}',
            rules: true
        } as Int,
        <#--验证码      -->
        <#elseif item.columnName == 'verify_code'>
        {
            type: 'verify-code',
            name: '${item.javaName}',
            default: '',
            disabled: false,
            label: '${item.columnComment}',
            rules: true
            // 验证码数量
            num: 4;
            // 高度
            contentHeight: 48;
            // 宽度
            contentWidth: 64;
        } as VerifyCode,
        <#--浮点数      -->
        <#elseif item.dataType == 'double'>
        {
            type: 'double',
            min: 0,
            name: '${item.javaName}',
            default: 0,
            disabled: false,
            label: '${item.columnComment}',
            rules: true
        } as Double,
        <#--远程下拉框      -->
        <#elseif  item.columnName?contains('select')>
        {
            type: 'select',
            // 是否多选
            more: false;
            //   下拉框改变的事件
            changeEvent: async(data: string) => {
            };
            // 异步加载数据
            initFunc: async () => {
                return []
            };
            // 异步加载的数据集合
            data: [];
            // 下拉框中的label的字段名称
            itemLabel: '';
            // 下拉框中的value的字段名称
            itemValue: '';
            name: '${item.javaName}',
            default: '',
            disabled: false,
            label: '${item.columnComment}',
            rules: true
        } as Select,
        <#--静态下拉框      -->
        <#elseif  item.columnName?contains('select_base')>
        {
            type: 'select-base',
            // 是否多选
            more: false;
            //   下拉框改变的事件
            changeEvent: async(data: string) => {
            };
            // 具体的选项
            options: [
                {
                    label: '';
                    value: '';
                }
            ]
            name: '${item.javaName}',
            default: '',
            disabled: false,
            label: '${item.columnComment}',
            rules: true
        } as BaseSelect,
        <#--调色盘      -->
        <#elseif item.dataType == 'varchar' && item.columnName?contains('color')>
        {
            type: 'color',
            name: '${item.javaName}',
            default: '',
            disabled: false,
            label: '${item.columnComment}',
            rules: true
        } as Color,
        <#--日期组件      -->
        <#elseif item.dataType == 'date'>
        {
            type: 'date',
            name: '${item.javaName}',
            default: '',
            disabled: false,
            label: '${item.columnComment}',
            rules: true
        } as Date,
        <#--日期时间组件      -->
        <#elseif item.dataType == 'datetime'>
        {
            type: 'datetime',
            name: '${item.javaName}',
            default: '',
            disabled: false,
            label: '${item.columnComment}',
            rules: true
        } as DateTime,
        <#--时间组件      -->
        <#elseif item.dataType == 'time'>
        {
            type: 'time',
            name: '${item.javaName}',
            default: '',
            disabled: false,
            label: '${item.columnComment}',
            rules: true
        } as Time,
        <#--评分组件      -->
        <#elseif item.columnName?contains('scope') && item.dataType == 'int'>
        {
            type: 'star',
            name: '${item.javaName}',
            default: 0,
            disabled: false,
            label: '${item.columnComment}',
            rules: true
        } as Star,
        <#--文件组件      -->
        <#elseif item.columnName?contains('file') && item.dataType == 'varchar'>
        {
            type: 'file',
            item: [{
                name: '';
                url: '';
            }],
            name: '${item.javaName}',
            default: 0,
            disabled: false,
            label: '${item.columnComment}',
            rules: true
        } as File,
        <#--输入框      -->
        <#elseif item.dataType == 'varchar'>
        {
            type: 'input',
            name: '${item.javaName}',
            default: '',
            disabled: false,
            label: '${item.columnComment}',
            rules: true
        } as Input,
        </#if>
        </#if>
        </#list>
    ]);
    const cusFormRef = ref();
    // 更新事件
    const updHandler = async () => {
        if (!cusFormRef.value.formRef) return;
        await cusFormRef.value.formRef.validate(async (valid: any, fields: any) => {
            if (valid) {
                console.log(cusFormRef.value.formData);
                const result: Res = await  api.${tablePrefix}.${lowerModelName}.upd${modelName}({
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
                    cusFormRef.value.formData[x] = data[item];
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
