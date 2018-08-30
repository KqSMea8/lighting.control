create table alarm_setting{
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `alarm_phone` varchar (20) default "" commit "告警手机号",
   `enable_alarm_phone` int default 0 commit "是否开启短信告警，1->启用,2->禁用",
   `alarm_email`  varchar(64) default "" commit  "告警邮件",
   `enable_alarm_email` int default 0 commit "是否开启邮件告警，1->启用,2->禁用",
   `enable_alarm_sound` int default 0 commit "是否开启语音告警，1->启用,2->禁用",
   `enable_clear_sound` int default 0 commit "是否开启告警消失时发送语音短信，1->启用,2->禁用",
   `interval` int default 0 commit "告警持续出现的时间",
   `clear_alarm_cotent` varchar(255) default "" commit "告警消失时的文本信息",
   `alarm_cotent` varchar (255) default "" commit "告警时的文本信息",
   `alarm_register_id` int not null  commit "告警变量id",
   `alarm_compare_value` varchar (8)  not null commit  "告警触发值",
   `alarm_compare_type` int not null commit "告警触发类型,1->等于，2->大于,3->小于,4->区间,5->不等于"
   `alarm_status` int default 1 commit "告警状态，1->正常状态,2->告警状态",
   `trigger_status` int default 1 commit "触发状态,1->未触发,2->已触发",
   `project_id` int not null commit "项目id",
    `create_by` int(11) DEFAULT NULL,
    `update_by` int(11) DEFAULT NULL,
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
} ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 commit "";

create table alarm_history (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alarm_id` int not null commit "告警id",
  `alarm_register_id` int not null  commit "告警变量id",
  `alarm_value` varchar (8) commit "告警值",
  `alarm_type` int default 0 commit "告警触发类型,1->发生告警,2->消除告警"
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP commit "告警发生时间,或者告警复位时间",
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 commit "告警记录表";

create table alarm_send_history (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alarm_id` int not null commit "告警id",
  `alarm_send_req` varchar(4096) commit "告警发送信息",
  `alarm_send_rsp` varchar (4096) commit "告警响应信息",
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP commit "告警发送时间",
   PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 commit "告警发送历史记录表";


-- 此处更新sequence字段时要用乐观锁,避免并发问题
create table `register_timing` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `register_id` int not null commit "变量id",
   `sequence` int not null  default 0 commit "序列号id,0,1,2,3,4,5",
   `timing_id` int commit "时序id",
   `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP commit "告警发送时间",
   `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 commit "时序优化时添加的设备序号表";


alter table `timing` add `adjust_time` int default 0 commit "指定节假日节点调整时间,正负120之间";

create table `alarm_message`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
     `message` varchar (2048) not null default "" commit "告警消息",
      `user_id` int not null commit "用户id",
      `view` int default 1 commit "是否查看,1->未查看,2->已查看",
     `project_id` int not null commit "项目id",
     `update_by` int(11) DEFAULT NULL,
     `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
     `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 commit "告警消息中心";
