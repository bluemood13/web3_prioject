import { StrTool } from "@/utils/tool/str";
// 加载组件列表
const modules: any = import.meta.glob("@/cus-components/**/index.vue", {
  eager: true,
});
// 要导出的组件列表
export const cusComList: any = {};

for (const path in modules) {
  const match: any = /cus-components\/([^/]+)\/index\.vue/.exec(path);
  if (match.length > 1) {
    const word = match[1];
    // 匹配自定义组件
    if (word.startsWith("cus-")) {
      cusComList[StrTool.toCamelCase(word)] = modules[path].default;
    }
  }
}
