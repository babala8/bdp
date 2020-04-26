package com.zjft.microservice.treasurybrain.datainsight.web;

import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.datainsight.service.GisBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 张笑
 */
@Slf4j
@RestController
public class GisBusinessResourceImpl implements GisBusinessResource {

	private final GisBusinessService gisBusinessService;

	@Autowired
	public GisBusinessResourceImpl(GisBusinessService gisBusinessService) {
		this.gisBusinessService = gisBusinessService;
	}

	@Override
	public ListDTO qryPointInMap() {
		// 需求：在地图中获取网点
		// 思路：调用service层的qryPointInMap()方法，获取包含网点信息的列表
		// 输出：ListDTO对象 输出从数据库中查询到的网点信息
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL);
		try {
			//调用service层方法获取地图中网点
			dto = gisBusinessService.qryPointInMap();
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public ListDTO qryPointsByCity(String cityName) {
		// 需求：根据城市名称查询网点列表
		// 思路：调用service层的qryOrgByCity()方法，根据城市名称获取该城市中的网点列表
		// 输入：城市名称，不能为空
		// 输出：ListDTO对象 输出从数据库中查询到的该城市下的网点信息
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL);
		try {
			//判断城市名称是否为空或者为空格或者包含空格
			if ("".equals(cityName) || cityName.contains(" ")) {
				return dto;
			}
			//调用service层方法，返回查询结果
			ListDTO listDTO = gisBusinessService.qryPointsByCityName(cityName);

			//如果查询结果为空
			if (listDTO.getRetList().isEmpty()) {
				dto.setRetCode(RetCodeEnum.RESULT_EMPTY.getCode());
				dto.setRetMsg(RetCodeEnum.RESULT_EMPTY.getTip());
				return dto;
			}
			dto.setRetList(listDTO.getRetList());
			dto.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setRetException("查询失败");
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public ListDTO<List> qryCaseListByAtm(String atmCode) {
		ListDTO<List> dto = new ListDTO<List>(RetCodeEnum.FAIL.getCode(), "查询失败！");
		try {
			String jsonString =
					"[{\"transDate\":\"2017-11-18\", \"transTotal\":\"239\"}," +
							"{\"transDate\":\"2017-11-19\", \"transTotal\":\"202\"}," +
							"{\"transDate\":\"2017-11-20\", \"transTotal\":\"203\"}," +
							"{\"transDate\":\"2017-11-21\", \"transTotal\":\"193\"}," +
							"{\"transDate\":\"2017-11-22\", \"transTotal\":\"218\"}," +
							"{\"transDate\":\"2017-11-23\", \"transTotal\":\"202\"}," +
							"{\"transDate\":\"2017-11-24\", \"transTotal\":\"211\"}," +
							"{\"transDate\":\"2017-11-25\", \"transTotal\":\"193\"}," +
							"{\"transDate\":\"2017-11-26\", \"transTotal\":\"195\"}," +
							"{\"transDate\":\"2017-12-01\", \"transTotal\":\"188\"}]";
			dto.setRetMsg("成功");
			dto.setRetCode("00");
			dto.setRetList((List) JSONUtil.parseJSONArray(jsonString));
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setRetException("查询失败");
		}
		return dto;
	}


	@Override
	public ListDTO<List> qryCaseTypesByAtm(String atmCode) {
		ListDTO<List> dto = new ListDTO<List>(RetCodeEnum.FAIL.getCode(), "查询失败！");
		try {
			String jsonString =
					"[{\"name\":\"钞空\",           \"value\":\"10\"}," +
							"{\"name\":\"钞箱故障\",       \"value\":\"19\"}," +
							"{\"name\":\"纸少\",           \"value\":\"7\"}," +
							"{\"name\":\"钞少\",           \"value\":\"30\"}," +
							"{\"name\":\"通讯故障\",       \"value\":\"301\"}," +
							"{\"name\":\"严重硬件故障\",   \"value\":\"10\"}," +
							"{\"name\":\"纸空\",           \"value\":\"14\"}," +
							"{\"name\":\"一般硬件故障\",   \"value\":\"18\"}," +
							"{\"name\":\"营业故障\",       \"value\":\"3\"}]";
			dto.setRetMsg("成功");
			dto.setRetCode("00");
			dto.setRetList((List) JSONUtil.parseJSONArray(jsonString));
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setRetException("查询失败");
		}
		return dto;
	}

	@Override
	public ListDTO<List> qryDevsInfoByOrgNo(String atmCode) {
		ListDTO<List> dto = new ListDTO<List>(RetCodeEnum.FAIL.getCode(), "查询失败！");
		try {
			String jsonString =
					"[{\"devNo\":\"000001\",\"address\":\"丁兰街道办\",   \"devCatalogName\":\"CRS\",\"devTypeName\":\"HT2845V\",\"devVendor\":\"10001\",\"caseCatalogList\":[{\"catalogName\":\"钞空\",\"caseTotal\":\"5\"},{\"catalogName\":\"钞箱故障\",\"caseTotal\":\"6\"}],\"transHisList\":[{\"transDate\":\"2018-01-21\",\"transTotal\":\"10\"},{\"transDate\":\"2018-01-22\",\"transTotal\":\"12\"}]}," +
							"{\"devNo\":\"000002\",\"address\":\"丁桥勤丰路自助\",\"devCatalogName\":\"CRS\",\"devTypeName\":\"HT2845V\",\"devVendor\":\"10001\",\"caseCatalogList\":[{\"catalogName\":\"钞空\",\"caseTotal\":\"5\"},{\"catalogName\":\"钞箱故障\",\"caseTotal\":\"6\"}],\"transHisList\":[{\"transDate\":\"2018-01-21\",\"transTotal\":\"10\"},{\"transDate\":\"2018-01-22\",\"transTotal\":\"12\"}]}," +
							"{\"devNo\":\"000003\",\"address\":\"丁桥支行\",    \"devCatalogName\":\"ATM\",\"devTypeName\":\"6625\",\"devVendor\":\"10002\",\"caseCatalogList\":[{\"catalogName\":\"钞空\",\"caseTotal\":\"5\"},{\"catalogName\":\"钞箱故障\",   \"caseTotal\":\"6\"}],\"transHisList\":[{\"transDate\":\"2018-01-21\",\"transTotal\":\"10\"},{\"transDate\":\"2018-01-22\",\"transTotal\":\"12\"}]}]";

			dto.setRetMsg("成功");
			dto.setRetCode("00");
			dto.setRetList((List) JSONUtil.parseJSONArray(jsonString));
		} catch (Exception e) {
			log.error("查询失败", e);
			dto.setRetException("查询失败");
		}
		return dto;
	}


	@Override
	public ListDTO qryRanking(Map<String, Object> paramMap) {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL.getCode(), "查询排名失败");
		try {
			String index = StringUtil.parseString(paramMap.get("index"));
			String subject = StringUtil.parseString(paramMap.get("subject"));
			String jsonString ="";
			if("devBenefit".equals(subject)){
				if("1".equals(index)){
					jsonString =
							"[{\"devNo\":\"000001\",\"orgName\":\"0001\",\"sortNo\":\"1\",\"score\":\"89.87\"}," +
									"{\"devNo\":\"000002\",\"orgName\":\"0002\",\"sortNo\":\"2\",\"score\":\"80.56\"}," +
									"{\"devNo\":\"000003\",\"orgName\":\"0003\",\"sortNo\":\"3\",\"score\":\"70.98\"},"+
									"{\"devNo\":\"000004\",\"orgName\":\"0004\",\"sortNo\":\"4\",\"score\":\"68.62\"},"+
									"{\"devNo\":\"000005\",\"orgName\":\"0005\",\"sortNo\":\"5\",\"score\":\"60.32\"}]";

				}else{
					jsonString =
							"[{\"devNo\":\"000006\",\"orgName\":\"0001\",\"sortNo\":\"1\",\"score\":\"40.32\"}," +
									"{\"devNo\":\"000007\",\"orgName\":\"0002\",\"sortNo\":\"2\",\"score\":\"48.62\"}," +
									"{\"devNo\":\"000008\",\"orgName\":\"0003\",\"sortNo\":\"3\",\"score\":\"50.98\"},"+
									"{\"devNo\":\"000009\",\"orgName\":\"0004\",\"sortNo\":\"4\",\"score\":\"53.56\"},"+
									"{\"devNo\":\"000010\",\"orgName\":\"0005\",\"sortNo\":\"5\",\"score\":\"55.87\"}]";
				}
			}else if("orgServiceQuality".equals(subject)){
				if("1".equals(index)){
					jsonString =
							"[{\"devNo\":\"A000001\",\"orgName\":\"0001\",\"sortNo\":\"1\",\"score\":\"89.87\"}," +
									"{\"devNo\":\"A000002\",\"orgName\":\"0002\",\"sortNo\":\"2\",\"score\":\"80.56\"}," +
									"{\"devNo\":\"A000003\",\"orgName\":\"0003\",\"sortNo\":\"3\",\"score\":\"70.98\"},"+
									"{\"devNo\":\"A000004\",\"orgName\":\"0004\",\"sortNo\":\"4\",\"score\":\"68.62\"},"+
									"{\"devNo\":\"A000005\",\"orgName\":\"0005\",\"sortNo\":\"5\",\"score\":\"60.32\"}]";

				}else{
					jsonString =
							"[{\"devNo\":\"A000001\",\"orgName\":\"0001\",\"sortNo\":\"1\",\"score\":\"9.87\"}," +
									"{\"devNo\":\"A000002\",\"orgName\":\"0002\",\"sortNo\":\"2\",\"score\":\"10.56\"}," +
									"{\"devNo\":\"A000003\",\"orgName\":\"0003\",\"sortNo\":\"3\",\"score\":\"17.98\"},"+
									"{\"devNo\":\"A000004\",\"orgName\":\"0004\",\"sortNo\":\"4\",\"score\":\"18.62\"},"+
									"{\"devNo\":\"A000005\",\"orgName\":\"0005\",\"sortNo\":\"5\",\"score\":\"20.32\"}]";
				}
			}


			dto.setRetMsg("成功");
			dto.setRetCode("00");
			dto.setRetList((List) JSONUtil.parseJSONArray(jsonString));
		} catch (Exception e) {
			log.error("查找失败", e);
			dto.setRetException("查找失败");
		}
		return dto;
	}

}
