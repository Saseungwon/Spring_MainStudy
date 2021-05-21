package com.study.login.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.common.util.CookieUtils;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.login.service.ILoginService;
import com.study.login.service.LoginServiceImpl;
import com.study.login.vo.UserVO;

@Controller
public class LoginController {
	@Inject
	ILoginService loginService;
	
	@GetMapping("/login/login.wow")
	public String loginGet() {
		return "login/login";
	}

	@PostMapping("/login/login.wow")
	public String loginPost(@RequestParam(value="userId",required = false) String id,@RequestParam(value = "userPass",required = false) String pw,@RequestParam(value="rememberMe",required = false) String save_id
							,HttpServletRequest req
							,HttpServletResponse resp) throws IOException {
		if (save_id == null) {
			CookieUtils cookieUtils = new CookieUtils(req);
			if (cookieUtils.exists("SAVE_ID")) {
				Cookie cookie = CookieUtils.createCookie("SAVE_ID", id, "/", 0);
				resp.addCookie(cookie);
			}
			save_id = "";
		}
		if (save_id.equals("Y")) {
			resp.addCookie(CookieUtils.createCookie("SAVE_ID", id, "/", 3600 * 24 * 7));
		}

		if ((id == null || id.isEmpty()) || (pw == null || pw.isEmpty())) {
			// redirectPage="03login.jsp?msg="+URLEncoder.encode("입력안함","utf-8");
			return "redirect:/login/login.wow?msg=" + URLEncoder.encode("입력안함", "utf-8");
		}
		
		try {
			UserVO user = loginService.loginCheck(id, pw);
			HttpSession session = req.getSession();
			session.setAttribute("USER_INFO", user);
			session.setMaxInactiveInterval(1800);

			return "redirect:/";
		} catch (BizNotFoundException e) {
			return "redirect:/login/login.wow?msg=" + URLEncoder.encode("아이디 또는 비번 확인", "utf-8");
		} catch (BizPasswordNotMatchedException e) {
			return "redirect:/login/login.wow?msg=" + URLEncoder.encode("아이디 또는 비번 확인", "utf-8");
		}
	}
	
	
	@RequestMapping("/login/logout.wow")
	public String logout(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session=req.getSession();
		session.removeAttribute("USER_INFO");
		return "redirect:/";
	}

}
