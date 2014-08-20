package com.shawn.sales.business;

import java.util.Date;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shawn.sales.business.dto.ResultDto;
import com.shawn.sales.business.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:app-all.xml")
public class UserServiceTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserService userService;
	
	@Test
	public void test(){
		User user = new User();
		user.setCreateTime(new Date());
		user.setEmail("w@x.com");
		user.setLoginName("testLoginName");
		user.setPassword("testPassword");
		user.setRole(1);
		user.setUpdateTime(new Date());
		user.setUserName("testUserName");
		ResultDto<User> ret = null;
		try {
			ret = userService.create(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(JSONObject.fromObject(user).get("createTime"));
		System.out.println("ret="+JSONObject.fromObject(ret).toString());
	}
}
