package com.shawn.sales.business;

import com.shawn.sales.business.dto.ResultDto;
import com.shawn.sales.business.model.VisitRecord;
import com.shawn.sales.common.Page;

public interface VisitService {

	ResultDto<Page<VisitRecord>> getList(Long saleId, Integer page, Integer count) throws Exception;
	
	ResultDto<VisitRecord> create(VisitRecord visit);
	
	ResultDto<VisitRecord> delete(Long id);
}
