package com.shawn.sales.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shawn.sales.business.UserService;
import com.shawn.sales.business.dto.ResultDto;
import com.shawn.sales.business.model.User;
import com.shawn.sales.common.EnumResultCode;
import com.shawn.sales.common.Page;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/create")
	public @ResponseBody
	ResultDto<User> create(@RequestBody User user) {
		ResultDto<User> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = userService.create(user);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/list")
	public @ResponseBody
	ResultDto<Page<User>> getList(@RequestParam("page") Integer page, @RequestParam("count") Integer count) {
		ResultDto<Page<User>> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = userService.getList(page, count);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/update")
	public @ResponseBody
	ResultDto<User> update(@RequestBody User user) {
		ResultDto<User> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = userService.update(user);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/delete")
	public @ResponseBody
	ResultDto<User> delete(@RequestParam(value = "id") Long id) {
		ResultDto<User> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = userService.delete(id);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/get")
	public @ResponseBody
	ResultDto<User> get(@RequestParam(value = "id") Long id) {
		ResultDto<User> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = userService.get(id);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/checkLoginName")
	public @ResponseBody
	ResultDto<Boolean> checkUserName(@RequestParam(value = "loginName") String loginName) {
		ResultDto<Boolean> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = userService.checkUserName(loginName);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}

	@RequestMapping(value = "/login")
	public @ResponseBody
	ResultDto<User> login(HttpServletRequest request,@RequestBody User user) {
		ResultDto<User> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = userService.login(request,user.getLoginName(),user.getPassword());
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
			
		}
		return ret;
	}
	
	@RequestMapping(value = "/logout")
	public @ResponseBody
	ResultDto<Boolean> logout(HttpServletRequest request) {
		ResultDto<Boolean> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = userService.logout(request);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}
	
	@RequestMapping(value = "/resetpwd")
	public @ResponseBody
	ResultDto<User> resetPassword(@RequestBody User user) {
		ResultDto<User> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = userService.resetPassword(user);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}
}
