export const useClearTabListHook = () => {
  const route = useRoute();
  return route.fullPath;
};
