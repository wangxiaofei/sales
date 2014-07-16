package com.shawn.sales.business.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shawn.sales.business.RepairService;
import com.shawn.sales.business.dto.ResultDto;
import com.shawn.sales.business.model.RepairRecord;
import com.shawn.sales.business.model.User;
import com.shawn.sales.common.EnumResultCode;
import com.shawn.sales.common.Page;
import com.shawn.sales.common.PageHelper;
import com.shawn.sales.persistance.RepairRecordDao;
import com.shawn.sales.persistance.UserDao;
@Service
public class RepairServiceImpl implements RepairService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RepairRecordDao repairDao;
	@Autowired
	private UserDao userDao;
	
	@Override
	public ResultDto<Page<RepairRecord>> getList(Long saleId, Integer page, Integer count) throws Exception {
		ResultDto<Page<RepairRecord>> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if(page == null || count == null){
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		List<RepairRecord>list = repairDao.getList(saleId,PageHelper.getStartPosition(page, count),count);
		Integer total = repairDao.getListCount(saleId);
		if(list != null && !list.isEmpty()){
			for(RepairRecord r : list){
				User user = userDao.getById(r.getRepairUserId());
				r.setRepairUser(user);
			}
			Page<RepairRecord> p = new Page<>(page,count);
			p.setList(list);
			p.setTotalResults(total);
			ret.setData(p);
		}else{
			ret.setCode(EnumResultCode.SUCCESS_NODATA.getCode());
		}
		return ret;
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public ResultDto<RepairRecord> create(RepairRecord repair) {
		ResultDto<RepairRecord> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if(repair == null){
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		
		repair.setCreateTime(new Date());
		repairDao.create(repair);
		return ret;
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public ResultDto<RepairRecord> delete(Long id) {
		ResultDto<RepairRecord> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if(id == null){
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		RepairRecord repair = new RepairRecord();
		repair.setId(id);
		repair.setIsDeleted(1);
		repairDao.updateIfNecessary(repair);
		return ret;
	}
}
