package com.zjft.microservice.treasurybrain.linecenter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.channelcenter.web.ClrCenterResource;
import com.zjft.microservice.treasurybrain.channelcenter.web.SysOrgResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.ClrCenterInnerResource;
import com.zjft.microservice.treasurybrain.channelcenter.web_inner.SysOrgInnerResource;
import com.zjft.microservice.treasurybrain.common.domain.ClrCenterTable;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.linecenter.domain.NetPointMatrix;
import com.zjft.microservice.treasurybrain.linecenter.domain.NetPointMatrixKey;
import com.zjft.microservice.treasurybrain.linecenter.dto.NetpointMatrixDTO;
import com.zjft.microservice.treasurybrain.linecenter.repository.LineNetPointMatrixMapper;
import com.zjft.microservice.treasurybrain.linecenter.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class RouteServiceImpl implements RouteService {
	@Resource
	private LineNetPointMatrixMapper lineNetPointMatrixMapper;

	@Resource
	private ClrCenterInnerResource clrCenterInnerResource;

	@Resource
	private SysOrgInnerResource sysOrgInnerResource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static double CHINA_EAST_LATLNG = 135.1,
			CHINA_WEST_LATLNG = 73.5,
			CHINA_NORTH_LATLNG = 53.5,
			CHINA_SOUTH_LATLNG = 3.8;

	@Override
	public Map<String, Object> qryNetMatrixByPage(String jsonParam) {
		log.info("------------[geNetMatrixByPage]MatrixServiceImpl-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(jsonParam);
			Integer pageSize = StringUtil.objectToInt(params.get("pageSize"));
			Integer curPage = StringUtil.objectToInt(params.get("curPage"));
			String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));
			Integer type = StringUtil.objectToInt(params.get("type"));
			Integer tactic = StringUtil.objectToInt(params.get("tactic"));
			Integer dataType = StringUtil.objectToInt(params.get("dataType"));
			String startPointNo = StringUtil.parseString(params.get("startPointNo"));
			String endPointNo = StringUtil.parseString(params.get("endPointNo"));

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("startRow", pageSize * (curPage - 1));
			paramMap.put("endRow", pageSize * curPage);
			paramMap.put("pageSize", pageSize);
			paramMap.put("clrCenterNo", clrCenterNo);
			paramMap.put("type", type);
			paramMap.put("tactic", tactic);
			paramMap.put("dataType", dataType);
			paramMap.put("startPointNo", startPointNo);
			paramMap.put("endPointNo", endPointNo);

			int totalRow = lineNetPointMatrixMapper.getNetMatrixRow(paramMap);
			int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);

			List<NetPointMatrix> retNetPointMatrix = lineNetPointMatrixMapper.getNetMatrix(paramMap);

			List<NetpointMatrixDTO> retList = new ArrayList<NetpointMatrixDTO>();

			for (NetPointMatrix netPointMatrix : retNetPointMatrix) {
				NetpointMatrixDTO netpointMatrixDTO = new NetpointMatrixDTO();

				netpointMatrixDTO.setStartPointNo(netPointMatrix.getStartPointNo());
				netpointMatrixDTO.setStartPointName(netPointMatrix.getStartPointNo());
				netpointMatrixDTO.setEndPointNo(netPointMatrix.getEndPointNo());
				netpointMatrixDTO.setEndPointName(netPointMatrix.getEndPointNo());
				
				netpointMatrixDTO.setType(netPointMatrix.getType());
				netpointMatrixDTO.setTactic(netPointMatrix.getTactic());
				netpointMatrixDTO.setDistance(netPointMatrix.getDistance());
				netpointMatrixDTO.setTimeCost(netPointMatrix.getTimeCost());
				netpointMatrixDTO.setNote(netPointMatrix.getNote());

				retList.add(netpointMatrixDTO);
			}

			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询成功！");

			retMap.put("retList", retList);
			retMap.put("totalRow", totalRow);
			retMap.put("totalPage", totalPage);
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "网点路程管理查询异常!");
			log.error("getNetMatrix Fail: ", e);
		}
		return retMap;
	}

	@Override
	public Map<String, Object> qryPathLinked(Map<String, Object> paramMap) {
		log.info("------------[getPathLinked]MatrixServiceImpl-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			String clrCenterNo = StringUtil.parseString(paramMap.get("clrCenterNo"));
			Integer dataType = StringUtil.objectToInteger(paramMap.get("dataType"));
			List<Map<String, Object>> retList = lineNetPointMatrixMapper.getPathLinked(clrCenterNo, dataType);
			retMap.put("retList", retList);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "获取清分中心的已关联路径数异常!");
			log.error("getPathLinked Fail: ", e);
		}
		return retMap;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public Map<String, Object> linkPath(String jsonParam) {
		log.info("------------[linkPath]MatrixServiceImpl-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
//		try{
		JSONObject params = JSONUtil.parseJSONObject(jsonParam);
		String clrCenterNo = StringUtil.parseString(params.get("clrCenterNo"));
		int cover = StringUtil.objectToInt(params.get("cover"));
		int tactic = StringUtil.objectToInt(params.get("tactic"));
		int dataType = StringUtil.objectToInt(params.get("dataType"));
		if (dataType == -1) {
			dataType = 1;
		}

//		List<Map<String, Object>> clrCenterList = clrCenterTableMapper.getClrCenterByClrNo(clrCenterNo);
		List<Map<String, Object>> clrCenterList = clrCenterInnerResource.getClrCenterByClrNo(clrCenterNo);
		if (clrCenterList == null || clrCenterList.size() == 0) {
			retMap.put("retMsg", "编号为" + clrCenterNo + "的清机中心不存在");
			log.error("[linkPath]编号为" + clrCenterNo + "的清机中心不存在");
			return retMap;
		}

		Map<String, Object> clrCenterTableMap = clrCenterList.get(0);

		if (StringUtil.objectToInt(clrCenterTableMap.get("netPointMatrixStatus")) == 1) {
			retMap.put("retCode", RetCodeEnum.SYS_BUSY.getCode());
			retMap.put("retMsg", "编号为" + clrCenterNo + "的清机中心正在关联");
			log.info("[linkPath]编号为" + clrCenterNo + "的清机中心正在关联");
			return retMap;
		}

//			//startup a link path thread
//			LinkPathThread tLinPath = new LinkPathThread(clrCenterNo, cover, tactic);
//			Thread t = new Thread(tLinPath);
//			t.start();


		// new
		String bankOrgNo = StringUtil.parseString(clrCenterTableMap.get("bankOrgNo"));
		this.process(clrCenterNo, bankOrgNo, cover, tactic, dataType);


		retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
		retMap.put("retMsg", "请求关联成功！");
//		} catch(Exception e) {
//			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
//			retMap.put("retMsg", "请求关联异常!");
//			log.error("linkPath Fail: ", e);
//		}
		return retMap;
	}

	@Override
	public Map<String, Object> qryClrNetPointMatrixStatus(String clrCenterNo) {
		log.info("------------[getClrNetPointMatrixStatus]MatrixServiceImpl-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
//			ClrCenterTable clrCenterTable = clrCenterTableMapper.selectByPrimaryKey(clrCenterNo);
			ClrCenterTable clrCenterTable = clrCenterInnerResource.selectByPrimaryKey(clrCenterNo);

			if (clrCenterTable == null) {
				retMap.put("retMsg", "编号为" + clrCenterNo + "的清机中心不存在");
				log.error("[getClrNetPointMatrixStatus]编号为" + clrCenterNo + "的清机中心不存在");
				return retMap;
			}

			retMap.put("status", clrCenterTable.getNetpointMatrixStatus());
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询成功！");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "获取清分中心的关联状态异常!");
			log.error("getClrNetPointMatrixStatus Fail: ", e);
		}
		return retMap;
	}


	public List<Map<String, Object>> getPoints(String clrCenterNo, String clrCenterOrgNo, int dataType) {
		List<Map<String, Object>> points = new ArrayList<Map<String, Object>>();

		if (dataType == 2) {
			//获取关联网点
//			List<Map<String, Object>> retOrgTables = sysOrgMapper.getNetpointsByClrNo(clrCenterNo);
			List<Map<String, Object>> retOrgTables = sysOrgInnerResource.getNetpointsByClrNo(clrCenterNo);

			if (retOrgTables == null || retOrgTables.size() == 0) {
				log.info("[linkPath]编号为[" + clrCenterNo + "]的清机中心关联网点为空，结束关联");
				return points;
			}

			for (Map<String, Object> orgMap : retOrgTables) {
				Map<String, Object> entity = new HashMap<String, Object>();
				entity.put("no", orgMap.get("no"));
				entity.put("x", orgMap.get("x"));
				entity.put("y", orgMap.get("y"));
				entity.put("type", orgMap.get("no").equals(clrCenterOrgNo) ? StatusEnum.MatrixPointType.CENTER.getValue()
						: StatusEnum.MatrixPointType.NETPOINT.getValue());
				//异常坐标点剔出
				if (orgMap.get("X") == null || orgMap.get("Y") == null ||
						StringUtil.ch2Double(orgMap.get("X").toString()) < CHINA_WEST_LATLNG || StringUtil.ch2Double(orgMap.get("X").toString()) > CHINA_EAST_LATLNG ||
						StringUtil.ch2Double(orgMap.get("Y").toString()) < CHINA_SOUTH_LATLNG || StringUtil.ch2Double(orgMap.get("Y").toString()) > CHINA_NORTH_LATLNG) {
					continue;
				}
				points.add(entity);
			}
		}
		if (dataType == 1) {

			//获取关联设备去重坐标
			String getDistinctPointsByClrNoSql = "SELECT DISTINCT dev1.NO,  dev1.X, dev1.Y FROM DEV_BASE_INFO dev1, DEV_BASE_INFO dev2 " +
					" WHERE dev1.X = dev2.X AND dev1.Y = dev2.Y AND dev1.CLR_CENTER_NO = '" + clrCenterNo + "'";
			List<Map<String, Object>> retDistinctPointsList = jdbcTemplate.queryForList(getDistinctPointsByClrNoSql);
			if (retDistinctPointsList == null || retDistinctPointsList.size() == 0) {
				log.info("[linkPath]编号为[" + clrCenterNo + "]的设备不存在，结束关联");
				return points;
			}
			for (int i = 0; i < retDistinctPointsList.size(); i++) {
				Map tmpMap = retDistinctPointsList.get(i);
				String no = StringUtil.parseString(tmpMap.get("NO"));
				String x = StringUtil.parseString(tmpMap.get("X"));
				String y = StringUtil.parseString(tmpMap.get("Y"));

				Map<String, Object> entity = new HashMap<>();
				entity.put("no", no);
				entity.put("x", x);
				entity.put("y", y);
				entity.put("type", 1);
				//异常坐标点剔出
				if (x == null || y == null ||
						Double.parseDouble(x) < CHINA_WEST_LATLNG || Double.parseDouble(x) > CHINA_EAST_LATLNG ||
						Double.parseDouble(y) < CHINA_SOUTH_LATLNG || Double.parseDouble(y) > CHINA_NORTH_LATLNG) {
					continue;
				}
				points.add(entity);
			}
		}

		return points;
	}


	/**
	 * From HZ BANK
	 * link path thread
	 */
	public class LinkPathThread implements Runnable {
		private String clrCenterNo;
		private int cover;
		private int tactic;

		public LinkPathThread(String clrCenterNo, int cover, int tactic) {
			this.clrCenterNo = clrCenterNo;
			this.cover = cover;
			this.tactic = tactic;
		}

		@Override
		public void run() {
			try {
				log.info("编号为[" + clrCenterNo + "]的清机中心请求开始关联");
				String ak = StringUtil.parseString(CfgProperty.getProperty("mapApiAk"));

				//获取清分中心对应的网点
//				List<String> clrCenterOrgNoList = clrCenterTableMapper.getClrCenterOrgNo(clrCenterNo);
				List<String> clrCenterOrgNoList = clrCenterInnerResource.getClrCenterOrgNo(clrCenterNo);
				if (clrCenterOrgNoList == null || clrCenterOrgNoList.size() == 0) {
					log.error("清分中心[" + clrCenterNo + "]对应的网点不存在");
					return;
				}
				String clrCenterOrgNo = clrCenterOrgNoList.get(0);

				//获取关联网点
//				List<Map<String, Object>> retOrgTables = sysOrgMapper.getNetpointsByClrNo(clrCenterNo);
				List<Map<String, Object>> retOrgTables = sysOrgInnerResource.getNetpointsByClrNo(clrCenterNo);
				if (retOrgTables == null || retOrgTables.size() == 0) {
					log.info("[linkPath]编号为[" + clrCenterNo + "]的清机中心关联网点为空，结束关联");
					return;
				}

				List<Map<String, Object>> points = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> orgMap : retOrgTables) {
					Map<String, Object> entity = new HashMap<String, Object>();
					entity.put("no", orgMap.get("NO"));
					entity.put("x", orgMap.get("X"));
					entity.put("y", orgMap.get("Y"));
					entity.put("type", orgMap.get("NO").equals(clrCenterOrgNo) ? StatusEnum.MatrixPointType.CENTER.getValue()
							: StatusEnum.MatrixPointType.NETPOINT.getValue());
					//异常坐标点剔出
					if (orgMap.get("X") == null || orgMap.get("Y") == null ||
							StringUtil.ch2Double(orgMap.get("X").toString()) < CHINA_WEST_LATLNG || StringUtil.ch2Double(orgMap.get("X").toString()) > CHINA_EAST_LATLNG ||
							StringUtil.ch2Double(orgMap.get("Y").toString()) < CHINA_SOUTH_LATLNG || StringUtil.ch2Double(orgMap.get("Y").toString()) > CHINA_NORTH_LATLNG) {
						continue;
					}
					points.add(entity);
				}

				//获取路程信息
				//覆盖关联
				int pointNum = points.size();
				int pathLinkedCount = lineNetPointMatrixMapper.getLinkedListByClrNo(tactic, clrCenterNo);
				//增量关联
				if (cover == 0 && pathLinkedCount == pointNum * (pointNum - 1)) {
					log.info("[linkPath]编号为[" + clrCenterNo + "]的清机中心与网点均已关联，结束关联");
					return;
				}

				//重复关联,先清空已有关联
				if (cover == 1 && pathLinkedCount > 0) {
					for (int i = 0; i < pathLinkedCount; i += 10000) {
						lineNetPointMatrixMapper.deleteLinkedListByClrNo(tactic, clrCenterNo);
					}
				}

				//状态置为正在关联
//				ClrCenterTable clrCenterTable = clrCenterTableMapper.selectByPrimaryKey(clrCenterNo);
				ClrCenterTable clrCenterTable = clrCenterInnerResource.selectByPrimaryKey(clrCenterNo);
				clrCenterTable.setNetpointMatrixStatus(1);
//				clrCenterTableMapper.updateByPrimaryKeySelective(clrCenterTable);
				clrCenterInnerResource.updateByPrimaryKeySelective(clrCenterTable);

				List<Map<String, Object>> startPoints = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> endPoints = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < pointNum; i++) {
					startPoints.clear();
					endPoints.clear();
					Map<String, Object> startPoint = points.get(i);
					startPoints.add(startPoint);
					List<Map<String, Object>> linkedEndPointList = lineNetPointMatrixMapper.getLinkedList(tactic, startPoint.get("no").toString());
					for (int j = 0; j < pointNum; j++) {
						if (i == j) {
							//自身不与自身关联
							continue;
						}
						//增量关联
						if (cover == 0) {
							//剔除已经关联路程
							boolean isLinked = false;
							if (linkedEndPointList != null && linkedEndPointList.size() > 0) {
								for (Map<String, Object> linkedEndPoint : linkedEndPointList) {
									if (linkedEndPoint.get("no").toString().equals(points.get(j).get("no").toString())) {
										isLinked = true;
										break;
									}
								}
							}
							if (isLinked) {
								continue;
							}
						}
						endPoints.add(points.get(j));
						if (endPoints.size() == 25) {
							Thread.sleep(3000);
							List<Map<String, Object>> matrixList = HttpClientUtil.routematrix(startPoints, endPoints, tactic, ak);
							if (matrixList == null || matrixList.size() == 0) {
								endPoints.clear();
								continue;
							}
							ArrayList<NetPointMatrix> netPointMatrixList = new ArrayList<NetPointMatrix>();
							for (int k = 0; k < matrixList.size(); k++) {
								NetPointMatrix netPointMatrix = new NetPointMatrix();
								netPointMatrix.setStartPointNo(startPoint.get("no").toString());
								netPointMatrix.setEndPointNo(endPoints.get(k).get("no").toString());

								if (StringUtil.objectToInt(startPoint.get("type")) == StatusEnum.MatrixPointType.NETPOINT.getValue() && StringUtil.objectToInt(endPoints.get(k).get("type")) == StatusEnum.MatrixPointType.NETPOINT.getValue()) {
									netPointMatrix.setType(StatusEnum.MatrixType.N_TO_N.getValue());
								} else if (StringUtil.objectToInt(startPoint.get("type")) == StatusEnum.MatrixPointType.CENTER.getValue() && StringUtil.objectToInt(endPoints.get(k).get("type")) == StatusEnum.MatrixPointType.NETPOINT.getValue()) {
									netPointMatrix.setType(StatusEnum.MatrixType.C_TO_N.getValue());
								} else if (StringUtil.objectToInt(startPoint.get("type")) == StatusEnum.MatrixPointType.NETPOINT.getValue() && StringUtil.objectToInt(endPoints.get(k).get("type")) == StatusEnum.MatrixPointType.CENTER.getValue()) {
									netPointMatrix.setType(StatusEnum.MatrixType.N_TO_C.getValue());
								}

								netPointMatrix.setTactic(tactic);
								netPointMatrix.setDistance(StringUtil.objectToInt(matrixList.get(k).get("distance")));
								netPointMatrix.setTimeCost(StringUtil.objectToInt(matrixList.get(k).get("timecost")));
								netPointMatrix.setClrCenterNo(clrCenterNo);
								netPointMatrixList.add(netPointMatrix);
							}
							if (netPointMatrixList != null && netPointMatrixList.size() > 0) {
								lineNetPointMatrixMapper.createOrUpdateBatch(netPointMatrixList);
							}
							endPoints.clear();
						}
					}

					if (endPoints.size() > 0 && endPoints.size() < 25) {
						Thread.sleep(3000);
						List<Map<String, Object>> matrixList = HttpClientUtil.routematrix(startPoints, endPoints, tactic, ak);
						if (matrixList == null || matrixList.size() == 0) {
							continue;
						}
						ArrayList<NetPointMatrix> netPointMatrixList = new ArrayList<NetPointMatrix>();
						for (int k = 0; k < matrixList.size(); k++) {
							NetPointMatrix netPointMatrix = new NetPointMatrix();
							netPointMatrix.setStartPointNo(startPoint.get("no").toString());
							netPointMatrix.setEndPointNo(endPoints.get(k).get("no").toString());

							if (StringUtil.objectToInt(startPoint.get("type")) == StatusEnum.MatrixPointType.NETPOINT.getValue() && StringUtil.objectToInt(endPoints.get(k).get("type")) == StatusEnum.MatrixPointType.NETPOINT.getValue()) {
								netPointMatrix.setType(StatusEnum.MatrixType.N_TO_N.getValue());
							} else if (StringUtil.objectToInt(startPoint.get("type")) == StatusEnum.MatrixPointType.CENTER.getValue() && StringUtil.objectToInt(endPoints.get(k).get("type")) == StatusEnum.MatrixPointType.NETPOINT.getValue()) {
								netPointMatrix.setType(StatusEnum.MatrixType.C_TO_N.getValue());
							} else if (StringUtil.objectToInt(startPoint.get("type")) == StatusEnum.MatrixPointType.NETPOINT.getValue() && StringUtil.objectToInt(endPoints.get(k).get("type")) == StatusEnum.MatrixPointType.CENTER.getValue()) {
								netPointMatrix.setType(StatusEnum.MatrixType.N_TO_C.getValue());
							}
							netPointMatrix.setTactic(tactic);
							netPointMatrix.setDistance(StringUtil.objectToInt(matrixList.get(k).get("distance")));
							netPointMatrix.setTimeCost(StringUtil.objectToInt(matrixList.get(k).get("timecost")));
							netPointMatrix.setClrCenterNo(clrCenterNo);
							netPointMatrixList.add(netPointMatrix);
						}
						if (netPointMatrixList != null && netPointMatrixList.size() > 0) {
							lineNetPointMatrixMapper.createOrUpdateBatch(netPointMatrixList);
						}
					}

				}

			} catch (Exception e) {
				log.error("[linkPath]编号为" + clrCenterNo + "的清机中心请求关联异常: ", e);
			} finally {
				try {
					//状态置为已经停止
//					ClrCenterTable clrCenterTable = clrCenterTableMapper.selectByPrimaryKey(clrCenterNo);
					ClrCenterTable clrCenterTable = clrCenterInnerResource.selectByPrimaryKey(clrCenterNo);
					clrCenterTable.setNetpointMatrixStatus(0);
//					clrCenterTableMapper.updateByPrimaryKeySelective(clrCenterTable);
					clrCenterInnerResource.updateByPrimaryKeySelective(clrCenterTable);
					log.info("[linkPath]编号为" + clrCenterNo + "的清机中心请求结束关联");
				} catch (Exception ex) {
					log.error("[linkPath]编号为" + clrCenterNo + "的清机中心请求结束关联,更新状态异常: ", ex);
				}
			}
		}

	}


	private static final Map<String, Object> matrixResult = new ConcurrentHashMap<>();

	private String process(String clrCenterNo, String clrCenterOrgNo, int cover, int tactic, int dataType) {
		Map<String, Integer> count = new ConcurrentHashMap<>();
		Map<String, Integer> count1 = new ConcurrentHashMap<>();
		count.put("count", 0);
		count1.put("count1", 0);

		log.info("编号为[" + clrCenterNo + "]的清机中心请求开始关联");
		String mapApiAkValue = StringUtil.parseString(CfgProperty.getProperty("mapApiAk"));

		//关联线程数量
		int threadNum = Integer.valueOf(4);
		log.debug("threadNum:" + threadNum);
		try {
			List<Map<String, Object>> distinctPoints = getPoints(clrCenterNo, clrCenterOrgNo, dataType);

			//开始进行坐标矩阵计算
			int distinctPointsNum = distinctPoints.size();
			if (distinctPoints.size() >= threadNum) {
				int value = distinctPointsNum / (threadNum - 1);
				for (int i = 0; i < (threadNum - 1); i++) {
					new Thread(new HandlerPointThread(distinctPointsNum, distinctPoints.subList(i * value, (i + 1) * value), distinctPoints, tactic, mapApiAkValue, count1)).start();
				}
				new Thread(new HandlerPointThread(distinctPointsNum, distinctPoints.subList((threadNum - 1) * value, distinctPoints.size()), distinctPoints, tactic, mapApiAkValue, count1)).start();
			} else {
				count1.put("count1", threadNum - 1);
				new Thread(new HandlerPointThread(distinctPointsNum, distinctPoints, distinctPoints, tactic, mapApiAkValue, count1)).start();
			}
			while (count1.get("count1") != threadNum) {
				synchronized (count1) {
					log.debug("count1 = " + count1.get("count1"));
					log.debug("matrixResult size:" + matrixResult.size());
					count1.wait();
				}
			}
			log.debug("end matrixResult size:" + matrixResult.size());
			//结束坐标计算


			List<Map<String, Object>> points = distinctPoints;

			//获取路程信息
			//覆盖关联
			int pointNum = points.size();
			int pathLinkedCount = lineNetPointMatrixMapper.getLinkedListByClrNo(tactic, clrCenterNo);

			//增量关联
			if (cover == 0 && pathLinkedCount == pointNum * (pointNum - 1)) {
				log.info("[linkPath]编号为[" + clrCenterNo + "]的清机中心与网点均已关联，结束关联");
				return "hasBeanLinked";
			}

			//重复关联,先清空已有关联
			if (cover == 1 && pathLinkedCount > 0) {
				lineNetPointMatrixMapper.deleteLinkedListByClrNo(tactic, clrCenterNo);
			}

			//状态置为正在关联
			ClrCenterTable clrCenterTable = new ClrCenterTable();
			clrCenterTable.setClrCenterNo(clrCenterNo);
			clrCenterTable.setNetpointMatrixStatus(1);
//			clrCenterTableMapper.updateByPrimaryKeySelective(clrCenterTable);
			clrCenterInnerResource.updateByPrimaryKeySelective(clrCenterTable);

			if (pointNum >= threadNum) {
				int value = pointNum / (threadNum - 1);
				for (int i = 0; i < (threadNum - 1); i++) {
					new Thread(new HandlerThread(pointNum, points.subList(i * value, (i + 1) * value), points, cover, tactic, clrCenterNo, count)).start();
				}
				new Thread(new HandlerThread(pointNum, points.subList((threadNum - 1) * value, points.size()), points, cover, tactic, clrCenterNo, count)).start();
			} else {
				count.put("count", threadNum - 1);
				new Thread(new HandlerThread(pointNum, points, points, cover, tactic, clrCenterNo, count)).start();
			}
			while (count.get("count") != threadNum) {
				synchronized (count) {
					log.debug("count = " + count.get("count"));
					count.wait();
				}
			}
			return "ok";
		} catch (NumberFormatException e) {
			log.error("[linkPath]编号为" + clrCenterNo + "的清机中心请求关联异常: ", e);
			return "fail";
		} catch (Exception e) {
			log.error("[linkPath]编号为" + clrCenterNo + "的清机中心请求关联异常: ", e);
			return "fail";
		} finally {
			try {
				//状态置为已经停止
				ClrCenterTable clrCenterTable = new ClrCenterTable();
				clrCenterTable.setClrCenterNo(clrCenterNo);
				clrCenterTable.setNetpointMatrixStatus(0);
//				clrCenterTableMapper.updateByPrimaryKeySelective(clrCenterTable);
				clrCenterInnerResource.updateByPrimaryKeySelective(clrCenterTable);
				log.info("[linkPath]编号为" + clrCenterNo + "的清机中心请求结束关联");
			} catch (Exception ex) {
				log.error("[linkPath]编号为" + clrCenterNo + "的清机中心请求结束关联,更新状态异常: ", ex);
			}
		}
	}


	/**
	 * 关联线程类
	 */
	private class HandlerThread implements Runnable {

		private int pointNum;
		private List<Map<String, Object>> points;
		private List<Map<String, Object>> allPoints;
		private int cover;
		private int tactic;
		private String clrCenterNo;
		private Map<String, Integer> count;

		public HandlerThread(int pointNum, List<Map<String, Object>> points, List<Map<String, Object>> allPoints, int cover, int tactic, String clrCenterNo, Map<String, Integer> count) {
			this.pointNum = pointNum;
			this.points = points;
			this.allPoints = allPoints;
			this.cover = cover;
			this.tactic = tactic;
			this.clrCenterNo = clrCenterNo;
			this.count = count;
		}

		@Override
		public void run() {
			try {
				List<Map<String, Object>> startPoints = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> endPoints = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < points.size(); i++) {
					startPoints.clear();
					endPoints.clear();
					Map<String, Object> startPoint = points.get(i);
					startPoints.add(startPoint);
					List<Map<String, Object>> linkedEndPointList = lineNetPointMatrixMapper.getLinkedList(tactic, startPoint.get("no").toString());
					Collection noList = new ArrayList();
					if (linkedEndPointList != null && linkedEndPointList.size() > 0) {
						for (int e = 0; e < linkedEndPointList.size(); e++) {
							Map tmpMap = linkedEndPointList.get(e);
							noList.add(StringUtil.parseString(tmpMap.get("NO")));
						}
					}

					List<Map<String, Object>> matrixList = new ArrayList<>();
					for (int j = 0; j < pointNum; j++) {
						if (points.get(i).get("no").equals(allPoints.get(j).get("no"))) {
							//自身不与自身关联
							continue;
						}
						//增量关联
						if (cover == 0) {
							//剔除已经关联路程
							boolean isLinked = false;
							if (linkedEndPointList.size() > 0) {
								for (Object no : noList) {
									if (no.toString().equals(allPoints.get(j).get("no").toString())) {
										isLinked = true;
										break;
									}
								}
							}
							if (isLinked) {
								continue;
							}
						}
						endPoints.add(allPoints.get(j));
						Map<String, Object> matrix = new HashMap<>();
						if (startPoint.get("x").equals(allPoints.get(j).get("x")) && startPoint.get("y").equals(allPoints.get(j).get("y"))) {
							matrix.put("distance", 0);
							matrix.put("timecost", 0);
							log.debug("distance: 0");
							log.debug("timecost: 0");
						} else {
							String key = startPoint.get("x") + "|" + startPoint.get("y") + "|" + allPoints.get(j).get("x") + "|" + allPoints.get(j).get("y");
							int[] data = (int[]) (matrixResult.get(key));
							matrix.put("distance", data[0]);
							matrix.put("timecost", data[1]);
							log.debug("distance: " + data[0]);
							log.debug("timecost: " + data[1]);
						}
						matrixList.add(matrix);
					}
					createOrUpdateNetPointMatrixList(matrixList, startPoint, endPoints, tactic, clrCenterNo);
				}
				count();
			} catch (Exception e) {
				log.error("出错咯", e);
			}
		}

		private void count() {
			synchronized (count) {
				log.debug("threadBefore - count = " + count.get("count"));
				int countValue = count.get("count");
				countValue++;
				count.put("count", countValue);
				log.debug("threadAfter - count = " + count.get("count"));
				count.notifyAll();
			}
		}

		public void createOrUpdateNetPointMatrixList(List<Map<String, Object>> matrixList, Map<String, Object> startPoint, List<Map<String, Object>> endPoints, int tactic, String clrCenterNo) throws Exception {
			NetPointMatrixKey netPointMatrixKey = new NetPointMatrixKey();
			netPointMatrixKey.setStartPointNo(startPoint.get("no").toString());
			netPointMatrixKey.setType(tactic);

			for (int k = 0; k < matrixList.size(); k++) {
				Integer type = null;
				if (StringUtil.objectToInt(startPoint.get("type")) == StatusEnum.MatrixPointType.NETPOINT.getValue() && StringUtil.objectToInt(endPoints.get(k).get("type")) == StatusEnum.MatrixPointType.NETPOINT.getValue()) {
					type = StatusEnum.MatrixType.N_TO_N.getValue();
				} else if (StringUtil.objectToInt(startPoint.get("type")) == StatusEnum.MatrixPointType.CENTER.getValue() && StringUtil.objectToInt(endPoints.get(k).get("type")) == StatusEnum.MatrixPointType.NETPOINT.getValue()) {
					type = StatusEnum.MatrixType.C_TO_N.getValue();
				} else if (StringUtil.objectToInt(startPoint.get("type")) == StatusEnum.MatrixPointType.NETPOINT.getValue() && StringUtil.objectToInt(endPoints.get(k).get("type")) == StatusEnum.MatrixPointType.CENTER.getValue()) {
					type = StatusEnum.MatrixType.N_TO_C.getValue();
				}

				netPointMatrixKey.setEndPointNo(endPoints.get(k).get("no").toString());
				netPointMatrixKey.setTactic(type);
				NetPointMatrix netPointMatrix = lineNetPointMatrixMapper.selectByPrimaryKey(netPointMatrixKey);

				if (netPointMatrix == null) {
					netPointMatrix = new NetPointMatrix();
					netPointMatrix.setStartPointNo(netPointMatrixKey.getStartPointNo());
					netPointMatrix.setEndPointNo(netPointMatrixKey.getEndPointNo());
					netPointMatrix.setTactic(netPointMatrixKey.getTactic());
					netPointMatrix.setType(netPointMatrixKey.getType());
					netPointMatrix.setDistance(StringUtil.objectToInt(matrixList.get(k).get("distance")));
					netPointMatrix.setTimeCost(StringUtil.objectToInt(matrixList.get(k).get("timecost")));
					netPointMatrix.setDataType(1);
					netPointMatrix.setClrCenterNo(clrCenterNo);
					lineNetPointMatrixMapper.insertSelective(netPointMatrix);
				} else {
					netPointMatrix.setDistance(StringUtil.objectToInt(matrixList.get(k).get("distance")));
					netPointMatrix.setTimeCost(StringUtil.objectToInt(matrixList.get(k).get("timecost")));
					lineNetPointMatrixMapper.updateByPrimaryKeySelective(netPointMatrix);
				}
			}
		}
	}


	/**
	 * 关联坐标类
	 */
	private class HandlerPointThread implements Runnable {

		private int pointNum;
		private List<Map<String, Object>> points;
		private List<Map<String, Object>> allPoints;
		private int tactic;
		private String mapApiAkValue;
		private Map<String, Integer> count1;

		public HandlerPointThread(int pointNum, List<Map<String, Object>> points, List<Map<String, Object>> allPoints, int tactic, String mapApiAkValue, Map<String, Integer> count1) {
			this.pointNum = pointNum;
			this.points = points;
			this.allPoints = allPoints;
			this.tactic = tactic;
			this.mapApiAkValue = mapApiAkValue;
			this.count1 = count1;
		}

		@Override
		public void run() {
			try {
				List<Map<String, Object>> startPoints = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> endPoints = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < points.size(); i++) {
					startPoints.clear();
					endPoints.clear();
					Map<String, Object> startPoint = points.get(i);
					startPoints.add(startPoint);
//                    String getLinkedListSql = "SELECT END_POINT_NO as no FROM NETPOINT_MATRIX_TABLE where TACTIC = '" + tactic + "' and START_POINT_NO = '" + startPoint.get("no").toString() + "'";
//                    CachedRowSet linkedEndPointList = DbOperate.executeQuery(getLinkedListSql, connection,log);
//                    Collection noList = linkedEndPointList.toCollection("no");
					for (int j = 0; j < pointNum; j++) {
						if (points.get(i).get("x").equals(allPoints.get(j).get("x")) && points.get(i).get("y").equals(allPoints.get(j).get("y"))) {
							//自身不与自身关联
							continue;
						}
						endPoints.add(allPoints.get(j));
						if (endPoints.size() == 25) {
							Thread.sleep(3000);
							List<Map<String, Object>> matrixList = HttpClientUtil.routematrix(startPoints, endPoints, tactic, mapApiAkValue);
							if (matrixList == null || matrixList.size() == 0) {
								endPoints.clear();
								continue;
							} else {
								putMatrixListToCache(matrixList, startPoint, endPoints);
							}
							endPoints.clear();
						}
					}

					if (endPoints.size() > 0 && endPoints.size() < 25) {
						Thread.sleep(3000);
						List<Map<String, Object>> matrixList = HttpClientUtil.routematrix(startPoints, endPoints, tactic, mapApiAkValue);
						if (matrixList == null || matrixList.size() == 0) {
							continue;
						} else {
							putMatrixListToCache(matrixList, startPoint, endPoints);
						}
					}
				}
				count();
			} catch (Exception e) {
				log.error("出错咯", e);
			}
		}

		private void count() {
			synchronized (count1) {
				log.debug("HandlerPointThreadBefore - count = " + count1.get("count1"));
				int countValue = count1.get("count1");
				countValue++;
				count1.put("count1", countValue);
				log.debug("HandlerPointThreadAfter - count = " + count1.get("count1"));
				count1.notifyAll();
			}
		}

		public void putMatrixListToCache(List<Map<String, Object>> matrixList, Map<String, Object> startPoint, List<Map<String, Object>> endPoints) {
			for (int i = 0; i < matrixList.size(); i++) {
				String key = startPoint.get("x") + "|" + startPoint.get("y") + "|" + endPoints.get(i).get("x") + "|"
						+ endPoints.get(i).get("y");
//                JSONObject data = new JSONObject();
				int[] data = new int[2];
				data[0] = StringUtil.objectToInt(matrixList.get(i).get("distance"));
				data[1] = StringUtil.objectToInt(matrixList.get(i).get("timecost"));
//                jsonObject.put("distance", matrixList.get(i).get("distance"));
//                jsonObject.put("timecost", matrixList.get(i).get("timecost"));
				matrixResult.put(key, data);
			}
		}

	}

}
