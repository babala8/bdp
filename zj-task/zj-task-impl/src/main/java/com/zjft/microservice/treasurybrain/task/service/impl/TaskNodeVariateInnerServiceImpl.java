package com.zjft.microservice.treasurybrain.task.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ServiceInnerResource;
import com.zjft.microservice.treasurybrain.task.dto.TaskNodeVariateDTO;
import com.zjft.microservice.treasurybrain.task.repository.TaskNodeVariateMapper;
import com.zjft.microservice.treasurybrain.task.service.TaskNodeVariateInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 韩通
 * @since 2020-01-07
 */
@Slf4j
@Service
public class TaskNodeVariateInnerServiceImpl implements TaskNodeVariateInnerService {
	@Resource
	private TaskNodeVariateMapper taskNodeVariateMapper;

	@Resource
	private ServiceInnerResource serviceInnerResource;

	@Override
	public int insertBatch(List<TaskNodeVariateDTO> list) {
		return taskNodeVariateMapper.insertBatch(list);
	}

	@Override
	public int insert(TaskNodeVariateDTO taskNodeVariatePO) {
		return taskNodeVariateMapper.insert(taskNodeVariatePO);
	}

	@Override
	public void getJSONToInsert(String taskNodeNo, int taskType, String operateType, Object obj) {
		//记录创建申请单过程中的变量
		String s = serviceInnerResource.getJSONTemplate(taskType,operateType);

		JSONObject jsonObject = JSONObject.parseObject(s);
		JSONObject o = (JSONObject) JSONObject.toJSON(obj);
		Map map = JSONToMap(jsonObject, o);
		String s1 = JSONObject.toJSONString(map);

		TaskNodeVariateDTO taskNodeVariateDTO = new TaskNodeVariateDTO();
		taskNodeVariateDTO.setTaskNodeNo(taskNodeNo);
		taskNodeVariateDTO.setName("nodeDetail");
		taskNodeVariateDTO.setValue(s1);


		if ( taskNodeVariateMapper.insert(taskNodeVariateDTO) <= 0 ){
			log.error("====== 记录任务节点变量信息失败 ======");
		}else {
			log.info("====== 记录任务节点变量信息成功 ======");
		}
	}

	@Override
	public void getJSONToInsertBatch(List<Map<String, Object>> list) {
		for (Map<String, Object> params: list) {
			String taskNodeNo = StringUtil.parseString(params.get("taskNodeNo"));
			int taskType = StringUtil.objectToInt(params.get("taskType"));
			String operateType = StringUtil.parseString(params.get("operateType"));
			Object object = params.get("object");

			//记录创建申请单过程中的变量
			String s = serviceInnerResource.getJSONTemplate(taskType,operateType);

			JSONObject jsonObject = JSONObject.parseObject(s);
			JSONObject o = (JSONObject) JSONObject.toJSON(object);
			Map map = JSONToMap(jsonObject, o);
			String s1 = JSONObject.toJSONString(map);

			TaskNodeVariateDTO taskNodeVariateDTO = new TaskNodeVariateDTO();
			taskNodeVariateDTO.setTaskNodeNo(taskNodeNo);
			taskNodeVariateDTO.setName("nodeDetail");
			taskNodeVariateDTO.setValue(s1);


			if ( taskNodeVariateMapper.insert(taskNodeVariateDTO) <= 0 ){
				log.error("====== 记录任务节点变量信息失败 ======");
			}else {
				log.info("====== 记录任务节点变量信息成功 ======");
			}
		}
	}


	/**
	 * JSON模板与JSON串比较，只要包含JSON模板中的字段都保存下来
	 * @param jObject JSON模板
	 * @param object JSON串
	 * @return 与JSON模板相同key值的Map
	 */
	private static Map JSONToMap(JSONObject jObject,Object object) {
		Map map = new HashMap<String, Object>();

		//遍历JSON串参数
		if (object instanceof JSONObject) {
			for (String key : ((JSONObject) object).keySet()) {
				for (String jkey : jObject.keySet()) {
					Object o1 = ((JSONObject) object).get(key);
					//如果是对象
					if(o1 instanceof JSONObject){
						Map map1 = JSONToMap(jObject, o1);
						if(map1.size() != 0){
							map.put(key,map1);
						}
					}
					//如果是数组
					else if(o1 instanceof JSONArray){
						Map map2 = new HashMap<Integer, Object>();
						for (int i = 0; i < ((JSONArray) o1).size(); i++) {
							//区分List<String> 和List<DTO>
							Object o = ((JSONArray) o1).get(i);
							if( o instanceof JSONObject){
								JSONObject jsonObject = ((JSONArray) o1).getJSONObject(i);
								Map map1 = JSONToMap(jObject, jsonObject);
								if(map1.size() != 0){
									map2.put(i,map1);
								}
							}else {
								//如果key值相等，记录
								if(key.equals(jkey)){
									map.put(jObject.get(key),((JSONObject) object).get(key));
								}
							}
						}
						if(map2.size() != 0){
							map.put(key,map2);
						}

					}
					//如果都不是
					else{
						//如果key值相等，记录
						if(key.equals(jkey)){
							map.put(jObject.get(key),((JSONObject) object).get(key));
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * JSON模板与JSON串比较，相同key时取JSON模板的value为key，取JSON串的value为value 放入一个Map中
	 * @param jObject JSON模板
	 * @param object JSON串
	 * @return 相同key值的两个value组成的Map
	 */
	private static Map JSONToMap2(Object jObject,Object object){
		Map map = new HashMap<String,Object>();
		//遍历模板
		if (jObject instanceof JSONObject) {
			for (String key :((JSONObject) jObject).keySet()) {
				//判断请求参数中是否包含该key值
				if (((JSONObject) object).containsKey(key)) {
					//判断该key的value值是否是JSONOBJECT
					if (((JSONObject) jObject).get(key) instanceof JSONObject) {
						//递归
						Map map1 = null;
						if(((JSONObject) object).getJSONObject(key).size() != 0){
							map1 = JSONToMap2(((JSONObject) jObject).getJSONObject(key), ((JSONObject) object).getJSONObject(key));
						}

						if(map1.size() != 0){
							map.put(((JSONObject) jObject).get(key),map1);
						}
					}
					//判断该key的value值是否是JSONARRAY
					else if (((JSONObject) jObject).get(key) instanceof JSONArray) {
						JSONArray o1 =  ((JSONObject) jObject).getJSONArray(key);
						JSONArray o2 =  ((JSONObject) object).getJSONArray(key);
						Map map1 = null;
						if(o2.size() != 0){
							map1 = JSONToMap2(o1, o2);
						}
						if(map1.size() != 0){
							map.put(key,map1);
						}

					}
					//如果不是对象或是数组直接加入map中
					else {
						map.put(((JSONObject) jObject).get(key), ((JSONObject) object).get(key));
					}
				}

			}

		}

		//遍历模板
		if(jObject instanceof JSONArray) {
			if(((JSONArray) jObject).size() > 0){
				Map objMap = new HashMap<Integer,Object>();
				for(int i = 0;i<((JSONArray) jObject).size();i++){
					JSONObject obj1 = ((JSONArray) jObject).getJSONObject(i);
					for (int j = 0; j < ((JSONArray) object).size(); j++) {
						JSONObject obj2 = ((JSONArray) object).getJSONObject(j);
						Map map1 = null;
						if(obj2.size() != 0){
							map1 = JSONToMap2(obj1, obj2);
						}
						if(map1.size() != 0){
							objMap.put(j,map1);
						}
					}
					if(objMap.size() != 0){
						map.put(i,objMap);
					}
				}
			}
		}
		return map;
	}
}
