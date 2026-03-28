import { createApp } from "vue";
import { p } from "@/stores";
import router from "./router";
import App from "./App.vue";
// element plus 组件库
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
// 全局自定义模块
import { cusComList } from "@/cus-components";
// element plus 暗黑模式
import "element-plus/theme-chalk/dark/css-vars.css";
// 配置tailwindcss
import "@/assets/css/tailwind.css";
// 全局主题（字体、圆角、阴影、布局层次）
import "@/assets/css/app-theme.css";
// 应用级主题（字体、圆角、阴影、主区域背景）
import "@/assets/css/app-theme.css";
// 全局主题（字体、圆角、阴影、布局质感）
import "@/assets/css/app-theme.css";
// 加载element plus的图标
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
// 引入动画库
import "animate.css";

const app = createApp(App);

app.use(ElementPlus);
app.use(p);
app.use(router);

// 配置全局错误处理函数
app.config.errorHandler = (err: any, vm: any, info: string) => {
  console.error("全局异常捕获：", err);
  console.error("错误信息", info);
};
// 加载全局自定义组件
for (const item in cusComList) {
  console.log(item);
  app.component(item, cusComList[item]);
}
// 加载全局图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}

app.mount("#app");
