alter table tb_file_record
    add column file_category varchar(32) not null default 'COMMON' comment '文件分类' after file_name;

create index idx_tb_file_record_file_category_deleted on tb_file_record (file_category, deleted);
