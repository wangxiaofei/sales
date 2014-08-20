package com.shawn.sales.persistance;

import com.shawn.sales.business.model.CustomerRelation;

public interface CustomerRelationDao {

	CustomerRelation getById(Long entityId);

	int create(CustomerRelation entity);

	int update(CustomerRelation entity);
	
	int updateIfNecessary(CustomerRelation entity);

	void delete(Long entityId);
}
