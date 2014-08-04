package com.shawn.sales.business.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shawn.sales.business.VisitService;
import com.shawn.sales.business.dto.ResultDto;
import com.shawn.sales.business.model.VisitRecord;
import com.shawn.sales.common.EnumResultCode;
import com.shawn.sales.common.Page;
import com.shawn.sales.common.PageHelper;
import com.shawn.sales.persistance.VisitRecordDao;

@Service
public class VisitServiceImpl implements VisitService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private VisitRecordDao visitDao;

	@Override
	public ResultDto<Page<VisitRecord>> getList(Long saleId, Integer page, Integer count) throws Exception {
		ResultDto<Page<VisitRecord>> ret = new ResultDto<Page<VisitRecord>>(EnumResultCode.SUCCESS.getCode());
		if (page == null || count == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		List<VisitRecord> list = visitDao.getList(saleId, PageHelper.getStartPosition(page, count), count);
		Integer total = visitDao.getListCount(saleId);
		if (list != null && !list.isEmpty()) {
			Page<VisitRecord> p = new Page<VisitRecord>(page, count);
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
	public ResultDto<VisitRecord> create(VisitRecord visit) {
		ResultDto<VisitRecord> ret = new ResultDto<VisitRecord>(EnumResultCode.SUCCESS.getCode());
		if (visit == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		visit.setCreateTime(new Date());
		visitDao.create(visit);
		return ret;
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public ResultDto<VisitRecord> delete(Long id) {
		ResultDto<VisitRecord> ret = new ResultDto<VisitRecord>(EnumResultCode.SUCCESS.getCode());
		if (id == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		VisitRecord  visit = new VisitRecord();
		visit.setId(id);
		visit.setIsDeleted(1);
		visitDao.updateIfNecessary(visit);
		return ret;
	}
}
