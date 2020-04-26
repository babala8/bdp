--删除顺序：先加后删，后加先删

drop table PRODUCT_STATUS_CONVERT cascade constraints;
drop table PRODUCT_SERVICE_STATUS cascade constraints;
drop table PRODUCT_MODULE_TABLE cascade constraints;
drop table PRODUCT_SERVICE_TABLE cascade constraints;
drop table PRODUCT_CUSTOMER_TYPE cascade constraints;
drop table PRODUCT_SERVICE_GOODS  cascade constraints;
drop table GOODS_PROPERTY_VALUE  cascade constraints;
drop table GOODS_PROPERTY_KEY  cascade constraints;
drop table GOODS_BASE_TABLE  cascade constraints;

/*==============================================================*/
/* Table: PROJECT_CUSTOMER_TYPE                                 */
/*==============================================================*/
create table PRODUCT_CUSTOMER_TYPE
(
    CUSTOMER_TYPE NUMBER not null
        constraint PK_CUSTOMER_TYPE
            primary key,
    NAME          VARCHAR2(100)
);

comment on table PRODUCT_CUSTOMER_TYPE is
    '服务对象类型表';

comment on column PRODUCT_CUSTOMER_TYPE.CUSTOMER_TYPE is
    '服务对象类型编号';

comment on column PRODUCT_CUSTOMER_TYPE.NAME is
    '服务对象类型名称';



/*==============================================================*/
/* Table: PRODUCT_SERVICE_TABLE                                 */
/*==============================================================*/
-- alter table PRODUCT_SERVICE_TABLE
--     drop constraint FK_PS_PCT;
-- auto-generated definition
-- Create table
create table PRODUCT_SERVICE_TABLE
(
    SERVICE_NO    NUMBER not null
        constraint PK_PRODUCT_SERVICE_TABLE
            primary key,
    SERVICE_NAME  VARCHAR2(100),
    CUSTOMER_TYPE NUMBER
        constraint FK_PS_PCT
            references PRODUCT_CUSTOMER_TYPE
                on delete cascade,
    NOTE          VARCHAR2(100),
    CREATE_TIME   VARCHAR2(20),
    UPDATE_TIME   VARCHAR2(20),
    STATUS        NUMBER default 0,
    TYPE          NUMBER default 0
);
-- Add comments to the table
comment on table PRODUCT_SERVICE_TABLE
    is '服务产品表';
-- Add comments to the columns
comment on column PRODUCT_SERVICE_TABLE.service_no
    is '服务编号';
comment on column PRODUCT_SERVICE_TABLE.service_name
    is '服务名称';
comment on column PRODUCT_SERVICE_TABLE.customer_type
    is '服务对象类型';
comment on column PRODUCT_SERVICE_TABLE.note
    is '备注';
comment on column PRODUCT_SERVICE_TABLE.create_time
    is '添加日期';
comment on column PRODUCT_SERVICE_TABLE.update_time
    is '更新日期';
comment on column PRODUCT_SERVICE_TABLE.STATUS
    is '0-已废弃  1-可使用';
comment on column PRODUCT_SERVICE_TABLE.TYPE
    is '0-固定产品 1-自定义产品';


/*==============================================================*/
/* Table: PRODUCT_MODULE_TABLE                                  */
/*==============================================================*/
create table PRODUCT_MODULE_TABLE
(
    MODULE_NO   NUMBER not null
        constraint PK_PRODUCT_MODULE_TABLE
            primary key,
    MODULE_NAME VARCHAR2(100)
);

comment on table PRODUCT_MODULE_TABLE is
    '产品模块表';

comment on column PRODUCT_MODULE_TABLE.MODULE_NO is
    '模块编号';

comment on column PRODUCT_MODULE_TABLE.MODULE_NAME is
    '名称';



/*==============================================================*/
/* Table: PRODUCT_SERVICE_STATUS                                */
/*==============================================================*/
-- alter table PRODUCT_SERVICE_STATUS
--     drop constraint FK_PS_PSS_SERV_NO;
-- alter table PRODUCT_SERVICE_STATUS
--     drop constraint FK_PS_PM_NO;
create table PRODUCT_SERVICE_STATUS
(
    SERVICE_NO NUMBER not null
        constraint FK_PS_PSS_SERV_NO
            references PRODUCT_SERVICE_TABLE
                on delete cascade,
    STATUS     NUMBER not null,
    NAME       VARCHAR2(100),
    NOTE       VARCHAR2(100),
    constraint PK_PRODUCT_SERVICE_STATUS
        primary key (SERVICE_NO, STATUS)
);

comment on table PRODUCT_SERVICE_STATUS is
    '产品服务状态表';

comment on column PRODUCT_SERVICE_STATUS.SERVICE_NO is
    '服务编号';

comment on column PRODUCT_SERVICE_STATUS.STATUS is
    '状态：模块编号+xx';

comment on column PRODUCT_SERVICE_STATUS.NAME is
    '名称';

comment on column PRODUCT_SERVICE_STATUS.NOTE is
    '备注';

alter table PRODUCT_SERVICE_STATUS
    add constraint FK_PS_PSS_SERV_NO foreign key (SERVICE_NO)
        references PRODUCT_SERVICE_TABLE (SERVICE_NO)
            on delete cascade;






/*==============================================================*/
/* Table: PRODUCT_STATUS_CONVERT                                */
/*==============================================================*/
-- alter table PRODUCT_STATUS_CONVERT
--     drop constraint FK_PSC_PSS_CUR;
--
-- alter table PRODUCT_STATUS_CONVERT
--     drop constraint FK_PSC_PSS_NEXT;

create table PRODUCT_STATUS_CONVERT
(
    ID          VARCHAR2(32) not null
        constraint PK_PRODUCT_STATUS_CONVERT
            primary key,
    SERVICE_NO  NUMBER,
    CUR_STATUS  NUMBER,
    NEXT_STATUS NUMBER,
    DESCRIPTION VARCHAR2(100),
    MODULE_NO   NUMBER
        constraint FK_PSC_PM_NO
            references PRODUCT_MODULE_TABLE
                on delete cascade,
    IMPL_NO     VARCHAR2(32),
    constraint FK_PSC_PSS_CUR
        foreign key (SERVICE_NO, CUR_STATUS) references PRODUCT_SERVICE_STATUS
            on delete cascade,
    constraint FK_PSC_PSS_NEXT
        foreign key (SERVICE_NO, NEXT_STATUS) references PRODUCT_SERVICE_STATUS
            on delete cascade
);

comment on table PRODUCT_STATUS_CONVERT is
    '产品类型转换表';

comment on column PRODUCT_STATUS_CONVERT.ID is
    '编号';

comment on column PRODUCT_STATUS_CONVERT.SERVICE_NO is
    '服务编号';

comment on column PRODUCT_STATUS_CONVERT.CUR_STATUS is
    '当前状态';

comment on column PRODUCT_STATUS_CONVERT.NEXT_STATUS is
    '下一状态';

comment on column PRODUCT_STATUS_CONVERT.DESCRIPTION is
    '交易描述';

comment on column PRODUCT_STATUS_CONVERT.MODULE_NO is
    '模块编号';

comment on column PRODUCT_STATUS_CONVERT.IMPL_NO is
    '实现信息编号（暂时不用）';

alter table PRODUCT_STATUS_CONVERT
    add constraint FK_PSC_PSS_CUR foreign key (SERVICE_NO, CUR_STATUS)
        references PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS)
            on delete cascade;

alter table PRODUCT_STATUS_CONVERT
    add constraint FK_PSC_PSS_NEXT foreign key (SERVICE_NO, NEXT_STATUS)
        references PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS)
            on delete cascade;

alter table PRODUCT_STATUS_CONVERT
    add constraint FK_PSC_PM_NO
        foreign key (MODULE_NO) references PRODUCT_MODULE_TABLE
            on delete cascade;

/*==============================================================*/
/* Table: PRODUCT_GOODS_TABLE                                   */
/*==============================================================*/
-- alter table PRODUCT_GOODS_TABLE
--     drop constraint FK_PRODUCT__PK_PG_PS__PRODUCT_;
--
-- drop table PRODUCT_GOODS_TABLE cascade constraints;
--
-- create table PRODUCT_GOODS_TABLE
-- (
--     SERVICE_NO           VARCHAR2(32)         not null,
--     FIELD                VARCHAR2(32),
--     ATTRIBUTE            VARCHAR2(50),
--     DATA_TYPE            VARCHAR2(32),
--     constraint PK_PRODUCT_GOODS_TABLE primary key (SERVICE_NO)
-- );
--
-- comment on table PRODUCT_GOODS_TABLE is
--     '产品物品表';
--
-- comment on column PRODUCT_GOODS_TABLE.SERVICE_NO is
--     '服务编号';
--
-- comment on column PRODUCT_GOODS_TABLE.FIELD is
--     '物品类型';
--
-- comment on column PRODUCT_GOODS_TABLE.ATTRIBUTE is
--     '属性';
--
-- comment on column PRODUCT_GOODS_TABLE.DATA_TYPE is
--     '数据类型';
--
-- alter table PRODUCT_GOODS_TABLE
--     add constraint FK_PRODUCT__PK_PG_PS__PRODUCT_ foreign key (SERVICE_NO)
--         references PRODUCT_SERVICE_TABLE (SERVICE_NO);

/*==============================================================*/
/* Table: GOODS_BASE_TABLE                                      */
/*==============================================================*/
create table GOODS_BASE_TABLE
(
    GOODS_NO       VARCHAR2(32) not null
        constraint PK_GOODS_BASE_TABLE
            primary key,
    UPPER_GOODS_NO VARCHAR2(32)
        constraint FK_GBT_GBT_GN
            references GOODS_BASE_TABLE
                on delete cascade,
    GOODS_NAME     VARCHAR2(100),
    CREATE_TIME    VARCHAR2(20),
    UPDATE_TIME    VARCHAR2(20)
);

alter table GOODS_BASE_TABLE
    add constraint FK_GBT_GBT_GN foreign key (UPPER_GOODS_NO)
        references GOODS_BASE_TABLE (GOODS_NO)
            on delete cascade ;

comment on table GOODS_BASE_TABLE is '商品基础信息表';
comment on column GOODS_BASE_TABLE.GOODS_NO is '商品编号';
comment on column GOODS_BASE_TABLE.UPPER_GOODS_NO is '父级商品编号';
comment on column GOODS_BASE_TABLE.GOODS_NAME is '商品名称';
comment on column GOODS_BASE_TABLE.CREATE_TIME is '创建时间';
comment on column GOODS_BASE_TABLE.UPDATE_TIME is '更新时间';


/*==============================================================*/
/* Table: GOODS_PROPERTY_KEY                                    */
/*==============================================================*/
create table GOODS_PROPERTY_KEY
(
    PROPERTY_NO   VARCHAR2(32) not null
        constraint PK_GOODS_PROPERTY_KEY
            primary key,
    GOODS_NO      VARCHAR2(32)
        constraint FK_GPK_GBT_GN
            references GOODS_BASE_TABLE
                on delete cascade,
    PROPERTY_NAME VARCHAR2(100),
    CREATE_TIME   VARCHAR2(20),
    UPDATE_TIME   VARCHAR2(20)
);

alter table GOODS_PROPERTY_KEY
    add constraint FK_GPK_GBT_GN foreign key (GOODS_NO)
        references GOODS_BASE_TABLE (GOODS_NO)
            on delete cascade ;

comment on table GOODS_PROPERTY_KEY is '商品属性KEY';
comment on column GOODS_PROPERTY_KEY.PROPERTY_NO is '属性编号';
comment on column GOODS_PROPERTY_KEY.GOODS_NO is '商品编号';
comment on column GOODS_PROPERTY_KEY.PROPERTY_NAME is '属性名称';
comment on column GOODS_PROPERTY_KEY.CREATE_TIME is '创建时间';
comment on column GOODS_PROPERTY_KEY.UPDATE_TIME is '更新时间';

/*==============================================================*/
/* Table: GOODS_PROPERTY_VALUE                                  */
/*==============================================================*/
create table GOODS_PROPERTY_VALUE
(
    ID             VARCHAR2(32) not null
        constraint PK_GOODS_PROPERTY_VALUE
            primary key,
    PROPERTY_NO    VARCHAR2(32)
        constraint FK_GPV_GPK_PN
            references GOODS_PROPERTY_KEY
                on delete cascade,
    PROPERTY_VALUE VARCHAR2(100),
    CREATE_TIME    VARCHAR2(20),
    UPDATE_TIME    VARCHAR2(20)
);

alter table GOODS_PROPERTY_VALUE
    add constraint FK_GPV_GPK_PN foreign key (PROPERTY_NO)
        references GOODS_PROPERTY_KEY (PROPERTY_NO)
            on delete cascade ;

comment on table GOODS_PROPERTY_VALUE is '商品属性VALUE';
comment on column GOODS_PROPERTY_VALUE.ID is 'ID';
comment on column GOODS_PROPERTY_VALUE.PROPERTY_NO is '属性编号';
comment on column GOODS_PROPERTY_VALUE.PROPERTY_VALUE is '属性值';
comment on column GOODS_PROPERTY_VALUE.CREATE_TIME is '创建时间';
comment on column GOODS_PROPERTY_VALUE.UPDATE_TIME is '更新时间';

/*==============================================================*/
/* Table: PRODUCT_SERVICE_GOODS                                 */
/*==============================================================*/
create table PRODUCT_SERVICE_GOODS
(
    SERVICE_NO    NUMBER       not null
        constraint FK_PSG_PST_SN
            references PRODUCT_SERVICE_TABLE
                on delete cascade,
    GOODS_NO      VARCHAR2(32) not null
        constraint FK_PSG_GBT_GN
            references GOODS_BASE_TABLE
                on delete cascade,
    PROPERTY_LIST VARCHAR2(100),
    DIRECTION     NUMBER,
    constraint PK_PRODUCT_SERVICE_GOODS
        primary key (SERVICE_NO, GOODS_NO)
)

comment on table PRODUCT_SERVICE_GOODS is '产品调入调出物品信息表';
comment on column PRODUCT_SERVICE_GOODS.SERVICE_NO is '产品编号';
comment on column PRODUCT_SERVICE_GOODS.GOODS_NO is '物品编号';
comment on column PRODUCT_SERVICE_GOODS.PROPERTY_LIST is '属性列表';
comment on column PRODUCT_SERVICE_GOODS.DIRECTION is '调入调出方向';

create table GOODS_ARGUMENT_TABLE
(
    GOODS_NO       VARCHAR2(32),
    PARAMETER      VARCHAR2(32),
    PARAMETER_NAME VARCHAR2(32),
    PARAMETER_TYPE VARCHAR2(32)
)

commit;
