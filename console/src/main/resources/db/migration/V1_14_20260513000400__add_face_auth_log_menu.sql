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
values (
    15,
    1,
    '人脸认证日志',
    '/system/face-auth-log',
    'Document',
    'MENU',
    'system:operationLog:query',
    55,
    1,
    1,
    now(),
    1,
    now(),
    0
)
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

insert into tb_sys_role_menu (role_id, menu_id)
values
    (1, 15),
    (2, 15)
on duplicate key update
    menu_id = values(menu_id);
