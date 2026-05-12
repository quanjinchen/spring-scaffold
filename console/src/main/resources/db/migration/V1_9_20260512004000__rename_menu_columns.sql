alter table tb_sys_menu
    change column name menu_name varchar(128) not null comment '菜单名称',
    add column icon varchar(128) null comment '菜单图标' after path,
    change column permission_code menu_code varchar(128) null comment '权限编码',
    change column sort_order order_num int not null default 0 comment '排序值';

drop index idx_tb_sys_menu_permission_code_deleted on tb_sys_menu;

create index idx_tb_sys_menu_menu_code_deleted on tb_sys_menu (menu_code, deleted);
