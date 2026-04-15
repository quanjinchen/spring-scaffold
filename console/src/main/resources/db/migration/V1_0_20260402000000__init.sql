create table if not exists sys_org (
    id bigint primary key,
    parent_id bigint not null default 0,
    org_code varchar(64) not null,
    name varchar(128) not null,
    leader_name varchar(64) null,
    sort_order int not null default 0,
    status tinyint not null default 1,
    create_by bigint null,
    create_time datetime null,
    update_by bigint null,
    update_time datetime null,
    deleted tinyint not null default 0
);

create table if not exists sys_user (
    id bigint primary key,
    username varchar(64) not null,
    nickname varchar(64) null,
    email varchar(128) null,
    phone varchar(32) null,
    org_id bigint null,
    password varchar(255) null,
    status tinyint not null default 1,
    create_by bigint null,
    create_time datetime null,
    update_by bigint null,
    update_time datetime null,
    deleted tinyint not null default 0
);

create table if not exists sys_role (
    id bigint primary key,
    code varchar(64) not null,
    name varchar(128) not null,
    data_scope varchar(32) not null default 'ALL',
    status tinyint not null default 1,
    remark varchar(255) null,
    create_by bigint null,
    create_time datetime null,
    update_by bigint null,
    update_time datetime null,
    deleted tinyint not null default 0
);

create table if not exists sys_menu (
    id bigint primary key,
    parent_id bigint not null default 0,
    name varchar(128) not null,
    path varchar(255) null,
    menu_type varchar(32) not null,
    permission_code varchar(128) null,
    sort_order int not null default 0,
    visible tinyint not null default 1,
    create_by bigint null,
    create_time datetime null,
    update_by bigint null,
    update_time datetime null,
    deleted tinyint not null default 0
);

create table if not exists sys_operation_log (
    id bigint primary key,
    module_name varchar(64) not null,
    action_name varchar(64) not null,
    operator_name varchar(64) null,
    request_path varchar(255) null,
    success_flag tinyint not null default 1,
    request_time datetime not null,
    create_by bigint null,
    create_time datetime null,
    update_by bigint null,
    update_time datetime null,
    deleted tinyint not null default 0
);

create table if not exists sys_user_role (
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id)
);

create table if not exists sys_org_user (
    org_id bigint not null,
    user_id bigint not null,
    primary key (org_id, user_id)
);

create table if not exists sys_role_menu (
    role_id bigint not null,
    menu_id bigint not null,
    primary key (role_id, menu_id)
);

create table if not exists tb_file (
    id bigint primary key,
    file_id varchar(100) null,
    file_name varchar(100) null,
    object_name varchar(200) null,
    content_type varchar(100) null,
    file_suffix varchar(100) null,
    file_size bigint null,
    create_by bigint null,
    create_time datetime null,
    update_by bigint null,
    update_time datetime null,
    deleted tinyint not null default 0
);

create index idx_tb_file_file_id on tb_file (file_id);

insert into sys_org (id, parent_id, org_code, name, leader_name, sort_order, status, create_time, deleted)
values (1, 0, 'ROOT', '总部', '系统负责人', 1, 1, now(), 0)
on duplicate key update name = values(name);

insert into sys_role (id, code, name, data_scope, status, remark, create_time, deleted)
values (1, 'ADMIN', '系统管理员', 'ALL', 1, '系统初始化管理员角色', now(), 0)
on duplicate key update name = values(name), remark = values(remark);

insert into sys_user (id, username, nickname, email, phone, org_id, password, status, create_time, deleted)
values (1, 'admin', '系统管理员', 'admin@example.com', 'kGyYSFD/ZqsdmzSZp8sH9A==', 1, '$2a$10$7EqJtq98hPqEX7fNZaFWoOHiB7C9HfM9vDOMkMt2rt7NmBGG99nmW', 1, now(), 0)
on duplicate key update nickname = values(nickname);

insert into sys_menu (id, parent_id, name, path, menu_type, permission_code, sort_order, visible, create_time, deleted)
values
    (1, 0, '系统管理', '/system', 'CATALOG', null, 1, 1, now(), 0),
    (2, 1, '菜单管理', '/system/menu', 'MENU', 'system:menu:query', 10, 1, now(), 0),
    (3, 1, '用户管理', '/system/user', 'MENU', 'system:user:query', 20, 1, now(), 0),
    (4, 1, '角色管理', '/system/role', 'MENU', 'system:role:query', 30, 1, now(), 0),
    (5, 1, '组织管理', '/system/org', 'MENU', 'system:org:query', 40, 1, now(), 0),
    (6, 1, '日志审计', '/system/operation-log', 'MENU', 'system:operationLog:query', 50, 1, now(), 0),
    (7, 1, '文件上传', '/system/file/upload', 'BUTTON', 'system:file:upload', 60, 1, now(), 0),
    (8, 1, '文件下载', '/system/file/download', 'BUTTON', 'system:file:download', 70, 1, now(), 0),
    (9, 1, '文件删除', '/system/file/delete', 'BUTTON', 'system:file:delete', 80, 1, now(), 0),
    (10, 3, '用户保存', '/system/user/save', 'BUTTON', 'system:user:update', 90, 1, now(), 0),
    (11, 3, '重置密码', '/system/user/reset-password', 'BUTTON', 'system:user:resetPassword', 100, 1, now(), 0),
    (12, 4, '角色保存', '/system/role/save', 'BUTTON', 'system:role:update', 110, 1, now(), 0),
    (119, 4, '角色删除', '/system/role/delete', 'BUTTON', 'system:role:delete', 111, 1, now(), 0),
    (13, 5, '组织保存', '/system/org/save', 'BUTTON', 'system:org:update', 120, 1, now(), 0),
    (14, 2, '菜单保存', '/system/menu/save', 'BUTTON', 'system:menu:update', 130, 1, now(), 0),
    (118, 2, '菜单删除', '/system/menu/delete', 'BUTTON', 'system:menu:delete', 131, 1, now(), 0)
on duplicate key update name = values(name);

insert into sys_user_role (user_id, role_id)
values (1, 1)
on duplicate key update role_id = values(role_id);

insert into sys_org_user (org_id, user_id)
values (1, 1)
on duplicate key update user_id = values(user_id);

insert into sys_role_menu (role_id, menu_id)
values
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (1, 7),
    (1, 8),
    (1, 9),
    (1, 10),
    (1, 11),
    (1, 12),
    (1, 13),
    (1, 14),
    (1, 118),
    (1, 119)
on duplicate key update menu_id = values(menu_id);
