--------------------------------------------------------
--                   auth-admin 模块
--------------------------------------------------------

create table SYS_PERMISSION
(
	NO VARCHAR2(20) not null
		primary key,
	NAME VARCHAR2(50) not null,
	URL VARCHAR2(200) not null,
	METHOD VARCHAR2(10) default NULL,
	CATALOG NUMBER(11) default 2 not null,
	NOTE VARCHAR2(200)
);
comment on column SYS_PERMISSION.CATALOG is '1-系统权限; 2-用户权限; 3-公共权限';


create table SYS_ORG_GRADE
(
    NO       NUMBER       not null
        primary key,
    NAME     VARCHAR2(50) not null,
    ORG_TYPE VARCHAR2(20) default NULL
);


create table SYS_ORG
(
    NO                  VARCHAR2(20)     not null
        constraint PK_SYS_ORG
            primary key,
    NAME                VARCHAR2(160)    not null,
    PARENT_ORG          VARCHAR2(20),
    LEFT                NUMBER,
    RIGHT               NUMBER,
    ORG_GRADE_NO        NUMBER
        constraint FK_ORG_R_ORG_TYPE
            references SYS_ORG_GRADE,
    MONEYORG_FLAG       VARCHAR2(1),
    X                   VARCHAR2(20),
    Y                   VARCHAR2(20),
    ADDRESS             VARCHAR2(200),
    LINKMAN             VARCHAR2(30),
    TELEPHONE           VARCHAR2(30),
    MOBILE              VARCHAR2(30),
    FAX                 VARCHAR2(30),
    EMAIL               VARCHAR2(40),
    BUSINESS_RANGE      VARCHAR2(256),
    CUP_AREA_CODE       VARCHAR2(8),
    ADDRESS_CODE        VARCHAR2(15),
    AREA_NO             VARCHAR2(10),
    AREA_TYPE           VARCHAR2(3),
    ORG_PHYSICS_CATALOG VARCHAR2(3),
    NOTE                VARCHAR2(100),
    CLR_CENTER_NO       VARCHAR2(10),
    CITY                VARCHAR2(30),
    REGION              VARCHAR2(30),
    STATUS              NUMBER,
    CLR_CENTER_FLAG     NUMBER default 0 not null,
    ORG_NO              VARCHAR2(10),
    SHORT_NAME          VARCHAR2(160),
    FULL_NAME           VARCHAR2(160),
    AWAY_FLAG           NUMBER,
    CLR_CENTER_NO_CASH  VARCHAR2(10),
    LINE_NO             VARCHAR2(32),
    DELIVERY_TIME       VARCHAR2(20),
    BACK_TIME           VARCHAR2(20)
);
comment on table SYS_ORG is '机构表';
comment on column SYS_ORG.NO is '机构编号';
comment on column SYS_ORG.NAME is '机构名称';
comment on column SYS_ORG.PARENT_ORG is '直接上级';
comment on column SYS_ORG.LEFT is '预排序左序号';
comment on column SYS_ORG.RIGHT is '预排序右序号';
comment on column SYS_ORG.MONEYORG_FLAG is '是否是加钞机构（1：是 2：否）';
comment on column SYS_ORG.X is '横坐标（经度）';
comment on column SYS_ORG.Y is '纵坐标（纬度）';
comment on column SYS_ORG.ADDRESS is '地址';
comment on column SYS_ORG.LINKMAN is '联系人';
comment on column SYS_ORG.TELEPHONE is '电话';
comment on column SYS_ORG.MOBILE is '手机';
comment on column SYS_ORG.FAX is '传真';
comment on column SYS_ORG.EMAIL is '电子邮件';
comment on column SYS_ORG.BUSINESS_RANGE is '业务范围';
comment on column SYS_ORG.CUP_AREA_CODE is '银联标准地区代码';
comment on column SYS_ORG.ADDRESS_CODE is '地点代码';
comment on column SYS_ORG.AREA_NO is '所属区域';
comment on column SYS_ORG.AREA_TYPE is '所处区域类型 ADDRESS_REGION_TYPE （1、中央商务区 2、市级商业中心 3、政府机关集中区 4、工业园区 5、区县商业中心 6、休闲购物娱乐区 7、工作区 8、居住区）';
comment on column SYS_ORG.ORG_PHYSICS_CATALOG is '物理网点类型（1、全面商务型网点 2、全面社区型网点 3、基本商务型网点 4、基本社区型网点 5、财富中心）';
comment on column SYS_ORG.NOTE is '备注';
comment on column SYS_ORG.CLR_CENTER_NO is '寄库金库编号';
comment on column SYS_ORG.STATUS is '机构状态 1—启用  2—停用';
comment on column SYS_ORG.CLR_CENTER_FLAG is '金库标志 1—是  0—不是';
comment on column SYS_ORG.SHORT_NAME is '机构名字简称';
comment on column SYS_ORG.FULL_NAME is '机构名字全称';
comment on column SYS_ORG.AWAY_FLAG is '自助银行 1-在行 2-离行';
comment on column SYS_ORG.CLR_CENTER_NO_CASH is '现金调缴金库编号';
comment on column SYS_ORG.LINE_NO is '网点所属线路';

alter table  SYS_ORG  add (DELIVERY_TIME  VARCHAR(20));
alter table  SYS_ORG  add (BACK_TIME  VARCHAR(20));

comment on column  SYS_ORG.DELIVERY_TIME is  '要求送达时间';
comment on column  SYS_ORG.BACK_TIME is  '回库时间';

create table SYS_ROLE
(
    NO           NUMBER(11)                 not null
        primary key,
    NAME         VARCHAR2(50)               not null,
    CATALOG      NUMBER(11)    default 2    not null,
    ORG_GRADE_NO NUMBER(11)    default NULL not null
        constraint SYS_C005451
            references SYS_ORG_GRADE,
    NOTE         VARCHAR2(200) default NULL
);
comment on table SYS_ROLE is '角色表';
comment on column SYS_ROLE.NO is '角色编号';
comment on column SYS_ROLE.NAME is '角色名称';
comment on column SYS_ROLE.CATALOG is '0-匿名角色; 1-平台管理员; 2-用户角色';
comment on column SYS_ROLE.NOTE is '备注';


create table SYS_MENU
(
	NO VARCHAR2(10) default NULL not null
		primary key,
	NAME VARCHAR2(50) default NULL not null,
	MENU_FATHER VARCHAR2(10) default NULL
		constraint SYS_MENU_IBFK_1
			references SYS_MENU,
	URL VARCHAR2(200),
	MENU_LEVEL NUMBER(11),
	MENU_ORDER NUMBER(11),
	NOTE VARCHAR2(30),
	MENU_ICON VARCHAR2(50),
	MENU_SIZE NUMBER default 2,
	MENU_BG VARCHAR2(30) default NULL,
	BUTTON_TAG NUMBER default 0,
	BUTTON VARCHAR2(20) default NULL
);
comment on table SYS_MENU is '菜单表';
comment on column SYS_MENU.NO is '编号';
comment on column SYS_MENU.NAME is '名称';
comment on column SYS_MENU.MENU_FATHER is '上级菜单';
comment on column SYS_MENU.URL is '菜单链接';
comment on column SYS_MENU.MENU_LEVEL is '菜单层次';
comment on column SYS_MENU.MENU_ORDER is '顺序';
comment on column SYS_MENU.NOTE is '备注';
comment on column SYS_MENU.MENU_ICON is '菜单图标';
comment on column SYS_MENU.MENU_SIZE is '菜单大小';
comment on column SYS_MENU.MENU_BG is '菜单背景颜色';
comment on column SYS_MENU.BUTTON_TAG is '0-父级菜单; 1-接口型按钮; 2-菜单型按钮; 3-隐藏型菜单';


create table SYS_USER
(
    USERNAME            VARCHAR2(20)      not null
        primary key,
    PASSWORD            VARCHAR2(80)      not null,
    NAME                VARCHAR2(50)      not null,
    STATUS              NUMBER default 1  not null,
    ONLINE_FLAG         NUMBER default -1 not null,
    ORG_NO              VARCHAR2(20),
    PHONE               VARCHAR2(20),
    MOBILE              VARCHAR2(20),
    EMAIL               VARCHAR2(40),
    PHOTO               VARCHAR2(50),
    LOGIN_IP            VARCHAR2(20),
    LOGIN_TIME          VARCHAR2(20),
    LOGIN_TERM          VARCHAR2(50),
    PASSWORD_EXPIRATION VARCHAR2(10),
    PASSWORD_ERROR      NUMBER default 0  not null,
    GROUP_TYPE          NUMBER default 3,
    SERVICE_COMPANY     VARCHAR2(5)
);
CREATE INDEX INDEX_USER_ORG ON sys_user (ORG_NO);
COMMENT ON TABLE sys_user IS '用户表';
comment on column SYS_USER.USERNAME is '用户账号';
comment on column SYS_USER.PASSWORD is '用户密码（BCrypt加密保存）';
comment on column SYS_USER.NAME is '人员姓名';
comment on column SYS_USER.STATUS is '用户状态 [-1:新增, 0:停用, 1:启用, 2:锁定]';
comment on column SYS_USER.ONLINE_FLAG is '在线状态 [-1:从未在线, 1:在线, 0:离线]';
comment on column SYS_USER.ORG_NO is '所属机构';
comment on column SYS_USER.PHONE is '办公电话';
comment on column SYS_USER.MOBILE is '手机号码';
comment on column SYS_USER.EMAIL is '电子邮件';
comment on column SYS_USER.LOGIN_IP is '上次登录IP';
comment on column SYS_USER.LOGIN_TIME is '上次登录时间（YYYY-MM-DD HH:MM24:SS）';
comment on column SYS_USER.LOGIN_TERM is '登录终端:终端类型|版本号,如msie|11.0';
comment on column SYS_USER.PASSWORD_EXPIRATION is '密码到期日（YYYY-MM-DD）';
comment on column SYS_USER.PASSWORD_ERROR is '密码错误次数';

create table SYS_USER_ROLE
(
    USERNAME VARCHAR2(20) not null
        references SYS_USER,
    ROLE_NO  NUMBER       not null
        references SYS_ROLE,
    primary key (USERNAME, ROLE_NO)
);

create table SYS_ROLE_MENU
(
    ROLE_NO NUMBER                    not null
        constraint SYS_C005452
            references SYS_ROLE,
    MENU_NO VARCHAR2(10) default NULL not null
        constraint SYS_ROLE_MENU_FK
            references SYS_MENU
);

create table SYS_MENU_PERMISSION
(
    MENU_NO       VARCHAR2(20)
        constraint SYS_MENU_PERMISSION_FK2
            references SYS_MENU,
    PERMISSION_NO VARCHAR2(10)
        constraint SYS_MENU_PERMISSION_FK1
            references SYS_PERMISSION,
    constraint SYS_MENU_PERMISSION_UK
        unique (MENU_NO, PERMISSION_NO)
);
create index SYS_MENU_PERMISSION_INDEX2
    on SYS_MENU_PERMISSION (PERMISSION_NO);

-- 菜单收藏表
create table SYS_USER_MENU_COLLECTION
(
    USER_NO VARCHAR2(20) not null
        constraint FK_USER_MENU_COLL1
            references SYS_USER
                on delete cascade,
    MENU_NO VARCHAR2(10) not null
        constraint FK_USER_MENU_COLL2
            references SYS_MENU
                on delete cascade,
    constraint PK_SYS_USER_MENU_COLLECTION
        primary key (USER_NO, MENU_NO)
);

comment on table SYS_USER_MENU_COLLECTION is '用户收藏菜单表';
comment on column SYS_USER_MENU_COLLECTION.USER_NO is '用户编号';
comment on column SYS_USER_MENU_COLLECTION.MENU_NO is '收藏菜单编号';

-- 初始化，zj-api-log插件的日志记录表
create table SYS_WEB_LOG
(
    USERNAME    VARCHAR2(200),
    TID         VARCHAR2(50),
    METHOD      VARCHAR2(10),
    URL         VARCHAR2(200),
    RESULT      VARCHAR2(10),
    START_TIME  VARCHAR2(50),
    COST_TIME   VARCHAR2(50),
    CLIENT_IP   VARCHAR2(50),
    SERVER_IP   VARCHAR2(50),
    DESCRIPTION VARCHAR2(200)
);
create index SYS_WEB_LOG_START_TIME_INDEX on SYS_WEB_LOG (START_TIME);

commit;
