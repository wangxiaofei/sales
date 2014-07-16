package com.shawn.sales.persistance;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shawn.sales.business.model.VisitRecord;

public interface VisitRecordDao {

	VisitRecord getById(Long entityId);

	int create(VisitRecord entity);

	int update(VisitRecord entity);
	
	int updateIfNecessary(VisitRecord entity);

	void delete(Long entityId);

	List<VisitRecord> getList(@Param("saleId")Long saleId, @Param("start")long startPosition, @Param("count")Integer count);

	Integer getListCount(@Param("saleId")Long saleId);
}
