create database Note ； 
USE Note;

drop table user; 
create table if not exists user( 
    user_mobile varchar (11) not null primary key
    , password varchar (255) not null
    , nick_name varchar (20) not null
    , lock_flg varchar (1)
    , create_time char (14) not null
    , login_time char (14)
    , update_time char (14)
); 

drop table userinfo; 
create table if not exists userinfo( 
    user_mobile varchar (11) not null primary key
    , profile_photo varchar (255)
    , signature varchar (255) not null
    , follow varchar (11) not null
    , fan varchar (11) not null
    , star varchar (12) not null
    , user_level char (2) not null
    , exp char (5) not null
    , update_time char (14)
); 

drop table if exists category; 
create table if not exists category( 
    id int primary key auto_increment comment 'id'
    , user_mobile varchar (11) not null comment '手机号码'
    , category_name varchar (50) not null comment '类目名称'
    , star char (11) comment '标星'
    , create_date char (14) not null comment '创建时间'
    , update_date char (14) comment '更新时间'
    , status int (1) not null comment '是否公开'
);
