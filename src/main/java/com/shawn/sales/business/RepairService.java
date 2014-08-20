package com.shawn.sales.business;

import com.shawn.sales.business.dto.ResultDto;
import com.shawn.sales.business.model.RepairRecord;
import com.shawn.sales.common.Page;

public interface RepairService {
	
	ResultDto<Page<RepairRecord>> getList(Long saleId, Integer page, Integer count) throws Exception;

	ResultDto<RepairRecord> create(RepairRecord visit);

	ResultDto<RepairRecord> delete(Long id);
}
