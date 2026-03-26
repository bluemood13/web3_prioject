const modules: any = import.meta.glob(
  "@/bus-components/user-info/templates/**/index.vue",
  {
    eager: true,
  },
);

export const useUserInfoTemplateHook = () => {
  const userInfoTemplate: any = {};
  for (const path in modules) {
    const match: any =
      /bus-components\/user-info\/templates\/([^/]+)\/index\.vue/.exec(path);
    if (match.length > 1) {
      const word = match[1];
      userInfoTemplate[word] = modules[path].default;
    }
  }
  return userInfoTemplate;
};
