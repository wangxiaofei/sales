package com.shawn.sales.business.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shawn.sales.business.UserService;
import com.shawn.sales.business.dto.ResultDto;
import com.shawn.sales.business.model.User;
import com.shawn.sales.common.EnumResultCode;
import com.shawn.sales.common.Page;
import com.shawn.sales.common.PageHelper;
import com.shawn.sales.persistance.UserDao;

@Service
public class UserServiceImpl implements UserService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResultDto<User> create(User user) throws Exception {
		ResultDto<User> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if (user == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		logger.debug("user.json=" + JSONObject.fromObject(user).toString());
		Date now = new Date();
		user.setCreateTime(now);
		int rs = userDao.create(user);
		logger.debug("insert result=" + rs);
		ret.setData(user);
		return ret;
	}

	@Override
	public ResultDto<Page<User>> getList(Integer pageIndex, Integer pageSize) throws Exception {
		ResultDto<Page<User>> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if (pageIndex == null || pageSize == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		List<User> list = userDao.getList(PageHelper.getStartPosition(pageIndex, pageSize), pageSize);
		Integer totalCount = userDao.getListCount();
		if (list != null && !list.isEmpty()) {
			Page<User> page = new Page<>(pageIndex,pageSize);
			page.setList(list);
			page.setTotalResults(totalCount);
			ret.setData(page);
		} else {
			ret.setCode(EnumResultCode.SUCCESS_NODATA.getCode());
		}
		return ret;
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public ResultDto<User> update(User user) throws Exception {
		ResultDto<User> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if (user == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		user.setUpdateTime(new Date());
		userDao.updateIfNecessary(user);
		return ret;
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public ResultDto<User> delete(Long id) throws Exception {
		ResultDto<User> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if (id == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		User user = new User();
		user.setId(id);
		user.setIsDeleted(1);
		user.setUpdateTime(new Date());
		userDao.updateIfNecessary(user);
		return ret;
	}

	@Override
	public ResultDto<User> get(Long id) {
		ResultDto<User> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if (id == null) {
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		User user = userDao.getById(id);
		if(user != null){
			ret.setData(user);
		}else{
			ret.setCode(EnumResultCode.SUCCESS_NODATA.getCode());
		}		
		return ret;
	}

	@Override
	public ResultDto<Boolean> checkUserName(String loginName) {
		ResultDto<Boolean> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if(StringUtils.isEmpty(loginName)){
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		Integer count = userDao.getCountByLoginName(loginName);
		if(count != null && count > 0){
			ret.setData(false);
		}else{
			ret.setData(true);
		}
		return ret;
	}

	@Override
	public ResultDto<User> login(HttpServletRequest request, String loginName, String password) throws Exception {
		ResultDto<User> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)){
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		User user = userDao.getByLoginName(loginName);
		if(user != null){
			if(!user.getPassword().equals(password)){
				ret.setCode(EnumResultCode.ERROR_USER_PASSWORD_WRONG.getCode());
				return ret;
			}else{
				ret.setData(user);				
				request.getSession(true).setAttribute("loginUser", user);
			}
		}else{
			ret.setCode(EnumResultCode.ERROR_USER_USER_NOTEXIST.getCode());
		}
		return ret;
	}

	@Override
	public ResultDto<Boolean> logout(HttpServletRequest request) {
		ResultDto<Boolean> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		HttpSession session = request.getSession(false);
		if(session != null){
			session.invalidate();
		}
		return ret;
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public ResultDto<User> resetPassword(User user) {
		ResultDto<User> ret = new ResultDto<>(EnumResultCode.SUCCESS.getCode());
		if(user == null || user.getId()==null ||  user.getPassword() == null || user.getNewPassword() == null){
			ret.setCode(EnumResultCode.ERROR_PARAM_EMPTY.getCode());
			return ret;
		}
		User userModel = userDao.getById(user.getId());
		if(!user.getPassword().equals(userModel.getPassword())){
			ret.setCode(EnumResultCode.ERROR_USER_PASSWORD_WRONG.getCode());
			return ret;
		}
		User user_save = new User();
		user_save.setId(user.getId());
		user_save.setPassword(user.getNewPassword());
		userDao.updateIfNecessary(user_save);
		return ret;
	}

}
