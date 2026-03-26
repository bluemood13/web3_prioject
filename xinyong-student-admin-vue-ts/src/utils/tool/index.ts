import { commonTool } from './common';
import { MessageTool } from './message';
import { NotificationTool } from './notification';
import { StrTool } from './str';
import { StoreTool } from './store';
import { dateTool } from './date';

/**
 * 工具类集合
 */
export const tt = {
  // 消息工具
  msg: MessageTool,
  // 提醒工具
  notif: NotificationTool,
  // 字符串工具
  str: StrTool,
  // 通用工具
  common: commonTool,
  // 本地仓库工具
  store: StoreTool,
  // 日期工具
  date: dateTool,
};
