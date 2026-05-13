alter table tb_face_auth_log
    add column auth_api_type tinyint not null default 1 comment '认证接口类型，1 表示 1:1，2 表示 1:N' after id;

create index idx_tb_face_auth_log_auth_api_type_deleted on tb_face_auth_log (auth_api_type, deleted);

