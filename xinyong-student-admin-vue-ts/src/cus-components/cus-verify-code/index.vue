<template>
  <div @click="makeCode()" style="cursor: pointer">
    <canvas
      id="code"
      :width="props.contentWidth"
      :height="props.contentHeight"></canvas>
  </div>
</template>

<script setup lang="ts">
import { tt } from '@/utils/tool';
import { getCurrentInstance, onMounted, ref, watch } from 'vue';

// 获取 Vue 实例中的全局属性
const app = getCurrentInstance()?.appContext.config.globalProperties;

// 定义 Props 的类型
interface Props {
  // 验证码数量
  num: number;
  //验证码图片宽度
  contentWidth: number;
  //验证码图片高度
  contentHeight: number;
}

// 定义 Props
const props = withDefaults(defineProps<Props>(), {
  num: 4,
  contentHeight: 40,
  contentWidth: 130,
});

// 定义 Emits (没有需要emit的事件，保留空数组)
const emits = defineEmits([]);

// 验证码
const identifyCode = ref<string>('');

// 字符列表
const identifyCodes = '123456789ABCDEFGHGKMNPQRSTUVWXYZ';

/**
 * 生成验证码
 */
const makeCode = () => {
  let tem = '';
  for (let i = 0; i < props.num; i++) {
    tem += identifyCodes[Math.floor(Math.random() * identifyCodes.length)];
  }
  identifyCode.value = tem;
  console.log('验证码 :>> ', identifyCode.value);
};

// 生成随机数
const randomNum = (min: number, max: number): number => {
  return Math.floor(Math.random() * (max - min) + min);
};

// 生成随机颜色
const randomColor = (min: number, max: number): string => {
  const r = randomNum(min, max);
  const g = randomNum(min, max);
  const b = randomNum(min, max);
  return `rgb(${r},${g},${b})`;
};

// 绘制文本
const drawText = (ctx: CanvasRenderingContext2D, txt: string, i: number) => {
  ctx.fillStyle = randomColor(50, 160);
  ctx.font = `${randomNum(
    props.contentHeight / 1.5,
    props.contentHeight
  )}px SimHei`;

  const x = (i + 1) * (props.contentWidth / (identifyCode.value.length + 1));
  const y = randomNum(props.contentHeight - 5, props.contentHeight);
  const deg = randomNum(-30, 30);

  ctx.translate(x, y);
  ctx.rotate((deg * Math.PI) / 270);
  ctx.fillText(txt, 0, 0);

  ctx.rotate((-deg * Math.PI) / 270);
  ctx.translate(-x, -y);
};

// 绘制线条
const drawLine = (ctx: CanvasRenderingContext2D) => {
  for (let i = 0; i < 2; i++) {
    ctx.strokeStyle = randomColor(40, 180);
    ctx.beginPath();
    ctx.moveTo(
      randomNum(0, props.contentWidth),
      randomNum(0, props.contentHeight)
    );
    ctx.lineTo(
      randomNum(0, props.contentWidth),
      randomNum(0, props.contentHeight)
    );
    ctx.stroke();
  }
};

// 绘制点
const drawDot = (ctx: CanvasRenderingContext2D) => {
  for (let i = 0; i < 20; i++) {
    ctx.fillStyle = randomColor(0, 255);
    ctx.beginPath();
    ctx.arc(
      randomNum(0, props.contentWidth),
      randomNum(0, props.contentHeight),
      1,
      0,
      2 * Math.PI
    );
    ctx.fill();
  }
};

// 绘制验证码图像
const drawPic = () => {
  const canvas = document.getElementById('code') as HTMLCanvasElement | null;
  if (canvas) {
    const ctx = canvas.getContext('2d');
    if (ctx) {
      ctx.textBaseline = 'bottom';
      ctx.fillStyle = randomColor(180, 240);
      ctx.fillRect(0, 0, props.contentWidth, props.contentHeight);
      for (let i = 0; i < identifyCode.value.length; i++) {
        drawText(ctx, identifyCode.value[i], i);
      }
      drawLine(ctx);
      drawDot(ctx);
    }
  }
};

// 监听验证码变化，重新绘制
watch(identifyCode, () => {
  drawPic();
});

// 初始化数据并生成验证码
const initData = async () => {
  makeCode();
};

// 对外暴露方法
defineExpose({ makeCode, identifyCode });

// 挂载时初始化
onMounted(async () => {
  await initData();
});
</script>

<style lang="scss" scoped></style>
