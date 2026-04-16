alter table tb_sys_role_user
    add column id bigint null first comment '主键 ID',
    add column create_by bigint null comment '创建人 ID',
    add column create_time datetime not null default current_timestamp comment '创建时间，默认当前时间',
    add column update_by bigint null comment '更新人 ID',
    add column update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间，默认当前时间并在更新时自动刷新',
    add column deleted tinyint not null default 0 comment '逻辑删除标记，0 正常，1 删除';

set @sys_role_user_row_num = 0;
update tb_sys_role_user
set id = (@sys_role_user_row_num := @sys_role_user_row_num + 1)
where id is null;

alter table tb_sys_role_user
    drop primary key,
    modify column id bigint not null auto_increment comment '主键 ID',
    add primary key (id);

create index idx_tb_sys_role_user_user_id_deleted on tb_sys_role_user (user_id, deleted);
create index idx_tb_sys_role_user_role_id_deleted on tb_sys_role_user (role_id, deleted);

alter table tb_org_user
    add column id bigint null first comment '主键 ID',
    add column create_by bigint null comment '创建人 ID',
    add column create_time datetime not null default current_timestamp comment '创建时间，默认当前时间',
    add column update_by bigint null comment '更新人 ID',
    add column update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间，默认当前时间并在更新时自动刷新',
    add column deleted tinyint not null default 0 comment '逻辑删除标记，0 正常，1 删除';

set @tb_org_user_row_num = 0;
update tb_org_user
set id = (@tb_org_user_row_num := @tb_org_user_row_num + 1)
where id is null;

alter table tb_org_user
    drop primary key,
    modify column id bigint not null auto_increment comment '主键 ID',
    add primary key (id);

create index idx_tb_org_user_org_id_deleted on tb_org_user (org_id, deleted);
create index idx_tb_org_user_user_id_deleted on tb_org_user (user_id, deleted);

alter table tb_sys_role_menu
    add column id bigint null first comment '主键 ID',
    add column create_by bigint null comment '创建人 ID',
    add column create_time datetime not null default current_timestamp comment '创建时间，默认当前时间',
    add column update_by bigint null comment '更新人 ID',
    add column update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间，默认当前时间并在更新时自动刷新',
    add column deleted tinyint not null default 0 comment '逻辑删除标记，0 正常，1 删除';

set @tb_sys_role_menu_row_num = 0;
update tb_sys_role_menu
set id = (@tb_sys_role_menu_row_num := @tb_sys_role_menu_row_num + 1)
where id is null;

alter table tb_sys_role_menu
    drop primary key,
    modify column id bigint not null auto_increment comment '主键 ID',
    add primary key (id);

create index idx_tb_sys_role_menu_role_id_deleted on tb_sys_role_menu (role_id, deleted);
create index idx_tb_sys_role_menu_menu_id_deleted on tb_sys_role_menu (menu_id, deleted);
