/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 50624
 Source Host           : localhost
 Source Database       : sell

 Target Server Type    : MySQL
 Target Server Version : 50624
 File Encoding         : utf-8

 Date: 08/15/2018 00:33:14 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `good`
-- ----------------------------
DROP TABLE IF EXISTS `good`;
CREATE TABLE `good` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `good`
-- ----------------------------
BEGIN;
INSERT INTO `good` VALUES ('1', 'xxx', '123');
COMMIT;

-- ----------------------------
--  Table structure for `number`
-- ----------------------------
DROP TABLE IF EXISTS `number`;
CREATE TABLE `number` (
  `id` int(11) NOT NULL DEFAULT '0',
  `num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `number`
-- ----------------------------
BEGIN;
INSERT INTO `number` VALUES ('1', '99'), ('2', '100'), ('3', '101'), ('4', '102'), ('5', '103');
COMMIT;

-- ----------------------------
--  Table structure for `order_detail`
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `detail_id` varchar(32) NOT NULL,
  `order_id` varchar(32) NOT NULL,
  `product_id` varchar(32) NOT NULL,
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '当前价格,单位分',
  `product_quantity` int(11) NOT NULL COMMENT '数量',
  `product_icon` varchar(512) DEFAULT NULL COMMENT '小图',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `order_master`
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master` (
  `order_id` varchar(32) NOT NULL,
  `buyer_name` varchar(32) NOT NULL COMMENT '买家名字',
  `buyer_phone` varchar(32) NOT NULL COMMENT '买家电话',
  `buyer_address` varchar(128) NOT NULL COMMENT '买家地址',
  `buyer_openid` varchar(64) NOT NULL COMMENT '买家微信openid',
  `order_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态, 默认为新下单',
  `pay_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付状态, 默认未支付',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_buyer_openid` (`buyer_openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `order_master`
-- ----------------------------
BEGIN;
INSERT INTO `order_master` VALUES ('123451', '胡凯', '15117946727', '北京市海淀区', '72h1xjayshas', '127.00', '0', '0', '2018-02-20 13:38:08', '2018-02-20 14:04:33'), ('123456', '胡凯', '15117946727', '北京市海淀区', '72h1xjayshas', '213.00', '0', '0', '2018-02-20 13:38:08', '2018-02-20 14:04:05'), ('1234567', '邹丽菲', '15117946727', '北京市海淀区', '72h1xjayshas', '178.00', '0', '0', '2018-08-06 17:00:15', '2018-08-06 17:00:15');
COMMIT;

-- ----------------------------
--  Table structure for `product_category`
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(64) NOT NULL COMMENT '类目名字',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `product_category`
-- ----------------------------
BEGIN;
INSERT INTO `product_category` VALUES ('1', '热销榜', '2', '2018-02-16 17:49:44', '2018-02-16 17:49:44'), ('2', '手机', '3', '2018-02-16 17:50:14', '2018-02-18 15:52:14'), ('3', '母婴', '5', '2018-02-16 20:24:30', '2018-02-16 20:24:30'), ('4', '文化用品', '6', '2018-02-16 20:28:18', '2018-02-18 15:58:02'), ('5', '儿童', '7', '2018-02-18 16:06:07', '2018-02-18 16:06:07'), ('6', '图书', '8', '2018-02-18 16:07:36', '2018-02-18 16:07:36'), ('7', '音像', '9', '2018-02-18 16:10:22', '2018-02-18 16:52:41'), ('8', '食品', '10', '2018-02-18 16:11:27', '2018-02-18 16:52:45'), ('10', '测试1', '2', '2018-02-24 10:00:20', '2018-05-18 09:48:05'), ('11', '图书', '8', '2018-08-06 17:00:15', '2018-08-06 17:00:15');
COMMIT;

-- ----------------------------
--  Table structure for `product_info`
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `product_id` varchar(32) NOT NULL,
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '单价',
  `product_stock` int(11) NOT NULL COMMENT '库存',
  `product_description` varchar(64) DEFAULT NULL COMMENT '描述',
  `product_icon` varchar(512) DEFAULT NULL COMMENT '小图',
  `product_status` tinyint(3) DEFAULT '0' COMMENT '商品状态,0正常1下架',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `product_info`
-- ----------------------------
BEGIN;
INSERT INTO `product_info` VALUES ('1518960258347538909', '蛋黄派', '105.00', '30', '产自福建', '/upload/file/pic', '0', '10', '2018-02-18 21:24:20', '2018-02-18 21:24:20'), ('1518960474411411923', '蛋糕', '666.00', '2222', '德国', '/upload/file/pic', '0', '5', '2018-02-18 21:27:56', '2018-02-23 14:45:33'), ('1518960474411411927', 'iphone 8', '1234.00', '2222', '额代工', '/upload/file/pic', '0', '3', '2018-02-18 21:27:56', '2018-02-18 22:07:55'), ('1518960474411411923', '奶粉', '777.00', '2222', '德国', '/upload/file/pic', '0', '5', '2018-02-18 21:27:56', '2018-02-23 14:45:12'), ('1518960474411411929', '巧克力派', '782.00', '1928', '日本', 'file', '0', '17', '2018-03-03 23:47:48', '2018-03-03 23:47:48'), ('1533546014900429278', 'iphone 8', '5000.00', '80', '加州制造', '/upload/file/pic', null, '3', '2018-08-06 17:00:14', '2018-08-06 17:00:14');
COMMIT;

-- ----------------------------
--  Table structure for `seckill`
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
  `name` varchar(14) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀开始时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '秒杀结束时间',
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- ----------------------------
--  Records of `seckill`
-- ----------------------------
BEGIN;
INSERT INTO `seckill` VALUES ('1000', '1000元秒杀iphone6', '100', '2018-01-22 07:41:57', '2016-01-01 00:00:00', '2016-01-02 00:00:00'), ('1001', '800元秒杀ipad', '200', '2018-01-22 07:41:57', '2016-01-01 00:00:00', '2016-01-02 00:00:00'), ('1002', '6600元秒杀', '300', '2018-01-22 07:41:57', '2016-01-01 00:00:00', '2016-01-02 00:00:00'), ('1003', '7000元秒杀iMac', '400', '2018-01-22 07:41:57', '2016-01-01 00:00:00', '2016-01-02 00:00:00');
COMMIT;

-- ----------------------------
--  Table structure for `seller_info`
-- ----------------------------
DROP TABLE IF EXISTS `seller_info`;
CREATE TABLE `seller_info` (
  `id` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `openid` varchar(64) NOT NULL COMMENT '微信openid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='卖家信息表';

-- ----------------------------
--  Table structure for `success_killed`
-- ----------------------------
DROP TABLE IF EXISTS `success_killed`;
CREATE TABLE `success_killed` (
  `seckill_id` bigint(20) NOT NULL COMMENT '秒杀商品ID',
  `user_phone` bigint(20) NOT NULL COMMENT '用户手机号',
  `state` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '状态标识:-1:无效 0:成功 1:已付款 2:已发货',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`,`user_phone`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `usercode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', '111', '111', '111'), ('2', '345', '345', '345'), ('3', 'hukai', 'hukai', 'hukai');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
