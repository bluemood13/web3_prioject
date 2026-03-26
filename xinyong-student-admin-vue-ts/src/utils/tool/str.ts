/**
 * 字符串的方法
 */
export const StrTool = {
  /**
   * cus-card变成CusCard
   * @param {*} str
   * @returns
   */
  toCamelCase: (str: string) => {
    // 使用正则表达式匹配单词，并使用 replace 方法将其转换为驼峰命名
    let result = str.replace(/-([a-z])/g, function (match, letter) {
      return letter.toUpperCase();
    });
    return result.charAt(0).toUpperCase() + result.slice(1);
  },
};
