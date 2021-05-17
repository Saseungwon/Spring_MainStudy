package com.study.member.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.study.member.service.IMemberService;
import com.study.member.vo.MemberVO;

@Controller
@RequestMapping("/join")
@SessionAttributes("member")
public class MemberJoinController {
	private String mapping="join";
	
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
	
	@ModelAttribute("member")   
	//model에 이미 "member가 있으면" 실행이안됨
	//세션에 있는 model객체를 사용해서 같은 model객체를 
	//사용하도록 해줘야함
	//그게 @SessionAttribute              pdf화면처리 컨트롤러어노테이션
	public MemberVO getMemberVO() {
		MemberVO member=new MemberVO();
		return member;
	}
	
	
	@RequestMapping(value="/step1.wow")
	public String step1(
			@ModelAttribute("member")MemberVO member) {
		return mapping+"/step1";
	}
	
	@RequestMapping(value="/step2.wow",method = RequestMethod.POST)
	public String step2(@ModelAttribute("member")
			@Validated({Step1.class})MemberVO member
			,BindingResult error) {
		if(error.hasErrors()) {
			return mapping+"/step1";
		}
		return mapping+"/step2";
	}
	@RequestMapping(value="/step3.wow",method = RequestMethod.POST)
	public String step3(@ModelAttribute("member")
			@Validated({Step2.class})MemberVO member
			,BindingResult error) {
		if(error.hasErrors()) {
			return mapping+"/step2";
		}
		return mapping+"/step3";
	}
	
	@RequestMapping(value="/regist.wow",method = RequestMethod.POST)
	public  ModelAndView regist(@ModelAttribute("member")
	@Validated({Step3.class})MemberVO member
	,BindingResult error
	,Model model
	,SessionStatus sessionStatus) {
		ModelAndView mav = new ModelAndView();
		if(error.hasErrors()) {
			mav.setViewName(mapping+"/step3");
			return mav;
		}
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
			sessionStatus.setComplete(); 
			//session의model에 담긴 값들  지우는 용도
			memberService.registMember(member);
			resultMessageVO.setMessage("글을 정상적으로 등록했습니다");
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("등록");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		} catch (BizDuplicateKeyException bizDuplicateKeyException) {
			resultMessageVO.setMessage("아이디가 중복이야");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("등록");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		} catch (BizNotEffectedException bizNotEffectedException) {
			resultMessageVO.setMessage("등록 실");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("등록");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		}
		mav.addObject("resultMessageVO", resultMessageVO);
		mav.setViewName("common/message");		
		return mav;	
	}
	@RequestMapping(value="/cancel.wow")
	public String cancel(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "redirect:/";
	}
	
	
	
	
}
