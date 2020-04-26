package com.zjft.microservice.treasurybrain.timejob.jobs;

import com.zjft.etl.rmi.RmiETLService;
import com.zjft.microservice.quartz.common.ResultEnum;
import com.zjft.microservice.quartz.jobs.BaseZjJob;
import com.zjft.microservice.treasurybrain.common.util.CfgProperty;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

/**
 * ETL接口调用定时任务
 * 1.读取参数
 * 2.调用jar中的ETL接口
 * 3.记录定时任务执行情况
 *
 * @author liuyuan
 * @since 2019/7/9 17:25
 */

@Slf4j
public class ETLJob extends BaseZjJob {

	@Override
	protected ResultEnum taskPerform(JobExecutionContext jobExecutionContext) throws Exception {

		//1.读取参数
		JobDetail jobDetail = jobExecutionContext.getJobDetail();
		String fileName= jobDetail.getJobDataMap().getString("fileName");
		String zjetlIP = CfgProperty.getProperty("zjetlIP");
		String zjetlPort = CfgProperty.getProperty("zjetlPort");
		String url = "rmi://"+ zjetlIP+":"+zjetlPort+"/etlRunner";

		//2.调用jar中的ETL接口
		RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
		rmiProxyFactoryBean.setServiceUrl(url);
		rmiProxyFactoryBean.setServiceInterface(RmiETLService.class);
		rmiProxyFactoryBean.afterPropertiesSet();
		Object o = rmiProxyFactoryBean.getObject();
		RmiETLService rmiETLClient = (RmiETLService) o;
		System.out.println(rmiETLClient.execute(fileName));
		return ResultEnum.SUCCEED;
	}

}
