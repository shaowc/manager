/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50713
 Source Host           : localhost
 Source Database       : smarth

 Target Server Type    : MySQL
 Target Server Version : 50713
 File Encoding         : utf-8

 Date: 09/28/2016 12:50:05 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `CK_CHECKER`
-- ----------------------------
DROP TABLE IF EXISTS `CK_CHECKER`;
CREATE TABLE `CK_CHECKER` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `gender` int(11) NOT NULL COMMENT '0 未知, 1 男 ，2 女',
  `birthday` varchar(10) DEFAULT NULL COMMENT '出生日期 yyyy-MM-dd',
  `identify_pic` int(11) NOT NULL COMMENT '身份证照片',
  `identify_pic1` int(11) DEFAULT NULL,
  `identify_pic2` int(255) DEFAULT NULL COMMENT '身份证反面',
  `qualification_pic` int(255) DEFAULT NULL COMMENT '资格证照片',
  `description` varchar(255) NOT NULL COMMENT '介绍',
  `longitude` varchar(20) DEFAULT NULL,
  `latitude` varchar(20) DEFAULT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `CK_IMAGE`
-- ----------------------------
DROP TABLE IF EXISTS `CK_IMAGE`;
CREATE TABLE `CK_IMAGE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img` mediumblob NOT NULL COMMENT 'base64 编码的图片内容',
  `gmt_create` datetime NOT NULL,
  `gmt_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `CK_ITEM`
-- ----------------------------
DROP TABLE IF EXISTS `CK_ITEM`;
CREATE TABLE `CK_ITEM` (
  `id` int(11) NOT NULL,
  `packet_id` int(11) NOT NULL COMMENT '体检包ID',
  `name` varchar(20) NOT NULL COMMENT '体检项目名称',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '-1:删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `CK_MEMBER_CHECKER`
-- ----------------------------
DROP TABLE IF EXISTS `CK_MEMBER_CHECKER`;
CREATE TABLE `CK_MEMBER_CHECKER` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) NOT NULL,
  `checker_id` bigint(20) NOT NULL,
  `status` int(11) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `CK_MEMBER_PACKET`
-- ----------------------------
DROP TABLE IF EXISTS `CK_MEMBER_PACKET`;
CREATE TABLE `CK_MEMBER_PACKET` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `packet_id` int(11) NOT NULL,
  `member_id` bigint(11) NOT NULL COMMENT '检手会员ID',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态，0：未支付，1：已支付，-1：已删除',
  `gmt_create` datetime NOT NULL,
  `gmt_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `CK_ORDER`
-- ----------------------------
DROP TABLE IF EXISTS `CK_ORDER`;
CREATE TABLE `CK_ORDER` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) NOT NULL COMMENT '用户会员ID',
  `open_id` varchar(50) DEFAULT NULL,
  `checker_id` bigint(20) NOT NULL COMMENT '快检手会员ID',
  `packet_id` int(11) NOT NULL COMMENT '体检包ID',
  `price` int(11) NOT NULL COMMENT '体检包价格',
  `package_content` varchar(255) NOT NULL COMMENT '体检项目列表，多项以英文,分隔',
  `checkup_result` varchar(500) DEFAULT NULL COMMENT '检查结果，json字符串{"体检项目":"值"}',
  `checkup_remark` varchar(255) DEFAULT NULL,
  `checkup_time` datetime NOT NULL COMMENT '预约体检时间',
  `city` varchar(50) DEFAULT NULL COMMENT '定位获取，省市区。',
  `address` varchar(255) NOT NULL COMMENT '详细地址',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '订单状态，0：待接单，1：已接单，2：已支付，3：已完成，4：超时关闭，已取消',
  `out_trade_no` varchar(32) DEFAULT NULL COMMENT '商户系统内部的订单号,32个字符内、可包含字母',
  `prepay_id` varchar(64) DEFAULT NULL COMMENT '预支付交易会话标识，该值2小时内有效',
  `gmt_prepay_id_valid` datetime DEFAULT NULL,
  `pay_status` int(11) NOT NULL DEFAULT '0' COMMENT '0:未支付，1：已支付',
  `gmt_pay` datetime DEFAULT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `CK_PACKET`
-- ----------------------------
DROP TABLE IF EXISTS `CK_PACKET`;
CREATE TABLE `CK_PACKET` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '体检包名称',
  `description` varchar(255) NOT NULL COMMENT '体检包描述，说明',
  `content` varchar(255) NOT NULL COMMENT '体检项目，多项意英文,分隔',
  `price` int(11) NOT NULL DEFAULT '0' COMMENT '体检包价格',
  `gmt_create` datetime NOT NULL,
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `MANAGER_EMPLOYEE`
-- ----------------------------
DROP TABLE IF EXISTS `MANAGER_EMPLOYEE`;
CREATE TABLE `MANAGER_EMPLOYEE` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '后台用户id',
  `real_name` varchar(50) NOT NULL COMMENT '真实姓名',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '花名',
  `emp_num` varchar(25) NOT NULL COMMENT '工号',
  `gender` int(2) DEFAULT NULL COMMENT '1 男 ，2 女',
  `birthday` varchar(50) DEFAULT NULL COMMENT '生日 yyyy-MM-dd',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `email` varchar(255) NOT NULL COMMENT '邮箱地址',
  `mobile` varchar(40) DEFAULT NULL COMMENT '手机',
  `province_id` int(11) DEFAULT NULL COMMENT '省份',
  `city_id` int(11) DEFAULT NULL COMMENT '城市',
  `district_id` int(11) DEFAULT NULL COMMENT '县区',
  `job_title` varchar(50) DEFAULT NULL COMMENT '职位',
  `entry_date` datetime DEFAULT NULL COMMENT '入职时间',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `pwd_version` datetime NOT NULL COMMENT '密码修改时间，作为版本，在多处登录的情况下可以在一处修改密码，其它登录状态全失效',
  `status` int(11) DEFAULT NULL COMMENT '在职状态, 1 在职， -1 离职',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` timestamp NULL DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `emp_num_idx` (`emp_num`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `MANAGER_EMPLOYEE_OPERATION`
-- ----------------------------
DROP TABLE IF EXISTS `MANAGER_EMPLOYEE_OPERATION`;
CREATE TABLE `MANAGER_EMPLOYEE_OPERATION` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `emp_id` int(11) NOT NULL,
  `emp_num` varchar(50) DEFAULT NULL COMMENT '工号',
  `emp_name` varchar(50) DEFAULT NULL COMMENT '员工姓名',
  `code` varchar(100) NOT NULL COMMENT '权限code',
  `gmt_create` datetime NOT NULL COMMENT '操作时间',
  `ip` varchar(100) DEFAULT NULL COMMENT '操作时ip地址',
  `params` text COMMENT '参数列表',
  `url` varchar(255) DEFAULT NULL COMMENT '访问url',
  `domain` varchar(100) DEFAULT NULL COMMENT '域名',
  PRIMARY KEY (`id`),
  KEY `emp_id_idx` (`emp_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `MANAGER_EMPLOYEE_PERMISSION`
-- ----------------------------
DROP TABLE IF EXISTS `MANAGER_EMPLOYEE_PERMISSION`;
CREATE TABLE `MANAGER_EMPLOYEE_PERMISSION` (
  `emp_id` int(11) NOT NULL COMMENT '员工id，非工号',
  `permissions` text NOT NULL COMMENT '权限id列表，以，分割',
  `channels` text NOT NULL COMMENT '权限包含的频道',
  `menus` text COMMENT '权限包含的一级菜单',
  `authorizer` int(11) NOT NULL COMMENT '授权人id',
  `gmt_create` datetime NOT NULL COMMENT '生成时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `MANAGER_PERMISSION`
-- ----------------------------
DROP TABLE IF EXISTS `MANAGER_PERMISSION`;
CREATE TABLE `MANAGER_PERMISSION` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `code` varchar(100) NOT NULL COMMENT '权限code , 用于权限检验, 格式 模块:功能, 如 account:list',
  `name` varchar(100) NOT NULL COMMENT '功能名称',
  `parent` int(11) NOT NULL COMMENT '父节点, 频道节点为 0',
  `root` int(11) NOT NULL COMMENT '挂载的频道id , 频道节点为0',
  `type` int(11) DEFAULT NULL COMMENT '类型,1频道，2模块，3功能，4子功能',
  `show` int(1) DEFAULT NULL COMMENT '是否显示, 默认0，不显示，1 显示',
  `base` int(1) DEFAULT '0' COMMENT '0可以隐藏，1不能隐藏',
  `domain` varchar(100) DEFAULT NULL COMMENT '域名，有可能需要在不同域名的功能',
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址，对于第三级，第四级是必须的',
  `gmt_create` datetime NOT NULL COMMENT '生成时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_idx` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `MEMBER`
-- ----------------------------
DROP TABLE IF EXISTS `MEMBER`;
CREATE TABLE `MEMBER` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id，会员id',
  `user_name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '用户名',
  `real_name` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '密码 md5',
  `avatar` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '头像链接',
  `gender` int(3) DEFAULT NULL COMMENT '0 未知, 1 男 ，2 女 ',
  `birthday` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '生日 yyyy-MM-dd',
  `mobile` varchar(25) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '邮件地址',
  `weixin_id` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '绑定的微信id',
  `province_id` int(11) DEFAULT NULL COMMENT '省份id',
  `city_id` int(11) DEFAULT NULL COMMENT '城市id',
  `county_id` int(11) DEFAULT NULL COMMENT '县区id',
  `longitude` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '经度',
  `latitude` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '纬度',
  `features` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户属性列表, 以,分隔，如检手，普通用户等',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1 正常, 2 禁用',
  `gmt_create` datetime NOT NULL COMMENT '注册时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_idx` (`user_name`) USING BTREE COMMENT '用户名索引',
  UNIQUE KEY `mobile_idx` (`mobile`) USING BTREE,
  UNIQUE KEY `weixin_idx` (`weixin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=842506 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
--  Table structure for `MEMBER_CHECKUP_ADDRESS`
-- ----------------------------
DROP TABLE IF EXISTS `MEMBER_CHECKUP_ADDRESS`;
CREATE TABLE `MEMBER_CHECKUP_ADDRESS` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) NOT NULL,
  `province_id` int(11) NOT NULL,
  `city_id` int(11) NOT NULL,
  `county_id` int(11) NOT NULL,
  `address` varchar(255) NOT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `MEMBER_LOCATION`
-- ----------------------------
DROP TABLE IF EXISTS `MEMBER_LOCATION`;
CREATE TABLE `MEMBER_LOCATION` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) DEFAULT NULL,
  `province` varchar(10) DEFAULT NULL,
  `city` varchar(10) DEFAULT NULL,
  `county` varchar(20) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `longitude` decimal(10,7) DEFAULT NULL,
  `latitude` decimal(10,7) DEFAULT NULL,
  `type` int(11) DEFAULT '0' COMMENT '0:普通用户，1：快检手',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modify` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `MEMBER_WECHAT`
-- ----------------------------
DROP TABLE IF EXISTS `MEMBER_WECHAT`;
CREATE TABLE `MEMBER_WECHAT` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `app` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '指定的app，如union',
  `open_id` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '微信openId',
  `member_id` bigint(20) DEFAULT NULL COMMENT '所属用户id',
  `subscribe` int(2) NOT NULL COMMENT '是否关注',
  `nick_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '昵称',
  `sex` int(3) DEFAULT NULL COMMENT '性别，1男，2女，0未知',
  `city` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '市',
  `province` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '省',
  `country` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '国家',
  `language` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '语言',
  `head_img_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
  `subscribe_time` datetime DEFAULT NULL COMMENT '关注事件',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
--  Table structure for `SMS_BLACK_LIST`
-- ----------------------------
DROP TABLE IF EXISTS `SMS_BLACK_LIST`;
CREATE TABLE `SMS_BLACK_LIST` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `reason` varchar(100) DEFAULT NULL COMMENT '原因',
  `status` int(2) DEFAULT NULL COMMENT '状态-1 删除，1:有效',
  `gmt_create` datetime NOT NULL COMMENT '记录创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '记录修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信黑名单';

-- ----------------------------
--  Table structure for `SMS_CHANEL`
-- ----------------------------
DROP TABLE IF EXISTS `SMS_CHANEL`;
CREATE TABLE `SMS_CHANEL` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '通道名称',
  `chanel_provider` int(4) NOT NULL COMMENT '1:建周',
  `account` varchar(50) NOT NULL COMMENT '通道账户',
  `password` varchar(50) NOT NULL COMMENT '通道密码',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人Id',
  `modify_user` int(11) DEFAULT NULL COMMENT '修改人Id',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信通道';

-- ----------------------------
--  Table structure for `SMS_SEND_LOG`
-- ----------------------------
DROP TABLE IF EXISTS `SMS_SEND_LOG`;
CREATE TABLE `SMS_SEND_LOG` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `content` varchar(200) NOT NULL COMMENT '短信内容',
  `template_id` int(11) NOT NULL COMMENT '模板ID',
  `send_time` datetime NOT NULL COMMENT '发送时间',
  `return_value` varchar(10) DEFAULT NULL COMMENT '返回值',
  `status` int(1) NOT NULL COMMENT '1:发送平台成功  2：系统异常，未发给送短信平台',
  `gmt_create` datetime NOT NULL COMMENT '记录创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '记录修改时间',
  `chanel_provider` int(4) DEFAULT NULL,
  `chanel_id` int(11) DEFAULT NULL,
  `chanel_account` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信发送日志';

-- ----------------------------
--  Table structure for `SMS_TEMPLATE`
-- ----------------------------
DROP TABLE IF EXISTS `SMS_TEMPLATE`;
CREATE TABLE `SMS_TEMPLATE` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '模板名称',
  `chanel_id` int(11) NOT NULL COMMENT '通道ID',
  `content` varchar(200) NOT NULL COMMENT '模板内容',
  `status` smallint(2) NOT NULL COMMENT '状态 1：正常，-1：删除',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人',
  `modify_user` int(11) DEFAULT NULL COMMENT '最近修改人',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modify` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信模板表';

-- ----------------------------
--  Table structure for `SMS_VERIFY_CODE`
-- ----------------------------
DROP TABLE IF EXISTS `SMS_VERIFY_CODE`;
CREATE TABLE `SMS_VERIFY_CODE` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `identifier` varchar(32) NOT NULL COMMENT '验证码唯一标识',
  `code` varchar(10) NOT NULL COMMENT '验证码',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0:有效，1：过期',
  `gmt_create` datetime NOT NULL COMMENT '生成时间',
  `gmt_modify` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `SMS_VERIFY_CODE_IDENTIFIER` (`identifier`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `XT_XZQH`
-- ----------------------------
DROP TABLE IF EXISTS `XT_XZQH`;
CREATE TABLE `XT_XZQH` (
  `code` int(6) NOT NULL COMMENT '行政区划代码',
  `name` varchar(50) DEFAULT NULL COMMENT '行政区划名称',
  `level` tinyint(4) DEFAULT NULL COMMENT '国标行政区划等级',
  `parent_code` int(6) DEFAULT NULL COMMENT '国标上级行政区划',
  `display` tinyint(4) DEFAULT NULL COMMENT '是否显示，0：否，1：是',
  `display_level` tinyint(4) DEFAULT NULL COMMENT '显示等级，过滤市辖区，省直辖县级行政区划',
  `display_parent_code` int(6) DEFAULT NULL COMMENT '显示查询的上级行政区划code',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区划';

SET FOREIGN_KEY_CHECKS = 1;
