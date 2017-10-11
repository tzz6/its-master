/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50532
Source Host           : localhost:3306
Source Database       : webdemo

Target Server Type    : MYSQL
Target Server Version : 50532
File Encoding         : 65001

Date: 2017-09-22 09:27:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bld_language`
-- ----------------------------
DROP TABLE IF EXISTS `bld_language`;
CREATE TABLE `bld_language` (
  `BLD_CODE` varchar(16) NOT NULL COMMENT '编码',
  `LANG` varchar(16) NOT NULL COMMENT '语言',
  `BLD_NAME` varchar(256) NOT NULL COMMENT '名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='语言表';

-- ----------------------------
-- Records of bld_language
-- ----------------------------
INSERT INTO `bld_language` VALUES ('sys_menu_0001', 'en', 'SystemManager');
INSERT INTO `bld_language` VALUES ('sys_menu_0002', 'en', 'UserManager');
INSERT INTO `bld_language` VALUES ('sys_menu_0006', 'en', 'RolerManager');
INSERT INTO `bld_language` VALUES ('sys_menu_0011', 'en', 'FileManager');
INSERT INTO `bld_language` VALUES ('sys_menu_0012', 'en', 'FileList');
INSERT INTO `bld_language` VALUES ('sys_menu_0011', 'zh', '文件管理');
INSERT INTO `bld_language` VALUES ('sys_menu_0006', 'zh', '角色管理');
INSERT INTO `bld_language` VALUES ('sys_menu_0002', 'zh', '用户管理');
INSERT INTO `bld_language` VALUES ('sys_menu_0001', 'zh', '系统管理');
INSERT INTO `bld_language` VALUES ('SYS_NAME_01', 'en', 'ITS-EN');
INSERT INTO `bld_language` VALUES ('SYS_NAME_01', 'zh', 'ITS-中文');
INSERT INTO `bld_language` VALUES ('sys_menu_0003', 'zh', '用户新增');
INSERT INTO `bld_language` VALUES ('sys_menu_0003', 'en', 'User Add');
INSERT INTO `bld_language` VALUES ('sys_menu_0004', 'en', 'User Update');
INSERT INTO `bld_language` VALUES ('sys_menu_0004', 'zh', '用户修改');
INSERT INTO `bld_language` VALUES ('sys_menu_00041', 'en', 'User Delete');
INSERT INTO `bld_language` VALUES ('sys_menu_00041', 'zh', '用户删除');
INSERT INTO `bld_language` VALUES ('sys_menu_0005', 'zh', '用户设置关联角色');
INSERT INTO `bld_language` VALUES ('sys_menu_0005', 'en', 'User Set Role');
INSERT INTO `bld_language` VALUES ('sys_menu_0007', 'zh', '角色新增');
INSERT INTO `bld_language` VALUES ('sys_menu_0007', 'en', 'Role Add');
INSERT INTO `bld_language` VALUES ('sys_menu_0008', 'en', 'Role Update');
INSERT INTO `bld_language` VALUES ('sys_menu_0008', 'zh', '角色修改');
INSERT INTO `bld_language` VALUES ('sys_menu_0009', 'en', 'Role Delete');
INSERT INTO `bld_language` VALUES ('sys_menu_0009', 'zh', '角色删除');
INSERT INTO `bld_language` VALUES ('sys_menu_0010', 'zh', '角色设置关联菜单');
INSERT INTO `bld_language` VALUES ('sys_menu_0010', 'en', 'Role Set Menu');
INSERT INTO `bld_language` VALUES ('sys_menu_0012', 'zh', '文件列表');
INSERT INTO `bld_language` VALUES ('SYS_NAME_02', 'en', 'WMS-EN');
INSERT INTO `bld_language` VALUES ('SYS_NAME_02', 'zh', 'WMS-中文');
INSERT INTO `bld_language` VALUES ('sys_menu_0014', 'en', 'photograph');
INSERT INTO `bld_language` VALUES ('sys_menu_0014', 'zh', '拍照');
INSERT INTO `bld_language` VALUES ('sys_menu_0015', 'en', 'Template technology');
INSERT INTO `bld_language` VALUES ('sys_menu_0015', 'zh', '模板技术');
INSERT INTO `bld_language` VALUES ('sys_menu_0016', 'en', 'Freemarker');
INSERT INTO `bld_language` VALUES ('sys_menu_0016', 'zh', 'Freemarker');
INSERT INTO `bld_language` VALUES ('sys_menu_0017', 'zh', 'PDF工具');
INSERT INTO `bld_language` VALUES ('sys_menu_0017', 'en', 'PDF工具');
INSERT INTO `bld_language` VALUES ('sys_menu_0018', 'en', 'NoSql管理');
INSERT INTO `bld_language` VALUES ('sys_menu_0018', 'zh', 'NoSql管理');
INSERT INTO `bld_language` VALUES ('sys_menu_0019', 'en', 'MongoDB');
INSERT INTO `bld_language` VALUES ('sys_menu_0019', 'zh', 'MongoDB');
INSERT INTO `bld_language` VALUES ('sys_menu_0020', 'en', 'WS管理');
INSERT INTO `bld_language` VALUES ('sys_menu_0020', 'zh', 'WS管理');
INSERT INTO `bld_language` VALUES ('sys_menu_0022', 'en', 'Hessian');
INSERT INTO `bld_language` VALUES ('sys_menu_0022', 'zh', 'Hessian');
INSERT INTO `bld_language` VALUES ('sys_menu_0023', 'en', 'CXF');
INSERT INTO `bld_language` VALUES ('sys_menu_0023', 'zh', 'CXF');
INSERT INTO `bld_language` VALUES ('sys_menu_0024', 'zh', '支付管理');
INSERT INTO `bld_language` VALUES ('sys_menu_0024', 'en', 'Pay Manager');
INSERT INTO `bld_language` VALUES ('sys_menu_0025', 'zh', '支付宝');
INSERT INTO `bld_language` VALUES ('sys_menu_0025', 'en', 'AliPay');

-- ----------------------------
-- Table structure for `bsd_weight_charge_type`
-- ----------------------------
DROP TABLE IF EXISTS `bsd_weight_charge_type`;
CREATE TABLE `bsd_weight_charge_type` (
  `WCT_CODE` varchar(5) NOT NULL COMMENT '代码',
  `BLD_CODE` varchar(16) NOT NULL COMMENT '国家语言'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='计费重量类型表';

-- ----------------------------
-- Records of bsd_weight_charge_type
-- ----------------------------

-- ----------------------------
-- Table structure for `hibernate_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('619');
INSERT INTO `hibernate_sequence` VALUES ('619');
INSERT INTO `hibernate_sequence` VALUES ('619');
INSERT INTO `hibernate_sequence` VALUES ('619');
INSERT INTO `hibernate_sequence` VALUES ('619');
INSERT INTO `hibernate_sequence` VALUES ('619');
INSERT INTO `hibernate_sequence` VALUES ('619');

-- ----------------------------
-- Table structure for `pac_exchange_rate`
-- ----------------------------
DROP TABLE IF EXISTS `pac_exchange_rate`;
CREATE TABLE `pac_exchange_rate` (
  `EXCHANGE_RATE_ID` varchar(32) NOT NULL COMMENT '汇率表ID',
  `BASE_CURRENCY` varchar(6) NOT NULL COMMENT '本位币，币种CODE',
  `EFFECTIVE_DATE` datetime NOT NULL COMMENT '生效日期',
  `EXPIRATION_DATE` datetime NOT NULL COMMENT '失效日期',
  `CREATE_BY` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TM` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(20) NOT NULL COMMENT '修改人',
  `UPDATE_TM` datetime NOT NULL COMMENT '修改时间',
  `STATE` int(10) NOT NULL COMMENT '状态',
  PRIMARY KEY (`EXCHANGE_RATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇率表';

-- ----------------------------
-- Records of pac_exchange_rate
-- ----------------------------

-- ----------------------------
-- Table structure for `pac_exchange_rate_detail`
-- ----------------------------
DROP TABLE IF EXISTS `pac_exchange_rate_detail`;
CREATE TABLE `pac_exchange_rate_detail` (
  `EXCHANGE_RATE_DETAIL_ID` varchar(32) NOT NULL COMMENT '汇率明细ID',
  `EXCHANGE_RATE_ID` varchar(32) NOT NULL COMMENT '汇率表ID',
  `CURRENCY_CODE` varchar(6) NOT NULL COMMENT '币种CODE',
  `RATE` varchar(20) NOT NULL COMMENT '汇率，存储时乘以1000',
  PRIMARY KEY (`EXCHANGE_RATE_DETAIL_ID`),
  KEY `FK_Reference_9` (`EXCHANGE_RATE_ID`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`EXCHANGE_RATE_ID`) REFERENCES `pac_exchange_rate` (`EXCHANGE_RATE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='税率明细';

-- ----------------------------
-- Records of pac_exchange_rate_detail
-- ----------------------------

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
INSERT INTO `qrtz_cron_triggers` VALUES ('schedulerFactory', 'WD_TRIGGER_NAME', 'DEFAULT', '0/5 * * * * ?', 'Asia/Shanghai');

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
INSERT INTO `qrtz_job_details` VALUES ('schedulerFactory', 'exQuartzAJob', 'DEFAULT', null, 'com.its.servers.job.quartz.EXQuartzAJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000774696D656F7574740001307800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerFactory', 'exQuartzBJob', 'DEFAULT', null, 'com.its.servers.job.quartz.EXQuartzBJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000774696D656F7574740001307800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerFactory', 'exQuartzJob', 'DEFAULT', null, 'com.its.servers.job.quartz.EXQuartzJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000774696D656F7574740001307800);
INSERT INTO `qrtz_job_details` VALUES ('schedulerFactory', 'WD_JOB_NAME', 'DEFAULT', null, 'com.its.servers.job.quartz.DynamicQuartzJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000B7363686564756C654A6F6273720024636F6D2E6974732E6D6F64656C2E64616F2E646F6D61696E2E5363686564756C654A6F6220DAD6D338998A120200094C0009636C6173734E616D657400124C6A6176612F6C616E672F537472696E673B4C000E63726F6E45787072657373696F6E71007E00094C000B6465736372697074696F6E71007E00094C000269647400104C6A6176612F6C616E672F4C6F6E673B4C00086A6F6247726F757071007E00094C00076A6F624E616D6571007E00094C00096A6F6253746174757371007E00094C000C7472696767657247726F757071007E00094C000B747269676765724E616D6571007E0009787074002B636F6D2E6974732E736572766572732E6A6F622E71756172747A2E44796E616D696351756172747A4A6F6274000D302F35202A202A202A202A203F740012E58AA8E68081E4BBBBE58AA1E8B083E5BAA67074000744454641554C5474000B57445F4A4F425F4E414D457400013171007E000F74000F57445F545249474745525F4E414D457800);

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
INSERT INTO `qrtz_scheduler_state` VALUES ('schedulerFactory', 'SFHQ931A1505962363848', '1505975489683', '20000');

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
INSERT INTO `qrtz_triggers` VALUES ('schedulerFactory', 'exQuartzAJobCronTrigger', 'DEFAULT', 'exQuartzAJob', 'DEFAULT', null, '1504581000000', '1504580990000', '0', 'PAUSED', 'CRON', '1489673292000', '0', null, '0', '');
INSERT INTO `qrtz_triggers` VALUES ('schedulerFactory', 'exQuartzBJobCronTrigger', 'DEFAULT', 'exQuartzBJob', 'DEFAULT', null, '1504581000000', '1504580990000', '0', 'PAUSED', 'CRON', '1489673292000', '0', null, '0', '');
INSERT INTO `qrtz_triggers` VALUES ('schedulerFactory', 'exQuartzJobCronTrigger', 'DEFAULT', 'exQuartzJob', 'DEFAULT', null, '1504580940000', '1504580930000', '0', 'PAUSED', 'CRON', '1489673292000', '0', null, '0', '');
INSERT INTO `qrtz_triggers` VALUES ('schedulerFactory', 'WD_TRIGGER_NAME', 'DEFAULT', 'WD_JOB_NAME', 'DEFAULT', null, '1489728640000', '1489728635000', '5', 'PAUSED', 'CRON', '1489728621000', '0', null, '0', '');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `MENU_ID` varchar(32) NOT NULL COMMENT '菜单ID',
  `PARENT_MENU_ID` varchar(32) DEFAULT NULL COMMENT '上级菜单ID',
  `MENU_NAME` varchar(128) DEFAULT NULL COMMENT '菜单名',
  `MENU_SORT` int(8) DEFAULT NULL COMMENT '排序',
  `MENU_URL` varchar(256) DEFAULT NULL COMMENT 'URL',
  `MENU_TYPE` varchar(2) DEFAULT NULL COMMENT '菜单类型（M:菜单、B:按钮）',
  `PERMISSION_CODE` varchar(128) DEFAULT NULL COMMENT '权限编码',
  `PERMISSION_URL` varchar(512) DEFAULT NULL COMMENT '权限URL',
  `SYS_NAME_CODE` varchar(128) DEFAULT NULL COMMENT '系统CODE',
  `BLD_CODE` varchar(16) DEFAULT NULL COMMENT '语言CODE',
  PRIMARY KEY (`MENU_ID`),
  KEY `FK_SYS_MENU_PARENT_MENU_ID` (`PARENT_MENU_ID`),
  CONSTRAINT `FK_SYS_MENU_PARENT_MENU_ID` FOREIGN KEY (`PARENT_MENU_ID`) REFERENCES `sys_menu` (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('0000000000000001', null, '系统管理', '1', null, 'M', 'SYS_USER_MANAGER_1', '', 'IOP-MCS', 'sys_menu_0001');
INSERT INTO `sys_menu` VALUES ('0000000000000002', '0000000000000001', '用户管理', '2', '/sysUser/toSysUserManage', 'M', 'SYS_USER_MANAGER', '/sysUser/toSysUserManage,/sysUser/sysUserManage', 'IOP-MCS', 'sys_menu_0002');
INSERT INTO `sys_menu` VALUES ('0000000000000003', '0000000000000002', '用户新增', '3', null, 'B', 'SYS_USER_ADD', '/sysUser/addSysUser', 'IOP-MCS', 'sys_menu_0003');
INSERT INTO `sys_menu` VALUES ('0000000000000004', '0000000000000002', '用户修改', '4', null, 'B', 'SYS_USER_UPDATE', '/sysUser/updateSysUser', 'IOP-MCS', 'sys_menu_0004');
INSERT INTO `sys_menu` VALUES ('0000000000000005', '0000000000000002', '用户删除', '5', null, 'B', 'SYS_USER_DELETE', '/sysUser/deleteSysUser', 'IOP-MCS', 'sys_menu_00041');
INSERT INTO `sys_menu` VALUES ('0000000000000006', '0000000000000002', '用户设置关联角色', '6', null, 'B', 'SYS_USER_ROLE', '/sysRole/sysRoleManage,/sysUser/saveSysUserRole', 'IOP-MCS', 'sys_menu_0005');
INSERT INTO `sys_menu` VALUES ('0000000000000007', '0000000000000001', '角色管理', '7', '/sysRole/toSysRoleManage', 'M', 'SYS_ROLE_MANAGER', '/sysRole/toSysRoleManage,/sysRole/sysRoleManage', 'IOP-MCS', 'sys_menu_0006');
INSERT INTO `sys_menu` VALUES ('0000000000000008', '0000000000000007', '角色新增', '8', null, 'B', 'SYS_ROLE_ADD', '/sysRole/addSysRole', 'IOP-MCS', 'sys_menu_0007');
INSERT INTO `sys_menu` VALUES ('0000000000000009', '0000000000000007', '角色修改', '9', null, 'B', 'SYS_ROLE_UPDATE', '/sysRole/updateSysRole', 'IOP-MCS', 'sys_menu_0008');
INSERT INTO `sys_menu` VALUES ('0000000000000010', '0000000000000007', '角色删除', '10', null, 'B', 'SYS_ROLE_DELETE', '/sysRole/deleteSysRole', 'IOP-MCS', 'sys_menu_0009');
INSERT INTO `sys_menu` VALUES ('0000000000000011', '0000000000000007', '角色设置关联菜单', '11', null, 'B', 'SYS_ROLE_MENU', '/sysRole/saveSysRoleMenu', 'IOP-MCS', 'sys_menu_0010');
INSERT INTO `sys_menu` VALUES ('0000000000000012', null, '文件管理', '12', null, 'M', 'FILE_MANAGER', 'PERMISSION_URL', 'IOP-MCS', 'sys_menu_0011');
INSERT INTO `sys_menu` VALUES ('0000000000000013', '0000000000000012', '文件管理', '13', '/file/toFileManage', 'M', 'FILE_MANAGER_LIST', '/file/toFileManage', 'IOP-MCS', 'sys_menu_0012');
INSERT INTO `sys_menu` VALUES ('0000000000000014', '0000000000000012', '拍照', '14', '/webcam/photograph', 'M', 'FILE_PHOTOGRAPH', '/webcam/photograph', 'IOP-MCS', 'sys_menu_0014');
INSERT INTO `sys_menu` VALUES ('0000000000000015', null, '模板技术', '15', null, 'M', 'TEMPLET_TECHNOLOGY', null, 'IOP-MCS', 'sys_menu_0015');
INSERT INTO `sys_menu` VALUES ('0000000000000016', '0000000000000015', 'Freemarker', '16', '/templet/freemarkerList', 'M', 'TEMPLET_TECHNOLOGY_FREEMARKER', '/templet/freemarkerList', 'IOP-MCS', 'sys_menu_0016');
INSERT INTO `sys_menu` VALUES ('0000000000000017', '0000000000000015', 'PDF技术', '17', '/pdf/index', 'M', 'PDF_TECHNOLOGY', '/pdf/index', 'IOP-MCS', 'sys_menu_0017');
INSERT INTO `sys_menu` VALUES ('0000000000000018', null, 'NoSql管理', '19', null, 'M', 'NO_SQL_MANAGER', null, 'IOP-MCS', 'sys_menu_0018');
INSERT INTO `sys_menu` VALUES ('0000000000000019', '0000000000000018', 'MongoDB', '18', '/mongoDB/toMongoDBManager', 'M', 'MONGO_DB', '/mongoDB/toMongoDBManager', 'IOP-MCS', 'sys_menu_0019');
INSERT INTO `sys_menu` VALUES ('0000000000000020', null, 'WS管理', '20', null, 'M', 'SYS_USER_MANAGER_1', '', 'IOP-MCS', 'sys_menu_0020');
INSERT INTO `sys_menu` VALUES ('0000000000000021', '0000000000000020', 'Hessian', '21', '/ws/hessianIndex', 'M', 'HESSIAN_INDEX', '/ws/hessianIndex', 'IOP-MCS', 'sys_menu_0022');
INSERT INTO `sys_menu` VALUES ('0000000000000023', '0000000000000020', 'Hessian', '21', '/ws/cxfIndex', 'M', 'CXF_INDEX', '/ws/cxfIndex', 'IOP-MCS', 'sys_menu_0023');
INSERT INTO `sys_menu` VALUES ('0000000000000024', null, '支付管理', '22', null, 'M', 'PAY_MANAGER', null, 'IOP-MCS', 'sys_menu_0024');
INSERT INTO `sys_menu` VALUES ('0000000000000025', '0000000000000024', '支付宝', '23', '/pay/alipay', 'M', 'ALIPAY_INDEX', '/pay/alipay', 'IOP-MCS', 'sys_menu_0025');

-- ----------------------------
-- Table structure for `sys_name`
-- ----------------------------
DROP TABLE IF EXISTS `sys_name`;
CREATE TABLE `sys_name` (
  `SYS_NAME_CODE` varchar(128) DEFAULT NULL COMMENT '系统CODE',
  `EN_NAME` varchar(256) DEFAULT NULL COMMENT '英文名称',
  `BLD_CODE` varchar(16) DEFAULT NULL COMMENT '语言编码',
  `SN_STATUS` varchar(2) DEFAULT NULL COMMENT '状态（1：有效、0：无效）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统名称表';

-- ----------------------------
-- Records of sys_name
-- ----------------------------
INSERT INTO `sys_name` VALUES ('IOP-MCS', 'IOP-MCS', 'SYS_NAME_01', '1');
INSERT INTO `sys_name` VALUES ('IOP-AMS', 'IOP-AMS', 'SYS_NAME_02', '1');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ROLE_ID` varchar(32) NOT NULL COMMENT 'ID',
  `ROLE_NAME` varchar(128) DEFAULT NULL COMMENT '名称',
  `SYS_NAME_CODE` varchar(128) DEFAULT NULL COMMENT '系统COD',
  `CREATE_BY` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATE_TM` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('2559940E454A4BCA94328D4155DE84A7', '超级管理员', 'IOP-MCS', '01115486', '2017-03-29 14:03:10', '01115486', null);

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `ROLE_ID` varchar(32) DEFAULT NULL COMMENT '角色ID',
  `MENU_ID` varchar(32) DEFAULT NULL COMMENT '菜单ID',
  KEY `FK_SYS_ROLE_MENU_ROLE_ID` (`ROLE_ID`),
  KEY `FK_SYS_ROLE_MENU_MENU_ID` (`MENU_ID`),
  CONSTRAINT `FK_SYS_ROLE_MENU_MENU_ID` FOREIGN KEY (`MENU_ID`) REFERENCES `sys_menu` (`MENU_ID`),
  CONSTRAINT `FK_SYS_ROLE_MENU_ROLE_ID` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000001');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000002');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000003');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000004');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000005');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000006');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000007');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000008');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000009');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000010');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000011');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000012');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000013');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000014');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000015');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000016');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000017');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000018');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000019');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000020');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000021');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000023');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000024');
INSERT INTO `sys_role_menu` VALUES ('2559940E454A4BCA94328D4155DE84A7', '0000000000000025');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `ST_ID` varchar(32) NOT NULL COMMENT 'ID',
  `ST_Code` varchar(16) NOT NULL COMMENT '用户名',
  `ST_Name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `ST_Password` varchar(128) DEFAULT NULL COMMENT '密码',
  `CREATE_BY` varchar(20) NOT NULL COMMENT '创建人',
  `CREATE_TM` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(20) NOT NULL COMMENT '修改人',
  `UPDATE_TM` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ST_ID`),
  UNIQUE KEY `ST_Code` (`ST_Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('A63418EDD2924FE18CD51FB8A4DEA8EC', 'Test01', 'Testxx', 'bcfe1b1ac8f5b18c2a35fae8452cf5fef062676dbe83b6b1d141601fdb0a842c0a222da40a401ffa9baecbfb5a756be1fb31e9bb4c3477dda355b6f0ef1a89aa', 'admin', '2017-06-14 16:25:43', 'admin', null);
INSERT INTO `sys_user` VALUES ('DA499AED4E9243049B8AA8BCC76CE4C0', 'admin', 'admin', 'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec', '01115486', '2017-04-13 17:34:26', '01115486', null);

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `ST_ID` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `ROLE_ID` varchar(32) DEFAULT NULL COMMENT '角色ID',
  KEY `FK_SYS_USER_ROLE_ST_ID` (`ST_ID`),
  KEY `FK_SYS_USER_ROLE_ROLE_ID` (`ROLE_ID`),
  CONSTRAINT `FK_SYS_USER_ROLE_ROLE_ID` FOREIGN KEY (`ROLE_ID`) REFERENCES `sys_role` (`ROLE_ID`),
  CONSTRAINT `FK_SYS_USER_ROLE_ST_ID` FOREIGN KEY (`ST_ID`) REFERENCES `sys_user` (`ST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('DA499AED4E9243049B8AA8BCC76CE4C0', '2559940E454A4BCA94328D4155DE84A7');

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
  CONSTRAINT `FKa77wm6o5wkdirjuxona67ffln` FOREIGN KEY (`parentid`) REFERENCES `tab_department` (`id`),
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
) ENGINE=InnoDB AUTO_INCREMENT=380 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_file_model
-- ----------------------------
INSERT INTO `tab_file_model` VALUES ('378', '2017-05-24 16:39:06', 'Hydrangeas.jpg', 'E:\\Eclipse\\4.6.0\\eclipse\\workspace\\its-master\\its-web\\src\\main\\webapp\\WEB-INF\\upload\\20170524\\3\\13\\b225d13c-d1ea-47df-a1be-15ab809bcf6e_20170524163906.Hydrangeas.jpg');
INSERT INTO `tab_file_model` VALUES ('379', '2017-05-24 16:39:06', 'Lighthouse.jpg', 'E:\\Eclipse\\4.6.0\\eclipse\\workspace\\its-master\\its-web\\src\\main\\webapp\\WEB-INF\\upload\\20170524\\4\\1\\50a8636d-85ae-42af-8c5a-2922c7a06f35_20170524163906.Lighthouse.jpg');

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
  CONSTRAINT `FK48wqymry571ufq4q9q4a0tbwr` FOREIGN KEY (`order_type_id`) REFERENCES `tab_role_type` (`id`),
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
  CONSTRAINT `FK9d96k2u6qc5v3urwr5mjyvacr` FOREIGN KEY (`departmentId`) REFERENCES `tab_department` (`id`),
  CONSTRAINT `FK_5wa9ua4sgjur7e5ap7y1tde3q` FOREIGN KEY (`departmentId`) REFERENCES `tab_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=391 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_role
-- ----------------------------
INSERT INTO `tab_role` VALUES ('1', '角色1', '1');
INSERT INTO `tab_role` VALUES ('2', '角色2', '1');
INSERT INTO `tab_role` VALUES ('3', '角色3', '3');
INSERT INTO `tab_role` VALUES ('390', 't', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=619 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tab_schedule_job
-- ----------------------------
INSERT INTO `tab_schedule_job` VALUES ('615', 'com.tzz.job.quartz.EXQuartzJob', '0/10 * * * * ?', '通过extends QuartzJobBean 的方式', 'DEFAULT', 'exQuartzJob', '1', 'DEFAULT', 'exQuartzJobCronTrigger');
INSERT INTO `tab_schedule_job` VALUES ('616', 'com.tzz.job.quartz.EXQuartzAJob', '0/10 * * * * ?', 'A--通过extends QuartzJobBean 的方式', 'DEFAULT', 'exQuartzAJob', '1', 'DEFAULT', 'exQuartzAJobCronTrigger');
INSERT INTO `tab_schedule_job` VALUES ('617', 'com.tzz.job.quartz.EXQuartzBJob', '0/10 * * * * ?', 'B--通过extends QuartzJobBean 的方式', 'DEFAULT', 'exQuartzBJob', '1', 'DEFAULT', 'exQuartzBJobCronTrigger');
INSERT INTO `tab_schedule_job` VALUES ('618', 'com.tzz.job.quartz.DynamicQuartzJob', '0 0/10 * * * ?', '动态添加任务', 'DEFAULT', 'dynamicQuartzJob', '1', 'DEFAULT', 'dynamicQuartzCronTrigger');

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
  CONSTRAINT `FK41rr5k7gw5uaqjiqbri2iq9v` FOREIGN KEY (`departmentid`) REFERENCES `tab_department` (`id`),
  CONSTRAINT `FK_iai43sss9fw8wakfuhc0apcar` FOREIGN KEY (`departmentid`) REFERENCES `tab_department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=390 DEFAULT CHARSET=utf8;

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
INSERT INTO `tab_user` VALUES ('26', 'Test2016051601', '123456', '2016-05-16 10:53:59', '男', '1');
INSERT INTO `tab_user` VALUES ('27', 'tanzhongzhu@sf-express.com', '123456', '2016-05-16 15:50:11', '男', '1');
INSERT INTO `tab_user` VALUES ('117', 'Test', '123456', '2017-03-15 22:33:35', '男', '1');
INSERT INTO `tab_user` VALUES ('388', '15813729906', '123456', '2017-05-24 19:02:09', '男', '1');
INSERT INTO `tab_user` VALUES ('389', '15813729906', '123456', '2017-05-24 19:03:03', '男', '1');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `uid` bigint(20) NOT NULL,
  `rid` bigint(20) NOT NULL,
  PRIMARY KEY (`uid`,`rid`),
  KEY `FK_q7oskmwmmyn5usc6cqsi2g28g` (`rid`),
  CONSTRAINT `FKe8dx2jkvht1s66eou1i7i7v25` FOREIGN KEY (`rid`) REFERENCES `tab_role` (`id`),
  CONSTRAINT `FKidi2oit0dl97b2xr246avtbvh` FOREIGN KEY (`uid`) REFERENCES `tab_user` (`id`),
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
INSERT INTO `user_role` VALUES ('26', '1');
INSERT INTO `user_role` VALUES ('27', '1');
INSERT INTO `user_role` VALUES ('388', '1');
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
INSERT INTO `user_role` VALUES ('26', '2');
INSERT INTO `user_role` VALUES ('27', '2');
INSERT INTO `user_role` VALUES ('117', '2');
INSERT INTO `user_role` VALUES ('389', '2');
INSERT INTO `user_role` VALUES ('4', '3');
INSERT INTO `user_role` VALUES ('6', '3');
INSERT INTO `user_role` VALUES ('8', '3');
INSERT INTO `user_role` VALUES ('9', '3');
INSERT INTO `user_role` VALUES ('11', '3');
INSERT INTO `user_role` VALUES ('15', '3');
INSERT INTO `user_role` VALUES ('21', '3');
INSERT INTO `user_role` VALUES ('23', '3');
INSERT INTO `user_role` VALUES ('25', '3');
