drop table if exists USER_LEVEL_LOG;

/*==============================================================*/
/* Table: USER_LEVEL_LOG                                        */
/*==============================================================*/
create table USER_LEVEL_LOG
(
   LOGID                bigint not null auto_increment comment '日志ID',
   USERID               bigint comment '用户ID',
   LEVELID              bigint comment '等级id',
   TIME                 DATE comment '调整日期',
   REASON               VARCHAR(128) comment '调整原因',
   primary key (LOGID)
);

alter table USER_LEVEL_LOG comment '用户等级调整记录表';

alter table USER_LEVEL_LOG add constraint FK_Reference_12 foreign key (LEVELID)
      references USER_LEVEL (LEVELID) on delete restrict on update restrict;

alter table USER_LEVEL_LOG add constraint FK_Reference_7 foreign key (USERID)
      references USERS (USERID) on delete restrict on update restrict;
