const modules: any = import.meta.glob(
  "@/bus-components/login/templates/**/index.vue",
  {
    eager: true,
  },
);

export const useLoginTemplateHook = () => {
  const loginTemplate: any = {};
  for (const path in modules) {
    const match: any =
      /bus-components\/login\/templates\/([^/]+)\/index\.vue/.exec(path);
    if (match.length > 1) {
      const word = match[1];
      loginTemplate[word] = modules[path].default;
    }
  }
  console.log("loginTemplate", loginTemplate);
  return loginTemplate;
};
