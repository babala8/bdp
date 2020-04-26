package com.zjft.microservice.treasurybrain.productcenter.web_inner;


import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ServiceInnerResource {

	Integer qryNextByOperateType(String operateType);

	String qryNameByOperateType(String operateType);

	int qryExist(Map<String, Object> Map);

	Integer isStatusConvertMatch(Integer taskType,Integer curStatus, String operateType);

	/**
	 *当你确定上一个转换状态只有一个的时候用这个
	 */
	String mayStatusConvert(Integer nextStatus, Integer serviceNo);

	/**
	 *否则就用这个
	 */
	List<String> mayStatusConverts(String operateType, Integer serviceNo);

	String getJSONTemplate(int serviceNo,String operateType);

	List<String> selectCurStatus(Integer taskType,String operateType);

	/**
	 * 判断任务单是否可以进行operateType操作
	 * @param taskNo 任务单编号
	 * @param operateType 操作类型
	 * @param set 跳过的操作
	 * @return
	 */
	boolean isConvert(String taskNo, String operateType, Set set);

//	/**
//	 * 判断任务单是否可以进行operateType操作
//	 * @param taskNo 任务单编号
//	 * @param operateType 操作类型
//	 * @return  0-正常转换  1-中间有弱节点可转换   -1-不可转换
//	 */
//	int isStatusConvert(String taskNo, String operateType);
}
