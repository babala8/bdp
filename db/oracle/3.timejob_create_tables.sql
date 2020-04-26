DROP TABLE TIME_JOB_LOG CASCADE CONSTRAINTS;
create table TIME_JOB_LOG
(
    LOGIC_ID    VARCHAR2(36)           not null
        primary key,
    JOB_NAME    VARCHAR2(40)           not null,
    JOB_TYPE    NUMBER(11) default '0' not null,
    JOB_RESULT  NUMBER(11) default '0' not null,
    JOB_CREATOR VARCHAR2(20),
    START_TIME  VARCHAR2(19),
    END_TIME    VARCHAR2(19),
    RESULT_DESC CLOB
);
-- tablespace BDP_SPACE;

DROP TABLE TIME_JOB CASCADE CONSTRAINTS;
create table TIME_JOB
(
    ID         VARCHAR2(36)         not null
        primary key,
    TRIG_NAME  VARCHAR2(40)         not null,
    CRON       VARCHAR2(40)         not null,
    JOB_NAME   VARCHAR2(40)         not null,
    OBJ_NAME   VARCHAR2(80)         not null,
    CONCURRENT NUMBER(11) default 0 not null,
    JOB_STATE  NUMBER(11) default 1 not null,
    DESP       VARCHAR2(100),
    ARGUMENTS  VARCHAR2(100),
    INIT_FLAG  NUMBER(1)  default 1 not null
);

commit;
-- tablespace BDP_SPACE;
