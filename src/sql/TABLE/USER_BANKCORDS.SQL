drop table if exists USER_BANKCORDS;

/*==============================================================*/
/* Table: USER_BANKCORDS                                        */
/*==============================================================*/
create table USER_BANKCORDS
(
   CARDID               bigint not null auto_increment comment '银行卡号ID',
   USERID               bigint comment '用户ID',
   BANKNAME             VARCHAR(256) comment '开户行',
   BANKNO               INT comment '卡号',
   primary key (CARDID)
);

alter table USER_BANKCORDS comment '用户银行卡表';

alter table USER_BANKCORDS add constraint FK_Reference_1 foreign key (USERID)
      references USERS (USERID) on delete restrict on update restrict;
