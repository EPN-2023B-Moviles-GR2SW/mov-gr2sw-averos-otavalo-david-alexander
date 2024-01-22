/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     14/1/2024 17:11:54                           */
/*==============================================================*/


drop index CONTAIN_FK;

drop index CYCLE_PK;

drop table CYCLE;

drop index HAVE_FK;

drop index SESION_PK;

drop table SESION;

drop index CONFIGURE_FK;

drop index SETTINGS_PK;

drop table SETTINGS;

drop index USER_PK;

drop table "USER";

/*==============================================================*/
/* Table: CYCLE                                                 */
/*==============================================================*/
create table CYCLE (
   CYCLE_ID             INT4                 not null,
   SESSION_ID           INT4                 not null,
   WORK_DURATION        TIME                 not null,
   REST_DURATION        TIME                 not null,
   START_CYCLE          TIME                 not null,
   END_CYCLE            TIME                 not null,
   constraint PK_CYCLE primary key (CYCLE_ID)
);

/*==============================================================*/
/* Index: CYCLE_PK                                              */
/*==============================================================*/
create unique index CYCLE_PK on CYCLE (
CYCLE_ID
);

/*==============================================================*/
/* Index: CONTAIN_FK                                            */
/*==============================================================*/
create  index CONTAIN_FK on CYCLE (
SESSION_ID
);

/*==============================================================*/
/* Table: SESION                                                */
/*==============================================================*/
create table SESION (
   SESSION_ID           INT4                 not null,
   USER_ID              INT4                 not null,
   START_TIME           TIME                 not null,
   END_TIME             TIME                 not null,
   STATE                VARCHAR(10)          null default 'pause',
   constraint PK_SESION primary key (SESSION_ID)
);

/*==============================================================*/
/* Index: SESION_PK                                             */
/*==============================================================*/
create unique index SESION_PK on SESION (
SESSION_ID
);

/*==============================================================*/
/* Index: HAVE_FK                                               */
/*==============================================================*/
create  index HAVE_FK on SESION (
USER_ID
);

/*==============================================================*/
/* Table: SETTINGS                                              */
/*==============================================================*/
create table SETTINGS (
   SETTINGS_ID          INT4                 not null,
   USER_ID              INT4                 not null,
   DEF_WORK_SETTINGS    TIME                 not null default '00:30:00',
   DEF_SET_SETTINGS     TIME                 not null default '00:05:00',
   constraint PK_SETTINGS primary key (SETTINGS_ID)
);

/*==============================================================*/
/* Index: SETTINGS_PK                                           */
/*==============================================================*/
create unique index SETTINGS_PK on SETTINGS (
SETTINGS_ID
);

/*==============================================================*/
/* Index: CONFIGURE_FK                                          */
/*==============================================================*/
create  index CONFIGURE_FK on SETTINGS (
USER_ID
);

/*==============================================================*/
/* Table: "USER"                                                */
/*==============================================================*/
create table "USER" (
   USER_ID              INT4                 not null,
   NAME                 VARCHAR(50)          not null,
   LAST_NAME            VARCHAR(50)          not null,
   EMAIL                VARCHAR(150)         not null,
   PASSWORD             VARCHAR(50)          not null,
   constraint PK_USER primary key (USER_ID)
);

/*==============================================================*/
/* Index: USER_PK                                               */
/*==============================================================*/
create unique index USER_PK on "USER" (
USER_ID
);

alter table CYCLE
   add constraint FK_CYCLE_CONTAIN_SESION foreign key (SESSION_ID)
      references SESION (SESSION_ID)
      on delete restrict on update restrict;

alter table SESION
   add constraint FK_SESION_HAVE_USER foreign key (USER_ID)
      references "USER" (USER_ID)
      on delete restrict on update restrict;

alter table SETTINGS
   add constraint FK_SETTINGS_CONFIGURE_USER foreign key (USER_ID)
      references "USER" (USER_ID)
      on delete restrict on update restrict;