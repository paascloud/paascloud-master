/*
Navicat MySQL Data Transfer

Source Server         : paascloud-dev
Source Server Version : 50719
Source Host           : 192.168.241.21:3306
Source Database       : paascloud_opc

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-03-19 16:19:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pc_mq_message_data
-- ----------------------------
DROP TABLE IF EXISTS `pc_mq_message_data`;
CREATE TABLE `pc_mq_message_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `version` int(11) DEFAULT '0' COMMENT '版本号',
  `message_key` varchar(200) CHARACTER SET utf8 DEFAULT '' COMMENT '消息key',
  `message_topic` varchar(50) CHARACTER SET utf8 DEFAULT '' COMMENT 'topic',
  `message_tag` varchar(50) CHARACTER SET utf8 DEFAULT '' COMMENT 'tag',
  `message_body` longtext CHARACTER SET utf8 COMMENT '消息内容',
  `message_type` int(11) DEFAULT '10' COMMENT '消息类型: 10 - 生产者 ; 20 - 消费者',
  `delay_level` int(11) DEFAULT '0' COMMENT '延时级别 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h',
  `order_type` int(11) DEFAULT '0' COMMENT '顺序类型 0有序 1无序',
  `status` int(11) DEFAULT '10' COMMENT '消息状态',
  `creator` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '创建人',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_operator` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '最近操作人',
  `last_operator_id` bigint(20) DEFAULT NULL COMMENT '最后操作人ID',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `yn` int(11) DEFAULT '0' COMMENT '是否删除 -0 未删除 -1 已删除',
  PRIMARY KEY (`id`),
  KEY `idx_message_key` (`message_key`) USING BTREE,
  KEY `idx_created_time` (`created_time`) USING BTREE,
  KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=318182082838798337 DEFAULT CHARSET=utf8mb4 COMMENT='消息记录表';

-- ----------------------------
-- Records of pc_mq_message_data
-- ----------------------------

-- ----------------------------
-- Table structure for pc_opc_sms_setting
-- ----------------------------
DROP TABLE IF EXISTS `pc_opc_sms_setting`;
CREATE TABLE `pc_opc_sms_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `version` int(4) NOT NULL DEFAULT '0' COMMENT '版本号',
  `again_send_time` int(4) DEFAULT NULL COMMENT '可再次发送时间（毫秒）',
  `invalid_time` int(4) DEFAULT NULL COMMENT '失效时间（分钟）',
  `type` varchar(32) CHARACTER SET utf8 DEFAULT '' COMMENT '短信类型',
  `type_desc` varchar(100) CHARACTER SET utf8 DEFAULT '' COMMENT '类型描述',
  `templet_code` varchar(32) CHARACTER SET utf8 DEFAULT '' COMMENT '模板code',
  `templet_content` varchar(1000) CHARACTER SET utf8 DEFAULT '' COMMENT '模板内容',
  `send_max_num` int(4) DEFAULT NULL COMMENT '一天中可发送的最大数量',
  `ip_send_max_num` int(4) DEFAULT NULL COMMENT '一个IP一天中可发送的最大数量',
  `remark` varchar(200) CHARACTER SET utf8 DEFAULT '' COMMENT '备注',
  `creator` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '创建人',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_operator` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '最近操作人',
  `last_operator_id` bigint(20) DEFAULT NULL COMMENT '最后操作人ID',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `yn` int(11) DEFAULT '0' COMMENT '删除标识(1-已删除；0-未删除)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='短信模板设置表';

-- ----------------------------
-- Records of pc_opc_sms_setting
-- ----------------------------

-- ----------------------------
-- Table structure for pc_opt_attachment
-- ----------------------------
DROP TABLE IF EXISTS `pc_opt_attachment`;
CREATE TABLE `pc_opt_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ref_no` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '上传附件的相关业务流水号',
  `center_name` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '中心名称(英文简写)',
  `bucket_name` varchar(20) CHARACTER SET utf8 DEFAULT '' COMMENT '文件服务器根目录',
  `name` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '附件名称',
  `path` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '附件存储相对路径',
  `type` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '附件类型',
  `format` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '附件格式',
  `description` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '备注',
  `version` bigint(20) DEFAULT '0' COMMENT '版本号',
  `creator` varchar(50) CHARACTER SET utf8 DEFAULT '' COMMENT '创建人',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  `last_operator` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '最后操作人',
  `last_operator_id` bigint(20) DEFAULT NULL COMMENT '最后操作人ID',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=318168887994621953 DEFAULT CHARSET=utf8mb4 COMMENT='业务附件表';

-- ----------------------------
-- Records of pc_opt_attachment
-- ----------------------------
INSERT INTO `pc_opt_attachment` VALUES ('314167466638974976', '27', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314167458443304960.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:14:37', '超级管理员', '1', '2018-03-10 11:14:49');
INSERT INTO `pc_opt_attachment` VALUES ('314167478794067968', '27', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314167475623174144.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:14:39', '超级管理员', '1', '2018-03-10 11:14:49');
INSERT INTO `pc_opt_attachment` VALUES ('314167500805775360', '27', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314167496099766272.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:14:41', '超级管理员', '1', '2018-03-10 11:14:49');
INSERT INTO `pc_opt_attachment` VALUES ('314168622983421952', '26', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314168619401486336.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:16:55', '超级管理员', '1', '2018-03-10 11:17:16');
INSERT INTO `pc_opt_attachment` VALUES ('314168653241131008', '26', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314168650615496704.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:16:59', '超级管理员', '1', '2018-03-10 11:17:16');
INSERT INTO `pc_opt_attachment` VALUES ('314168677257715712', '26', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314168675412221952.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:17:02', '超级管理员', '1', '2018-03-10 11:17:16');
INSERT INTO `pc_opt_attachment` VALUES ('314168716944220160', '26', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314168713681051648.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:17:06', '超级管理员', '1', '2018-03-10 11:17:16');
INSERT INTO `pc_opt_attachment` VALUES ('314169253622194176', '28', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314169250627461120.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:18:10', '超级管理员', '1', '2018-03-10 11:18:20');
INSERT INTO `pc_opt_attachment` VALUES ('314169275776507904', '28', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314169272446230528.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:18:13', '超级管理员', '1', '2018-03-10 11:18:20');
INSERT INTO `pc_opt_attachment` VALUES ('314169295019974656', '28', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314169291622588416.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:18:15', '超级管理员', '1', '2018-03-10 11:18:20');
INSERT INTO `pc_opt_attachment` VALUES ('314169831807000576', '29', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314169828216676352.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:19:19', '超级管理员', '1', '2018-03-10 11:19:31');
INSERT INTO `pc_opt_attachment` VALUES ('314169860747698176', '29', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314169850421321728.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:19:23', '超级管理员', '1', '2018-03-10 11:19:31');
INSERT INTO `pc_opt_attachment` VALUES ('314169878858702848', '29', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314169873087340544.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:19:25', '超级管理员', '1', '2018-03-10 11:19:31');
INSERT INTO `pc_opt_attachment` VALUES ('314169969338228736', '312426496260057088', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314169966418993152.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:19:36', '超级管理员', '1', '2018-03-10 11:19:43');
INSERT INTO `pc_opt_attachment` VALUES ('314169988875296768', '312426496260057088', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314169985905729536.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:19:38', '超级管理员', '1', '2018-03-10 11:19:43');
INSERT INTO `pc_opt_attachment` VALUES ('314170064850919424', '312415946721470464', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '314170052939096064.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-10 11:19:47', '超级管理员', '1', '2018-03-11 20:13:06');
INSERT INTO `pc_opt_attachment` VALUES ('315163180122775552', '312415946721470464', 'paascloud-oss-bucket', 'paascloud-oss-bucket', '315163179812397056.jpg', 'paascloud/picture/product/', 'picture', 'jpg', '', '0', '', null, '2018-03-11 20:12:58', '超级管理员', '1', '2018-03-11 20:13:06');
