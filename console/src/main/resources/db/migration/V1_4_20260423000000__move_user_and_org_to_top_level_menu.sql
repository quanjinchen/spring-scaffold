update tb_sys_menu
set
    parent_id = 0,
    path = '/user',
    sort_order = 2
where id = 3;

update tb_sys_menu
set
    path = '/user/save'
where id = 10;

update tb_sys_menu
set
    path = '/user/reset-password'
where id = 11;

update tb_sys_menu
set
    parent_id = 3,
    path = '/user/create'
where id = 106;

update tb_sys_menu
set
    path = '/user/delete'
where id = 107;

update tb_sys_menu
set
    parent_id = 0,
    path = '/organization',
    sort_order = 3
where id = 5;

update tb_sys_menu
set
    path = '/organization/save'
where id = 13;

update tb_sys_menu
set
    parent_id = 5,
    path = '/organization/create'
where id = 108;

update tb_sys_menu
set
    path = '/organization/delete'
where id = 109;

