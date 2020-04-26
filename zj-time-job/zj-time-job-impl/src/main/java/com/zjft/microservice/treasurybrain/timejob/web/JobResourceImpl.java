package com.zjft.microservice.treasurybrain.timejob.web;

import com.zjft.microservice.quartz.dto.BaseDTO;
import com.zjft.microservice.quartz.dto.PageDTO;
import com.zjft.microservice.quartz.dto.TimeJobDTO;
import com.zjft.microservice.quartz.dto.TimeJobParamDTO;
import com.zjft.microservice.quartz.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cwang
 */
@RestController
public class JobResourceImpl implements JobResource {

	private final JobService jobService;

	@Autowired
	public JobResourceImpl(JobService jobService) {
		this.jobService = jobService;
	}

	@Override
	public PageDTO qryTimeJob(TimeJobParamDTO timeJobParamDTO) {
		return jobService.qryTimeJob(timeJobParamDTO);
	}

	@Override
	public BaseDTO modTimeJob(TimeJobDTO timeJobDTO) {
		return jobService.modTimeJob(timeJobDTO);
	}

	@Override
	public BaseDTO addTimeJob(TimeJobDTO timeJobDTO) {
		return jobService.addTimeJob(timeJobDTO);
	}

	@Override
	public BaseDTO delTimeJob(String jobID) {
		return jobService.delTimeJob(jobID);
	}
}
