-- auto-generated definition
create table mint
(
  id       int auto_increment
    primary key,
  account  varchar(64) null
  comment '账号',
  password varchar(64) null
  comment '密码',
  nick_name varchar(64) null
  comment '昵称'
);

-- auto-generated definition
create table secret
(
  project_id varchar(64) not null
  comment '项目ID'
    primary key,
  public_key text        null
  comment '公钥'
);




