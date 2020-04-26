INSERT INTO TIME_JOB (ID, TRIG_NAME, CRON, JOB_NAME, OBJ_NAME, CONCURRENT, JOB_STATE, DESP, ARGUMENTS, INIT_FLAG) VALUES ('f81a6cbf57e645cfa808b1219b9b9481', 'tset_scheduleJob', '0 0 6 * * ?', 'schedulePushJob', 'com.zjft.microservice.treasurybrain.timejob.jobs.SchedulePushJob', 0, 1, '线路信息推送', '{}', 0);
INSERT INTO TIME_JOB (ID, TRIG_NAME, CRON, JOB_NAME, OBJ_NAME, CONCURRENT, JOB_STATE, DESP, ARGUMENTS, INIT_FLAG) VALUES ('eda0f5db862245fcb48a8cc93d4a3ca2', 'TestWeatherJob_1', '*/30 * * * * ?', 'weatherJob', 'com.zjft.microservice.treasurybrain.timejob.jobs.WeatherJob', 0, 1, 'weathertest', '{"cityNo":"1700"}', 0);
INSERT INTO TIME_JOB (ID, TRIG_NAME, CRON, JOB_NAME, OBJ_NAME, CONCURRENT, JOB_STATE, DESP, ARGUMENTS, INIT_FLAG) VALUES ('3b68c76a7c254f45b75e630aaf3d524a', 'outTaskJobTrig', '0 0 23 * * ?', 'createOutTask', 'com.zjft.microservice.treasurybrain.timejob.jobs.OutTaskJob', 0, 1, '生成网点出库任务单', '{}', 0);
INSERT INTO TIME_JOB (ID, TRIG_NAME, CRON, JOB_NAME, OBJ_NAME, CONCURRENT, JOB_STATE, DESP, ARGUMENTS, INIT_FLAG) VALUES ('04053dc7eb4345b1b7bc427aa3ce00fc', 'taskTimeOutTrig', '01 21 20 * * ?', 'taskTimeOut', 'com.zjft.microservice.treasurybrain.timejob.jobs.TaskOutTimeJob', 0, 1, '任务单过期', '{"outTimeStatus":208,"originStatus":201,"taskType":1}', 0);


commit;
