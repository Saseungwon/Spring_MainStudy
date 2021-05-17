package com.study.mypage.web;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.groups.Default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.valid.ModifyType;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.login.vo.UserVO;
import com.study.member.service.IMemberService;
import com.study.member.vo.MemberVO;

@Controller
public class InfoController {
	@Inject
	IMemberService memberService;
	@Inject
	ICommonCodeService codeService;
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@ModelAttribute("jobList")
	public List<CodeVO> getJobList() {
		List<CodeVO> jobList= codeService.getCodeListByParent("JB00");
		return jobList;
	}
	
	@ModelAttribute("hobbyList")
	public List<CodeVO> getHobbyList() {
		List<CodeVO> hobbyList= codeService.getCodeListByParent("HB00");
		return hobbyList;
	}
	
	@RequestMapping("/mypage/info.wow")
	public String info(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();
		UserVO user = (UserVO)session.getAttribute("USER_INFO");
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		if (user == null) {
			return "login/login";
		} else {
			try {
				MemberVO member = memberService.getMember(user.getUserId());
				req.setAttribute("member", member);
				
				return "mypage/info";
			} catch (BizNotFoundException e) {
				resultMessageVO.setResult(false);
				resultMessageVO.setTitle("regist");
				resultMessageVO.setMessage("해당 멤버를 찾을 수 없습니다.");
				resultMessageVO.setUrl("memberList.wow");
				resultMessageVO.setUrlTitle("목록으로");
				
				req.setAttribute("resultMessageVO", resultMessageVO);
				
				return "common/message";
			}
		}
	}
	
	@RequestMapping("/mypage/edit.wow")
	public String edit(@RequestParam(value="memId", required=true)String memId, Model model) {
		logger.info("memId=" + memId);
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
			MemberVO member = memberService.getMember(memId);
			model.addAttribute("member", member);
		} catch (BizNotFoundException e) {
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("view");
			resultMessageVO.setMessage("해당 멤버를 찾을 수 없습니다.");
			resultMessageVO.setUrl("info.wow");
			resultMessageVO.setUrlTitle("내 정보");
			model.addAttribute("resultMessageVO", resultMessageVO);
			
			return "common/message";
		}
		
		return "mypage/edit";
	}
	
	@PostMapping(value="/mypage/modify.wow")
	public String modify(@ModelAttribute("member")@Validated({Default.class, ModifyType.class}) MemberVO member,
			BindingResult error,
			Model model) {
		if (error.hasErrors()) {
			return "mypage/edit";
		}
		logger.info("MemberVO={}", member);
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
			memberService.modifyMember(member);
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("modify");
			resultMessageVO.setMessage("정상적으로 수정했습니다.");
			resultMessageVO.setUrl("info.wow");
			resultMessageVO.setUrlTitle("내 정보");
			model.addAttribute("resultMessageVO", resultMessageVO);
		} catch (BizNotFoundException bizNotFoundException) {
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("modify");
			resultMessageVO.setMessage("해당 멤버를 찾을 수 없습니다.");
			resultMessageVO.setUrl("info.wow");
			resultMessageVO.setUrlTitle("내 정보");
			model.addAttribute("resultMessageVO", resultMessageVO);
		} catch (BizNotEffectedException bizNotEffectedException) {
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("modify");
			resultMessageVO.setMessage("수정 실패");
			resultMessageVO.setUrl("info.wow");
			resultMessageVO.setUrlTitle("내 정보");
			model.addAttribute("resultMessageVO", resultMessageVO);	
		}
		
		return "common/message";
	}

}
