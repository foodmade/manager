drop table if exists USER_AMOUNT_DETAIL;

/*==============================================================*/
/* Table: USER_AMOUNT_DETAIL                                    */
/*==============================================================*/
create table USER_AMOUNT_DETAIL
(
   DELID                bigint not null auto_increment comment '明细ID',
   USERID               bigint comment '用户ID',
   TIME                 DATE comment '时间',
   COMAMOUNT            FLOAT(15,2) comment '分红',
   AMOUNT               FLOAT(15,2) comment '金额',
   TAX                  FLOAT(15,2) comment '税收',
   REPEATAMOUNT         FLOAT(15,2) comment '重消',
   RELAMOUNT            FLOAT(15,2) comment '实得金额',
   primary key (DELID)
);

alter table USER_AMOUNT_DETAIL comment '日工资表';

alter table USER_AMOUNT_DETAIL add constraint FK_Reference_3 foreign key (USERID)
      references USERS (USERID) on delete restrict on update restrict;
