create database Note ； USE Note;
drop table user;
create table if not exists user(
  user_mobile varchar (11) not null primary key,
  password varchar (255) not null,
  nick_name varchar (20) not null,
  lock_flg varchar (1),
  create_time char (14) not null,
  login_time char (14),
  update_time char (14)
);
drop table userinfo;
create table if not exists userinfo(
  user_mobile char (11) not null primary key,
  profile_photo varchar (255),
  signature varchar (255) not null,
  message int (4) not null,
  note bigint(11) not null,
  category bigint(11) not null,
  follow bigint(11) not null,
  fan bigint(11) not null,
  starred bigint(11) not null,
  star bigint(12) not null,
  user_level int (2) not null,
  exp int (5) not null,
  update_time char (14)
);
drop table if exists category;
create table if not exists category(
  id int primary key auto_increment comment 'id',
  user_mobile varchar (11) not null comment '手机号码',
  category_name varchar (50) not null comment '类目名称',
  star char (11) comment '标星',
  create_date char (14) not null comment '创建时间',
  update_date char (14) comment '更新时间',
  status int (1) not null comment '是否公开'
);
drop table if exists note;
CREATE TABLE if not exists note (
  id int NOT NULL AUTO_INCREMENT COMMENT 'id',
  user_mobile varchar(11) NOT NULL COMMENT '电话号码',
  category_id int NOT NULL COMMENT '科目ID',
  star char(10) DEFAULT NULL COMMENT '标星',
  create_date char(14) NOT NULL COMMENT '创建日期',
  update_date char(14) DEFAULT NULL COMMENT '更新日期',
  status int NOT NULL COMMENT '状态',
  title varchar(50) NOT NULL COMMENT '标题',
  content varchar(5000) NOT NULL COMMENT '内容',
  PRIMARY KEY (id)
) drop table if exists levelmaster;
CREATE TABLE if not exists levelmaster (
  id int NOT NULL AUTO_INCREMENT COMMENT 'id',
  level int(2) NOT NULL COMMENT '等级',
  exp int(4) NOT NULL COMMENT '升级所需经验',
  PRIMARY KEY (id)
)
insert into levelmaster(level, exp) value(0, 10);
insert into levelmaster(level, exp) value(1, 20);
insert into levelmaster(level, exp) value(2, 30);
insert into levelmaster(level, exp) value(3, 40);
insert into levelmaster(level, exp) value(4, 50);
insert into levelmaster(level, exp) value(5, 60);
insert into levelmaster(level, exp) value(6, 70);
insert into levelmaster(level, exp) value(7, 80);
insert into levelmaster(level, exp) value(8, 90);
insert into levelmaster(level, exp) value(9, 100);
insert into levelmaster(level, exp) value(10, 120);
insert into levelmaster(level, exp) value(11, 140);
insert into levelmaster(level, exp) value(12, 160);
insert into levelmaster(level, exp) value(13, 180);
insert into levelmaster(level, exp) value(14, 200);
insert into levelmaster(level, exp) value(15, 220);
insert into levelmaster(level, exp) value(16, 240);
insert into levelmaster(level, exp) value(17, 260);
insert into levelmaster(level, exp) value(18, 280);
insert into levelmaster(level, exp) value(19, 300);
insert into levelmaster(level, exp) value(20, 320);
insert into levelmaster(level, exp) value(21, 350);
insert into levelmaster(level, exp) value(22, 380);
insert into levelmaster(level, exp) value(23, 410);
insert into levelmaster(level, exp) value(24, 440);
insert into levelmaster(level, exp) value(25, 470);
insert into levelmaster(level, exp) value(26, 500);
insert into levelmaster(level, exp) value(27, 530);
insert into levelmaster(level, exp) value(28, 560);
insert into levelmaster(level, exp) value(29, 590);
insert into levelmaster(level, exp) value(30, 620);
insert into levelmaster(level, exp) value(31, 650);
insert into levelmaster(level, exp) value(32, 680);
insert into levelmaster(level, exp) value(33, 710);
insert into levelmaster(level, exp) value(34, 740);
insert into levelmaster(level, exp) value(35, 770);
insert into levelmaster(level, exp) value(36, 800);
insert into levelmaster(level, exp) value(37, 830);
insert into levelmaster(level, exp) value(38, 860);
insert into levelmaster(level, exp) value(39, 890);
insert into levelmaster(level, exp) value(40, 920);
insert into levelmaster(level, exp) value(41, 960);
insert into levelmaster(level, exp) value(42, 1000);
insert into levelmaster(level, exp) value(43, 1040);
insert into levelmaster(level, exp) value(44, 1080);
insert into levelmaster(level, exp) value(45, 1120);
insert into levelmaster(level, exp) value(46, 1160);
insert into levelmaster(level, exp) value(47, 1200);
insert into levelmaster(level, exp) value(48, 1240);
insert into levelmaster(level, exp) value(49, 1280);
insert into levelmaster(level, exp) value(50, 1320);
insert into levelmaster(level, exp) value(51, 1370);
insert into levelmaster(level, exp) value(52, 1420);
insert into levelmaster(level, exp) value(53, 1470);
insert into levelmaster(level, exp) value(54, 1520);
insert into levelmaster(level, exp) value(55, 1570);
insert into levelmaster(level, exp) value(56, 1620);
insert into levelmaster(level, exp) value(57, 1670);
insert into levelmaster(level, exp) value(58, 1720);
insert into levelmaster(level, exp) value(59, 1770);
insert into levelmaster(level, exp) value(60, 1820);
insert into levelmaster(level, exp) value(61, 1880);
insert into levelmaster(level, exp) value(62, 1940);
insert into levelmaster(level, exp) value(63, 2000);
insert into levelmaster(level, exp) value(64, 2060);
insert into levelmaster(level, exp) value(65, 2120);
insert into levelmaster(level, exp) value(66, 2180);
insert into levelmaster(level, exp) value(67, 2240);
insert into levelmaster(level, exp) value(68, 2300);
insert into levelmaster(level, exp) value(69, 2360);
insert into levelmaster(level, exp) value(70, 2420);
insert into levelmaster(level, exp) value(71, 2490);
insert into levelmaster(level, exp) value(72, 2560);
insert into levelmaster(level, exp) value(73, 2630);
insert into levelmaster(level, exp) value(74, 2700);
insert into levelmaster(level, exp) value(75, 2770);
insert into levelmaster(level, exp) value(76, 2840);
insert into levelmaster(level, exp) value(77, 2910);
insert into levelmaster(level, exp) value(78, 2980);
insert into levelmaster(level, exp) value(79, 3050);
insert into levelmaster(level, exp) value(80, 3120);
insert into levelmaster(level, exp) value(81, 3200);
insert into levelmaster(level, exp) value(82, 3280);
insert into levelmaster(level, exp) value(83, 3360);
insert into levelmaster(level, exp) value(84, 3440);
insert into levelmaster(level, exp) value(85, 3520);
insert into levelmaster(level, exp) value(86, 3600);
insert into levelmaster(level, exp) value(87, 3680);
insert into levelmaster(level, exp) value(88, 3760);
insert into levelmaster(level, exp) value(89, 3840);
insert into levelmaster(level, exp) value(90, 3920);
insert into levelmaster(level, exp) value(91, 4010);
insert into levelmaster(level, exp) value(92, 4100);
insert into levelmaster(level, exp) value(93, 4190);
insert into levelmaster(level, exp) value(94, 4280);
insert into levelmaster(level, exp) value(95, 4370);
insert into levelmaster(level, exp) value(96, 4460);
insert into levelmaster(level, exp) value(97, 4550);
insert into levelmaster(level, exp) value(98, 4640);
insert into levelmaster(level, exp) value(99, 4730);
drop table if exists messagemaster;
CREATE TABLE if not exists messagemaster(
  type int NOT NULL COMMENT '消息类型',
  title varchar (50) NOT NULL COMMENT '标题',
  content varchar (5000) NOT NULL COMMENT '内容',
  PRIMARY KEY (type)
)
insert into messagemaster(type, title, content) value(0, 'Weclome', '账号建好了赶紧用起来吧。');
insert into messagemaster(type, title, content) value(1, '科目作成の通知', '还没想好写啥。');
insert into messagemaster(type, title, content) value(2, 'ノート作成作成の通知', '还没想好写啥。');
insert into messagemaster(type, title, content) value(3, '有人给你小星星啦', '还没想好写啥。');
insert into messagemaster(type, title, content) value(4, '有人关注了你', '还没想好写啥。');
insert into messagemaster(type, title, content) value(5, '有人访问了你的主页', '还没想好写啥。');
insert into messagemaster(type, title, content) value(6, 'ノート改修の通知', '还没想好写啥。');
insert into messagemaster(type, title, content) value(7, 'ノート削除の通知', '还没想好写啥。');
insert into messagemaster(type, title, content) value(8, '科目改修の通知', '还没想好写啥。');
