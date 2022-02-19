-- ----------------------------
-- 用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigint(20)      not null auto_increment    comment '用户ID',
  user_name         varchar(30)     not null                   comment '用户账号',
  nick_name         varchar(30)     not null                   comment '用户昵称',
  email             varchar(50)     default ''                 comment '用户邮箱',
  phone_number      varchar(11)     default ''                 comment '手机号码',
  sex               char(1)         default '2'                comment '用户性别（0-男 1-女 2-未知）',
  avatar            varchar(100)    default ''                 comment '头像地址',
  password          varchar(100)    default ''                 comment '密码',
  status            char(1)         default '0'                comment '帐号状态（0-正常 1-停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0-代表存在 2-代表删除）',
  login_ip          varchar(128)    default ''                 comment '最后登录IP',
  login_date        datetime                                   comment '最后登录时间',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default ''                 comment '备注',
  primary key (user_id)
) engine=innodb auto_increment=100 comment = '用户信息表';

-- ----------------------------
-- 初始化-用户信息表数据
-- ----------------------------
insert into sys_user values(1, 'admin', '检测', 'sltg@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '', null, 'admin', sysdate(), '', null, '管理员');
insert into sys_user values(2, 'sltg',  '谣言', 'sltg@qq.com',  '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '', null, 'admin', sysdate(), '', null, '测试员');

-- ----------------------------
-- 角色信息表
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id              bigint(20)      not null auto_increment    comment '角色ID',
  role_name            varchar(30)     not null                   comment '角色名称',
  role_key             varchar(100)    not null                   comment '角色权限字符串',
  role_sort            int(4)          not null                   comment '显示顺序',
  data_scope           char(1)         default '1'                comment '数据范围（1-全部数据权限 2-自定数据权限）',
  menu_check_strictly  tinyint(1)      default 1                  comment '菜单树选择项是否关联显示',
  status               char(1)         not null                   comment '角色状态（0-正常 1-停用）',
  del_flag             char(1)         default '0'                comment '删除标志（0-存在 2-删除）',
  create_by            varchar(64)     default ''                 comment '创建者',
  create_time          datetime                                   comment '创建时间',
  update_by            varchar(64)     default ''                 comment '更新者',
  update_time          datetime                                   comment '更新时间',
  remark               varchar(500)    default ''                 comment '备注',
  primary key (role_id)
) engine=innodb auto_increment=100 comment = '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into sys_role values('1', '超级管理员',  'admin',  1, 1, 1, '0', '0', 'admin', sysdate(), '', null, '超级管理员');
insert into sys_role values('2', '普通角色',    'common', 2, 2, 1, '0', '0', 'admin', sysdate(), '', null, '普通角色');


-- ----------------------------
-- 菜单权限表
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint(20)      not null auto_increment    comment '菜单ID',
  menu_name         varchar(50)     not null                   comment '菜单名称',
  parent_id         bigint(20)      default 0                  comment '父菜单ID',
  order_num         int(4)          default 0                  comment '显示顺序',
  path              varchar(200)    default ''                 comment '路由地址',
  component         varchar(255)    default ''                 comment '组件路径',
  is_frame          int(1)          default 1                  comment '是否为外链（0-是 1-否）',
  is_cache          int(1)          default 0                  comment '是否缓存（0-缓存 1-不缓存）',
  menu_type         char(1)         default ''                 comment '菜单类型（M-目录 C-菜单 B-按钮）',
  visible           char(1)         default 0                  comment '菜单状态（0-显示 1-隐藏）',
  status            char(1)         default 0                  comment '菜单状态（0-正常 1-停用）',
  perms             varchar(100)    default ''                 comment '权限标识',
  icon              varchar(100)    default '#'                comment '菜单图标',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default ''                 comment '备注',
  primary key (menu_id)
) engine=innodb auto_increment=2000 comment = '菜单权限表';

-- ----------------------------
-- 初始化-菜单信息表数据
-- ----------------------------
-- 一级菜单
insert into sys_menu values('1', '新闻中心', '0', '1', 'news',             null,   1, 0, 'M', '0', '0', '', 'international',   'admin', sysdate(), '', null, '新闻中心目录');
insert into sys_menu values('2', '用户中心', '0', '2', 'user',             null,   1, 0, 'M', '0', '0', '', 'people',          'admin', sysdate(), '', null, '用户中心目录');
insert into sys_menu values('3', '系统管理', '0', '3', 'system',           null,   1, 0, 'M', '0', '0', '', 'system',          'admin', sysdate(), '', null, '系统管理目录');

-- 二级菜单
insert into sys_menu values('90',  '新闻列表', '1',   '1', 'table',    'news/table/index',        1, 0, 'C', '0', '0', 'news:table:list',        'example',          'admin', sysdate(), '', null, '新闻列表菜单');
insert into sys_menu values('91',  '新闻分析', '1',   '2', 'analysis',    'news/analysis/index',    1, 0, 'C', '0', '0', 'news:analysis:list',     'swagger',         'admin', sysdate(), '', null, '新闻分析菜单');
insert into sys_menu values('92',  '知识库',   '1',   '3', 'knowledge',   'news/knowledge/index',   1, 0, 'C', '0', '0', 'news:knowledge:list',    'redis',          'admin', sysdate(), '', null, '知识库菜单');

insert into sys_menu values('100',  '新闻管理', '2',   '1', 'management',  'user/management/index',  1, 0, 'C', '0', '0', 'user:news:list',    'nested',         'admin', sysdate(), '', null, '用户中心菜单');
insert into sys_menu values('101',  '消息公告', '2',   '2', 'notice',      'system/notice/index',    1, 0, 'C', '0', '0', 'system:notice:list',      'message',        'admin', sysdate(), '', null, '消息公告菜单');

insert into sys_menu values('200',  '用户管理', '3',   '1', 'user',       'system/user/index',        1, 0, 'C', '0', '0', 'system:user:list',        'user',          'admin', sysdate(), '', null, '用户管理菜单');
insert into sys_menu values('201',  '定时任务', '3',   '2', 'job',        'monitor/job/index',        1, 0, 'C', '0', '0', 'monitor:job:list',        'job',           'admin', sysdate(), '', null, '定时任务菜单');
insert into sys_menu values('202',  '操作日志', '3',   '3', 'log', 'monitor/operateLog/index',    1, 0, 'C', '0', '0', 'monitor:operateLog:list',    'form',          'admin', sysdate(), '', null, '操作日志菜单');

-- 用户新闻管理按钮
insert into sys_menu values('800', '新闻查询', '100', '1',  '#', '', 1, 0, 'B', '0', '0', 'user:news:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('801', '新闻新增', '100', '2',  '#', '', 1, 0, 'B', '0', '0', 'user:news:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('802', '新闻修改', '100', '3',  '#', '', 1, 0, 'B', '0', '0', 'user:news:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('803', '新闻删除', '100', '4',  '#', '', 1, 0, 'B', '0', '0', 'user:news:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('804', '新闻导出', '100', '5',  '#', '', 1, 0, 'B', '0', '0', 'user:news:export',         '#', 'admin', sysdate(), '', null, '');
-- 消息公告按钮
insert into sys_menu values('900', '消息查询', '101', '1', '#', '', 1, 0, 'B', '0', '0', 'system:notice:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('901', '消息新增', '101', '2', '#', '', 1, 0, 'B', '0', '0', 'system:notice:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('902', '消息修改', '101', '3', '#', '', 1, 0, 'B', '0', '0', 'system:notice:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('903', '消息删除', '101', '4', '#', '', 1, 0, 'B', '0', '0', 'system:notice:remove',       '#', 'admin', sysdate(), '', null, '');
-- 用户管理按钮
insert into sys_menu values('1001', '用户查询', '200', '1',  '', '', 1, 0, 'B', '0', '0', 'system:user:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1002', '用户新增', '200', '2',  '', '', 1, 0, 'B', '0', '0', 'system:user:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1003', '用户修改', '200', '3',  '', '', 1, 0, 'B', '0', '0', 'system:user:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1004', '用户删除', '200', '4',  '', '', 1, 0, 'B', '0', '0', 'system:user:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1005', '用户导出', '200', '5',  '', '', 1, 0, 'B', '0', '0', 'system:user:export',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1006', '重置密码', '200', '6',  '', '', 1, 0, 'B', '0', '0', 'system:user:resetPwd',       '#', 'admin', sysdate(), '', null, '');
-- 定时任务按钮
insert into sys_menu values('1100', '任务查询', '201', '1', '#', '', 1, 0, 'B', '0', '0', 'monitor:job:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1101', '任务新增', '201', '2', '#', '', 1, 0, 'B', '0', '0', 'monitor:job:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1102', '任务修改', '201', '3', '#', '', 1, 0, 'B', '0', '0', 'monitor:job:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1103', '任务删除', '201', '4', '#', '', 1, 0, 'B', '0', '0', 'monitor:job:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1104', '状态修改', '201', '5', '#', '', 1, 0, 'B', '0', '0', 'monitor:job:changeStatus',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1105', '任务导出', '201', '6', '#', '', 1, 0, 'B', '0', '0', 'monitor:job:export',         '#', 'admin', sysdate(), '', null, '');

-- 操作日志按钮
insert into sys_menu values('1201', '操作查询', '202', '1', '#', '', 1, 0, 'B', '0', '0', 'monitor:operateLog:query',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1202', '操作删除', '202', '2', '#', '', 1, 0, 'B', '0', '0', 'monitor:operateLog:remove',     '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1203', '日志导出', '202', '3', '#', '', 1, 0, 'B', '0', '0', 'monitor:operateLog:export',     '#', 'admin', sysdate(), '', null, '');

-- ----------------------------
-- 用户和角色关联表  用户N-1角色
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint(20) not null comment '用户ID',
  role_id   bigint(20) not null comment '角色ID',
  primary key(user_id, role_id)
) engine=innodb comment = '用户和角色关联表';

-- ----------------------------
-- 初始化-用户和角色关联表数据
-- ----------------------------
insert into sys_user_role values ('1', '1');
insert into sys_user_role values ('2', '2');


-- ----------------------------
-- 角色和菜单关联表  角色1-N菜单
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint(20) not null comment '角色ID',
  menu_id   bigint(20) not null comment '菜单ID',
  primary key(role_id, menu_id)
) engine=innodb comment = '角色和菜单关联表';

-- ----------------------------
-- 初始化-角色和菜单关联表数据
-- ----------------------------
insert into sys_role_menu values ('2', '1');
insert into sys_role_menu values ('2', '2');
insert into sys_role_menu values ('2', '90');
insert into sys_role_menu values ('2', '91');
insert into sys_role_menu values ('2', '92');
insert into sys_role_menu values ('2', '100');
insert into sys_role_menu values ('2', '101');
insert into sys_role_menu values ('2', '800');
insert into sys_role_menu values ('2', '801');
insert into sys_role_menu values ('2', '802');
insert into sys_role_menu values ('2', '803');
insert into sys_role_menu values ('2', '804');
insert into sys_role_menu values ('2', '900');

-- ----------------------------
-- 操作日志记录
-- ----------------------------
drop table if exists sys_operate_log;
create table sys_operate_log (
  operate_id        bigint(20)      not null auto_increment    comment '日志主键',
  title             varchar(50)     default ''                 comment '模块标题',
  business_type     int(2)          default 0                  comment '业务类型（0-其它 1-新增 2-修改 3-删除）',
  method            varchar(100)    default ''                 comment '方法名称',
  request_method    varchar(10)     default ''                 comment '请求方式',
  operate_type      int(1)          default 0                  comment '操作类别（0-其它 1-后台用户 2-手机端用户）',
  operate_name      varchar(50)     default ''                 comment '操作人员',
  operate_url       varchar(255)    default ''                 comment '请求URL',
  operate_ip        varchar(128)    default ''                 comment '主机地址',
  operate_location  varchar(255)    default ''                 comment '操作地点',
  operate_param     varchar(2000)   default ''                 comment '请求参数',
  json_result       varchar(2000)   default ''                 comment '返回参数',
  status            int(1)          default 0                  comment '操作状态（0-正常 1-异常）',
  error_msg         varchar(2000)   default ''                 comment '错误消息',
  operate_time      datetime                                   comment '操作时间',
  primary key (operate_id)
) engine=innodb auto_increment=100 comment = '操作日志记录';

-- ----------------------------
-- 字典数据表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data (
  dict_code        bigint(20)      not null auto_increment    comment '字典编码',
  dict_sort        int(4)          default 0                  comment '字典排序',
  dict_label       varchar(100)    default ''                 comment '字典标签',
  dict_value       varchar(100)    default ''                 comment '字典键值',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  css_class        varchar(100)    default ''                 comment '样式属性（其他样式扩展）',
  list_class       varchar(100)    default ''                 comment '表格回显样式',
  is_default       char(1)         default 'N'                comment '是否默认（Y-是 N-否）',
  status           char(1)         default '0'                comment '状态（0-正常 1-停用）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default ''                 comment '备注',
  primary key (dict_code)
) engine=innodb auto_increment=100 comment = '字典数据表';

insert into sys_dict_data values(1,  1,  '男',       '0',       'sys_user_sex',        '',   '',        'Y', '0', 'admin', sysdate(), '', null, '性别男');
insert into sys_dict_data values(2,  2,  '女',       '1',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, '性别女');
insert into sys_dict_data values(3,  3,  '未知',     '2',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, '性别未知');
insert into sys_dict_data values(4,  1,  '正常',     '0',       'sys_normal_disable',  '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(5,  2,  '停用',     '1',       'sys_normal_disable',  '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停用状态');
insert into sys_dict_data values(6,  1,  '正常',     '0',       'sys_job_status',      '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(7,  2,  '失败',     '1',       'sys_job_status',      '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '失败状态');
insert into sys_dict_data values(8,  1,  '默认',     'DEFAULT', 'sys_job_group',       '',   '',        'Y', '0', 'admin', sysdate(), '', null, '默认分组');
insert into sys_dict_data values(9,  2,  '系统',     'SYSTEM',  'sys_job_group',       '',   '',        'N', '0', 'admin', sysdate(), '', null, '系统分组');
insert into sys_dict_data values(10, 1,  '通知',     '1',       'sys_notice_type',     '',   'warning', 'Y', '0', 'admin', sysdate(), '', null, '通知');
insert into sys_dict_data values(11, 2,  '公告',     '2',       'sys_notice_type',     '',   'success', 'N', '0', 'admin', sysdate(), '', null, '公告');
insert into sys_dict_data values(12, 1,  '正常',     '0',       'sys_notice_status',   '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(13, 2,  '关闭',     '1',       'sys_notice_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '关闭状态');
insert into sys_dict_data values(14, 1,  '新增',     '1',       'sys_operate_type',    '',   'info',    'N', '0', 'admin', sysdate(), '', null, '新增操作');
insert into sys_dict_data values(15, 2,  '修改',     '2',       'sys_operate_type',    '',   'info',    'N', '0', 'admin', sysdate(), '', null, '修改操作');
insert into sys_dict_data values(16, 3,  '删除',     '3',       'sys_operate_type',    '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '删除操作');
insert into sys_dict_data values(17, 4,  '导入',     '4',       'sys_operate_type',    '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '导入操作');
insert into sys_dict_data values(18, 5,  '导出',     '5',       'sys_operate_type',    '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '导出操作');
insert into sys_dict_data values(19, 1,  '成功',     '0',       'sys_common_status',   '',   'primary', 'N', '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(20, 2,  '失败',     '1',       'sys_common_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '停用状态');
insert into sys_dict_data values(21, 1,  '虚假',     '0',       'sys_detection_type',  '',   'false',  'N', '0', 'admin', sysdate(), '', null, '检测分类');
insert into sys_dict_data values(22, 2,  '疑似诈骗',  '1',       'sys_detection_type',  '',   'suspectedFraud',  'N', '0', 'admin', sysdate(), '', null, '检测分类');
insert into sys_dict_data values(23, 3,  '真实',     '2',       'sys_detection_type',  '',   'real',  'N', '0', 'admin', sysdate(), '', null, '检测分类');
insert into sys_dict_data values(24, 4,  '待定',     '3',       'sys_detection_type',  '',   'undetermined',  'N', '0', 'admin', sysdate(), '', null, '检测分类');
insert into sys_dict_data values(25, 5,  '分情况',   '4',       'sys_detection_type',  '',   'diffScene',  'N', '0', 'admin', sysdate(), '', null, '检测分类');
insert into sys_dict_data values(36, 6,  '其他',     '5',       'sys_detection_type',  '',   'other',  'N', '0', 'admin', sysdate(), '', null, '检测分类');

-- ----------------------------
-- 系统访问记录
-- ----------------------------
drop table if exists sys_login_info;
create table sys_login_info (
  info_id        bigint(20)     not null auto_increment   comment '访问ID',
  user_name      varchar(50)    default ''                comment '用户账号',
  ipaddr         varchar(128)   default ''                comment '登录IP地址',
  login_location varchar(255)   default ''                comment '登录地点',
  browser        varchar(50)    default ''                comment '浏览器类型',
  os             varchar(50)    default ''                comment '操作系统',
  status         char(1)        default '0'               comment '登录状态（0-成功 1-失败）',
  msg            varchar(255)   default ''                comment '提示消息',
  login_time     datetime                                 comment '访问时间',
  primary key (info_id)
) engine=innodb auto_increment=100 comment = '系统访问记录';


-- ----------------------------
-- 定时任务调度表
-- ----------------------------
drop table if exists sys_job;
create table sys_job (
  job_id              bigint(20)    not null auto_increment    comment '任务ID',
  job_name            varchar(64)   default ''                 comment '任务名称',
  job_group           varchar(64)   default 'DEFAULT'          comment '任务组名',
  invoke_target       varchar(500)  not null                   comment '调用目标字符串',
  cron_expression     varchar(255)  default ''                 comment 'cron执行表达式',
  misfire_policy      varchar(20)   default '3'                comment '计划执行错误策略（1-立即执行 2-执行一次 3-放弃执行）',
  concurrent          char(1)       default '1'                comment '是否并发执行（0-允许 1-禁止）',
  status              char(1)       default '0'                comment '状态（0-正常 1-暂停）',
  create_by           varchar(64)   default ''                 comment '创建者',
  create_time         datetime                                 comment '创建时间',
  update_by           varchar(64)   default ''                 comment '更新者',
  update_time         datetime                                 comment '更新时间',
  remark              varchar(500)  default ''                 comment '备注信息',
  primary key (job_id, job_name, job_group)
) engine=innodb auto_increment=1 comment = '定时任务调度表';

insert into sys_job values (1, '网易新闻爬虫', 'DEFAULT', 'sltgTask.sltgMultipleParams(\'spider/wy-news-spider.py\', \'currentDate\')', '0 10 0 * * ? *', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values (2, '新浪新闻爬虫', 'DEFAULT', 'sltgTask.sltgMultipleParams(\'spider/sina-news-spider.py\', \'currentDate\')', '0 30 0 * * ? *', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values (3, '腾讯较真平台新闻爬虫', 'DEFAULT', 'sltgTask.sltgParams(\'spider/tencentFactSpider.py\')', '0 50 0 * * ? *', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values (4, '辟谣官方平台新闻爬虫', 'DEFAULT', 'sltgTask.sltgMultipleParams(\'spider/piyao-org-news-spider.py\', \'currentDate\')', '0 10 1 * * ? *', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values (5, '微博新闻爬虫', 'DEFAULT', 'sltgTask.sltgMultipleParams(\'spider/weibo-news-spider.py\', \'currentDate\')', '0 40 1 * * ? *', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values (6, '新闻词云', 'DEFAULT', 'sltgTask.sltgParams(\'spider/gjc_cp.py\')', '0 0 2 * * ? *', '3', '1', '1', 'admin', sysdate(), '', null, '');

-- ----------------------------
-- 定时任务调度日志表
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          bigint(20)     not null auto_increment    comment '任务日志ID',
  original_job_id     bigint(20)     not null                   comment '原始任务ID',
  job_name            varchar(64)    not null                   comment '任务名称',
  job_group           varchar(64)    not null                   comment '任务组名',
  invoke_target       varchar(500)   not null                   comment '调用目标字符串',
  job_runtime         varchar(20)    default 0                  comment '任务运行时间',
  job_message         varchar(2000)  default ''                 comment '日志信息',
  status              char(1)        default '0'                comment '执行状态（0正常 1失败）',
  exception_info      varchar(2000)  default ''                 comment '异常信息',
  create_time         datetime                                  comment '创建时间',
  primary key (job_log_id)
) engine=innodb comment = '定时任务调度日志表';


-- ----------------------------
-- 消息公告表
-- ----------------------------
drop table if exists sys_notice;
create table sys_notice (
  notice_id         int(4)          not null auto_increment    comment '公告ID',
  notice_title      varchar(50)     not null                   comment '公告标题',
  notice_type       char(1)         not null                   comment '公告类型（1-通知 2-公告）',
  notice_content    longblob        default null               comment '公告内容',
  status            char(1)         default '0'                comment '公告状态（0-正常 1-关闭）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(255)    default ''                 comment '备注',
  primary key (notice_id)
) engine=innodb auto_increment=10 comment = '消息公告表';

-- ----------------------------
-- 初始化-公告信息表数据
-- ----------------------------
insert into sys_notice values('1', '温馨提醒：2021-03-14 谣言检测系统新版本发布啦', '2', '新版本内容', '0', 'admin', sysdate(), '', null, '管理员');
insert into sys_notice values('2', '维护通知：2021-05-01 谣言检测系统凌晨维护', '1', '维护内容',   '0', 'admin', sysdate(), '', null, '管理员');


-- ----------------------------
-- 新闻信息表
-- ----------------------------
drop table if exists sys_news;
create table sys_news (
    news_index         bigint(20)     not null auto_increment    comment '新闻序列号',
    news_id            varchar(100)   not null                   comment '新闻ID',
    news_title         varchar(100)   not null                   comment '新闻标题',
    have_title         char(1)        default 0                  comment '新闻内容是否有标题（0-是 1-否）',
    news_url           varchar(200)   not null                   comment '新闻url',
    news_text          varchar(5000)  default ''                 comment '新闻正文',
    news_theme         varchar(100)   not null                   comment '新闻关键词',
    detection_percent  char(10)       default ''                 comment '虚假检测百分比',
    detection_type     char(1)        default ''                 comment '检测类型',
    news_date          varchar(10)    not null                   comment '新闻日期',
    news_spider        varchar(200)   not null                   comment '新闻获取来源',
    news_from          varchar(200)   default ''                 comment '新闻真实来源',
    is_file            char(1)        default 0                  comment '新闻内容是否存为文件（0-是 1-否）',
    create_time        datetime                                  comment '创建时间',
    update_by          varchar(64)    default ''                 comment '更新者',
    update_time        datetime                                  comment '更新时间',
    remark             varchar(255)   default ''                 comment '备注',
    primary key (news_index)
) engine=innodb auto_increment=0 comment = '新闻信息表';


-- ----------------------------
-- 虚假新闻知识库表
-- ----------------------------
drop table if exists sys_news_knowledge;
create table sys_news_knowledge (
    news_index         bigint(20)     not null auto_increment    comment '新闻序列号',
    news_id            varchar(100)   not null                   comment '新闻ID',
    news_title         varchar(200)   not null                   comment '新闻标题',
    news_url           varchar(200)   not null                   comment '新闻url',
    news_theme         varchar(100)   not null                   comment '新闻关键词',
    detection_type     char(1)        default ''                 comment '检测类型',
    detection_result   varchar(50)    default ''                 comment '检测结果',
    news_date          varchar(10)    not null                   comment '新闻日期',
    news_spider        varchar(200)   not null                   comment '新闻获取来源',
    news_from          varchar(200)   default ''                 comment '新闻真实来源',
    original_url       varchar(200)   default ''                 comment '新闻来源url，为空则本平台新闻',
    create_time        datetime                                  comment '创建时间',
    update_by          varchar(64)    default ''                 comment '更新者',
    update_time        datetime                                  comment '更新时间',
    remark             varchar(255)   default ''                 comment '备注',
    primary key (news_index)
) engine=innodb auto_increment=0 comment = '虚假新闻知识库表';



-- ----------------------------
-- 用户新闻信息表
-- ----------------------------
drop table if exists sys_user_news;
create table sys_user_news (
    news_id            bigint(20)     not null auto_increment    comment '新闻ID',
    user_id            bigint(20)     not null                   comment '用户ID',
    news_title         varchar(100)   not null                   comment '新闻标题',
    news_theme         varchar(100)   not null                   comment '新闻关键词',
    news_date          varchar(10)    not null                   comment '新闻日期',
    news_from          varchar(50)    default ''                 comment '新闻来源',
    detection_percent  char(10)       default ''                 comment '虚假检测百分比',
    detection_type     char(1)        default ''                 comment '检测类型',
    content_file       varchar(100)   not null                   comment '新闻内容文件',
    comment_file       varchar(100)   default ''                 comment '新闻评论文件',
    store_id           varchar(50)    default ''                 comment '储存id',
    del_flag           char(1)        default '0'                comment '删除标志（0-代表存在 2-代表删除）',
    create_time        datetime                                  comment '创建时间',
    update_by          varchar(64)    default ''                 comment '更新者',
    update_time        datetime                                  comment '更新时间',
    remark             varchar(255)   default ''                 comment '备注',
    primary key (news_id),
    foreign key (user_id) references sys_user(user_id)
) engine=innodb auto_increment=0 comment = '用户新闻信息表';


