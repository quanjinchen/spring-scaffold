alter table tb_user
    add column id_card varchar(128) null comment '身份证号' after phone;

create index idx_tb_user_id_card_deleted on tb_user (id_card, deleted);
