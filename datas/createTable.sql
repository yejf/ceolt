-- create table
drop table tbl_word;

create table tbl_word(
	id number primary key,
	en varchar2(56) not null unique,
	cn varchar2(256) not null,
	-- 首字母
	first char(1),
	-- 类别
	category varchar2(100)
);

drop sequence word_id;

create sequence word_id start with 100;

-- create 
drop table tbl_vocabulary;

create table tbl_vocabulary(
	id number primary key,
	en varchar2(256) not null unique,
	cn varchar2(256) not null,
	-- 缩写
	acronym varchar2(18),
	-- 类别
	category varchar2(100)
);

drop sequence vocabulary_id;

create sequence vocabulary_id start with 600;
