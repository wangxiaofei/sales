package com.shawn.sales.persistance;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shawn.sales.business.model.User;

public interface UserDao {

	User getById(Long entityId);

	int create(User entity);

	int update(User entity);

	int updateIfNecessary(User entity);

	void delete(Long entityId);

	List<User> getList(@Param("start") long start, @Param("count") Integer count);

	Integer getListCount();

	Integer getCountByLoginName(@Param("loginName") String loginName);

	User getByLoginName(String loginName);

	List<User> getAllAdmin();
}
