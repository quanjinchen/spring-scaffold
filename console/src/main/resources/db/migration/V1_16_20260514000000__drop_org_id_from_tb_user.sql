alter table tb_user
    drop column org_id;

create index idx_tb_user_status_deleted on tb_user (status, deleted);
