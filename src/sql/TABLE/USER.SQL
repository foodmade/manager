drop table if exists USERS;

/*==============================================================*/
/* Table: USERS                                                 */
/*==============================================================*/
create table USERS
(
   USERID               bigint not null auto_increment comment '用户ID',
   PID                  bigint comment '推荐人ID',
   PERID                bigint comment '权限ID',
   LEVELID              bigint comment '等级id',
   USERNAME             VARCHAR(64) comment '用户名',
   USERNO               VARCHAR(32) comment '用户编号',
   PAYPWD               VARCHAR(32) comment '支付密码',
   PWD                  VARCHAR(32) comment '用户密码',
   IDCORD               VARCHAR(64) comment '身份证号',
   ADDRESS              VARCHAR(256) comment '地址',
   REGISTTIME           DATE comment '注册时间',
   ISOLDUSER            INTEGER comment '是否是新用户',
   TLENO                bigint comment '手机号',
   STATE                INT comment '用户状态',
   HEADICONURL          VARCHAR(128) comment '头像地址',
   AREA                 INT comment '所属区域',
   TOPPID               BIGINT comment '顶级父节点',
   primary key (USERID)
);

alter table USERS comment '用户表';

alter table USERS add constraint FK_Reference_2 foreign key (PID)
      references USERS (USERID) on delete restrict on update restrict;

alter table USERS add constraint FK_Reference_5 foreign key (PERID)
      references USER_PERMISION (PERID) on delete restrict on update restrict;

alter table USERS add constraint FK_Reference_6 foreign key (LEVELID)
      references USER_LEVEL (LEVELID) on delete restrict on update restrict;
