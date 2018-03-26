-- 超级管理员用户信息
INSERT INTO `user`(user_id,user_name,PASSWORD,create_by) VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1');
-- 角色信息
INSERT INTO `role`(role_id,role_name,create_by) VALUES ('1', '超级管理员', 1);
INSERT INTO `role` VALUES ('2', '项目管理员', '1', '1', '2018-01-21 22:42:01', '2018-01-24 15:27:41', '1', NULL);
--  用户角色关联
INSERT INTO `user_role` VALUES ('1', '1', '1', '2018-01-21 22:40:37', '2018-01-23 22:24:02', '1', '1');
-- 菜单
INSERT INTO `menu`(menu_id,menu_name,menu_url,parent_id,create_by) VALUES ('1', '项目列表', '', '0', '1');
INSERT INTO `menu`(menu_id,menu_name,menu_url,parent_id,create_by) VALUES ('2', '用户管理', '', '0', '1');
INSERT INTO `menu`(menu_id,menu_name,menu_url,parent_id,create_by) VALUES ('3', '在线用户', '', '0', '1');
INSERT INTO `menu`(menu_id,menu_name,menu_url,parent_id,create_by) VALUES ('4', '设备设定', '/device', '0', '1');
INSERT INTO `menu`(menu_id,menu_name,menu_url,parent_id,create_by) VALUES ('5', '变量管理', '/variable-edit', '0','1');
INSERT INTO `menu`(menu_id,menu_name,menu_url,parent_id,create_by) VALUES ('6', '连线状态', '/deviceStat', '0', '1');
INSERT INTO `menu`(menu_id,menu_name,menu_url,parent_id,create_by) VALUES ('7', '群组设定', '/group', '0', '1');
INSERT INTO `menu`(menu_id,menu_name,menu_url,parent_id,create_by) VALUES ('8', '时序设定', '/timing', '0', '1');
INSERT INTO `menu`(menu_id,menu_name,menu_url,parent_id,create_by) VALUES ('9', '设备监控', '/monitor', '0', '1');
-- 管理类型信息
INSERT INTO `manager_type`(type_id,manager_type_name,create_by) VALUES ('1', '设备可控','1');
INSERT INTO `manager_type` (type_id,manager_type_name,create_by) VALUES ('2', '可配置', '1');
-- 管理类型和菜单管理关系
INSERT INTO `manager_type_menu`(id,type_id,menu_id,create_by) VALUES ('1', '1', '4','1');
INSERT INTO `manager_type_menu`(id,type_id,menu_id,create_by) VALUES ('2', '1', '6','1');
INSERT INTO `manager_type_menu`(id,type_id,menu_id,create_by) VALUES ('3', '1', '9','1');
INSERT INTO `manager_type_menu`(id,type_id,menu_id,create_by) VALUES ('4', '2', '4','1');
INSERT INTO `manager_type_menu`(id,type_id,menu_id,create_by) VALUES ('5', '2', '5','1');
INSERT INTO `manager_type_menu`(id,type_id,menu_id,create_by) VALUES ('6', '2', '6','1');
INSERT INTO `manager_type_menu`(id,type_id,menu_id,create_by) VALUES ('7', '2', '7','1');
INSERT INTO `manager_type_menu`(id,type_id,menu_id,create_by) VALUES ('8', '2', '8','1');
INSERT INTO `manager_type_menu`(id,type_id,menu_id,create_by) VALUES ('9', '2', '9','1');

INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (1,"/light/user/login",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (2,"/light/user/login-out",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (3,"/light/user/add",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (4,"/light/user/change/info",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (5,"/light/user/change/pwd",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (6,"/light/user/list",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (7,"/light/user/del/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (8,"/light/user/update",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (9,"/light/user/online/list",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (10,"/light/user/project/add",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (11,"/light/user/project/del",1,1);
-- INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (12,"/light/user/project/enter/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (13,"/light/user/project/list/*",1,1);

INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (14,"/light/cnarea/list/*",1,1);


INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (15,"/light/device/del/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (16,"/light/device/add",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (17,"/light/device/update",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (18,"/device/point/table/upload/*",1,1);

-- INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (1,"/lightdevice/list/*",1,1);
-- INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (1,"/light/device/all",1,1);
-- INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (1,"/light/device/online",1,1);

-- INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (1,"/device/online/refresh",1,1);

-- INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (1,"/light/dtu/list",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (19,"/light/dtu/del/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (20,"/light/dtu/add",1,1);
-- INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (1,"/light/dtu/*",1,1);
-- INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (1,"/light/dtu/id/list",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (21,"/light/dtu/update",1,1);

INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (22,"/light/group/list",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (23,"/light/group/add",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (24,"/light/group/del/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (25,"/light/group/device/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (26,"/light/group/del/device/ids",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (27,"/light/group/add/device",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (28,"/light//group/update/device",1,1);


-- INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (1,"/light/history/list",1,1);

INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (29,"/light/type/list",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (30,"/light/type/add",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (31,"/light/type/del/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (32,"/light/type/update",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (33,"/light/type/menu/add",1,1);

-- INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (1,"/light/monitor/info/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (34,"/light/monitor/add",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (35,"/light/monitor/del/*",1,1);
-- INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (1,"/light/monitor/list/*/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (36,"/light/monitor/update",1,1);
-- INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (1,"/light/monitor/change/*/*",1,1);
-- INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (1,"/light/monitor/refresh/*/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (37,"/light/monitor/source/list/*",1,1);

-- INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (1,"/light/project/list",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (38,"/light/project/add",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (39,"/light/project/del/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (40,"/light/project/update",1,1);

INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (41,"/light/register/list",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (42,"/light/register/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (43,"/light/register/value",1,1);

INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (44,"/light/system/add/type/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (45,"/light/system/list/typeValue",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (46,"/light/system/add",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (47,"/light/system/value/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (48,"/light/system/list",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (49,"/light/system/del/*",1,1);

INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (50,"/light/sys/var/update",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (51,"/light/sys/var/list",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (52,"/light/dtu/var/list",1,1);

INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (53,"/light/timing/list/nodetype",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (54,"/light/timing/add/ordinary/node",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (55,"/light/timing/ordinary/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (56,"/light/timing/add/specified/node",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (57,"/light/timing/add/holiday/node",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (58,"/light/timing/del/holiday/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (59,"/light/timing/holiday/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (60,"/light/timing/node/view/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (61,"/light/timing/del/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (62,"/light/timing/board/list",1,1);

INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (63,"/light/panel/add",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (64,"/light/panel/del/*",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (65,"/light/panel/list",1,1);
INSERT INTO `back_uri`(id,back_uri,resource_id,create_by) VALUES (66,"/light/panel/update",1,1);


INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(1,1,14,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(2,1,51,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(3,1,52,1);


INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(4,2,14,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(5,2,15,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(6,2,16,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(7,2,17,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(8,2,18,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(9,2,19,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(10,2,20,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(11,2,21,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(12,2,22,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(13,2,23,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(14,2,24,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(15,2,25,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(16,2,26,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(17,2,27,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(18,2,28,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(19,2,29,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(20,2,30,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(21,2,31,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(22,2,32,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(23,2,33,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(24,2,34,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(25,2,35,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(26,2,36,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(27,2,37,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(28,2,41,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(29,2,42,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(30,2,43,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(31,2,50,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(32,2,51,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(33,2,52,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(34,2,53,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(35,2,54,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(36,2,55,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(37,2,56,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(38,2,57,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(39,2,58,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(40,2,59,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(41,2,60,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(42,2,61,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(43,1,5,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(44,2,5,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(45,2,63,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(46,2,64,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(47,2,65,1);
INSERT INTO `manager_type_uri`(id,manager_type_id,back_uri_id,create_by) VALUES(48,2,66,1);