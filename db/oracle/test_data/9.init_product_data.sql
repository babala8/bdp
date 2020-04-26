
--PRODUCT_MODULE_TABLE
INSERT INTO PRODUCT_MODULE_TABLE (MODULE_NO, MODULE_NAME) VALUES (1, '产品中心');
INSERT INTO PRODUCT_MODULE_TABLE (MODULE_NO, MODULE_NAME) VALUES (2, '订单中心');
INSERT INTO PRODUCT_MODULE_TABLE (MODULE_NO, MODULE_NAME) VALUES (3, '流转中心');
INSERT INTO PRODUCT_MODULE_TABLE (MODULE_NO, MODULE_NAME) VALUES (4, '仓储中心');
INSERT INTO PRODUCT_MODULE_TABLE (MODULE_NO, MODULE_NAME) VALUES (5, '钞处中心');

--PRODUCT_CUSTOMER_TYPE
INSERT INTO PRODUCT_CUSTOMER_TYPE (CUSTOMER_TYPE, NAME) VALUES (1, '人民银行');
INSERT INTO PRODUCT_CUSTOMER_TYPE (CUSTOMER_TYPE, NAME) VALUES (2, '金交所客户');
INSERT INTO PRODUCT_CUSTOMER_TYPE (CUSTOMER_TYPE, NAME) VALUES (3, '网点');
INSERT INTO PRODUCT_CUSTOMER_TYPE (CUSTOMER_TYPE, NAME) VALUES (4, '临售客户');
INSERT INTO PRODUCT_CUSTOMER_TYPE (CUSTOMER_TYPE, NAME) VALUES (5, '对公客户');
INSERT INTO PRODUCT_CUSTOMER_TYPE (CUSTOMER_TYPE, NAME) VALUES (6, '自助机具');
INSERT INTO PRODUCT_CUSTOMER_TYPE (CUSTOMER_TYPE, NAME) VALUES (7, '现金中心');

--PRODUCT_SERVICE_TABLE
INSERT INTO PRODUCT_SERVICE_TABLE (SERVICE_NO, SERVICE_NAME, CUSTOMER_TYPE, NOTE, CREATE_TIME, UPDATE_TIME) VALUES (1, '自助机具加钞产品', 6, '为自助机具设备制定的装卸钞计划，包括加钞日期、设备信息、所属线路、加钞人员等信息', '2019-09-01', '2019-09-03');
INSERT INTO PRODUCT_SERVICE_TABLE (SERVICE_NO, SERVICE_NAME, CUSTOMER_TYPE, NOTE, CREATE_TIME, UPDATE_TIME) VALUES (2, '网点解现&寄库产品', 3, '网点解现&寄库任务单是营业网点制定的解现及寄库计划，包括寄库款箱信息、解现金额及面值、预约时间等信息', null, null);
INSERT INTO PRODUCT_SERVICE_TABLE (SERVICE_NO, SERVICE_NAME, CUSTOMER_TYPE, NOTE, CREATE_TIME, UPDATE_TIME) VALUES (3, '网点寄库领回产品', 3, '系统根据网点营业时间及预约的领用时间生成的调拨计划', null, null);
INSERT INTO PRODUCT_SERVICE_TABLE (SERVICE_NO, SERVICE_NAME, CUSTOMER_TYPE, NOTE, CREATE_TIME, UPDATE_TIME) VALUES (4, '网点领现产品', 3, '网点根据营业需要评估出需要从现金中心领用现金的计划，包括领现金额及面值、预约时间等信息', null, null);
INSERT INTO PRODUCT_SERVICE_TABLE (SERVICE_NO, SERVICE_NAME, CUSTOMER_TYPE, NOTE, CREATE_TIME, UPDATE_TIME) VALUES (5, '现金调拨产品', 7, '现金作业区根据作业安排从库房调拨现金的计划，分为调入、调出任务', null, null);

--PRODUCT_SERVICE_STATUS
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (1, 308, '已出库交接', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (1, 201, '已创建', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (1, 501, '已配钞', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (1, 301, '已出库', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (1, 502, '尾钞已清点', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (1, 402, '尾钞已调出', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (1, 304, '尾箱已入库', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (1, 401, '已配钞入库', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (1, 205, '已完成', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (1, 309, '已入库交接', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (1, 208, '已过期', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (1, 200, '已取消', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (2, 206, '已拆分', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (2, 209, '已合并', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (2, 201, '已创建', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (2, 207, '金库已确认', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (2, 305, '经警已接库', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (2, 304, '已入库', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (2, 208, '已过期', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (2, 202, '已退回', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (2, 205, '已完成', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (2, 402, '解现箱已调出', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (2, 306, '已入库交接', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (2, 204, '网点已审批', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (3, 201, '已创建', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (3, 208, '已过期', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (3, 308, '已出库交接', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (3, 307, '网点已接库', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (3, 301, '已出库', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (4, 201, '已创建', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (4, 501, '已配款', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (4, 304, '已调入', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (4, 207, '金库已确认', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (4, 204, '网点已审批', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (4, 308, '已出库交接', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (4, 301, '已出库', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (4, 208, '已过期', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (4, 307, '网点已接库', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (5, 201, '已创建', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (5, 301, '已出库', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (5, 304, '已入库', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (5, 501, '已清分', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (5, 502, '已清点', null);
INSERT INTO PRODUCT_SERVICE_STATUS (SERVICE_NO, STATUS, NAME, NOTE) VALUES (5, 205, '已完成', null);

--PRODUCT_STATUS_CONVERT

INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('212', 2, 201, 206, '任务拆分', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('213', 2, 204, 206, '任务拆分', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('214', 2, 207, 206, '任务拆分', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('215', 2, 201, 209, '任务合并', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('216', 2, 204, 209, '任务合并', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('217', 2, 207, 209, '任务合并', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('501', 5, 201, 502, '现金清点', 5, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('502', 5, 502, 304, '现金入库', 5, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('503', 5, 304, 205, '结单', 5, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('504', 5, 201, 301, '现金出库', 5, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('505', 5, 301, 501, '现金清分', 5, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('506', 5, 501, 205, '结单', 5, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('101', 1, 201, 501, '加钞计划配钞', 5, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('102', 1, 501, 401, '配钞调入', 4, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('103', 1, 501, 308, '紧急加钞', 4, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('104', 1, 401, 301, '配钞出库', 4, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('105', 1, 309, 304, '尾箱入库', 4, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('106', 1, 304, 402, '尾箱调出', 4, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('107', 1, 402, 502, '尾箱清点', 5, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('109', 1, 201, 200, '任务单取消', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('110', 1, 201, 301, '无配钞出库', 4, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('111', 1, 309, 205, '无钞箱入库', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('201', 2, 201, 204, '网点审批', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('202', 2, 204, 207, '金库确认', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('203', 2, 207, 305, '经警接库', 3, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('204', 2, 305, 306, '入库交接', 3, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('205', 2, 306, 304, '入库', 4, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('206', 2, 304, 402, '解现箱调出', 4, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('207', 2, 402, 205, '解现箱清点结束', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('208', 2, 201, 202, '网点审批退回', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('209', 2, 204, 202, '金库退回', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('210', 2, 201, 208, '任务过期', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('301', 3, 201, 301, '寄存箱出库', 4, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('302', 3, 308, 307, '网点接库', 3, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('303', 3, 301, 308, '出库交接', 3, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('112', 1, 301, 308, '出库交接', 3, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('113', 1, 308, 309, '尾箱入库交接', 3, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('401', 4, 201, 204, '网点审批', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('402', 4, 204, 207, '金库确认', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('403', 4, 207, 501, '款箱配钞', 5, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('404', 4, 501, 304, '调入库房', 4, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('406', 4, 301, 308, '出库交接', 3, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('407', 4, 308, 307, '网点接库', 3, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('405', 4, 304, 301, '领现箱出库', 4, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('211', 2, 202, 201, '退回调整', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('108', 1, 201, 208, '任务单过期', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('304', 3, 201, 208, '任务单过期', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('408', 4, 201, 208, '任务单过期', 2, null);
INSERT INTO PRODUCT_STATUS_CONVERT (ID, SERVICE_NO, CUR_STATUS, NEXT_STATUS, DESCRIPTION, MODULE_NO, IMPL_NO) VALUES ('114', 1, 502, 205, '日结单', 2, null);
commit ;

INSERT INTO GOODS_BASE_TABLE (GOODS_NO, UPPER_GOODS_NO, GOODS_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('112233', 'X0002', '黄金纪念币', '2019-09-26', '2019-09-26');
INSERT INTO GOODS_BASE_TABLE (GOODS_NO, UPPER_GOODS_NO, GOODS_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('R', null, '容器', '2019-09-01', null);
INSERT INTO GOODS_BASE_TABLE (GOODS_NO, UPPER_GOODS_NO, GOODS_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('R0001', 'R', '钞袋', '2019-09-01', null);
INSERT INTO GOODS_BASE_TABLE (GOODS_NO, UPPER_GOODS_NO, GOODS_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('R0002', 'R', '钞箱', '2019-09-01', null);
INSERT INTO GOODS_BASE_TABLE (GOODS_NO, UPPER_GOODS_NO, GOODS_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('R0003', 'R', '款箱', '2019-09-25', null);
INSERT INTO GOODS_BASE_TABLE (GOODS_NO, UPPER_GOODS_NO, GOODS_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('X', null, '现金实物', '2019-09-01', null);
INSERT INTO GOODS_BASE_TABLE (GOODS_NO, UPPER_GOODS_NO, GOODS_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('X0001', 'X', '人民币', '2019-09-04', '2019-09-05');
INSERT INTO GOODS_BASE_TABLE (GOODS_NO, UPPER_GOODS_NO, GOODS_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('X0002', 'X', '黄金', '2019-09-01', null);

INSERT INTO GOODS_PROPERTY_KEY (PROPERTY_NO, GOODS_NO, PROPERTY_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('0007', '112233', '重量', '2019-09-26', null);
INSERT INTO GOODS_PROPERTY_KEY (PROPERTY_NO, GOODS_NO, PROPERTY_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('0008', '112233', '长度', '2019-09-26', null);
INSERT INTO GOODS_PROPERTY_KEY (PROPERTY_NO, GOODS_NO, PROPERTY_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('0001', 'R0001', '钞箱容量', '2019-09-01', null);
INSERT INTO GOODS_PROPERTY_KEY (PROPERTY_NO, GOODS_NO, PROPERTY_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('0002', 'R0002', '人民币容量', '2019-09-01', null);
INSERT INTO GOODS_PROPERTY_KEY (PROPERTY_NO, GOODS_NO, PROPERTY_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('0003', 'R0002', '品牌', '2019-09-01', null);
INSERT INTO GOODS_PROPERTY_KEY (PROPERTY_NO, GOODS_NO, PROPERTY_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('0004', 'X0001', '券别', '2019-09-04', null);
INSERT INTO GOODS_PROPERTY_KEY (PROPERTY_NO, GOODS_NO, PROPERTY_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('0005', 'X0001', '类别', '2019-09-04', null);
INSERT INTO GOODS_PROPERTY_KEY (PROPERTY_NO, GOODS_NO, PROPERTY_NAME, CREATE_TIME, UPDATE_TIME) VALUES ('0006', 'X0002', '黄金重量', '2019-09-05', null);

INSERT INTO GOODS_PROPERTY_VALUE (ID, PROPERTY_NO, PROPERTY_VALUE, CREATE_TIME, UPDATE_TIME) VALUES ('0c0d7fa40f4f43978a25f4e5e65ceefd', '0006', '200', '2019-09-05', null);
INSERT INTO GOODS_PROPERTY_VALUE (ID, PROPERTY_NO, PROPERTY_VALUE, CREATE_TIME, UPDATE_TIME) VALUES ('1', '0001', '1', '2019-09-01', null);
INSERT INTO GOODS_PROPERTY_VALUE (ID, PROPERTY_NO, PROPERTY_VALUE, CREATE_TIME, UPDATE_TIME) VALUES ('16a3c08bdbb544c8acc467fda2c153fe', '0008', '2cm', '2019-09-26', null);
INSERT INTO GOODS_PROPERTY_VALUE (ID, PROPERTY_NO, PROPERTY_VALUE, CREATE_TIME, UPDATE_TIME) VALUES ('17149c8b8649406480068d4c49416dcd', '0007', '10g', '2019-09-26', null);
INSERT INTO GOODS_PROPERTY_VALUE (ID, PROPERTY_NO, PROPERTY_VALUE, CREATE_TIME, UPDATE_TIME) VALUES ('2', '0001', '2', '2019-09-01', null);
INSERT INTO GOODS_PROPERTY_VALUE (ID, PROPERTY_NO, PROPERTY_VALUE, CREATE_TIME, UPDATE_TIME) VALUES ('3', '0002', '200000', '2019-09-01', null);
INSERT INTO GOODS_PROPERTY_VALUE (ID, PROPERTY_NO, PROPERTY_VALUE, CREATE_TIME, UPDATE_TIME) VALUES ('4', '0002', '400000', '2019-09-01', null);
INSERT INTO GOODS_PROPERTY_VALUE (ID, PROPERTY_NO, PROPERTY_VALUE, CREATE_TIME, UPDATE_TIME) VALUES ('5', '0003', '日立', '2019-09-01', null);
INSERT INTO GOODS_PROPERTY_VALUE (ID, PROPERTY_NO, PROPERTY_VALUE, CREATE_TIME, UPDATE_TIME) VALUES ('6', '0004', '100', '2019-09-04', null);
INSERT INTO GOODS_PROPERTY_VALUE (ID, PROPERTY_NO, PROPERTY_VALUE, CREATE_TIME, UPDATE_TIME) VALUES ('7', '0005', '流通币', '2019-09-04', null);
INSERT INTO GOODS_PROPERTY_VALUE (ID, PROPERTY_NO, PROPERTY_VALUE, CREATE_TIME, UPDATE_TIME) VALUES ('fc60f53995ed46e78ae15aef804e80d1', '0006', '50', '2019-09-05', null);

INSERT INTO PRODUCT_SERVICE_GOODS (SERVICE_NO, GOODS_NO, GOODS_NAME, PROPERTY_LIST, DIRECTION) VALUES (1, 'R0001', '钞袋', '{''钞箱容量'': [''1'', ''2'']}', 1);

commit;
