-- Super admin account, admin role, frontend route menus, and role-menu bindings.
-- Password plaintext for user `admin`: Admin@123
-- BCrypt hash generated locally for this project.

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
)
values (
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
)
on duplicate key update
    code = values(code),
    name = values(name),
    status = values(status),
    remark = values(remark),
    update_by = values(update_by),
    update_time = values(update_time),
    deleted = values(deleted);

insert into tb_user (
    id,
    username,
    full_name,
    email,
    phone,
    org_id,
    password,
    status,
    create_by,
    create_time,
    update_by,
    update_time,
    deleted
)
values (
    1,
    'admin',
    '超级管理员',
    'admin@local.test',
    null,
    1,
    '$2a$10$5jEE.2xAuijvcRpFVjhXt.ZODYlK/bsYzAxn6EPnmh0tOw0B5ArjG',
    1,
    1,
    now(),
    1,
    now(),
    0
)
on duplicate key update
    username = values(username),
    full_name = values(full_name),
    email = values(email),
    org_id = values(org_id),
    password = values(password),
    status = values(status),
    update_by = values(update_by),
    update_time = values(update_time),
    deleted = values(deleted);

insert into tb_sys_role_user (user_id, role_id)
values (1, 2)
on duplicate key update
    role_id = values(role_id);

insert into tb_org_user (org_id, user_id)
values (1, 1)
on duplicate key update
    user_id = values(user_id);

insert into tb_sys_menu (
    id,
    parent_id,
    name,
    path,
    menu_type,
    permission_code,
    sort_order,
    visible,
    create_by,
    create_time,
    update_by,
    update_time,
    deleted
)
values
    (100, 0, '首页', '/index', 'CATALOG', null, 1, 1, 1, now(), 1, now(), 0),
    (101, 100, '基础信息', '/index/baseInfo', 'MENU', 'system:index:baseInfo', 10, 1, 1, now(), 1, now(), 0),
    (102, 101, '用户总数', '/index/baseInfo/userNum', 'BUTTON', 'system:index:userNum', 11, 1, 1, now(), 1, now(), 0),
    (103, 101, '活跃用户', '/index/baseInfo/userActive', 'BUTTON', 'system:index:userActive', 12, 1, 1, now(), 1, now(), 0),
    (104, 101, '应用排行', '/index/baseInfo/appRank', 'BUTTON', 'system:index:appRank', 13, 1, 1, now(), 1, now(), 0),
    (105, 101, '设备统计', '/index/baseInfo/userDevice', 'BUTTON', 'system:index:userDevice', 14, 1, 1, now(), 1, now(), 0),
    (106, 3, '用户新增', '/user/create', 'BUTTON', 'system:user:add', 21, 1, 1, now(), 1, now(), 0),
    (107, 3, '用户删除', '/user/delete', 'BUTTON', 'system:user:delete', 22, 1, 1, now(), 1, now(), 0),
    (108, 5, '组织新增', '/organization/create', 'BUTTON', 'system:org:add', 41, 1, 1, now(), 1, now(), 0),
    (109, 5, '组织删除', '/organization/delete', 'BUTTON', 'system:org:delete', 42, 1, 1, now(), 1, now(), 0),
    (118, 2, '菜单删除', '/system/menu/delete', 'BUTTON', 'system:menu:delete', 121, 1, 1, now(), 1, now(), 0),
    (119, 4, '角色删除', '/system/role/delete', 'BUTTON', 'system:role:delete', 101, 1, 1, now(), 1, now(), 0)
on duplicate key update
    parent_id = values(parent_id),
    name = values(name),
    path = values(path),
    menu_type = values(menu_type),
    permission_code = values(permission_code),
    sort_order = values(sort_order),
    visible = values(visible),
    update_by = values(update_by),
    update_time = values(update_time),
    deleted = values(deleted);

insert into tb_sys_role_menu (role_id, menu_id)
values
    (2, 1),
    (2, 2),
    (2, 3),
    (2, 4),
    (2, 5),
    (2, 6),
    (2, 7),
    (2, 8),
    (2, 9),
    (2, 10),
    (2, 11),
    (2, 12),
    (2, 13),
    (2, 14),
    (2, 100),
    (2, 101),
    (2, 102),
    (2, 103),
    (2, 104),
    (2, 105),
    (2, 106),
    (2, 107),
    (2, 108),
    (2, 109),
    (2, 118),
    (2, 119)
on duplicate key update
    menu_id = values(menu_id);
