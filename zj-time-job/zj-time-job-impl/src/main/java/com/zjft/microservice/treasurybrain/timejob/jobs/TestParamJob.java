package com.zjft.microservice.treasurybrain.timejob.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author cwang
 */
@Slf4j
public class TestParamJob extends QuartzJobBean {
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		log.info("TestJob:"+jobExecutionContext);

		System.out.println("TestParamJob");
		String[] keys = jobExecutionContext.getJobDetail().getJobDataMap().getKeys();
		for (String key : keys) {
			System.err.println("param:" + key + " value:" + jobExecutionContext.getJobDetail().getJobDataMap().get(key));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
