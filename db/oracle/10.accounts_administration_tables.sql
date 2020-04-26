create table BIZTXLOG
(
    TXTYPE    VARCHAR2(100) not null,
    CASHTYPE  VARCHAR2(100) not null,
    TXSTATUS  VARCHAR2(100),
    TXDATE    VARCHAR2(100),
    TXINFO    VARCHAR2(100),
    MEDIUMNO1 VARCHAR2(100),
    MEDIUMNO2 VARCHAR2(100)
)
comment on table BIZTXLOG is '交易记录表';

commit;

