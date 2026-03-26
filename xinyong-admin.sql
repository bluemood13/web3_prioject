/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80403
 Source Host           : localhost:3306
 Source Schema         : xinyong-admin

 Target Server Type    : MySQL
 Target Server Version : 80403
 File Encoding         : 65001

 Date: 21/03/2026 22:57:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `file_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件名',
  `file_origin_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '原始文件名',
  `file_size` int(0) NULL DEFAULT NULL COMMENT '文件大小字节',
  `file_path` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件目录',
  `file_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件url',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` int(0) NULL DEFAULT NULL COMMENT '创建用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 172 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统文件表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (172, '1761840392828.png', 'img2.png', 1574171, 'D:\\java_project\\student-admin\\file\\1761840392828.png', 'http://localhost:9999/static/1761840392828.png', '2025-10-31 00:06:33', 7);

-- ----------------------------
-- Table structure for sys_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_info`;
CREATE TABLE `sys_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '公告信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` int(0) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` int(0) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_info
-- ----------------------------
INSERT INTO `sys_info` VALUES (5, '这是新的公告', '2024-12-30 02:27:16', 7, NULL, NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由名称，外部链接也在这里填写',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由图标',
  `ranks` int(0) NULL DEFAULT 0 COMMENT '菜单排序，值越高越靠前',
  `parent_id` int(0) NULL DEFAULT 0 COMMENT '父节点',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件的路径',
  `is_frame` bit(1) NULL DEFAULT b'0' COMMENT '是否是外部链接',
  `is_hide` bit(1) NULL DEFAULT b'0' COMMENT '是否在左侧菜单中隐藏该项，默认否\r\n',
  `is_affix` bit(1) NULL DEFAULT b'0' COMMENT '是否固定到tab栏，默认是',
  `is_cache` bit(1) NULL DEFAULT b'1' COMMENT '是否缓存页面，默认是',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` int(0) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` int(0) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (8, 'home', '/home', '首页', 'Sugar', 888, 0, '/home/index', b'0', b'0', b'1', b'0', '2023-10-22 19:57:42', 7, '2025-02-20 05:45:29', 7);
INSERT INTO `sys_menu` VALUES (9, 'system', '/system', '系统管理', 'Sugar', 0, 0, '', b'0', b'0', b'0', b'0', '2023-10-31 00:18:12', 7, '2025-02-20 05:45:05', 7);
INSERT INTO `sys_menu` VALUES (10, 'systemUser', '/system/user', '用户管理', '', 0, 9, '/system/user/index', b'0', b'0', b'0', b'0', '2023-10-31 00:19:21', 7, '2025-10-20 01:39:34', 7);
INSERT INTO `sys_menu` VALUES (11, 'systemMenu', '/system/menu', '菜单管理', '', 1, 9, '/system/menu/index', b'0', b'0', b'0', b'0', '2023-11-01 20:42:32', 7, '2025-02-20 05:44:33', 7);
INSERT INTO `sys_menu` VALUES (12, 'systemRole', '/system/role', '角色管理', '', 2, 9, '/system/role/index', b'0', b'0', b'0', b'0', '2023-11-01 20:43:16', 7, '2025-02-20 05:44:34', 7);
INSERT INTO `sys_menu` VALUES (21, 'userInfo', '/system/user-info', '个人资料', 'UserFilled', 5, 0, '/system/user-info/index', b'0', b'0', b'0', b'0', '2023-12-05 23:24:53', 7, '2025-02-20 05:46:19', 7);
INSERT INTO `sys_menu` VALUES (22, 'systemInfo', '/system/info', '系统公告', '', 0, 9, '/system/info/index', b'0', b'0', b'0', b'0', '2023-12-07 21:47:01', 7, '2025-02-20 05:44:28', 7);
INSERT INTO `sys_menu` VALUES (23, 'systemFile', '/system/file', '文件管理', '', 0, 9, '/system/file/index', b'0', b'0', b'0', b'0', '2023-12-08 21:27:04', 7, '2025-02-20 05:44:25', 7);
INSERT INTO `sys_menu` VALUES (25, 'solana', '/xinyong/solana', 'Solana价格管理', 'AddLocation', 8, 0, '/xinyong/solana/index', b'0', b'0', b'0', b'0', '2026-03-20 21:56:00', 7, NULL, NULL);
INSERT INTO `sys_menu` VALUES (26, 'qianbao', '/xinyong/qianbao', '钱包管理', 'AddLocation', 8, 0, '/xinyong/qianbao/index', b'0', b'0', b'0', b'0', '2026-03-21 01:51:27', 7, NULL, NULL);
INSERT INTO `sys_menu` VALUES (27, 'myUserQianbao', '/xinyong/myUserQianbao', '我的钱包', 'Van', 8, 0, '/xinyong/myUserQianbao/index', b'0', b'0', b'0', b'0', '2026-03-21 01:51:34', 7, NULL, NULL);
INSERT INTO `sys_menu` VALUES (28, 'jiaoyi', '/xinyong/jiaoyi', '交易管理', 'AddLocation', 8, 0, '/xinyong/jiaoyi/index', b'0', b'0', b'0', b'0', '2026-03-21 01:51:54', 7, NULL, NULL);
INSERT INTO `sys_menu` VALUES (29, 'jiaoyiLink', '/xinyong/jiaoyiLink', '交易区块管理', 'AddLocation', 8, 0, '/xinyong/jiaoyiLink/index', b'0', b'0', b'0', b'0', '2026-03-21 01:51:56', 7, NULL, NULL);
INSERT INTO `sys_menu` VALUES (30, 'myUserJiaoyi', '/xinyong/myUserJiaoyi', '我的交易', 'DCaret', 8, 0, '/xinyong/myUserJiaoyi/index', b'0', b'0', b'0', b'0', '2026-03-21 01:52:52', 7, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `role_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色值',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` int(0) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` int(0) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 'admin', '2023-10-22 20:01:43', 7, '2025-11-09 20:06:46', NULL);
INSERT INTO `sys_role` VALUES (2, '默认角色', 'default', '2023-10-22 20:01:43', 7, '2025-11-09 20:06:49', NULL);
INSERT INTO `sys_role` VALUES (6, '普通用户', 'common', '2025-11-09 20:18:36', NULL, '2025-11-09 20:18:46', NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(0) NULL DEFAULT NULL COMMENT '关联角色',
  `menu_id` int(0) NULL DEFAULT NULL COMMENT '关联菜单',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `menu_id`(`menu_id`) USING BTREE,
  CONSTRAINT `sys_role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (15, 1, 9);
INSERT INTO `sys_role_menu` VALUES (16, 1, 12);
INSERT INTO `sys_role_menu` VALUES (17, 1, 11);
INSERT INTO `sys_role_menu` VALUES (18, 1, 10);
INSERT INTO `sys_role_menu` VALUES (25, 1, 22);
INSERT INTO `sys_role_menu` VALUES (26, 1, 23);
INSERT INTO `sys_role_menu` VALUES (28, 1, 8);
INSERT INTO `sys_role_menu` VALUES (29, 2, 8);
INSERT INTO `sys_role_menu` VALUES (37, 1, 21);
INSERT INTO `sys_role_menu` VALUES (38, 2, 21);
INSERT INTO `sys_role_menu` VALUES (53, 6, 8);
INSERT INTO `sys_role_menu` VALUES (54, 6, 21);
INSERT INTO `sys_role_menu` VALUES (55, 1, 25);
INSERT INTO `sys_role_menu` VALUES (56, 1, 26);
INSERT INTO `sys_role_menu` VALUES (57, 6, 27);
INSERT INTO `sys_role_menu` VALUES (58, 1, 28);
INSERT INTO `sys_role_menu` VALUES (59, 1, 29);
INSERT INTO `sys_role_menu` VALUES (60, 6, 30);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `sex` int(0) NULL DEFAULT 1 COMMENT '性别 0 女 1男',
  `avatar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '头像',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '介绍',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次登录时间',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '登陆时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `money` double NULL DEFAULT 0 COMMENT '金币',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (7, 'admin', '123456', '管理员', 0, 'http://localhost:9999/static/1761840392828.png', 'xxx', 'xxx', 'xxx', '2026-03-21 22:46:25', '2026-03-21 22:56:47', '2023-12-07 23:53:07', '2025-10-31 00:06:35', 999999);
INSERT INTO `sys_user` VALUES (8, 'common', '123456', '普通用户', 1, 'http://localhost:9999/static/1761840392828.png', 'xxx', 'xxx', 'xxx', '2026-03-21 22:00:11', '2026-03-21 22:51:18', '2023-12-07 23:53:07', '2025-11-09 20:21:40', 968041.7799999998);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(0) NOT NULL COMMENT '关联用户',
  `role_id` int(0) NOT NULL COMMENT '关联角色',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `sys_user_role_ibfk_2`(`role_id`) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 111 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (81, 7, 1);
INSERT INTO `sys_user_role` VALUES (107, 8, 2);
INSERT INTO `sys_user_role` VALUES (108, 8, 6);

-- ----------------------------
-- Table structure for xinyong_jiaoyi
-- ----------------------------
DROP TABLE IF EXISTS `xinyong_jiaoyi`;
CREATE TABLE `xinyong_jiaoyi`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区块hash',
  `tx` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '交易hash',
  `num` double NULL DEFAULT NULL COMMENT '交易数量【区块】',
  `types` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '交易类型【区块】',
  `price` double NULL DEFAULT NULL COMMENT '交易单价【区块】',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '关联用户',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` int(0) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` int(0) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `xinyong_jiaoyi_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '交易表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xinyong_jiaoyi
-- ----------------------------
INSERT INTO `xinyong_jiaoyi` VALUES (5, '0x4fe7487584bed28f9a34de68492f2e07ef7faec65458fe2ca65a1d7eb49cf49e', '0x1910385789db17cd52e56253773be246e46df239c5905ee9b2db01750a0f2037', 1, 'buy', 90.87, 8, '2026-03-21 22:34:10', 8, NULL, NULL);
INSERT INTO `xinyong_jiaoyi` VALUES (6, '0xc075fc149123a6eeded5bfb6e53aa74a96194ae728cf029113074c4c1be293f9', '0xf7e9fe041202255d2c98ee10ba91b89df2f43c28fd9baee831dfd8f6fe9763d0', 33, 'buy', 90.87, 8, '2026-03-21 22:34:15', 8, NULL, NULL);
INSERT INTO `xinyong_jiaoyi` VALUES (7, '0x22dae1bf22c15d9737094fdb7cc98c30958a8e0694b2410376d01401fd020b2e', '0xbd63f7a290f1044660c0bbbf9f761ae296574f26f7545f8137339e419a031e1f', 2, 'sell', 90.87, 8, '2026-03-21 22:34:17', 8, NULL, NULL);
INSERT INTO `xinyong_jiaoyi` VALUES (8, '0x6202c7e1b68fbd9446a936b233f68bdecd3166a04aec563fed8f1f8409d1a3fd', '0xb57fa0b0e8d23a8df94139400732d1c30a9ccc4168381c880b24b55143f964f4', 22, 'sell', 84.83, 8, '2026-03-21 22:35:49', 8, NULL, NULL);
INSERT INTO `xinyong_jiaoyi` VALUES (9, '0x673a4267a679cbbc2f20d60bb652a1abafc0fdf6474cac33a930c871bf9ee3c6', '0x24c35387cb934a6d9c946fc655d0acfc5a27c9c9867e3b45833b58f9bf8bd90c', 1, 'sell', 84.83, 8, '2026-03-21 22:42:18', 8, NULL, NULL);

-- ----------------------------
-- Table structure for xinyong_jiaoyi_link
-- ----------------------------
DROP TABLE IF EXISTS `xinyong_jiaoyi_link`;
CREATE TABLE `xinyong_jiaoyi_link`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区块hash',
  `tx` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '交易hash',
  `jiaoyi_id` int(0) NULL DEFAULT NULL COMMENT '关联交易',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` int(0) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` int(0) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `jiaoyi_id`(`jiaoyi_id`) USING BTREE,
  CONSTRAINT `xinyong_jiaoyi_link_ibfk_1` FOREIGN KEY (`jiaoyi_id`) REFERENCES `xinyong_jiaoyi` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '交易区块表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xinyong_jiaoyi_link
-- ----------------------------
INSERT INTO `xinyong_jiaoyi_link` VALUES (5, '0x4fe7487584bed28f9a34de68492f2e07ef7faec65458fe2ca65a1d7eb49cf49e', '0x1910385789db17cd52e56253773be246e46df239c5905ee9b2db01750a0f2037', 5, '2026-03-21 22:34:10', 8, NULL, NULL);
INSERT INTO `xinyong_jiaoyi_link` VALUES (6, '0xc075fc149123a6eeded5bfb6e53aa74a96194ae728cf029113074c4c1be293f9', '0xf7e9fe041202255d2c98ee10ba91b89df2f43c28fd9baee831dfd8f6fe9763d0', 6, '2026-03-21 22:34:15', 8, NULL, NULL);
INSERT INTO `xinyong_jiaoyi_link` VALUES (7, '0x22dae1bf22c15d9737094fdb7cc98c30958a8e0694b2410376d01401fd020b2e', '0xbd63f7a290f1044660c0bbbf9f761ae296574f26f7545f8137339e419a031e1f', 7, '2026-03-21 22:34:17', 8, NULL, NULL);
INSERT INTO `xinyong_jiaoyi_link` VALUES (8, '0x6202c7e1b68fbd9446a936b233f68bdecd3166a04aec563fed8f1f8409d1a3fd', '0xb57fa0b0e8d23a8df94139400732d1c30a9ccc4168381c880b24b55143f964f4', 8, '2026-03-21 22:35:49', 8, NULL, NULL);
INSERT INTO `xinyong_jiaoyi_link` VALUES (9, '0x673a4267a679cbbc2f20d60bb652a1abafc0fdf6474cac33a930c871bf9ee3c6', '0x24c35387cb934a6d9c946fc655d0acfc5a27c9c9867e3b45833b58f9bf8bd90c', 9, '2026-03-21 22:42:18', 8, NULL, NULL);

-- ----------------------------
-- Table structure for xinyong_qianbao
-- ----------------------------
DROP TABLE IF EXISTS `xinyong_qianbao`;
CREATE TABLE `xinyong_qianbao`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `coin` double NULL DEFAULT NULL COMMENT '代币数',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '关联用户',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` int(0) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` int(0) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `xinyong_qianbao_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '钱包表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xinyong_qianbao
-- ----------------------------
INSERT INTO `xinyong_qianbao` VALUES (1, 474, 8, '2026-03-21 21:44:44', 8, '2026-03-21 21:45:16', 8);
INSERT INTO `xinyong_qianbao` VALUES (2, 11, 7, '2026-03-21 21:54:21', 7, NULL, NULL);

-- ----------------------------
-- Table structure for xinyong_solana
-- ----------------------------
DROP TABLE IF EXISTS `xinyong_solana`;
CREATE TABLE `xinyong_solana`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `current_price` double NULL DEFAULT NULL COMMENT '当前价格',
  `hour_max_price` double NULL DEFAULT NULL COMMENT '24小时最高价',
  `hour_min_price` double NULL DEFAULT NULL COMMENT '24小时最低价',
  `hour_rate` double NULL DEFAULT NULL COMMENT '24小时涨跌幅',
  `day_rate` double NULL DEFAULT NULL COMMENT '7天涨跌幅',
  `month_rate` double NULL DEFAULT NULL COMMENT '月涨跌幅',
  `shizhi` double NULL DEFAULT NULL COMMENT '市值',
  `chengjiaoliang` double NULL DEFAULT NULL COMMENT '成交量',
  `total` double NULL DEFAULT NULL COMMENT '总量',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` int(0) NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` int(0) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'Solana价格表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xinyong_solana
-- ----------------------------
INSERT INTO `xinyong_solana` VALUES (1, 88.75, 90.13, 87.28, 0.3403, -3.47081, 7.48543, 50741052464, 3198872021, 571566818.8775859, '2026-03-20 22:04:12', NULL, NULL, NULL);
INSERT INTO `xinyong_solana` VALUES (10, 87.82, 90.13, 87.28, 0.55539, -3.39396, 7.571, 50741052464, 3179590419, 571566818.8775859, '2026-03-20 22:06:18', NULL, NULL, NULL);
INSERT INTO `xinyong_solana` VALUES (15, 86.87, 90.13, 87.28, 0.61391, -3.33774, 7.6336, 50782692355, 3202269961, 571566818.8775859, '2026-03-20 22:09:05', NULL, NULL, NULL);
INSERT INTO `xinyong_solana` VALUES (16, 85.86, 90.13, 87.28, 0.60017, -3.35094, 7.6189, 50782692355, 3202298233, 571566818.8775859, '2026-03-20 22:10:06', NULL, NULL, NULL);
INSERT INTO `xinyong_solana` VALUES (21, 84.83, 90.13, 87.28, 0.56506, -3.38467, 7.58134, 50782692355, 3204754703, 571566818.8775859, '2026-03-20 22:11:12', NULL, NULL, NULL);
INSERT INTO `xinyong_solana` VALUES (23, 85.85, 90.13, 87.28, 0.58777, -3.36286, 7.60563, 50782692355, 3204114453, 571566818.8775859, '2026-03-20 22:12:16', NULL, NULL, NULL);
INSERT INTO `xinyong_solana` VALUES (24, 81.94, 90.13, 87.28, 0.69111, -3.26358, 7.71618, 50782692355, 3179913273, 571566818.8775859, '2026-03-20 22:13:17', NULL, NULL, NULL);
INSERT INTO `xinyong_solana` VALUES (30, 86.95, 90.13, 87.28, 0.69923, -3.25577, 7.72488, 50782692355, 3179778313, 571566818.8775859, '2026-03-20 22:14:24', NULL, NULL, NULL);
INSERT INTO `xinyong_solana` VALUES (34, 86.9, 90.13, 87.28, 0.63914, -3.3135, 7.66059, 50782692355, 3211414210, 571566818.8775859, '2026-03-20 22:15:27', NULL, NULL, NULL);
INSERT INTO `xinyong_solana` VALUES (37, 90.87, 90.13, 87.28, 0.61394, -3.33771, 7.63363, 50782692355, 3211230529, 571566818.8775859, '2026-03-20 22:16:29', NULL, NULL, NULL);
INSERT INTO `xinyong_solana` VALUES (41, 91.82, 90.13, 87.28, 0.83179, -3.3979, 7.56661, 50782692355, 3211249561, 571566818.8775859, '2026-03-20 22:17:32', NULL, NULL, NULL);
INSERT INTO `xinyong_solana` VALUES (43, 83.81, 90.13, 87.28, 0.81744, -3.41165, 7.5513, 50782692355, 3211047580, 571566818.8775859, '2026-03-20 22:18:36', NULL, NULL, NULL);
INSERT INTO `xinyong_solana` VALUES (45, 80.8, 90.13, 87.28, 0.81151, -3.41734, 7.54497, 50750934790, 3176173757, 571566818.8775859, '2026-03-20 22:18:37', NULL, NULL, NULL);
INSERT INTO `xinyong_solana` VALUES (49, 79.79, 90.13, 87.28, 0.80265, -3.42582, 7.53553, 50750934790, 3177031687, 571566818.8775859, '2026-03-20 22:19:40', NULL, NULL, NULL);
INSERT INTO `xinyong_solana` VALUES (53, 87.75, 90.13, 87.28, 0.75054, -3.47575, 7.47993, 50750934790, 3207675507, 571566818.8775859, '2026-03-20 22:20:43', NULL, NULL, NULL);
INSERT INTO `xinyong_solana` VALUES (54, 82.65, 90.13, 87.28, 0.82317, -3.58005, 7.36379, 50750934790, 3169586572, 571566818.8775859, '2026-03-20 22:21:44', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
