/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : miaowu

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-05-16 01:08:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for catinfo
-- ----------------------------
DROP TABLE IF EXISTS `catinfo`;
CREATE TABLE `catinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL COMMENT '品种',
  `sex` int(1) DEFAULT '0' COMMENT '性别',
  `age` int(2) DEFAULT '0' COMMENT '年龄',
  `neutering` int(1) DEFAULT '0' COMMENT '是否绝育',
  `insecticide` int(1) DEFAULT '0' COMMENT '是否驱虫',
  `adddate` date NOT NULL,
  `conditions` text COMMENT '领养条件 自定义',
  `isAdopted` int(1) NOT NULL DEFAULT '0' COMMENT '领养标志位 1=已被领养 ',
  `adoptdate` date DEFAULT NULL COMMENT '被领养日期',
  `uid` int(11) DEFAULT '0' COMMENT '领养者编号',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of catinfo
-- ----------------------------
INSERT INTO `catinfo` VALUES ('22', '波斯猫', '0', '0', '0', '0', '2018-04-16', '默认', '1', '2018-04-17', '20');
INSERT INTO `catinfo` VALUES ('23', '黑猫', '1', '3', '1', '1', '2018-04-17', 'warmhearted and kind.', '1', '2018-04-17', '20');
INSERT INTO `catinfo` VALUES ('25', '花猫', '1', '2', '1', '0', '2018-04-17', 'has free time and patient.', '1', '2018-04-17', '20');
INSERT INTO `catinfo` VALUES ('26', '啦啦啦', '0', '0', '0', '0', '2018-05-10', '有空陪猫咪', '1', '2018-05-10', '23');
INSERT INTO `catinfo` VALUES ('27', 'sww', '1', '0', '2', '0', '2018-05-15', '热心肠', '1', '2018-05-15', '22');
INSERT INTO `catinfo` VALUES ('28', '', '0', '0', '0', '0', '2018-05-15', '1', '0', '2018-05-15', '0');

-- ----------------------------
-- Table structure for cat_photo
-- ----------------------------
DROP TABLE IF EXISTS `cat_photo`;
CREATE TABLE `cat_photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cat_photo
-- ----------------------------
INSERT INTO `cat_photo` VALUES ('4', '22', '22_0.png');
INSERT INTO `cat_photo` VALUES ('5', '22', '22_1.png');
INSERT INTO `cat_photo` VALUES ('6', '23', '23_0.png');
INSERT INTO `cat_photo` VALUES ('7', '23', '23_1.png');
INSERT INTO `cat_photo` VALUES ('8', '24', '24_0.png');
INSERT INTO `cat_photo` VALUES ('9', '24', '24_1.png');
INSERT INTO `cat_photo` VALUES ('10', '25', '25_0.png');
INSERT INTO `cat_photo` VALUES ('11', '25', '25_1.png');
INSERT INTO `cat_photo` VALUES ('12', '26', '26_0.png');
INSERT INTO `cat_photo` VALUES ('13', '27', '27_0.png');
INSERT INTO `cat_photo` VALUES ('14', '27', '27_1.png');
INSERT INTO `cat_photo` VALUES ('15', '27', '27_2.png');
INSERT INTO `cat_photo` VALUES ('16', '28', '28_0.png');
INSERT INTO `cat_photo` VALUES ('17', '28', '28_1.png');

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid1` int(11) NOT NULL COMMENT '主动关注',
  `uid2` int(11) NOT NULL COMMENT '被关注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of follow
-- ----------------------------
INSERT INTO `follow` VALUES ('24', '20', '1');
INSERT INTO `follow` VALUES ('25', '20', '21');
INSERT INTO `follow` VALUES ('32', '1', '20');
INSERT INTO `follow` VALUES ('39', '1', '22');
INSERT INTO `follow` VALUES ('40', '1', '24');
INSERT INTO `follow` VALUES ('41', '24', '1');

-- ----------------------------
-- Table structure for note
-- ----------------------------
DROP TABLE IF EXISTS `note`;
CREATE TABLE `note` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '发帖者的id',
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `publish` datetime NOT NULL,
  `viewcount` int(11) DEFAULT '0',
  `thumbs` int(11) DEFAULT '0',
  `verified` int(1) NOT NULL DEFAULT '0' COMMENT '0=待审核 1=审核通过 2=审核失败 3=锁定',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of note
-- ----------------------------
INSERT INTO `note` VALUES ('76', '20', 'nihao', 'good morning.\nWhat\'s your name?\n秋风起兮白云飞，草木黄落兮雁南归。\n兰有秀兮菊有芳，怀佳人兮不能忘。\n泛楼船兮济汾河，横中流兮扬素波。\n箫鼓鸣兮发棹歌，欢乐极兮哀情多。\n少壮几时兮奈老何！当年万里觅封侯，匹马戍梁州。关河梦断何处？尘暗旧貂裘。\r\n胡未灭，鬓先秋，泪空流。此生谁料，心在天山，身老沧洲。中秋作本名小秦王，入腔即阳关曲\r\n\r\n暮云收尽溢清寒，银汉无声转玉盘。\r\n此生此夜不长好，明月明年何处看。', '2018-04-16 03:03:04', '56', '7', '1');
INSERT INTO `note` VALUES ('77', '1', 'welcome', 'Welcome to miaowu ,\nyou can view cat\'s information in this application and post notes too,\nhave a nice day.', '2018-04-17 00:51:36', '16', '4', '1');
INSERT INTO `note` VALUES ('78', '20', 'new', 'hello world!', '2018-04-17 09:53:48', '6', '1', '1');
INSERT INTO `note` VALUES ('79', '1', 'slkslkkm', 'shdmsaokkskald\nslmldssadasd', '2018-05-10 12:14:10', '4', '1', '1');
INSERT INTO `note` VALUES ('80', '1', 'wwwddd', 'dddd', '2018-05-10 13:11:37', '0', '0', '0');
INSERT INTO `note` VALUES ('81', '22', 'gagagag', 'dddd', '2018-05-15 13:14:51', '3', '0', '1');

-- ----------------------------
-- Table structure for note_photo
-- ----------------------------
DROP TABLE IF EXISTS `note_photo`;
CREATE TABLE `note_photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nid` int(11) NOT NULL COMMENT '所属帖子的id',
  `name` varchar(255) NOT NULL COMMENT '图片路径',
  PRIMARY KEY (`id`),
  KEY `FK_NID` (`nid`)
) ENGINE=MyISAM AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of note_photo
-- ----------------------------
INSERT INTO `note_photo` VALUES ('72', '76', '76_0.png');
INSERT INTO `note_photo` VALUES ('77', '78', '78_0.png');
INSERT INTO `note_photo` VALUES ('76', '77', '77_0.png');
INSERT INTO `note_photo` VALUES ('78', '79', '79_0.png');
INSERT INTO `note_photo` VALUES ('79', '79', '79_1.png');
INSERT INTO `note_photo` VALUES ('80', '80', '80_0.png');
INSERT INTO `note_photo` VALUES ('81', '80', '80_1.png');
INSERT INTO `note_photo` VALUES ('82', '81', '81_0.png');

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid1` int(11) NOT NULL COMMENT '事件发起者',
  `uid2` int(11) NOT NULL COMMENT '事件接收者',
  `content` varchar(255) NOT NULL COMMENT '事件内容',
  `time` datetime DEFAULT NULL COMMENT '事件产生的时间',
  `isread` int(1) DEFAULT '0' COMMENT '是否已阅读',
  `type` int(1) NOT NULL DEFAULT '1' COMMENT '通知类别',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notification
-- ----------------------------
INSERT INTO `notification` VALUES ('33', '19', '20', 'admin 关注了你.', '2018-04-16 10:56:32', '1', '1');
INSERT INTO `notification` VALUES ('34', '76', '20', '你发布的帖子\"nihao\"审核通过了.', '2018-04-16 14:11:10', '1', '2');
INSERT INTO `notification` VALUES ('35', '22', '1', '有人向你发送了一个领养请求,查看详情', '2018-04-16 16:08:50', '1', '3');
INSERT INTO `notification` VALUES ('36', '0', '20', '恭喜你，你申请领养No.22 已被批准', '2018-04-16 16:26:28', '1', '4');
INSERT INTO `notification` VALUES ('37', '20', '1', 'user 关注了你.', '2018-04-16 17:24:53', '1', '1');
INSERT INTO `notification` VALUES ('38', '77', '1', '你发布的帖子\"welcome\"审核通过了.', '2018-04-17 08:51:57', '1', '2');
INSERT INTO `notification` VALUES ('39', '23', '1', '有人向你发送了一个领养请求,查看详情', '2018-04-17 08:58:28', '1', '3');
INSERT INTO `notification` VALUES ('40', '0', '20', '恭喜你，你申请领养No.23 已被批准', '2018-04-17 09:00:55', '1', '4');
INSERT INTO `notification` VALUES ('41', '20', '21', 'user 关注了你.', '2018-04-17 09:07:07', '0', '1');
INSERT INTO `notification` VALUES ('42', '25', '1', '有人向你发送了一个领养请求,查看详情', '2018-04-17 17:47:04', '1', '3');
INSERT INTO `notification` VALUES ('43', '0', '20', '恭喜你，你申请领养No.25 已被批准', '2018-04-17 17:47:39', '0', '4');
INSERT INTO `notification` VALUES ('44', '78', '20', '你发布的帖子\"new\"审核通过了.', '2018-04-17 17:54:27', '0', '2');
INSERT INTO `notification` VALUES ('45', '26', '1', '有人向你发送了一个领养请求,查看详情', '2018-05-10 10:34:51', '1', '3');
INSERT INTO `notification` VALUES ('46', '0', '23', '恭喜你，你申请领养No.26 已被批准', '2018-05-10 10:36:34', '1', '4');
INSERT INTO `notification` VALUES ('47', '79', '1', '你发布的帖子\"slkslkkm\"审核通过了.', '2018-05-10 20:14:22', '1', '2');
INSERT INTO `notification` VALUES ('48', '27', '1', '有人向你发送了一个领养请求,查看详情', '2018-05-15 21:06:16', '1', '3');
INSERT INTO `notification` VALUES ('49', '0', '22', '恭喜你，你申请领养No.27 已被批准', '2018-05-15 21:06:25', '1', '4');
INSERT INTO `notification` VALUES ('50', '1', '20', 'admin 关注了你.', '2018-05-15 21:10:35', '0', '1');
INSERT INTO `notification` VALUES ('51', '1', '22', 'admin 关注了你.', '2018-05-15 21:12:08', '1', '1');
INSERT INTO `notification` VALUES ('52', '81', '22', '你发布的帖子\"gagagag\"审核通过了.', '2018-05-15 21:17:42', '1', '2');
INSERT INTO `notification` VALUES ('53', '28', '1', '有人向你发送了一个领养请求,查看详情', '2018-05-15 21:46:59', '1', '3');
INSERT INTO `notification` VALUES ('54', '0', '22', '很遗憾，你申请领养No.28 已被拒绝', '2018-05-15 21:48:09', '1', '4');
INSERT INTO `notification` VALUES ('55', '1', '24', 'admin 关注了你.', '2018-05-15 22:00:34', '1', '1');
INSERT INTO `notification` VALUES ('56', '24', '1', 'qqq 关注了你.', '2018-05-15 22:00:38', '1', '1');

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `floorid` int(11) DEFAULT '0' COMMENT '楼层数 区分回复',
  `nid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `replycontent` varchar(255) NOT NULL,
  `replytime` datetime DEFAULT NULL,
  `replyuid` int(11) DEFAULT NULL COMMENT '0=新楼层',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=151 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reply
-- ----------------------------
INSERT INTO `reply` VALUES ('141', '141', '76', '1', 'wahahah', '2018-04-16 07:36:33', '0');
INSERT INTO `reply` VALUES ('142', '142', '76', '20', 'ooooh', '2018-04-16 07:50:32', '0');
INSERT INTO `reply` VALUES ('143', '143', '77', '1', 'wahaha', '2018-04-17 00:52:26', '0');
INSERT INTO `reply` VALUES ('144', '144', '76', '20', 'afternoon', '2018-04-17 07:35:01', '0');
INSERT INTO `reply` VALUES ('145', '145', '77', '20', 'Nice to meet you.', '2018-04-17 09:46:26', '0');
INSERT INTO `reply` VALUES ('146', '146', '78', '1', 'wooo', '2018-05-10 02:57:30', '0');
INSERT INTO `reply` VALUES ('147', '147', '78', '1', 'wwww', '2018-05-10 02:57:36', '0');
INSERT INTO `reply` VALUES ('148', '148', '78', '1', '233', '2018-05-10 02:57:39', '0');
INSERT INTO `reply` VALUES ('149', '149', '79', '1', '', '2018-05-10 13:25:58', '0');
INSERT INTO `reply` VALUES ('150', '150', '76', '23', 'Jakarta', '2018-05-14 23:50:57', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phonenumber` varchar(11) NOT NULL COMMENT '手机号 唯一',
  `nickname` varchar(20) NOT NULL COMMENT '昵称 唯一',
  `password` varchar(20) NOT NULL,
  `icon` varchar(40) DEFAULT NULL,
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `address` varchar(255) DEFAULT NULL,
  `role` int(1) NOT NULL DEFAULT '0' COMMENT '用户权限 0=普通用户 1=管理员',
  `contacttype` int(1) NOT NULL DEFAULT '0' COMMENT '联系方式 1=电话 2=QQ 3=微信 4=邮箱',
  `contact` varchar(255) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_nickname` (`nickname`),
  UNIQUE KEY `unique_phonenumber` (`phonenumber`),
  UNIQUE KEY `unique_user` (`phonenumber`,`nickname`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '13024779500', 'admin', 'password', '1.png', '2018-04-16', 'Xiamen', '1', '2', '112039023');
INSERT INTO `user` VALUES ('20', '13024779510', 'user', '123', '20.png', '2018-04-11', 'Hong Kong', '0', '2', '20203030');
INSERT INTO `user` VALUES ('21', '12052712503', 'Jack', '123', '21.png', '2018-04-18', 'Beijing', '0', '0', null);
INSERT INTO `user` VALUES ('22', '13024779520', 'Halo', '123', '22.png', '2018-04-17', '', '0', '1', 'dddd');
INSERT INTO `user` VALUES ('23', '18850508888', 'helloworld', '111', '23.png', '2018-05-10', null, '0', '0', null);
INSERT INTO `user` VALUES ('24', '13024779512', 'qqq', '666', null, '2018-05-15', null, '0', '0', null);
