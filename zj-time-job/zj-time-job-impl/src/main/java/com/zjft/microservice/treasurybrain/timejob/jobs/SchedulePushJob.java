package com.zjft.microservice.treasurybrain.timejob.jobs;

import com.zjft.microservice.quartz.common.ResultEnum;
import com.zjft.microservice.quartz.jobs.BaseZjJob;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.AddnoteLineInnerResource;
import com.zjft.microservice.treasurybrain.managecenter.web_inner.CarInnerResource;
import com.zjft.microservice.treasurybrain.pushserver.web_inner.SendInfoInnerResource;
import com.zjft.microservice.treasurybrain.usercenter.dto.OutSourcingLineMapInnerDTO;
import com.zjft.microservice.treasurybrain.usercenter.web_inner.OutSourcingInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/09/23
 */
@Slf4j
public class SchedulePushJob extends BaseZjJob {

	@Resource
	private SendInfoInnerResource sendInfoInnerResource;

	@Resource
	private AddnoteLineInnerResource addnoteLineInnerResource;

	@Resource
	private OutSourcingInnerResource outSourcingInnerResource;

	@Resource
	private CarInnerResource carInnerResource;

	@Override
	protected ResultEnum taskPerform(JobExecutionContext jobExecutionContext) throws Exception {

		try {
			String nowDate = CalendarUtil.getSysTimeYMD();
			List<OutSourcingLineMapInnerDTO> dtoList = outSourcingInnerResource.qryInfoByDutyDate(nowDate);
			if (dtoList.size() != 0) {
				for (OutSourcingLineMapInnerDTO outSourcingLineMapInnerDTO : dtoList) {
					List<String> nameList = outSourcingInnerResource.qryOpNameByLineRunNo(outSourcingLineMapInnerDTO.getLineRunNo());
					StringBuilder nameTotal = new StringBuilder();
					for (String name : nameList) {
						nameTotal.append(name + " ");
					}
					String type = "SchedulePush";
					String lineName = addnoteLineInnerResource.qryLineNameByLineNo(outSourcingLineMapInnerDTO.getLineNo());
					String carNumber = carInnerResource.qryCarNumberByNo(outSourcingLineMapInnerDTO.getCarNo());
					String message = "日期：" + nowDate + " 线路：" + lineName + " 押运车牌号：" + carNumber + " 押运人员：" + nameTotal;
					log.info(message);

					Map map = new HashMap();
					map.put("lineName", lineName);
					map.put("carNumber", carNumber);
					map.put("nowDate", nowDate);
					map.put("nameTotal", nameTotal);
					map.put("type", type);
					String json = JSONUtil.createJsonString(map);
					DTO sendInfo2All = sendInfoInnerResource.sendInfo2All(json);
					log.info("推送给所有人是否成功：" + sendInfo2All.getRetMsg());
				}
			} else {
				return ResultEnum.FAIL;
			}
		} catch (Exception e) {
			log.error("[SchedulePush Exception]", e);
		}
		return ResultEnum.SUCCEED;
	}
}
