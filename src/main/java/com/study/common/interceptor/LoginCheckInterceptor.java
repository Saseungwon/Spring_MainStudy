package com.study.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		// ajax로 요청했는지 아닌지
		String XRequested = request.getHeader("X-Requested-With");
		if (session == null) {
			if (XRequested == null) {
				// 403, forbidden 접근금지
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return false;
			} else {
				// 401, 인증 X
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return false;
			}
		}
		
		if (session.getAttribute("USER_INFO") == null) {
			if (XRequested == null) {
				response.sendRedirect(request.getContextPath() + "/login/login.wow");
				return false;
			} else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return false;
			}
		}
		return true;
	}
}
