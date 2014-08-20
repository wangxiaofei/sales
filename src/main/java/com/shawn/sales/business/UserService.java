package com.shawn.sales.business;

import javax.servlet.http.HttpServletRequest;

import com.shawn.sales.business.dto.ResultDto;
import com.shawn.sales.business.model.User;
import com.shawn.sales.common.Page;

public interface UserService {

	ResultDto<User> create(User user) throws Exception;

	ResultDto<Page<User>> getList(Integer pageIndex, Integer pageSize) throws Exception;

	ResultDto<User> update(User user) throws Exception;

	ResultDto<User> delete(Long id) throws Exception;

	ResultDto<User> get(Long id) throws Exception;

	ResultDto<Boolean> checkUserName(String loginName) throws Exception;

	ResultDto<User> login(HttpServletRequest request, String loginName, String password) throws Exception;

	ResultDto<Boolean> logout(HttpServletRequest request);

	ResultDto<User> resetPassword(User user);
}
