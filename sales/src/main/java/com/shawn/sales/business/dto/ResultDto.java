/**
 * Id
 */
package com.shawn.sales.business.dto;

import java.io.Serializable;

import com.shawn.sales.common.EnumResultCode;

/**
 * 返回带有状态信息的对象
 * 
 * @author Author
 * @version Revision Date
 */
public class ResultDto<T> implements Serializable {

	private static final long serialVersionUID = -3059930318831708557L;

	private String code;

	private String info;

	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ResultDto(String code) {
		this.info = EnumResultCode.getValueOf(code).getDesc();
		this.code = code;
	}

	public ResultDto(String code, T data) {
		this.info = EnumResultCode.getValueOf(code).getDesc();
		this.code = code;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.setInfo(EnumResultCode.getValueOf(code).getDesc());
		this.code = code;
	}

	@Override
	public String toString() {
		return "ResultDto [data=" + data + ", code=" + code + ",info=" + info + "]";
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
