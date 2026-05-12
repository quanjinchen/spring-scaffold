alter table tb_user
    add column face_file_id varchar(100) null comment '人脸文件 ID' after phone;

create index idx_tb_user_face_file_id_deleted on tb_user (face_file_id, deleted);
