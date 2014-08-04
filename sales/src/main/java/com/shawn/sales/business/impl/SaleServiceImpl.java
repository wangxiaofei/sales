package com.shawn.sales.business.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shawn.sales.business.SaleService;
import com.shawn.sales.business.dto.ResultDto;
import com.shawn.sales.business.model.Customer;
import com.shawn.sales.business.model.SaleRecord;
import com.shawn.sales.business.model.User;
import com.shawn.sales.common.EnumResultCode;
import com.shawn.sales.common.Page;
import com.shawn.sales.common.PageHelper;
import com.shawn.sales.persistance.CustomerDao;
import com.shawn.sales.persistance.SaleRecordDao;
import com.shawn.sales.persistance.UserDao;

@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleRecordDao saleDao;
	@Autowired
	private CustomerDao cusDao;
	@Autowired
	private UserDao userDao;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResultDto<SaleRecord> create(SaleRecord sale) throws Exception {
		ResultDto<SaleRecord> ret = new ResultDto<SaleRecord>(EnumResultCode.SUCCESS.getCode());
		if (sale == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		sale.setCreateTime(new Date());
		saleDao.create(sale);
		ret.setData(sale);
		return ret;
	}

	@Override
	public ResultDto<Page<SaleRecord>> getList(Long userId, Integer page, Integer count) throws Exception {
		ResultDto<Page<SaleRecord>> ret = new ResultDto<Page<SaleRecord>>(EnumResultCode.SUCCESS.getCode());
		if (page == null || count == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		List<SaleRecord> list = saleDao.getList(userId, PageHelper.getStartPosition(page, count), count);
		Integer total = saleDao.getListCount(userId);
		if (list != null && !list.isEmpty()) {
			for (SaleRecord s : list) {
				Customer cus = cusDao.getById(s.getCustomerId());
				if (cus != null) {
					s.setCunstomer(cus);
				}
				User user = userDao.getById(s.getUserId());
				if (user != null) {
					s.setSaleUser(user);
				}

				Date saleTime = s.getSaleTime();
				Date now = new Date();
				SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
				Calendar date = Calendar.getInstance();
				date.setTime(now);
				date.set(Calendar.YEAR, date.get(Calendar.YEAR) - 1);
				Date processDate = dft.parse(dft.format(date.getTime()));
				if (s.getIsProcess() == null) {
					if (saleTime.before(processDate)) {
						s.setIsProcess(0);
					} else {
						s.setIsProcess(-1);
					}
				}
			}

			Page<SaleRecord> p = new Page<SaleRecord>(page, count);
			p.setList(list);
			p.setTotalResults(total);
			ret.setData(p);
		} else {
			ret.setCode(EnumResultCode.SUCCESS_NODATA.getCode());
		}
		return ret;
	}

	@Override
	public ResultDto<Page<SaleRecord>> search(Long userId, Integer page, Integer count, String keywords,String dateWords) throws Exception {
		ResultDto<Page<SaleRecord>> ret = new ResultDto<Page<SaleRecord>>(EnumResultCode.SUCCESS.getCode());
		if (page == null || count == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		List<SaleRecord> list = saleDao.searchList(userId, PageHelper.getStartPosition(page, count), count,keywords,dateWords);
		Integer total = saleDao.searchListCount(userId,keywords,dateWords);
		if (list != null && !list.isEmpty()) {
			for (SaleRecord s : list) {
				Customer cus = cusDao.getById(s.getCustomerId());
				if (cus != null) {
					s.setCunstomer(cus);
				}
				User user = userDao.getById(s.getUserId());
				if (user != null) {
					s.setSaleUser(user);
				}

				Date saleTime = s.getSaleTime();
				Date now = new Date();
				SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
				Calendar date = Calendar.getInstance();
				date.setTime(now);
				date.set(Calendar.YEAR, date.get(Calendar.YEAR) - 1);
				Date processDate = dft.parse(dft.format(date.getTime()));
				if (s.getIsProcess() == null) {
					if (saleTime.before(processDate)) {
						s.setIsProcess(0);
					} else {
						s.setIsProcess(-1);
					}
				}
			}

			Page<SaleRecord> p = new Page<SaleRecord>(page, count);
			p.setList(list);
			p.setTotalResults(total);
			ret.setData(p);
		} else {
			ret.setCode(EnumResultCode.SUCCESS_NODATA.getCode());
		}
		return ret;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResultDto<SaleRecord> update(SaleRecord sale) throws Exception {
		ResultDto<SaleRecord> ret = new ResultDto<SaleRecord>(EnumResultCode.SUCCESS.getCode());
		if (sale == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		sale.setUpdateTime(new Date());
		saleDao.updateIfNecessary(sale);
		return ret;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResultDto<SaleRecord> delete(Long id) throws Exception {
		ResultDto<SaleRecord> ret = new ResultDto<SaleRecord>(EnumResultCode.SUCCESS.getCode());
		if (id == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		SaleRecord sale = new SaleRecord();
		sale.setId(id);
		sale.setIsDeleted(1);
		saleDao.updateIfNecessary(sale);
		return ret;
	}

	@Override
	public ResultDto<SaleRecord> get(Long id) {
		ResultDto<SaleRecord> ret = new ResultDto<SaleRecord>(EnumResultCode.SUCCESS.getCode());
		if (id == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		SaleRecord sale = saleDao.getById(id);
		if (sale != null) {
			ret.setData(sale);
		} else {
			ret.setCode(EnumResultCode.SUCCESS_NODATA.getCode());
		}
		return ret;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResultDto<SaleRecord> process(Long id) {
		ResultDto<SaleRecord> ret = new ResultDto<SaleRecord>(EnumResultCode.SUCCESS.getCode());
		if (id == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		SaleRecord sale = new SaleRecord();
		sale.setId(id);
		sale.setIsProcess(1);
		saleDao.updateIfNecessary(sale);
		return ret;
	}

}
