-----------------------------------------------------------------
---------                   base 模块
-----------------------------------------------------------------

create table DEV_CATALOG_TABLE
(
    NO           VARCHAR2(5)  not null
        constraint PK_DEV_CATALOG_TABLE
            primary key,
    NAME         VARCHAR2(30) not null,
    ENNAME       VARCHAR2(30),
    MONITOR_TYPE VARCHAR2(2)
);
comment on table DEV_CATALOG_TABLE is '设备类型表';
comment on column DEV_CATALOG_TABLE.NO is '编号 10001 CRS 10002 CDM 10003 ATM 10004 BSM';
comment on column DEV_CATALOG_TABLE.NAME is '设备类型';
comment on column DEV_CATALOG_TABLE.ENNAME is '描述';
comment on column DEV_CATALOG_TABLE.MONITOR_TYPE is '监控类型[1:自助设备][2:发卡机][3:非现金] [其它 不需要监控的设备]';


create table DEV_STATUS_TABLE
(
    DEV_NO                  VARCHAR2(20) not null
        constraint PK_DEV_STAT
            primary key,
    STATUS_LAST_TIME        CHAR(14),
    DEV_RUN_STATUS          VARCHAR2(20),
    DEV_MOD_STATUS          VARCHAR2(20),
    DEV_CASHBOX_STATUS      VARCHAR2(20),
    IDC_DEVICE_STATUS       VARCHAR2(15),
    CHK_DEVICE_STATUS       VARCHAR2(15),
    PBK_DEVICE_STATUS       VARCHAR2(15),
    PIN_DEVICE_STATUS       VARCHAR2(15),
    SIU_DEVICE_STATUS       VARCHAR2(15),
    DEP_DEVICE_STATUS       VARCHAR2(15),
    CAM_DEVICE_STATUS       VARCHAR2(15),
    CIM_DEVICE_STATUS       VARCHAR2(15),
    CDM_DEVICE_STATUS       VARCHAR2(15),
    SPR_DEVICE_STATUS       VARCHAR2(15),
    RPR_DEVICE_STATUS       VARCHAR2(15),
    JPR_DEVICE_STATUS       VARCHAR2(15),
    TTU_DEVICE_STATUS       VARCHAR2(15),
    RPR_PAPER_STATUS        VARCHAR2(20),
    JPR_PAPER_STATUS        VARCHAR2(20),
    CDM_SHUTTER_STATUS      VARCHAR2(40),
    CDM_STACKER_STATUS      VARCHAR2(20),
    CDM_INPUT_OUTPUT_STATUS VARCHAR2(20),
    CIM_SHUTTER_STATUS      VARCHAR2(40),
    CIM_ESCROW_STATUS       VARCHAR2(20),
    CIM_INPUT_OUTPUT_STATUS VARCHAR2(100),
    IDC_CAPTURE_BIN_COUNT   VARCHAR2(10),
    AVAILABLE_AMT           NUMBER,
    LAST_ADDNOTE_DATE       VARCHAR2(10),
    LAST_ADDNOTE_TIME       VARCHAR2(8)
);
comment on table DEV_STATUS_TABLE is '设备模块状态表';
comment on column DEV_STATUS_TABLE.DEV_NO is '设备号';
comment on column DEV_STATUS_TABLE.STATUS_LAST_TIME is '更新日期';
comment on column DEV_STATUS_TABLE.DEV_RUN_STATUS is '设备运行状态';
comment on column DEV_STATUS_TABLE.DEV_MOD_STATUS is '设备模块状态';
comment on column DEV_STATUS_TABLE.DEV_CASHBOX_STATUS is '设备钱箱状态';
comment on column DEV_STATUS_TABLE.IDC_DEVICE_STATUS is '设备读卡器模块状态';
comment on column DEV_STATUS_TABLE.CHK_DEVICE_STATUS is '设备支票赌扫描模块状态';
comment on column DEV_STATUS_TABLE.PBK_DEVICE_STATUS is '设备存折模块状态';
comment on column DEV_STATUS_TABLE.PIN_DEVICE_STATUS is '设备密码键盘模块状态';
comment on column DEV_STATUS_TABLE.SIU_DEVICE_STATUS is '设备传感器模块状态';
comment on column DEV_STATUS_TABLE.DEP_DEVICE_STATUS is '设备信封存款模块状态';
comment on column DEV_STATUS_TABLE.CAM_DEVICE_STATUS is '设备照相机模块状态';
comment on column DEV_STATUS_TABLE.CIM_DEVICE_STATUS is '设备存款模块状态';
comment on column DEV_STATUS_TABLE.CDM_DEVICE_STATUS is '设备取款模块状态';
comment on column DEV_STATUS_TABLE.SPR_DEVICE_STATUS is '设备结单打印机模块状态';
comment on column DEV_STATUS_TABLE.RPR_DEVICE_STATUS is '设备凭条打印机模块状态';
comment on column DEV_STATUS_TABLE.JPR_DEVICE_STATUS is '设备日志打印机模块状态';
comment on column DEV_STATUS_TABLE.TTU_DEVICE_STATUS is '设备文本终端模块状态';
comment on column DEV_STATUS_TABLE.RPR_PAPER_STATUS is 'RPR打印纸状态';
comment on column DEV_STATUS_TABLE.JPR_PAPER_STATUS is 'JPR打印纸状态';
comment on column DEV_STATUS_TABLE.CDM_SHUTTER_STATUS is 'CDM挡板集合';
comment on column DEV_STATUS_TABLE.CDM_STACKER_STATUS is 'CDM挡板集合';
comment on column DEV_STATUS_TABLE.CDM_INPUT_OUTPUT_STATUS is 'CDM输入输出状态';
comment on column DEV_STATUS_TABLE.CIM_SHUTTER_STATUS is 'CIM挡板集合';
comment on column DEV_STATUS_TABLE.CIM_ESCROW_STATUS is 'CIM暂存器状态';
comment on column DEV_STATUS_TABLE.CIM_INPUT_OUTPUT_STATUS is 'CIM输入输出状态';
comment on column DEV_STATUS_TABLE.IDC_CAPTURE_BIN_COUNT is '回收箱状态';
comment on column DEV_STATUS_TABLE.AVAILABLE_AMT is '剩余可取钞量';
comment on column DEV_STATUS_TABLE.LAST_ADDNOTE_DATE is '最近加钞日期';
comment on column DEV_STATUS_TABLE.LAST_ADDNOTE_TIME is '最近加钞时间';


create table DEV_TYPE_TABLE
(
    NO          VARCHAR2(5)  not null
        constraint PK_DEV_TYPE_TABLE
            primary key,
    NAME        VARCHAR2(60) not null,
    DEV_VENDOR  VARCHAR2(5)  not null,
    DEV_CATALOG VARCHAR2(5)  not null,
    SPEC        VARCHAR2(20),
    WEIGHT      VARCHAR2(10),
    WATT        VARCHAR2(10),
    CASH_TYPE   VARCHAR2(2)
);
comment on table DEV_TYPE_TABLE is '设备型号表';
comment on column DEV_TYPE_TABLE.NO is '编号';
comment on column DEV_TYPE_TABLE.NAME is '设备型号';
comment on column DEV_TYPE_TABLE.DEV_VENDOR is '所属品牌 来自设备品牌表';
comment on column DEV_TYPE_TABLE.DEV_CATALOG is '所属类型 来自设备类型表';
comment on column DEV_TYPE_TABLE.SPEC is '设备规格';
comment on column DEV_TYPE_TABLE.WEIGHT is '设备重量';
comment on column DEV_TYPE_TABLE.WATT is '平均功率';
comment on column DEV_TYPE_TABLE.CASH_TYPE is '非现金标志 1、现金；2、非现金';


create table DEV_SERVICE_COMPANY
(
    NO      VARCHAR2(5)  not null
        constraint PK_DEV_SERVICE_COMPANY
            primary key,
    NAME    VARCHAR2(80) not null,
    LINKMAN VARCHAR2(30),
    ADDRESS VARCHAR2(80),
    PHONE   VARCHAR2(30),
    MOBILE  VARCHAR2(30),
    FAX     VARCHAR2(30),
    EMAIL   VARCHAR2(40),
    TYPE    NUMBER
);
comment on table DEV_SERVICE_COMPANY is '设备维护商表';
comment on column DEV_SERVICE_COMPANY.NO is '编号';
comment on column DEV_SERVICE_COMPANY.NAME is '服务商名称';
comment on column DEV_SERVICE_COMPANY.LINKMAN is '联系人';
comment on column DEV_SERVICE_COMPANY.ADDRESS is '地址';
comment on column DEV_SERVICE_COMPANY.PHONE is '固定电话';
comment on column DEV_SERVICE_COMPANY.MOBILE is '手机';
comment on column DEV_SERVICE_COMPANY.FAX is '传真';
comment on column DEV_SERVICE_COMPANY.EMAIL is '电子邮箱';
comment on column DEV_SERVICE_COMPANY.TYPE is '服务商类型';

alter table  DEV_SERVICE_COMPANY  add (TYPE  NUMBER);
comment on column  DEV_SERVICE_COMPANY.TYPE is  '服务商类型';

create table DEV_VENDOR_TABLE
(
    NO       VARCHAR2(5)  not null
        constraint PK_DEV_VENDOR_TABLE
            primary key,
    NAME     VARCHAR2(80) not null,
    ADDRESS  VARCHAR2(80),
    HOTLINE1 VARCHAR2(20),
    STATUS   VARCHAR2(2)
);
comment on table DEV_VENDOR_TABLE is '设备品牌表';
comment on column DEV_VENDOR_TABLE.NO is '编号';
comment on column DEV_VENDOR_TABLE.NAME is '品牌名称';
comment on column DEV_VENDOR_TABLE.ADDRESS is '生产商地址';
comment on column DEV_VENDOR_TABLE.HOTLINE1 is '生产商热线';
comment on column DEV_VENDOR_TABLE.STATUS is '生产商状态1 –设备供应 2 –设备服役 3 –设备退役';


create table DEV_REGION_TABLE
(
    NO   NUMBER       not null
        primary key,
    NAME VARCHAR2(20) not null
);


create table DEV_BASE_INFO
(
    NO                      VARCHAR2(20)                 not null
        constraint PK_DEV_BASE_INFO
            primary key,
    IP                      VARCHAR2(20)                 not null,
    ORG_NO                  VARCHAR2(20)                 not null,
    AWAY_FLAG               VARCHAR2(2)                  not null,
    DEV_TYPE                VARCHAR2(5)                  not null,
    WORK_TYPE               VARCHAR2(2)                  not null,
    STATUS                  VARCHAR2(2)                  not null,
    DEV_SERVICE             VARCHAR2(5)                  not null,
    ADDRESS                 VARCHAR2(200),
    AREA_NO                 VARCHAR2(10),
    X                       NUMBER(20, 10),
    Y                       NUMBER(20, 10),
    CASHBOX_LIMIT           VARCHAR2(50),
    VIRTUAL_TELLER_NO       VARCHAR2(10),
    SETUP_TYPE              NUMBER,
    NET_TYPE                VARCHAR2(1),
    CASSETTE_STANTARD_SIZE  NUMBER        default 250000 not null,
    DEV_STANTARD_SIZE       NUMBER        default 800000 not null,
    ADDNOTES_LINE           VARCHAR2(100),
    ADD_CLR_PERIOD          NUMBER        default 3      not null,
    MAX_ADD_CLR_PERIOD      NUMBER        default 3      not null,
    NOTE1                   VARCHAR2(30),
    NOTE2                   VARCHAR2(30),
    NOTE3                   VARCHAR2(30),
    NOTE4                   VARCHAR2(30),
    FEEDBACK                NUMBER(4),
    CYCLE_FLAG              NUMBER,
    CITY                    VARCHAR2(30),
    REGION                  VARCHAR2(30),
    CLR_CENTER_NO           VARCHAR2(20),
    TAG_TID                 VARCHAR2(50),
    GROUP_NO                VARCHAR2(100),
    PROVINCE                VARCHAR2(40)  default null,
    ACCOUNT_NETPOINT        VARCHAR2(30),
    DEV_TYPE_CASH           VARCHAR2(30),
    NOTE5                   VARCHAR2(30),
    DISTRIBUTION_AREA       NUMBER,
    REG_LOCATION            NUMBER
        constraint FK_REGION_TABLE
            references DEV_REGION_TABLE,
    TOWNANDCOUNTRY_FALG     VARCHAR2(100) default null,
    BELONG_REGION           VARCHAR2(100) default null,
    COOPERATIVE_ENTERPRISE  VARCHAR2(100) default null,
    INDUSTRY                VARCHAR2(100) default null,
    HEAD_BANK_ADDNOTES_LINE VARCHAR2(100)
);
comment on table DEV_BASE_INFO is '设备基本信息';
comment on column DEV_BASE_INFO.NO is '设备号';
comment on column DEV_BASE_INFO.IP is '设备IP地址';
comment on column DEV_BASE_INFO.ORG_NO is '所属网点';
comment on column DEV_BASE_INFO.AWAY_FLAG is '离行&在行标志 1-在行  2-离行';
comment on column DEV_BASE_INFO.DEV_TYPE is '设备型号';
comment on column DEV_BASE_INFO.WORK_TYPE is '经营方式 1－自营  2－联营';
comment on column DEV_BASE_INFO.STATUS is '设备状态 1—正常  2—停机';
comment on column DEV_BASE_INFO.DEV_SERVICE is '设备维护商';
comment on column DEV_BASE_INFO.ADDRESS is '设备地址';
comment on column DEV_BASE_INFO.AREA_NO is '所属区域';
comment on column DEV_BASE_INFO.X is '横坐标（经度） 地图使用';
comment on column DEV_BASE_INFO.Y is '纬度';
comment on column DEV_BASE_INFO.CASHBOX_LIMIT is '钱箱报警金额(元)';
comment on column DEV_BASE_INFO.VIRTUAL_TELLER_NO is '虚拟柜员号';
comment on column DEV_BASE_INFO.SETUP_TYPE is '安装方式 0、穿墙；1、大堂';
comment on column DEV_BASE_INFO.NET_TYPE is '网络类型标志 C：CABLE有线  W：WIRELESS无线';
comment on column DEV_BASE_INFO.CASSETTE_STANTARD_SIZE is '钞箱标准容量 单位：张';
comment on column DEV_BASE_INFO.DEV_STANTARD_SIZE is '设备最大装钞量 单位：万元';
comment on column DEV_BASE_INFO.NOTE1 is '备选1';
comment on column DEV_BASE_INFO.NOTE2 is '备选2';
comment on column DEV_BASE_INFO.NOTE3 is '备选3';
comment on column DEV_BASE_INFO.NOTE4 is '备选4';
comment on column DEV_BASE_INFO.NOTE5 is '备选5';
comment on column DEV_BASE_INFO.FEEDBACK is '??';
comment on column DEV_BASE_INFO.CYCLE_FLAG is '是否开通循环(CRS设备)： 0-未开通 1-开通';
comment on column DEV_BASE_INFO.GROUP_NO is '根据算法计算的分组号';
comment on column DEV_BASE_INFO.ACCOUNT_NETPOINT is '账务网点';
comment on column DEV_BASE_INFO.DEV_TYPE_CASH is '设备类型（0:未分类，1：存大于取型，2：存取均衡型，3：取大于存型 4:取款机型）';
comment on column DEV_BASE_INFO.DISTRIBUTION_AREA is '布放区域：1-城市，2-郊县';
comment on column DEV_BASE_INFO.REG_LOCATION is '地理位置';
comment on column DEV_BASE_INFO.TOWNANDCOUNTRY_FALG is '城乡标志：1-城市，2-郊县';
comment on column DEV_BASE_INFO.BELONG_REGION is '所属区域';
comment on column DEV_BASE_INFO.COOPERATIVE_ENTERPRISE is '合作企业';
comment on column DEV_BASE_INFO.INDUSTRY is '所属行业';

create table CLR_CENTER_TABLE
(
    CLR_CENTER_NO              VARCHAR2(10)     not null
        primary key,
    CENTER_NAME                VARCHAR2(80)     not null,
    BANK_ORG_NO                VARCHAR2(20),
    NETPOINT_MATRIX_STATUS     NUMBER default 0 not null,
    CASHTRUCK_NUM              NUMBER default 0 not null,
    NOTE                       VARCHAR2(100),
    NETPOINT_MATRIX_STATUS_ORG NUMBER default 0 not null,
    COST_MATRIX_POINT_TYPE     NUMBER default 0
);
comment on table CLR_CENTER_TABLE is '清机中心表';
comment on column CLR_CENTER_TABLE.CLR_CENTER_NO is '编号';
comment on column CLR_CENTER_TABLE.CENTER_NAME is '清机中心名称';
comment on column CLR_CENTER_TABLE.BANK_ORG_NO is '银行机构编号';
comment on column CLR_CENTER_TABLE.NETPOINT_MATRIX_STATUS is '下辖设备路程矩阵状态位';
comment on column CLR_CENTER_TABLE.CASHTRUCK_NUM is '加钞车数目';
comment on column CLR_CENTER_TABLE.NETPOINT_MATRIX_STATUS_ORG is '下辖网点路程矩阵状态位';
comment on column CLR_CENTER_TABLE.COST_MATRIX_POINT_TYPE is '分组方式:0-设备分组 1-网点分组';


create table SYS_PARAM_CATALOG
(
    CATALOG      NUMBER       not null
        primary key,
    CATALOG_NAME VARCHAR2(40) not null,
    DESCRIPTION  VARCHAR2(100)
);
comment on table SYS_PARAM_CATALOG is '系统参数类别表';
comment on column SYS_PARAM_CATALOG.CATALOG is '类别';
comment on column SYS_PARAM_CATALOG.CATALOG_NAME is '类别名';
comment on column SYS_PARAM_CATALOG.DESCRIPTION is '描述';


create table SYS_PARAM
(
    LOGIC_ID    VARCHAR2(36) not null
        primary key,
    CATALOG     NUMBER       not null
        references SYS_PARAM_CATALOG,
    PARAM_NAME  VARCHAR2(80) not null,
    PARAM_VALUE VARCHAR2(256),
    STATEMENT   VARCHAR2(100),
    DESCRIPTION VARCHAR2(100)
);
comment on table SYS_PARAM is '系统参数表';
comment on column SYS_PARAM.LOGIC_ID is '逻辑主键';
comment on column SYS_PARAM.CATALOG is '参数类别：1－系统运行参数；2－定时任务参数；3－钞箱面值参数；4－钞箱类型参数；5－任务单相关参数；';
comment on column SYS_PARAM.PARAM_NAME is '参数名';
comment on column SYS_PARAM.PARAM_VALUE is '参数值';
comment on column SYS_PARAM.STATEMENT is '参数说明';
comment on column SYS_PARAM.DESCRIPTION is '描述';
create index FK_PRM_R_CLG on SYS_PARAM (CATALOG);
create unique index UK_SYSP_NAME on SYS_PARAM (PARAM_NAME);




;*==============================================================*;
;* Table: LINE_RUN_NET                                          *;
;*==============================================================*;
-- drop table LINE_RUN_NET cascade constraints;
create table LINE_RUN_NET
(
    LINE_RUN_NO    VARCHAR2(32) not null
        constraint PK_LINE_RUN_NET
            primary key,
    LINE_NO        VARCHAR2(32),
    NET_ACCOUNT    NUMBER,
    THE_YEAR_MONTH VARCHAR2(7),
    THE_DAY        VARCHAR2(2)
);

comment on table LINE_RUN_NET is
'网点线路运行图';

comment on column LINE_RUN_NET.LINE_RUN_NO is
'线路运行图编号';

comment on column LINE_RUN_NET.LINE_NO is
'线路编号';

comment on column LINE_RUN_NET.NET_ACCOUNT is
'网点数';

comment on column LINE_RUN_NET.THE_YEAR_MONTH is
'年月';

comment on column LINE_RUN_NET.THE_DAY is
'日';





;*==============================================================*;
;* Table: LINE_RUN_NET_DETAIL                                   *;
;*==============================================================*;
-- alter table LINE_RUN_NET_DETAIL
--    drop constraint FK_LRND_LRN_NO;
-- drop table LINE_RUN_NET_DETAIL cascade constraints;
create table LINE_RUN_NET_DETAIL
(
    LINE_RUN_NO VARCHAR2(32) not null
        constraint FK_LRND_LRN_NO
            references LINE_RUN_NET,
    ORG_NO      VARCHAR2(20) not null,
    SORT        NUMBER,
    constraint PK_LINE_RUN_NET_DETAIL
        primary key (LINE_RUN_NO, ORG_NO)
);

comment on table LINE_RUN_NET_DETAIL is
'网点线路运行图详情';

comment on column LINE_RUN_NET_DETAIL.LINE_RUN_NO is
'线路运行图编号';

comment on column LINE_RUN_NET_DETAIL.ORG_NO is
'网点号';

comment on column LINE_RUN_NET_DETAIL.SORT is
'顺序';

alter table LINE_RUN_NET_DETAIL
   add constraint FK_LRND_LRN_NO foreign key (LINE_RUN_NO)
      references LINE_RUN_NET (LINE_RUN_NO);

-- auto-generated definition
create table SHELF_TABLE
(
    SHELF_NO      VARCHAR2(32) not null
        constraint PK_SHELF_TABLE
            primary key,
    TYPE          NUMBER,
    STATUS        NUMBER,
    CLR_CENTER_NO VARCHAR2(20)
);

comment on table SHELF_TABLE is '笼车基础信息表';

comment on column SHELF_TABLE.SHELF_NO is '笼车编号';

comment on column SHELF_TABLE.TYPE is '类型';

comment on column SHELF_TABLE.STATUS is '状态 0-禁用 1-空闲 2-占用';

comment on column SHELF_TABLE.CLR_CENTER_NO is '金库编号';

-- drop table BASE_ENTITY_INFO;
-- auto-generated definition
create table BASE_ENTITY_INFO
(
    ENTITY_NO     VARCHAR2(32) not null
        constraint PK_BASE_ENTITY_INFO
            primary key,
    GOODS_NO      VARCHAR2(32)
        constraint FK_BEI_GBT_GNO
            references GOODS_BASE_TABLE,
    PARAMS        VARCHAR2(200),
    CUSTOMER_NO   VARCHAR2(32),
    CUSTOMER_TYPE NUMBER,
    CREATE_TIME   VARCHAR2(20),
    MODE_TIME     VARCHAR2(20),
    STATUS        NUMBER
);

comment on table BASE_ENTITY_INFO is '物品信息表';

comment on column BASE_ENTITY_INFO.ENTITY_NO is '物品编号';

comment on column BASE_ENTITY_INFO.GOODS_NO is '物品类型编号';

comment on column BASE_ENTITY_INFO.PARAMS is '属性json字符串';

comment on column BASE_ENTITY_INFO.CUSTOMER_NO is '所属对象编号';

comment on column BASE_ENTITY_INFO.CUSTOMER_TYPE is '所属对象类型';

comment on column BASE_ENTITY_INFO.CREATE_TIME is '创建时间';

comment on column BASE_ENTITY_INFO.MODE_TIME is '最后修改时间';

comment on column BASE_ENTITY_INFO.STATUS is '状态 0-未启用 1-启用';


-- auto-generated definition
create table JOB_INFO
(
    GROUP_TYPE NUMBER(4)    not null
        primary key,
    JOB_NAME   VARCHAR2(50) not null,
    JOB_TYPE   NUMBER(2),
    JOB_NUMBER NUMBER(22),
    NOTE       VARCHAR2(200),
    constraint POST_INFO_UNIQUE
        unique (JOB_NAME, JOB_TYPE)
);
comment on table JOB_INFO is '岗位信息息表';
comment on column JOB_INFO.GROUP_TYPE is '岗位编号';
comment on column JOB_INFO.JOB_NAME is '岗位名称';
comment on column JOB_INFO.JOB_TYPE is '岗位类别（1：金库 2:	网点）';
comment on column JOB_INFO.JOB_NUMBER is '岗位规模人数';
comment on column JOB_INFO.NOTE is '岗位信息备注';

create table CAR_INFO
(
    CAR_NO       NUMBER not null
        constraint PK_CAR_INFO
            primary key,
    TYPE         NUMBER,
    CAR_NUMBER   VARCHAR2(20),
    STATUS       NUMBER,
    COMPANY      VARCHAR2(200),
    SIGNING_TYPE NUMBER,
    MAX_DURATION VARCHAR2(10),
    MAX_MILEAGE  VARCHAR2(10)
);

comment on table CAR_INFO is
'车辆基础信息表';

comment on column CAR_INFO.CAR_NO is
'车辆编号：自增主键';

comment on column CAR_INFO.TYPE is
'类型--0：小型 1：中型 2：大型';

comment on column CAR_INFO.CAR_NUMBER is
'车牌';

comment on column CAR_INFO.STATUS is
'状态（0-故障；1-正常）';

comment on column CAR_INFO.COMPANY is
'公司';

comment on column CAR_INFO.SIGNING_TYPE is
'签约方式（0-计次；1-月付；2-里程付）';

comment on column CAR_INFO.MAX_DURATION is
'最大时间 单位小时';

comment on column CAR_INFO.MAX_MILEAGE is
'最大里程 单位千米';


create table OUTSOURCING_INFO
(
    NO             VARCHAR2(20) not null
        constraint PK_OUTSOURCING_INFO
            primary key,
    NAME           VARCHAR2(50) not null,
    POST           NUMBER,
    AGE            NUMBER,
    FAMILY_ADDR    VARCHAR2(200),
    RESIDENCE_ADDR VARCHAR2(200),
    MOBILE         VARCHAR2(30)
);

comment on table OUTSOURCING_INFO is
'外包人员信息表';

comment on column OUTSOURCING_INFO.NO is
'人员编号';

comment on column OUTSOURCING_INFO.NAME is
'人员姓名';

comment on column OUTSOURCING_INFO.POST is
'岗位（0-外包人员；1-安保人员；2-车辆驾驶员）';

comment on column OUTSOURCING_INFO.AGE is
'年龄';

comment on column OUTSOURCING_INFO.FAMILY_ADDR is
'家庭住址';

comment on column OUTSOURCING_INFO.RESIDENCE_ADDR is
'户籍地址';

comment on column OUTSOURCING_INFO.MOBILE is
'联系方式';


--上门收款线路排班
create table CALL_CUSTOMER_LINE_RUM
(
    LINE_RUN_NO         VARCHAR2(32) not null
        constraint PK_CALL_CUSTOMER_LINE_RUM_NO
            primary key,
    LINE_NO             VARCHAR2(32) not null,
    THE_YEAR_MONTH      VARCHAR2(7)  not null,
    THE_DAY             VARCHAR2(2)  not null,
    CALL_CUSTOMER_COUNT NUMBER       not null
);
comment on table CALL_CUSTOMER_LINE_RUM is '上门收款线路排班表';
comment on column CALL_CUSTOMER_LINE_RUM.LINE_RUN_NO is '编号';
comment on column CALL_CUSTOMER_LINE_RUM.LINE_NO is '线路号';
comment on column CALL_CUSTOMER_LINE_RUM.THE_YEAR_MONTH is '年月';
comment on column CALL_CUSTOMER_LINE_RUM.THE_DAY is '日';
comment on column CALL_CUSTOMER_LINE_RUM.CALL_CUSTOMER_COUNT is '客户数';


--押运人员排班
create table CALL_CUSTOMER_LINE_RUN_DETAIL
(
    CUSTOMER_NO  VARCHAR2(32) not null,
    LINE_RUN_NO  VARCHAR2(32) not null
        constraint FK_CLLRD_CLLR_NO
            references CALL_CUSTOMER_LINE_RUM(LINE_RUN_NO),
    ARRIVAL_TIME VARCHAR2(8)  not null,
    CLR_TIME_INTERVAL VARCHAR2(1)
);
comment on table CALL_CUSTOMER_LINE_RUN_DETAIL is '上门收款线路排班详情表';
comment on column CALL_CUSTOMER_LINE_RUN_DETAIL.CUSTOMER_NO is '客户编号';
comment on column CALL_CUSTOMER_LINE_RUN_DETAIL.LINE_RUN_NO is '线路排班记录编号';
comment on column CALL_CUSTOMER_LINE_RUN_DETAIL.ARRIVAL_TIME is '要求到达网点时间';
comment on column CALL_CUSTOMER_LINE_RUN_DETAIL.CLR_TIME_INTERVAL is '时间段 1-上午 2-下午';
create unique index UNQ_CCLRD_CN_LN
    on CALL_CUSTOMER_LINE_RUN_DETAIL (CUSTOMER_NO, LINE_RUN_NO);
alter table CALL_CUSTOMER_LINE_RUN_DETAIL
    add constraint PK_CCLRD_CN_LN
        primary key (CUSTOMER_NO, LINE_RUN_NO);

create table OUTSOURCING_LINE_MAP(
    LINE_RUN_NO VARCHAR(32)
        constraint  PK_OLM_NO primary key,
    DUTY_DATE VARCHAR2(10) not null ,
    LINE_NO VARCHAR2(32) not null,
    CAR_NO INTEGER
);
comment on table OUTSOURCING_LINE_MAP is '押运人员排班表';
comment on column OUTSOURCING_LINE_MAP.LINE_RUN_NO is '排班记录编号';
comment on column OUTSOURCING_LINE_MAP.DUTY_DATE is '值班日期';
comment on column OUTSOURCING_LINE_MAP.LINE_NO is '押运线路编号';
comment on column OUTSOURCING_LINE_MAP.CAR_NO is '押运车辆编号';


create table OUTSOURCING_LINE_MAP_DETAIL(
    LINE_RUN_NO VARCHAR(32)
        CONSTRAINT FK_OLMD_OLM_NO references OUTSOURCING_LINE_MAP(line_run_no),
     OUTSOURCING_OP_NO VARCHAR2(20)
        constraint FK_OLMD_OI_NO references OUTSOURCING_INFO(NO),
    constraint PK_PLMD_LNO_OPNO PRIMARY KEY (LINE_RUN_NO,OUTSOURCING_OP_NO)
);
comment on table OUTSOURCING_LINE_MAP_DETAIL is '押运人员排班详情表';
comment on column OUTSOURCING_LINE_MAP_DETAIL.LINE_RUN_NO is '排班记录编号';
comment on column OUTSOURCING_LINE_MAP_DETAIL.OUTSOURCING_OP_NO is '值班人员';

create table CALL_CUSTOMER_TABLE
(
    CUSTOMER_NO          VARCHAR2(36) not null
        constraint PK_CALL_CUSTOMER_TABLE
            primary key,
    CUSTOMER_SHORT_NAME  VARCHAR2(200),
    ADDRESS              VARCHAR2(200),
    LOCATION             VARCHAR2(200),
    X                    VARCHAR2(50),
    Y                    VARCHAR2(50),
    ISONESELF            VARCHAR2(20),
    CUSTOMER_NUMBER      VARCHAR2(32),
    CNCUSTOMER_LONG_NAME VARCHAR2(500),
    CUSTOMER_AUTH_PHONE  VARCHAR2(20),
    CUSTOMER_MANAGE      VARCHAR2(200),
    TOUCH_PHONE_ONE      VARCHAR2(20),
    TOUCH_PHONE_TWO      VARCHAR2(20),
    CALL_CUSTOMER_LINE   VARCHAR2(20),
    CALL_CLR_PERIOD      NUMBER(2),
    CALL_CUSTOMER_TYPE   VARCHAR2(5)
);

comment on table CALL_CUSTOMER_TABLE is '上门客户信息表';

comment on column CALL_CUSTOMER_TABLE.CUSTOMER_NO is '客户编号';

comment on column CALL_CUSTOMER_TABLE.CUSTOMER_SHORT_NAME is '客户简称';

comment on column CALL_CUSTOMER_TABLE.ADDRESS is '地址';

comment on column CALL_CUSTOMER_TABLE.LOCATION is '区域';

comment on column CALL_CUSTOMER_TABLE.X is '经度';

comment on column CALL_CUSTOMER_TABLE.Y is '纬度';

comment on column CALL_CUSTOMER_TABLE.ISONESELF is '是否为本行客户 1：是；0：不是';

comment on column CALL_CUSTOMER_TABLE.CUSTOMER_NUMBER is '客户号';

comment on column CALL_CUSTOMER_TABLE.CNCUSTOMER_LONG_NAME is 'CN客户全称';

comment on column CALL_CUSTOMER_TABLE.CUSTOMER_AUTH_PHONE is '客户身份认证电话';

comment on column CALL_CUSTOMER_TABLE.CUSTOMER_MANAGE is '客户经理';

comment on column CALL_CUSTOMER_TABLE.TOUCH_PHONE_ONE is '联系电话1';

comment on column CALL_CUSTOMER_TABLE.TOUCH_PHONE_TWO is '联系电话2';

comment on column CALL_CUSTOMER_TABLE.CALL_CUSTOMER_LINE is '上门线路';

comment on column CALL_CUSTOMER_TABLE.CALL_CLR_PERIOD is '收款周期';

comment on column CALL_CUSTOMER_TABLE.CALL_CUSTOMER_TYPE is '客户类型'

create table CALL_CUSTOMER_DATE_TABLE
(
    CUSTOMER_NO        VARCHAR2(32),
    CLR_TIME_INTERVAL  VARCHAR2(1),
    CLR_DAY            VARCHAR2(2),
    ARRIVAL_TIME       VARCHAR2(8),
    CALL_CUSTOMER_LINE VARCHAR2(20)
);

comment on table CALL_CUSTOMER_DATE_TABLE is '上门客户日期表';

comment on column CALL_CUSTOMER_DATE_TABLE.CUSTOMER_NO is '客户编号';

comment on column CALL_CUSTOMER_DATE_TABLE.CLR_TIME_INTERVAL is '时段（1：上午；2：下午）';

comment on column CALL_CUSTOMER_DATE_TABLE.CLR_DAY is '天数序号';

comment on column CALL_CUSTOMER_DATE_TABLE.ARRIVAL_TIME is '到达时间';

comment on column CALL_CUSTOMER_DATE_TABLE.CALL_CUSTOMER_LINE is '上门线路';

create table BARCODE_INFO
(
    BARCODE_NO   VARCHAR2(32) not null
        constraint PK_BARCODE
            primary key,
    BARCODE_TYPE NUMBER       not null,
    STATUS       NUMBER       not null,
    NOTE         VARCHAR2(100)
);

comment on table BARCODE_INFO is '条形码信息表';
comment on column BARCODE_INFO.BARCODE_NO is '条形码编号';
comment on column BARCODE_INFO.BARCODE_TYPE is '条形码类型--1：钞箱条形码；2：钞箱袋条形码；3：设备条形码';
comment on column BARCODE_INFO.STATUS is '状态--0：未启用；1；启用；2：停用';
comment on column BARCODE_INFO.NOTE is '备注';

create table CASSETTE_BAG_INFO
(
    BAG_NO      VARCHAR2(32)     not null
        constraint PK_CAS_BAG
            primary key,
    BAG_NO_BANK VARCHAR2(32),
    BAG_SERIAL  VARCHAR2(32),
    TAG_TID     VARCHAR2(50)     not null,
    BAG_VENDOR  NUMBER default 0 not null,
    BAG_SIZE    NUMBER           not null,
    STATUS      NUMBER           not null
);
comment on table CASSETTE_BAG_INFO is '钞箱袋信息表';
comment on column CASSETTE_BAG_INFO.BAG_NO is '钞箱袋管理编号';
comment on column CASSETTE_BAG_INFO.BAG_NO_BANK is '钞箱袋银行编号';
comment on column CASSETTE_BAG_INFO.BAG_SERIAL is '钞箱袋序列号';
comment on column CASSETTE_BAG_INFO.TAG_TID is '标签编号';
comment on column CASSETTE_BAG_INFO.BAG_VENDOR is '钞箱袋品牌：0：默认';
comment on column CASSETTE_BAG_INFO.BAG_SIZE is '钞箱袋规格:最多容纳钞箱的个数';
comment on column CASSETTE_BAG_INFO.STATUS is '状态：-1:未启用；0：启用';

create table CASSETTE_INFO
(
    CASSETTE_NO           VARCHAR2(32) not null
        constraint PK_CAS
            primary key,
    CASSETTE_NO_BANK      VARCHAR2(32),
    CASSETTE_SERIAL       VARCHAR2(32),
    TAG_TID               VARCHAR2(50) not null,
    CASSETTE_TYPE         NUMBER       not null,
    CASSETTE_NOTE_VALUE   NUMBER       not null,
    CASSETTE_CURRENCY     VARCHAR2(5)  not null,
    CASSETTE_MAX_NOTESNUM NUMBER       not null,
    CASSETTE_VENDOR       NUMBER       not null,
    STATUS                NUMBER       not null
);
comment on table CASSETTE_INFO is '钞箱信息表';
comment on column CASSETTE_INFO.CASSETTE_NO is '钞箱管理号';
comment on column CASSETTE_INFO.CASSETTE_NO_BANK is '银行钞箱编号';
comment on column CASSETTE_INFO.CASSETTE_SERIAL is '钞箱序列号';
comment on column CASSETTE_INFO.TAG_TID is '标签编号';
comment on column CASSETTE_INFO.CASSETTE_TYPE is '钞箱类型：0：循环箱；1：回收箱；2：废钞箱；3：存款箱；4：取款箱；';
comment on column CASSETTE_INFO.CASSETTE_NOTE_VALUE is '钞箱面值：0：100；1：50；2：20；3：10；4：5；';
comment on column CASSETTE_INFO.CASSETTE_CURRENCY is '钞箱币种';
comment on column CASSETTE_INFO.CASSETTE_MAX_NOTESNUM is '钞箱最大张数';
comment on column CASSETTE_INFO.CASSETTE_VENDOR is '钞箱品牌';
comment on column CASSETTE_INFO.STATUS is '状态：-1:未启用；0：启用；1：已装填；20：出库待审核；2：已出库;已交接；30：虚拟签收待审核；3：已虚拟签收；4：已落地；50：虚拟回收待审核；5：已虚拟回收；60：入库待审核；6：已入库;已交接；7：纸币已回收；8：损坏；9：维修；10：报废；';

create table CITY_INFO
(
    CITY_NO        VARCHAR2(20) not null
        primary key,
    CITY_NAME      VARCHAR2(32) not null,
    PROVINCE       VARCHAR2(32) not null,
    WEATHER_ID     VARCHAR2(20) not null,
    CLR_CENTER_NUM NUMBER(2)    not null,
    NOTE           VARCHAR2(200)
);
comment on table CITY_INFO is '城市业务表';
comment on column CITY_INFO.CITY_NO is '城市编号';
comment on column CITY_INFO.CITY_NAME is '城市名称';
comment on column CITY_INFO.PROVINCE is '省份';
comment on column CITY_INFO.WEATHER_ID is '获取天气对应的ID';
comment on column CITY_INFO.CLR_CENTER_NUM is '金库个数';
comment on column CITY_INFO.NOTE is '备注';

create table CITY_WEATHER
(
    CITY_ID        VARCHAR2(10) not null,
    CITY_NAME      VARCHAR2(10),
    WEATHER_DATE   VARCHAR2(10) not null,
    DAY_WEATHER_ID NUMBER(10),
    DAY_WEATHER    VARCHAR2(20),
    DAY_TEMP       NUMBER(10),
    DAY_WIND       VARCHAR2(20),
    DAY_WIND_COMP  VARCHAR2(20),
    primary key (CITY_ID, WEATHER_DATE)
);
comment on table CITY_WEATHER is '天气信息表';
comment on column CITY_WEATHER.CITY_ID is '城市编号';
comment on column CITY_WEATHER.CITY_NAME is '城市名称';
comment on column CITY_WEATHER.WEATHER_DATE is '日期';
comment on column CITY_WEATHER.DAY_WEATHER_ID is '天气编号';
comment on column CITY_WEATHER.DAY_WEATHER is '天气类型';
comment on column CITY_WEATHER.DAY_TEMP is '温度';
comment on column CITY_WEATHER.DAY_WIND is '风向';
comment on column CITY_WEATHER.DAY_WIND_COMP is '风级';

create table ESB_TRANS_TABLE
(
    SERIALNO     VARCHAR2(32) not null
        constraint PK_ESB_TRANS_TABLE
            primary key,
    TRANDATE     VARCHAR2(10),
    TRANTIME     VARCHAR2(8),
    RETCODESYSID VARCHAR2(10),
    RETCODE      VARCHAR2(10),
    RETMSG       VARCHAR2(32),
    DEVNO        VARCHAR2(32),
    LOCKCODE     VARCHAR2(20),
    OPNO         VARCHAR2(20),
    DEVORG       VARCHAR2(20)
);

create table KEY_GROUP_INFO
(
    KEY_GROUP_NO   VARCHAR2(32) not null
        constraint PK_KEY_GROUP
            primary key,
    KEY_GROUP_DESP VARCHAR2(100),
    CLR_CENTER_NO  VARCHAR2(20) not null,
    STATUS         NUMBER       not null,
    NOTE           VARCHAR2(100)
);

create table KEY_INFO
(
    KEY_NO        VARCHAR2(32)     not null
        constraint PK_KEY
            primary key,
    KEY_DESP      VARCHAR2(100),
    CLR_CENTER_NO VARCHAR2(20)     not null,
    USE_RANGE     NUMBER           not null,
    KEY_TYPE      VARCHAR2(32)     not null,
    KEY_GROUP_NO  VARCHAR2(32),
    NET_POINT_NO  VARCHAR2(20),
    DEV_NO        VARCHAR2(32),
    KEY_PROPERTY  NUMBER default 0 not null,
    STATUS        NUMBER           not null,
    NOTE          VARCHAR2(100)
);

create table KEY_TYPE_INFO
(
    CLR_CENTER_NO VARCHAR2(20) not null,
    KEY_USE_RANGE NUMBER       not null,
    KEY_TYPE_NO   VARCHAR2(32) not null
        constraint PK_KEY_TYPE
            primary key,
    KEY_TYPE_NAME VARCHAR2(40) not null,
    NOTE          VARCHAR2(100)
);

create table KEY_USE_DETAIL
(
    KEY_NO             VARCHAR2(32) not null
        constraint FK_KEY_USE_DTL_1
            references KEY_INFO,
    KEY_USE_NO         VARCHAR2(32) not null
        constraint FK_KEY_USE_DTL_2
            references KEY_USE_INFO,
    KEY_GROUP_NO       VARCHAR2(32),
    RETURN_OP_NO       VARCHAR2(20),
    RETURN_RCV_NO      VARCHAR2(20),
    RETURN_DATE        VARCHAR2(10),
    RETURN_TIME        VARCHAR2(8),
    LATE_RETURN_REASON VARCHAR2(100),
    NOTE               VARCHAR2(100),
    constraint PK_KEY_USE_DTL
        primary key (KEY_NO, KEY_USE_NO)
);

create table KEY_USE_INFO
(
    KEY_USE_NO         VARCHAR2(32)     not null
        constraint PK_KEY_USE
            primary key,
    DISTRIBUTE_OP_NO   VARCHAR2(20)     not null,
    DISTRIBUTE_DATE    VARCHAR2(10)     not null,
    DISTRIBUTE_TIME    VARCHAR2(8)      not null,
    REQUEST_OP_NO      VARCHAR2(20),
    REQUEST_DATE       VARCHAR2(10),
    REQUEST_TIME       VARCHAR2(8),
    REJECT_REASON      VARCHAR2(100),
    GRANT_OP_NO        VARCHAR2(20),
    GRANT_DATE         VARCHAR2(10),
    GRANT_TIME         VARCHAR2(8),
    IS_REPLACE_OP      NUMBER default 0 not null,
    REPLACE_OP_NO      VARCHAR2(20),
    RETURN_OP_NO       VARCHAR2(20),
    RETURN_RCV_NO      VARCHAR2(20),
    RETURN_DATE        VARCHAR2(10),
    RETURN_TIME        VARCHAR2(8),
    LATE_RETURN_REASON VARCHAR2(100),
    KEY_USE_STATUS     NUMBER           not null,
    CRASH_FLAG         NUMBER default 0 not null,
    NOTE               VARCHAR2(100)
);

create table LOCK_BASE_INFO
(
    LOCKCODE    VARCHAR2(20) not null
        constraint PK_LOCK_INFO
            primary key,
    DEVNO       VARCHAR2(32),
    VERSION     VARCHAR2(20),
    CVERSION    VARCHAR2(20),
    STATE       NUMBER,
    MADEDATE    VARCHAR2(10),
    INSTALLDATE VARCHAR2(10),
    NOTE        VARCHAR2(50),
    BLOCKNUM    VARCHAR2(50)
);

create table LOCK_STATUS_TABLE
(
    LOCKCODE   VARCHAR2(20) not null
        constraint PK_LOCK_STATUS
            primary key,
    STATUS     VARCHAR2(20),
    UPDATETIME VARCHAR2(19)
);

create table LOCK_TRANS_TABLE
(
    SERIALNO    VARCHAR2(32) not null
        constraint PK_LOCK_TRANS
            primary key,
    DEVNO       VARCHAR2(32),
    LOCKCODE    VARCHAR2(20),
    TRANDATE    VARCHAR2(10),
    TRANTIME    VARCHAR2(8),
    TRANTYPE    NUMBER,
    ENCRYPTCODE VARCHAR2(10),
    ESBCODE     VARCHAR2(10),
    RETCODE     VARCHAR2(10),
    RETMSG      VARCHAR2(32)
);

create table OP_TABLE
(
    NO                VARCHAR2(20)     not null
        constraint PK_OP
            primary key,
    WORK_NO           VARCHAR2(10)     not null,
    PASSWD            VARCHAR2(50)     not null,
    NAME              VARCHAR2(20)     not null,
    STATUS            NUMBER           not null,
    ONLINE_FLAG       NUMBER           not null,
    ROLE_NO           NUMBER           not null
        constraint FK_OP_R_ROLE
            references SYS_ROLE,
    ORG_NO            VARCHAR2(20)     not null,
    PHONE             VARCHAR2(20),
    MOBILE            VARCHAR2(20),
    EMAIL             VARCHAR2(40),
    PHOTO             VARCHAR2(50),
    LOGIN_IP          VARCHAR2(20),
    LOGIN_TIME        VARCHAR2(20),
    PASSWD_EXPIRATION VARCHAR2(10),
    PASSWD_ERROR      NUMBER default 0 not null,
    SIGN_FLAG         NUMBER default 0,
    TAG_READER_NO     VARCHAR2(32),
    DEK               VARCHAR2(48),
    DEKDEC            VARCHAR2(48),
    MACKEY            VARCHAR2(48),
    MACKEYDEC         VARCHAR2(48),
    TMK               VARCHAR2(48),
    GROUP_TYPE        NUMBER           not null,
    DUTY              NUMBER           not null,
    WORK_STATUS       NUMBER default 1 not null
);
comment on table OP_TABLE is '人员表';
comment on column OP_TABLE.NO is '编号';
comment on column OP_TABLE.WORK_NO is '工号';
comment on column OP_TABLE.PASSWD is '设备类型';
comment on column OP_TABLE.NAME is '操作员姓名';
comment on column OP_TABLE.STATUS is '操作员状态：0：停用；1：启用；2：锁定';
comment on column OP_TABLE.ONLINE_FLAG is '在线状态：0：离线；1：在线';
comment on column OP_TABLE.ROLE_NO is '操作员角色';
comment on column OP_TABLE.ORG_NO is '所属机构';
comment on column OP_TABLE.PHONE is '办公电话';
comment on column OP_TABLE.MOBILE is '手机号码';
comment on column OP_TABLE.EMAIL is 'EMAIL';
comment on column OP_TABLE.PHOTO is '照片';
comment on column OP_TABLE.LOGIN_IP is '登录IP地址';
comment on column OP_TABLE.LOGIN_TIME is '登录时间';
comment on column OP_TABLE.PASSWD_EXPIRATION is '密码有效期：默认为1年';
comment on column OP_TABLE.PASSWD_ERROR is '密码错误次数：最大5次';
comment on column OP_TABLE.SIGN_FLAG is '签到标志：用户使用读写器对应的签到签退标志：0，签退；1，签到';
comment on column OP_TABLE.TAG_READER_NO is '读写器编号：用户使用的读写器编号，只有用户签到标志为签到（SIGN_FLAG=1）时读写器编号有效；';
comment on column OP_TABLE.DEK is 'DEK';
comment on column OP_TABLE.DEKDEC is 'DEKDEC';
comment on column OP_TABLE.MACKEY is 'MACKEY';
comment on column OP_TABLE.MACKEYDEC is 'MACKEYDEC';
comment on column OP_TABLE.TMK is 'web客户端主密钥';


create table PROJECT_PARAM_TABLE
(
    PROJECT_NO  VARCHAR2(10) not null,
    PARAM_NAME  VARCHAR2(80) not null,
    PARAM_VALUE VARCHAR2(80) not null,
    STATEMENT   VARCHAR2(100),
    DESCRIPTION VARCHAR2(100),
    EDIT_FLAG   NUMBER       not null,
    constraint PK_PRJ_PRM
        primary key (PROJECT_NO, PARAM_NAME)
);
comment on table PROJECT_PARAM_TABLE is '清机外包项目参数表';
comment on column PROJECT_PARAM_TABLE.PROJECT_NO is '项目编号';
comment on column PROJECT_PARAM_TABLE.PARAM_NAME is '参数名';
comment on column PROJECT_PARAM_TABLE.PARAM_VALUE is '参数值';
comment on column PROJECT_PARAM_TABLE.STATEMENT is '参数说明';
comment on column PROJECT_PARAM_TABLE.DESCRIPTION is '描述';
comment on column PROJECT_PARAM_TABLE.EDIT_FLAG is '0-不可修改，不可删除；1-可修改，不可删除；2-不可修改，可删除；3-可修改，可删除';


create table TAG_INFO
(
    TAG_TID              VARCHAR2(50)                  not null
        constraint PK_TAG
            primary key,
    EPC_INFO             VARCHAR2(32),
    EPC_MEMORY_SIZE      NUMBER,
    TAG_TYPE             NUMBER                        not null,
    STATUS               NUMBER                        not null,
    USERDATA_MEMORY_SIZE NUMBER,
    NOTE                 VARCHAR2(100),
    CLR_CENTER_NO        VARCHAR2(20) default '010211' not null
);
comment on table TAG_INFO is '电子标签信息表';
comment on column TAG_INFO.TAG_TID is '标签编号';
comment on column TAG_INFO.EPC_INFO is 'EPC信息';
comment on column TAG_INFO.EPC_MEMORY_SIZE is 'EPC存储容量';
comment on column TAG_INFO.TAG_TYPE is '标签类型：1：钞箱标签；2：钞箱袋标签；3：设备标签';
comment on column TAG_INFO.STATUS is '状态：0-未启用;1-启用;2-停用';
comment on column TAG_INFO.USERDATA_MEMORY_SIZE is '用户区存储容量';
comment on column TAG_INFO.NOTE is '备注';

create table TAG_READER_COORDS_INFO
(
    TAG_READER_NO VARCHAR2(40)     not null,
    RD_DATE       VARCHAR2(10)     not null,
    RD_TIME       VARCHAR2(8)      not null,
    USER_NO       VARCHAR2(20)     not null,
    X             NUMBER(20, 10)   not null,
    Y             NUMBER(20, 10)   not null,
    COORDS_SRC    NUMBER default 1 not null,
    TASK_TYPE     NUMBER           not null,
    TASK_NO       VARCHAR2(32),
    DEV_NO        VARCHAR2(32),
    TRANS_CODE    VARCHAR2(8),
    constraint TAG_READER_COORDS_INFO_PK
        primary key (TAG_READER_NO, RD_DATE, RD_TIME, TASK_TYPE)
);

create table TAG_READER_INFO
(
	TAG_READER_NO VARCHAR2(40) not null
		constraint PK_READER
			primary key,
	READER_TYPE NUMBER not null,
	LOCATION VARCHAR2(100),
	WHETHER_GPS_MODULE CHAR,
	WHETHER_GPRS_MODULE CHAR,
	WHETHER_WIFI_MODULE CHAR,
	WHETHER_BARCODE_MODULE CHAR,
	GPRS_VOL_THRESHOLD NUMBER,
	GPRS_VOL_MAX_THRESHOLD NUMBER,
	GPRS_VOL_MIN_THRESHOLD NUMBER,
	GPRS_VOL_OFFSET NUMBER,
	TIMING_TASK_INTERVAL NUMBER,
	GPRS_MONTHLY_FREE_FLOW NUMBER,
	TMK VARCHAR2(48),
	NOTE VARCHAR2(100),
	STATUS NUMBER not null,
	CLR_CENTER_NO VARCHAR2(20) default '010211' not null,
	SIM_MOBILE_NO VARCHAR2(20)
);
comment on table TAG_READER_INFO is '手持机信息表';
comment on column TAG_READER_INFO.TAG_READER_NO is '标签读写器编号';
comment on column TAG_READER_INFO.READER_TYPE is '读写器类型：1-固定式；2-手持式；3-发卡器';
comment on column TAG_READER_INFO.LOCATION is '安放地址';
comment on column TAG_READER_INFO.WHETHER_GPS_MODULE is '是否有GPS模块';
comment on column TAG_READER_INFO.WHETHER_GPRS_MODULE is '是否有GPRS模块';
comment on column TAG_READER_INFO.WHETHER_WIFI_MODULE is '是否有WIFI模块';
comment on column TAG_READER_INFO.WHETHER_BARCODE_MODULE is '是否有条码模块';
comment on column TAG_READER_INFO.GPRS_VOL_THRESHOLD is 'GPRS信息量阈值';
comment on column TAG_READER_INFO.GPRS_VOL_MAX_THRESHOLD is 'GPRS信息量阈值最大值';
comment on column TAG_READER_INFO.GPRS_VOL_MIN_THRESHOLD is 'GPRS信息量阈值最小值';
comment on column TAG_READER_INFO.GPRS_VOL_OFFSET is 'GPRS信息量下浮偏移量';
comment on column TAG_READER_INFO.TIMING_TASK_INTERVAL is '定时任务时间间隔(分钟)';
comment on column TAG_READER_INFO.GPRS_MONTHLY_FREE_FLOW is 'GPRS包月流量(MB)';
comment on column TAG_READER_INFO.TMK is '设备主密钥';
comment on column TAG_READER_INFO.STATUS is '状态---1：未领用；2：已申请；3：已领用；4：已遗失；5：已损坏；';


create table TAG_READER_USE_INFO
(
    TAGREADER_USE_NO     VARCHAR2(32) not null
        constraint PK_READER_USE
            primary key,
    TAG_READER_NO        VARCHAR2(40) not null,
    REQUEST_OP_NO        VARCHAR2(20) not null,
    REQUEST_DATE         VARCHAR2(10) not null,
    REQUEST_TIME         VARCHAR2(8)  not null,
    REVIEW_RESULT        NUMBER,
    REJECT_REASON        VARCHAR2(100),
    GRANT_OP_NO          VARCHAR2(20),
    GRANT_DATE           VARCHAR2(10),
    GRANT_TIME           VARCHAR2(8),
    SIGN_OP_NO           VARCHAR2(20),
    RETURN_OP_NO         VARCHAR2(20),
    RETURN_DATE          VARCHAR2(10),
    RETURN_TIME          VARCHAR2(8),
    CRASH_FLAG           CHAR         not null,
    TAGREADER_USE_STATUS NUMBER       not null
);
comment on table TAG_READER_USE_INFO is '读写器领用记录表';
comment on column TAG_READER_USE_INFO.TAGREADER_USE_NO is '编码规则：8位日期+6位顺序号';
comment on column TAG_READER_USE_INFO.TAG_READER_NO is '读写器编号';
comment on column TAG_READER_USE_INFO.REQUEST_OP_NO is '申请人员';
comment on column TAG_READER_USE_INFO.REQUEST_DATE is '申请日期';
comment on column TAG_READER_USE_INFO.REQUEST_TIME is '申请时间';
comment on column TAG_READER_USE_INFO.REVIEW_RESULT is '审核结果--1，通过；2，拒绝；';
comment on column TAG_READER_USE_INFO.REJECT_REASON is '拒绝原因';
comment on column TAG_READER_USE_INFO.GRANT_OP_NO is '发放人员';
comment on column TAG_READER_USE_INFO.GRANT_DATE is '发放日期';
comment on column TAG_READER_USE_INFO.GRANT_TIME is '发放时间';
comment on column TAG_READER_USE_INFO.SIGN_OP_NO is '签收人员';
comment on column TAG_READER_USE_INFO.RETURN_OP_NO is '归还人员';
comment on column TAG_READER_USE_INFO.RETURN_DATE is '归还日期';
comment on column TAG_READER_USE_INFO.RETURN_TIME is '归还时间';
comment on column TAG_READER_USE_INFO.CRASH_FLAG is '紧急领用标志';
comment on column TAG_READER_USE_INFO.TAGREADER_USE_STATUS is '读写器领用状态--1：待审核；2：已审核（通过）；3：已审核（拒绝）；4：已归还；';


create table VISIT_ORDER
(
    ID                VARCHAR2(32) not null
        primary key,
    ORDER_DATE        VARCHAR2(32),
    ORDER_TIME_PERIOD NUMBER,
    ORDER_TIME        VARCHAR2(32),
    NOTE              VARCHAR2(500),
    STATUS            NUMBER
);
comment on column VISIT_ORDER.ID is '上门客户编号';
comment on column VISIT_ORDER.ORDER_DATE is '预约日期';
comment on column VISIT_ORDER.ORDER_TIME_PERIOD is '预约时间段 ：1-上午 2-下午';
comment on column VISIT_ORDER.ORDER_TIME is '预约时间 ：8-18点的时间范围，粒度为小时';
comment on column VISIT_ORDER.NOTE is '备注：描述押运的物品类型及数量';
comment on column VISIT_ORDER.STATUS is '状态 ：0-待审核 1-金库人员已审核 2-审核不通过'

create table ARMORED_CAR_INFO
(
    CAR_NUMBER VARCHAR2(20) not null
        constraint PK_CAR
            primary key,
    CAR_TYPE   NUMBER,
    TAG_TID    VARCHAR2(50),
    STATUS     NUMBER       not null
)
comment on table ARMORED_CAR_INFO is '运钞车信息表';
comment on column ARMORED_CAR_INFO.CAR_NUMBER is '车牌编号';
comment on column ARMORED_CAR_INFO.CAR_TYPE is '车辆类型';
comment on column ARMORED_CAR_INFO.TAG_TID is '标签编号';
comment on column ARMORED_CAR_INFO.STATUS is '状态';

create table CALL_CUSTOMER_TYPE
(
    NO   VARCHAR2(5)  not null
        constraint PK_CALL_CUSTOMER_TYPE
            primary key,
    NAME VARCHAR2(60) not null
)

create table DEV_COUNT_INFO
(
    DEV_NO        VARCHAR2(50)  default NULL not null
        constraint PK_DEV_COUNT_INFO
            primary key,
    DEV_TYPE      NUMBER(2)                  not null,
    CLR_CENTER_NO VARCHAR2(50)  default NULL not null,
    DEV_STATUS    NUMBER(2)                  not null,
    DEV_MODEL     VARCHAR2(300) default NULL not null,
    DEV_STANDARDS VARCHAR2(300) default NULL not null,
    USER_DATE     VARCHAR2(50)  default NULL not null,
    LOCATION      VARCHAR2(300) default NULL,
    NOTE          VARCHAR2(300) default NULL,
    IP            VARCHAR2(50)  default NULL not null
)
comment on table DEV_COUNT_INFO is '清分设备信息表';
comment on column DEV_COUNT_INFO.DEV_NO is '设备编号';
comment on column DEV_COUNT_INFO.DEV_TYPE is '设备类别 1：清分机 2：点钞机';
comment on column DEV_COUNT_INFO.CLR_CENTER_NO is '清机中心编号';
comment on column DEV_COUNT_INFO.DEV_STATUS is '设备状态 1：启用 2：停用';
comment on column DEV_COUNT_INFO.DEV_MODEL is '设备型号';
comment on column DEV_COUNT_INFO.DEV_STANDARDS is '设备规格';
comment on column DEV_COUNT_INFO.USER_DATE is '投入使用日期 YYYY-MM-DD';
comment on column DEV_COUNT_INFO.LOCATION is '摆放位置';
comment on column DEV_COUNT_INFO.NOTE is '备注';
comment on column DEV_COUNT_INFO.IP is '设备ip';

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
)

create index FK_GIS_CREATOR
    on GIS_POINT_INFO (POINT_CREATOR);
create index FK_GIS_MOD_OP
    on GIS_POINT_INFO (LASTEST_MOD_OP);
create index FK_GIS_ORGNO
    on GIS_POINT_INFO (POINT_ORGNO);

create table PARKING_INFO
(
    PLATE_NO       VARCHAR2(32) not null,
    AREA_NO        VARCHAR2(32) not null,
    PARK_DATE      VARCHAR2(10) not null,
    PARK_TIME      VARCHAR2(20) not null,
    FLAG           NUMBER       not null,
    GATECAMERANAME VARCHAR2(32),
    ID             NUMBER(32)   not null
)

create table WD_TIME_TABLE
(
    NO                    VARCHAR2(100) not null,
    ARRIVE_TIME           VARCHAR2(20),
    HANDOVER_TIME         VARCHAR2(20),
    BUSINESS_TIME         VARCHAR2(100),
    HOLIDAY_BUSINESS_TIME VARCHAR2(100),
    ORG_TYPE              NUMBER
)

commit;





