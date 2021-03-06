/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package com.cloudo.wms.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cloudo.wms.ApplicationContext;
import com.cloudo.wms.config.Constant;
import com.cloudo.wms.lib.annotation.Role;
import com.cloudo.wms.modules.core.models.User;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		// permission checking
		User currentUser = ApplicationContext.getCurrentUser();
		if (handler instanceof HandlerMethod && currentUser != null) {
			HandlerMethod method = (HandlerMethod) handler;
			Role role = method.getMethodAnnotation(Role.class);
			// TODO: check permission for API
			if (role != null) {

			}
		}

		// pass pre-flight requests in Cross-origin in AJAX
		if (!request.getMethod().equalsIgnoreCase("OPTIONS")) {
			String uri = request.getRequestURI();
			logger.info("Request {}", uri);
			Object user = request.getSession().getAttribute(Constant.SESSION_USER_KEY);
			if (user == null) {
				return false;
			}
		}
		return true;
	}

}
