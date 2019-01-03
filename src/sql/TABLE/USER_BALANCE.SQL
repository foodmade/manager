drop table if exists USER_BALANCE;

/*==============================================================*/
/* Table: USER_BALANCE                                          */
/*==============================================================*/
create table USER_BALANCE
(
   BALID                bigint not null auto_increment comment '余额ID',
   USERID               bigint comment '用户ID',
   BALANCE              FLOAT(15,2) comment '余额',
   primary key (BALID)
);

alter table USER_BALANCE comment '余额表';

alter table USER_BALANCE add constraint FK_Reference_8 foreign key (USERID)
      references USERS (USERID) on delete restrict on update restrict;
