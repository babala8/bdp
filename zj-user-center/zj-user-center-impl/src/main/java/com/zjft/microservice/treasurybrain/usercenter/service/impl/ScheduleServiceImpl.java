package com.zjft.microservice.treasurybrain.usercenter.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.AddnoteLineInnerResource;
import com.zjft.microservice.treasurybrain.managecenter.dto.CarDTO;
import com.zjft.microservice.treasurybrain.managecenter.web.CarResource;
import com.zjft.microservice.treasurybrain.usercenter.domain.*;
import com.zjft.microservice.treasurybrain.usercenter.dto.*;
import com.zjft.microservice.treasurybrain.usercenter.mapstruct.OutsourcingLineMapConverter;
import com.zjft.microservice.treasurybrain.usercenter.mapstruct.PostScheduleConverter;
import com.zjft.microservice.treasurybrain.usercenter.mapstruct.PostScheduleMouldClassesConverter;
import com.zjft.microservice.treasurybrain.usercenter.mapstruct.PostScheduleMouldConverter;
import com.zjft.microservice.treasurybrain.usercenter.po.*;
import com.zjft.microservice.treasurybrain.usercenter.repository.*;
import com.zjft.microservice.treasurybrain.usercenter.service.ScheduleService;
import com.zjft.microservice.treasurybrain.usercenter.web.OutsourcingResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author liuyuan
 * @since 2019/9/25 10:25
 */
@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Resource
	OutsourcingLineMapMapper outsourcingLineMapMapper;

	@Resource
	OutsourcingLineMapDetailMapper outsourcingLineMapDetailMapper;

	@Resource
	private AddnoteLineInnerResource addnoteLineInnerResource;

	@Resource
	CarResource carResource;

	@Resource
	OutsourcingResource outsourcingResource;

	@Resource
	private PostScheduleMouldMapper postScheduleMouldMapper;

	@Resource
	private PostScheduleMouldPersionMapper postScheduleMouldPersionMapper;

	@Resource
	private PostScheduleMapper postScheduleMapper;

	@Resource
	private PostSchedulePlanMapper postSchedulePlanMapper;



	@Override
	public PageDTO<OutSourcingLineMapDTO> qryOutSourcingByPage(Map<String, Object> paramsMap) {
		PageDTO<OutSourcingLineMapDTO> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(paramsMap, pageDTO);
		int totalRow = outsourcingLineMapMapper.qryTotalRow(paramsMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<OutsourcingLineMapDO> outsourcingLineMapDOS = outsourcingLineMapMapper.qryByPage(paramsMap);
		List<OutSourcingLineMapDTO> outSourcingLineMapDTOS
				= OutsourcingLineMapConverter.INSTANCE.do2Dto(outsourcingLineMapDOS);

		pageDTO.setRetList(outSourcingLineMapDTOS);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setPageSize(pageSize);

		return pageDTO;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public DTO addOutSourcing(OutSourcingLineMapAddDTO outSourcingLineMapAddDTO) {
		DTO result = new DTO();
		String startDate = outSourcingLineMapAddDTO.getStartDate();
		String endDate = outSourcingLineMapAddDTO.getEndDate();

		if (CalendarUtil.compare2Dates(startDate, endDate) > 0) {
			result.setRetCode(RetCodeEnum.FAIL.getCode());
			result.setRetMsg("开始日期不能大于结束日期");
			return result;
		}
		String today = CalendarUtil.getSysTimeYMD();
		if (CalendarUtil.compare2Dates(startDate, today) < 0) {
			result.setRetCode(RetCodeEnum.FAIL.getCode());
			result.setRetMsg("开始日期不能再今天之前");
			return result;
		}


		List<String> lineNos = outSourcingLineMapAddDTO.getLineNos();
		if (0 == lineNos.size()) {
			//根据金库编号和线路类型查询线路
			Map<String, Object> map = new HashMap<>(2);
			if (null != outSourcingLineMapAddDTO.getLineType()) {
				map.put("lineType", outSourcingLineMapAddDTO.getLineType());
			}
			if (null != outSourcingLineMapAddDTO.getClrCenterNo()) {
				map.put("clrCenterNo", outSourcingLineMapAddDTO.getClrCenterNo());
			}

			lineNos = addnoteLineInnerResource.getLineNosWithTypeAndClrNo(map);
		}

		//删除原有计划
		outsourcingLineMapDetailMapper.deleteInPeriod(startDate, endDate, lineNos);
		outsourcingLineMapMapper.deleteInPeriod(startDate, endDate, lineNos);

		//生成方案:查询当天所有线路对应的线路运行图，按正序分配人员车辆
		//TODO 待完善规则
		//查询所有押运人员信息和押运车辆信息
		if (lineNos.size() >= 100) {
			log.error("线路过多");
			result.setRetMsg("线路过多");
			return result;
		} else {
			List<CarDTO> carDTOS = carResource.qryAllCar();
			List<String> driverNoList =
					outsourcingResource.qryByPost(StatusEnum.OutSourcingPost.DRIVER.getPost());
			List<String> protectorList =
					outsourcingResource.qryByPost(StatusEnum.OutSourcingPost.PROTECTOR.getPost());
			List<String> outerList =
					outsourcingResource.qryByPost(StatusEnum.OutSourcingPost.OUT_SOURCING.getPost());

			for (String curDay = startDate;
				 CalendarUtil.compare2Dates(curDay, endDate) <= 0;
				 curDay = CalendarUtil.getFixedDate(curDay, 1)) {
				//年月
				String theYearMonth = curDay.substring(0, 7);
				//日
				String day = curDay.substring(8);

				//查询当日有线路运行图的线路
				Map<String, Object> dateAndLineNos = new HashMap<>(3);
				dateAndLineNos.put("theYearMonth", theYearMonth);
				dateAndLineNos.put("day", day);
				dateAndLineNos.put("lineNos", lineNos);
				List<String> curDateLineNos = addnoteLineInnerResource.qryLineHasMapWithDate(dateAndLineNos);
				//生成排班表
				int driverNum = 0, protectorNum = 0, outerNum = 0, carNum = 0;
				for (String lineNo : curDateLineNos) {
					OutsourcingLineMapDO outsourcingLineMapDO = new OutsourcingLineMapDO();
					outsourcingLineMapDO.setLineRunNo(lineNo + curDay.replace("-", ""));
					outsourcingLineMapDO.setLineNo(lineNo);

					//TODO 待完善选择逻辑，目前会造成冲突
					if (carNum >= carDTOS.size()) {
						//车用完从头再来
						carNum = 0;
					}
					outsourcingLineMapDO.setCarNo(carDTOS.get(carNum).getCarNo());
					carNum++;

					outsourcingLineMapDO.setDutyDate(curDay);
					//插入总记录
					outsourcingLineMapMapper.insert(outsourcingLineMapDO);

					//分配人员，按照三个岗位分配
					Map<String, Object> detail = new HashMap<>(2);
					List<OutsourcingPO> opNoList = new ArrayList<>();

					//司机
					if (driverNum >= driverNoList.size()) {
						driverNum = 0;
					}
					OutsourcingPO driver = new OutsourcingPO();
					driver.setNo(driverNoList.get(driverNum));
					opNoList.add(driver);
					driverNum++;

					//安保
					if (protectorNum >= protectorList.size()) {
						protectorNum = 0;
					}
					OutsourcingPO protector = new OutsourcingPO();
					protector.setNo(protectorList.get(protectorNum));
					opNoList.add(protector);
					protectorNum++;

					//押运
					if (outerNum >= outerList.size()) {
						outerNum = 0;
					}
					OutsourcingPO operator = new OutsourcingPO();
					operator.setNo(outerList.get(outerNum));
					opNoList.add(operator);
					outerNum++;
					//插入详情
					outsourcingLineMapDO.setOutsourcingList(opNoList);
					outsourcingLineMapDetailMapper.insertList(outsourcingLineMapDO);
				}
			}
		}

		result.setResult(RetCodeEnum.SUCCEED);
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public DTO deleteOutSourcing(String no) {
		DTO result = new DTO();

		if (dutyDateBeforeToday(no)) {
			log.error("不能删除今天之前的押运人员排班计划");
			result.setRetCode(RetCodeEnum.FAIL.getCode());
			result.setRetMsg("不能删除今天之前的押运人员排班计划");
			return result;
		}

		outsourcingLineMapDetailMapper.deleteByLineRunNo(no);
		outsourcingLineMapMapper.deleteByPrimaryKey(no);
		result.setResult(RetCodeEnum.SUCCEED);

		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public DTO modOutSourcing(OutSourcingLineMapDTO outSourcingLineMapDTO) {
		DTO result = new DTO();
		String lineRunNo = outSourcingLineMapDTO.getLineRunNo();
		if (dutyDateBeforeToday(lineRunNo)) {
			log.error("不能修改今天之前的押运人员排班计划");
			result.setRetCode(RetCodeEnum.FAIL.getCode());
			result.setRetMsg("不能修改今天之前的押运人员排班计划");
			return result;
		}

		outsourcingLineMapDetailMapper.deleteByLineRunNo(lineRunNo);
		OutsourcingLineMapDO outsourcingLineMapDO = OutsourcingLineMapConverter.INSTANCE.dto2do(outSourcingLineMapDTO);
		outsourcingLineMapDetailMapper.insertList(outsourcingLineMapDO);

		outsourcingLineMapMapper.modeByPrimaryKey(outsourcingLineMapDO);
		result.setResult(RetCodeEnum.SUCCEED);
		return result;
	}


	/**
	 * 比较排班记录日期是否在今日之前
	 *
	 * @param no 线路排班记录号
	 * @return
	 */
	private boolean dutyDateBeforeToday(String no) {
		OutsourcingLineMapPO outsourcingLineMapPO = outsourcingLineMapMapper.qryByPrimaryKey(no);
		String dutyDate = outsourcingLineMapPO.getDutyDate();
		String today = CalendarUtil.getSysTimeYMD();
		if (CalendarUtil.compare2Dates(today, dutyDate) > 0) {
			return true;
		} else {
			return false;
		}
	}


	@Override
	public DTO export(HttpServletRequest request, HttpServletResponse response, OutSourcingLineMapExportDTO outSourcingLineMapExportDTO) {
		return new DTO(RetCodeEnum.SUCCEED);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public DTO addPostScheduleMould(PostScheduleMouldDTO postScheduleMouldDTO) {
		log.info("------------[add]PostScheduleMouldService-------------");
		DTO dto = new DTO();
		String maxNo = postScheduleMouldMapper.qryMaxNo();
		String newMouldNo;
		if (StringUtil.isNullorEmpty(maxNo)) {
			newMouldNo = "0000000001";
		} else {
			Integer tmpInt = Integer.parseInt(maxNo) + 1;
			newMouldNo = String.format("%010d", tmpInt);
		}
		String mouldNo = newMouldNo;
		String mouldName = postScheduleMouldDTO.getMouldName();
		Integer mouldType = postScheduleMouldDTO.getMouldType();
		String orgNo = postScheduleMouldDTO.getOrgNo();
		String postNo = postScheduleMouldDTO.getPostNo();
		PostScheduleMouldPO postScheduleMouldPO = new PostScheduleMouldPO();
		postScheduleMouldPO.setMouldNo(newMouldNo);
		postScheduleMouldPO.setMouldType(mouldType);
		postScheduleMouldPO.setMouldName(mouldName);
		postScheduleMouldPO.setOrgNo(orgNo);
		postScheduleMouldPO.setPostNo(postNo);
		postScheduleMouldPO.setCreateTime(CalendarUtil.getSysTimeYMD());

		if (postScheduleMouldMapper.qryMouldNameExist(mouldName) > 0) {
			dto.setRetMsg("模板名称已存在，请勿重复添加！");
			dto.setRetCode(RetCodeEnum.FAIL.getCode());
			return dto;
		}
		int x = postScheduleMouldMapper.insert(postScheduleMouldPO);
		if (x != 1) {
			log.error("新增模板失败！");
			throw new RuntimeException("新增模板失败！");
		}
		List<PostScheduleMouldDetailDTO> detailList = postScheduleMouldDTO.getDetailList();
		if (detailList.size() > 0) {
			for (PostScheduleMouldDetailDTO postScheduleMouldDetailDTO : detailList) {
				if (StringUtil.isNullorEmpty(postScheduleMouldDetailDTO.getClassesNo()) ||
						postScheduleMouldDetailDTO.getCountNo() == null) {
					throw new RuntimeException("缺少参数");
				}
				if (postScheduleMouldDetailDTO.getOpList() == null || postScheduleMouldDetailDTO.getOpList().size() == 0) {
					continue;
				}
				PostScheduleMouldPersionPO postScheduleMouldPersionPO = new PostScheduleMouldPersionPO();
				postScheduleMouldPersionPO.setMouldNo(newMouldNo);
				postScheduleMouldPersionPO.setClassesNo(postScheduleMouldDetailDTO.getClassesNo());
				postScheduleMouldPersionPO.setCountNo(postScheduleMouldDetailDTO.getCountNo());
				List<PostScheduleMouldOpDO> classList = PostScheduleMouldClassesConverter.INSTANCE.dto2domain(postScheduleMouldDetailDTO.getOpList());
				for (PostScheduleMouldOpDO postScheduleMouldOpDO : classList) {
					postScheduleMouldPersionPO.setOpNo(postScheduleMouldOpDO.getOpNo());
					int y = postScheduleMouldPersionMapper.insert(postScheduleMouldPersionPO);
					if (y != 1) {
						log.error("新增模板人员失败！");
						throw new RuntimeException("新增模板人员失败！");
					}
				}
			}
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public PageDTO<PostScheduleMouldDTO> qryPostScheduleMouldBypage(Map<String, Object> param) {
		log.info("------------[qryBypage]PostScheduleMouldService-------------");
		PageDTO<PostScheduleMouldDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		int pageSize = PageUtil.transParam2Page(param, pageDTO);
		int totalRow = postScheduleMouldMapper.qryTotalRow(param);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<PostScheduleMouldDO> doList = postScheduleMouldMapper.qryByPage(param);
		for (PostScheduleMouldDO postScheduleMouldDO : doList) {
			String mouldNo = postScheduleMouldDO.getMouldNo();
			List<PostScheduleMouldDetailDO> postScheduleMouldDetailDO = postScheduleMouldPersionMapper.qryDetailByMouldNo(mouldNo);
			for (PostScheduleMouldDetailDO detail : postScheduleMouldDetailDO) {
				String classesNo = detail.getClassesNo();
				Integer countNo = detail.getCountNo();
				Map<String, Object> map = new HashMap<>();
				map.put("classesNo", classesNo);
				map.put("countNo", countNo);
				map.put("mouldNo", mouldNo);
				List<PostScheduleMouldOpDO> opList = postScheduleMouldPersionMapper.qryOpDetailByNos(map);
				detail.setOpList(opList);
			}
			postScheduleMouldDO.setDetailList(postScheduleMouldDetailDO);
		}
		List<PostScheduleMouldDTO> dtoList = PostScheduleMouldConverter.INSTANCE.do2dto(doList);

		pageDTO.setRetList(dtoList);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setResult(RetCodeEnum.SUCCEED);
		return pageDTO;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public DTO modPostScheduleMould(PostScheduleMouldDTO postScheduleMouldDTO) {
		log.info("------------[mod]PostScheduleMouldService-------------");
		String MouldNo = postScheduleMouldDTO.getMouldNo();
		//postScheduleMouldMapper.deleteByPrimaryKey(oldMouldNo);
		postScheduleMouldPersionMapper.deleteByPrimaryKey(MouldNo);
		/*String maxNo = postScheduleMouldMapper.qryMaxNo();
		String newMouldNo;
		if (StringUtil.isNullorEmpty(maxNo)) {
			newMouldNo = "0000000001";
		} else {
			Integer tmpInt = Integer.parseInt(maxNo) + 1;
			newMouldNo = String.format("%010d", tmpInt);
		}
		String mouldName = postScheduleMouldDTO.getMouldName();
		Integer mouldType = postScheduleMouldDTO.getMouldType();
		String orgNo = postScheduleMouldDTO.getOrgNo();
		String postNo = postScheduleMouldDTO.getPostNo();
		PostScheduleMouldPO postScheduleMouldPO = new PostScheduleMouldPO();
		postScheduleMouldPO.setMouldNo(newMouldNo);
		postScheduleMouldPO.setMouldType(mouldType);
		postScheduleMouldPO.setMouldName(mouldName);
		postScheduleMouldPO.setOrgNo(orgNo);
		postScheduleMouldPO.setPostNo(postNo);
		int x = postScheduleMouldMapper.insert(postScheduleMouldPO);
		if (x != 1) {
			log.error("新增模板失败！");
			throw new RuntimeException("新增模板失败！");
		}*/
		List<PostScheduleMouldDetailDTO> detailList = postScheduleMouldDTO.getDetailList();
		if (detailList.size() > 0) {
			for (PostScheduleMouldDetailDTO postScheduleMouldDetailDTO : detailList) {
				if (StringUtil.isNullorEmpty(postScheduleMouldDetailDTO.getClassesNo()) ||
						postScheduleMouldDetailDTO.getCountNo() == null) {
					throw new RuntimeException("缺少参数");
				}
				if (postScheduleMouldDetailDTO.getOpList() == null || postScheduleMouldDetailDTO.getOpList().size() == 0) {
					continue;
				}
				PostScheduleMouldPersionPO postScheduleMouldPersionPO = new PostScheduleMouldPersionPO();
				postScheduleMouldPersionPO.setMouldNo(MouldNo);
				postScheduleMouldPersionPO.setClassesNo(postScheduleMouldDetailDTO.getClassesNo());
				postScheduleMouldPersionPO.setCountNo(postScheduleMouldDetailDTO.getCountNo());
				List<PostScheduleMouldOpDO> classList = PostScheduleMouldClassesConverter.INSTANCE.dto2domain(postScheduleMouldDetailDTO.getOpList());
				for (PostScheduleMouldOpDO postScheduleMouldOpDO : classList) {
					postScheduleMouldPersionPO.setOpNo(postScheduleMouldOpDO.getOpNo());
					int y = postScheduleMouldPersionMapper.insert(postScheduleMouldPersionPO);
					if (y != 1) {
						log.error("新增模板人员失败！");
						throw new RuntimeException("新增模板人员失败！");
					}
				}
			}
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public DTO deletePostScheduleMould(String mouldNo) {
		log.info("------------[delete]PostScheduleMouldService-------------");
		int x = postScheduleMouldPersionMapper.qryCountByMouldNo(mouldNo);
		int y = postScheduleMouldMapper.deleteByPrimaryKey(mouldNo);
		if (y == 1) {
			int z = postScheduleMouldPersionMapper.deleteByPrimaryKey(mouldNo);
			if (z != x) {
				log.error("删除模板失败！");
				throw new RuntimeException("删除模板失败！");
			}
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	@Override
	public ListDTO<PostScheduleMouldDTO> qryAllMouldByOrgNoAndPostNo(Map<String, Object> map) {
		log.info("------------[qryAllMouldByOrgNoAndPostNo]PostScheduleMouldService-------------");
		ListDTO<PostScheduleMouldDTO> listDTO = new ListDTO<>(RetCodeEnum.FAIL);
		List<PostScheduleMouldDO> postScheduleMouldDOList = postScheduleMouldMapper.qryAll(map);
		//if (postScheduleMouldDOList.size()>0){
		for (PostScheduleMouldDO postScheduleMouldDO : postScheduleMouldDOList) {
			String mouldNo = postScheduleMouldDO.getMouldNo();
			List<PostScheduleMouldDetailDO> postScheduleMouldDetailDO = postScheduleMouldPersionMapper.qryDetailByMouldNo(mouldNo);
			for (PostScheduleMouldDetailDO detail : postScheduleMouldDetailDO) {
				String classesNo = detail.getClassesNo();
				Integer countNo = detail.getCountNo();
				Map<String, Object> detailMap = new HashMap<>();
				detailMap.put("classesNo", classesNo);
				detailMap.put("countNo", countNo);
				List<PostScheduleMouldOpDO> opList = postScheduleMouldPersionMapper.qryOpDetailByNos(detailMap);
				detail.setOpList(opList);
			}
			postScheduleMouldDO.setDetailList(postScheduleMouldDetailDO);
		}
		List<PostScheduleMouldDTO> dtoList = PostScheduleMouldConverter.INSTANCE.do2dto(postScheduleMouldDOList);
		//}
		listDTO.setResult(RetCodeEnum.SUCCEED);
		listDTO.setRetList(dtoList);
		return listDTO;
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public DTO createSchedule(CreateScheduleDTO createScheduleDTO) {
		String mouldNo = createScheduleDTO.getMouldNo();
		String orgNo = createScheduleDTO.getOrgNo();
		String postNo = createScheduleDTO.getPostNo();
		String scheduleMonth = createScheduleDTO.getScheduleMonth();
		String date = scheduleMonth + "-01";
		int year = StringUtil.objectToInt(scheduleMonth.substring(0, 4));
		int month = StringUtil.objectToInt(scheduleMonth.substring(5, 7));
		Calendar c = Calendar.getInstance();
		c.set(year, month, 0);
		int days = c.get(Calendar.DAY_OF_MONTH);
		List<PostScheduleDetailDO> postScheduleDetailDOList = postScheduleMouldMapper.qryInfoByMouldNo(mouldNo);
		for (PostScheduleDetailDO postScheduleDetailDO : postScheduleDetailDOList) {
			Integer countNo = postScheduleDetailDO.getCountNo();
			Map<String, Object> cMap = new HashMap<>();
			cMap.put("countNo", countNo);
			cMap.put("mouldNo", mouldNo);
			List<PostScheduleClassDetailDO> classesList = postScheduleMouldPersionMapper.qryClassesNos(cMap);
			for (PostScheduleClassDetailDO postScheduleClassDetailDO : classesList) {
				String classesNo = postScheduleClassDetailDO.getClassesNo();
				Map<String, Object> map = new HashMap<>();
				map.put("classesNo", classesNo);
				map.put("countNo", countNo);
				map.put("mouldNo", mouldNo);
				List<String> opList = postScheduleMouldPersionMapper.qryOpNos(map);
				postScheduleClassDetailDO.setOpList(opList);
			}
			postScheduleDetailDO.setClassesList(classesList);
		}
		Collections.sort(postScheduleDetailDOList);
		//插入排班计划表
		String planNo = java.util.UUID.randomUUID().toString().replace("-", "");
		PostSchedulePO postSchedulePO = new PostSchedulePO();
		postSchedulePO.setPlanNo(planNo);
		postSchedulePO.setPostNo(postNo);
		postSchedulePO.setOrgNo(orgNo);
		postSchedulePO.setMouldNo(mouldNo);
		postSchedulePO.setCreateTime(CalendarUtil.getSysTimeYMD());
		postSchedulePO.setScheduleMonth(scheduleMonth);
		postSchedulePlanMapper.deleteByMonth(scheduleMonth);
		postScheduleMapper.deleteByMonth(scheduleMonth);
		postScheduleMapper.insert(postSchedulePO);

		int mouldType = postScheduleMouldMapper.qryTypeByNo(mouldNo);
		if (mouldType == 1) {
			//周模板
			String fDate;
			for (int i = 1; i <= days; i++) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date sDate = null;
				try {
					sDate = sdf.parse(date);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Calendar cal = Calendar.getInstance();
				if (sDate != null) {
					cal.setTime(new Date(sDate.getTime()));
				}
				//cal.get(Calendar.DAY_OF_WEEK)结果 1是星期日、2是星期一、3是星期二、4是星期三、5是星期四、6是星期五、7是星期六
				//dayFlag作为索引进行去数据
				int dayFlag = cal.get(Calendar.DAY_OF_WEEK) - 2;
				if (dayFlag == -1) {
					dayFlag = 6;
				}
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				cal.add(Calendar.DAY_OF_MONTH, +1);
				fDate = formatter.format(cal.getTime()).toString();

				if (dayFlag >= postScheduleDetailDOList.size()) {
					date = fDate;
					continue;
				}
				List<PostScheduleClassDetailDO> classesList = postScheduleDetailDOList.get(dayFlag).getClassesList();
				Integer countNo = postScheduleDetailDOList.get(dayFlag).getCountNo();
				for (PostScheduleClassDetailDO postScheduleClassDetailDO : classesList) {
					List<String> opList = postScheduleClassDetailDO.getOpList();
					String classesNo = postScheduleClassDetailDO.getClassesNo();
					PostSchedulePlanPO postSchedulePlanPO = new PostSchedulePlanPO();
					postSchedulePlanPO.setPlanNo(planNo);
					postSchedulePlanPO.setPlanDate(StringUtil.parseString(i));
					postSchedulePlanPO.setScheduleMonth(scheduleMonth);
					postSchedulePlanPO.setClassesNo(classesNo);
					for (String opNo : opList) {
						postSchedulePlanPO.setOpNo(opNo);
						postSchedulePlanMapper.insert(postSchedulePlanPO);
					}
				}
				date = fDate;
			}
			return new DTO(RetCodeEnum.SUCCEED);
		}
		//月模板
		int n = 0;
		for (int m = 1; m <= days; m++) {
			List<PostScheduleClassDetailDO> classesList = postScheduleDetailDOList.get(n).getClassesList();
			for (PostScheduleClassDetailDO postScheduleClassDetailDO : classesList) {
				String classesNo = postScheduleClassDetailDO.getClassesNo();
				List<String> opList = postScheduleClassDetailDO.getOpList();
				PostSchedulePlanPO postSchedulePlanPO = new PostSchedulePlanPO();
				postSchedulePlanPO.setPlanNo(planNo);
				postSchedulePlanPO.setPlanDate(StringUtil.parseString(StringUtil.parseString(m)));
				postSchedulePlanPO.setScheduleMonth(scheduleMonth);
				postSchedulePlanPO.setClassesNo(classesNo);
				for (String opNo : opList) {
					postSchedulePlanPO.setOpNo(opNo);
					postSchedulePlanMapper.insert(postSchedulePlanPO);
				}
				n++;
				if (n >= postScheduleDetailDOList.size()) {
					n = 0;
				}
			}
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public DTO modSchedule(PostScheduleDTO postScheduleDTO) {
		PostScheduleDO postScheduleDO = PostScheduleConverter.INSTANCE.dto2domain(postScheduleDTO);
		String planNo = postScheduleDO.getPlanNo();
		String scheduleMonth = postScheduleDO.getScheduleMonth();
		postSchedulePlanMapper.deleteByMonth(scheduleMonth);
		List<PostSchedulePlanDO> planList = postScheduleDO.getDetailList();
		for (PostSchedulePlanDO postSchedulePlanDO : planList) {
			String planDate = postSchedulePlanDO.getPlanDate();
			String classesNo = postSchedulePlanDO.getClassesNo();
			List<PostScheduleMouldOpDO> opList = postSchedulePlanDO.getOpList();
			for (PostScheduleMouldOpDO postScheduleMouldOpDO : opList) {
				String opNo = postScheduleMouldOpDO.getOpNo();
				PostSchedulePlanPO postSchedulePlanPO = new PostSchedulePlanPO();
				postSchedulePlanPO.setPlanNo(planNo);
				postSchedulePlanPO.setClassesNo(classesNo);
				postSchedulePlanPO.setScheduleMonth(scheduleMonth);
				postSchedulePlanPO.setPlanDate(planDate);
				postSchedulePlanPO.setOpNo(opNo);
				int x = postSchedulePlanMapper.insert(postSchedulePlanPO);
				if (x != 1) {
					log.error("修改排班失败！");
					throw new RuntimeException("修改排班失败!");
				}
			}
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public PageDTO<PostScheduleDTO> qryScheduleByPage(Map<String, Object> param) {
		PageDTO<PostScheduleDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		int pageSize = PageUtil.transParam2Page(param, pageDTO);
		int totalRow = postScheduleMapper.qryTotalRow(param);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<PostScheduleDO> doList = postScheduleMapper.qryByPage(param);
		for (PostScheduleDO postScheduleDO : doList) {
			String planNo = postScheduleDO.getPlanNo();
			String scheduleMonth = postScheduleDO.getScheduleMonth();
			List<PostSchedulePlanDO> detailList = postSchedulePlanMapper.qryPlanInfoByNo(planNo);
			for (PostSchedulePlanDO postSchedulePlanDO : detailList) {
				String classesNo = postSchedulePlanDO.getClassesNo();
				String planDate = postSchedulePlanDO.getPlanDate();
				Map<String, Object> map = new HashMap<>();
				map.put("classesNo", classesNo);
				map.put("planDate", planDate);
				map.put("scheduleMonth", scheduleMonth);
				List<PostScheduleMouldOpDO> opList = postSchedulePlanMapper.qryOpDetail(map);
				postSchedulePlanDO.setOpList(opList);
			}
			postScheduleDO.setDetailList(detailList);
		}
		List<PostScheduleDTO> dtoList = PostScheduleConverter.INSTANCE.domain2dto(doList);

		pageDTO.setRetList(dtoList);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setResult(RetCodeEnum.SUCCEED);
		return pageDTO;
	}

	@Override
	public DTO deleteSchedule(String planNo) {
		int x = postSchedulePlanMapper.qryCountByPlanNo(planNo);
		int y = postScheduleMapper.delete(planNo);
		if (y != 1) {
			log.error("删除排班计划失败！");
			return new DTO(RetCodeEnum.FAIL);
		}
		int z = postSchedulePlanMapper.deleteByPlanNo(planNo);
		if (z != x) {
			log.error("删除计划详情失败！");
			return new DTO(RetCodeEnum.FAIL);
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public List<OutSourcingLineMapInnerDTO> qryInfoByDutyDate(String dutyDate) {

		List<OutsourcingLineMapPO> lineMapPOS= outsourcingLineMapMapper.qryInfoByDate(dutyDate);
		List<OutSourcingLineMapInnerDTO> dtoList = OutsourcingLineMapConverter.INSTANCE.po2dto(lineMapPOS);
		return dtoList;
	}

	@Override
	public List<String> qryOpNameByLineRunNo(String lineRunNo) {
		return outsourcingLineMapMapper.qryNameByNo(lineRunNo);
	}
}
