alter table tb_user
    add column full_name varchar(64) null comment '姓名' after username;

create index idx_tb_user_full_name_deleted on tb_user (full_name, deleted);

update tb_user
set full_name = '超级管理员'
where full_name is null
  and username = 'admin';
