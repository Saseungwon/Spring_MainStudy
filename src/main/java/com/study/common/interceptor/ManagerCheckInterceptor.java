package com.study.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.study.login.vo.UserVO;

public class ManagerCheckInterceptor  extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session=request.getSession(false);
		if(session==null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);   //403, forbidden  접근금지
			return false;
		}
		//보통은 session이 null이 아닙니다
		UserVO user=(UserVO)session.getAttribute("USER_INFO");
		if(user!=null) { //로그인이 되어있죠.  매니저인지 아닌지 체그
			if (user.getUserRole().contains("MANAGER")) {
				return true;}
			else { //일반유저가 member 페이지 볼려는 경우
				response.sendError(HttpServletResponse.SC_FORBIDDEN);  
				 return false;
			}
		}
		//로그인 안하고 member페이지 접근하려는 경우
		response.sendError(HttpServletResponse.SC_FORBIDDEN);  
		return false;
	}
	
}
