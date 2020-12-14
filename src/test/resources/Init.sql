-- member表
DROP TABLE IF EXISTS Member;
CREATE TABLE Member(
id bigint primary key not null comment '雪花id',
dimid bigint comment'维度id',
pid bigint comment'父id',
name varchar(255) not null comment'成员名称',
code varchar(255) not null comment'成员编码',
position int comment'成员排序',
generation int comment'成员代',
datatype int default 0 comment'数据类型:0数值,1货币,2整数,3时间类型,4文本,5下拉列表,6手动上卷,7自动上卷',
membertype int default 1 comment'成员类型,0维度,1成员,2共享成员',
status int default 0 comment'成员状态,0正常,1只读,2冻结',
weight float default 1 comment'权重',
unicode text comment '唯一编码',
unipos text comment '唯一排序'
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE index index_code  ON Member (dimid,code,status);
CREATE index index_position  ON Member (position asc);
-- CREATE index index_unicode  ON Member (unicode);

-- ATTR表
DROP TABLE IF EXISTS ATTR;
CREATE TABLE ATTR(
id bigint primary key not null comment '雪花id',
dimid bigint comment'维度id',
attrName varchar(255)comment'属性名称'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ATTRValue表
DROP TABLE IF EXISTS ATTRValue;
CREATE TABLE ATTRValue(
id bigint primary key not null comment '雪花id',
attrid bigint comment '属性id',
memberCode varchar(255) comment'成员code',
attrValue varchar(255)comment'属性值'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE index index_code  ON attrvalue (attrid,memberCode);
-- Cube表
DROP TABLE IF EXISTS Cube;
CREATE TABLE Cube(
id bigint primary key not null comment '雪花id',
dimids text comment'包含的id信息',
facttable varchar(255) comment'实体表名',
pkcol varchar(255) default 'ID' comment 'id列',
measurecol varchar(500) default 'VALUE' comment'度量列',
postion int comment'cube排序',
autoload int default 0 comment'状态,0自动加载,1手动加载'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Script表
DROP TABLE IF EXISTS Script;
CREATE TABLE Script(
id bigint primary key not null comment '雪花id',
scrpitContent text comment '脚本信息',
scrpitName varchar(255) comment '脚本名称',
status int default 0 comment'状态,0正常,1不执行',
lastStatus int default 0 comment '上次执行状态,0未执行,1执行成功,2执行失败',
lastupdate timestamp default now() comment '更新时间',
updateuser varchar(255) comment '更新人'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;