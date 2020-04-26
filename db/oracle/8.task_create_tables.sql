-- auto-generated definition
create table CONTAINER_TYPE
(
    CONTAINER_TYPE NUMBER not null
        constraint PK_CONTAINER_TYPE
            primary key,
    NAME           VARCHAR2(20)
);

comment on table CONTAINER_TYPE is '容器类型表';

comment on column CONTAINER_TYPE.CONTAINER_TYPE is '容器类型';

comment on column CONTAINER_TYPE.NAME is '名称';



create table TASK_TABLE
(
    TASK_NO          VARCHAR2(32) not null
        constraint PK_TASK_TABLE
            primary key,
    TASK_TYPE        NUMBER,
    PLAN_FINISH_DATE VARCHAR2(10),
    STATUS           NUMBER,
    CLR_CENTER_NO    VARCHAR2(10),
    CAR_NUMBER       VARCHAR2(20),
    LINE_NO          VARCHAR2(32),
    OP_NO1           VARCHAR2(20),
    OP_NO2           VARCHAR2(20),
    CREATE_OP_NO     VARCHAR2(20),
    CREATE_TIME      VARCHAR2(20),
    NOTE             VARCHAR2(100),
    MODE_OP          VARCHAR2(20),
    MODE_TIME        VARCHAR2(20),
    MODE_NOTE        VARCHAR2(100),
    AUDIT_OP         VARCHAR2(20),
    AUDIT_TIME       VARCHAR2(20),
    AUDIT_NOTE       VARCHAR2(100),
    PLAN_DISTANCE    NUMBER,
    PLAN_TIME_COST   NUMBER,
    URGENT_FLAG      NUMBER,
    SHELF_NO         VARCHAR2(32),
    ADDNOTES_PLAN_NO VARCHAR2(32),
    constraint FK_TASK_PSS_SO_STATUS
        foreign key (TASK_TYPE, STATUS) references PRODUCT_SERVICE_STATUS
);

alter table TASK_TABLE
	add constraint FK_TASK_PSS_SO_STATUS
		foreign key (TASK_TYPE, STATUS) references PRODUCT_SERVICE_STATUS(SERVICE_NO,STATUS);

comment on table TASK_TABLE is '订单任务表';

comment on column TASK_TABLE.TASK_NO is '任务编号';

comment on column TASK_TABLE.TASK_TYPE is '任务单类型';

comment on column TASK_TABLE.PLAN_FINISH_DATE is '计划完成日期';

comment on column TASK_TABLE.STATUS is '状态';

comment on column TASK_TABLE.CLR_CENTER_NO is '金库编号';

comment on column TASK_TABLE.CAR_NUMBER is '押运车编号';

comment on column TASK_TABLE.LINE_NO is '线路编号';

comment on column TASK_TABLE.OP_NO1 is '执行人员1编号';

comment on column TASK_TABLE.OP_NO2 is '执行人员2编号2';

comment on column TASK_TABLE.CREATE_OP_NO is '创建人员编号';

comment on column TASK_TABLE.CREATE_TIME is '创建时间';

comment on column TASK_TABLE.NOTE is '备注';

comment on column TASK_TABLE.MODE_OP is '最近修改人员';

comment on column TASK_TABLE.MODE_TIME is '最近修改时间';

comment on column TASK_TABLE.MODE_NOTE is '修改备注';

comment on column TASK_TABLE.AUDIT_OP is '审批人';

comment on column TASK_TABLE.AUDIT_TIME is '审批时间';

comment on column TASK_TABLE.AUDIT_NOTE is '审批意见';

comment on column TASK_TABLE.PLAN_DISTANCE is '预计路程(m)';

comment on column TASK_TABLE.PLAN_TIME_COST is '预计耗时（单位：min）';

comment on column TASK_TABLE.URGENT_FLAG is '是否紧急';

comment on column TASK_TABLE.SHELF_NO is '笼车编号';

comment on column TASK_TABLE.ADDNOTES_PLAN_NO is '加钞计划编号';

-- auto-generated definition
create table TASK_DETAIL_TABLE
(
    TASK_NO       VARCHAR2(32) not null
        constraint FK_TD_T_NO
            references TASK_TABLE
                on delete cascade,
    CUSTOMER_NO   VARCHAR2(32) not null,
    CUSTOMER_TYPE NUMBER,
    SORT          NUMBER,
    ADDRESS       VARCHAR2(100),
    NOTE          VARCHAR2(100),
    STATUS        NUMBER,
    constraint PK_TASK_DETAIL_TABLE
        primary key (TASK_NO, CUSTOMER_NO)
);

comment on table TASK_DETAIL_TABLE is '任务明细表';

comment on column TASK_DETAIL_TABLE.TASK_NO is '任务编号';

comment on column TASK_DETAIL_TABLE.CUSTOMER_NO is '服务对象';

comment on column TASK_DETAIL_TABLE.CUSTOMER_TYPE is '服务对象类型';

comment on column TASK_DETAIL_TABLE.SORT is '执行顺序';

comment on column TASK_DETAIL_TABLE.ADDRESS is '地址';

comment on column TASK_DETAIL_TABLE.NOTE is '备注';

comment on column TASK_DETAIL_TABLE.STATUS is '备注 1-已配钞 2-已签收 3-已回收 4-已清点';



create table TASK_IN_OUT
(
    ID             VARCHAR2(32) not null
        constraint PK_TASK_IN_OUT
            primary key,
    TASK_NO        VARCHAR2(32),
    CUSTOMER_NO    VARCHAR2(32),
    DIRECTION      NUMBER,
    CONTAINER_TYPE NUMBER,
    AMOUNT         NUMBER,
    CURRENCY_TYPE  NUMBER,
    CURRENCY_CODE  VARCHAR2(10),
    DENOMINATION   NUMBER
);

comment on table TASK_IN_OUT is '任务调入调出表';

comment on column TASK_IN_OUT.ID is '编号';

comment on column TASK_IN_OUT.TASK_NO is '任务编号';

comment on column TASK_IN_OUT.CUSTOMER_NO is '服务对象';

comment on column TASK_IN_OUT.DIRECTION is '方向 1-入库 2-出库';

comment on column TASK_IN_OUT.CONTAINER_TYPE is '物品类型';

comment on column TASK_IN_OUT.AMOUNT is '金额(元);数量;重量';

comment on column TASK_IN_OUT.CURRENCY_TYPE is '钞币类别';

comment on column TASK_IN_OUT.CURRENCY_CODE is '币种';

comment on column TASK_IN_OUT.DENOMINATION is '面值';

create table TASK_PER_RECORDER
(
    ID             VARCHAR2(32) not null
        constraint PK_TASK_PER_RECORDER
            primary key,
    TASK_NO        VARCHAR2(32) not null,
    CONTAINER_NO   VARCHAR2(32) not null,
    PERFORM_TIME   VARCHAR2(20) not null,
    PERFORM_TYPE   NUMBER,
    OP_NO1         VARCHAR2(10),
    OP_NO2         VARCHAR2(10),
    CONTAINER_TYPE NUMBER
);

comment on table TASK_PER_RECORDER is '任务执行记录表';

comment on column TASK_PER_RECORDER.ID is '记录编号';

comment on column TASK_PER_RECORDER.TASK_NO is '任务编号';

comment on column TASK_PER_RECORDER.CONTAINER_NO is '货物编号';

comment on column TASK_PER_RECORDER.PERFORM_TIME is '操作时间';

comment on column TASK_PER_RECORDER.PERFORM_TYPE is
    '操作类型 操作类型 1-配钞 2-入库 3-出库 4-签收 5-回收 6-入库交接 7-出库交接 8-库房调出清分区 9-清分区调入库房';

comment on column TASK_PER_RECORDER.OP_NO1 is '操作人员1';

comment on column TASK_PER_RECORDER.OP_NO2 is '操作人员2';

comment on column TASK_PER_RECORDER.CONTAINER_TYPE is '货物类型';

-- auto-generated definition
create table TASK_ENTITY_TABLE
(
    TASK_NO      VARCHAR2(32) not null,
    CONTAINER_NO VARCHAR2(32) not null,
    CUSTOMER_NO  VARCHAR2(32) not null,
    ENTITY_TYPE  NUMBER
        constraint FK_TASK_ENT_FK_CONTAI_CONTAINE
            references CONTAINER_TYPE,
    DIRECTION    NUMBER       not null,
    UPPER_NO     VARCHAR2(32),
    LEAF_FLAG    NUMBER,
    STATUS       NUMBER,
    NOTE         VARCHAR2(100),
    DEPOSIT_TYPE NUMBER,
    constraint PK_TASK_ENTITY_TABLE
        primary key (TASK_NO, CONTAINER_NO)
);
comment on table TASK_ENTITY_TABLE is '任务物品关系表';

comment on column TASK_ENTITY_TABLE.TASK_NO is '任务编号';

comment on column TASK_ENTITY_TABLE.CONTAINER_NO is '容器编号';

comment on column TASK_ENTITY_TABLE.CUSTOMER_NO is '服务对象编号';

comment on column TASK_ENTITY_TABLE.ENTITY_TYPE is '货物类型 1-现金 2-贵金属 3-重空';

comment on column TASK_ENTITY_TABLE.DIRECTION is '调拨方向 1-出库 2-入库';

comment on column TASK_ENTITY_TABLE.UPPER_NO is '上次容器编号';

comment on column TASK_ENTITY_TABLE.LEAF_FLAG is '是否为最下级容器:0-否 1-是';

comment on column TASK_ENTITY_TABLE.STATUS is '容器状态';

comment on column TASK_ENTITY_TABLE.NOTE is '备注';

comment on column TASK_ENTITY_TABLE.DEPOSIT_TYPE is '寄库类型 1-长寄库 2-短寄库';



create table TASK_SHELF_USER
(
    ID       VARCHAR2(32) not null
        constraint PK_TASK_SHELF_USER
            primary key,
    TASK_NO  VARCHAR2(32)
        constraint FK_TASK_SHE_FK_TASK_S_TASK_TAB
            references TASK_TABLE,
    SHELF_NO VARCHAR2(32)
        constraint FK_TASK_SHE_FK_TASK_S_SHELF_TA
            references SHELF_TABLE,
    OP_TYPE  NUMBER,
    OP_TIME  VARCHAR2(20)
);

comment on column TASK_SHELF_USER.ID is '记录编号';

comment on column TASK_SHELF_USER.TASK_NO is '任务单编号';

comment on column TASK_SHELF_USER.SHELF_NO is '笼车编号';

comment on column TASK_SHELF_USER.OP_TYPE is '操作类型 1-装车 2-卸货';

comment on column TASK_SHELF_USER.OP_TIME is '操作时间';

--任务物品装箱清点表
create table TASK_ENTITY_DETAIL
(
    ID               VARCHAR2(32) not null
        constraint PK_TASK_ENTITY_DETAIL
            primary key,
    TASK_NO          VARCHAR2(32),
    CONTAINER_NO     VARCHAR2(32),
    CONTAINER_TYPE   NUMBER,
    AMOUNT           NUMBER,
    CURRENCY_TYPE    NUMBER,
    CURRENCY_CODE    VARCHAR2(10),
    DENOMINATION     NUMBER,
    OP_TYPE          NUMBER,
    OP_NO            VARCHAR2(32),
    OP_TIME          VARCHAR2(32),
    CLEAR_MACHINE_NO VARCHAR2(32),
    APPLY_AMOUNT     NUMBER
);

comment on table TASK_ENTITY_DETAIL is '任务物品装箱清点表';

comment on column TASK_ENTITY_DETAIL.ID is '记录编号';

comment on column TASK_ENTITY_DETAIL.TASK_NO is '任务编号';

comment on column TASK_ENTITY_DETAIL.CONTAINER_NO is '容器编号';

comment on column TASK_ENTITY_DETAIL.CONTAINER_TYPE is '容器类型';

comment on column TASK_ENTITY_DETAIL.APPLY_AMOUNT is '申请金额(元)'

comment on column TASK_ENTITY_DETAIL.AMOUNT is '金额(元);数量;重量';

comment on column TASK_ENTITY_DETAIL.CURRENCY_TYPE is '钞币类别';

comment on column TASK_ENTITY_DETAIL.CURRENCY_CODE is '币种';

comment on column TASK_ENTITY_DETAIL.DENOMINATION is '面值';

comment on column TASK_ENTITY_DETAIL.OP_TYPE is '操作类型 1-配钞 2-清点';

comment on column TASK_ENTITY_DETAIL.OP_NO is '操作人员';

comment on column TASK_ENTITY_DETAIL.OP_TIME is '操作时间';

comment on column TASK_ENTITY_DETAIL.CLEAR_MACHINE_NO is '清点设备';

create table TASK_CHECK_TABLE
(
    ID               VARCHAR2(32) not null
        constraint PK_TASK_CHECK_TABLE
            primary key,
    TASK_NO          VARCHAR2(32),
    CUSTOMER_NO      VARCHAR2(32),
    CONTAINER_NO     VARCHAR2(32),
    AMOUNT           NUMBER,
    CURRENCY_CODE    VARCHAR2(10),
    CURRENCY_TYPE    NUMBER,
    DENOMINATION     NUMBER,
    OP_NO            VARCHAR2(32),
    OP_TIME          VARCHAR2(32),
    CLEAR_MACHINE_NO VARCHAR2(32),
    CASH_SHORT_OVER  NUMBER
);

comment on table TASK_CHECK_TABLE is '物品清点表';
comment on column TASK_CHECK_TABLE.ID is '记录编号';
comment on column TASK_CHECK_TABLE.TASK_NO is '任务单编号';
comment on column TASK_CHECK_TABLE.CUSTOMER_NO is '服务对象编号';
comment on column TASK_CHECK_TABLE.CONTAINER_NO is '容器编号';
comment on column TASK_CHECK_TABLE.AMOUNT is '金额/数量/重量';
comment on column TASK_CHECK_TABLE.CURRENCY_CODE is '币种';
comment on column TASK_CHECK_TABLE.CURRENCY_TYPE is '面值';
comment on column TASK_CHECK_TABLE.DENOMINATION is '钞币类别';
comment on column TASK_CHECK_TABLE.OP_NO is '清点人';
comment on column TASK_CHECK_TABLE.OP_TIME is '清点时间';
comment on column TASK_CHECK_TABLE.CLEAR_MACHINE_NO is '清点设备';
comment on column TASK_CHECK_TABLE.CASH_SHORT_OVER is '长短款标志 1-长款  2-短款  3-持平 ';

create table COUNT_TASK_INFO
(
    TASK_NO          VARCHAR2(50) default NULL not null
        constraint PK_COUNT_TASK_INFO
            primary key,
    DEV_NO           VARCHAR2(50) default NULL not null,
    BATCH            NUMBER(2)                 not null,
    COUNT_STATUS     NUMBER(2)                 not null,
    COUNT_START_DATE VARCHAR2(50) default NULL not null,
    COUNT_END_DATE   VARCHAR2(50) default NULL
)
comment on table COUNT_TASK_INFO is '清分任务信息表';
comment on column COUNT_TASK_INFO.TASK_NO is '任务编号';
comment on column COUNT_TASK_INFO.DEV_NO is '设备编号';
comment on column COUNT_TASK_INFO.BATCH is '清分批次';
comment on column COUNT_TASK_INFO.COUNT_STATUS is '清分状态';
comment on column COUNT_TASK_INFO.COUNT_START_DATE is '清分开始时间';
comment on column COUNT_TASK_INFO.COUNT_END_DATE is '清分结束时间';

commit;
