create table DROA
(
   DROAID               bigint not null auto_increment comment '提现ID',
   USERID               bigint comment '用户ID',
   EXEUSERID            bigint comment '提现处理操作员',
   STATE                INTEGER(1) comment '提现状态',
   EXETIME              DATE comment '处理时间',
   APPLYTIME            DATE comment '提现时间',
   DROAAMOUNT           FLOAT(15,2) comment '提现金额',
   CROD                 varchar(32) comment '提现银行卡',
   BALANCE              Double comment '提现后余额',
   primary key (DROAID)
);

alter table DROA comment '提现表';

alter table DROA add constraint FK_Reference_10 foreign key (EXEUSERID)
      references USERS (USERID) on delete restrict on update restrict;

alter table DROA add constraint FK_Reference_9 foreign key (USERID)
      references USERS (USERID) on delete restrict on update restrict;
