create table if not exists tb_app (
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
)
values
    (120, 0, '应用管理', '/application', 'Grid', 'MENU', 'system:app:query', 4, 1, 1, now(), 1, now(), 0),
    (121, 120, '应用保存', '/application/save', null, 'BUTTON', 'system:app:update', 10, 1, 1, now(), 1, now(), 0),
    (122, 120, '应用删除', '/application/delete', null, 'BUTTON', 'system:app:delete', 20, 1, 1, now(), 1, now(), 0)
on duplicate key update
    parent_id = values(parent_id),
    menu_name = values(menu_name),
    path = values(path),
    icon = values(icon),
    menu_type = values(menu_type),
    menu_code = values(menu_code),
    order_num = values(order_num),
    visible = values(visible),
    update_by = values(update_by),
    update_time = values(update_time),
    deleted = values(deleted);

insert into tb_sys_role_menu (role_id, menu_id, create_by, create_time, update_by, update_time, deleted)
select 1, 120, 1, now(), 1, now(), 0 from dual
where not exists (
    select 1 from tb_sys_role_menu where role_id = 1 and menu_id = 120 and deleted = 0
);

insert into tb_sys_role_menu (role_id, menu_id, create_by, create_time, update_by, update_time, deleted)
select 1, 121, 1, now(), 1, now(), 0 from dual
where not exists (
    select 1 from tb_sys_role_menu where role_id = 1 and menu_id = 121 and deleted = 0
);

insert into tb_sys_role_menu (role_id, menu_id, create_by, create_time, update_by, update_time, deleted)
select 1, 122, 1, now(), 1, now(), 0 from dual
where not exists (
    select 1 from tb_sys_role_menu where role_id = 1 and menu_id = 122 and deleted = 0
);

insert into tb_sys_role_menu (role_id, menu_id, create_by, create_time, update_by, update_time, deleted)
select 2, 120, 1, now(), 1, now(), 0 from dual
where not exists (
    select 1 from tb_sys_role_menu where role_id = 2 and menu_id = 120 and deleted = 0
);

insert into tb_sys_role_menu (role_id, menu_id, create_by, create_time, update_by, update_time, deleted)
select 2, 121, 1, now(), 1, now(), 0 from dual
where not exists (
    select 1 from tb_sys_role_menu where role_id = 2 and menu_id = 121 and deleted = 0
);

insert into tb_sys_role_menu (role_id, menu_id, create_by, create_time, update_by, update_time, deleted)
select 2, 122, 1, now(), 1, now(), 0 from dual
where not exists (
    select 1 from tb_sys_role_menu where role_id = 2 and menu_id = 122 and deleted = 0
);
