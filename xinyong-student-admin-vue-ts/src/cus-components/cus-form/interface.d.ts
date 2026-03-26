import type { FormItemRule } from "element-plus";

declare global {
  declare interface Base {
    name: string;
    label: string;
    default: any;
    disabled: boolean;
    rules: boolean | FormItemRule[];
  }
  /**
   * Id
   */
  declare interface Id extends Base {
    type: "id";
  }
  /**
   * 基本输入框
   */
  declare interface Input extends Base {
    type: "input";
  }
  /**
   * 富文本
   */
  declare interface Futext extends Base {
    type: "futext";
  }

  /**
   * 密码框
   */
  declare interface Password extends Base {
    type: "password";
  }

  /**
   * 文本框
   */
  declare interface Textarea extends Base {
    type: "textarea";
  }
  /**
   * 开关
   */
  declare interface Bit extends Base {
    type: "bit";
    //   开关打开的显示文本信息
    bitY: string;
    //   开关关闭的显示文本信息
    bitN: string;
  }

  /**
   * 整数数字框
   */
  declare interface Int extends Base {
    type: "int";
    //   最小值
    min: number;
  }

  /**
   * 验证码
   */
  declare interface VerifyCode extends Base {
    type: "verify-code";
    // 验证码数量
    num: number;
    // 高度
    contentHeight: number;
    // 宽度
    contentWidth: number;
  }
  /**
   * 浮点数数字框
   */
  declare interface Double extends Base {
    type: "double";
    //  最小值
    min: number;
  }

  /**
   * 下拉框
   */
  declare interface Select extends Base {
    type: "select";
    // 是否多选
    more: boolean;
    //   下拉框改变的事件
    changeEvent: (data: string) => Promise<void>;
    // 异步加载数据
    initFunc: Function;
    // 异步加载的数据集合
    data: Array<any>;
    // 下拉框中的label的字段名称
    itemLabel: string;
    // 下拉框中的value的字段名称
    itemValue: string;
  }

  /**
   * 静态下拉框
   */
  declare interface BaseSelect extends Base {
    type: "select-base";
    // 是否多选
    more: boolean;
    //   下拉框改变的事件
    changeEvent: (data: string) => Promise<void>;
    options: Array<{
      label: string;
      value: any;
    }>;
  }

  /**
   * 调色盘
   */
  declare interface Color extends Base {
    type: "color";
  }

  /**
   * 日期
   */
  declare interface Date extends Base {
    type: "date";
  }

  /**
   * 日期时间
   */
  declare interface DateTime extends Base {
    type: "datetime";
  }

  /**
   * 时间
   */
  declare interface Time extends Base {
    type: "time";
  }

  /**
   * 评分
   */
  declare interface Star extends Base {
    type: "star";
  }

  /**
   * 文件上传组件
   */
  declare interface File extends Base {
    type: "file";
    item: Array[{
      name: string;
      url: string;
    }];
  }

  /**
   * 图标
   */
  declare interface Icon extends Base {
    type: "icon";
  }

  /**
   * 树形列表组件
   */
  declare interface Tree extends Base {
    type: "tree";
    // 初始化方法
    initFunc: Function;
    // 树节点映射
    initProps?: {
      children: string;
      label: string;
    };
    // 节点选择事件
    currentChoose?: (data: string) => Promise<void>;
    // 初始化节点
    currentKey?: string;
  }

  /**
   * 穿梭框组件
   */
  declare interface Tran extends Base {
    type: "tran";
    // 初始化方法
    initFunc: Function;
    // 是否开启搜索过滤功能
    isFilter?: boolean;
    // 过滤方法
    filterFunc?: (query: string, item: TransferDataItem) => boolean;
    // 改变的方法
    changeFunc?: (
      value: TransferKey[],
      direction: TransferDirection,
      movedKeys: TransferKey[],
    ) => void;
  }

  /**
   * 树形下拉选择
   */
  declare interface TreeSelect extends Base {
    type: "tree-select";
    // 数据源
    data: Array<any>;
    // 初始化方法
    initFunc: Function;
    // 映射属性
    props?: {
      label: string;
      value?: string;
      children: string;
      disabled?: string | Function;
    };
    checkStrictly?: boolean;
  }

  declare interface Sku extends Base {
    type: "sku";
    // 初始化方法
    initFunc?: Function;
    itemLabel?: string;
    itemValue?: string;
    specValueFunc?: Function;
    specValueLabel?: string;
    specValueValue?: string;
  }
}
export {};
