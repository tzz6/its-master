/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50532
Source Host           : localhost:3306
Source Database       : webdemo

Target Server Type    : MYSQL
Target Server Version : 50532
File Encoding         : 65001

Date: 2016-04-19 11:10:20
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `qrtz_blob_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_calendars`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_cron_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerFactory', 'exQuartzAJobCronTrigger', 'DEFAULT', '0/10 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerFactory', 'exQuartzBJobCronTrigger', 'DEFAULT', '0/10 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerFactory', 'exQuartzJobCronTrigger', 'DEFAULT', '0/10 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for `qrtz_fired_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_job_details`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('schedulerFactory', 'exQuartzAJob', 'DEFAULT', null, 'com.tzz.job.quartz.EXQuartzAJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000774696D656F7574740001307800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerFactory', 'exQuartzBJob', 'DEFAULT', null, 'com.tzz.job.quartz.EXQuartzBJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000774696D656F7574740001307800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerFactory', 'exQuartzJob', 'DEFAULT', null, 'com.tzz.job.quartz.EXQuartzJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000774696D656F7574740001307800);

-- ----------------------------
-- Table structure for `qrtz_locks`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('schedulerFactory', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('schedulerFactory', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for `qrtz_paused_trigger_grps`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_scheduler_state`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('schedulerFactory', 'SFHQ931A1460959861654', '1460960926756', '20000');

-- ----------------------------
-- Table structure for `qrtz_simple_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_simprop_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for `qrtz_triggers`
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('schedulerFactory', 'exQuartzAJobCronTrigger', 'DEFAULT', 'exQuartzAJob', 'DEFAULT', null, '1455851150000', '1455851140000', '0', 'PAUSED', 'CRON', '1453180901000', '0', null, '0', '');
INSERT INTO `qrtz_triggers` VALUES ('schedulerFactory', 'exQuartzBJobCronTrigger', 'DEFAULT', 'exQuartzBJob', 'DEFAULT', null, '1455851140000', '1455851130000', '0', 'PAUSED', 'CRON', '1453180901000', '0', null, '0', '');
INSERT INTO `qrtz_triggers` VALUES ('schedulerFactory', 'exQuartzJobCronTrigger', 'DEFAULT', 'exQuartzJob', 'DEFAULT', null, '1455851140000', '1455851130000', '0', 'PAUSED', 'CRON', '1453180901000', '0', null, '0', '');

-- ----------------------------
-- Table structure for `tab_department`
-- ----------------------------
DROP TABLE IF EXISTS `tab_department`;
CREATE TABLE `tab_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `parentid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tn6jjvns0hbrg58ybengax9di` (`parentid`),
  CONSTRAINT `FK_tn6jjvns0hbrg58ybengax9di` FOREIGN KEY (`parentid`) REFERENCES `tab_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_department
-- ----------------------------
INSERT INTO `tab_department` VALUES ('1', null, '董事会', null);
INSERT INTO `tab_department` VALUES ('2', null, '总经办', '1');
INSERT INTO `tab_department` VALUES ('3', null, '开发部', '2');
INSERT INTO `tab_department` VALUES ('4', null, '财务部', '2');

-- ----------------------------
-- Table structure for `tab_file_model`
-- ----------------------------
DROP TABLE IF EXISTS `tab_file_model`;
CREATE TABLE `tab_file_model` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `savePath` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_file_model
-- ----------------------------
INSERT INTO `tab_file_model` VALUES ('1', '2015-12-30 18:03:23', 'ehcache.xml', 'E:\\eclipse\\eclipse\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp4\\wtpwebapps\\web-demo\\WEB-INF\\upload\\13\\11\\0d633834-994d-448f-8b05-9faa0c655fca_ehcache.xml');
INSERT INTO `tab_file_model` VALUES ('2', '2016-01-05 15:07:27', 'doc-render.zip', 'E:\\eclipse\\eclipse\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp4\\wtpwebapps\\web-demo\\WEB-INF\\upload\\9\\4\\f8cc41d3-7f1e-49e7-ba83-6953e6163fb4_doc-render.zip');
INSERT INTO `tab_file_model` VALUES ('3', '2016-01-05 16:45:49', 'doc-render.zip', 'E:\\eclipse\\eclipse\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp4\\wtpwebapps\\web-demo\\WEB-INF\\upload\\3\\2\\96f4bf9f-7c08-45e1-8fc6-73885cf83e91_doc-render.zip');
INSERT INTO `tab_file_model` VALUES ('4', '2016-01-06 15:39:23', '速运转交.zip', 'E:\\eclipse\\eclipse\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp4\\wtpwebapps\\web-demo\\WEB-INF\\upload\\11\\2\\898bb982-08da-4080-9b49-4ba953af0cb9_速运转交.zip');
INSERT INTO `tab_file_model` VALUES ('5', '2016-01-06 15:39:23', '专题页上传图.zip', 'E:\\eclipse\\eclipse\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp4\\wtpwebapps\\web-demo\\WEB-INF\\upload\\12\\0\\244640f2-5672-41e2-94e8-f16962120436_专题页上传图.zip');
INSERT INTO `tab_file_model` VALUES ('6', '2016-01-08 16:33:19', 'axis2wsdl.bat', 'E:\\eclipse\\eclipse\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp4\\wtpwebapps\\web-demo\\WEB-INF\\upload\\10\\0\\f4bd9780-0082-4efc-accb-5c02f6093f91_axis2wsdl.bat');
INSERT INTO `tab_file_model` VALUES ('7', '2016-03-18 17:29:20', 'rcp.txt', 'E:\\Eclipse\\4.x\\eclipse\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\web-demo\\WEB-INF\\upload\\7\\12\\41bfbfdb-79a6-441c-8e76-5a04208bb409_rcp.txt');

-- ----------------------------
-- Table structure for `tab_orders`
-- ----------------------------
DROP TABLE IF EXISTS `tab_orders`;
CREATE TABLE `tab_orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `orderNum` varchar(255) DEFAULT NULL,
  `order_type_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_92am8pp5atmiten3mmeic8i2i` (`order_type_id`),
  CONSTRAINT `FK_92am8pp5atmiten3mmeic8i2i` FOREIGN KEY (`order_type_id`) REFERENCES `tab_role_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_orders
-- ----------------------------
INSERT INTO `tab_orders` VALUES ('1', '2015-12-30 18:01:04', 'O123456789', '1');
INSERT INTO `tab_orders` VALUES ('2', '2015-12-30 18:01:21', 'O223456789', '2');

-- ----------------------------
-- Table structure for `tab_role`
-- ----------------------------
DROP TABLE IF EXISTS `tab_role`;
CREATE TABLE `tab_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `departmentId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5wa9ua4sgjur7e5ap7y1tde3q` (`departmentId`),
  CONSTRAINT `FK_5wa9ua4sgjur7e5ap7y1tde3q` FOREIGN KEY (`departmentId`) REFERENCES `tab_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_role
-- ----------------------------
INSERT INTO `tab_role` VALUES ('1', '角色1', '1');
INSERT INTO `tab_role` VALUES ('2', '角色2', '1');
INSERT INTO `tab_role` VALUES ('3', '角色3', '3');

-- ----------------------------
-- Table structure for `tab_role_type`
-- ----------------------------
DROP TABLE IF EXISTS `tab_role_type`;
CREATE TABLE `tab_role_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `orderType` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_role_type
-- ----------------------------
INSERT INTO `tab_role_type` VALUES ('1', '支付宝');
INSERT INTO `tab_role_type` VALUES ('2', '微信');

-- ----------------------------
-- Table structure for `tab_schedule_job`
-- ----------------------------
DROP TABLE IF EXISTS `tab_schedule_job`;
CREATE TABLE `tab_schedule_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `className` varchar(255) DEFAULT NULL,
  `cronExpression` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `job_group` varchar(255) DEFAULT NULL,
  `jobName` varchar(255) DEFAULT NULL,
  `jobStatus` varchar(255) DEFAULT NULL,
  `triggerGroup` varchar(255) DEFAULT NULL,
  `triggerName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1099 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_schedule_job
-- ----------------------------
INSERT INTO `tab_schedule_job` VALUES ('1095', 'com.tzz.job.quartz.EXQuartzJob', '0/10 * * * * ?', '通过extends QuartzJobBean 的方式', 'DEFAULT', 'exQuartzJob', '1', 'DEFAULT', 'exQuartzJobCronTrigger');
INSERT INTO `tab_schedule_job` VALUES ('1096', 'com.tzz.job.quartz.EXQuartzAJob', '0/10 * * * * ?', 'A--通过extends QuartzJobBean 的方式', 'DEFAULT', 'exQuartzAJob', '1', 'DEFAULT', 'exQuartzAJobCronTrigger');
INSERT INTO `tab_schedule_job` VALUES ('1097', 'com.tzz.job.quartz.EXQuartzBJob', '0/10 * * * * ?', 'B--通过extends QuartzJobBean 的方式', 'DEFAULT', 'exQuartzBJob', '1', 'DEFAULT', 'exQuartzBJobCronTrigger');
INSERT INTO `tab_schedule_job` VALUES ('1098', 'com.tzz.job.quartz.DynamicQuartzJob', '0 0/10 * * * ?', '动态添加任务', 'DEFAULT', 'dynamicQuartzJob', '1', 'DEFAULT', 'dynamicQuartzCronTrigger');

-- ----------------------------
-- Table structure for `tab_user`
-- ----------------------------
DROP TABLE IF EXISTS `tab_user`;
CREATE TABLE `tab_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `regDate` datetime DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `departmentid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_iai43sss9fw8wakfuhc0apcar` (`departmentid`),
  CONSTRAINT `FK_iai43sss9fw8wakfuhc0apcar` FOREIGN KEY (`departmentid`) REFERENCES `tab_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_user
-- ----------------------------
INSERT INTO `tab_user` VALUES ('1', '测试01', 'safdfa', '2015-12-30 18:02:58', '男', '3');
INSERT INTO `tab_user` VALUES ('2', '测试02', '123456', '2016-01-05 15:37:45', '男', '3');
INSERT INTO `tab_user` VALUES ('3', '测试03', '123456', '2016-01-06 15:38:14', '男', '3');
INSERT INTO `tab_user` VALUES ('4', '1', '1', '2016-02-23 13:58:52', '男', '1');
INSERT INTO `tab_user` VALUES ('5', '2', '3', '2016-02-23 13:59:03', '男', '1');
INSERT INTO `tab_user` VALUES ('6', '3', '3', '2016-02-23 13:59:16', '女', '1');
INSERT INTO `tab_user` VALUES ('7', '4', '4', '2016-02-23 13:59:27', '女', '4');
INSERT INTO `tab_user` VALUES ('8', '5', '5', '2016-02-23 13:59:38', '女', '4');
INSERT INTO `tab_user` VALUES ('9', '6', '6', '2016-02-23 13:59:49', '女', '4');
INSERT INTO `tab_user` VALUES ('10', '7', '7', '2016-02-23 14:00:03', '女', '4');
INSERT INTO `tab_user` VALUES ('11', '9', '9', '2016-02-23 14:00:17', '女', '4');
INSERT INTO `tab_user` VALUES ('15', 'admin', 'admin', '2016-02-26 10:55:52', '男', '2');
INSERT INTO `tab_user` VALUES ('18', 'Test01', '123456', '2016-02-26 14:12:57', '男', '4');
INSERT INTO `tab_user` VALUES ('19', 'Test03', '123456', '2016-02-26 14:24:06', '男', '3');
INSERT INTO `tab_user` VALUES ('20', 'Test03', '123456', '2016-02-26 14:24:30', '男', '3');
INSERT INTO `tab_user` VALUES ('21', 'Test04', '123456', '2016-02-26 14:26:28', '男', '2');
INSERT INTO `tab_user` VALUES ('22', 'Test05', '123456', '2016-02-26 14:27:00', '女', '4');
INSERT INTO `tab_user` VALUES ('23', 'Test05', '123456', '2016-02-26 14:28:37', '男', '2');
INSERT INTO `tab_user` VALUES ('24', 'Test06', '123456', '2016-02-26 14:57:37', '女', '3');
INSERT INTO `tab_user` VALUES ('25', 'Test09', '123456', '2016-02-26 14:57:49', '女', '4');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `uid` bigint(20) NOT NULL,
  `rid` bigint(20) NOT NULL,
  PRIMARY KEY (`uid`,`rid`),
  KEY `FK_q7oskmwmmyn5usc6cqsi2g28g` (`rid`),
  CONSTRAINT `FK_q7oskmwmmyn5usc6cqsi2g28g` FOREIGN KEY (`rid`) REFERENCES `tab_role` (`id`),
  CONSTRAINT `FK_r5y4sx1dta0whavqah04r5uu8` FOREIGN KEY (`uid`) REFERENCES `tab_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('2', '1');
INSERT INTO `user_role` VALUES ('3', '1');
INSERT INTO `user_role` VALUES ('4', '1');
INSERT INTO `user_role` VALUES ('7', '1');
INSERT INTO `user_role` VALUES ('9', '1');
INSERT INTO `user_role` VALUES ('10', '1');
INSERT INTO `user_role` VALUES ('11', '1');
INSERT INTO `user_role` VALUES ('15', '1');
INSERT INTO `user_role` VALUES ('19', '1');
INSERT INTO `user_role` VALUES ('20', '1');
INSERT INTO `user_role` VALUES ('22', '1');
INSERT INTO `user_role` VALUES ('24', '1');
INSERT INTO `user_role` VALUES ('2', '2');
INSERT INTO `user_role` VALUES ('4', '2');
INSERT INTO `user_role` VALUES ('5', '2');
INSERT INTO `user_role` VALUES ('8', '2');
INSERT INTO `user_role` VALUES ('10', '2');
INSERT INTO `user_role` VALUES ('11', '2');
INSERT INTO `user_role` VALUES ('15', '2');
INSERT INTO `user_role` VALUES ('18', '2');
INSERT INTO `user_role` VALUES ('19', '2');
INSERT INTO `user_role` VALUES ('20', '2');
INSERT INTO `user_role` VALUES ('21', '2');
INSERT INTO `user_role` VALUES ('22', '2');
INSERT INTO `user_role` VALUES ('23', '2');
INSERT INTO `user_role` VALUES ('24', '2');
INSERT INTO `user_role` VALUES ('25', '2');
INSERT INTO `user_role` VALUES ('4', '3');
INSERT INTO `user_role` VALUES ('6', '3');
INSERT INTO `user_role` VALUES ('8', '3');
INSERT INTO `user_role` VALUES ('9', '3');
INSERT INTO `user_role` VALUES ('11', '3');
INSERT INTO `user_role` VALUES ('15', '3');
INSERT INTO `user_role` VALUES ('21', '3');
INSERT INTO `user_role` VALUES ('23', '3');
INSERT INTO `user_role` VALUES ('25', '3');
