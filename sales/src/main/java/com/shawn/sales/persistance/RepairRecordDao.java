package com.shawn.sales.persistance;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shawn.sales.business.model.RepairRecord;

public interface RepairRecordDao {

	RepairRecord getById(Long entityId);

	int create(RepairRecord entity);

	int update(RepairRecord entity);
	
	int updateIfNecessary(RepairRecord entity);

	void delete(Long entityId);

	List<RepairRecord> getList(@Param("saleId")Long saleId, @Param("start")long startPosition, @Param("count")Integer count);

	Integer getListCount(@Param("saleId")Long saleId);
}
