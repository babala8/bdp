package com.zjft.microservice.treasurybrain.clearcenter.service.impl;

import com.zjft.microservice.treasurybrain.clearcenter.domain.TaskCountInfoDO;
import com.zjft.microservice.treasurybrain.clearcenter.repository.CountTaskInfoMapper;
import com.zjft.microservice.treasurybrain.clearcenter.service.TaskToCountService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.task.dto.TaskTableDTO;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjs
 * @since 2019-10-10
 */
@Slf4j
@Service
public class TaskToCountServiceImpl implements TaskToCountService {

	@Resource
	CountTaskInfoMapper countTaskInfoMapper;


	@Resource
	private TaskInnerResource taskInnerResource;

	/**
	 * 配钞清分
	 *
	 * @param paramMap 配钞清分数据
	 * @return 响应信息
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	@Override
	public DTO countTask(Map<String, Object> paramMap) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		ArrayList<LinkedHashMap> list = (ArrayList<LinkedHashMap>) paramMap.get("taskCountList");
		String devNo = StringUtil.parseString(paramMap.get("devNo"));
		int batch = StringUtil.objectToInt(paramMap.get("batch"));
		List<String> taskNos = new ArrayList<>();

		TaskCountInfoDO tbatch = new TaskCountInfoDO();
		tbatch.setDevNo(devNo);
		tbatch.setBatch(batch);

		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap taskCount = (LinkedHashMap) list.get(i);
			String taskNo = StringUtil.parseString(taskCount.get("taskNo"));

			TaskCountInfoDO t = new TaskCountInfoDO();
			t.setBatch(batch);
			t.setDevNo(devNo);
			t.setCountStartDate(CalendarUtil.getSysTimeYMDHMS());
			t.setCountEndDate("");
			t.setTaskNo(taskNo);
			t.setCountStatus(1);

//			countTaskInfoMapper.selectByPrimaryKey(taskNo);
			TaskTableDTO taskTableDTO = taskInnerResource.getByPrimaryKey(taskNo);
			if (taskTableDTO.getTaskType() == 4) {
				taskNos.add(taskNo);
			}
			countTaskInfoMapper.deleteByPrimaryKey(taskNo);
		}

		List<TaskCountInfoDO> taskCountInfoDOS = countTaskInfoMapper.selectByBatch(tbatch);
		if (taskCountInfoDOS != null && taskCountInfoDOS.size() != 0) {
			if (taskCountInfoDOS.get(0).getBatch() == batch) {
				for (int i = 0; i < taskCountInfoDOS.size(); i++) {
					TaskCountInfoDO task = taskCountInfoDOS.get(i);
					countTaskInfoMapper.updateNextAdd(task);
				}
				for (int i = 0; i < list.size(); i++) {
					LinkedHashMap taskCount = (LinkedHashMap) list.get(i);
					String taskNo = StringUtil.parseString(taskCount.get("taskNo"));

					TaskCountInfoDO t = new TaskCountInfoDO();
					t.setBatch(batch);
					t.setDevNo(devNo);
					t.setCountStartDate(CalendarUtil.getSysTimeYMDHMS());
					t.setCountEndDate("");
					t.setTaskNo(taskNo);
					t.setCountStatus(1);

					countTaskInfoMapper.insert(t);
				}
			} else {
				for (int i = 0; i < list.size(); i++) {
					LinkedHashMap taskCount = (LinkedHashMap) list.get(i);
					String taskNo = StringUtil.parseString(taskCount.get("taskNo"));

					TaskCountInfoDO t = new TaskCountInfoDO();
					t.setBatch(batch);
					t.setDevNo(devNo);
					t.setCountStartDate(CalendarUtil.getSysTimeYMDHMS());
					t.setCountEndDate("");
					t.setTaskNo(taskNo);
					t.setCountStatus(1);

					countTaskInfoMapper.insert(t);
				}
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap taskCount = (LinkedHashMap) list.get(i);
				String taskNo = StringUtil.parseString(taskCount.get("taskNo"));

				TaskCountInfoDO t = new TaskCountInfoDO();
				t.setBatch(batch);
				t.setDevNo(devNo);
				t.setCountStartDate(CalendarUtil.getSysTimeYMDHMS());
				t.setCountEndDate("");
				t.setTaskNo(taskNo);
				t.setCountStatus(1);

				countTaskInfoMapper.insert(t);
			}
		}

		if (taskNos != null && taskNos.size() != 0) {
			Integer status = 209;
			int x = taskInnerResource.cashInStorage(taskNos, status);
		}

		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

}
