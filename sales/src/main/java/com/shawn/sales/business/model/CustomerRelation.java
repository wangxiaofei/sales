package com.shawn.sales.business.model;

import java.util.Date;

public class CustomerRelation {

	private Long id;
	private Long customerAId;
	private Long customerBId;
	private Integer relation;
	private Date createTime;
	private Date updateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCustomerAId() {
		return customerAId;
	}
	public void setCustomerAId(Long customerAId) {
		this.customerAId = customerAId;
	}
	public Long getCustomerBId() {
		return customerBId;
	}
	public void setCustomerBId(Long customerBId) {
		this.customerBId = customerBId;
	}
	public Integer getRelation() {
		return relation;
	}
	public void setRelation(Integer relation) {
		this.relation = relation;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
