/*
Navicat MySQL Data Transfer

Source Server         : kevin_schema
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : kevindevice

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2018-08-22 19:03:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_device`
-- ----------------------------
DROP TABLE IF EXISTS `t_device`;
CREATE TABLE `t_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备id',
  `device_name` varchar(20) DEFAULT NULL COMMENT '设备名',
  `device_type` enum('ezviz','GB','ehome') NOT NULL COMMENT '设备类型',
  `device_channel` varchar(255) DEFAULT NULL COMMENT '设备通道号',
  `device_no` varchar(255) DEFAULT NULL COMMENT '设备编号',
  `ctime` datetime DEFAULT NULL COMMENT '上线时间',
  `utime` datetime DEFAULT NULL COMMENT '最近一次操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='设备表';

-- ----------------------------
-- Records of t_device
-- ----------------------------
INSERT INTO `t_device` VALUES ('1', 'zncucu', 'ezviz', 'hsais', 'asas', '2018-08-19 00:01:41', '2018-08-20 15:23:30');
INSERT INTO `t_device` VALUES ('2', 'znznznzn', 'ehome', 'sdasda', 'asdasd', '2018-08-19 00:33:11', '2018-08-19 00:33:13');
INSERT INTO `t_device` VALUES ('3', 'dddddddddddddddd', 'GB', 'aaaaaaaaaaaaa', '435345', '2018-08-22 11:32:01', '2018-08-22 11:32:01');
INSERT INTO `t_device` VALUES ('4', 'dfsdfsfdsf', 'GB', 'dddddd', 'ddddddd', '2018-08-20 10:53:43', '2018-08-20 10:53:43');
INSERT INTO `t_device` VALUES ('5', 'dsf', 'ehome', 'dsfdsfdsf67', 'sdfdsfdsfdfds', '2018-08-19 14:22:54', '2018-08-20 19:53:28');
INSERT INTO `t_device` VALUES ('6', 'qweqwewe', 'ehome', '344345', '9999999999999999', '2018-08-19 14:05:45', '2018-08-20 10:54:06');
INSERT INTO `t_device` VALUES ('7', 'zncucucu', 'GB', '78787878878', '卢欣桐520', '2018-08-19 14:22:39', '2018-08-19 14:22:39');
INSERT INTO `t_device` VALUES ('8', 'zzzzzzzzzzzzzzzzzzzz', 'ehome', '33333333', '6tyiuho', '2018-08-19 15:59:28', '2018-08-21 15:22:49');
INSERT INTO `t_device` VALUES ('9', 'zn', 'ehome', '7898898', '32423423423', '2018-08-20 09:29:01', '2018-08-20 21:28:12');
INSERT INTO `t_device` VALUES ('10', 'zzzzzzzzzzzzzzzzzzz', 'ezviz', '80980909809', '46465646', '2018-08-20 15:05:53', '2018-08-20 15:05:53');
INSERT INTO `t_device` VALUES ('11', 'DAASD', 'ehome', 'KEVIN', '', '2018-08-20 15:26:16', '2018-08-20 15:26:16');
INSERT INTO `t_device` VALUES ('12', 'kevin-Zncu', 'GB', '4566465', '4564654665', '2018-08-20 18:25:49', '2018-08-20 18:25:49');
INSERT INTO `t_device` VALUES ('13', 'asdasdasasdsadasd', 'GB', '45444444444444444444', '44444444444444444', '2018-08-20 18:28:27', '2018-08-20 21:28:49');
INSERT INTO `t_device` VALUES ('14', 'asdksdasdasdasdasdas', 'ezviz', '111111111111111111111', '11111111111111111111111111111', '2018-08-20 18:31:11', '2018-08-20 18:31:11');
INSERT INTO `t_device` VALUES ('15', 'saaaaaaaaaa', 'ezviz', '121164564', 'hsdaihdsad', '2018-08-20 20:52:01', '2018-08-20 20:52:01');

-- ----------------------------
-- Table structure for `t_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `icon` varchar(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `p_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=605302 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1', 'menu-plugin', '系统菜单', '1', null, '-1');
INSERT INTO `t_menu` VALUES ('10', '&#xe68e;', '资源管理', '1', null, '1');
INSERT INTO `t_menu` VALUES ('60', '&#xe631;', '系统管理', '1', null, '1');
INSERT INTO `t_menu` VALUES ('61', '&#xe705;', '新闻资讯', '1', 'https://ieeexplore.ieee.org', '1');
INSERT INTO `t_menu` VALUES ('6000', '&#xe631;', '菜单管理', '2', 'admin/menu/tomunemanage', '60');
INSERT INTO `t_menu` VALUES ('6010', 'icon-icon10', '角色管理', '2', 'admin/role/torolemanage', '60');
INSERT INTO `t_menu` VALUES ('6020', '&#xe612;', '用户管理', '2', 'admin/user/tousermanage', '60');
INSERT INTO `t_menu` VALUES ('6030', '&#xe631;', 'sql监控', '2', 'druid/index.html', '60');
INSERT INTO `t_menu` VALUES ('6040', 'icon-ziliao', '修改密码', '2', 'admin/user/toUpdatePassword', '60');
INSERT INTO `t_menu` VALUES ('6050', 'icon-tuichu', '安全退出', '2', 'user/logout', '60');
INSERT INTO `t_menu` VALUES ('6051', '&#xe631', '设备管理', '2', 'admin/device/todevicemanage', '10');
INSERT INTO `t_menu` VALUES ('6052', 'icon-vip4', '设备查询与管理', '2', 'admin/userdevice/todevicemanage', '10');
INSERT INTO `t_menu` VALUES ('6100', 'icon-text', '海康威视', '2', 'https://www.hikvision.com/cn/', '61');
INSERT INTO `t_menu` VALUES ('6101', 'icon-text', 'dsds', '2', 'asasd', '61');
INSERT INTO `t_menu` VALUES ('200000', '44', '44', '3', '44', '2000');

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bz` varchar(1000) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '系统管理员 最高权限', '管理员', null);
INSERT INTO `t_role` VALUES ('2', '主管', '主管', null);
INSERT INTO `t_role` VALUES ('4', '采购员', '采购员', null);
INSERT INTO `t_role` VALUES ('5', '销售经理', '销售经理', '22');
INSERT INTO `t_role` VALUES ('7', '仓库管理员', '仓库管理员', null);
INSERT INTO `t_role` VALUES ('9', '总经理', '总经理', null);
INSERT INTO `t_role` VALUES ('15', '111111', '111111', null);
INSERT INTO `t_role` VALUES ('16', '222', '222222', null);
INSERT INTO `t_role` VALUES ('17', '33', '333333', null);
INSERT INTO `t_role` VALUES ('18', '444444', '444444', null);
INSERT INTO `t_role` VALUES ('19', '555', '555555', null);
INSERT INTO `t_role` VALUES ('20', '666666', '666666', null);
INSERT INTO `t_role` VALUES ('21', 'zncu', '东南女神~', null);

-- ----------------------------
-- Table structure for `t_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=378 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES ('45', '1', '4');
INSERT INTO `t_role_menu` VALUES ('48', '1', '5');
INSERT INTO `t_role_menu` VALUES ('55', '1', '9');
INSERT INTO `t_role_menu` VALUES ('65', '1', '7');
INSERT INTO `t_role_menu` VALUES ('66', '10', '7');
INSERT INTO `t_role_menu` VALUES ('126', '60', '15');
INSERT INTO `t_role_menu` VALUES ('127', '6010', '15');
INSERT INTO `t_role_menu` VALUES ('128', '6020', '15');
INSERT INTO `t_role_menu` VALUES ('129', '6030', '15');
INSERT INTO `t_role_menu` VALUES ('130', '6040', '15');
INSERT INTO `t_role_menu` VALUES ('131', '6050', '15');
INSERT INTO `t_role_menu` VALUES ('248', '2000', '1');
INSERT INTO `t_role_menu` VALUES ('259', '100000', '1');
INSERT INTO `t_role_menu` VALUES ('319', '1000', '1');
INSERT INTO `t_role_menu` VALUES ('339', '1000', '2');
INSERT INTO `t_role_menu` VALUES ('345', '10', '2');
INSERT INTO `t_role_menu` VALUES ('346', '6052', '2');
INSERT INTO `t_role_menu` VALUES ('347', '60', '2');
INSERT INTO `t_role_menu` VALUES ('348', '6020', '2');
INSERT INTO `t_role_menu` VALUES ('349', '6040', '2');
INSERT INTO `t_role_menu` VALUES ('350', '6050', '2');
INSERT INTO `t_role_menu` VALUES ('351', '61', '2');
INSERT INTO `t_role_menu` VALUES ('352', '6100', '2');
INSERT INTO `t_role_menu` VALUES ('353', '10', '1');
INSERT INTO `t_role_menu` VALUES ('354', '6051', '1');
INSERT INTO `t_role_menu` VALUES ('355', '6052', '1');
INSERT INTO `t_role_menu` VALUES ('357', '605300', '1');
INSERT INTO `t_role_menu` VALUES ('358', '60', '1');
INSERT INTO `t_role_menu` VALUES ('359', '6000', '1');
INSERT INTO `t_role_menu` VALUES ('360', '6010', '1');
INSERT INTO `t_role_menu` VALUES ('361', '6020', '1');
INSERT INTO `t_role_menu` VALUES ('362', '6030', '1');
INSERT INTO `t_role_menu` VALUES ('363', '6040', '1');
INSERT INTO `t_role_menu` VALUES ('364', '6050', '1');
INSERT INTO `t_role_menu` VALUES ('365', '61', '1');
INSERT INTO `t_role_menu` VALUES ('366', '6100', '1');
INSERT INTO `t_role_menu` VALUES ('367', '10', '21');
INSERT INTO `t_role_menu` VALUES ('368', '6052', '21');
INSERT INTO `t_role_menu` VALUES ('369', '60', '21');
INSERT INTO `t_role_menu` VALUES ('370', '6010', '21');
INSERT INTO `t_role_menu` VALUES ('371', '6020', '21');
INSERT INTO `t_role_menu` VALUES ('372', '6030', '21');
INSERT INTO `t_role_menu` VALUES ('373', '6040', '21');
INSERT INTO `t_role_menu` VALUES ('374', '6050', '21');
INSERT INTO `t_role_menu` VALUES ('375', '61', '21');
INSERT INTO `t_role_menu` VALUES ('376', '6100', '21');
INSERT INTO `t_role_menu` VALUES ('377', '6101', '21');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bz` varchar(1000) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `true_name` varchar(50) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '管理员', '1', '金余概', 'admin', '1');
INSERT INTO `t_user` VALUES ('2', '主管', '123', '王大锤', 'jack', '2');
INSERT INTO `t_user` VALUES ('5', '1', '111111', '1', '111111', null);
INSERT INTO `t_user` VALUES ('6', '2', '222222', '2', '222222', null);
INSERT INTO `t_user` VALUES ('7', '3', '333333', '3', '333333', null);
INSERT INTO `t_user` VALUES ('8', '', '444444', '44', '444444', null);
INSERT INTO `t_user` VALUES ('9', '5', '555555', '5', '555555', null);
INSERT INTO `t_user` VALUES ('11', '7', '777777', '7', '777777', null);
INSERT INTO `t_user` VALUES ('12', '88', '888888', '88', '888888', null);
INSERT INTO `t_user` VALUES ('13', '9', '999999', '9', '999999', null);
INSERT INTO `t_user` VALUES ('14', '121212', '121212', '121212', '121212', null);
INSERT INTO `t_user` VALUES ('15', '女神', '520520', '卢筒子', 'Zncucu', null);
INSERT INTO `t_user` VALUES ('16', '编辑测试', '111111', 'keivn', 'kevin818', null);

-- ----------------------------
-- Table structure for `t_user_device`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_device`;
CREATE TABLE `t_user_device` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `device_id` int(11) NOT NULL COMMENT '设备id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8 COMMENT='用户设备关系图';

-- ----------------------------
-- Records of t_user_device
-- ----------------------------
INSERT INTO `t_user_device` VALUES ('2', '5', '2');
INSERT INTO `t_user_device` VALUES ('3', '1', '1');
INSERT INTO `t_user_device` VALUES ('4', '2', '1');
INSERT INTO `t_user_device` VALUES ('5', '5', '1');
INSERT INTO `t_user_device` VALUES ('7', '7', '1');
INSERT INTO `t_user_device` VALUES ('8', '8', '1');
INSERT INTO `t_user_device` VALUES ('9', '11', '1');
INSERT INTO `t_user_device` VALUES ('10', '12', '1');
INSERT INTO `t_user_device` VALUES ('18', '1', '7');
INSERT INTO `t_user_device` VALUES ('19', '11', '7');
INSERT INTO `t_user_device` VALUES ('20', '12', '7');
INSERT INTO `t_user_device` VALUES ('21', '14', '7');
INSERT INTO `t_user_device` VALUES ('22', '1', '9');
INSERT INTO `t_user_device` VALUES ('23', '2', '9');
INSERT INTO `t_user_device` VALUES ('24', '5', '9');
INSERT INTO `t_user_device` VALUES ('25', '6', '9');
INSERT INTO `t_user_device` VALUES ('26', '1', '6');
INSERT INTO `t_user_device` VALUES ('27', '2', '6');
INSERT INTO `t_user_device` VALUES ('28', '9', '6');
INSERT INTO `t_user_device` VALUES ('29', '11', '6');
INSERT INTO `t_user_device` VALUES ('30', '12', '6');
INSERT INTO `t_user_device` VALUES ('31', '13', '6');
INSERT INTO `t_user_device` VALUES ('32', '14', '6');
INSERT INTO `t_user_device` VALUES ('33', '5', '6');
INSERT INTO `t_user_device` VALUES ('34', '6', '6');
INSERT INTO `t_user_device` VALUES ('35', '7', '6');
INSERT INTO `t_user_device` VALUES ('41', '1', '8');
INSERT INTO `t_user_device` VALUES ('42', '2', '8');
INSERT INTO `t_user_device` VALUES ('43', '5', '8');
INSERT INTO `t_user_device` VALUES ('44', '6', '8');
INSERT INTO `t_user_device` VALUES ('45', '7', '8');
INSERT INTO `t_user_device` VALUES ('46', '9', '8');
INSERT INTO `t_user_device` VALUES ('47', '11', '8');
INSERT INTO `t_user_device` VALUES ('48', '12', '8');
INSERT INTO `t_user_device` VALUES ('49', '1', '12');
INSERT INTO `t_user_device` VALUES ('53', '1', '13');
INSERT INTO `t_user_device` VALUES ('54', '5', '14');
INSERT INTO `t_user_device` VALUES ('55', '1', '4');
INSERT INTO `t_user_device` VALUES ('56', '2', '4');
INSERT INTO `t_user_device` VALUES ('57', '5', '4');
INSERT INTO `t_user_device` VALUES ('58', '6', '4');
INSERT INTO `t_user_device` VALUES ('59', '7', '4');
INSERT INTO `t_user_device` VALUES ('60', '1', '15');
INSERT INTO `t_user_device` VALUES ('61', '1', '3');
INSERT INTO `t_user_device` VALUES ('62', '2', '3');
INSERT INTO `t_user_device` VALUES ('63', '5', '3');
INSERT INTO `t_user_device` VALUES ('64', '6', '3');
INSERT INTO `t_user_device` VALUES ('65', '8', '3');
INSERT INTO `t_user_device` VALUES ('66', '9', '3');
INSERT INTO `t_user_device` VALUES ('68', '2', '5');
INSERT INTO `t_user_device` VALUES ('69', '5', '5');
INSERT INTO `t_user_device` VALUES ('70', '8', '5');
INSERT INTO `t_user_device` VALUES ('71', '1', '5');
INSERT INTO `t_user_device` VALUES ('72', '6', '5');

-- ----------------------------
-- Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=916 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1', '1');
INSERT INTO `t_user_role` VALUES ('28', '2', '3');
INSERT INTO `t_user_role` VALUES ('29', '4', '3');
INSERT INTO `t_user_role` VALUES ('30', '5', '3');
INSERT INTO `t_user_role` VALUES ('31', '7', '3');
INSERT INTO `t_user_role` VALUES ('890', '4', '2');
INSERT INTO `t_user_role` VALUES ('891', '5', '2');
INSERT INTO `t_user_role` VALUES ('892', '2', '2');
INSERT INTO `t_user_role` VALUES ('893', '1', '2');
INSERT INTO `t_user_role` VALUES ('894', '7', '2');
INSERT INTO `t_user_role` VALUES ('895', '9', '2');
INSERT INTO `t_user_role` VALUES ('896', '15', '2');
INSERT INTO `t_user_role` VALUES ('897', '16', '2');
INSERT INTO `t_user_role` VALUES ('898', '17', '2');
INSERT INTO `t_user_role` VALUES ('899', '18', '2');
INSERT INTO `t_user_role` VALUES ('900', '19', '2');
INSERT INTO `t_user_role` VALUES ('901', '20', '2');
INSERT INTO `t_user_role` VALUES ('902', '1', '7');
INSERT INTO `t_user_role` VALUES ('903', '2', '7');
INSERT INTO `t_user_role` VALUES ('904', '7', '7');
INSERT INTO `t_user_role` VALUES ('905', '9', '7');
INSERT INTO `t_user_role` VALUES ('906', '1', '5');
INSERT INTO `t_user_role` VALUES ('907', '15', '5');
INSERT INTO `t_user_role` VALUES ('908', '2', '5');
INSERT INTO `t_user_role` VALUES ('909', '1', '6');
INSERT INTO `t_user_role` VALUES ('910', '2', '6');
INSERT INTO `t_user_role` VALUES ('911', '7', '8');
INSERT INTO `t_user_role` VALUES ('912', '9', '8');
INSERT INTO `t_user_role` VALUES ('913', '9', '11');
INSERT INTO `t_user_role` VALUES ('914', '19', '11');
INSERT INTO `t_user_role` VALUES ('915', '21', '15');
