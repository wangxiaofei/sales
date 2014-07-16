package com.shawn.sales.persistance;

import com.shawn.sales.business.model.GiftRecord;

public interface GiftRecordDao {

	GiftRecord getById(Long entityId);

	int create(GiftRecord entity);

	int update(GiftRecord entity);
	
	int updateIfNecessary(GiftRecord entity);

	void delete(Long entityId);
}
