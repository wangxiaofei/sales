package com.shawn.sales.business.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shawn.sales.business.CustomerService;
import com.shawn.sales.business.dto.ResultDto;
import com.shawn.sales.business.model.Customer;
import com.shawn.sales.common.EnumResultCode;
import com.shawn.sales.common.Page;
import com.shawn.sales.common.PageHelper;
import com.shawn.sales.persistance.CustomerDao;
import com.shawn.sales.persistance.CustomerRelationDao;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao cusDao;
	@Autowired
	private CustomerRelationDao cusRelationDao;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public ResultDto<Customer> create(Customer customer) throws Exception {
		ResultDto<Customer> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if (customer == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		customer.setCreateTime(new Date());
		cusDao.create(customer);
		ret.setData(customer);
		return ret;
	}

	@Override
	public ResultDto<Page<Customer>> getList(Long userId, Integer page, Integer count) throws Exception {
		ResultDto<Page<Customer>> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if (page == null || count == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		List<Customer> list = cusDao.getList(userId, PageHelper.getStartPosition(page, count), count);
		Integer total = cusDao.getListCount(userId);
		if (list != null && !list.isEmpty()) {
			Page<Customer> p = new Page<>(page, count);
			p.setList(list);
			p.setTotalResults(total);
			ret.setData(p);
		} else {
			ret.setCode(EnumResultCode.SUCCESS_NODATA.getCode());
		}
		return ret;
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public ResultDto<Customer> delete(Long id) throws Exception {
		ResultDto<Customer> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if (id == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		Customer c = new Customer();
		c.setId(id);
		c.setIsDeleted(1);
		cusDao.updateIfNecessary(c);
		return ret;
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public ResultDto<Customer> update(Customer customer) throws Exception {
		ResultDto<Customer> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if (customer == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		customer.setUpdateTime(new Date());
		cusDao.updateIfNecessary(customer);
		return ret;
	}

	@Override
	public ResultDto<Customer> get(Long id) {
		ResultDto<Customer> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if (id == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		Customer cus = cusDao.getById(id);
		if (cus != null) {
			ret.setData(cus);
		} else {
			ret.setCode(EnumResultCode.SUCCESS_NODATA.getCode());
		}
		return ret;
	}

	@Override
	public ResultDto<List<Customer>> getAllList(Long userId) {
		ResultDto<List<Customer>> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		List<Customer> cus = cusDao.getAll(userId);
		if (cus != null) {
			ret.setData(cus);
		} else {
			ret.setCode(EnumResultCode.SUCCESS_NODATA.getCode());
		}
		return ret;
	}

}
