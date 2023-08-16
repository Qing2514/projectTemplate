/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : project

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2023-07-20 13:54:42
*/

SET
    FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ums_user
-- ----------------------------
DROP TABLE IF EXISTS `ums_user`;
CREATE TABLE `ums_user`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `username`    varchar(64)  DEFAULT NULL COMMENT '用户名',
    `password`    varchar(64)  DEFAULT NULL COMMENT '密码',
    `icon`        varchar(500) DEFAULT NULL COMMENT '头像',
    `phone`       varchar(16)  DEFAULT NULL COMMENT '手机号',
    `email`       varchar(100) DEFAULT NULL COMMENT '邮箱',
    `nick_name`   varchar(200) DEFAULT NULL COMMENT '昵称',
    `note`        varchar(500) DEFAULT NULL COMMENT '备注信息',
    `login_time`  datetime     DEFAULT NULL COMMENT '最后登录时间',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_by`   varchar(64)  DEFAULT NULL COMMENT '创建用户',
    `update_time` datetime     DEFAULT NULL COMMENT '修改时间',
    `update_by`   varchar(64)  DEFAULT NULL COMMENT '修改用户',
    `status`      int(1)       DEFAULT 1 COMMENT '帐号启用状态：0->禁用；1->启用',
    `deleted`     int(1)       DEFAULT 0 COMMENT '逻辑删除标识：0->否；1->是',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='后台用户表';

-- ----------------------------
-- Records of ums_user
-- ----------------------------
INSERT INTO `ums_user`
VALUES (1, '15312345678', '$2a$10$DrijOiDM9VwY.qgijJWaXuR.Y7gV4iS1CfMNvdsXZCzTh0Hki.Pfu',
        'https://tupian.qqw21.com/article/UploadPic/2021-3/20213132143427824.jpg', '15312345678', 'user@qq.com',
        '系统管理员', '系统管理员账号', '2023-07-21 16:54:35', '2023-07-20 13:55:30', '15312345678', null, null, 1, 0);
INSERT INTO `ums_user`
VALUES (2, '15123123123', '$2a$10$xsqXIPrRd0GVk76Yf9XH9OyNWy30LH4cqFS4xl0vgB0coEmq61eVa',
        'https://tupian.qqw21.com/article/UploadPic/2021-3/20213132143427824.jpg', '15123123123', '123@qq.com',
        '普通用户', '普通用户账号', '2023-07-21 16:54:35', '2023-07-20 13:55:30', '15312345678', null, null, 1, 0);

-- ----------------------------
-- Table structure for ums_user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `ums_user_login_log`;
CREATE TABLE `ums_user_login_log`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20)   DEFAULT NULL COMMENT '用户ID',
    `ip`          varchar(64)  DEFAULT NULL COMMENT '登陆地IP',
    `address`     varchar(100) DEFAULT NULL COMMENT '登录地址',
    `user_agent`  varchar(200) DEFAULT NULL COMMENT '浏览器登录类型',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_by`   varchar(64)  DEFAULT NULL COMMENT '创建用户',
    `update_time` datetime     DEFAULT NULL COMMENT '修改时间',
    `update_by`   varchar(64)  DEFAULT NULL COMMENT '修改用户',
    `deleted`     int(1)       DEFAULT 0 COMMENT '逻辑删除标识：0->否；1->是',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='后台用户登录日志表';

-- ----------------------------
-- Records of ums_user_login_log
-- ----------------------------
INSERT INTO `ums_user_login_log`
VALUES (1, 1, '0:0:0:0:0:0:0:1', null,
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36 Edg/115.0.1901.188',
        '2023-07-21 16:54:35', '15312345678', null, null, 0);

-- ----------------------------
-- Table structure for ums_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_user_role_relation`;
CREATE TABLE `ums_user_role_relation`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`     bigint(20)  DEFAULT NULL,
    `role_id`     bigint(20)  DEFAULT NULL,
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `create_by`   varchar(64) DEFAULT NULL COMMENT '创建用户',
    `update_time` datetime    DEFAULT NULL COMMENT '修改时间',
    `update_by`   varchar(64) DEFAULT NULL COMMENT '修改用户',
    `deleted`     int(1)      DEFAULT 0 COMMENT '逻辑删除标识：0->否；1->是',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='后台用户和角色关系表';

-- ----------------------------
-- Records of ums_user_role_relation
-- ----------------------------
INSERT INTO `ums_user_role_relation`
VALUES (1, 1, 1, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_user_role_relation`
VALUES (2, 2, 2, '2023-07-21 16:54:35', '15312345678', null, null, 0);

-- ----------------------------
-- Table structure for ums_menu
-- ----------------------------
DROP TABLE IF EXISTS `ums_menu`;
CREATE TABLE `ums_menu`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `parent_id`   bigint(20)   DEFAULT NULL COMMENT '父级ID',
    `title`       varchar(100) DEFAULT NULL COMMENT '菜单名称',
    `level`       int(4)       DEFAULT NULL COMMENT '菜单级数',
    `name`        varchar(100) DEFAULT NULL COMMENT '前端名称',
    `icon`        varchar(200) DEFAULT NULL COMMENT '前端图标',
    `hidden`      int(1)       DEFAULT NULL COMMENT '前端隐藏',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_by`   varchar(64)  DEFAULT NULL COMMENT '创建用户',
    `update_time` datetime     DEFAULT NULL COMMENT '修改时间',
    `update_by`   varchar(64)  DEFAULT NULL COMMENT '修改用户',
    `deleted`     int(1)       DEFAULT 0 COMMENT '逻辑删除标识：0->否；1->是',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='后台菜单表';

-- ----------------------------
-- Records of ums_menu
-- ----------------------------
INSERT INTO `ums_menu`
VALUES (1, '0', '权限', '0', 'ums', 'ums', '0', '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_menu`
VALUES (2, 1, '用户列表', '1', 'admin', 'ums-admin', '0', '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_menu`
VALUES (3, 1, '角色列表', '1', 'role', 'ums-role', '0', '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_menu`
VALUES (4, 1, '菜单列表', '1', 'menu', 'ums-menu', '0', '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_menu`
VALUES (5, 1, '资源列表', '1', 'resource', 'ums-resource', '0', '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_menu`
VALUES (6, 1, '个人信息', '1', 'user', 'ums-user', '0', '2023-07-21 16:54:35', '15312345678', null, null, 0);

-- ----------------------------
-- Table structure for ums_resource
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource`;
CREATE TABLE `ums_resource`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `name`        varchar(200) DEFAULT NULL COMMENT '资源名称',
    `url`         varchar(200) DEFAULT NULL COMMENT '资源URL',
    `description` varchar(500) DEFAULT NULL COMMENT '描述',
    `category_id` bigint(20)   DEFAULT NULL COMMENT '资源分类ID',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_by`   varchar(64)  DEFAULT NULL COMMENT '创建用户',
    `update_time` datetime     DEFAULT NULL COMMENT '修改时间',
    `update_by`   varchar(64)  DEFAULT NULL COMMENT '修改用户',
    `deleted`     int(1)       DEFAULT 0 COMMENT '逻辑删除标识：0->否；1->是',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='后台资源表';

-- ----------------------------
-- Records of ums_resource
-- ----------------------------
INSERT INTO `ums_resource`
VALUES (1, '后台用户管理', '/user/**', null, 1, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_resource`
VALUES (2, '后台用户角色管理', '/role/**', null, 1, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_resource`
VALUES (3, '后台菜单管理', '/menu/**', null, 1, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_resource`
VALUES (4, '后台资源分类管理', '/resourceCategory/**', null, 1, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_resource`
VALUES (5, '后台资源管理', '/resource/**', null, 1, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_resource`
VALUES (6, '个人信息管理', '/user/**', null, 1, '2023-07-21 16:54:35', '15312345678', null, null, 0);

-- ----------------------------
-- Table structure for ums_resource_category
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource_category`;
CREATE TABLE `ums_resource_category`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `name`        varchar(200) DEFAULT NULL COMMENT '分类名称',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_by`   varchar(64)  DEFAULT NULL COMMENT '创建用户',
    `update_time` datetime     DEFAULT NULL COMMENT '修改时间',
    `update_by`   varchar(64)  DEFAULT NULL COMMENT '修改用户',
    `deleted`     int(1)       DEFAULT 0 COMMENT '逻辑删除标识：0->否；1->是',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='资源分类表';

-- ----------------------------
-- Records of ums_resource_category
-- ----------------------------
INSERT INTO `ums_resource_category`
VALUES (1, '权限模块', '2023-07-21 16:54:35', '15312345678', null, null, 0);

-- ----------------------------
-- Table structure for ums_role
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `name`        varchar(100) DEFAULT NULL COMMENT '名称',
    `description` varchar(500) DEFAULT NULL COMMENT '描述',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `create_by`   varchar(64)  DEFAULT NULL COMMENT '创建用户',
    `update_time` datetime     DEFAULT NULL COMMENT '修改时间',
    `update_by`   varchar(64)  DEFAULT NULL COMMENT '修改用户',
    `status`      int(1)       DEFAULT 1 COMMENT '启用状态：0->禁用；1->启用',
    `deleted`     int(1)       DEFAULT 0 COMMENT '逻辑删除标识：0->否；1->是',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='后台用户角色表';

-- ----------------------------
-- Records of ums_role
-- ----------------------------
INSERT INTO `ums_role`
VALUES (1, '系统管理员', '拥有所有权限', '2023-07-21 16:54:35', '15312345678', null, null, 1, 0);
INSERT INTO `ums_role`
VALUES (2, '普通用户', '个人信息管理权限', '2023-07-21 16:54:35', '15312345678', null, null, 1, 0);

-- ----------------------------
-- Table structure for ums_role_menu_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_menu_relation`;
CREATE TABLE `ums_role_menu_relation`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id`     bigint(20)  DEFAULT NULL COMMENT '角色ID',
    `menu_id`     bigint(20)  DEFAULT NULL COMMENT '菜单ID',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `create_by`   varchar(64) DEFAULT NULL COMMENT '创建用户',
    `update_time` datetime    DEFAULT NULL COMMENT '修改时间',
    `update_by`   varchar(64) DEFAULT NULL COMMENT '修改用户',
    `deleted`     int(1)      DEFAULT 0 COMMENT '逻辑删除标识：0->否；1->是',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='后台角色菜单关系表';

-- ----------------------------
-- Records of ums_role_menu_relation
-- ----------------------------
INSERT INTO `ums_role_menu_relation`
VALUES (1, 1, 1, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_role_menu_relation`
VALUES (2, 1, 2, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_role_menu_relation`
VALUES (3, 1, 3, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_role_menu_relation`
VALUES (4, 1, 4, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_role_menu_relation`
VALUES (5, 1, 5, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_role_menu_relation`
VALUES (6, 1, 6, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_role_menu_relation`
VALUES (7, 2, 6, '2023-07-21 16:54:35', '15312345678', null, null, 0);

-- ----------------------------
-- Table structure for ums_role_resource_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_resource_relation`;
CREATE TABLE `ums_role_resource_relation`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id`     bigint(20)  DEFAULT NULL COMMENT '角色ID',
    `resource_id` bigint(20)  DEFAULT NULL COMMENT '资源ID',
    `create_time` datetime    DEFAULT NULL COMMENT '创建时间',
    `create_by`   varchar(64) DEFAULT NULL COMMENT '创建用户',
    `update_time` datetime    DEFAULT NULL COMMENT '修改时间',
    `update_by`   varchar(64) DEFAULT NULL COMMENT '修改用户',
    `deleted`     int(1)      DEFAULT 0 COMMENT '逻辑删除标识：0->否；1->是',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='后台角色资源关系表';

-- ----------------------------
-- Records of ums_role_resource_relation
-- ----------------------------
INSERT INTO `ums_role_resource_relation`
VALUES (1, 1, 1, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_role_resource_relation`
VALUES (2, 1, 2, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_role_resource_relation`
VALUES (3, 1, 3, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_role_resource_relation`
VALUES (4, 1, 4, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_role_resource_relation`
VALUES (5, 1, 5, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_role_resource_relation`
VALUES (6, 1, 6, '2023-07-21 16:54:35', '15312345678', null, null, 0);
INSERT INTO `ums_role_resource_relation`
VALUES (7, 2, 6, '2023-07-21 16:54:35', '15312345678', null, null, 0);