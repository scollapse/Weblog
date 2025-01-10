/*
 Navicat Premium Dump SQL

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80403 (8.4.3)
 Source Host           : localhost:3306
 Source Schema         : weblog

 Target Server Type    : MySQL
 Target Server Version : 80403 (8.4.3)
 File Encoding         : 65001

 Date: 10/01/2025 17:51:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(120) NOT NULL DEFAULT '' COMMENT '文章标题',
  `cover` varchar(120) NOT NULL DEFAULT '' COMMENT '文章封面',
  `summary` varchar(160) DEFAULT '' COMMENT '文章摘要',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '删除标志位：0：未删除 1：已删除',
  `read_num` int unsigned NOT NULL DEFAULT '1' COMMENT '被阅读次数',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章表';

-- ----------------------------
-- Records of t_article
-- ----------------------------
BEGIN;
INSERT INTO `t_article` (`id`, `title`, `cover`, `summary`, `create_time`, `update_time`, `is_deleted`, `read_num`) VALUES (1, '测试文章', 'test', '', '2025-01-10 16:40:47', '2025-01-10 16:40:47', 0, 1);
INSERT INTO `t_article` (`id`, `title`, `cover`, `summary`, `create_time`, `update_time`, `is_deleted`, `read_num`) VALUES (2, '测试标题33', 'https://img.quanxiaoha.com/quanxiaoha/193dd1504ebb4f138085acb23619e0dd.jpg', '测试摘要333', '2025-01-10 17:05:22', '2025-01-10 17:34:27', 0, 1);
INSERT INTO `t_article` (`id`, `title`, `cover`, `summary`, `create_time`, `update_time`, `is_deleted`, `read_num`) VALUES (3, '测试标题', 'https://img.quanxiaoha.com/quanxiaoha/193dd1504ebb4f138085acb23619e0dd.jpg', '测试摘要', '2025-01-10 17:07:08', '2025-01-10 17:07:08', 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for t_article_category_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_article_category_rel`;
CREATE TABLE `t_article_category_rel` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint unsigned NOT NULL COMMENT '文章id',
  `category_id` bigint unsigned NOT NULL COMMENT '分类id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uni_article_id` (`article_id`) USING BTREE,
  KEY `idx_category_id` (`category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章所属分类关联表';

-- ----------------------------
-- Records of t_article_category_rel
-- ----------------------------
BEGIN;
INSERT INTO `t_article_category_rel` (`id`, `article_id`, `category_id`) VALUES (1, 1, 1);
INSERT INTO `t_article_category_rel` (`id`, `article_id`, `category_id`) VALUES (3, 3, 6);
INSERT INTO `t_article_category_rel` (`id`, `article_id`, `category_id`) VALUES (5, 2, 3);
COMMIT;

-- ----------------------------
-- Table structure for t_article_content
-- ----------------------------
DROP TABLE IF EXISTS `t_article_content`;
CREATE TABLE `t_article_content` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '文章内容id',
  `article_id` bigint NOT NULL COMMENT '文章id',
  `content` text COMMENT '教程正文',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_article_id` (`article_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章内容表';

-- ----------------------------
-- Records of t_article_content
-- ----------------------------
BEGIN;
INSERT INTO `t_article_content` (`id`, `article_id`, `content`) VALUES (1, 1, '测试内容');
INSERT INTO `t_article_content` (`id`, `article_id`, `content`) VALUES (2, 2, '内容333');
INSERT INTO `t_article_content` (`id`, `article_id`, `content`) VALUES (3, 3, '内容');
COMMIT;

-- ----------------------------
-- Table structure for t_article_tag_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_article_tag_rel`;
CREATE TABLE `t_article_tag_rel` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint unsigned NOT NULL COMMENT '文章id',
  `tag_id` bigint unsigned NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_article_id` (`article_id`) USING BTREE,
  KEY `idx_tag_id` (`tag_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章对应标签关联表';

-- ----------------------------
-- Records of t_article_tag_rel
-- ----------------------------
BEGIN;
INSERT INTO `t_article_tag_rel` (`id`, `article_id`, `tag_id`) VALUES (3, 3, 1);
INSERT INTO `t_article_tag_rel` (`id`, `article_id`, `tag_id`) VALUES (4, 3, 9);
INSERT INTO `t_article_tag_rel` (`id`, `article_id`, `tag_id`) VALUES (9, 2, 16);
COMMIT;

-- ----------------------------
-- Table structure for t_blog_settings
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_settings`;
CREATE TABLE `t_blog_settings` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `logo` varchar(120) NOT NULL DEFAULT '' COMMENT '博客Logo',
  `name` varchar(60) NOT NULL DEFAULT '' COMMENT '博客名称',
  `author` varchar(20) NOT NULL DEFAULT '' COMMENT '作者名',
  `introduction` varchar(120) NOT NULL DEFAULT '' COMMENT '介绍语',
  `avatar` varchar(120) NOT NULL DEFAULT '' COMMENT '作者头像',
  `github_homepage` varchar(60) NOT NULL DEFAULT '' COMMENT 'GitHub 主页访问地址',
  `csdn_homepage` varchar(60) NOT NULL DEFAULT '' COMMENT 'CSDN 主页访问地址',
  `gitee_homepage` varchar(60) NOT NULL DEFAULT '' COMMENT 'Gitee 主页访问地址',
  `zhihu_homepage` varchar(60) NOT NULL DEFAULT '' COMMENT '知乎主页访问地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='博客设置表';

-- ----------------------------
-- Records of t_blog_settings
-- ----------------------------
BEGIN;
INSERT INTO `t_blog_settings` (`id`, `logo`, `name`, `author`, `introduction`, `avatar`, `github_homepage`, `csdn_homepage`, `gitee_homepage`, `zhihu_homepage`) VALUES (1, 'https://img.quanxiaoha.com/quanxiaoha/f97361c0429d4bb1bc276ab835843065.jpg', 'weblog', 'syl123', 'my blog', 'http://127.0.0.1:9000/weblog/7bd4558f3c184ae6aea80ff480e48a5f.png', '', '', '', '');
COMMIT;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` varchar(60) NOT NULL DEFAULT '' COMMENT '分类名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位：0：未删除 1：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_name` (`name`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章分类表';

-- ----------------------------
-- Records of t_category
-- ----------------------------
BEGIN;
INSERT INTO `t_category` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (1, '测试分类 1', '2024-12-30 10:29:54', '2024-12-30 10:29:54', 0);
INSERT INTO `t_category` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (2, '测试分类 2', '2024-12-30 10:29:59', '2024-12-30 10:29:59', 0);
INSERT INTO `t_category` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (3, '测试分类 3', '2024-12-30 10:30:03', '2024-12-30 10:30:03', 0);
INSERT INTO `t_category` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (4, '测试分类 4', '2024-12-30 11:25:53', '2024-12-30 11:25:53', 0);
INSERT INTO `t_category` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (5, '测试分类 5', '2024-12-30 11:25:58', '2024-12-30 11:25:58', 0);
INSERT INTO `t_category` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (6, '测试分类 6', '2024-12-30 11:26:04', '2024-12-30 11:26:04', 0);
INSERT INTO `t_category` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (7, '测试分类 7', '2024-12-30 11:26:08', '2024-12-30 11:26:08', 0);
INSERT INTO `t_category` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (8, '测试分类 8', '2024-12-30 11:26:12', '2024-12-30 11:26:12', 0);
INSERT INTO `t_category` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (9, '测试分类 9', '2024-12-30 11:26:30', '2024-12-30 11:26:30', 0);
INSERT INTO `t_category` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (10, '测试分类 10', '2024-12-30 11:26:34', '2024-12-30 11:26:34', 0);
INSERT INTO `t_category` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (11, '测试分类 11', '2024-12-30 11:26:38', '2024-12-30 11:26:38', 0);
INSERT INTO `t_category` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (18, '1', '2024-12-31 15:04:16', '2024-12-31 15:04:16', 0);
COMMIT;

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `name` varchar(60) NOT NULL DEFAULT '' COMMENT '标签名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除标志位：0：未删除 1：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_name` (`name`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章标签表';

-- ----------------------------
-- Records of t_tag
-- ----------------------------
BEGIN;
INSERT INTO `t_tag` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (1, 'java', '2024-12-31 16:38:33', '2024-12-31 16:38:33', 0);
INSERT INTO `t_tag` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (2, 'python', '2024-12-31 16:38:33', '2024-12-31 16:38:33', 0);
INSERT INTO `t_tag` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (3, 'php', '2024-12-31 16:38:33', '2024-12-31 16:38:33', 0);
INSERT INTO `t_tag` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (9, 'css', '2025-01-02 16:08:42', '2025-01-02 16:08:42', 0);
INSERT INTO `t_tag` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (10, 'html', '2025-01-02 16:09:15', '2025-01-02 16:09:15', 0);
INSERT INTO `t_tag` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (11, 'js', '2025-01-02 16:09:15', '2025-01-02 16:09:15', 0);
INSERT INTO `t_tag` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (12, '新的标签1', '2025-01-10 17:05:22', '2025-01-10 17:05:22', 0);
INSERT INTO `t_tag` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (13, '新的标签2', '2025-01-10 17:05:22', '2025-01-10 17:05:22', 0);
INSERT INTO `t_tag` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (14, '新的标签3', '2025-01-10 17:08:05', '2025-01-10 17:08:05', 0);
INSERT INTO `t_tag` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (15, '新的标签4', '2025-01-10 17:08:05', '2025-01-10 17:08:05', 0);
INSERT INTO `t_tag` (`id`, `name`, `create_time`, `update_time`, `is_deleted`) VALUES (16, 'test', '2025-01-10 17:34:27', '2025-01-10 17:34:27', 0);
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(60) NOT NULL COMMENT '用户名',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除：0：未删除 1：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` (`id`, `username`, `password`, `create_time`, `update_time`, `is_deleted`) VALUES (1, 'admin@mail.com', '$2a$10$2SVI0SVzhQN7ZLBRGTT3.eLwtAg2WU1llnu5lxbMdK3XQukgd9NDO', '2024-12-24 01:23:48', '2024-12-27 17:33:05', 0);
INSERT INTO `t_user` (`id`, `username`, `password`, `create_time`, `update_time`, `is_deleted`) VALUES (2, 'test@mail.com', '$2a$10$mZ7pPSI.HcM43x6oUaiLBuCt9nfdnMtiYwvVpW3MhqvJnfjvGit3e', '2024-12-24 14:38:37', '2024-12-24 14:38:41', 0);
INSERT INTO `t_user` (`id`, `username`, `password`, `create_time`, `update_time`, `is_deleted`) VALUES (3, '犬小哈1', '123456', '2025-01-07 17:20:26', '2025-01-07 17:20:26', 0);
COMMIT;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(60) NOT NULL COMMENT '用户名',
  `role` varchar(60) NOT NULL COMMENT '角色',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户角色表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
BEGIN;
INSERT INTO `t_user_role` (`id`, `username`, `role`, `create_time`) VALUES (1, 'admin@mail.com', 'ROLE_ADMIN', '2023-07-07 01:21:15');
INSERT INTO `t_user_role` (`id`, `username`, `role`, `create_time`) VALUES (2, 'test@mail.com', 'ROLE_VISITOR', '2023-07-07 01:23:33');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
