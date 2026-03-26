import { ElMessage, ElMessageBox } from 'element-plus';

// 持续时间
const duration: number = 2000;

/**
 * 消息提示工具
 */
export const MessageTool = {
  /**
   * 成功
   * @param {*} content
   * @param {*} onClose
   */
  success: (content: string, onClose = () => {}) => {
    ElMessage.success({
      message: content,
      onClose: onClose,
      duration: duration,
    });
  },
  /**
   * 错误
   * @param {*} content
   * @param {*} onClose
   */
  error: (content: string, onClose = () => {}) => {
    ElMessage.error({ message: content, onClose: onClose, duration: duration });
  },
  /**
   * 消息
   * @param {*} content
   * @param {*} onClose
   */
  info: (content: string, onClose = () => {}) => {
    ElMessage.info({ message: content, onClose: onClose, duration: duration });
  },
  /**
   * 警告
   * @param {*} content
   * @param {*} onClose
   */
  warning: (content: string, onClose = () => {}) => {
    ElMessage.warning({
      message: content,
      onClose: onClose,
      duration: duration,
    });
  },
  /**
   * 消息确认框
   * @param {*} content
   * @param {*} successCallback 确认回调
   * @param {*} failCallback 取消回调
   */
  confirm: (
    content: string,
    successCallback = () => {},
    failCallback = () => {}
  ) => {
    ElMessageBox.confirm(content, '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
      callback: (action, instance) => {
        if (action == 'confirm') {
          successCallback();
        } else {
          failCallback();
        }
      },
    });
  },
  /**
   * 消息输入框
   * @param {*} content 提示内容
   * @param {*} title 标题
   * @param {*} options 配置选项
   * @param {*} successCallback 确认回调，参数为输入的值
   * @param {*} failCallback 取消回调
   */
  prompt: (
    content: string,
    title: string = '提示',
    options: any = {},
    successCallback = (value: string) => {},
    failCallback = () => {}
  ) => {
    ElMessageBox.prompt(content, title, {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
      ...options,
    })
      .then(({ value }) => {
        successCallback(value);
      })
      .catch(() => {
        failCallback();
      });
  },
};
