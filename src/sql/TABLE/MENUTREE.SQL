create table MENUTREE
(
   MENUID               bigint not null auto_increment comment 'ID',
   PID                  bigint comment '父ID',
   MENUTREE             varchar(32) comment '菜单树层级编号',
   MENUNAME             varchar(64) comment '菜单名称',
   primary key (MENUID)
);

alter table MENUTREE comment '菜单表';

alter table MENUTREE add constraint FK_Reference_15 foreign key (PID)
      references MENUTREE (MENUID) on delete restrict on update restrict;