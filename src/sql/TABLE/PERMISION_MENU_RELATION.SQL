drop table if exists PERMISION_MENU_RELATION;

/*==============================================================*/
/* Table: PERMISION_MENU_RELATION                               */
/*==============================================================*/
create table PERMISION_MENU_RELATION
(
   ID                   bigint not null auto_increment,
   PERID                bigint comment '权限ID',
   MENUID               bigint comment 'ID',
   primary key (ID)
);

alter table PERMISION_MENU_RELATION comment '角色_菜单中间表';

alter table PERMISION_MENU_RELATION add constraint FK_Reference_13 foreign key (PERID)
      references USER_PERMISION (PERID) on delete restrict on update restrict;

alter table PERMISION_MENU_RELATION add constraint FK_Reference_14 foreign key (MENUID)
      references MUNUTREE (MENUID) on delete restrict on update restrict;
