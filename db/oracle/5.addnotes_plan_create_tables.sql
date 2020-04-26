
-----------------------------------------------------------------
--                     addnotes_plan 模块
-----------------------------------------------------------------

create table  ADDNOTES_DATA
(
  DEV_NO                   VARCHAR2(500),
  ADDNOTES_LINE            VARCHAR2(500),
  ADDNOTES_PATCH           VARCHAR2(500),
  ADDNOTES_MODE            VARCHAR2(500),
  ADDNOTES_PLAN_AMOUNT     NUMBER,
  ADDNOTES_PLAN_OP         VARCHAR2(500),
  CASH_OUT_ACCOUNTER       VARCHAR2(500),
  CASH_OUT_AUDITER         VARCHAR2(500),
  CASH_OUT_TIME            VARCHAR2(500),
  HOST_BALANCE             NUMBER,
  ADDNOTES_HOST_AMOUNT     NUMBER,
  ADDNOTES_DEV_AMOUNT      NUMBER,
  DEV_REAL_AMOUNT          NUMBER,
  DEV_BACK_AMOUNT          NUMBER,
  DEV_LSMONEY_AMOUNT       NUMBER,
  PROCESS_FALG             VARCHAR2(500),
  ADDNOTES_DEV_TIME        VARCHAR2(500),
  ATM_CHECK_AMOUNT         NUMBER,
  ATM_LSMONEY_AMOUNT       NUMBER,
  UNLOADMONEY_CHECK_FLAG   VARCHAR2(500),
  UNLOADMONEY_IN_ACCOUNTER VARCHAR2(500),
  UNLOADMONEY_IN_AUDIT     VARCHAR2(500),
  UNLOADMONEY_IN_TIME      VARCHAR2(500),
  STATUS                   VARCHAR2(500),
  EXCEP_PROCESS_FLAG       VARCHAR2(500),
  ORG_NO                   VARCHAR2(500),
  ADDRESS                  VARCHAR2(500),
  REPORT_DATE              VARCHAR2(500),
  ADD_CHECK_RESULT         NUMBER(1) default 0,
  BACK_CHECK_RESULT        NUMBER(1) default 0
);
comment on table ADDNOTES_DATA is '装卸钞原始数据';
comment on column ADDNOTES_DATA.DEV_NO is 'atm终端号';
comment on column ADDNOTES_DATA.ADDNOTES_LINE is '线路号';
comment on column ADDNOTES_DATA.ADDNOTES_PATCH is '加钞批次号';
comment on column ADDNOTES_DATA.ADDNOTES_MODE is '加钞模式';
comment on column ADDNOTES_DATA.ADDNOTES_PLAN_AMOUNT is '计划加钞金额元';
comment on column ADDNOTES_DATA.ADDNOTES_PLAN_OP is '计划编制人员';
comment on column ADDNOTES_DATA.CASH_OUT_ACCOUNTER is '现金调出记账员';
comment on column ADDNOTES_DATA.CASH_OUT_AUDITER is '现金调出授权人员';
comment on column ADDNOTES_DATA.CASH_OUT_TIME is '现金调出时间';
comment on column ADDNOTES_DATA.HOST_BALANCE is '主机加钞时点余额元';
comment on column ADDNOTES_DATA.ADDNOTES_HOST_AMOUNT is '主机加钞金额元';
comment on column ADDNOTES_DATA.ADDNOTES_DEV_AMOUNT is '端机加钞金额元';
comment on column ADDNOTES_DATA.DEV_REAL_AMOUNT is '端机实物清点金额元';
comment on column ADDNOTES_DATA.DEV_BACK_AMOUNT is '端机带回金额元';
comment on column ADDNOTES_DATA.DEV_LSMONEY_AMOUNT is '端机长短款金额元';
comment on column ADDNOTES_DATA.PROCESS_FALG is '差额处理标志';
comment on column ADDNOTES_DATA.ADDNOTES_DEV_TIME is '端机加钞时间';
comment on column ADDNOTES_DATA.ATM_CHECK_AMOUNT is 'ATM卸钞实物清点金额元';
comment on column ADDNOTES_DATA.ATM_LSMONEY_AMOUNT is 'ATM卸钞长短款金额元';
comment on column ADDNOTES_DATA.UNLOADMONEY_CHECK_FLAG is '卸钞差额处理标志';
comment on column ADDNOTES_DATA.UNLOADMONEY_IN_ACCOUNTER is '卸钞入库记账员';
comment on column ADDNOTES_DATA.UNLOADMONEY_IN_AUDIT is '卸钞入库授权人员';
comment on column ADDNOTES_DATA.UNLOADMONEY_IN_TIME is '卸钞入库时间';
comment on column ADDNOTES_DATA.STATUS is '状态';
comment on column ADDNOTES_DATA.EXCEP_PROCESS_FLAG is '异常处理标记';
comment on column ADDNOTES_DATA.ORG_NO is '摆放网点号';
comment on column ADDNOTES_DATA.ADDRESS is '机具摆放地点';
comment on column ADDNOTES_DATA.REPORT_DATE is '填报日期';
comment on column ADDNOTES_DATA.ADD_CHECK_RESULT is '装钞金额校验结果 0-正常 1-非整百  2-负数  3-非数字型 4.装钞金额超限';
comment on column ADDNOTES_DATA.BACK_CHECK_RESULT is '卸钞金额校验结果  0-正常 1-非整百  2-负数  3-非数字型 4.卸钞金额异常';
create unique index PK_ADDNOTES_DATA
  on ADDNOTES_DATA (DEV_NO, ADDNOTES_PATCH);


create table ADDNOTES_LINE_TABLE
(
    LINE_NO        VARCHAR2(32)     not null
        primary key,
    LINE_NAME      VARCHAR2(40)     not null,
    ADD_CLR_PERIOD NUMBER default 3 not null,
    NOTE           VARCHAR2(100),
    CLR_CENTER_NO  VARCHAR2(10)     not null
        constraint FK_CLR_CENTER_NO
            references CLR_CENTER_TABLE,
    LINE_TYPE      NUMBER default 0 not null,
    PLAN_IN_TIME   VARCHAR2(30),
    PLAN_OUT_TIME  VARCHAR2(30)

);
comment on column ADDNOTES_LINE_TABLE.LINE_NO is '线路编号';
comment on column ADDNOTES_LINE_TABLE.LINE_NAME is '描述';
comment on column ADDNOTES_LINE_TABLE.ADD_CLR_PERIOD is '加钞循环周期';
comment on column ADDNOTES_LINE_TABLE.LINE_TYPE is '线路类型 0-ATM加钞路线 1-网点加钞路线 2-上门收款 3-紧急加钞路线';
comment on column ADDNOTES_LINE_TABLE.PLAN_IN_TIME is '计划入库时间';
comment on column ADDNOTES_LINE_TABLE.PLAN_OUT_TIME is '计划出库时间';
comment on column ADDNOTES_LINE_TABLE.NOTE is '备注';


create table ADDNOTES_PLAN
(
    ADDNOTES_PLAN_NO   VARCHAR2(32)          not null
        primary key,
    CLR_CENTER_NO      VARCHAR2(20)          not null,
    PLAN_ADDNOTES_DATE VARCHAR2(10)          not null,
    PLAN_START_TIME    VARCHAR2(8) default '',
    LASTEST_END_TIME   VARCHAR2(8),
    PLAN_DEV_COUNT     NUMBER                not null,
    PLAN_ADDNOTES_AMT  NUMBER                not null,
    PLAN_GEN_MODE      NUMBER                not null,
    PLAN_GEN_OP_NO     VARCHAR2(20),
    PLAN_GEN_DATE      VARCHAR2(10)          not null,
    PLAN_GEN_TIME      VARCHAR2(8)           not null,
    STATUS             NUMBER      default 0 not null,
    SUBMIT_OP_NO       VARCHAR2(20),
    SUBMIT_DATE        VARCHAR2(10),
    SUBMIT_TIME        VARCHAR2(8),
    MOD_OP_NO          VARCHAR2(20),
    MOD_DATE           VARCHAR2(10),
    MOD_TIME           VARCHAR2(8),
    LINE_MODE          NUMBER,
    LINE_NO            VARCHAR2(1000),
    AUDIT_OP_NO        VARCHAR2(20),
    AUDIT_DATE         VARCHAR2(10),
    AUDIT_TIME         VARCHAR2(8),
    REFUSE_SUGGESTION  VARCHAR2(1024),
    NOTE               VARCHAR2(100),
    IS_URGENCY         NUMBER      default 0
);
comment on table ADDNOTES_PLAN is '加钞计划表';
comment on column ADDNOTES_PLAN.ADDNOTES_PLAN_NO is '计划编号';
comment on column ADDNOTES_PLAN.CLR_CENTER_NO is '金库编号';
comment on column ADDNOTES_PLAN.PLAN_ADDNOTES_DATE is '计划加钞日期';
comment on column ADDNOTES_PLAN.PLAN_START_TIME is '计划出发时间';
comment on column ADDNOTES_PLAN.LASTEST_END_TIME is '最迟完成时间';
comment on column ADDNOTES_PLAN.PLAN_DEV_COUNT is '设备台数';
comment on column ADDNOTES_PLAN.PLAN_ADDNOTES_AMT is '计划加钞金额';
comment on column ADDNOTES_PLAN.PLAN_GEN_MODE is '计划生成方式1-自动2-人工';
comment on column ADDNOTES_PLAN.PLAN_GEN_OP_NO is '计划生成人';
comment on column ADDNOTES_PLAN.PLAN_GEN_DATE is '计划生成日期';
comment on column ADDNOTES_PLAN.PLAN_GEN_TIME is '计划生成时间';
comment on column ADDNOTES_PLAN.STATUS is '计划状态0-未选择设备1-已选择设备2-已预测加钞金额3-已分组4-待审批5-退回调整6-审批通过7-已出单8-已过期';
comment on column ADDNOTES_PLAN.SUBMIT_OP_NO is '提交人';
comment on column ADDNOTES_PLAN.SUBMIT_DATE is '提交日期';
comment on column ADDNOTES_PLAN.SUBMIT_TIME is '提交时间';
comment on column ADDNOTES_PLAN.MOD_OP_NO is '最近修改人';
comment on column ADDNOTES_PLAN.MOD_DATE is '最近修改日期';
comment on column ADDNOTES_PLAN.MOD_TIME is '最近修改时间';
comment on column ADDNOTES_PLAN.LINE_MODE is '线路规划方式1-自动规划2-固定线路';
comment on column ADDNOTES_PLAN.LINE_NO is '线路号';
comment on column ADDNOTES_PLAN.AUDIT_OP_NO is '审核人';
comment on column ADDNOTES_PLAN.AUDIT_DATE is '审核日期';
comment on column ADDNOTES_PLAN.AUDIT_TIME is '审核时间';
comment on column ADDNOTES_PLAN.REFUSE_SUGGESTION is '退审意见';
comment on column ADDNOTES_PLAN.NOTE is '备注';
comment on column ADDNOTES_PLAN.IS_URGENCY is '0-非紧急 1-紧急';


create table ADDNOTES_PLAN_DETAIL
(
    DEV_NO            VARCHAR2(20)     not null,
    ADDNOTES_PLAN_NO  VARCHAR2(32)     not null
        references ADDNOTES_PLAN,
    KEY_EVENT         VARCHAR2(32),
    CHS_EST_SCORE     NUMBER,
    CHS_AUX_SCORE     NUMBER,
    LINE_NO           VARCHAR2(32),
    SORT_NO           NUMBER,
    CASH_REQ_AMT      NUMBER,
    PLAN_PREDICT_AMT  NUMBER,
    PLAN_ADDNOTES_AMT NUMBER,
    NOTE              VARCHAR2(100),
    KEY_EVENT_DETAIL  VARCHAR2(80),
    STATUS            NUMBER default 0 not null,
    USEDAYS           NUMBER,
    primary key (ADDNOTES_PLAN_NO, DEV_NO)
);
comment on table ADDNOTES_PLAN_DETAIL is '加钞计划明细表';
comment on column ADDNOTES_PLAN_DETAIL.DEV_NO is '设备号';
comment on column ADDNOTES_PLAN_DETAIL.ADDNOTES_PLAN_NO is '计划加钞编号';
comment on column ADDNOTES_PLAN_DETAIL.KEY_EVENT is '决定型事件';
comment on column ADDNOTES_PLAN_DETAIL.CHS_EST_SCORE is '预测型加钞优先度';
comment on column ADDNOTES_PLAN_DETAIL.CHS_AUX_SCORE is '辅助型加钞优先度';
comment on column ADDNOTES_PLAN_DETAIL.LINE_NO is '线路号';
comment on column ADDNOTES_PLAN_DETAIL.SORT_NO is '加钞顺序号';
comment on column ADDNOTES_PLAN_DETAIL.CASH_REQ_AMT is '现金需求预测量(单位元)';
comment on column ADDNOTES_PLAN_DETAIL.PLAN_PREDICT_AMT is '计划加钞预测量(单位元)';
comment on column ADDNOTES_PLAN_DETAIL.PLAN_ADDNOTES_AMT is '计划加钞金额(单位元)';
comment on column ADDNOTES_PLAN_DETAIL.NOTE is '备注';
comment on column ADDNOTES_PLAN_DETAIL.KEY_EVENT_DETAIL is '决定型事件描述';
comment on column ADDNOTES_PLAN_DETAIL.STATUS is '状态0未通过1已通过';
comment on column ADDNOTES_PLAN_DETAIL.USEDAYS is '加钞金额使用天数';


create table ADDNOTES_PLAN_GROUP
(
    ADDNOTES_PLAN_NO  VARCHAR2(32)     not null,
    GROUP_NO          VARCHAR2(32)     not null,
    CLR_TIME_INTERVAL NUMBER default 0 not null,
    PLAN_DEV_CNT      NUMBER           not null,
    PLAN_NETPNT_CNT   NUMBER           not null,
    PLAN_DISTANCE     NUMBER,
    PLAN_TIME_COST    NUMBER,
    constraint PK_PLAN_GROUP
        primary key (ADDNOTES_PLAN_NO, GROUP_NO)
);
comment on column ADDNOTES_PLAN_GROUP.ADDNOTES_PLAN_NO is '加钞计划编号';
comment on column ADDNOTES_PLAN_GROUP.GROUP_NO is '加钞分组号';
comment on column ADDNOTES_PLAN_GROUP.CLR_TIME_INTERVAL is '清机时段';
comment on column ADDNOTES_PLAN_GROUP.PLAN_DEV_CNT is '设备台数';
comment on column ADDNOTES_PLAN_GROUP.PLAN_NETPNT_CNT is '网点数';
comment on column ADDNOTES_PLAN_GROUP.PLAN_DISTANCE is '预计路程';
comment on column ADDNOTES_PLAN_GROUP.PLAN_TIME_COST is '预计用时';


create table ADDNOTES_RULE_TABLE
(
    RULE_ID         VARCHAR2(32)  not null
        primary key,
    RULE_DESP       VARCHAR2(100) not null,
    RULE_GEN_OP     VARCHAR2(20)  not null,
    RULE_GEN_DATE   VARCHAR2(10)  not null,
    RULE_GEN_TIME   VARCHAR2(8)   not null,
    ADDNOTES_COEFF  NUMBER(4, 2),
    QUOTA_RATIO     NUMBER(4, 2),
    ADDNOTES_PERIOD NUMBER(6, 2),
    CLR_CENTER_NO   VARCHAR2(20)
);
comment on column ADDNOTES_RULE_TABLE.RULE_ID is '编号';
comment on column ADDNOTES_RULE_TABLE.RULE_DESP is '描述';
comment on column ADDNOTES_RULE_TABLE.RULE_GEN_OP is '规则制订人';
comment on column ADDNOTES_RULE_TABLE.RULE_GEN_DATE is '规则制订日期';
comment on column ADDNOTES_RULE_TABLE.RULE_GEN_TIME is '规则制订时间';
comment on column ADDNOTES_RULE_TABLE.ADDNOTES_COEFF is '配钞系数';
comment on column ADDNOTES_RULE_TABLE.QUOTA_RATIO is '定额配比';
comment on column ADDNOTES_RULE_TABLE.ADDNOTES_PERIOD is '加钞周期';
comment on column ADDNOTES_RULE_TABLE.CLR_CENTER_NO is '金库编号';


create table LINE_RUN_INFO
(
    LINE_RUN_NO    VARCHAR2(32) not null
        constraint PK_LINE_RUN
            primary key,
    LINE_NO        VARCHAR2(32) not null,
    THE_YEAR_MONTH VARCHAR2(7)  not null,
    THE_DAY        VARCHAR2(2)  not null,
    TASK_COUNT     NUMBER       not null,
    DEV_COUNT      NUMBER       not null,
    START_TIME_AM  VARCHAR2(8),
    END_TIME_AM    VARCHAR2(8),
    START_TIME_PM  VARCHAR2(8),
    END_TIME_PM    VARCHAR2(8),
    RETURN_UNIT_AM VARCHAR2(80),
    RETURN_UNIT_PM VARCHAR2(80)
);
comment on table LINE_RUN_INFO is '线路运行表';
comment on column LINE_RUN_INFO.LINE_RUN_NO is '编号';
comment on column LINE_RUN_INFO.LINE_NO is '线路号';
comment on column LINE_RUN_INFO.THE_YEAR_MONTH is '年月';
comment on column LINE_RUN_INFO.THE_DAY is '日';
comment on column LINE_RUN_INFO.TASK_COUNT is '任务次数';
comment on column LINE_RUN_INFO.START_TIME_AM is '上午开始时间';
comment on column LINE_RUN_INFO.END_TIME_AM is '上午结束时间';
comment on column LINE_RUN_INFO.START_TIME_PM is '下午开始时间';
comment on column LINE_RUN_INFO.END_TIME_PM is '下午结束时间';
comment on column LINE_RUN_INFO.RETURN_UNIT_AM is '上午返回地点';
comment on column LINE_RUN_INFO.RETURN_UNIT_PM is '下午返回地点';


create table LINE_RUN_DEV_DETAIL
(
    DEV_NO            VARCHAR2(32) not null,
    LINE_RUN_NO       VARCHAR2(32) not null
        constraint FK_DEV_RT_RUN
            references LINE_RUN_INFO,
    ORG_NO            VARCHAR2(20) not null,
    ORG_NAME          VARCHAR2(80) not null,
    CLR_TIME_INTERVAL CHAR         not null,
    ARRIVAL_TIME      VARCHAR2(8)  not null
);
comment on table LINE_RUN_DEV_DETAIL is '线路运行设备明细表';
comment on column LINE_RUN_DEV_DETAIL.DEV_NO is '设备号';
comment on column LINE_RUN_DEV_DETAIL.LINE_RUN_NO is '线路运行编号';
comment on column LINE_RUN_DEV_DETAIL.ORG_NO is '设备所在网点号';
comment on column LINE_RUN_DEV_DETAIL.ORG_NAME is '设备所在网点名称';
comment on column LINE_RUN_DEV_DETAIL.CLR_TIME_INTERVAL is '清机时段';
comment on column LINE_RUN_DEV_DETAIL.ARRIVAL_TIME is '要求到达网点时间';

create unique index PK_RT_RUN_DEV_LIST
  on LINE_RUN_DEV_DETAIL (DEV_NO, LINE_RUN_NO);

alter table LINE_RUN_DEV_DETAIL
  add constraint PK_LINE_RUN_DEV_LIST
    primary key (DEV_NO, LINE_RUN_NO);


create table NETPOINT_MATRIX_TABLE
(
    START_POINT_NO VARCHAR2(20) not null,
    END_POINT_NO   VARCHAR2(20) not null,
    TYPE           NUMBER       not null,
    TACTIC         NUMBER       not null,
    DISTANCE       NUMBER       not null,
    TIME_COST      NUMBER       not null,
    CLR_CENTER_NO  VARCHAR2(20) not null
        constraint FK_MTRX_CLR
            references CLR_CENTER_TABLE,
    NOTE           VARCHAR2(100),
    DATA_TYPE      NUMBER,
    constraint NP_MATRIX_PK
        primary key (START_POINT_NO, END_POINT_NO, TYPE, TACTIC)
);
comment on table NETPOINT_MATRIX_TABLE is '网点矩阵表';
comment on column NETPOINT_MATRIX_TABLE.START_POINT_NO is '起点';
comment on column NETPOINT_MATRIX_TABLE.END_POINT_NO is '终点';
comment on column NETPOINT_MATRIX_TABLE.TYPE is '类型:0-网点到网点;1-清机中心到网点;2-网点到清机中心';
comment on column NETPOINT_MATRIX_TABLE.TACTIC is '策略';
comment on column NETPOINT_MATRIX_TABLE.DISTANCE is '路程(米)';
comment on column NETPOINT_MATRIX_TABLE.TIME_COST is '耗时(分钟)';
comment on column NETPOINT_MATRIX_TABLE.NOTE is '备注';
comment on column NETPOINT_MATRIX_TABLE.DATA_TYPE is '数据类型：1，设备；2，网点；';


create table SPDATE_COEFF_TABLE
(
    START_DATE     VARCHAR2(10)              not null,
    END_DATE       VARCHAR2(10)              not null,
    CLR_CENTER_NO  VARCHAR2(20)              not null,
    ADDNOTES_COEFF NUMBER(4, 2) default 1.00 not null,
    constraint PK_SPDATE
        primary key (START_DATE, END_DATE, CLR_CENTER_NO)
);
comment on table SPDATE_COEFF_TABLE is '特殊日期配钞系数表';
comment on column SPDATE_COEFF_TABLE.START_DATE is '特殊开始日期';
comment on column SPDATE_COEFF_TABLE.END_DATE is '特殊结束日期';
comment on column SPDATE_COEFF_TABLE.CLR_CENTER_NO is '金库编号表';
comment on column SPDATE_COEFF_TABLE.ADDNOTES_COEFF is '配钞系数';


create table SPECIAL_DAY
(
    SPECIAL_DAY VARCHAR2(10) not null
        constraint PK_SPECIAL_DAY
            primary key,
    DAY_TYPE    NUMBER       not null,
    NOTE        VARCHAR2(50)
);
comment on column SPECIAL_DAY.SPECIAL_DAY is '特殊日期';
comment on column SPECIAL_DAY.DAY_TYPE is '日期类型';
comment on column SPECIAL_DAY.NOTE is '备注';


create table FORECAST_CASH_AMOUNT_INFO
(
    DEV_NO         VARCHAR2(32) not null,
    FORECAST_DATE  VARCHAR2(10) not null,
    CLR_CENTER_NO  VARCHAR2(20),
    DEPOSIT_AMOUNT NUMBER,
    DRAW_AMOUNT    NUMBER,
    SYS_TIME       VARCHAR2(20),
    constraint PK_FORECAST_CASH_AMOUNT_INFO
        primary key (DEV_NO, FORECAST_DATE)
);
comment on table FORECAST_CASH_AMOUNT_INFO is '预测金额信息表';
comment on column FORECAST_CASH_AMOUNT_INFO.DEV_NO is '设备号';
comment on column FORECAST_CASH_AMOUNT_INFO.FORECAST_DATE is '预测日期';
comment on column FORECAST_CASH_AMOUNT_INFO.CLR_CENTER_NO is '所属金库';
comment on column FORECAST_CASH_AMOUNT_INFO.DEPOSIT_AMOUNT is '预测存款量';
comment on column FORECAST_CASH_AMOUNT_INFO.DRAW_AMOUNT is '预测取款量';
comment on column FORECAST_CASH_AMOUNT_INFO.SYS_TIME is '预测生成时间';


create table DISPATCH_TABLE
(
  DISPATCH_NO       VARCHAR2(32)     not null
    constraint PK_DISPATCH
      primary key,
  CLR_CENTER_NO     VARCHAR2(20),
  ADDNOTES_GROUP_NO VARCHAR2(32),
  ADDNOTES_PLAN_NO  VARCHAR2(32),
  ADDNOTES_OP_NO1   VARCHAR2(20),
  ADDNOTES_OP_NO2   VARCHAR2(20),
  CAR_NO            VARCHAR2(32),
  ADDNOTES_DATE     VARCHAR2(20)     not null,
  PLAN_START_TIME   VARCHAR2(8),
  PLAN_END_TIME     VARCHAR2(8),
  PLAN_DISTANCE     NUMBER,
  PLAN_TIME_COST    NUMBER,
  STATUS            NUMBER           not null,
  CANCEL_REASON     VARCHAR2(100),
  ADD_OP_NO         VARCHAR2(20),
  ADD_DATE          VARCHAR2(10)     not null,
  ADD_TIME          VARCHAR2(8),
  ADD_MODE          CHAR             not null,
  MOD_OP_NO         VARCHAR2(20),
  MOD_DATE          VARCHAR2(10),
  MOD_TIME          VARCHAR2(8),
  REFUSE_SUGGESTION VARCHAR2(1024),
  AUDIT_OP_NO       VARCHAR2(20),
  AUDIT_DATE        VARCHAR2(10),
  AUDIT_TIME        VARCHAR2(8),
  NOTE              VARCHAR2(100),
  CIR_START_TIME    VARCHAR2(8),
  CIR_END_TIME      VARCHAR2(8),
  KEY_USE_NO        VARCHAR2(32),
  URGENT_FLAG       NUMBER default 0 not null,
  URG_NOTIFY_TIME   VARCHAR2(8),
  TAG_READER_USE_NO VARCHAR2(20),
  LINE_NO           VARCHAR2(20),
  CLR_TIME_INTERVAL NUMBER
);
comment on table DISPATCH_TABLE is '加钞任务表';
comment on column DISPATCH_TABLE.DISPATCH_NO is '任务编号';
comment on column DISPATCH_TABLE.CLR_CENTER_NO is '金库编号';
comment on column DISPATCH_TABLE.ADDNOTES_GROUP_NO is '加钞分组号';
comment on column DISPATCH_TABLE.ADDNOTES_PLAN_NO is '加钞计划编号';
comment on column DISPATCH_TABLE.ADDNOTES_OP_NO1 is '加钞人员1';
comment on column DISPATCH_TABLE.ADDNOTES_OP_NO2 is '加钞人员2';
comment on column DISPATCH_TABLE.CAR_NO is '运钞车编号';
comment on column DISPATCH_TABLE.ADDNOTES_DATE is '加钞日期';
comment on column DISPATCH_TABLE.PLAN_START_TIME is '预计出发时间';
comment on column DISPATCH_TABLE.PLAN_END_TIME is '预计完成时间';
comment on column DISPATCH_TABLE.PLAN_DISTANCE is '预计路程';
comment on column DISPATCH_TABLE.PLAN_TIME_COST is '预计耗时';
comment on column DISPATCH_TABLE.STATUS is '1-未配钞 2-已配钞 3-已取消 4-已过期 5-流转中 6-流转完成 7-已完成';
comment on column DISPATCH_TABLE.CANCEL_REASON is '取消原因';
comment on column DISPATCH_TABLE.ADD_OP_NO is '任务单生成人';
comment on column DISPATCH_TABLE.ADD_DATE is '任务生成日期';
comment on column DISPATCH_TABLE.ADD_TIME is '任务生成时间';
comment on column DISPATCH_TABLE.ADD_MODE is '生成方式0：手工1：自动';
comment on column DISPATCH_TABLE.MOD_OP_NO is '最近修改人';
comment on column DISPATCH_TABLE.MOD_DATE is '最近修改日期';
comment on column DISPATCH_TABLE.MOD_TIME is '最近修改时间';
comment on column DISPATCH_TABLE.REFUSE_SUGGESTION is '最近退审意见';
comment on column DISPATCH_TABLE.AUDIT_OP_NO is '最近审核人';
comment on column DISPATCH_TABLE.AUDIT_DATE is '最近审核日期';
comment on column DISPATCH_TABLE.AUDIT_TIME is '最近审核时间';
comment on column DISPATCH_TABLE.NOTE is '备注';
comment on column DISPATCH_TABLE.CIR_START_TIME is '流转开始时间';
comment on column DISPATCH_TABLE.CIR_END_TIME is '流转结束时间';
comment on column DISPATCH_TABLE.KEY_USE_NO is '要是领用编号';
comment on column DISPATCH_TABLE.URGENT_FLAG is '是否紧急任务0：正常任务1：紧急任务';
comment on column DISPATCH_TABLE.URG_NOTIFY_TIME is '中途加钞通知时间';
comment on column DISPATCH_TABLE.TAG_READER_USE_NO is '手持机领用编号';
comment on column DISPATCH_TABLE.LINE_NO is '线路编号';
comment on column DISPATCH_TABLE.CLR_TIME_INTERVAL is '加钞时间段 1-上午 2-下午 3-全天';

create index IDX_DIS_ADDDATE
  on DISPATCH_TABLE (ADDNOTES_DATE);


create table DISPATCH_DETAIL_TABLE
(
  DISPATCH_NO      VARCHAR2(32)     not null,
  DEV_NO           VARCHAR2(20)     not null,
  ADDNOTES_DATE    VARCHAR2(10)     not null,
  ADDNOTES_AMOUNT  NUMBER,
  ADDNOTES_SORT_NO NUMBER,
  EX_FLAG          NUMBER default 0 not null,
  STATUS           NUMBER default 0 not null,
  NOTE             VARCHAR2(100),
  BACK_AMOUNT      NUMBER,
  BACK_DATE        VARCHAR2(20),
  STUCK_AMOUNT     NUMBER,
  COUNT_MACHINE_NO VARCHAR2(20),
  DISCARD_AMOUNT   NUMBER,
  STORAGE_AMOUNT   NUMBER,
  constraint PK_DISP_DTL
    primary key (DISPATCH_NO, DEV_NO)
);
comment on table DISPATCH_DETAIL_TABLE is '任务单明细表';
comment on column DISPATCH_DETAIL_TABLE.DISPATCH_NO is '任务编号';
comment on column DISPATCH_DETAIL_TABLE.DEV_NO is '设备号';
comment on column DISPATCH_DETAIL_TABLE.ADDNOTES_DATE is '加钞日期';
comment on column DISPATCH_DETAIL_TABLE.ADDNOTES_AMOUNT is '加钞金额(单位元)';
comment on column DISPATCH_DETAIL_TABLE.ADDNOTES_SORT_NO is '加钞顺序号';
comment on column DISPATCH_DETAIL_TABLE.EX_FLAG is '执行异常标志位0-正常1-异常';
comment on column DISPATCH_DETAIL_TABLE.STATUS is '审批状态0-未审批通过1-已审批通过';
comment on column DISPATCH_DETAIL_TABLE.NOTE is '备注';
comment on column DISPATCH_DETAIL_TABLE.STORAGE_AMOUNT is '已装填金额';


create table DEV_RULE_EXEC_TABLE
(
    DEV_NO           VARCHAR2(20)     not null,
    START_DATE       VARCHAR2(10)     not null,
    END_DATE         VARCHAR2(10)     not null,
    ADDNOTES_RULE_ID VARCHAR2(32)     not null,
    STATUS           NUMBER default 0 not null
);
comment on column DEV_RULE_EXEC_TABLE.DEV_NO is '设备编号';
comment on column DEV_RULE_EXEC_TABLE.START_DATE is '执行开始日期';
comment on column DEV_RULE_EXEC_TABLE.END_DATE is '执行结束日期';
comment on column DEV_RULE_EXEC_TABLE.ADDNOTES_RULE_ID is '加钞规则编号';
comment on column DEV_RULE_EXEC_TABLE.STATUS is '状态0-未执行1-执行中2-过期无效3-已取消';
create unique index PK_DEV_RULE_EXEC1
    on DEV_RULE_EXEC_TABLE (DEV_NO, START_DATE, END_DATE);

alter table DEV_RULE_EXEC_TABLE
    add constraint PK_DEV_RULE_EXEC
        primary key (DEV_NO, START_DATE, END_DATE);

create table DEV_CHS_EST_PARAM
(
    EST_PARAM_NO   VARCHAR2(3)            not null,
    CLR_CENTER_NO  VARCHAR2(20)           not null
        constraint FK_CLR_CENTER_NO_EST
            references CLR_CENTER_TABLE,
    EST_PARAM_NAME VARCHAR2(40)           not null,
    EST_PARAM_DESP VARCHAR2(100),
    WEIGHT         NUMBER(4, 2) default 0 not null,
    IS_VALID       NUMBER       default 1 not null,
    constraint PK_CHS_ESTP
        primary key (EST_PARAM_NO, CLR_CENTER_NO)
);
comment on table DEV_CHS_EST_PARAM is '加钞设备选择预测型参数配置表';
comment on column DEV_CHS_EST_PARAM.EST_PARAM_NO is '预测型参数编号';
comment on column DEV_CHS_EST_PARAM.CLR_CENTER_NO is '金库编号';
comment on column DEV_CHS_EST_PARAM.EST_PARAM_NAME is '参数名称';
comment on column DEV_CHS_EST_PARAM.EST_PARAM_DESP is '参数释义';
comment on column DEV_CHS_EST_PARAM.WEIGHT is '权重';
comment on column DEV_CHS_EST_PARAM.IS_VALID is '是否有效0-无效1-有效';


create table DEV_CHS_KEY_EVENT
(
    EVENT_NO      VARCHAR2(3)      not null,
    CLR_CENTER_NO VARCHAR2(20)     not null
        constraint FK_CLEAR_CENTER_NO_KEY
            references CLR_CENTER_TABLE,
    EVENT_NAME    VARCHAR2(40)     not null,
    EVENT_DESP    VARCHAR2(100),
    IS_VALID      NUMBER default 1 not null,
    constraint PK_CHS_EVENT
        primary key (EVENT_NO, CLR_CENTER_NO)
);
comment on table DEV_CHS_KEY_EVENT is '加钞设备选择决定型事件配置表';
comment on column DEV_CHS_KEY_EVENT.EVENT_NO is '事件编号';
comment on column DEV_CHS_KEY_EVENT.CLR_CENTER_NO is '金库编号';
comment on column DEV_CHS_KEY_EVENT.EVENT_NAME is '事件名称';
comment on column DEV_CHS_KEY_EVENT.EVENT_DESP is '事件释义';
comment on column DEV_CHS_KEY_EVENT.IS_VALID is '是否有效0-无效1-有效';


create table DEV_CASH_CLEAR
(
    DEV_NO               VARCHAR2(20) not null,
    ADDCASH_ID           VARCHAR2(10) not null,
    ADDCASH_DATETIME     VARCHAR2(19),
    ADDCASH_AMOUNT       NUMBER,
    ADDCASH_TYPE         VARCHAR2(60),
    ADDCASH_COUNT        VARCHAR2(60),
    CLEAR_DATETIME       VARCHAR2(19),
    ADDCASH_LEFT         NUMBER,
    ADDCASH_LASTAMOUNT   NUMBER,
    ADDCASH_RETRACTCOUNT NUMBER,
    DEPOSIT_COUNT        NUMBER,
    DEPOSIT_AMOUNT       NUMBER,
    WITHDRAW_COUNT       NUMBER,
    WITHDRAW_AMOUNT      NUMBER,
    CLEAR_ID             VARCHAR2(30),
    CASHUTIL_AMOUNT      VARCHAR2(100),
    CASHBY_HANDCOUNT     VARCHAR2(100),
    ADD_ID               VARCHAR2(30),
    ST_DATE              VARCHAR2(10),
    constraint PK_DEV_CASH
        primary key (DEV_NO, ADDCASH_ID)
);
comment on table DEV_CASH_CLEAR is '设备加钞清机表';
comment on column DEV_CASH_CLEAR.DEV_NO is '设备号';
comment on column DEV_CASH_CLEAR.ADDCASH_ID is '加钞标识';
comment on column DEV_CASH_CLEAR.ADDCASH_DATETIME is '加钞时间';
comment on column DEV_CASH_CLEAR.ADDCASH_AMOUNT is '加钞金额';
comment on column DEV_CASH_CLEAR.ADDCASH_TYPE is '加钞面值结婚如50,100，多种面值以逗号分隔';
comment on column DEV_CASH_CLEAR.ADDCASH_COUNT is '加钞张数';
comment on column DEV_CASH_CLEAR.CLEAR_DATETIME is '清机时间';
comment on column DEV_CASH_CLEAR.ADDCASH_LEFT is '主机尾箱余额';
comment on column DEV_CASH_CLEAR.ADDCASH_LASTAMOUNT is '钞箱剩余金额（不包括回收箱）';
comment on column DEV_CASH_CLEAR.ADDCASH_RETRACTCOUNT is '回收箱张数';
comment on column DEV_CASH_CLEAR.DEPOSIT_COUNT is '存款总笔数';
comment on column DEV_CASH_CLEAR.DEPOSIT_AMOUNT is '存款总金额';
comment on column DEV_CASH_CLEAR.WITHDRAW_COUNT is '取款总笔数';
comment on column DEV_CASH_CLEAR.WITHDRAW_AMOUNT is '取款总金额';
comment on column DEV_CASH_CLEAR.ST_DATE is '统计日期';


create table BIZTXLOG_INIT
(
    TERMID             VARCHAR2(18) not null,
    AMOUNT_CWD         NUMBER(12),
    TRANDATE           VARCHAR2(10),
    AMOUNT_CDM         NUMBER(12),
    AMOUNT_PROCESS_CWD NUMBER(12),
    DATA_TYPE          VARCHAR2(4)  not null,
    AMOUNT_PROCESS_CDM NUMBER(12),
    DATA_FALG          VARCHAR2(2),
    CHECK_RESULT       NUMBER(1),
    AMOUNT_ORIGIN_CWD  NUMBER(12),
    AMOUNT_ORIGIN_CDM  NUMBER(12),
    constraint BIZTXLOG_INIT_TEMP_PK
        unique (TERMID, TRANDATE)
);
comment on table BIZTXLOG_INIT is '设备交易日汇总原始数据表';
comment on column BIZTXLOG_INIT.TERMID is '终端编号';
comment on column BIZTXLOG_INIT.AMOUNT_CWD is '取款交易金额';
comment on column BIZTXLOG_INIT.TRANDATE is '交易日期';
comment on column BIZTXLOG_INIT.AMOUNT_CDM is '存款交易金额';
comment on column BIZTXLOG_INIT.AMOUNT_PROCESS_CWD is '处理后取款金额';
comment on column BIZTXLOG_INIT.DATA_TYPE is '0 原始数据 1 预处理数据';
comment on column BIZTXLOG_INIT.AMOUNT_PROCESS_CDM is '处理后存款金额';
comment on column BIZTXLOG_INIT.DATA_FALG is '数据类别标志：1，设备数据；2，网点。';
comment on column BIZTXLOG_INIT.CHECK_RESULT is '数据校验结果 0-正常 1-非整百 2-负数 3-非数字';
comment on column BIZTXLOG_INIT.AMOUNT_ORIGIN_CWD is '原始取款金额';
comment on column BIZTXLOG_INIT.AMOUNT_ORIGIN_CDM is '原始存款金额';

create table DEV_CLR_TIME_PARAM
(
    DEV_NO            VARCHAR2(32)            not null,
    CLR_TIME_INTERVAL CHAR                    not null,
    ARRIVAL_TIME      VARCHAR2(8)             not null,
    CLR_DAY           NUMBER(2)               not null,
    ADDNOTES_LINE     VARCHAR2(32) default '' not null,
    constraint PK_DEV_CLR_TIME
        primary key (DEV_NO, CLR_TIME_INTERVAL, CLR_DAY, ADDNOTES_LINE)
);
comment on table DEV_CLR_TIME_PARAM is '设备清机时间参数表(设备加钞周期表)';
comment on column DEV_CLR_TIME_PARAM.DEV_NO is '设备号';
comment on column DEV_CLR_TIME_PARAM.CLR_TIME_INTERVAL is '清机时段(1-上午,2-下午)';
comment on column DEV_CLR_TIME_PARAM.ARRIVAL_TIME is '要求到达时间(hh:mm:ss)';
comment on column DEV_CLR_TIME_PARAM.CLR_DAY is '清机天数序号';
comment on column DEV_CLR_TIME_PARAM.ADDNOTES_LINE is '所属线路';


create table ADDNOTES_LINE_DETAIL
(
    LINE_NO        VARCHAR2(32) not null,
    THE_DAY        NUMBER(2)    not null,
    TASK_COUNT     NUMBER       not null,
    TASK_ONE_TYPE  CHAR,
    TASK_TWO_TYPE  CHAR,
    START_TIME_AM  VARCHAR2(8),
    END_TIME_AM    VARCHAR2(8),
    START_TIME_PM  VARCHAR2(8),
    END_TIME_PM    VARCHAR2(8),
    RETURN_UNIT_AM VARCHAR2(80),
    RETURN_UNIT_PM VARCHAR2(80),
    constraint PK_ROUTE_DTL
        primary key (LINE_NO, THE_DAY)
);
comment on column ADDNOTES_LINE_DETAIL.LINE_NO is '线路号';
comment on column ADDNOTES_LINE_DETAIL.THE_DAY is '星期';
comment on column ADDNOTES_LINE_DETAIL.TASK_COUNT is '任务次数';
comment on column ADDNOTES_LINE_DETAIL.TASK_ONE_TYPE is '任务1类型';
comment on column ADDNOTES_LINE_DETAIL.TASK_TWO_TYPE is '任务2类型';
comment on column ADDNOTES_LINE_DETAIL.START_TIME_AM is '上午开始时间';
comment on column ADDNOTES_LINE_DETAIL.END_TIME_AM is '上午结束时间';
comment on column ADDNOTES_LINE_DETAIL.START_TIME_PM is '下午开始时间';
comment on column ADDNOTES_LINE_DETAIL.END_TIME_PM is '上午结束时间';
comment on column ADDNOTES_LINE_DETAIL.RETURN_UNIT_AM is '上午返回地点';
comment on column ADDNOTES_LINE_DETAIL.RETURN_UNIT_PM is '下午返回地点';


create table ADDNOTES_INDICATOR_TABLE
(
    TERM_ID                VARCHAR2(20)  not null,
    DISPATCH_PATH          VARCHAR2(200) not null,
    ADDNOTES_BEGIN_DATE    VARCHAR2(20),
    BACKNOTES_END_DATE     VARCHAR2(20),
    ADDNOTES_DAYS          VARCHAR2(10),
    MIN_ADDNOTES_AMT       NUMBER,
    MIN_AVG_INVENTORY      NUMBER,
    MIN_BACK_AMT           NUMBER,
    REAL_ADDNOTES_AMT      NUMBER,
    REAL_AVG_INVENTORY     NUMBER,
    REAL_BACK_AMT          NUMBER,
    CWD_AMT_SUM            NUMBER,
    CDM_AMT_SUM            NUMBER,
    CWD_CDM_AMT_SUM        NUMBER,
    TOWNANDCOUNTRY_FALG    VARCHAR2(100),
    BELONG_REGION          VARCHAR2(100),
    COOPERATIVE_ENTERPRISE VARCHAR2(100),
    INDUSTRY               VARCHAR2(100),
    ADDNOTES_DATE          VARCHAR2(10),
    ADDNOTES_TIME          VARCHAR2(10),
    BACKNOTES_DATE         VARCHAR2(10),
    BACKNOTES_TIME         VARCHAR2(10),
    constraint PK_ADDNOTES_INDICATOR_TABLE
        primary key (TERM_ID, DISPATCH_PATH)
);

comment on table ADDNOTES_INDICATOR_TABLE is '加钞指标表';
comment on column ADDNOTES_INDICATOR_TABLE.DISPATCH_PATH is '加钞批次号';
comment on column ADDNOTES_INDICATOR_TABLE.ADDNOTES_BEGIN_DATE is '开始日期';
comment on column ADDNOTES_INDICATOR_TABLE.BACKNOTES_END_DATE is '结束日期';
comment on column ADDNOTES_INDICATOR_TABLE.ADDNOTES_DAYS is '加钞周期';
comment on column ADDNOTES_INDICATOR_TABLE.MIN_ADDNOTES_AMT is '最小加钞金额 元';
comment on column ADDNOTES_INDICATOR_TABLE.MIN_AVG_INVENTORY is '最小日均库存 元';
comment on column ADDNOTES_INDICATOR_TABLE.MIN_BACK_AMT is '最小回钞 元';
comment on column ADDNOTES_INDICATOR_TABLE.REAL_ADDNOTES_AMT is '生产实际加钞 元';
comment on column ADDNOTES_INDICATOR_TABLE.REAL_AVG_INVENTORY is '生产实际日均库存 元';
comment on column ADDNOTES_INDICATOR_TABLE.REAL_BACK_AMT is '生产实际回钞 元';
comment on column ADDNOTES_INDICATOR_TABLE.CWD_AMT_SUM is '本周期取款合计 元';
comment on column ADDNOTES_INDICATOR_TABLE.CDM_AMT_SUM is '本周期存款合计 元';
comment on column ADDNOTES_INDICATOR_TABLE.CWD_CDM_AMT_SUM is '本周期收付量合计 元';
comment on column ADDNOTES_INDICATOR_TABLE.TOWNANDCOUNTRY_FALG is '城乡标志：1:城乡结合部；2:开发区；3:老城区；4:县城；5:乡镇；6:新城区；';
comment on column ADDNOTES_INDICATOR_TABLE.BELONG_REGION is '所属区域：1：城乡结合部；2：高新技术园区；3：工业园区；4：火车站；5：机场；6：居住社区内部或者附近；7：开发区；8：旅游景点；9：其他；10：其他交通枢纽；11：其他园区；12：汽车站；13：商品交易市场；14：商业、商业区内部或者附近；';
comment on column ADDNOTES_INDICATOR_TABLE.COOPERATIVE_ENTERPRISE is '合作企业：1：非合作企业；2：公司、集团、工厂；3：军队；4：其他；5：事业单位；6：校园、大学城；7：医院；8：政府机关；';
comment on column ADDNOTES_INDICATOR_TABLE.INDUSTRY is '所属行业：1：采矿业；2：电力、燃气及水的生产和供应业；3：房地产业；4：公共管理和社会组织；5：交通运输、仓储和邮政业；6：教育；7：金融业；8：居民服务和其他服务业；9：科学研究、技术服务和地质勘查业；10：批发和零售业；11：其他行业；12：卫生、社会保障和社会福利业；13：文化、体育和娱乐业；14：信息传输、计算机服务及软件业；15：制造业；16：住宿和餐饮业；；17：租赁和商务服务业；';
comment on column ADDNOTES_INDICATOR_TABLE.ADDNOTES_DATE is '加钞日期';
comment on column ADDNOTES_INDICATOR_TABLE.ADDNOTES_TIME is '加钞时间';
comment on column ADDNOTES_INDICATOR_TABLE.BACKNOTES_DATE is '结束日期';
comment on column ADDNOTES_INDICATOR_TABLE.BACKNOTES_TIME is '结束时间';
create index INDEX_ADDNOTES_INDICATOR_TABLE
    on ADDNOTES_INDICATOR_TABLE (TERM_ID, ADDNOTES_BEGIN_DATE, BACKNOTES_END_DATE);


create table ADDNOTES_MONITOR_TABLE
(
    TERM_ID                VARCHAR2(20) not null,
    MONITOR_MONTH          VARCHAR2(50) not null,
    AVG_MIN_ADDNOTES_AMT   NUMBER,
    MIN_ADDNOTES_MODULUS   VARCHAR2(10),
    AVG_MIN_AVG_INVENTORY  NUMBER,
    MIN_INVENTORY_MODULUS  VARCHAR2(10),
    AVG_MIN_BACK_AMT       NUMBER,
    MIN_BACK_MODULUS       VARCHAR2(10),
    AVG_REAL_ADDNOTES_AMT  NUMBER,
    AVG_REAL_AVG_INVENTORY NUMBER,
    AVG_REAL_BACK_AMT      NUMBER,
    AVG_CWD_AMT_SUM        NUMBER,
    AVG_CDM_AMT_SUM        NUMBER,
    AVG_CWD_CDM_AMT_SUM    NUMBER,
    GOAL_ADDNOTES_AMT      NUMBER,
    GOAL_AVG_INVENTORY     NUMBER,
    GOAL_BACK_AMT          NUMBER,
    ACTUAL_ADDNOTES_AMT    NUMBER,
    ACTUAL_AVG_INVENTORY   NUMBER,
    ACTUAL_BACK_AMT        NUMBER,
    ACTUAL_CWD_AMT_SUM     NUMBER,
    ACTUAL_CDM_AMT_SUM     NUMBER,
    ACTUAL_CWD_CDM_AMT_SUM NUMBER,
    constraint PK_ADDNOTES_MONITOR_TABLE
        primary key (TERM_ID, MONITOR_MONTH)
);

comment on table ADDNOTES_MONITOR_TABLE is '加钞监测表';
comment on column ADDNOTES_MONITOR_TABLE.TERM_ID is '设备编号';
comment on column ADDNOTES_MONITOR_TABLE.MONITOR_MONTH is '月份';
comment on column ADDNOTES_MONITOR_TABLE.AVG_MIN_ADDNOTES_AMT is '同期平均最小加钞 元';
comment on column ADDNOTES_MONITOR_TABLE.MIN_ADDNOTES_MODULUS is '最小加钞系数';
comment on column ADDNOTES_MONITOR_TABLE.AVG_MIN_AVG_INVENTORY is '同期平均最小日均库存 元';
comment on column ADDNOTES_MONITOR_TABLE.MIN_INVENTORY_MODULUS is '最小库存系数';
comment on column ADDNOTES_MONITOR_TABLE.AVG_MIN_BACK_AMT is '同期平均最小回钞 元';
comment on column ADDNOTES_MONITOR_TABLE.MIN_BACK_MODULUS is '最小回钞系数';
comment on column ADDNOTES_MONITOR_TABLE.AVG_REAL_ADDNOTES_AMT is '同期平均实际加钞 元';
comment on column ADDNOTES_MONITOR_TABLE.AVG_REAL_AVG_INVENTORY is '同期平均实际日均库存 元';
comment on column ADDNOTES_MONITOR_TABLE.AVG_REAL_BACK_AMT is '同期平均实际回钞 元';
comment on column ADDNOTES_MONITOR_TABLE.AVG_CWD_AMT_SUM is '同期平均取款交易量 元';
comment on column ADDNOTES_MONITOR_TABLE.AVG_CDM_AMT_SUM is '同期平均存款交易量 元';
comment on column ADDNOTES_MONITOR_TABLE.AVG_CWD_CDM_AMT_SUM is '同期平均存取款收付交易量 元';
comment on column ADDNOTES_MONITOR_TABLE.GOAL_ADDNOTES_AMT is '目标加钞 元';
comment on column ADDNOTES_MONITOR_TABLE.GOAL_AVG_INVENTORY is '目标平均库存 元';
comment on column ADDNOTES_MONITOR_TABLE.GOAL_BACK_AMT is '目标回钞 元';
comment on column ADDNOTES_MONITOR_TABLE.ACTUAL_ADDNOTES_AMT is '运行实际加钞 元';
comment on column ADDNOTES_MONITOR_TABLE.ACTUAL_AVG_INVENTORY is '运行实际平均库存 元';
comment on column ADDNOTES_MONITOR_TABLE.ACTUAL_BACK_AMT is '运行实际回钞 元';
comment on column ADDNOTES_MONITOR_TABLE.ACTUAL_CWD_AMT_SUM is '运行实际取款量 元';
comment on column ADDNOTES_MONITOR_TABLE.ACTUAL_CDM_AMT_SUM is '运行实际取款量 元';
comment on column ADDNOTES_MONITOR_TABLE.ACTUAL_CWD_CDM_AMT_SUM is '运行实际存取款量收付量 元';

create table ZJML_PREDICT_DATA
(
    TERMID   VARCHAR2(20) not null,
    TRANDATE VARCHAR2(20) not null,
    TXTYPE   VARCHAR2(2)  not null,
    ALGTYPE  VARCHAR2(10) not null,
    AMT      NUMBER(10),
    NOTE     VARCHAR2(20) default null,
    constraint PK_ZJML_PREDICT_DATA
        primary key (TERMID, TRANDATE, TXTYPE, ALGTYPE)
);
comment on table ZJML_PREDICT_DATA is '预测数据汇总表';
comment on column ZJML_PREDICT_DATA.TERMID is '设备编号';
comment on column ZJML_PREDICT_DATA.TRANDATE is '预测日期';
comment on column ZJML_PREDICT_DATA.TXTYPE is '存取款模型标志 0:取款 1:存款';
comment on column ZJML_PREDICT_DATA.ALGTYPE is '使用的预测模型：101:上下文GRU,102:时间序列GRU,103:上下文LSTM,104:时间序列LSTM,105:HoltWinters';
comment on column ZJML_PREDICT_DATA.AMT is '预测值, 单位：元';
comment on column ZJML_PREDICT_DATA.NOTE is '备注';


create table ZJML_SCHEDULE_JOB
(
    JOB_ID          VARCHAR2(32) not null
        primary key,
    JOB_NAME        VARCHAR2(32)
        unique,
    CRON_EXPRESSION VARCHAR2(32),
    MODULE_NAME     VARCHAR2(200),
    METHOD_NAME     VARCHAR2(20),
    IS_CONCURRENT   NUMBER default 0,
    JOB_STATE       NUMBER default 0,
    DESCRIPTION     VARCHAR2(200),
    ARGUMENTS       VARCHAR2(2000)
);
comment on column ZJML_SCHEDULE_JOB.JOB_ID is '定时任务编号';
comment on column ZJML_SCHEDULE_JOB.JOB_NAME is '定时任务名称';
comment on column ZJML_SCHEDULE_JOB.CRON_EXPRESSION is '定时任务执行规则';
comment on column ZJML_SCHEDULE_JOB.MODULE_NAME is '模块名称';
comment on column ZJML_SCHEDULE_JOB.METHOD_NAME is '方法名称';
comment on column ZJML_SCHEDULE_JOB.IS_CONCURRENT is '是否并发启动 default 0 0-关闭 1-启动';
comment on column ZJML_SCHEDULE_JOB.JOB_STATE is '任务状态 default 0 0-关闭 1-启动';
comment on column ZJML_SCHEDULE_JOB.DESCRIPTION is '任务描述';
comment on column ZJML_SCHEDULE_JOB.ARGUMENTS is '任务参数';

create table ZJML_TIMEJOB_PREDICT
(
    DEV_NO         VARCHAR2(20) not null,
    FORECAST_DATE  VARCHAR2(10) not null,
    DEPOSIT_AMOUNT NUMBER,
    DRAW_AMOUNT    NUMBER,
    SYS_TIME       VARCHAR2(20),
    constraint ZJML_TIMEJOB_PREDICT_PK
        primary key (DEV_NO, FORECAST_DATE)
);
comment on table ZJML_TIMEJOB_PREDICT is '离线预测值记录表';
comment on column ZJML_TIMEJOB_PREDICT.DEV_NO is '设备号';
comment on column ZJML_TIMEJOB_PREDICT.FORECAST_DATE is '预测日期';
comment on column ZJML_TIMEJOB_PREDICT.DEPOSIT_AMOUNT is '预测存款量';
comment on column ZJML_TIMEJOB_PREDICT.DRAW_AMOUNT is '预测取款量';
comment on column ZJML_TIMEJOB_PREDICT.SYS_TIME is '预测生成时间';


create table ZJML_TIMEJOB_TRAIN
(
    TERMID     VARCHAR2(20) not null
        constraint ZJML_TIMEJOB_TRAIN_PK
            primary key,
    DAYOFMONTH NUMBER
);
comment on table ZJML_TIMEJOB_TRAIN is '定时训练表';
comment on column ZJML_TIMEJOB_TRAIN.TERMID is '设备号';
comment on column ZJML_TIMEJOB_TRAIN.DAYOFMONTH is '每个月第几天';

commit;
-----------------------------------------------------------------
--                      addnotes_plan 模块
-----------------------------------------------------------------
