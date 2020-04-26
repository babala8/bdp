
-----------------------------------------------------------------
--                     data_insight 模块
-----------------------------------------------------------------

create table INSIGHT_USER_CONFIG
(
    USERNAME          VARCHAR2(20) not null
        constraint SYS_USER_CONFIG_IBFK_1
            references SYS_USER,
    USER_DEFAULT_VIEW CLOB,
    SUBJECT_ID        NUMBER       not null,
    primary key (USERNAME, SUBJECT_ID)
);
comment on column INSIGHT_USER_CONFIG.USER_DEFAULT_VIEW is '驾驶舱配置';



create table SAIKU_USER_GROUP
(
    GROUP_ID   VARCHAR2(20) not null
        primary key,
    GROUP_NAME VARCHAR2(20) not null,
    DESP       VARCHAR2(100),
    TOKEN      VARCHAR2(20)
);
create unique index UK_GPNAME
    on SAIKU_USER_GROUP (GROUP_NAME);


create table GIS_POINT_INFO
(
    POINT_ID         VARCHAR2(36)              not null
        primary key,
    POINT_CREATOR    VARCHAR2(36)              not null,
    POINT_ORGNO      VARCHAR2(36)              not null,
    CREATE_DATE      VARCHAR2(34) default NULL not null,
    STATUS           NUMBER                    not null,
    POINT_HTML       CLOB                      not null,
    POINT_JS         CLOB                      not null,
    LASTEST_MOD_OP   VARCHAR2(20),
    LASTEST_MOD_TIME VARCHAR2(34) default NULL
);
create index FK_GIS_CREATOR
    on GIS_POINT_INFO (POINT_CREATOR);
create index FK_GIS_MOD_OP
    on GIS_POINT_INFO (LASTEST_MOD_OP);
create index FK_GIS_ORGNO
    on GIS_POINT_INFO (POINT_ORGNO);


create table SELFDEF_GROUP
(
    GROUPID   NUMBER not null
        primary key,
    GROUPNAME VARCHAR2(36)
);

create table SELFDEF_DETAILQUERY
(
    ID               VARCHAR2(36)              not null
        primary key,
    NAME             VARCHAR2(20)              not null,
    DEF              CLOB                      not null,
    STATUS           NUMBER       default '1'  not null,
    GROUPID          NUMBER                    not null
        constraint SYS_C005442
            references SELFDEF_GROUP,
    CREATOR          VARCHAR2(20)              not null
        constraint SYS_C005440
            references SYS_USER,
    CREATOR_ORGNO    VARCHAR2(20)              not null,
    CREATE_TIME      VARCHAR2(20) default NULL not null,
    LASTEST_MOD_OP   VARCHAR2(20)
        constraint SYS_C005441
            references SYS_USER,
    LASTEST_MOD_TIME VARCHAR2(20) default NULL
);
create index FK_DETAILQUERY_CRTR on SELFDEF_DETAILQUERY (CREATOR);
create index FK_DETAILQUERY_GROUP on SELFDEF_DETAILQUERY (GROUPID);
create index FK_DETAILQUERY_MOD on SELFDEF_DETAILQUERY (LASTEST_MOD_OP);
create unique index UK_DETAILQUERY_NAME on SELFDEF_DETAILQUERY (NAME);

create table SELFDEF_REPORTS
(
    ID               VARCHAR2(36)              not null
        primary key,
    NAME             VARCHAR2(36) default NULL not null,
    FILENAME         VARCHAR2(36)              not null,
    PARAMETERS       CLOB,
    GROUPID          NUMBER                    not null
        constraint SYS_C005443
            references SELFDEF_GROUP,
    STATUS           NUMBER       default '1'  not null,
    CREATOR          VARCHAR2(20)              not null,
    CREATOR_ORGNO    VARCHAR2(20)              not null,
    CREATE_TIME      VARCHAR2(20) default NULL not null,
    LASTEST_MOD_OP   VARCHAR2(20),
    LASTEST_MOD_TIME VARCHAR2(20) default NULL
);
create index FK_REPORT_CRTR on SELFDEF_REPORTS (CREATOR);
create index FK_REPORT_GROUP on SELFDEF_REPORTS (GROUPID);
create index FK_REPORT_MOD on SELFDEF_REPORTS (LASTEST_MOD_OP);
create index FK_REPORT_ORGNO on SELFDEF_REPORTS (CREATOR_ORGNO);
create unique index UK_REPORT_NAME on SELFDEF_REPORTS (NAME);


create table CHART_TYPE
(
    TYPE_ID   VARCHAR2(32) not null
        primary key,
    TYPE_NAME VARCHAR2(20) not null,
    TYPE_DESC VARCHAR2(64)
);

create unique index UK_CHART_TYPE
    on CHART_TYPE (TYPE_NAME);

create table CHART_SUBJECT
(
    SUBJECT_ID   NUMBER       not null
        primary key,
    SUBJECT_NAME VARCHAR2(20) not null,
    SUBJECT_DESC VARCHAR2(100)
);

create unique index UK_CHART_SUBJ
    on CHART_SUBJECT (SUBJECT_NAME);


create table SELFDEF_CHARTS
(
    CHART_ID         VARCHAR2(36)              not null
        primary key,
    CHART_NAME       VARCHAR2(32)              not null,
    CHART_SUBJECT    NUMBER       default '0'  not null
        constraint SYS_C005439
            references CHART_SUBJECT,
    CHART_TYPE       VARCHAR2(32) default NULL,
    CHART_ICON       VARCHAR2(50),
    CHART_DEF        CLOB                      not null,
    CHART_DESC       VARCHAR2(100),
    CHART_OPTION     CLOB,
    CHART_STATUS     NUMBER       default '1'  not null,
    CREATOR          VARCHAR2(20)              not null
        constraint SYS_C005437
            references SYS_USER,
    CREATE_TIME      VARCHAR2(20) default NULL not null,
    LASTEST_MOD_OP   VARCHAR2(20)
        constraint SYS_C005438
            references SYS_USER,
    LASTEST_MOD_TIME VARCHAR2(20) default NULL,
    CREATOR_ORGNO    VARCHAR2(20)
);
comment on table SELFDEF_CHARTS is '自定义图表';
comment on column SELFDEF_CHARTS.CHART_ID is '图表编号';
comment on column SELFDEF_CHARTS.CHART_NAME is '图表名称';
comment on column SELFDEF_CHARTS.CHART_SUBJECT is '图表主题';
comment on column SELFDEF_CHARTS.CHART_TYPE is '图表类型';
comment on column SELFDEF_CHARTS.CHART_DEF is '图表定义';
comment on column SELFDEF_CHARTS.CHART_DESC is '图表描述';
comment on column SELFDEF_CHARTS.CHART_OPTION is '图表样式 JS内容';
comment on column SELFDEF_CHARTS.CHART_STATUS is '图表状态 0-不可用 1-可用';
comment on column SELFDEF_CHARTS.CREATOR is '图表创建者';
comment on column SELFDEF_CHARTS.CREATE_TIME is '图表创建时间YYYY-MM-DD hh:mm:ss';
comment on column SELFDEF_CHARTS.LASTEST_MOD_OP is '图表最近修改人';
comment on column SELFDEF_CHARTS.LASTEST_MOD_TIME is '图表最近修改时间YYYY-MM-DD hh:mm:ss';
comment on column SELFDEF_CHARTS.CREATOR_ORGNO is '图表创建者所在机构号';
create index FK_CHART_CRTR on SELFDEF_CHARTS (CREATOR);
create index FK_CHART_MOD on SELFDEF_CHARTS (LASTEST_MOD_OP);
create index FK_CHART_SUBJ on SELFDEF_CHARTS (CHART_SUBJECT);
create unique index UK_CHART_NAME on SELFDEF_CHARTS (CHART_NAME);

create table MODEL_ADAPTER_TABLE
(
    TERMID        VARCHAR2(30) not null,
    TXTYPE        VARCHAR2(10) not null,
    MODELID       VARCHAR2(30),
    MODELSOURCE   VARCHAR2(20),
    ADJUSTAMT     NUMBER,
    USETIME       VARCHAR2(20),
    ALGORITHMTYPE VARCHAR2(500),
    constraint PK_MODEL_ADAPTER_TABLE
        primary key (TERMID, TXTYPE)
);
comment on table MODEL_ADAPTER_TABLE is '模型适配表';
comment on column MODEL_ADAPTER_TABLE.TERMID is '设备号';
comment on column MODEL_ADAPTER_TABLE.TXTYPE is '存取款模型标志';
comment on column MODEL_ADAPTER_TABLE.MODELID is '模型ID';
comment on column MODEL_ADAPTER_TABLE.MODELSOURCE is '模型应用源';
comment on column MODEL_ADAPTER_TABLE.ADJUSTAMT is '浮动加钞值 单位:元';
comment on column MODEL_ADAPTER_TABLE.USETIME is '模型使用时间';
comment on column MODEL_ADAPTER_TABLE.ALGORITHMTYPE is '机器学习算法类别 ,101:上下文GRU,102:时间序列GRU,103:上下文LSTM,104:时间序列LSTM,105:HoltWinters';

create table MODEL_SPECIDAY_TRAIN_CFG
(
    DEV_NO                 VARCHAR2(32)     not null,
    HOLIDAYTYPE            NUMBER           not null,
    WEIGHT_MODEL_TRAIN_CWD NUMBER default 1 not null,
    WEIGHT_FEATURE_CWD     NUMBER default 0 not null,
    TRAIN_CYCLE            NUMBER default 3,
    WEIGHT_MODEL_TRAIN_CDM NUMBER default 1,
    WEIGHT_FEATURE_CDM     NUMBER default 0,
    constraint PK_MODEL_SPECIDAY_TRAIN_CFG
        primary key (DEV_NO, HOLIDAYTYPE)
);
comment on column MODEL_SPECIDAY_TRAIN_CFG.DEV_NO is '设备号';
comment on column MODEL_SPECIDAY_TRAIN_CFG.HOLIDAYTYPE is '节日类型';
comment on column MODEL_SPECIDAY_TRAIN_CFG.WEIGHT_MODEL_TRAIN_CWD is '模型训练权重-取款';
comment on column MODEL_SPECIDAY_TRAIN_CFG.WEIGHT_FEATURE_CWD is '特征值权重-取款';
comment on column MODEL_SPECIDAY_TRAIN_CFG.TRAIN_CYCLE is '训练周期：代表1日--28日定期进行模型训练';
comment on column MODEL_SPECIDAY_TRAIN_CFG.WEIGHT_MODEL_TRAIN_CDM is '模型训练权重-存款';
comment on column MODEL_SPECIDAY_TRAIN_CFG.WEIGHT_FEATURE_CDM is '特征值权重-存款';

create table MODEL_TRAIN_CFG
(
    DEV_NO                 VARCHAR2(32)            not null
        constraint PK_MODEL_TRAIN
            primary key,
    WEIGHT_MODEL_TRAIN_CWD NUMBER        default 0 not null,
    WEIGHT_FEATURE_CWD     NUMBER        default 1 not null,
    TRAIN_CYCLE            NUMBER        default 5 not null,
    WEIGHT_MODEL_TRAIN_CDM NUMBER        default 0,
    WEIGHT_FEATURE_CDM     NUMBER        default 1,
    PRACTICE_PERIOD        NUMBER        default 7,
    MIN_ADDNOTES_MODULUS   NUMBER(10, 2) default 0.5,
    MIN_INVENTORY_MODULUS  NUMBER(10, 2) default 0.5,
    MIN_BACK_MODULUS       NUMBER(10, 2) default 0.5
);
comment on column MODEL_TRAIN_CFG.DEV_NO is '设备号';
comment on column MODEL_TRAIN_CFG.WEIGHT_MODEL_TRAIN_CWD is '模型训练权重-取款';
comment on column MODEL_TRAIN_CFG.WEIGHT_FEATURE_CWD is '特征值权重-取款';
comment on column MODEL_TRAIN_CFG.TRAIN_CYCLE is '训练周期：代表1日--28日定期进行模型训练';
comment on column MODEL_TRAIN_CFG.WEIGHT_MODEL_TRAIN_CDM is '模型训练权重-存款';
comment on column MODEL_TRAIN_CFG.WEIGHT_FEATURE_CDM is '特征值权重-存款';
comment on column MODEL_TRAIN_CFG.PRACTICE_PERIOD is '模型训练周期';
comment on column MODEL_TRAIN_CFG.MIN_ADDNOTES_MODULUS is '最小加钞系数';
comment on column MODEL_TRAIN_CFG.MIN_INVENTORY_MODULUS is '最小库存系数';
comment on column MODEL_TRAIN_CFG.MIN_BACK_MODULUS is '最小回钞系数';

create table MODEL_TRAIN_TABLE
(
    MODELID       VARCHAR2(30) not null
        constraint PK_MODEL_TRAIN_TABLE
            primary key,
    MODELLEVEL    VARCHAR2(2),
    ALGORITHMTYPE VARCHAR2(3),
    TRAINTIME     VARCHAR2(20),
    STARTDATE     VARCHAR2(20),
    ENDDATE       VARCHAR2(20),
    TERMID        VARCHAR2(20),
    TXTYPE        VARCHAR2(10),
    TRAINSTATE    VARCHAR2(4)
);
comment on table MODEL_TRAIN_TABLE is '模型训练记录表';
comment on column MODEL_TRAIN_TABLE.MODELID is '模型ID';
comment on column MODEL_TRAIN_TABLE.MODELLEVEL is '模型层级';
comment on column MODEL_TRAIN_TABLE.ALGORITHMTYPE is '机器学习算法类别,101:上下文GRU,102:时间序列GRU,103:上下文LSTM,104:时间序列LSTM,105:HoltWinters';
comment on column MODEL_TRAIN_TABLE.TRAINTIME is '模型训练时间';
comment on column MODEL_TRAIN_TABLE.STARTDATE is '训练数据开始时间';
comment on column MODEL_TRAIN_TABLE.ENDDATE is '训练数据结束时间';
comment on column MODEL_TRAIN_TABLE.TERMID is '所属设备';
comment on column MODEL_TRAIN_TABLE.TXTYPE is '存取款类型 0：取款 1：存款';
comment on column MODEL_TRAIN_TABLE.TRAINSTATE is '训练状态 00:成功 FF:失败';

commit;




