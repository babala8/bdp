package com.zjft.microservice.treasurybrain.timejob.jobs;

import com.zjft.microservice.quartz.common.ResultEnum;
import com.zjft.microservice.quartz.jobs.BaseZjJob;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.task.web.TaskResource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/9/10 10:22
 */
@Slf4j
public class TaskOutTimeJob extends BaseZjJob {

	@Resource

	private TaskResource taskResource;

	@Override
	protected ResultEnum taskPerform(JobExecutionContext jobExecutionContext) throws Exception {


		//1.读取参数
		JobDetail jobDetail = jobExecutionContext.getJobDetail();
		int outTimeStatus = jobDetail.getJobDataMap().getIntValue("outTimeStatus");
		int originStatus = jobDetail.getJobDataMap().getIntValue("originStatus");
		int taskType = jobDetail.getJobDataMap().getIntValue("taskType");
		String nowTime = CalendarUtil.getSysTimeYMD();

		Map<String,Object> map = new HashMap<>(4);
		map.put("outTimeStatus",outTimeStatus);
		map.put("originStatus",originStatus);
		map.put("taskType",taskType);
		map.put("nowTime",nowTime);

		taskResource.taskOutTime(map);

		return ResultEnum.SUCCEED;
	}
}
