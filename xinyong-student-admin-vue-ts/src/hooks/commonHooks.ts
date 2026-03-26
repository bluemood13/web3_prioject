import defaultImg from "@/assets/img/img1.png";
// import { computed } from "vue";
import { store } from "@/utils/store";

const getHeadImgHook = computed(() => {
  return store.user.userInfo?.avatar || defaultImg;
});

export default { getHeadImgHook };
