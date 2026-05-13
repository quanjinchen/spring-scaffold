create table if not exists tb_face_auth_log (
    id bigint not null auto_increment,
    auth_api_type tinyint not null default 1 comment '认证接口类型，1 表示 1:1，2 表示 1:N',
    ip varchar(64) null comment '请求 IP',
    app_id bigint null comment '应用 ID',
    app_name varchar(100) null comment '应用名称',
    auth_full_name varchar(100) null comment '认证人员姓名',
    auth_user_id bigint null comment '认证人员 ID',
    status tinyint not null default 0 comment '状态，0 失败 1 成功',
    errmsg varchar(500) null comment '失败原因',
    create_by bigint null,
    create_time datetime not null default current_timestamp,
    update_by bigint null,
    update_time datetime not null default current_timestamp on update current_timestamp,
    deleted tinyint not null default 0,
    primary key (id)
) engine=InnoDB default charset=utf8mb4 collate=utf8mb4_unicode_ci comment='人脸认证日志表';

create index idx_tb_face_auth_log_auth_api_type_deleted on tb_face_auth_log (auth_api_type, deleted);
create index idx_tb_face_auth_log_app_id_deleted on tb_face_auth_log (app_id, deleted);
create index idx_tb_face_auth_log_auth_user_id_deleted on tb_face_auth_log (auth_user_id, deleted);
create index idx_tb_face_auth_log_status_deleted on tb_face_auth_log (status, deleted);
create index idx_tb_face_auth_log_create_time_deleted on tb_face_auth_log (create_time, deleted);
