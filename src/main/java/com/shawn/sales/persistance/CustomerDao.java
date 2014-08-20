package com.shawn.sales.persistance;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shawn.sales.business.model.Customer;

public interface CustomerDao {

	List<Customer> getList(@Param("userId") Long userId, @Param("start") long start, @Param("count") int count);

	int getListCount(@Param("userId") Long userId);

	Customer getById(Long entityId);

	int create(Customer entity);

	int update(Customer entity);

	int updateIfNecessary(Customer entity);

	void delete(Long entityId);

	List<Customer> getAll(@Param("userId")Long userId);
}
