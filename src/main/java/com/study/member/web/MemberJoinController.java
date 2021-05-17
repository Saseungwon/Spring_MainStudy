package com.study.member.web;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.valid.Step1;
import com.study.common.valid.Step2;
import com.study.common.valid.Step3;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizDuplicateKeyException;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.member.service.IMemberService;
import com.study.member.service.MailSendService;
import com.study.member.vo.MemberVO;

@Controller
@RequestMapping(value = "/join")
@SessionAttributes("member")
public class MemberJoinController {
	@Inject
	IMemberService memberService;
	@Inject
	ICommonCodeService codeService;
	@Inject
	MailSendService mailSendService;
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	private String mapping = "join";
	
	// model
	@ModelAttribute("member")
	public MemberVO getMemberVO() {
		MemberVO member = new MemberVO();
		return member;
	}
	
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
	
	@RequestMapping(value = "/step1.wow")
	public String step1(@ModelAttribute("member")MemberVO member) {
		return mapping + "/step1";
	}
	
	@RequestMapping(value = "/step2.wow", method = RequestMethod.POST)
	public String step2(@ModelAttribute("member")@Validated({Step1.class}) MemberVO member,
			BindingResult error) {
		if (error.hasErrors()) {
			return mapping + "/step1";
		}
		return mapping + "/step2";
	}
	
	@RequestMapping(value = "/idck", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String idCk(MemberVO member) {
		logger.info("member = {}", member);
		try {
			memberService.getMember(member.getMemId());
			return "isExist";
		} catch (BizNotFoundException e) {			
			return "isNotExist";
		}
	}
	
	@RequestMapping(value = "/email.wow", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String email(@RequestParam("email")String email, HttpServletRequest req) {
		logger.info("email = " + email);
		return mailSendService.sendAuthMail(email, req.getServerName(), req.getServerPort());
	}
	
	@RequestMapping(value = "/step3.wow", method = RequestMethod.POST)
	public String step3(@ModelAttribute("member")@Validated({Step2.class}) MemberVO member,
			BindingResult error) {
		if (error.hasErrors()) {
			return mapping + "/step2";
		}
		return mapping + "/step3";
	}
	
	@RequestMapping(value = "/regist.wow", method = RequestMethod.POST)
	public ModelAndView regist(@ModelAttribute("member")@Validated({Step3.class})MemberVO member,
			BindingResult error,
			Model model,
			SessionStatus sessionStatus) {
		ModelAndView mav = new ModelAndView();
		if (error.hasErrors()) {
			mav.setViewName(mapping + "/step3");
			return mav;
		}
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		
		try {
			// session의 model에 담긴 값들 지우는 용도
			sessionStatus.setComplete();
			memberService.registMember(member);
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("regist");
			resultMessageVO.setMessage("정상적으로 회원 등록 되었습니다.");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		} catch (BizDuplicateKeyException bizDuplicateKeyException) {
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("regist");
			resultMessageVO.setMessage("아이디 중복");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		} catch (BizNotEffectedException bizNotEffectedException) {
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("regist");
			resultMessageVO.setMessage("등록 실패");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		}
		mav.addObject("resultMessageVO", resultMessageVO);
		mav.setViewName("common/message");
		
		return mav;
	}
	
	@RequestMapping(value = "/cancel")
	public String cancel(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "redirect:/";
	}
	
}
