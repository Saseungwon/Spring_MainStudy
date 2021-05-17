package com.study.login.web;

import java.net.URLEncoder;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.common.util.CookieUtils;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.login.service.ILoginService;
import com.study.login.vo.UserVO;

@Controller
public class LoginController {
	@Inject
	ILoginService loginService;
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@GetMapping("/login/login.wow")
	public String getLogin() throws Exception {
		return "login/login";
	}
	
	@PostMapping("/login/login.wow")
	public String postLogin(HttpServletRequest req,
			HttpServletResponse resp,
			@RequestParam(value="userId", required = false)String id,
			@RequestParam(value="userPass", required = false)String pw,
			@RequestParam(value="rememberMe", required = false)String save_id,
			Model model) throws Exception {
		logger.info("id = " + id + ", pw = " + pw + ", save_id = " + save_id);
		if((id==null||id.isEmpty() )|| (pw==null||pw.isEmpty())){	
			return "redirect:/login/login.wow?msg=" + URLEncoder.encode("입력안함", "utf-8");
		}

		try {				
			UserVO user = loginService.loginCheck(id, pw);
			HttpSession session = req.getSession();
			
			if(save_id == null){
				CookieUtils cookieUtils=new CookieUtils(req);
				if(cookieUtils.exists("SAVE_ID")){
					Cookie cookie=CookieUtils.createCookie("SAVE_ID",id,"/",0);
					resp.addCookie(cookie);						
				}
				save_id="";
			}
			if(save_id.equals("Y")){
				resp.addCookie(CookieUtils.createCookie("SAVE_ID", id,"/",3600*24*7));
			}
			CookieUtils cookieUtils=new CookieUtils(req);
			if (cookieUtils.exists("SAVE_ID")) {
				session.setAttribute("checked", "checked");
				session.setAttribute("id", cookieUtils.getValue("SAVE_ID"));
			}
			session.setAttribute("USER_INFO", user);
			session.setMaxInactiveInterval(1800);
			
			return "redirect:/";
		} catch (BizNotFoundException e) {
			return "redirect:/login/login.wow?msg=" + URLEncoder.encode("아이디 또는 비번 확인","utf-8");
		} catch (BizPasswordNotMatchedException e) {
			return "redirect:/login/login.wow?msg=" + URLEncoder.encode("아이디 또는 비번 확인","utf-8");
		}
	}
	
	@RequestMapping("/login/logout.wow")
	public String getLogout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
		session.removeAttribute("USER_INFO");
		
		return "redirect:/";
	}

}
