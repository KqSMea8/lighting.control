-- MySQL dump 10.13  Distrib 5.6.39, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: light_controller
-- ------------------------------------------------------
-- Server version	5.6.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `back_uri`
--

DROP TABLE IF EXISTS `back_uri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `back_uri` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `back_uri` varchar(255) NOT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cmd_record`
--

DROP TABLE IF EXISTS `cmd_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmd_record` (
  `record_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_code` varchar(256) NOT NULL COMMENT 'dtu注册码',
  `dev_code` varchar(2) NOT NULL COMMENT '串口设备地址',
  `regis_addr` varchar(256) DEFAULT '' COMMENT '变量地址',
  `cmd_info` varchar(16) NOT NULL COMMENT '发送的命令内容',
  `result` varchar(128) DEFAULT '' COMMENT '错误相应记录',
  `create_by` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=66769 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnarea_2016`
--

DROP TABLE IF EXISTS `cnarea_2016`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnarea_2016` (
  `id` mediumint(7) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` mediumint(7) unsigned NOT NULL DEFAULT '0' COMMENT '父级ID',
  `level` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '层级',
  `area_code` bigint(12) unsigned NOT NULL DEFAULT '0' COMMENT '行政代码',
  `zip_code` mediumint(6) unsigned zerofill NOT NULL DEFAULT '000000' COMMENT '邮政编码',
  `city_code` char(6) NOT NULL DEFAULT '' COMMENT '区号',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `short_name` varchar(50) NOT NULL DEFAULT '' COMMENT '简称',
  `merger_name` varchar(50) NOT NULL DEFAULT '' COMMENT '组合名',
  `pinyin` varchar(30) NOT NULL DEFAULT '' COMMENT '拼音',
  `lng` decimal(10,6) NOT NULL DEFAULT '0.000000' COMMENT '经度',
  `lat` decimal(10,6) NOT NULL DEFAULT '0.000000' COMMENT '维度',
  PRIMARY KEY (`id`),
  KEY `idx_lev` (`level`,`parent_id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=752234 DEFAULT CHARSET=utf8 COMMENT='中国行政地区表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dtu_id` bigint(20) NOT NULL COMMENT 'dtu id',
  `external_id` varchar(2) NOT NULL DEFAULT '' COMMENT '对外id第三方控制时需引用的地址号',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '设备名称',
  `code` varchar(2) DEFAULT '' COMMENT '设备编码,串口设备id号,ID号可以是01-16. 最小为1， 最大为16',
  `model` varchar(255) DEFAULT '' COMMENT '设备通讯电表文件可以存为设备型号',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '在线状态，0->离线，1->在线',
  `last_call` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '显示连线的统计次数',
  `connect_count` int(11) DEFAULT NULL COMMENT '显示连线的统计次数',
  `disconnect_count` int(255) DEFAULT NULL COMMENT '未连接的统计次数',
  `is_delete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否删除，1->否,2->是',
  `model_file` varchar(255) DEFAULT '' COMMENT '点位文件存放路径',
  `last_online_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次在线时间',
  `last_offline_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次离线时间',
  `use_times` bigint(20) DEFAULT '0' COMMENT '设备使用时长',
  `task_name` varchar(64) DEFAULT '' COMMENT '定时读取调用设备是否在线的任务名称',
  PRIMARY KEY (`id`),
  KEY `dtu_id_index` (`dtu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COMMENT='设备表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_property`
--

DROP TABLE IF EXISTS `device_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dev_id` bigint(20) NOT NULL COMMENT '设备id',
  `system_name` varchar(255) DEFAULT '' COMMENT '系统参数设置名字',
  `system_value` varchar(255) DEFAULT '' COMMENT '系统参数设置值',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dtu`
--

DROP TABLE IF EXISTS `dtu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dtu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device` varchar(255) NOT NULL COMMENT 'DTU设备',
  `device_name` varchar(255) NOT NULL COMMENT '设备名称',
  `device_code` varchar(255) DEFAULT '' COMMENT '设备码',
  `beat_content` varchar(255) DEFAULT '' COMMENT '心跳包内容',
  `beat_time` int(10) NOT NULL DEFAULT '180' COMMENT '心跳包内容，默认180秒',
  `order_id` int(11) DEFAULT '0' COMMENT '排序id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否删除，1->否,2->是',
  `proj_id` int(11) NOT NULL COMMENT '项目id',
  `online_status` tinyint(2) DEFAULT '0' COMMENT '在线状态,0->离线,1->在线',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dtu_device_code` (`device_code`),
  KEY `device_name_index` (`device_name`),
  KEY `proj_id_index` (`proj_id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `equipment_monitor`
--

DROP TABLE IF EXISTS `equipment_monitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipment_monitor` (
  `monitor_id` int(11) NOT NULL AUTO_INCREMENT,
  `monitor_type` tinyint(4) NOT NULL COMMENT '1->设备监控  2->自定义监控',
  `caption` varchar(128) DEFAULT '' COMMENT '显示文字信息',
  `source_id` bigint(20) NOT NULL COMMENT '监控对象(串口设备/群组/时序)id',
  `source_type` tinyint(4) NOT NULL COMMENT '监控对象类型 1->单个设备 2->群组 3->时序',
  `current_value` decimal(12,4) DEFAULT '0.0000' COMMENT '变量值',
  `value_type` varchar(8) NOT NULL COMMENT '变量值类型',
  `unit` varchar(16) DEFAULT '' COMMENT '单位',
  `factor` decimal(12,4) DEFAULT '0.0000' COMMENT '最小值',
  `max` decimal(12,4) DEFAULT '0.0000' COMMENT '变量数值的最大值',
  `min` decimal(12,4) DEFAULT '0.0000' COMMENT '最小值',
  `panel_id` int(11) DEFAULT NULL COMMENT '面板id(设备监控为DTUid,自定义监控为自定义面板id)',
  `project_id` int(11) NOT NULL COMMENT '所属项目id',
  `is_delete` tinyint(1) DEFAULT '1' COMMENT '1->未删除 2->删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`monitor_id`),
  KEY `panel_id_index` (`panel_id`),
  KEY `project_id_index` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group`
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) NOT NULL COMMENT '群组名称',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `group_code` varchar(255) DEFAULT NULL COMMENT '群组编码，Group1,内部变量依次累加',
  `proj_id` int(11) NOT NULL DEFAULT '0' COMMENT '项目id',
  `is_delete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否删除，1->否,2->是',
  PRIMARY KEY (`id`),
  KEY `proj_id_index` (`proj_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='群组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `group_device_middle`
--

DROP TABLE IF EXISTS `group_device_middle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_device_middle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_id` bigint(20) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `regis_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `device_group_id_index` (`device_id`,`group_id`,`regis_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `var_id` bigint(20) NOT NULL COMMENT '变量id',
  `var_type` tinyint(4) NOT NULL COMMENT '变量类型,1->设备变量,2->群组变量,3->时序变量',
  `var_value` varchar(255) NOT NULL COMMENT '变量的值',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=305 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `holiday`
--

DROP TABLE IF EXISTS `holiday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `holiday` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `holiday_time` varchar(255) NOT NULL COMMENT '指定节假日日期',
  `proj_id` int(11) NOT NULL COMMENT '项目id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `start_task` varchar(255) DEFAULT NULL COMMENT '开始任务名称,节假日的当天00:00',
  PRIMARY KEY (`id`),
  KEY `holiday_time_index` (`holiday_time`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manager_type`
--

DROP TABLE IF EXISTS `manager_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `manager_type_name` varchar(32) NOT NULL COMMENT '管理类型名称',
  `type_status` tinyint(4) DEFAULT '1' COMMENT '1->启用，2->禁用',
  `is_delete` tinyint(4) DEFAULT '1' COMMENT '1->未删除 2->删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manager_type_menu`
--

DROP TABLE IF EXISTS `manager_type_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager_type_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_id` int(11) NOT NULL COMMENT '1->可控 2->可配置',
  `menu_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='管理类型和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manager_type_uri`
--

DROP TABLE IF EXISTS `manager_type_uri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager_type_uri` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `manager_type_id` int(11) NOT NULL,
  `back_uri_id` int(11) NOT NULL,
  `create_by` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(32) NOT NULL COMMENT '菜单名称',
  `menu_status` tinyint(4) DEFAULT '1' COMMENT '1->启用，2->禁用',
  `menu_url` varchar(512) CHARACTER SET latin1 DEFAULT '' COMMENT '菜单地址',
  `parent_id` int(11) DEFAULT '0' COMMENT '父菜单id',
  `is_delete` tinyint(4) DEFAULT '1' COMMENT '1->未删除 2->删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='菜单信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `monitor_panel`
--

DROP TABLE IF EXISTS `monitor_panel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monitor_panel` (
  `panel_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL COMMENT '所属项目id',
  `panel_name` varchar(62) DEFAULT NULL COMMENT '面板名称',
  `is_delete` tinyint(4) DEFAULT '1' COMMENT '1->未删除 2->删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`panel_id`),
  KEY `project_id_index` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='监控面板';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(32) NOT NULL COMMENT '项目名称',
  `project_status` tinyint(4) DEFAULT '1',
  `is_delete` tinyint(4) DEFAULT '1' COMMENT '1->启用，2->禁用',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '1->未删除 2->删除',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`project_id`),
  UNIQUE KEY `UNIQUE_project_name` (`project_name`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='项目信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `register`
--

DROP TABLE IF EXISTS `register`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `register` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '变量id',
  `device_id` bigint(20) NOT NULL COMMENT '设备id,系统变量的设备id用0来代替',
  `var_name` varchar(255) NOT NULL COMMENT '变量显示名称',
  `regis_name` varchar(255) NOT NULL COMMENT '寄存器名字',
  `regis_addr` bigint(20) DEFAULT NULL COMMENT '寄存器地址',
  `regis_type` varchar(8) DEFAULT NULL COMMENT '接口类型，模拟还是数字，BV,AI,AV,BI',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `proj_id` int(11) NOT NULL DEFAULT '0' COMMENT '项目id',
  `regis_value` varchar(255) DEFAULT '0' COMMENT '寄存器变量的值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `device_id_index` (`device_id`,`regis_addr`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3604 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_name` varchar(32) NOT NULL COMMENT '资源名称',
  `menu_id` int(11) NOT NULL COMMENT '菜单id',
  `resource_info` varchar(128) CHARACTER SET latin1 DEFAULT '' COMMENT '资源信息',
  `resource_status` tinyint(4) DEFAULT '1' COMMENT '1->启用，2->禁用',
  `is_delete` tinyint(4) DEFAULT '1' COMMENT '1->未删除 2->删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='资源权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) NOT NULL COMMENT '角色名称',
  `role_status` tinyint(4) DEFAULT '1' COMMENT '1->启用，2->禁用',
  `is_delete` tinyint(4) DEFAULT '1' COMMENT '1->未删除 2->删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role_menu`
--

DROP TABLE IF EXISTS `role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `menu_id` int(11) NOT NULL COMMENT '菜单id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role_resource`
--

DROP TABLE IF EXISTS `role_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` int(11) NOT NULL COMMENT '资源id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='角色资源关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_var`
--

DROP TABLE IF EXISTS `sys_var`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_var` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `var_name` varchar(255) NOT NULL DEFAULT '' COMMENT '变量名称',
  `var_type` varchar(255) DEFAULT NULL COMMENT '变量类型',
  `var_value` varchar(255) DEFAULT NULL COMMENT '变量值',
  `proj_id` int(11) NOT NULL COMMENT '项目id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sys_var_type` int(11) NOT NULL COMMENT '系统变量类型,1->时序变量,2->群组变量',
  `var_id` bigint(20) DEFAULT NULL COMMENT '变量地址,群组变量时这个值就是群组id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='系统变量表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `timing`
--

DROP TABLE IF EXISTS `timing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timing` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `node_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '节点类型，1->普通节点，2->指定日节点，3->指定假日',
  `node_content_run_time` varchar(255) NOT NULL COMMENT '节点内容执行时间',
  `node_content_run_time_type` tinyint(2) DEFAULT '0' COMMENT '执行时间类型，1->普通时间，2->天亮时间，3->天黑时间',
  `node_content_city` varchar(255) DEFAULT NULL,
  `run_var` bigint(20) DEFAULT NULL COMMENT '执行变量,可以是组id,设备id,变量id',
  `run_type` tinyint(4) DEFAULT '0' COMMENT '执行类型，1->群组类型，2->设备类型，3->变量类型',
  `valid_cycle` varchar(255) DEFAULT NULL COMMENT '有效执行周期',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `proj_id` int(11) NOT NULL COMMENT '项目id',
  `run_id` bigint(20) NOT NULL COMMENT '执行id',
  `node_name` varchar(255) NOT NULL DEFAULT '' COMMENT '节点名称',
  `stop_work` tinyint(2) NOT NULL COMMENT '是否在节假日停止,1->停止,0->不停止',
  `week_list` varchar(255) DEFAULT '' COMMENT '普通节点中的周几执行,多个之间用,分割',
  `run_varlue` varchar(255) DEFAULT '' COMMENT '变量或者群组的设定值',
  `is_delete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否删除，1->否,2->是',
  `month_list` varchar(1024) DEFAULT '' COMMENT '指定日节点中的月list',
  `task_name` varchar(256) DEFAULT '' COMMENT '定时任务名称',
  PRIMARY KEY (`id`),
  KEY `proj_id_index` (`proj_id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8 COMMENT='时序表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `timing_cron`
--

DROP TABLE IF EXISTS `timing_cron`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timing_cron` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `timing_id` bigint(20) NOT NULL,
  `cron_json` varchar(2048) DEFAULT '' COMMENT '定时任务对象',
  `task_name` varchar(256) NOT NULL COMMENT '定时任务job name',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=308 DEFAULT CHARSET=utf8 COMMENT='实现和定时任务关联表,一个时序有多个定时任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL COMMENT '用户名、登录名',
  `password` varchar(32) NOT NULL COMMENT '登陆密码',
  `user_status` tinyint(4) DEFAULT '1' COMMENT '1->启用，2->禁用',
  `is_delete` tinyint(4) DEFAULT '1' COMMENT '1->未删除 2->删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UNIQUE_user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_project`
--

DROP TABLE IF EXISTS `user_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `project_id` int(11) NOT NULL COMMENT '项目id',
  `manager_type_id` int(11) DEFAULT NULL COMMENT '1->可控 2->可配置',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COMMENT='用户项目关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-19 17:04:36

ALTER TABLE device ADD  `create_by` int(11) DEFAULT NULL;
ALTER TABLE device ADD  `update_by` int(11) DEFAULT NULL;

ALTER TABLE dtu ADD  `create_by` int(11) DEFAULT NULL;
ALTER TABLE dtu ADD  `update_by` int(11) DEFAULT NULL;

ALTER TABLE `group` ADD  `create_by` int(11) DEFAULT NULL;
ALTER TABLE `group` ADD  `update_by` int(11) DEFAULT NULL;

ALTER TABLE holiday ADD  `create_by` int(11) DEFAULT NULL;
ALTER TABLE holiday ADD  `update_by` int(11) DEFAULT NULL;

ALTER TABLE timing ADD  `create_by` int(11) DEFAULT NULL;
ALTER TABLE timing ADD  `update_by` int(11) DEFAULT NULL;


