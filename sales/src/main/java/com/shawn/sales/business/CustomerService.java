package com.shawn.sales.business;

import java.util.List;

import com.shawn.sales.business.dto.ResultDto;
import com.shawn.sales.business.model.Customer;
import com.shawn.sales.common.Page;

public interface CustomerService {

	ResultDto<Customer> create(Customer customer) throws Exception;

	ResultDto<Page<Customer>> getList(Long userId, Integer page, Integer count) throws Exception;

	ResultDto<Customer> delete(Long id) throws Exception;

	ResultDto<Customer> update(Customer customer) throws Exception;

	ResultDto<Customer> get(Long id);

	ResultDto<List<Customer>> getAllList(Long userId);

}
