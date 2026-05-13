alter table tb_user
    add column face_feature text null comment '人脸特征值' after face_file_id;
