package com.shawn.sales.persistance;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shawn.sales.business.model.SaleRecord;

public interface SaleRecordDao {

	SaleRecord getById(Long entityId);

	int create(SaleRecord entity);

	int update(SaleRecord entity);

	int updateIfNecessary(SaleRecord entity);

	void delete(Long entityId);

	List<SaleRecord> getList(@Param("userId") Long userId, @Param("start") long start, @Param("count") Integer count);

	int getListCount(@Param("userId") Long userId);

	List<SaleRecord> getNeedUpdateList();

	List<SaleRecord> searchList(@Param("userId") Long userId, @Param("start") long startPosition, @Param("count") Integer count, @Param("keywords") String keywords, @Param("dateWords")String dateWords);

	Integer searchListCount(@Param("userId") Long userId, @Param("keywords") String keywords,@Param("dateWords")String dateWords);
}
