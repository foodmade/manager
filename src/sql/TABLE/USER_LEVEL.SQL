drop table if exists USER_LEVEL;

/*==============================================================*/
/* Table: USER_LEVEL                                            */
/*==============================================================*/
create table USER_LEVEL
(
   LEVELID              bigint not null auto_increment comment '等级id',
   LEVELNAME            VARCHAR(64) comment '等级名称',
   AMOUNT               float(15,2) comment '日工资',
   DAYNUM               INT comment '天数',
   primary key (LEVELID)
);

alter table USER_LEVEL comment '用户等级表';
