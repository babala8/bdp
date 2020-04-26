create table WARN_MESSAGE_TABLE
(
    WARNMESSAGE_ID              VARCHAR2(36) not null
        constraint PK_WARN_MESS
            primary key,
    WARNMESSAGE_TYPE            VARCHAR2(3),
    WARNMESSAGE_INFO            VARCHAR2(500),
    WARNMESSAGE_DATE            VARCHAR2(10),
    WARNMESSAGE_TIME            VARCHAR2(8),
    WARNMESSAGE_DETAILINFO      VARCHAR2(500),
    WARNMESSAGE_TO_USERNO       VARCHAR2(100),
    WARNMESSAGE_TO_ROLENO       VARCHAR2(100),
    WARNMESSAGE_HANDLE_STATUS   VARCHAR2(2),
    WARNMESSAGE_HANDLE_USERNO   VARCHAR2(100),
    WARNMESSAGE_HANDLE_USERNAME VARCHAR2(100),
    WARNMESSAGE_HANDLE_DATE     VARCHAR2(20),
    WARNMESSAGE_HANDLE_RESULT   VARCHAR2(2)
);
comment on table WARN_MESSAGE_TABLE is '预警信息表';
comment on column WARN_MESSAGE_TABLE.WARNMESSAGE_ID is '预警信息编号';
comment on column WARN_MESSAGE_TABLE.WARNMESSAGE_TYPE is '预警信息类别';
comment on column WARN_MESSAGE_TABLE.WARNMESSAGE_INFO is '预警信息内容';
comment on column WARN_MESSAGE_TABLE.WARNMESSAGE_DATE is '预警信息生成日期';
comment on column WARN_MESSAGE_TABLE.WARNMESSAGE_TIME is '预警信息生成时间';
comment on column WARN_MESSAGE_TABLE.WARNMESSAGE_DETAILINFO is '预警信息内容详情';
comment on column WARN_MESSAGE_TABLE.WARNMESSAGE_TO_USERNO is '预警信息推送人员';
comment on column WARN_MESSAGE_TABLE.WARNMESSAGE_TO_ROLENO is '预警信息推送角色';
comment on column WARN_MESSAGE_TABLE.WARNMESSAGE_HANDLE_STATUS is '预警信息处理状态';
comment on column WARN_MESSAGE_TABLE.WARNMESSAGE_HANDLE_USERNO is '预警信息处理人员';
comment on column WARN_MESSAGE_TABLE.WARNMESSAGE_HANDLE_USERNAME is '预警信息处理人姓名';
comment on column WARN_MESSAGE_TABLE.WARNMESSAGE_HANDLE_DATE is '预警信息处理人日期';
comment on column WARN_MESSAGE_TABLE.WARNMESSAGE_HANDLE_RESULT is '预警信息处理结果';

create table PUSH_SERVER_INFO
(
    NO      VARCHAR2(32) not null
        primary key,
    TIME    VARCHAR2(30),
    NAME    VARCHAR2(30),
    MESSAGE VARCHAR2(200) default NULL
)

comment on table PUSH_SERVER_INFO is '推送信息记录表';
comment on column PUSH_SERVER_INFO.NO is '序号';
comment on column PUSH_SERVER_INFO.TIME is '推送时间';
comment on column PUSH_SERVER_INFO.NAME is '推送人员';
comment on column PUSH_SERVER_INFO.MESSAGE is '推送信息';

commit;
