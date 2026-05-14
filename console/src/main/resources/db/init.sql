drop table if exists tb_sys_role_menu;
drop table if exists tb_org_user;
drop table if exists tb_sys_role_user;
drop table if exists tb_operation_log;
drop table if exists tb_face_auth_log;
drop table if exists tb_file_record;
drop table if exists tb_app;
drop table if exists tb_sys_menu;
drop table if exists tb_user;
drop table if exists tb_sys_role;
drop table if exists tb_org;

create table tb_org (
    id bigint not null auto_increment comment '主键 ID',
    parent_id bigint not null default 0 comment '父组织 ID，0 表示根节点',
    org_code varchar(64) not null comment '组织编码',
    name varchar(128) not null comment '组织名称',
    leader_name varchar(64) null comment '负责人名称',
    sort_order int not null default 0 comment '排序值',
    status tinyint not null default 1 comment '状态，1 启用，0 禁用',
    create_by bigint null comment '创建人 ID',
    create_time datetime not null default current_timestamp comment '创建时间，默认当前时间',
    update_by bigint null comment '更新人 ID',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间，默认当前时间并在更新时自动刷新',
    deleted tinyint not null default 0 comment '逻辑删除标记，0 正常，1 删除',
    primary key (id)
) comment='组织表';

create index idx_tb_org_parent_id_deleted on tb_org (parent_id, deleted);
create index idx_tb_org_org_code_deleted on tb_org (org_code, deleted);
create index idx_tb_org_status_deleted on tb_org (status, deleted);

create table tb_user (
    id bigint not null auto_increment comment '主键 ID',
    username varchar(64) not null comment '用户名',
    full_name varchar(64) null comment '姓名',
    email varchar(128) null comment '邮箱',
    phone varchar(32) null comment '手机号',
    id_card varchar(128) null comment '身份证号',
    face_file_id varchar(100) null comment '人脸文件 ID',
    face_feature text null comment '人脸特征值',
    password varchar(255) null comment '密码密文',
    status tinyint not null default 1 comment '状态，1 启用，0 禁用',
    create_by bigint null comment '创建人 ID',
    create_time datetime not null default current_timestamp comment '创建时间，默认当前时间',
    update_by bigint null comment '更新人 ID',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间，默认当前时间并在更新时自动刷新',
    deleted tinyint not null default 0 comment '逻辑删除标记，0 正常，1 删除',
    primary key (id)
) comment='用户表';

create index idx_tb_user_username_deleted on tb_user (username, deleted);
create index idx_tb_user_full_name_deleted on tb_user (full_name, deleted);
create index idx_tb_user_email_deleted on tb_user (email, deleted);
create index idx_tb_user_phone_deleted on tb_user (phone, deleted);
create index idx_tb_user_id_card_deleted on tb_user (id_card, deleted);
create index idx_tb_user_face_file_id_deleted on tb_user (face_file_id, deleted);
create index idx_tb_user_status_deleted on tb_user (status, deleted);

create table tb_sys_role (
    id bigint not null auto_increment comment '主键 ID',
    code varchar(64) not null comment '角色编码',
    name varchar(128) not null comment '角色名称',
    status tinyint not null default 1 comment '状态，1 启用，0 禁用',
    remark varchar(255) null comment '备注',
    create_by bigint null comment '创建人 ID',
    create_time datetime not null default current_timestamp comment '创建时间，默认当前时间',
    update_by bigint null comment '更新人 ID',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间，默认当前时间并在更新时自动刷新',
    deleted tinyint not null default 0 comment '逻辑删除标记，0 正常，1 删除',
    primary key (id)
) comment='角色表';

create index idx_tb_sys_role_code_deleted on tb_sys_role (code, deleted);
create index idx_tb_sys_role_name_deleted on tb_sys_role (name, deleted);
create index idx_tb_sys_role_status_deleted on tb_sys_role (status, deleted);

create table tb_sys_menu (
    id bigint not null auto_increment comment '主键 ID',
    parent_id bigint not null default 0 comment '父菜单 ID，0 表示根节点',
    menu_name varchar(128) not null comment '菜单名称',
    path varchar(255) null comment '菜单路由路径',
    icon varchar(128) null comment '菜单图标',
    menu_type varchar(32) not null comment '菜单类型',
    menu_code varchar(128) null comment '权限编码',
    order_num int not null default 0 comment '排序值',
    visible tinyint not null default 1 comment '是否可见，1 可见，0 不可见',
    create_by bigint null comment '创建人 ID',
    create_time datetime not null default current_timestamp comment '创建时间，默认当前时间',
    update_by bigint null comment '更新人 ID',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间，默认当前时间并在更新时自动刷新',
    deleted tinyint not null default 0 comment '逻辑删除标记，0 正常，1 删除',
    primary key (id)
) comment='菜单表';

create index idx_tb_sys_menu_parent_id_deleted on tb_sys_menu (parent_id, deleted);
create index idx_tb_sys_menu_menu_code_deleted on tb_sys_menu (menu_code, deleted);
create index idx_tb_sys_menu_menu_type_deleted on tb_sys_menu (menu_type, deleted);

create table tb_operation_log (
    id bigint not null auto_increment comment '主键 ID',
    module_name varchar(64) not null comment '模块名称',
    action_name varchar(64) not null comment '操作名称',
    operator_name varchar(64) null comment '操作人名称',
    request_path varchar(255) null comment '请求路径',
    success_flag tinyint not null default 1 comment '是否成功，1 成功，0 失败',
    request_time datetime not null comment '请求时间',
    create_by bigint null comment '创建人 ID',
    create_time datetime not null default current_timestamp comment '创建时间，默认当前时间',
    update_by bigint null comment '更新人 ID',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间，默认当前时间并在更新时自动刷新',
    deleted tinyint not null default 0 comment '逻辑删除标记，0 正常，1 删除',
    primary key (id)
) comment='操作日志表';

create index idx_tb_operation_log_request_time_deleted on tb_operation_log (request_time, deleted);
create index idx_tb_operation_log_module_name_deleted on tb_operation_log (module_name, deleted);
create index idx_tb_operation_log_operator_name_deleted on tb_operation_log (operator_name, deleted);
create index idx_tb_operation_log_success_flag_deleted on tb_operation_log (success_flag, deleted);

create table tb_face_auth_log (
    id bigint not null auto_increment comment '主键 ID',
    auth_api_type tinyint not null default 1 comment '认证接口类型，1 表示 1:1，2 表示 1:N',
    ip varchar(64) null comment '请求 IP',
    app_id bigint null comment '应用 ID',
    app_name varchar(100) null comment '应用名称',
    auth_full_name varchar(100) null comment '认证人员姓名',
    auth_user_id bigint null comment '认证人员 ID',
    status tinyint not null default 0 comment '状态，0 失败 1 成功',
    errmsg varchar(500) null comment '失败原因',
    create_by bigint null comment '创建人',
    create_time datetime not null default current_timestamp comment '创建时间',
    update_by bigint null comment '更新人',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    deleted tinyint not null default 0 comment '逻辑删除标记',
    primary key (id)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='人脸认证日志表';

create index idx_tb_face_auth_log_auth_api_type_deleted on tb_face_auth_log (auth_api_type, deleted);
create index idx_tb_face_auth_log_app_id_deleted on tb_face_auth_log (app_id, deleted);
create index idx_tb_face_auth_log_auth_user_id_deleted on tb_face_auth_log (auth_user_id, deleted);
create index idx_tb_face_auth_log_status_deleted on tb_face_auth_log (status, deleted);
create index idx_tb_face_auth_log_create_time_deleted on tb_face_auth_log (create_time, deleted);

create table tb_sys_role_user (
    id bigint not null auto_increment comment '主键 ID',
    user_id bigint not null comment '用户 ID',
    role_id bigint not null comment '角色 ID',
    create_by bigint null comment '创建人 ID',
    create_time datetime not null default current_timestamp comment '创建时间，默认当前时间',
    update_by bigint null comment '更新人 ID',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间，默认当前时间并在更新时自动刷新',
    deleted tinyint not null default 0 comment '逻辑删除标记，0 正常，1 删除',
    primary key (id)
) comment='用户角色关联表';

create index idx_tb_sys_role_user_user_id_deleted on tb_sys_role_user (user_id, deleted);
create index idx_tb_sys_role_user_role_id_deleted on tb_sys_role_user (role_id, deleted);

create table tb_org_user (
    id bigint not null auto_increment comment '主键 ID',
    org_id bigint not null comment '组织 ID',
    user_id bigint not null comment '用户 ID',
    create_by bigint null comment '创建人 ID',
    create_time datetime not null default current_timestamp comment '创建时间，默认当前时间',
    update_by bigint null comment '更新人 ID',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间，默认当前时间并在更新时自动刷新',
    deleted tinyint not null default 0 comment '逻辑删除标记，0 正常，1 删除',
    primary key (id)
) comment='组织用户关联表';

create index idx_tb_org_user_org_id_deleted on tb_org_user (org_id, deleted);
create index idx_tb_org_user_user_id_deleted on tb_org_user (user_id, deleted);

create table tb_sys_role_menu (
    id bigint not null auto_increment comment '主键 ID',
    role_id bigint not null comment '角色 ID',
    menu_id bigint not null comment '菜单 ID',
    create_by bigint null comment '创建人 ID',
    create_time datetime not null default current_timestamp comment '创建时间，默认当前时间',
    update_by bigint null comment '更新人 ID',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间，默认当前时间并在更新时自动刷新',
    deleted tinyint not null default 0 comment '逻辑删除标记，0 正常，1 删除',
    primary key (id)
) comment='角色菜单关联表';

create index idx_tb_sys_role_menu_role_id_deleted on tb_sys_role_menu (role_id, deleted);
create index idx_tb_sys_role_menu_menu_id_deleted on tb_sys_role_menu (menu_id, deleted);

create table tb_file_record (
    id bigint not null auto_increment comment '主键 ID',
    file_id varchar(100) null comment '文件业务 ID',
    file_name varchar(100) null comment '文件名称',
    file_category varchar(32) not null default 'COMMON' comment '文件分类',
    object_name varchar(200) null comment '对象存储中的对象名',
    content_type varchar(100) null comment '文件内容类型',
    file_suffix varchar(100) null comment '文件后缀',
    file_size bigint null comment '文件大小，单位字节',
    create_by bigint null comment '创建人 ID',
    create_time datetime not null default current_timestamp comment '创建时间，默认当前时间',
    update_by bigint null comment '更新人 ID',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间，默认当前时间并在更新时自动刷新',
    deleted tinyint not null default 0 comment '逻辑删除标记，0 正常，1 删除',
    primary key (id)
) comment='文件记录表';

create index idx_tb_file_record_file_id_deleted on tb_file_record (file_id, deleted);
create index idx_tb_file_record_file_category_deleted on tb_file_record (file_category, deleted);
create index idx_tb_file_record_object_name_deleted on tb_file_record (object_name, deleted);
create index idx_tb_file_record_create_time_deleted on tb_file_record (create_time, deleted);

create table tb_app (
    id bigint not null auto_increment comment '主键 ID',
    app_name varchar(128) not null comment '应用名称',
    app_code varchar(64) not null comment '应用编码',
    client_id varchar(128) not null comment '客户端 ID',
    client_secret varchar(255) not null comment '客户端密钥',
    remark varchar(255) null comment '备注',
    create_by bigint null comment '创建人 ID',
    create_time datetime not null default current_timestamp comment '创建时间，默认当前时间',
    update_by bigint null comment '更新人 ID',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间，默认当前时间并在更新时自动刷新',
    deleted tinyint not null default 0 comment '逻辑删除标记，0 正常，1 删除',
    primary key (id)
) comment='应用表';

create index idx_tb_app_app_code_deleted on tb_app (app_code, deleted);
create index idx_tb_app_client_id_deleted on tb_app (client_id, deleted);
create index idx_tb_app_app_name_deleted on tb_app (app_name, deleted);

insert into tb_org (
    id,
    parent_id,
    org_code,
    name,
    leader_name,
    sort_order,
    status,
    create_by,
    create_time,
    update_by,
    update_time,
    deleted
) values (
    1,
    0,
    'ROOT',
    '总部',
    '系统负责人',
    1,
    1,
    1,
    now(),
    1,
    now(),
    0
);

insert into tb_sys_role (
    id,
    code,
    name,
    status,
    remark,
    create_by,
    create_time,
    update_by,
    update_time,
    deleted
) values
    (
        1,
        'ADMIN',
        '系统管理员',
        1,
        '系统初始化管理员角色',
        1,
        now(),
        1,
        now(),
        0
    ),
    (
        2,
        'SUPER_ADMIN',
        '超级管理员',
        1,
        '系统超级管理员角色',
        1,
        now(),
        1,
        now(),
        0
    );

insert into tb_user (
    id,
    username,
    full_name,
    email,
    phone,
    id_card,
    face_file_id,
    face_feature,
    password,
    status,
    create_by,
    create_time,
    update_by,
    update_time,
    deleted
) values (
    1,
    'admin',
    '超级管理员',
    'admin@local.test',
    null,
    null,
    null,
    null,
    '$2a$10$5jEE.2xAuijvcRpFVjhXt.ZODYlK/bsYzAxn6EPnmh0tOw0B5ArjG',
    1,
    1,
    now(),
    1,
    now(),
    0
);

insert into tb_sys_menu (
    id,
    parent_id,
    menu_name,
    path,
    icon,
    menu_type,
    menu_code,
    order_num,
    visible,
    create_by,
    create_time,
    update_by,
    update_time,
    deleted
) values
    (1, 0, '系统管理', '/system', 'Setting', 'CATALOG', null, 1, 1, 1, now(), 1, now(), 0),
    (2, 1, '菜单管理', '/system/menu', 'Menu', 'MENU', 'system:menu:query', 10, 1, 1, now(), 1, now(), 0),
    (3, 0, '用户管理', '/user', 'User', 'MENU', 'system:user:query', 2, 1, 1, now(), 1, now(), 0),
    (4, 1, '角色管理', '/system/role', 'Avatar', 'MENU', 'system:role:query', 30, 1, 1, now(), 1, now(), 0),
    (5, 0, '组织管理', '/organization', 'OfficeBuilding', 'MENU', 'system:org:query', 3, 1, 1, now(), 1, now(), 0),
    (6, 1, '日志审计', '/system/operation-log', 'Document', 'MENU', 'system:operationLog:query', 50, 1, 1, now(), 1, now(), 0),
    (15, 1, '人脸认证日志', '/system/face-auth-log', 'Document', 'MENU', 'system:operationLog:query', 55, 1, 1, now(), 1, now(), 0),
    (7, 1, '文件上传', '/system/file/upload', null, 'BUTTON', 'system:file:upload', 60, 1, 1, now(), 1, now(), 0),
    (8, 1, '文件下载', '/system/file/download', null, 'BUTTON', 'system:file:download', 70, 1, 1, now(), 1, now(), 0),
    (9, 1, '文件删除', '/system/file/delete', null, 'BUTTON', 'system:file:delete', 80, 1, 1, now(), 1, now(), 0),
    (10, 3, '用户保存', '/user/save', null, 'BUTTON', 'system:user:update', 90, 1, 1, now(), 1, now(), 0),
    (11, 3, '重置密码', '/user/reset-password', null, 'BUTTON', 'system:user:resetPassword', 100, 1, 1, now(), 1, now(), 0),
    (12, 4, '角色保存', '/system/role/save', null, 'BUTTON', 'system:role:update', 110, 1, 1, now(), 1, now(), 0),
    (13, 5, '组织保存', '/organization/save', null, 'BUTTON', 'system:org:update', 120, 1, 1, now(), 1, now(), 0),
    (14, 2, '菜单保存', '/system/menu/save', null, 'BUTTON', 'system:menu:update', 130, 1, 1, now(), 1, now(), 0),
    (100, 0, '首页', '/index', 'HomeFilled', 'CATALOG', null, 1, 1, 1, now(), 1, now(), 0),
    (101, 100, '基础信息', '/index/baseInfo', 'DataAnalysis', 'MENU', 'system:index:baseInfo', 10, 1, 1, now(), 1, now(), 0),
    (102, 101, '用户总数', '/index/baseInfo/userNum', null, 'BUTTON', 'system:index:userNum', 11, 1, 1, now(), 1, now(), 0),
    (103, 101, '活跃用户', '/index/baseInfo/userActive', null, 'BUTTON', 'system:index:userActive', 12, 1, 1, now(), 1, now(), 0),
    (104, 101, '应用排行', '/index/baseInfo/appRank', null, 'BUTTON', 'system:index:appRank', 13, 1, 1, now(), 1, now(), 0),
    (105, 101, '设备统计', '/index/baseInfo/userDevice', null, 'BUTTON', 'system:index:userDevice', 14, 1, 1, now(), 1, now(), 0),
    (106, 3, '用户新增', '/user/create', null, 'BUTTON', 'system:user:add', 21, 1, 1, now(), 1, now(), 0),
    (107, 3, '用户删除', '/user/delete', null, 'BUTTON', 'system:user:delete', 22, 1, 1, now(), 1, now(), 0),
    (108, 5, '组织新增', '/organization/create', null, 'BUTTON', 'system:org:add', 41, 1, 1, now(), 1, now(), 0),
    (109, 5, '组织删除', '/organization/delete', null, 'BUTTON', 'system:org:delete', 42, 1, 1, now(), 1, now(), 0),
    (120, 0, '应用管理', '/application', 'Grid', 'MENU', 'system:app:query', 4, 1, 1, now(), 1, now(), 0),
    (121, 120, '应用保存', '/application/save', null, 'BUTTON', 'system:app:update', 10, 1, 1, now(), 1, now(), 0),
    (122, 120, '应用删除', '/application/delete', null, 'BUTTON', 'system:app:delete', 20, 1, 1, now(), 1, now(), 0),
    (118, 2, '菜单删除', '/system/menu/delete', null, 'BUTTON', 'system:menu:delete', 121, 1, 1, now(), 1, now(), 0),
    (119, 4, '角色删除', '/system/role/delete', null, 'BUTTON', 'system:role:delete', 111, 1, 1, now(), 1, now(), 0);

insert into tb_sys_role_user (
    id,
    user_id,
    role_id,
    create_by,
    create_time,
    update_by,
    update_time,
    deleted
) values
    (1, 1, 1, 1, now(), 1, now(), 0),
    (2, 1, 2, 1, now(), 1, now(), 0);

insert into tb_org_user (
    id,
    org_id,
    user_id,
    create_by,
    create_time,
    update_by,
    update_time,
    deleted
) values
    (1, 1, 1, 1, now(), 1, now(), 0);

insert into tb_sys_role_menu (
    id,
    role_id,
    menu_id,
    create_by,
    create_time,
    update_by,
    update_time,
    deleted
) values
    (1, 1, 1, 1, now(), 1, now(), 0),
    (2, 1, 2, 1, now(), 1, now(), 0),
    (3, 1, 3, 1, now(), 1, now(), 0),
    (4, 1, 4, 1, now(), 1, now(), 0),
    (5, 1, 5, 1, now(), 1, now(), 0),
    (6, 1, 6, 1, now(), 1, now(), 0),
    (49, 1, 15, 1, now(), 1, now(), 0),
    (7, 1, 7, 1, now(), 1, now(), 0),
    (8, 1, 8, 1, now(), 1, now(), 0),
    (9, 1, 9, 1, now(), 1, now(), 0),
    (10, 1, 10, 1, now(), 1, now(), 0),
    (11, 1, 11, 1, now(), 1, now(), 0),
    (12, 1, 12, 1, now(), 1, now(), 0),
    (13, 1, 13, 1, now(), 1, now(), 0),
    (14, 1, 14, 1, now(), 1, now(), 0),
    (15, 1, 118, 1, now(), 1, now(), 0),
    (16, 1, 119, 1, now(), 1, now(), 0),
    (17, 1, 120, 1, now(), 1, now(), 0),
    (18, 1, 121, 1, now(), 1, now(), 0),
    (19, 1, 122, 1, now(), 1, now(), 0),
    (20, 2, 1, 1, now(), 1, now(), 0),
    (21, 2, 2, 1, now(), 1, now(), 0),
    (22, 2, 3, 1, now(), 1, now(), 0),
    (23, 2, 4, 1, now(), 1, now(), 0),
    (24, 2, 5, 1, now(), 1, now(), 0),
    (25, 2, 6, 1, now(), 1, now(), 0),
    (50, 2, 15, 1, now(), 1, now(), 0),
    (26, 2, 7, 1, now(), 1, now(), 0),
    (27, 2, 8, 1, now(), 1, now(), 0),
    (28, 2, 9, 1, now(), 1, now(), 0),
    (29, 2, 10, 1, now(), 1, now(), 0),
    (30, 2, 11, 1, now(), 1, now(), 0),
    (31, 2, 12, 1, now(), 1, now(), 0),
    (32, 2, 13, 1, now(), 1, now(), 0),
    (33, 2, 14, 1, now(), 1, now(), 0),
    (34, 2, 100, 1, now(), 1, now(), 0),
    (35, 2, 101, 1, now(), 1, now(), 0),
    (36, 2, 102, 1, now(), 1, now(), 0),
    (37, 2, 103, 1, now(), 1, now(), 0),
    (38, 2, 104, 1, now(), 1, now(), 0),
    (39, 2, 105, 1, now(), 1, now(), 0),
    (40, 2, 106, 1, now(), 1, now(), 0),
    (41, 2, 107, 1, now(), 1, now(), 0),
    (42, 2, 108, 1, now(), 1, now(), 0),
    (43, 2, 109, 1, now(), 1, now(), 0),
    (44, 2, 118, 1, now(), 1, now(), 0),
    (45, 2, 119, 1, now(), 1, now(), 0),
    (46, 2, 120, 1, now(), 1, now(), 0),
    (47, 2, 121, 1, now(), 1, now(), 0),
    (48, 2, 122, 1, now(), 1, now(), 0);
