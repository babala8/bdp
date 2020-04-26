package com.zjft.microservice.treasurybrain.tolly.util;

/**
 * @ClassName TollySystemsEnum
 * @Description 第三方系统结果集
 * @Author zhangjs
 * @CreateDate 2019/12/10 10:39
 * @Version 1.0.1
 * @modify zhangsan 2019-12-10 10:44:00 数据修正
 **/
public enum TollySystemsEnum {

	/**
	 * 0-ATM加钞路线
	 */
	RETURNCODE01("01", "网点领现实物准备出库"),
	RETURNCODE02("02", "网点领现实物出库完成"),
	RETURNCODE03("03", "网点经办人员已接到押运人员押送的实物"),
	RETURNCODE04("04", "物流已到达解现网点并已接受到实物，请开始押运 "),
	RETURNCODE05("05", "押运人员已押送实解现物到达金库"),
	RETURNCODE06("06", "解现单审核通过，请物流系统进行调运。"),
	RETURNCODE07("07", "解现实物已通过门禁并入库。"),
	RETURNCODE08("08", "仓储人员已确定领现箱出库操作，请开始出库。"),
	RETURNCODE09("09", "钞处领现已入库，请仓储系统入库。"),
	RETURNCODE10("10", "仓储领现出库已到押运人员，请仓储开始进行出库。"),
	RETURNCODE11("11", "解现箱已入库，请仓储系统入库。"),
	RETURNCODE12("12", "钞处已申请仓储解现箱出库，请仓储系统开始出库。"),
	RETURNCODE13("13", "钞处解现已入库，请仓储系统入库。"),
	RETURNCODE14("14", "仓储系统工作已完成，请运营系统记录实物货位。（领现箱）"),
	RETURNCODE15("15", "仓储系统工作已完成，请运营系统记录实物货位。（解现箱）"),
	RETURNCODE16("16", "仓储系统工作已完成，请运营系统记录实物货位。（钞处入库到仓储入货位—立体库）");

	private String code;
	private String tip;

	/**
	*@MethodName: TollySystemsEnum
	*@Description: 第三方系统结果集构造器
	*@Author: zhangjs
	*@Param: 
	*@Return: 
	*@Date: 2019/12/11 15:59
	**/
	private TollySystemsEnum(String code, String tip) {
		this.code = code;
		this.tip = tip;
	}

	/**
	*@MethodName: getCode
	*@Description: 获取code信息
	*@Author: zhangjs
	*@Param: 
	*@Return: 
	*@Date: 2019/12/11 16:52
	**/
	public String getCode() {
		return this.code;
	}

	/**
	*@MethodName: getTip
	*@Description: 获取tip信息
	*@Author: zhangjs
	*@Param:
	*@Return:
	*@Date: 2019/12/11 17:11
	**/
	public String getTip() {
		return this.tip;
	}

	/**
	*@MethodName: getTipByCode
	*@Description: 通过code获取tip信息
	*@Author: zhangjs
	*@Param:
	*@Return:
	*@Date: 2019/12/11 17:11
	**/
	public static String getTipByCode(String code) {
		for (TollySystemsEnum temp : TollySystemsEnum.values()) {
			if (temp.getCode().equals(code)) {
				return temp.getTip();
			}
		}
		return "";
	}
}

