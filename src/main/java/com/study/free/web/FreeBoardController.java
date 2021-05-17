package com.study.free.web;

import java.util.List;

import javax.inject.Inject;
import javax.validation.groups.Default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

@Controller
public class FreeBoardController {
	@Inject
	IFreeBoardService freeBoardService;
	@Inject
	ICommonCodeService codeService;
	
	// 컨트롤러단에서의 로그는 보통 파라미터가 넘어왔는지 안넘어왔는지
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	// @ModelAttribute가 붙은 메소드는 요청 메소드 실행되기전에 실행되는 메소드
	// model에 담길 때의 이름
	@ModelAttribute("cateList")
	public List<CodeVO> getCodeList() {
		List<CodeVO> cateList= codeService.getCodeListByParent("BC00");
		// model에 담길 값
		return cateList;
	}
	
	@RequestMapping(value="/free/freeList.wow")
	// 스프링이 알아서 파라미터 값들 VO에 매핑할때 기본적으로는 VO변수의 이름은 첫자만 소문자
	// VO변수 이름을 정하려면 @ModelAttribute
	public String freeList(@ModelAttribute("searchVO")FreeBoardSearchVO searchVO,
			Model model) {
		logger.info("searchVO={}", searchVO);
		model.addAttribute("searchVO", searchVO);
		
		List<FreeBoardVO> freeList= freeBoardService.getBoardList(searchVO);
		model.addAttribute("freeList", freeList);
		
		return "free/freeList";
	}
	
	@RequestMapping(value="/free/freeView.wow")
	// Parameter boNo가 있을 때는 자동으로 Mapping
	public String freeView(@RequestParam(value="boNo", required=true)int boNo,
			Model model) {
		logger.info("boNo=" + boNo);
		try {
			FreeBoardVO free = freeBoardService.getBoard(boNo);
			freeBoardService.increaseHit(boNo);
			model.addAttribute("free", free);
		} catch (BizNotFoundException | BizNotEffectedException e) {
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("view");
			resultMessageVO.setMessage("해당 글이 존재하지 않습니다. 또는 조회수증가 실패했습니다.");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로");
			model.addAttribute("resultMessageVO", resultMessageVO);
			
			return "common/message";
		}
		
		return "free/freeView";
	}
	
	// view에서 edit으로 이동할 때 글번호가 있어야함
	// @RequestParam은 required 기본적으로 true
	@GetMapping(value="/free/freeEdit.wow")
	public String freeEdit(@RequestParam int boNo, Model model) {
		logger.info("boNo=" + boNo);
		FreeBoardVO free = null;
		
		try {
			free = freeBoardService.getBoard(boNo);
		} catch (BizNotFoundException e) {
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("edit");
			resultMessageVO.setMessage("해당 글이 존재하지 않습니다.");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로");
			model.addAttribute("resultMessageVO", resultMessageVO);
			
			return "common/message";
		}
		model.addAttribute("free", free);
		
		return "free/freeEdit";
	}
	
	@PostMapping(value="/free/freeModify.wow")
	public ModelAndView freeModify(@ModelAttribute("free")@Validated({Default.class, ModifyType.class}) FreeBoardVO free,
			BindingResult error) {
		logger.info("freeBoardVO={}", free);
		ModelAndView mav = new ModelAndView();
		if (error.hasErrors()) {
			mav.setViewName("free/freeEdit");
			return mav;
		}
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		
		try{
			freeBoardService.modifyBoard(free);
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("modify");
			resultMessageVO.setMessage("정상적으로 수정했습니다.");
			resultMessageVO.setUrl("freeView.wow?boNo=" + free.getBoNo());
			resultMessageVO.setUrlTitle("글로 돌아가기");
		}catch (BizPasswordNotMatchedException bizPasswordNotMatchedException){
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("modify");
			resultMessageVO.setMessage("비밀번호가 틀립니다.");
			resultMessageVO.setUrl("freeView.wow?boNo=" + free.getBoNo());
			resultMessageVO.setUrlTitle("글로 돌아가기");
		}catch(BizNotEffectedException bizNotEffectedException){
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("modify");
			resultMessageVO.setMessage("수정 실패");
			resultMessageVO.setUrl("freeView.wow?boNo=" + free.getBoNo());
			resultMessageVO.setUrlTitle("글로 돌아가기");
		}catch(BizNotFoundException bizNotFoundException){
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("modify");
			resultMessageVO.setMessage("해당 글이 존재하지 않습니다.");
			resultMessageVO.setUrl("freeView.wow?boNo=" + free.getBoNo());
			resultMessageVO.setUrlTitle("글로 돌아가기");
		}
		mav.addObject("resultMessageVO", resultMessageVO);
		mav.setViewName("common/message");

		return mav;
	}
	
	@RequestMapping(value="/free/freeForm.wow")
	public String freeForm(@ModelAttribute("free")FreeBoardVO free, Model model) {
		return "free/freeForm";
	}
	
	// 검사하고 나서 BindingResult는 항상 검사대상 객체 뒤에
	@PostMapping(value="/free/freeRegist.wow")
	public String freeRegist(@ModelAttribute("free")@Validated({Default.class, RegistType.class}) FreeBoardVO free,
			BindingResult errors,
			Model model) {
		if (errors.hasErrors()) {
			return "free/freeForm";
		}
		logger.info("freeBoardVO={}", free);
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		
		try{
			freeBoardService.registBoard(free);
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("regist");
			resultMessageVO.setMessage("정상적으로 글이 되었습니다.");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		}catch(BizNotEffectedException e){
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("regist");
			resultMessageVO.setMessage("글 등록 실패");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		}
		model.addAttribute("resultMessageVO", resultMessageVO);
		
		return "common/message";
	}
	
	@PostMapping(value="/free/freeDelete.wow")
	public String freeDelete(@ModelAttribute("free")FreeBoardVO free, Model model) {
		logger.info("freeBoardVO={}", free);
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		
		try{
			freeBoardService.removeBoard(free);
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("delete");
			resultMessageVO.setMessage("정상적으로 삭제했습니다.");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		}catch (BizPasswordNotMatchedException bizPasswordNotMatchedException){
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("delete");
			resultMessageVO.setMessage("비밀번호가 틀립니다.");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		}catch(BizNotEffectedException bizNotEffectedException){
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("delete");
			resultMessageVO.setMessage("삭제 실패");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		}catch(BizNotFoundException bizNotFoundException){
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("delete");
			resultMessageVO.setMessage("해당 글이 존재하지 않습니다.");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		}
		model.addAttribute("resultMessageVO", resultMessageVO);
		
		return "common/message";
	}
	
}
