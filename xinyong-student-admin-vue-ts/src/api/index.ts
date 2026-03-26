import axios from "axios";
import { ConfigData } from "@/config";
import router from "@/router";
import { useUserStore } from "@/stores/modules/user";
import { tt } from "@/utils/tool";
import { ElNotification } from "element-plus";

// 创建实例
const instance = axios.create({
  // 60秒超时时间
  timeout: 60 * 1000,
  baseURL: import.meta.env.VITE_API,
  headers: {
    // 设置请求头为 JSON 格式
    "Content-Type": "application/json",
  },
});

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    const userStore = useUserStore();
    // 将token添加到请求头;
    if (userStore.token) {
      config.headers[ConfigData.AUTH_HEADER] = userStore.token;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

// 响应拦截器
instance.interceptors.response.use(
  (response) => {
    const resp = response.data;
    // 后端逻辑错误
    if (resp.code == 404) {
      tt.msg.error(resp.message ?? "后端异常");
      return Promise.reject(resp);
    }
    // 权限认证失败
    if (resp.code == 401) {
      tt.msg.error(resp.message ?? "权限异常");
      // 清除登录信息
      const userStore = useUserStore();
      userStore.reset();
      // 跳转登录页面
      router.push(ConfigData.ROUTE_CONST.LOGIN_ROUTE.PATH);
      return Promise.reject(resp);
    }
    // 判断数据是不是区块链成功的请求
    if (resp.data?.option == "happain") {
      ElNotification({
        title: "区块链交易成功",
        customClass: "notification-wide",
        dangerouslyUseHTMLString: true,
        message: `
<p><strong>区块哈希:</strong> ${resp?.data?.hash.replace("\n", "")}</p>
<p><strong>区块高度:</strong> ${resp?.data?.number}</p>
<p><strong>交易哈希:</strong> ${resp?.data?.tx.replace("\n", "")}</p>
`,
        type: "success",
        duration: 3000,
      });
    }
    return Promise.resolve(resp);
  },
  (error) => {
    // 在请求失败时进行一些处理，例如处理错误信息
    tt.msg.error("网络异常");
    return Promise.reject(error);
  },
);

// GET 请求封装
export const get = async (url: string, params = {}): Promise<Res> => {
  return await instance.get(url, { params });
};

// POST 请求封装
export const post = async (url: string, data = {}): Promise<Res> => {
  return await instance.post(url, data);
};

// 文件上传封装
export const upload = async (
  url: string,
  files: [File],
  data: any = {},
): Promise<Res> => {
  // 创建 FormData 实例
  const formData = new FormData();
  files.forEach((file, index) => {
    formData.append("files", file); // 'files' 是上传的字段名，可以根据后台要求修改
  });
  // 将其他参数追加到 formData 中
  for (const key in data) {
    formData.append(key, data[key]);
  }
  // 创建一个配置对象，设置请求头
  const config = {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  };
  return await instance.post(url, formData, config);
};
