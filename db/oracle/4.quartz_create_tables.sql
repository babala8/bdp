--
-- A hint submitted by a user: Oracle DB MUST be created as "shared" and the 
-- job_queue_processes parameter  must be greater than 2
-- However, these settings are pretty much standard after any
-- Oracle install, so most users need not worry about this.
--
-- Many other users (including the primary author of Quartz) have had success
-- runing in dedicated mode, so only consider the above as a hint ;-)
--

delete from qrtz_fired_triggers;
delete from qrtz_simple_triggers;
delete from qrtz_simprop_triggers;
delete from qrtz_cron_triggers;
delete from qrtz_blob_triggers;
delete from qrtz_triggers;
delete from qrtz_job_details;
delete from qrtz_calendars;
delete from qrtz_paused_trigger_grps;
delete from qrtz_locks;
delete from qrtz_scheduler_state;

drop table qrtz_calendars;
drop table qrtz_fired_triggers;
drop table qrtz_blob_triggers;
drop table qrtz_cron_triggers;
drop table qrtz_simple_triggers;
drop table qrtz_simprop_triggers;
drop table qrtz_triggers;
drop table qrtz_job_details;
drop table qrtz_paused_trigger_grps;
drop table qrtz_locks;
drop table qrtz_scheduler_state;


create table QRTZ_JOB_DETAILS
(
    SCHED_NAME        VARCHAR2(120) not null,
    JOB_NAME          VARCHAR2(200) not null,
    JOB_GROUP         VARCHAR2(200) not null,
    DESCRIPTION       VARCHAR2(250),
    JOB_CLASS_NAME    VARCHAR2(250) not null,
    IS_DURABLE        VARCHAR2(1)   not null,
    IS_NONCONCURRENT  VARCHAR2(1)   not null,
    IS_UPDATE_DATA    VARCHAR2(1)   not null,
    REQUESTS_RECOVERY VARCHAR2(1)   not null,
    JOB_DATA          BLOB,
    constraint QRTZ_JOB_DETAILS_PK
        primary key (SCHED_NAME, JOB_NAME, JOB_GROUP)
);
create table QRTZ_TRIGGERS
(
    SCHED_NAME     VARCHAR2(120) not null,
    TRIGGER_NAME   VARCHAR2(200) not null,
    TRIGGER_GROUP  VARCHAR2(200) not null,
    JOB_NAME       VARCHAR2(200) not null,
    JOB_GROUP      VARCHAR2(200) not null,
    DESCRIPTION    VARCHAR2(250),
    NEXT_FIRE_TIME NUMBER(13),
    PREV_FIRE_TIME NUMBER(13),
    PRIORITY       NUMBER(13),
    TRIGGER_STATE  VARCHAR2(16)  not null,
    TRIGGER_TYPE   VARCHAR2(8)   not null,
    START_TIME     NUMBER(13)    not null,
    END_TIME       NUMBER(13),
    CALENDAR_NAME  VARCHAR2(200),
    MISFIRE_INSTR  NUMBER(2),
    JOB_DATA       BLOB,
    constraint QRTZ_TRIGGERS_PK
        primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_TRIGGER_TO_JOBS_FK
        foreign key (SCHED_NAME, JOB_NAME, JOB_GROUP) references QRTZ_JOB_DETAILS
);
create table QRTZ_SIMPLE_TRIGGERS
(
    SCHED_NAME      VARCHAR2(120) not null,
    TRIGGER_NAME    VARCHAR2(200) not null,
    TRIGGER_GROUP   VARCHAR2(200) not null,
    REPEAT_COUNT    NUMBER(7)     not null,
    REPEAT_INTERVAL NUMBER(12)    not null,
    TIMES_TRIGGERED NUMBER(10)    not null,
    constraint QRTZ_SIMPLE_TRIG_PK
        primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_SIMPLE_TRIG_TO_TRIG_FK
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references QRTZ_TRIGGERS
);
create table QRTZ_CRON_TRIGGERS
(
    SCHED_NAME      VARCHAR2(120) not null,
    TRIGGER_NAME    VARCHAR2(200) not null,
    TRIGGER_GROUP   VARCHAR2(200) not null,
    CRON_EXPRESSION VARCHAR2(120) not null,
    TIME_ZONE_ID    VARCHAR2(80),
    constraint QRTZ_CRON_TRIG_PK
        primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_CRON_TRIG_TO_TRIG_FK
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references QRTZ_TRIGGERS
);
create table QRTZ_SIMPROP_TRIGGERS
(
    SCHED_NAME    VARCHAR2(120) not null,
    TRIGGER_NAME  VARCHAR2(200) not null,
    TRIGGER_GROUP VARCHAR2(200) not null,
    STR_PROP_1    VARCHAR2(512),
    STR_PROP_2    VARCHAR2(512),
    STR_PROP_3    VARCHAR2(512),
    INT_PROP_1    NUMBER(10),
    INT_PROP_2    NUMBER(10),
    LONG_PROP_1   NUMBER(13),
    LONG_PROP_2   NUMBER(13),
    DEC_PROP_1    NUMBER(13, 4),
    DEC_PROP_2    NUMBER(13, 4),
    BOOL_PROP_1   VARCHAR2(1),
    BOOL_PROP_2   VARCHAR2(1),
    constraint QRTZ_SIMPROP_TRIG_PK
        primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_SIMPROP_TRIG_TO_TRIG_FK
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references QRTZ_TRIGGERS
);
create table QRTZ_BLOB_TRIGGERS
(
    SCHED_NAME    VARCHAR2(120) not null,
    TRIGGER_NAME  VARCHAR2(200) not null,
    TRIGGER_GROUP VARCHAR2(200) not null,
    BLOB_DATA     BLOB,
    constraint QRTZ_BLOB_TRIG_PK
        primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_BLOB_TRIG_TO_TRIG_FK
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references QRTZ_TRIGGERS
);
create table QRTZ_CALENDARS
(
    SCHED_NAME    VARCHAR2(120) not null,
    CALENDAR_NAME VARCHAR2(200) not null,
    CALENDAR      BLOB          not null,
    constraint QRTZ_CALENDARS_PK
        primary key (SCHED_NAME, CALENDAR_NAME)
);
create table QRTZ_PAUSED_TRIGGER_GRPS
(
    SCHED_NAME    VARCHAR2(120) not null,
    TRIGGER_GROUP VARCHAR2(200) not null,
    constraint QRTZ_PAUSED_TRIG_GRPS_PK
        primary key (SCHED_NAME, TRIGGER_GROUP)
);
create table QRTZ_FIRED_TRIGGERS
(
    SCHED_NAME        VARCHAR2(120) not null,
    ENTRY_ID          VARCHAR2(95)  not null,
    TRIGGER_NAME      VARCHAR2(200) not null,
    TRIGGER_GROUP     VARCHAR2(200) not null,
    INSTANCE_NAME     VARCHAR2(200) not null,
    FIRED_TIME        NUMBER(13)    not null,
    SCHED_TIME        NUMBER(13)    not null,
    PRIORITY          NUMBER(13)    not null,
    STATE             VARCHAR2(16)  not null,
    JOB_NAME          VARCHAR2(200),
    JOB_GROUP         VARCHAR2(200),
    IS_NONCONCURRENT  VARCHAR2(1),
    REQUESTS_RECOVERY VARCHAR2(1),
    constraint QRTZ_FIRED_TRIGGER_PK
        primary key (SCHED_NAME, ENTRY_ID)
);
create table QRTZ_SCHEDULER_STATE
(
    SCHED_NAME        VARCHAR2(120) not null,
    INSTANCE_NAME     VARCHAR2(200) not null,
    LAST_CHECKIN_TIME NUMBER(13)    not null,
    CHECKIN_INTERVAL  NUMBER(13)    not null,
    constraint QRTZ_SCHEDULER_STATE_PK
        primary key (SCHED_NAME, INSTANCE_NAME)
);
create table QRTZ_LOCKS
(
    SCHED_NAME VARCHAR2(120) not null,
    LOCK_NAME  VARCHAR2(40)  not null,
    constraint QRTZ_LOCKS_PK
        primary key (SCHED_NAME, LOCK_NAME)
);

create index idx_qrtz_j_req_recovery on qrtz_job_details(SCHED_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_j_grp on qrtz_job_details(SCHED_NAME,JOB_GROUP);

create index idx_qrtz_t_j on qrtz_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_t_jg on qrtz_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_t_c on qrtz_triggers(SCHED_NAME,CALENDAR_NAME);
create index idx_qrtz_t_g on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP);
create index idx_qrtz_t_state on qrtz_triggers(SCHED_NAME,TRIGGER_STATE);
create index idx_qrtz_t_n_state on qrtz_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_n_g_state on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_next_fire_time on qrtz_triggers(SCHED_NAME,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st on qrtz_triggers(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME);
create index idx_qrtz_ft_inst_job_req_rcvry on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_ft_j_g on qrtz_fired_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_ft_jg on qrtz_fired_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_ft_t_g on qrtz_fired_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
create index idx_qrtz_ft_tg on qrtz_fired_triggers(SCHED_NAME,TRIGGER_GROUP);

commit;


