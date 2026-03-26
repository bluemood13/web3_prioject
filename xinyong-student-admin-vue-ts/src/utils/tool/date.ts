/**
 * 日期工具
 */
export const dateTool = {
  /**
   * 格式化日期为 yyyy-mm-dd 格式
   * @param data 要格式化的日期，可以是 Date 对象、时间戳或者日期字符串
   * @returns 格式化后的日期字符串
   */
  formatDate: (data: any): string => {
    const date = new Date(data);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    return `${year}-${month}-${day}`;
  },

  /**
   * 格式化日期时间为 yyyy-mm-dd HH:mm:ss 格式
   * @param data 要格式化的日期时间，可以是 Date 对象、时间戳或者日期字符串
   * @returns 格式化后的日期时间字符串
   */
  formatDateTime: (data: any): string => {
    const date = new Date(data);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    const seconds = String(date.getSeconds()).padStart(2, "0");
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  },

  formatTime: (data: string | number | Date): string => {
    const date = new Date(data);
    // 容错：解析失败返回默认值
    if (isNaN(date.getTime())) {
      // 尝试单独解析时间部分（应对纯时间字符串）
      const timeMatch = String(data).match(
        /(\d{1,2}):(\d{1,2})(?::(\d{1,2}))?/,
      );
      if (timeMatch) {
        const hours = String(timeMatch[1]).padStart(2, "0");
        const minutes = String(timeMatch[2]).padStart(2, "0");
        const seconds = String(timeMatch[3] || 0).padStart(2, "0");
        return `${hours}:${minutes}:${seconds}`;
      }
      return "00:00:00";
    }

    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    const seconds = String(date.getSeconds()).padStart(2, "0");
    return `${hours}:${minutes}:${seconds}`;
  },
};
