-- auto-generated definition
create table secret
(
  project_id varchar(64) not null
  comment '项目ID'
    primary key,
  public_key text        null
  comment '公钥'
);




