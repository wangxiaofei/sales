package com.shawn.sales.business;

import com.shawn.sales.business.dto.ResultDto;
import com.shawn.sales.business.model.SaleRecord;
import com.shawn.sales.common.Page;

public interface SaleService {

	ResultDto<SaleRecord> create(SaleRecord sale) throws Exception;

	ResultDto<Page<SaleRecord>> getList(Long userId, Integer page, Integer count) throws Exception;

	ResultDto<SaleRecord> update(SaleRecord sale) throws Exception;

	ResultDto<SaleRecord> delete(Long id) throws Exception;

	ResultDto<SaleRecord> get(Long id) throws Exception;

	ResultDto<SaleRecord> process(Long id) throws Exception;

	ResultDto<Page<SaleRecord>> search(Long userId, Integer page, Integer count, String keywords, String dateWords) throws Exception;

}
