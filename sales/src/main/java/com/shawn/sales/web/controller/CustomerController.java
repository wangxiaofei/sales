package com.shawn.sales.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shawn.sales.business.CustomerService;
import com.shawn.sales.business.dto.ResultDto;
import com.shawn.sales.business.model.Customer;
import com.shawn.sales.business.model.User;
import com.shawn.sales.common.EnumResultCode;
import com.shawn.sales.common.Page;

@Controller
@RequestMapping(value="/customer")
public class CustomerController {

	@Autowired
	private CustomerService cusService;
	
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/create")
	public @ResponseBody ResultDto<Customer> create(@RequestBody Customer customer) {
		ResultDto<Customer> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = cusService.create(customer);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}
	@RequestMapping(value = "/list")
	public @ResponseBody ResultDto<Page<Customer>> getList(HttpServletRequest request,@RequestParam(value="userId",required=false) Long userId,@RequestParam("page")Integer page,@RequestParam("count")Integer count){
		ResultDto<Page<Customer>> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = cusService.getList(null,page,count);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}
	
	@RequestMapping(value = "/list/all")
	public @ResponseBody ResultDto<List<Customer>> getAllList(HttpServletRequest request,@RequestParam(value="userId",required=false) Long userId){
		ResultDto<List<Customer>> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = cusService.getAllList(null);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}
	
	@RequestMapping(value = "/update")
	public @ResponseBody ResultDto<Customer> update(@RequestBody Customer customer){
		ResultDto<Customer> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = cusService.update(customer);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}
	@RequestMapping(value = "/delete")
	public @ResponseBody ResultDto<Customer> delete(@RequestParam(value = "id") Long id){
		ResultDto<Customer> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = cusService.delete(id);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}
	
	@RequestMapping(value = "/get")
	public @ResponseBody ResultDto<Customer> get(@RequestParam(value = "id") Long id){
		ResultDto<Customer> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		try {
			ret = cusService.get(id);
		} catch (Exception e) {
			ret.setCode(EnumResultCode.ERROR_SERVICE.getCode());
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return ret;
	}
}
