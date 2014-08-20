package com.shawn.sales.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shawn.sales.business.model.User;

public class LoginHandler implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private final String LOGIN = "/index.html";
	private final String SESSION_KEY_USER = "loginUser";

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		User user = (User) request.getSession().getAttribute(SESSION_KEY_USER);
		if (null == user) {
			logger.debug("loginUser is NULL...");
			response.sendRedirect(LOGIN);
			return false;
		}
		return true;
	}

}