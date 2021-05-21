package com.study.mypage.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.exception.BizNotFoundException;
import com.study.login.vo.UserVO;
import com.study.member.dao.IMemberDao;
import com.study.member.service.IMemberService;
import com.study.member.service.MemberServiceImpl;
import com.study.member.vo.MemberVO;


@Controller
public class InfoController {
	@Inject
	IMemberService memberService;
	@RequestMapping("/mypage/info.wow")
	public String myPage(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		UserVO user = (UserVO) session.getAttribute("USER_INFO");
		try {
			MemberVO member = memberService.getMember(user.getUserId());
			req.setAttribute("member", member);
		} catch (BizNotFoundException e) {
			return "redirect:/";
		}
		return "mypage/info";
	}
}
