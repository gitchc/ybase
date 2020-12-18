-- member表
DROP TABLE IF EXISTS Member;
CREATE TABLE Member(
id varchar(100) primary key not null ,
dimid varchar(100) ,
pid varchar(100) ,
name varchar(255) not null ,
code varchar(255) not null ,
position int ,
generation int ,
datatype int default 0 ,
membertype int default 1 ,
status int default 0 ,
weight float default 1 ,
unicode text ,
unipos text 
);

CREATE index index_code  ON Member (dimid,code,status,membertype);
CREATE index index_position  ON Member (position asc);


-- ATTR表
DROP TABLE IF EXISTS ATTR;
CREATE TABLE ATTR(
id varchar(100) primary key not null,
dimid varchar(100) ,
attrName varchar(255)
);
CREATE index index_attr  ON ATTR (dimid,attrName);
-- ATTRValue表
DROP TABLE IF EXISTS ATTRValue;
CREATE TABLE ATTRValue(
id varchar(100) primary key not null ,
attrid varchar(100) ,
memberCode varchar(255) ,
attrValue varchar(255)
);
CREATE index index_attrvalue  ON attrvalue (attrid,memberCode);
-- Cube表
DROP TABLE IF EXISTS Cube;
CREATE TABLE Cube(
id varchar(100) primary key not null ,
cubename varchar(255) ,
cubecode varchar(255) ,
dimids text  ,
loadsql text ,
position int ,
autoload int default 1 ,
autosql  int default 1
);

-- Script表
DROP TABLE IF EXISTS Script;
CREATE TABLE Script(
id varchar(100) primary key not null ,
name varchar(255) ,
content text ,
laststatus int default 0 ,
lastupdate timestamp default now() ,
updateuser varchar(255) ,
version int default 0
)

