package com.shawn.sales.business.model;

import java.util.Date;
import java.util.List;

public class SaleRecord {

	private Long id;
	private String machineModel;
	private String machineNumber;
	private Date saleTime;
	private Long customerId;
	private String customerName;
	private String customerPhone;
	private String customerAddress;
	private Long userId;
	private Integer isDeleted;
	/**处理状态［－1不需处理 0未处理 1已处理］*/
	private Integer isProcess;
	private Date createTime;
	private Date updateTime;
	//用于数据展示
	private Customer cunstomer;
	private User saleUser;
	private List<RepairRecord> repairs;
	private List<VisitRecord> visits;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMachineModel() {
		return machineModel;
	}

	public void setMachineModel(String machineModel) {
		this.machineModel = machineModel;
	}

	public String getMachineNumber() {
		return machineNumber;
	}

	public void setMachineNumber(String machineNumber) {
		this.machineNumber = machineNumber;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Customer getCunstomer() {
		return cunstomer;
	}

	public void setCunstomer(Customer cunstomer) {
		this.cunstomer = cunstomer;
	}

	public User getSaleUser() {
		return saleUser;
	}

	public void setSaleUser(User saleUser) {
		this.saleUser = saleUser;
	}

	public List<RepairRecord> getRepairs() {
		return repairs;
	}

	public void setRepairs(List<RepairRecord> repairs) {
		this.repairs = repairs;
	}

	public List<VisitRecord> getVisits() {
		return visits;
	}

	public void setVisits(List<VisitRecord> visits) {
		this.visits = visits;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public Integer getIsProcess() {
		return isProcess;
	}

	public void setIsProcess(Integer isProcess) {
		this.isProcess = isProcess;
	}
}
