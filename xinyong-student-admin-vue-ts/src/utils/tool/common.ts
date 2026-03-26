import { MessageTool } from "./message";
// 引入lodash
import _ from "lodash";
/**
 * 通用工具
 */
export const commonTool = {
  /**
   * 防抖
   * @param func - 要防抖的函数
   * @param wait - 等待时间（毫秒）
   * @returns 一个新的防抖函数
   */
  debounce: (
    func: (...args: any[]) => void,
    wait: number = 300,
  ): ((...args: any[]) => void) => {
    let timeout: NodeJS.Timeout | null = null; // 计时器
    return function (this: any, ...args: any[]) {
      if (timeout) clearTimeout(timeout); // 清除已有的计时器
      timeout = setTimeout(() => {
        func.apply(this, args);
      }, wait);
    };
  },

  /**
   * 节流
   * @param func - 要节流的函数
   * @param wait - 等待时间（毫秒）
   * @returns 一个新的节流函数
   */
  throttle: (
    func: (...args: any[]) => void,
    wait: number = 300,
  ): ((...args: any[]) => void) => {
    let timer: NodeJS.Timeout | null = null;

    return function (this: any, ...args: any[]) {
      if (timer) return;
      timer = setTimeout(() => {
        func.apply(this, args);
        timer = null;
      }, wait);
    };
  },

  /**
   * 判断是否是图片
   * @param fileName - 文件名
   * @returns 是否是图片
   */
  /**
   */
  isImg: (fileName: string = ""): boolean => {
    if (!fileName) return false; // 空文件名直接返回 false

    // 从文件名中提取后缀（处理路径和多个点的情况，如 'dir/image.1.2.png' → 'png'）
    const baseName = fileName.split(/[\\/]/).pop() || ""; // 提取文件名（去除路径）
    const lastDotIndex = baseName.lastIndexOf(".");

    // 无后缀的情况（如 'image' 或 '.image' 视为无后缀）
    if (lastDotIndex <= 0) return false;

    // 提取并转换后缀为小写
    const fileExtension = baseName.slice(lastDotIndex + 1).toLowerCase();

    // 常见图片格式（扩展支持 webp、svg、ico 等）
    const imageExtensions = [
      "jpg",
      "jpeg",
      "png",
      "gif",
      "bmp",
      "jfif",
      "webp",
      "svg",
      "ico",
      "tiff",
      "tif",
      "heic",
      "heif",
    ];

    return imageExtensions.includes(fileExtension);
  },

  /**
   * 交换数组的元素
   * @param arr
   * @param index1
   * @param index2
   * @returns
   */
  swapArr: (arr: any[], index1: number, index2: number): any[] => {
    [arr[index1], arr[index2]] = [arr[index2], arr[index1]];
    return arr;
  },

  /**
   * @description 变浅颜色值
   * @param {String} color 颜色值字符串
   * @param {Number} level 加深的程度，限0-1之间
   * @returns {String} 返回处理后的颜色值
   */
  getLightColor: (color: string, level: number) => {
    let reg = /^\#?[0-9A-Fa-f]{6}$/;
    if (!reg.test(color)) return MessageTool.warning("输入错误的hex颜色值");
    let rgb = commonTool.hexToRgb(color);
    for (let i = 0; i < 3; i++)
      rgb[i] = Math.round(255 * level + rgb[i] * (1 - level));
    return commonTool.rgbToHex(rgb[0], rgb[1], rgb[2]);
  },
  /**
   * @description 加深颜色值
   * @param {String} color 颜色值字符串
   * @param {Number} level 加深的程度，限0-1之间
   * @returns {String} 返回处理后的颜色值
   */
  getDarkColor: (color: string, level: number) => {
    let reg = /^\#?[0-9A-Fa-f]{6}$/;
    if (!reg.test(color)) return MessageTool.warning("输入错误的hex颜色值");
    let rgb = commonTool.hexToRgb(color);
    for (let i = 0; i < 3; i++)
      rgb[i] = Math.round(20.5 * level + rgb[i] * (1 - level));
    return commonTool.rgbToHex(rgb[0], rgb[1], rgb[2]);
  },
  /**
   * @description rgb颜色转Hex颜色
   * @param {*} r 代表红色
   * @param {*} g 代表绿色
   * @param {*} b 代表蓝色
   * @returns {String} 返回处理后的颜色值
   */
  rgbToHex: (r: any, g: any, b: any) => {
    let reg = /^\d{1,3}$/;
    if (!reg.test(r) || !reg.test(g) || !reg.test(b))
      return MessageTool.warning("输入错误的rgb颜色值");
    let hexs = [r.toString(16), g.toString(16), b.toString(16)];
    for (let i = 0; i < 3; i++)
      if (hexs[i].length == 1) hexs[i] = `0${hexs[i]}`;
    return `#${hexs.join("")}`;
  },
  /**
   * @description hex颜色转rgb颜色
   * @param {String} str 颜色值字符串
   * @returns {String} 返回处理后的颜色值
   */
  hexToRgb: (str: any) => {
    let hexs: any = "";
    let reg = /^\#?[0-9A-Fa-f]{6}$/;
    if (!reg.test(str)) return MessageTool.warning("输入错误的hex");
    str = str.replace("#", "");
    hexs = str.match(/../g);
    for (let i = 0; i < 3; i++) hexs[i] = parseInt(hexs[i], 16);
    return hexs;
  },

  /**
   * 判断是否是图片
   * @param link - 文件链接
   * @returns 是否是图片
   */
  isImage: (link: string): boolean => {
    const imageExtensions = ["jpg", "jpeg", "png", "gif", "bmp"];
    const ext = link.split(".").pop()?.toLowerCase();
    return imageExtensions.includes(ext || "");
  },

  /**
   * 从链接中提取文件名
   * @param link - 文件链接
   * @returns 文件名
   */
  getFileName: (link: string): string => {
    return link.split("/").pop() || "";
  },
  /**
   * 获取对象的属性
   * @param data
   * @param path
   */
  getValue: (data: any, path: string) => {
    return _.get(data, path);
  },
  /**
   * 是否包含img字符串
   * @param item
   * @returns
   */
  haveImgStr: (item: string) => {
    if (item.includes("img")) {
      return true;
    }
    return false;
  },
  /**
   * 深拷贝对象
   * @param data - 要拷贝的数据
   * @returns 拷贝后的数据
   */
  deepCopy: (data: any) => {
    if (data === null || data === undefined) {
      return data;
    }
    try {
      // 序列化后反序列化，实现深拷贝
      return JSON.parse(JSON.stringify(data));
    } catch (error) {
      // 拷贝失败时返回默认值（如数据包含不可序列化类型时）
      console.warn("深拷贝失败，返回默认值:", error);
      return data;
    }
  },
  /**
   * 去除对象里面的空值，然后返回新对象
   * @param obj
   */
  filterNullValueObj: (obj: any) => {
    // 默认配置
    const defaultOptions = {
      filterEmptyArray: true, // 是否过滤空数组
      filterEmptyObject: true, // 是否过滤空对象
    };
    // 合并用户配置和默认配置
    const { filterEmptyArray, filterEmptyObject } = { ...defaultOptions };

    const result: any = {};

    // 遍历对象的每个键值对
    for (const key in obj) {
      // 只处理对象自身的属性（不包含原型链上的）
      if (obj.hasOwnProperty(key)) {
        const value = obj[key];
        let isEmpty = false;

        // 判断值是否为空
        if (value === "" || value === null || value === undefined) {
          isEmpty = true;
        } else if (
          filterEmptyArray &&
          Array.isArray(value) &&
          value.length === 0
        ) {
          // 过滤空数组
          isEmpty = true;
        } else if (
          filterEmptyObject &&
          typeof value === "object" &&
          !Array.isArray(value) &&
          Object.keys(value).length === 0
        ) {
          // 过滤空对象
          isEmpty = true;
        }

        // 非空值才保留
        if (!isEmpty) {
          result[key] = value;
        }
      }
    }

    return result;
  },
};
