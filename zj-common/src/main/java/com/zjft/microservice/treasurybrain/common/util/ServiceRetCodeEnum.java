package com.zjft.microservice.treasurybrain.common.util;

public enum ServiceRetCodeEnum {
	
	SUCCEED("00", "成功"),
	
	FAIL("FF", "失败"),
	
	RESULT_EMPTY("F1", "结果为空"),
	
	PSW_EMPTY("F2", "密码为空"),
	
	SYS_BUSY("F3", "系统正忙,请稍后再试"),
	
	EXCEPTION("EE", "异常"),
	
	TIME_OUT("TT", "请求超时"),
	
	AUDIT_FLAG("99", "该操作已提交至审核记录中,请等待审核"),
	
	AUDIT_OBJECT_EXIST("98", "对象已存在,请勿重复添加!"),
	
	AUDIT_OBJECT_DELETED("97", "对象不存在!"),
	
	AUDIT_DEV_IP_EXIST("95", "该设备所设置的IP地址已经存在,不能重复"),
	
	AUDIT_OBJECT_CONSTRAINT_OK("100", "审核对象合法");
	
	private String code;
	private String tip;

	private ServiceRetCodeEnum(String code, String tip) {
		this.code = code;
		this.tip = tip;
	}

	public String getCode() {
		return this.code;
	}

	public String getTip() {
		return this.tip;
	}
	
	public static String getTipByCode(String code) {
		for (ServiceRetCodeEnum temp : ServiceRetCodeEnum.values()) {
			if (temp.getCode().equals(code)) {
				return temp.getTip();
			}
		}
		return "";
	}

}
