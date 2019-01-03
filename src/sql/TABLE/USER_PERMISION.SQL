drop table if exists USER_PERMISION;

/*==============================================================*/
/* Table: USER_PERMISION                                        */
/*==============================================================*/
create table USER_PERMISION
(
   PERID                bigint not null auto_increment comment '权限ID',
   PERNAME              VARCHAR(64) comment '权限名称',
   PERLEV               INTEGER(1) comment '权限等级',
   primary key (PERID)
);

alter table USER_PERMISION comment '用户权限表';
