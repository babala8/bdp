-- auto-generated definition
create table STORAGE_ENTITY_TABLE
(
    ID             VARCHAR2(32) not null
        constraint PK_STORAGE_ENTITY_TABLE
            primary key,
    CLR_CENTER_NO  VARCHAR2(20) not null,
    CONTAINER_NO   VARCHAR2(32) not null,
    CONTAINER_TYPE VARCHAR2(32),
    ENTITY_TYPE    NUMBER,
    CURRENCY_TYPE  NUMBER,
    CURRENCY_CODE  VARCHAR2(10),
    DENOMINATION   NUMBER,
    AMOUNT         NUMBER,
    UPPER_NO       VARCHAR2(32),
    SHELF_NO       VARCHAR2(32),
    IS_LEAF        NUMBER,
    IN_OUT_FLAG    NUMBER       not null,
    TIME           VARCHAR2(32)
);

comment on table STORAGE_ENTITY_TABLE is '库房物品表';

comment on column STORAGE_ENTITY_TABLE.ID is '记录编号';

comment on column STORAGE_ENTITY_TABLE.CLR_CENTER_NO is '金库编号';

comment on column STORAGE_ENTITY_TABLE.CONTAINER_NO is '实物编号';

comment on column STORAGE_ENTITY_TABLE.CONTAINER_TYPE is '容器编号';

comment on column STORAGE_ENTITY_TABLE.ENTITY_TYPE is '实物类型 1-现金 2-贵金属 3-重空';

comment on column STORAGE_ENTITY_TABLE.CURRENCY_TYPE is '钞币类型';

comment on column STORAGE_ENTITY_TABLE.CURRENCY_CODE is '币种';

comment on column STORAGE_ENTITY_TABLE.DENOMINATION is '面值';

comment on column STORAGE_ENTITY_TABLE.AMOUNT is '金额';

comment on column STORAGE_ENTITY_TABLE.UPPER_NO is '上级容器编号';

comment on column STORAGE_ENTITY_TABLE.SHELF_NO is '所在笼车编号';

comment on column STORAGE_ENTITY_TABLE.IS_LEAF is '是否为叶子节点 1-是 2-不是';

comment on column STORAGE_ENTITY_TABLE.IN_OUT_FLAG is '在库标志 1-库内 2-库外';

comment on column STORAGE_ENTITY_TABLE.TIME is '时间';




-- auto-generated definition
create table STORAGE_TRANSFER_TABLE
(
    RECORD_NO     VARCHAR2(32) not null,
    CLR_CENTER_NO VARCHAR2(20),
    DIRECTION     NUMBER,
    TRANSFER_DATE VARCHAR2(10),
    TRANSFER_TIME VARCHAR2(10),
    DELIVERER_NO1 VARCHAR2(32),
    DELIVERER_NO2 VARCHAR2(32),
    RECEIVER_NO1  VARCHAR2(32),
    RECEIVER_NO2  VARCHAR2(32)
);

comment on column STORAGE_TRANSFER_TABLE.RECORD_NO is '记录编号';

comment on column STORAGE_TRANSFER_TABLE.CLR_CENTER_NO is '金库编号';

comment on column STORAGE_TRANSFER_TABLE.DIRECTION is '方向 1-调出 2调入';

comment on column STORAGE_TRANSFER_TABLE.TRANSFER_DATE is '日期';

comment on column STORAGE_TRANSFER_TABLE.TRANSFER_TIME is '时间';

comment on column STORAGE_TRANSFER_TABLE.DELIVERER_NO1 is '物品交出人1';

comment on column STORAGE_TRANSFER_TABLE.DELIVERER_NO2 is '物品交出人2';

comment on column STORAGE_TRANSFER_TABLE.RECEIVER_NO1 is '物品接收人1';

comment on column STORAGE_TRANSFER_TABLE.RECEIVER_NO2 is '物品接收人2';

create unique index PK_STORAGRE_TRANSFER_TABLE
    on STORAGE_TRANSFER_TABLE (RECORD_NO);

alter table STORAGE_TRANSFER_TABLE
    add constraint PK_STORAGE_TRANSFER_TABLE
        primary key (RECORD_NO);




-- auto-generated definition
create table STORAGE_TRANSFER_ENTITY
(
    ID             VARCHAR2(32) not null
        constraint PK_STORAGE_TRANSFER_ENTITY
            primary key,
    RECORD_NO      VARCHAR2(32)
        constraint FK_STE_ST_NO
            references STORAGE_TRANSFER_TABLE,
    CONTAINER_NO   VARCHAR2(32),
    CONTAINER_TYPE VARCHAR2(32),
    ENTITY_TYPE    NUMBER,
    CURRENCY_TYPE  NUMBER,
    CURRENCY_CODE  VARCHAR2(10),
    DENOMINATION   NUMBER,
    UPPER_NO       VARCHAR2(32),
    SHELF_NO       VARCHAR2(32),
    IS_LEAF        NUMBER,
    AMOUNT         NUMBER
);

comment on table STORAGE_TRANSFER_ENTITY is '库房出入库物品表';

comment on column STORAGE_TRANSFER_ENTITY.ID is '编号';

comment on column STORAGE_TRANSFER_ENTITY.RECORD_NO is '记录编号';

comment on column STORAGE_TRANSFER_ENTITY.CONTAINER_NO is '容器编号';

comment on column STORAGE_TRANSFER_ENTITY.CONTAINER_TYPE is '容器类型';

comment on column STORAGE_TRANSFER_ENTITY.ENTITY_TYPE is '物品类型 1-现金 2-贵金属 3-重空';

comment on column STORAGE_TRANSFER_ENTITY.CURRENCY_TYPE is '钞币类型';

comment on column STORAGE_TRANSFER_ENTITY.CURRENCY_CODE is '币种';

comment on column STORAGE_TRANSFER_ENTITY.DENOMINATION is '面值';

comment on column STORAGE_TRANSFER_ENTITY.UPPER_NO is '上级容器编号';

comment on column STORAGE_TRANSFER_ENTITY.SHELF_NO is '绑定笼车编号';

comment on column STORAGE_TRANSFER_ENTITY.IS_LEAF is '是否叶子节点 1-是 2-不是';

comment on column STORAGE_TRANSFER_ENTITY.AMOUNT is '金额';

create table ENTITY_REAL_TIME_DETAIL
(
	LOGIC_ID VARCHAR2(50) not null,
	CASH_BOX_NO VARCHAR2(32),
	BUNDLE_NO VARCHAR2(32),
	HANDLE_NO VARCHAR2(32),
	ORG_NO VARCHAR2(20) not null,
	OBJECT_NO VARCHAR2(20) not null,
	ENTITY_RESOURCE VARCHAR2(50) not null,
	ENTITY_TYPE VARCHAR2(20) not null,
	CURRENCY VARCHAR2(20) not null,
	CURRENCY_CODE VARCHAR2(3),
	CURRENCY_TYPE NUMBER(1),
	SORT_FLAG VARCHAR2(1) not null,
	AMOUNT NUMBER(20,2) not null,
	APPEAR_TIME VARCHAR2(20),
	VOUCHER_NO VARCHAR2(32),
	CASH_CODE VARCHAR2(40),
	constraint PK_ENTITY_REAL_TIME_DETAIL
		primary key (LOGIC_ID, ORG_NO, OBJECT_NO, ENTITY_RESOURCE, ENTITY_TYPE, CURRENCY, SORT_FLAG)
)
comment on table ENTITY_REAL_TIME_DETAIL is '金库实物信息表';
comment on column ENTITY_REAL_TIME_DETAIL.CASH_BOX_NO is '款箱编号';
comment on column ENTITY_REAL_TIME_DETAIL.BUNDLE_NO is '钱捆编号';
comment on column ENTITY_REAL_TIME_DETAIL.HANDLE_NO is '钱把编号';
comment on column ENTITY_REAL_TIME_DETAIL.ORG_NO is '持有机构编号';
comment on column ENTITY_REAL_TIME_DETAIL.OBJECT_NO is '场所/设备：场所指库房，作业区，在途；设备指ATM设备；柜员指网点柜员。';
comment on column ENTITY_REAL_TIME_DETAIL.ENTITY_RESOURCE is '实物来源：网点上缴；人行领款；现金作业区缴款；ATM备钞；';
comment on column ENTITY_REAL_TIME_DETAIL.ENTITY_TYPE is '实物类别：1：现金；2：贵金属；3：重空。';
comment on column ENTITY_REAL_TIME_DETAIL.CURRENCY is 'ENTITY_TYPE 为1(现金)时 券别:1-(一百元) 2-(五十元) 3-(二十元)  4-(十元) 5-(五元) 6-(两元) 7-(一元) 8-(五角) 9-(两角) 10-(一角) 11-(五分) 12-(两分) 13-(一分),
ENTITY_TYPE 为2(贵金属)时 单位:20-(g)';
comment on column ENTITY_REAL_TIME_DETAIL.CURRENCY_CODE is '币种(钞币代码)/金属类型: 1.金 2.银 3.其他';
comment on column ENTITY_REAL_TIME_DETAIL.CURRENCY_TYPE is '钞币类型';
comment on column ENTITY_REAL_TIME_DETAIL.SORT_FLAG is '整理标志：1：已整券；2：未整券。';
comment on column ENTITY_REAL_TIME_DETAIL.AMOUNT is '总值';
comment on column ENTITY_REAL_TIME_DETAIL.APPEAR_TIME is '记录生成时间';
comment on column ENTITY_REAL_TIME_DETAIL.VOUCHER_NO is '单据编号';
comment on column ENTITY_REAL_TIME_DETAIL.CASH_CODE is '冠字号标识符';

create table RPT_STORAGE_TEST
(
    ID            VARCHAR2(32) not null
        constraint RPT_STORAGE_TEST_PK
            primary key,
    RECORD_NO     VARCHAR2(20),
    FINISH_DATE   VARCHAR2(20),
    CLR_CENTER_NO VARCHAR2(15),
    OBJECT_TYPE   VARCHAR2(30),
    ENTITY_TYPE   NUMBER default 1,
    CURRENCY_TYPE NUMBER,
    DENOMINATION  NUMBER,
    AMOUNT        NUMBER,
    DIRECTION     NUMBER,
    NOTE          VARCHAR2(30)
)

comment on table RPT_STORAGE_TEST is '现金出入库明细（假数据）';
comment on column RPT_STORAGE_TEST.ID is '编号';
comment on column RPT_STORAGE_TEST.RECORD_NO is '记录编号';
comment on column RPT_STORAGE_TEST.FINISH_DATE is '执行时间';
comment on column RPT_STORAGE_TEST.CLR_CENTER_NO is '金库编号';
comment on column RPT_STORAGE_TEST.ENTITY_TYPE is '物品类型 1-现金 2-贵金属 3-重空';
comment on column RPT_STORAGE_TEST.CURRENCY_TYPE is '钞币类型  完整券: 1 ,流通券: 2,破损券: 3,假钞: 4';
comment on column RPT_STORAGE_TEST.DENOMINATION is '面值';
comment on column RPT_STORAGE_TEST.AMOUNT is '金额';
comment on column RPT_STORAGE_TEST.DIRECTION is '1、支行，2、人行，3、金库';

create table STORAGE_CHECK_TABLE
(
    NO                    VARCHAR2(32) not null,
    STORAGE_CHECK_MONEY   NUMBER       not null,
    DATABASE_RECORD_MONEY NUMBER       not null,
    TIME                  VARCHAR2(30),
    FLAG                  NUMBER,
    CLR_CENTER_NO         VARCHAR2(20)
)

comment on table STORAGE_CHECK_TABLE is '账务核库表';
comment on column STORAGE_CHECK_TABLE.NO is '编号';
comment on column STORAGE_CHECK_TABLE.STORAGE_CHECK_MONEY is '库房实际清点金额';
comment on column STORAGE_CHECK_TABLE.DATABASE_RECORD_MONEY is '数据库记录金额';
comment on column STORAGE_CHECK_TABLE.TIME is '核库时间';
comment on column STORAGE_CHECK_TABLE.FLAG is '金额是否一致 -- 1：一致，0：不一致';
comment on column STORAGE_CHECK_TABLE.CLR_CENTER_NO is '金库编号';

create unique index STORAGE_CHECK_TABLE_NO_UINDEX
    on STORAGE_CHECK_TABLE (NO);

alter table STORAGE_CHECK_TABLE
    add constraint STORAGE_CHECK_TABLE_PK
        primary key (NO);

commit;
