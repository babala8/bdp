CREATE TABLE CASH_BOX_TABLE
(
    CASH_BOX_NO VARCHAR2(32) PRIMARY KEY NOT NULL,
    ORG_NO VARCHAR2(20) NOT NULL,
    SPECIFICATIONS VARCHAR2(200) NOT NULL,
    VENDOR VARCHAR2(32) NOT NULL,
    STATUS NUMBER NOT NULL,
    CONSTRAINT CASH_BOX_TABLE_SYS_ORG_NO_fk FOREIGN KEY (ORG_NO) REFERENCES SYS_ORG (NO)
);
COMMENT ON COLUMN CASH_BOX_TABLE.CASH_BOX_NO IS '款箱编号';
COMMENT ON COLUMN CASH_BOX_TABLE.ORG_NO IS '所属机构编号';
COMMENT ON COLUMN CASH_BOX_TABLE.SPECIFICATIONS IS '规格';
COMMENT ON COLUMN CASH_BOX_TABLE.VENDOR IS '品牌';
COMMENT ON COLUMN CASH_BOX_TABLE.STATUS IS '状态：1-启用，0-未启用';
COMMENT ON TABLE CASH_BOX_TABLE IS '款箱基本信息表';

alter table WARN_MESSAGE_TABLE add(CLR_CENTER_NO VARCHAR2(10));

ALTER TABLE GOODS_PROPERTY_KEY ADD FLAG NUMBER NOT NULL;
COMMENT ON COLUMN GOODS_PROPERTY_KEY.FLAG IS '属性类型：1-下拉框型，2-手动输入数字型，3-手动输入字符串型，4-日期型，5-选择机构型';

alter table TASK_TABLE
	add END_TIME VARCHAR2(20);
comment on column TASK_TABLE.END_TIME is '结束时间';

create table TASK_NODE_TABLE
(
    TASK_NODE_NO VARCHAR2(32) not null
        constraint TASK_NODE_TABLE_PK
            primary key,
    TASK_NO      VARCHAR2(32),
    OP_NO        VARCHAR2(10),
    START_TIME   VARCHAR2(10),
    END_TIME     VARCHAR2(10),
    CUR_NODE     NUMBER,
    NEXT_NODE    NUMBER,
    DESCRIPTION  VARCHAR2(32)
)

comment on table TASK_NODE_TABLE is '任务节点表';
comment on column TASK_NODE_TABLE.TASK_NODE_NO is '任务节点编号';
comment on column TASK_NODE_TABLE.TASK_NO is '任务编号';
comment on column TASK_NODE_TABLE.OP_NO is '操作人';
comment on column TASK_NODE_TABLE.START_TIME is '开始时间';
comment on column TASK_NODE_TABLE.END_TIME is '结束时间';
comment on column TASK_NODE_TABLE.CUR_NODE is '当前节点(第一节点默认201)';
comment on column TASK_NODE_TABLE.NEXT_NODE is '下一节点';
comment on column TASK_NODE_TABLE.DESCRIPTION is '描述';

create table TASK_NODE_VARIATE
(
    TASK_NODE_NO VARCHAR2(32),
    NAME         VARCHAR2(32),
    VAR_TYPE     VARCHAR2(10),
    VAR_VALUE    VARCHAR2(100),
    NUM_VALUE    NUMBER,
    TEXT_VALUE   VARCHAR2(500)
)

comment on table TASK_NODE_VARIATE is '任务节点变量表';
comment on column TASK_NODE_VARIATE.TASK_NODE_NO is '任务节点编号';
comment on column TASK_NODE_VARIATE.NAME is '变量名';
comment on column TASK_NODE_VARIATE.VAR_TYPE is '变量类型';
comment on column TASK_NODE_VARIATE.VAR_VALUE is '字符串型变量值';
comment on column TASK_NODE_VARIATE.NUM_VALUE is '数字型变量值';
comment on column TASK_NODE_VARIATE.TEXT_VALUE is '文本型变量值';

alter table TASK_NODE_TABLE modify start_time VARCHAR2(20);
alter table TASK_NODE_TABLE modify end_time VARCHAR2(20);

--创建状态拓展表
create table PRODUCT_STATUS_EXPAND
(
    OPERATE_TYPE VARCHAR2(100) not null
        primary key,
    DESCRIPTION  VARCHAR2(100),
    NEXT_STATUS  NUMBER
);
comment on column PRODUCT_STATUS_EXPAND.OPERATE_TYPE is '操作类型';
comment on column PRODUCT_STATUS_EXPAND.DESCRIPTION is '操作描述';
comment on column PRODUCT_STATUS_EXPAND.NEXT_STATUS is '下一状态';

INSERT INTO PRODUCT_STATUS_EXPAND (OPERATE_TYPE, DESCRIPTION, NEXT_STATUS) VALUES ('jingjingjieku', '经警接库', 305);
INSERT INTO PRODUCT_STATUS_EXPAND (OPERATE_TYPE, DESCRIPTION, NEXT_STATUS) VALUES ('wangdianjieku', '网点接库', 307);
INSERT INTO PRODUCT_STATUS_EXPAND (OPERATE_TYPE, DESCRIPTION, NEXT_STATUS) VALUES ('rukujiaojie', '入库交接', 306);
INSERT INTO PRODUCT_STATUS_EXPAND (OPERATE_TYPE, DESCRIPTION, NEXT_STATUS) VALUES ('chukujiaojie', '出库交接', 308);
INSERT INTO PRODUCT_STATUS_EXPAND (OPERATE_TYPE, DESCRIPTION, NEXT_STATUS) VALUES ('wuliuruku', '物流入库', 310);
INSERT INTO PRODUCT_STATUS_EXPAND (OPERATE_TYPE, DESCRIPTION, NEXT_STATUS) VALUES ('wuliuchuku', '物流出库', 311);
INSERT INTO PRODUCT_STATUS_EXPAND (OPERATE_TYPE, DESCRIPTION, NEXT_STATUS) VALUES ('chaochuruku', '钞处入库', 312);
INSERT INTO PRODUCT_STATUS_EXPAND (OPERATE_TYPE, DESCRIPTION, NEXT_STATUS) VALUES ('chaochuchuku', '钞处出库', 313);
INSERT INTO PRODUCT_STATUS_EXPAND (OPERATE_TYPE, DESCRIPTION, NEXT_STATUS) VALUES ('qingfen', '清分', 501);
INSERT INTO PRODUCT_STATUS_EXPAND (OPERATE_TYPE, DESCRIPTION, NEXT_STATUS) VALUES ('qingdian', '清点', 502);
INSERT INTO PRODUCT_STATUS_EXPAND (OPERATE_TYPE, DESCRIPTION, NEXT_STATUS) VALUES ('chuagnjian', '创建', 201);
INSERT INTO PRODUCT_STATUS_EXPAND (OPERATE_TYPE, DESCRIPTION, NEXT_STATUS) VALUES ('wancheng', '完成', 205);
INSERT INTO PRODUCT_STATUS_EXPAND (OPERATE_TYPE, DESCRIPTION, NEXT_STATUS) VALUES ('guoqi', '过期', 208);
INSERT INTO PRODUCT_STATUS_EXPAND (OPERATE_TYPE, DESCRIPTION, NEXT_STATUS) VALUES ('quxiao', '取消', 200);

--删除下一状态的记录
update PRODUCT_STATUS_CONVERT set NEXT_STATUS=null;
--更改状态表下一状态为操作类型
alter table PRODUCT_STATUS_CONVERT rename column NEXT_STATUS to OPERATE_TYPE;
alter table PRODUCT_STATUS_CONVERT drop constraint FK_PSC_PSS_NEXT;
alter table PRODUCT_STATUS_CONVERT modify (OPERATE_TYPE VARCHAR2(100));

--状态表增加四个状态码
insert into PRODUCT_SERVICE_STATUS (service_no, status, name) values (5,310,'物流已入库');
insert into PRODUCT_SERVICE_STATUS (service_no, status, name) values (5,311,'物流已出库');
insert into PRODUCT_SERVICE_STATUS (service_no, status, name) values (5,312,'钞处已入库');
insert into PRODUCT_SERVICE_STATUS (service_no, status, name) values (5,313,'钞处已出库');

alter table TASK_NODE_TABLE
  add OPERATE_TYPE varchar2(200);

comment on column TASK_NODE_TABLE.OPERATE_TYPE is '操作类型';

alter table TASK_NODE_VARIATE drop column NUM_VALUE;

alter table TASK_NODE_VARIATE drop column TEXT_VALUE;

alter table CLR_CENTER_TABLE add auto_flag number default 0;
comment on column CLR_CENTER_TABLE.auto_flag
  is '自动化标识:0-非自动化 1-自动化';

alter table CLR_CENTER_TABLE add center_type number default 0;
comment on column CLR_CENTER_TABLE.center_type
  is '金库类型:0-总库 1-业务库 2-营运库 3-代理库 4-黄金交割库 5-备用金库';

insert into SYS_MENU (NO, NAME, MENU_FATHER, URL, MENU_LEVEL, MENU_ORDER, NOTE, MENU_ICON, MENU_SIZE, MENU_BG, BUTTON_TAG, BUTTON)
values ('A0403', '金库参数调整', 'A04', null, null, null, null, null, 2, null, 1, null);

insert into SYS_PERMISSION (NO, NAME, URL, METHOD, CATALOG, NOTE)
values ('2134', '金库参数调整', '/channel-center/v2/clrCenter/updateCenterNum', 'PUT', 2, '渠道中心—金库管理');

insert into SYS_MENU_PERMISSION (MENU_NO, PERMISSION_NO)
values ('A0403', '2134');

alter table TASK_NODE_TABLE rename column START_TIME to FINISH_TIME;

comment on column TASK_NODE_TABLE.FINISH_TIME is '完成时间';

alter table TASK_NODE_TABLE drop column END_TIME;

create table SYS_POST
(
    POST_NO VARCHAR2(36)    not null
        primary key,
    POST_NAME   VARCHAR2(100) not null,
    POST_TYPE   NUMBER(1),
    NOTE       VARCHAR2(200)
);
comment on table SYS_POST is '岗位信息表';
comment on column SYS_POST.POST_NO is '岗位编号';
comment on column SYS_POST.POST_NAME is '岗位名称';
comment on column SYS_POST.POST_TYPE is '岗位类别（1：总行 2：分行 3：支行 4：网点 5：金库）';
comment on column SYS_POST.NOTE is '注释';

create table SYS_POST_LIMIT
(
    POST_NO VARCHAR2(36) not null,
    POST_LIMIT_NO  VARCHAR2(36) not null,
    POST_LIMIT_NAME   VARCHAR2(100) not null,
    NOTE       VARCHAR2(200),
    primary key (POST_NO, POST_LIMIT_NO)
);
comment on table SYS_POST_LIMIT is '岗位制约信息表';
comment on column SYS_POST_LIMIT.POST_NO is '岗位编号';
comment on column SYS_POST_LIMIT.POST_LIMIT_NO is '制约岗位编号';
comment on column SYS_POST_LIMIT.POST_NAME is '制约岗位名称';
comment on column SYS_POST_LIMIT.NOTE is '注释';

create table SYS_USER_POST
(
    USERNAME VARCHAR2(20)    not null
        references SYS_USER,
    POST_NO   VARCHAR2(36) not null
        references SYS_POST
);
comment on table SYS_USER_POST is '用户岗位关联表';
comment on column SYS_USER_POST.USERNAME is '用户名';
comment on column SYS_USER_POST.POST_NO is '岗位编号';

create table POST_SCHEDULE_CLASSESS_TABLE
(
    CLASSESS_NO VARCHAR2(36)  not null
        primary key,
    ORG_NO VARCHAR2(36)   not null,
    CLASSESS_NAME VARCHAR2(36)  not null,
    CLASSESS_COLOR VARCHAR2(36)  not null,
    CLASSESS_SHORTER_NAME VARCHAR2(36)  not null,
    CLASSESS_TIME_NUM NUMBER(10)  not null,
    NOTE       VARCHAR2(200)
);
comment on table POST_SCHEDULE_CLASSESS_TABLE is '班次信息表';
comment on column POST_SCHEDULE_CLASSESS_TABLE.CLASSESS_NO is '班次编号';
comment on column POST_SCHEDULE_CLASSESS_TABLE.ORG_NO is '所属机构';
comment on column POST_SCHEDULE_CLASSESS_TABLE.CLASSESS_NAME is '班次名称';
comment on column POST_SCHEDULE_CLASSESS_TABLE.CLASSESS_COLOR is '班次代指颜色';
comment on column POST_SCHEDULE_CLASSESS_TABLE.CLASSESS_SHORTER_NAME is '班次简称';
comment on column POST_SCHEDULE_CLASSESS_TABLE.CLASSESS_TIME_NUM is '班次时间段数量';
comment on column POST_SCHEDULE_CLASSESS_TABLE.NOTE is '注释';

create table POST_CLASSESS_TIME_TABLE
(
    CLASSESS_NO VARCHAR2(36)  not null,
    CLASSESS_START_NAME VARCHAR2(100)  not null,
    CLASSESS_START_TIME VARCHAR2(10)  not null,
    CLASSESS_END_NAME VARCHAR2(100)  not null,
    CLASSESS_END_TIME VARCHAR2(10)  not null
);
comment on table POST_CLASSESS_TIME_TABLE is '班次时段表';
comment on column POST_CLASSESS_TIME_TABLE.CLASSESS_NO is '班次编号';
comment on column POST_CLASSESS_TIME_TABLE.CLASSESS_START_NAME is '时段开始名称';
comment on column POST_CLASSESS_TIME_TABLE.CLASSESS_START_TIME is '时段开始时间';
comment on column POST_CLASSESS_TIME_TABLE.CLASSESS_END_NAME is '时段结束名称';
comment on column POST_CLASSESS_TIME_TABLE.CLASSESS_END_TIME is '时段结束时间';

create table POST_SCHEDULE_RULE_TABLE
(
    RULE_NO VARCHAR2(36)  not null
        primary key,
    RULE_TYPE NUMBER(1)  not null,
    ORG_NO VARCHAR2(36)  not null,
    RULE_NAME VARCHAR2(100)  not null,
    RANGE_TYPE NUMBER(1)  not null,
    RANGE_NO VARCHAR2(36)  not null,
    ROUND_START_DATE VARCHAR2(36)  not null,
    ROUND_END_DATE VARCHAR2(36)  not null,
    ROUND_TYPE NUMBER(1)  not null,
    ROUND_NUM NUMBER(10)  not null,
    NOTE       VARCHAR2(200),

);
comment on table POST_SCHEDULE_RULE_TABLE is '排班规则表';
comment on column POST_SCHEDULE_RULE_TABLE.RULE_NO is '规则编号';
comment on column POST_SCHEDULE_RULE_TABLE.RULE_TYPE is '规则种类 1：一般，2：节假日，3：自定义';
comment on column POST_SCHEDULE_RULE_TABLE.RULE_NAME is '规则名称';
comment on column POST_SCHEDULE_RULE_TABLE.ORG_NO is '所属机构';
comment on column POST_SCHEDULE_RULE_TABLE.RANGE_TYPE is '范围种类 1：机构 2：岗位 3：个人';
comment on column POST_SCHEDULE_RULE_TABLE.RANGE_NO is '范围编号';
comment on column POST_SCHEDULE_RULE_TABLE.ROUND_START_DATE is '范围开始日期';
comment on column POST_SCHEDULE_RULE_TABLE.ROUND_END_DATE is '范围结束日期';
comment on column POST_SCHEDULE_RULE_TABLE.ROUND_TYPE is '循环种类 1：周，2：月，3：自定义';
comment on column POST_SCHEDULE_RULE_TABLE.ROUND_NUM is '循环天数';
comment on column POST_SCHEDULE_RULE_TABLE.NOTE is '注释';

create table POST_SCHEDULE_RULE_CLASSESS
(
    RULE_NO VARCHAR2(36)  not null,
    CLASSESS_NO VARCHAR2(36)  not null,
    ROUND_DATE VARCHAR2(36),
    ROUND_NO NUMBER(10),
    POST_COMMON_COUNT NUMBER(8) not null,
    POST_NIGHT_COUNT NUMBER(8) not null,
    NOTE       VARCHAR2(200),
    primary key (RULE_NO, CLASSESS_NO, ROUND_NO, ROUND_DATE)

);
comment on table POST_SCHEDULE_RULE_CLASSESS is '排班规则班次关联表';
comment on column POST_SCHEDULE_RULE_CLASSESS.RULE_NO is '规则编号';
comment on column POST_SCHEDULE_RULE_CLASSESS.CLASSESS_NO is '班次编号';
comment on column POST_SCHEDULE_RULE_CLASSESS.ROUND_DATE is '范围日期';
comment on column POST_SCHEDULE_RULE_CLASSESS.ROUND_NO is '范围次序';
comment on column POST_SCHEDULE_RULE_CLASSESS.POST_COMMON_COUNT is '一般最低需求人数';
comment on column POST_SCHEDULE_RULE_CLASSESS.POST_NIGHT_COUNT is '值班最低需求人数';
comment on column POST_SCHEDULE_RULE_CLASSESS.NOTE is '备注';

create table POST_SCHEDULE_MOULD_TABLE
(
    MOULD_NO VARCHAR2(36)  not null
        primary key,
    MOULD_TYPE NUMBER(1)  not null,
    MOULD_NAME VARCHAR2(100) not null,
    ORG_NO VARCHAR2(20) not null,
    POST_NO VARCHAR2(36) not null,
    CREATE_TIME VARCHAR2(12) not null,
);
comment on table POST_SCHEDULE_MOULD_TABLE is '排班模板表';
comment on column POST_SCHEDULE_MOULD_TABLE.MOULD_NO is '模板编号';
comment on column POST_SCHEDULE_MOULD_TABLE.MOULD_TYPE is '模板类型 1：周 2：月';
comment on column POST_SCHEDULE_MOULD_TABLE.MOULD_NAME is '模板名称';
comment on column POST_SCHEDULE_MOULD_TABLE.ORG_NO is '所属机构';
comment on column POST_SCHEDULE_MOULD_TABLE.POST_NO is '岗位编号';
comment on column POST_SCHEDULE_MOULD_TABLE.CREATE_TIME is '创建时间';

create table POST_SCHEDULE_MOULD_PERSION
(
    MOULD_NO VARCHAR2(36)  not null,
    OP_NO VARCHAR2(20) not null,
    CLASSES_NO VARCHAR2(36) not null,
    COUNT_NO NUMBER(8) not null,
    primary key (MOULD_NO, OP_NO, CLASSES_NO, COUNT_NO)
);
comment on table POST_SCHEDULE_MOULD_PERSION is '排班模板人员表';
comment on column POST_SCHEDULE_MOULD_PERSION.MOULD_NO is '模板编号';
comment on column POST_SCHEDULE_MOULD_PERSION.OP_NO is '人员编号';
comment on column POST_SCHEDULE_MOULD_PERSION.CLASSES_NO is '班次';
comment on column POST_SCHEDULE_MOULD_PERSION.COUNT_NO is '时间轴分布';

create table POST_SCHEDULE_TABLE
(
  PLAN_NO VARCHAR2(36) not null
    primary key,
  ORG_NO VARCHAR2(20) not null,
  POST_NO VARCHAR2(36) not null,
  SCHEDULE_MONTH VARCHAR2(10) not null,
  MOULD_NO VARCHAR2(36)  not null,
  CREATE_TIME VARCHAR2(12) not null,
);
comment on table POST_SCHEDULE_TABLE is '排班计划表';
comment on column POST_SCHEDULE_TABLE.PLAN_NO is '计划编号';
comment on column POST_SCHEDULE_TABLE.ORG_NO is '所属机构';
comment on column POST_SCHEDULE_TABLE.POST_NO is '岗位';
comment on column POST_SCHEDULE_TABLE.SCHEDULE_MONTH is '月份';
comment on column POST_SCHEDULE_TABLE.MOULD_NO is '所用模板';
comment on column POST_SCHEDULE_TABLE.CREATE_TIME is '创建时间';

create table POST_SCHEDULE_PLAN_TABLE
(
  PLAN_NO VARCHAR2(36) not null,
  OP_NO VARCHAR2(100) not null,
  PLAN_DATE VARCHAR2(12) not null,
  SCHEDULE_MONTH VARCHAR2(10) not null,
  CLASSES_NO VARCHAR2(36) not null,
  primary key (PLAN_NO, OP_NO, PLAN_DATE, CLASSES_NO)
);
comment on table POST_SCHEDULE_PLAN_TABLE is '排班计划人员表';
comment on column POST_SCHEDULE_PLAN_TABLE.PLAN_NO is '计划编号';
comment on column POST_SCHEDULE_PLAN_TABLE.OP_NO is '人员编号';
comment on column POST_SCHEDULE_PLAN_TABLE.SCHEDULE_MONTH is '月份';
comment on column POST_SCHEDULE_PLAN_TABLE.PLAN_DATE is '日期';
comment on column POST_SCHEDULE_PLAN_TABLE.CLASSES_NO is '班次';

--create table SYS_ORG_POST
--(
--    NO VARCHAR2(20)    not null
--        references SYS_ORG,
--    POST_NO   VARCHAR2(36) not null
--        references SYS_POST,
--    POST_COMMON_COUNT NUMBER(8) not null,
--    POST_VACATION_COUNT NUMBER(8) not null,
--    POST_WEEKEND_COUNT NUMBER(8) not null,
--    POST_NIGHT_COUNT NUMBER(8) not null,
--    primary key (NO, POST_NO)
--);
--comment on table SYS_ORG_POST is '机构岗位关联表';
--comment on column SYS_USER_POST.NO is '机构编号';
--comment on column SYS_USER_POST.POST_NO is '岗位编号';
--comment on column SYS_USER_POST.POST_COMMON_COUNT is '工作日岗位需求人数';
--comment on column SYS_USER_POST.POST_VACATION_COUNT is '节假日岗位需求人数';
--comment on column SYS_USER_POST.POST_WEEKEND_COUNT is '周末岗位需求人数';
--comment on column SYS_USER_POST.POST_NIGHT_COUNT is '夜间岗位需求人数';

--create table SYS_WORK_REST
--(
--    DATE_NO   VARCHAR2(36)    not null
--        primary key,
--    YEAR_NO   VARCHAR2(4) not null,
--    MONTH_NO  VARCHAR2(2) not null,
--    DAY_NO    VARCHAR2(2) not null,
--    DAY_TYPE  NUMBER(1)   not null,
--    NIGHT_FLAG NUMBER(1)   not null,
--    NOTE       VARCHAR2(200)
--);
--comment on table SYS_WORK_REST is '人员作息表';
--comment on column SYS_WORK_REST.DATE_NO is '日期';
--comment on column SYS_WORK_REST.YEAR_NO is '年 2019';
--comment on column SYS_WORK_REST.MONTH_NO is '月 01';
--comment on column SYS_WORK_REST.DAY_NO is '日 01';
--comment on column SYS_WORK_REST.DAY_TYPE is '日期类型 1：工作日 2：节假日 3：周末 ';
--comment on column SYS_WORK_REST.NIGHT_FLAG is '是否夜间值班 1：值班 2：不值班';
--comment on column SYS_WORK_REST.NOTE is '注释';

--create table POST_SCHEDULE_TABLE
--(
--    DATE_NO   VARCHAR2(36)  not null,
--    YEAR_NO   VARCHAR2(4) not null,
--    MONTH_NO  VARCHAR2(2) not null,
--    DAY_NO    VARCHAR2(2) not null,
--    POST_NO  VARCHAR2(36)   not null,
--    ORG_NO VARCHAR2(36)   not null,
--    NIGHT_FLAG NUMBER(1)   not null,
--    primary key (DATE_NO, POST_NO, ORG_NO, NIGHT_FLAG)
--);
--comment on table POST_SCHEDULE_TABLE is '排班信息表';
--comment on column POST_SCHEDULE_TABLE.DATE_NO is '日期';
--comment on column POST_SCHEDULE_TABLE.YEAR_NO is '年 2019';
--comment on column POST_SCHEDULE_TABLE.MONTH_NO is '月 01';
--comment on column POST_SCHEDULE_TABLE.DAY_NO is '日 01';
--comment on column POST_SCHEDULE_TABLE.POST_NO is '岗位编号';
--comment on column POST_SCHEDULE_TABLE.ORG_NO is '机构编号';
--comment on column POST_SCHEDULE_TABLE.NIGHT_FLAG is '是否夜间值班 1：值班 2：不值班';

--create table POST_SCHEDULE_PERSON_TABLE
--(
--    DATE_NO   VARCHAR2(36)  not null,
--    POST_NO  VARCHAR2(36)   not null,
--    ORG_NO VARCHAR2(36)   not null,
--    NIGHT_FLAG NUMBER(1)   not null,
--    USERNAME VARCHAR2(20)    not null,
--    primary key (DATE_NO, POST_NO, ORG_NO, NIGHT_FLAG, USERNAME)
--);
--comment on table POST_SCHEDULE_PERSON_TABLE is '排班人员安排表';
--comment on column POST_SCHEDULE_PERSON_TABLE.DATE_NO is '日期';
--comment on column POST_SCHEDULE_PERSON_TABLE.POST_NO is '岗位编号';
--comment on column POST_SCHEDULE_PERSON_TABLE.ORG_NO is '机构编号';
--comment on column POST_SCHEDULE_PERSON_TABLE.NIGHT_FLAG is '是否夜间值班 1：值班 2：不值班';
--comment on column POST_SCHEDULE_PERSON_TABLE.USERNAME is '用户名';

alter table PRODUCT_STATUS_CONVERT
  add TEMPLATE VARCHAR2(500);
comment on column PRODUCT_STATUS_CONVERT.TEMPLATE is 'JSON模板';

alter table PRODUCT_STATUS_CONVERT modify TEMPLATE VARCHAR2(800);

update CLR_CENTER_TABLE set BANK_ORG_NO = '0147', CLR_CENTER_NO = '0147' where CLR_CENTER_NO = '0280010101';
update CLR_CENTER_TABLE set BANK_ORG_NO = '0148', CLR_CENTER_NO = '0148' where CLR_CENTER_NO = '0280010102';
update CLR_CENTER_TABLE set BANK_ORG_NO = '0150', CLR_CENTER_NO = '0150' where CLR_CENTER_NO = '0280010301';
update CLR_CENTER_TABLE set BANK_ORG_NO = '00080001', CLR_CENTER_NO = '00080001' where CLR_CENTER_NO = '028002';
update CLR_CENTER_TABLE set BANK_ORG_NO = '00080003', CLR_CENTER_NO = '00080003' where CLR_CENTER_NO = '028003';
update CLR_CENTER_TABLE set BANK_ORG_NO = '0440123', CLR_CENTER_NO = '0440123' where CLR_CENTER_NO = '0280020301';

UPDATE SYS_MENU SET NAME = '服务中心' WHERE NO = 'P';
UPDATE SYS_MENU SET NAME = '服务管理' WHERE NO = 'P02';
UPDATE SYS_MENU SET NAME = '服务基础信息查询' WHERE NO = 'P0201';
UPDATE SYS_MENU SET NAME = '新增服务信息' WHERE NO = 'P0202';
UPDATE SYS_MENU SET NAME = '服务状态修改' WHERE NO = 'P0203';
UPDATE SYS_MENU SET NAME = '服务状态节点更新' WHERE NO = 'P0204';
UPDATE SYS_MENU SET NAME = '服务状态顺序更新' WHERE NO = 'P0205';
UPDATE SYS_MENU SET NAME = '服务模块信息查询' WHERE NO = 'P0206';
UPDATE SYS_MENU SET NAME = '服务物品信息更新' WHERE NO = 'P0207';
UPDATE SYS_MENU SET NAME = '服务状态修改' WHERE NO = 'P0208';
UPDATE SYS_MENU SET NAME = '服务所含状态查询' WHERE NO = 'P0209';
UPDATE SYS_MENU SET NAME = '产品管理' WHERE NO = 'P03';
UPDATE SYS_MENU SET NAME = '增加产品信息' WHERE NO = 'P0301';
UPDATE SYS_MENU SET NAME = '查询产品基础信息' WHERE NO = 'P0302';
UPDATE SYS_MENU SET NAME = '修改产品信息' WHERE NO = 'P0303';
UPDATE SYS_MENU SET NAME = '删除产品信息' WHERE NO = 'P0304';
UPDATE SYS_MENU SET NAME = '产品基础信息详情查询' WHERE NO = 'P0305';
UPDATE SYS_MENU SET NAME = '增加产品属性信息' WHERE NO = 'P0306';
UPDATE SYS_MENU SET NAME = '产品所属信息查询' WHERE NO = 'P0307';
UPDATE SYS_MENU SET NAME = '产品属性值查询' WHERE NO = 'P0308';

--网点营业时间表
create table ORG_BUSINESS_TIME
(
    ORG_NO     VARCHAR2(20) not null,
    ORG_TIME_INTERVAL  CHAR not null,
    OPEN_TIME      VARCHAR2(8) not null,
    CLOSE_TIME      VARCHAR2(8) not null,
    ORG_DAY           NUMBER(2) not null,
    constraint PK_ORG_BUSINESS_TIME
        primary key (ORG_NO, ORG_TIME_INTERVAL,ORG_DAY)
);

comment on table ORG_BUSINESS_TIME is '网点营业时间表';
comment on column ORG_BUSINESS_TIME.ORG_NO is '机构号';
comment on column ORG_BUSINESS_TIME.ORG_TIME_INTERVAL is '营业时段(1-上午,2-下午)';
comment on column ORG_BUSINESS_TIME.OPEN_TIME is '开始时间';
comment on column ORG_BUSINESS_TIME.CLOSE_TIME is '结束时间';
comment on column ORG_BUSINESS_TIME.ORG_DAY is '日期：1-周一,2-周二';

alter table TASK_NODE_TABLE rename column NEXT_NODE to BEFORE_NODE;
comment on column TASK_NODE_TABLE.BEFORE_NODE is '上一节点';

alter table PUSH_SERVER_INFO modify  MESSAGE varchar2(500);

alter table dev_base_info add dev_least_size number default 50000;
comment on column dev_base_info.dev_least_size is '最小加钞金额';
alter table clr_center_table add line_mode number default 1;
comment on column clr_center_table.line_mode is '线路规划方式1-自动规划2-固定线路'

commit;
