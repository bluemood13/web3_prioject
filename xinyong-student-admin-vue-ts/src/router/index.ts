import { createRouter, createWebHistory } from "vue-router";
import { createGuard } from "./guard";
import { staticRouter } from "./routes";

/**
 * 创建路由
 */
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...staticRouter],
  strict: true,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      // 如果有保存的滚动位置，恢复它
      return savedPosition;
    } else {
      // 否则，滚动到页面顶部
      return { top: 0 };
    }
  },
});
// 创建路由守卫
createGuard(router);

export default router;
