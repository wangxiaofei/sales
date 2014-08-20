package com.shawn.sales.common;

public enum EnumResultCode {

	SUCCESS("200", "成功"), 
	SUCCESS_NODATA("201", "成功，无数据"), 
	
	ERROR_PARAM_EMPTY("301", "参数为空"), 
	ERROR_PARAM_VALUE("302", "参数值错误"), 
	ERROR_SERVICE("303", "服务异常"), 

	ERROR_USER_USER_NOTEXIST("402", "用户不存在"), 
	ERROR_USER_PASSWORD_WRONG("403", "用户密码错误"), 
	ERROR_USER_USER_EXIST("404", "用户已经存在"),
	ERROR_USER_NOTPERMISSION("405", "用户无此操作权限"), 
	ERROR_USER_RESETPWD_URL_ERROR("406", "重置密码链接不正确"), 
	ERROR_USER_EMAIL_NOTMATCH("407", "用户邮箱不匹配"),
	

	ERROR_AUTH_FAIL("701", "授权失败，您无权操作"), 
	
	
	ERROR_FILEUP_EMPTY("801", "文件为空"), ERROR_FILEUP_FAIL("802", "文件上传失败"),
	ERROR_FILEUP_FORMAT("803", "文件格式错误"), ERROR_FILEUP_MAXSIZE("804", "文件过大");

	private String code;
	private String desc;

	private EnumResultCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static EnumResultCode getValueOf(String code) {
		if (null == code || "".equals(code)) {
			return null;
		}
		EnumResultCode[] codes = EnumResultCode.values();
		for (EnumResultCode type : codes) {
			if (type != null && type.getCode().equals(code)) {
				return type;
			}
		}
		return null;
	}
}
