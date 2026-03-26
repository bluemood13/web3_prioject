import { ElNotification } from "element-plus";

const duration: number = 2000;
/**
 * 侧边提醒工具
 */
export const NotificationTool = {
  /**
   * 成功通知
   * @param message
   * @param title
   * @param onClose
   * @param onClick
   */
  success: (
    message: string,
    title: string = "提醒",
    position:
      | "top-right"
      | "top-left"
      | "bottom-right"
      | "bottom-left" = "top-right",
    onClose = () => {},
    onClick = () => {},
  ) => {
    ElNotification({
      title: title,
      message: message,
      type: "success",
      duration: duration,
      position: position,
      onClose: onClose,
      onClick: onClick,
    });
  },

  /**
   * 警告通知
   * @param message
   * @param title
   * @param onClose
   * @param onClick
   */
  warning: (
    message: string,
    title: string = "提醒",
    position:
      | "top-right"
      | "top-left"
      | "bottom-right"
      | "bottom-left" = "top-right",
    onClose = () => {},
    onClick = () => {},
  ) => {
    ElNotification({
      title: title,
      message: message,
      type: "warning",
      duration: duration,
      position: position,
      onClose: onClose,
      onClick: onClick,
    });
  },

  /**
   * 正常通知
   * @param message
   * @param title
   * @param onClose
   * @param onClick
   */
  info: (
    message: string,
    title: string = "提醒",
    position:
      | "top-right"
      | "top-left"
      | "bottom-right"
      | "bottom-left" = "top-right",
    onClose = () => {},
    onClick = () => {},
  ) => {
    ElNotification({
      title: title,
      message: message,
      type: "info",
      duration: duration,
      position: position,
      onClose: onClose,
      onClick: onClick,
    });
  },
  /**
   * 错误通知
   * @param message
   * @param title
   * @param onClose
   * @param onClick
   */
  error: (
    message: string,
    title: string = "提醒",
    position:
      | "top-right"
      | "top-left"
      | "bottom-right"
      | "bottom-left" = "top-right",
    onClose = () => {},
    onClick = () => {},
  ) => {
    ElNotification({
      title: title,
      message: message,
      type: "error",
      duration: duration,
      position: position,
      onClose: onClose,
      onClick: onClick,
    });
  },
};
