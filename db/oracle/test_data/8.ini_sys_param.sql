INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('10030', 5, 'dispatch_dev_num', '20', '每个任务单关联设备最大数量', ' ');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('10031', 5, 'op_dispatch_num', '6', '每个加钞人员每日关联任务最大数量', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('10032', 5, 'car_dispatch_num', '6', '每个运钞车每日关联任务单最大数量', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('10061', 5, 'addForecastDay', '2', '多预测天数', '多预测天数');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('10063', 5, 'predictCoefficient', '0.9', '预测存取款比例', '预测存取款比例');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('10064', 5, 'dayCount', '30', '获取每日最大现金需求天数', '获取每日最大现金需求天数');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('10076', 5, 'integerCoefficient', '50000', '求整除数', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('10077', 5, 'minAddAmt', '50000', '加钞最低限额', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('10078', 5, 'maxDevBestAmount', '180000', '最佳现金持有量极值', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('10133', 5, 'standardAvgCwd', '250000', '日均支出标准', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('10134', 5, 'lineMode_028001', '2', '成都金库线路模式', '成都金库线路模式');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('10135', 5, 'predictCoefficient_028001', '0.92', '成都金库预测金额系数', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('12001', 12, 'zipFlag', '1', '压缩标识', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('12002', 12, 'secretFlag', '1', '加密标识', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('12003', 12, 'macFlag', '1', '校验标识', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('12006', 12, 'addnotesAmountStep', '50000', '设备加钞量步长', '单位:元');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('12007', 12, 'addnotesDefaultAmtLimit', '100000', '设备默认加钞量', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('12008', 5, 'ATM_CAPCITY', '1000000', '装钞最大容量金额元', '装钞最大容量');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('13001', 13, 'MIN_SAMPLE_VOLUME', '30', '最小样本容量', '当样本总量小于该值时视为无效');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('13002', 13, 'MAX_ADDNOTES_PERIOD', '3', '设备默认最大加钞周期(天)', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('13003', 13, 'addnotesCountLimit', '10', '加钞设备数量上限约束', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('13004', 13, 'routeTimeWeighting', '1.2', '路线耗时权重', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('13005', 13, 'maxNetpointNumOfGroup', '10', '每组最大网点数', '通常情况下每个计划分组最大的网点数');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('13006', 13, 'addnotesCountLimitOneLine', '100', '单条线路加钞设备数量上限约束', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('13007', 13, 'groupMode', '1', '分组方式', '1-自动分组 2-线路分组');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('13008', 13, 'additional_Amt', '0', '预测金额后固定增加金额值', '预测金额后固定增加金额值');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('13011', 13, 'OutInSequenceStartDate_028001', '2018-06-25', '金库[028001]对应的顺延开始日期', '顺延开始日期');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('14003', 14, 'warn_catalog_Weather', '1003', '天气导入失败预警', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('14022', 5, 'DELIVERY_NUM', '6', '预出库调拨数目', null);
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('2001', 2, 'typeOfForecastMode', '1', '预测的模式，定时和实时。', '1为定时，其他值为实时');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('60001', 6, 'IS_RETRAIN_MODEL', '1', 'POC流程中是否重复训练模型', '0-如果模型已训练，不重复训练；1-重复训练。缺省0。');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('60002', 6, 'PREDICT_MODE', '1', '预测模式', '0-预测模式；1-生产模式。缺省0。');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('60003', 6, 'CRS_PREDICT_MODE', '0', '一体机预测模式', '0-存、取款分别训练预测；1-采用净付出量训练预测');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('60004', 6, 'MODEL_TYPE', '1', '模型类型', '0-竞赛模型；1-混合模型。缺省0。');
INSERT INTO SYS_PARAM (LOGIC_ID, CATALOG, PARAM_NAME, PARAM_VALUE, STATEMENT, DESCRIPTION) VALUES ('60005', 6, 'OBJ_TYPE', '1', 'poc预测对象类型', '1-设备， 2-设备组');

commit;
