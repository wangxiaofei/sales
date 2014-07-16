package com.shawn.sales.business.model;

import java.util.Date;

public class RepairRecord {

	private Long id;
	private String content;
	private Date repairTime;
	private Long saleId;
	private Long repairUserId;
	private Integer isDeleted;
	private Date createTime;
	private Date updateTime;
	
	private User repairUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRepairTime() {
		return repairTime;
	}

	public void setRepairTime(Date repairTime) {
		this.repairTime = repairTime;
	}

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public Long getRepairUserId() {
		return repairUserId;
	}

	public void setRepairUserId(Long repairUserId) {
		this.repairUserId = repairUserId;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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

	public User getRepairUser() {
		return repairUser;
	}

	public void setRepairUser(User repairUser) {
		this.repairUser = repairUser;
	}
}
