package com.study.member.web;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizDuplicateKeyException;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.member.service.IMemberService;
import com.study.member.service.MemberServiceImpl;
import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;
@Controller
public class MemberController {

	@Inject
	IMemberService memberService;
	@Inject
	ICommonCodeService codeService;

	private Logger logger = LoggerFactory.getLogger(getClass());

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

	@RequestMapping("/member/memberList.wow")
	public void memberList(@ModelAttribute("searchVO") MemberSearchVO searchVO, Model model) {
		logger.info("searchVO={}", searchVO);
		model.addAttribute("searchVO", searchVO);
		List<MemberVO> memberList = memberService.getMemberList(searchVO);
		model.addAttribute("memberList", memberList);
	}

	@RequestMapping("/member/memberView.wow")
	public String memberView(@RequestParam(value = "memId", required = true) String memId, Model model) {
		logger.info("memId={}", memId);
		try {
			MemberVO member = memberService.getMember(memId);
			model.addAttribute("member", member);
		} catch (BizNotFoundException e) {
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.setMessage("글을 찾을 수 없엉");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("상세보기");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}
		return "member/memberView";
	}

	@RequestMapping("/member/memberEdit.wow")
	public String memberEdit(@RequestParam(value = "memId", required = true) String memId, Model model) {
		logger.info("memId={}", memId);
		try {
			MemberVO member = memberService.getMember(memId);
			model.addAttribute("member", member);
		} catch (BizNotFoundException e) {
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.setMessage("글을 찾을 수 없엉");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("수정");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}
		return "member/memberEdit";
	}

	@RequestMapping(value = "/member/meberModify.wow", params = "memid", method = RequestMethod.POST)
	public String memberModify(@ModelAttribute("member") MemberVO member, Model model) {
		logger.info("member={}", member);

		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
			memberService.modifyMember(member);
			resultMessageVO.setMessage("수정 성공");
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("수정");
			resultMessageVO.setUrl("memberView.wow?member=" + member.getMemId());
			resultMessageVO.setUrlTitle("목록으로");
		} catch (BizNotFoundException bizNotFoundException) {
			resultMessageVO.setMessage("글을 찾을 수 없엉");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("수정");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		} catch (BizNotEffectedException bizNotEffectedException) {
			resultMessageVO.setMessage("수정실패 ");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("수정");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		}
		model.addAttribute("resultMessageVO", resultMessageVO);
		return "common/message";
	}

	@RequestMapping(value = "/member/memberDelete", params = { "memid", "memPass" }, method = RequestMethod.POST)
	public String memberDelte(@ModelAttribute("member") MemberVO member, Model model) {
		logger.info("member={}", member);
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
			memberService.removeMember(member);
			resultMessageVO.setMessage("삭제성공");
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("삭제");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		} catch (BizNotFoundException bizNotFoundException) {
			resultMessageVO.setMessage("글을 찾을 수 없엉");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("삭제");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		} catch (BizNotEffectedException bizNotEffectedException) {
			resultMessageVO.setMessage("삭제 실패 ");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("삭제");
			resultMessageVO.setUrl("memberList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		}
		model.addAttribute("resultMessageVO", resultMessageVO);
		return "common/message";
	}

	@RequestMapping(value = "/member/memberForm.wow")
	public void memberForm(Model model) {
	}

	@RequestMapping(value = "/member/memberRegist.wow", method = RequestMethod.POST)
	public ModelAndView memberRegist(@ModelAttribute("member") MemberVO member) {
		logger.info("member={}", member);

		ModelAndView mav = new ModelAndView();
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
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

}
