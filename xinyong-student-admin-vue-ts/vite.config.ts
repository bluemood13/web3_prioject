import { fileURLToPath, URL } from "node:url";
import { ConfigEnv, defineConfig, loadEnv } from "vite";
import vue from "@vitejs/plugin-vue";
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { ElementPlusResolver } from "unplugin-vue-components/resolvers";
import IconsResolver from "unplugin-icons/resolver";
import Icons from "unplugin-icons/vite";
import tailwindcss from "@tailwindcss/vite";
export default defineConfig(({ mode }: ConfigEnv) => {
  const env = loadEnv(mode, process.cwd());
  return {
    plugins: [
      tailwindcss(),
      vue(),
      // 自动化导入vue、vue-router、pinia等常用模块
      AutoImport({
        imports: ["vue", "vue-router", "pinia"],
        resolvers: [
          ElementPlusResolver(),
          // 自动化导入图标
          IconsResolver({
            prefix: "Icon",
          }),
        ],
        eslintrc: {
          // 这里先设置成true然后pnpm dev 运行之后会生成 .auto-import.json 文件之后，在改为false
          enabled: true,
          filepath: ".auto-import.json",
          globalsPropValue: true,
        },
      }),
      Components({
        // 2. 扫描组件目录（必配！否则可能漏扫自定义组件）
        dirs: ["src/cus-components", "src/components"],
        resolvers: [
          ElementPlusResolver(),
          // 自动注册图标组件
          IconsResolver({
            enabledCollections: ["ep"],
          }),
          // 自动导入cus-components下的组件
          (componentName: any) => {
            // 假设组件名以 "Cus" 开头（如 CusBtn、CusCard）
            if (componentName.startsWith("Cus-")) {
              // 转换为 kebab-case 目录名（CusBtn → cus-btn）
              const kebabName = componentName
                .replace(/([A-Z])/g, "-$1")
                .toLowerCase()
                .replace(/^-/, ""); // 去除开头的连字符

              return {
                from: `./src/cus-components/${kebabName}/index.vue`, // 组件路径
              };
            }
          },
        ],
      }),
      // 自动化导入图片
      Icons({
        autoInstall: true,
      }),
    ],
    css: {
      preprocessorOptions: {
        scss: {
          api: "modern-compiler",
          additionalData: `
          @use "@/assets/scss/global.scss";
          @use "@/assets/scss/index.scss";
        `,
        },
      },
    },
    resolve: {
      alias: {
        "@": fileURLToPath(new URL("./src", import.meta.url)),
      },
    },
    server: {
      // 是否自动启动浏览器
      open: true,
      // 端口
      port: Number.parseInt(env.VITE_PORT),
      proxy: {
        // 代理配置
        "/api": {
          target: env.VITE_API,
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, ""),
        },
      },
    },
  };
});
