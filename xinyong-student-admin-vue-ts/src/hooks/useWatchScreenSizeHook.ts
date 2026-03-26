import { ref, onBeforeUnmount } from 'vue';
import { useDebounceFn } from '@vueuse/core';
import { useGlobalStore } from '@/stores/modules/global';

/**
 * 检测窗口大小调整是否收缩菜单栏
 */
export function useWatchScreenSizeHook() {
  // 创建一个响应式变量，用于存储当前窗口宽度
  const screenWidth = ref(0);
  // 创建一个响应式变量，用于表示侧边栏是否折叠
  const isCollapse = ref(false);
  // 获取全局状态存储对象
  const globalStore = useGlobalStore();

  // 创建一个经过防抖处理的函数，用于处理窗口大小变化
  const listeningWindow = useDebounceFn(() => {
    // 获取当前文档主体的宽度，并赋值给 screenWidth 响应式变量
    screenWidth.value = document.body.clientWidth;
    // 如果侧边栏当前未折叠且窗口宽度小于 1200，则将全局状态中的 isCollapse 设置为 true，表示折叠侧边栏
    if (!isCollapse.value && screenWidth.value < 1200) {
      globalStore.isColl = true;
    }
    // 如果侧边栏当前折叠且窗口宽度大于 1200，则将全局状态中的 isCollapse 设置为 false，表示展开侧边栏
    if (isCollapse.value && screenWidth.value > 1200) {
      globalStore.isColl = false;
    }
  }, 100);

  // 为窗口的 resize 事件添加监听函数 listeningWindow
  window.addEventListener('resize', listeningWindow, false);

  // 在组件卸载前，移除窗口的 resize 事件监听
  onBeforeUnmount(() => {
    window.removeEventListener('resize', listeningWindow);
  });
}
