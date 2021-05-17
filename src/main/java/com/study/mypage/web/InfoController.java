package com.study.mypage.web;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.login.vo.UserVO;
import com.study.member.dao.IMemberDao;
import com.study.member.service.IMemberService;
import com.study.member.service.MemberServiceImpl;
import com.study.member.vo.MemberVO;


@Controller
public class InfoController {
	//mypage Edit 추가해줍시다
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	IMemberService memberService;
	@Inject
	ICommonCodeService codeService;

	@ModelAttribute("jobList")
	public List<CodeVO> getJobList() {
		List<CodeVO> jobList = codeService.getCodeListByParent("JB00");
		return jobList;
	}

	@ModelAttribute("hobbyList")
	public List<CodeVO> getHobbyList() {
		List<CodeVO> hobbyList = codeService.getCodeListByParent("HB00");
		return hobbyList;
	}
	
	
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
	
	@RequestMapping("/mypage/edit.wow")
	public String edit(@RequestParam(value = "memId", required = true) String memId, Model model) {
		logger.info("memId={}", memId);
		try {
			MemberVO member = memberService.getMember(memId);
			model.addAttribute("member", member);
		} catch (BizNotFoundException e) {
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.setMessage("글을 찾을 수 없엉");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("수정");
			resultMessageVO.setUrl("info.wow");
			resultMessageVO.setUrlTitle("내정보화면");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}
		return "mypage/edit";
	}
	
	@RequestMapping(value = "/mypage/modify.wow", params = "memId", method = RequestMethod.POST)
	public String modify(@ModelAttribute("member") MemberVO member, Model model) {
		logger.info("member={}", member);
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
			memberService.modifyMember(member);
			resultMessageVO.setMessage("수정 성공");
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("수정");
			resultMessageVO.setUrl("info.wow");
			resultMessageVO.setUrlTitle("내정보화면");
		} catch (BizNotFoundException bizNotFoundException) {
			resultMessageVO.setMessage("글을 찾을 수 없엉");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("수정");
			resultMessageVO.setUrl("info.wow");
			resultMessageVO.setUrlTitle("내정보화면");
		} catch (BizNotEffectedException bizNotEffectedException) {
			resultMessageVO.setMessage("수정실패 ");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("수정");
			resultMessageVO.setUrl("info.wow");
			resultMessageVO.setUrlTitle("내정보화면");
		}
		model.addAttribute("resultMessageVO", resultMessageVO);
		return "common/message";
	}
	
	
}
