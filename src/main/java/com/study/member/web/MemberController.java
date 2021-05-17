package com.study.member.web;

import java.util.List;

import javax.inject.Inject;
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
import org.springframework.web.servlet.ModelAndView;

import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.valid.ModifyType;
import com.study.common.valid.RegistType;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizDuplicateKeyException;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.member.service.IMemberService;
import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

@Controller
public class MemberController {
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
	
	@RequestMapping(value="/member/memberList.wow")
	public void memberList(@ModelAttribute("searchVO")MemberSearchVO searchVO,
			Model model) {
		logger.info("searchVO={}", searchVO);
		List<MemberVO> memberList=memberService.getMemberList(searchVO);
		model.addAttribute("memberList", memberList);
	}
	
	@RequestMapping(value="/member/memberView.wow")
	public String memberView(@RequestParam(value="memId", required=true)String memId,
			Model model) {
		logger.info("memId=" + memId);
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
			MemberVO member = memberService.getMember(memId);
			model.addAttribute("member", member);
		} catch (BizNotFoundException e) {
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("view");
			resultMessageVO.setMessage("해당 멤버를 찾을 수 없습니다.");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}
		
		return "member/memberView";
	}
	
	@RequestMapping(value="/member/memberEdit.wow")
	public String memberEdit(@RequestParam(value="memId", required=true)String memId, Model model) {
		logger.info("memId=" + memId);
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
			MemberVO member = memberService.getMember(memId);
			model.addAttribute("member", member);
		} catch (BizNotFoundException e) {
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("view");
			resultMessageVO.setMessage("해당 멤버를 찾을 수 없습니다.");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
			model.addAttribute("resultMessageVO", resultMessageVO);
			
			return "common/message";
		}
		
		return "member/memberEdit";
	}
	
	@PostMapping(value="/member/memberModify.wow")
	public String memberModify(@ModelAttribute("member")@Validated({Default.class, ModifyType.class}) MemberVO member,
			BindingResult error,
			Model model) {
		if (error.hasErrors()) {
			return "member/memberEdit";
		}
		logger.info("MemberVO={}", member);
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
			memberService.modifyMember(member);
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("modify");
			resultMessageVO.setMessage("정상적으로 수정했습니다.");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
			model.addAttribute("resultMessageVO", resultMessageVO);
		} catch (BizNotFoundException bizNotFoundException) {
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("modify");
			resultMessageVO.setMessage("해당 멤버를 찾을 수 없습니다.");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
			model.addAttribute("resultMessageVO", resultMessageVO);
		} catch (BizNotEffectedException bizNotEffectedException) {
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("modify");
			resultMessageVO.setMessage("수정 실패");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
			model.addAttribute("resultMessageVO", resultMessageVO);	
		}
		
		return "common/message";
	}
	
	@RequestMapping(value="/member/memberForm.wow")
	public void memberForm(@ModelAttribute("member")MemberVO member,
			Model model) {}
	
	@PostMapping(value="/member/memberRegist.wow")
	public ModelAndView memberRegist(@ModelAttribute("member")@Validated({Default.class, RegistType.class}) MemberVO member,
			BindingResult error) {
		logger.info("MemberVO={}", member);
		ModelAndView mav = new ModelAndView();
		if (error.hasErrors()) {
			mav.setViewName("member/memberForm");
			return mav;
		}
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		
		try {
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
	
	@PostMapping(value="/member/memberDelete.wow", params={"memId", "memPass"})
	public String memberDelete(@ModelAttribute("member")MemberVO member, Model model) {
		logger.info("MemberVO={}", member);
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		
		try {
			memberService.removeMember(member);
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("delete");
			resultMessageVO.setMessage("정상적으로 회원을 삭제했습니다.");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
			model.addAttribute("resultMessageVO", resultMessageVO);
		} catch (BizNotFoundException bizNotFoundException) {
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("delete");
			resultMessageVO.setMessage("회원이 존재하지 않습니다.");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
			model.addAttribute("resultMessageVO", resultMessageVO);
		} catch (BizNotEffectedException bizNotEffectedException) {
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("delete");
			resultMessageVO.setMessage("삭제에 실패했습니다.");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
			model.addAttribute("resultMessageVO", resultMessageVO);
		}
		
		return "common/message";
	}
	
}
