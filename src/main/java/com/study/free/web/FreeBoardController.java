package com.study.free.web;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;

import org.apache.commons.beanutils.BeanUtils;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.common.valid.ModifyType;
import com.study.common.valid.RegistType;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.exception.DaoDuplicateKeyException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

@Controller
public class FreeBoardController {
	@Inject
	IFreeBoardService freeBoardService;
	@Inject
	ICommonCodeService codeService;
	
	//컨트롤러단에서의 로그는 보통 파라미터가 넘어왔는지 안넘어왔는지
	// import 제발 log4j
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	
	//@ModelAttribute 가붙은 메소드는 요청 메소드 실행되기전에 실행되는 메소드
	@ModelAttribute("cateList")     //model 담길 때의 이름
	public List<CodeVO> getCodeList() {
		List<CodeVO> cateList = codeService.getCodeListByParent("BC00");
		return cateList;//model 담길 값 
	}
	
	@RequestMapping(value = "/free/freeList.wow")
	// 스프링이 알아서 파라미터 값들 VO에 매핑할때
	// 기본적으론 VO변수의 이름은 첫자만 소문자
	// vo변수 이름을 정할려면 @ModelAttribute
	public String freeList(@ModelAttribute("searchVO") FreeBoardSearchVO searchVO, Model model) {
		logger.info("searchVO={}", searchVO);
		model.addAttribute("searchVO", searchVO);
		List<FreeBoardVO> freeList = freeBoardService.getBoardList(searchVO);
		model.addAttribute("freeList", freeList);
		return "free/freeList";
	}

	@RequestMapping("/free/freeView.wow")
	// parameter boNo가 있을 때는 자동으로 mapping
	public String freeView(@RequestParam(value = "boNo", required = true) int boNo, Model model) {
		logger.info("boNo={}", boNo);
		try {
			FreeBoardVO free = freeBoardService.getBoard(boNo);
			freeBoardService.increaseHit(boNo);
			model.addAttribute("free", free);
		} catch (BizNotFoundException | BizNotEffectedException e) {
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.setMessage("글을 찾을 수 없거나 조회수 증가 실패.");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("view");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}
		return "free/freeView";
	}

	// view에서 edit으로 갈때 글번호가 있어야할거같다
	// 버튼으로가니까 post만 되야할거같다
	// @RequestParam은 required기본적으로 true

	@GetMapping(value = "/free/freeEdit.wow")
	public String freeEdit(@RequestParam int boNo, Model model) {
		logger.info("boNo={}", boNo);
		FreeBoardVO free = null;
		try {
			free = freeBoardService.getBoard(boNo);
		} catch (BizNotFoundException e) {
			ResultMessageVO resultMessageVO = new ResultMessageVO();
			resultMessageVO.setMessage("글을 찾을 수 없습니다.");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("edit");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로");
			model.addAttribute("resultMessageVO", resultMessageVO);
			return "common/message";
		}
		model.addAttribute("free", free);
		return "free/freeEdit";
	}

	// @RequestMapping(value="/free/freeEdit.wow",method = RequestMethod.POST)
	@PostMapping("/free/freeModify.wow")
	public ModelAndView freeModify(@ModelAttribute("free")
	@Validated({Default.class,ModifyType.class})
	FreeBoardVO free,BindingResult error) {
		logger.info("FreeBoardVO={}", free);
		ModelAndView mav = new ModelAndView();
		if(error.hasErrors()) {
			mav.setViewName("free/freeEdit");
			return mav;
		}
		
		
		
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
			freeBoardService.modifyBoard(free);
			resultMessageVO.setMessage("정상적으로 수정했습니다.");
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("수정");
			resultMessageVO.setUrl("freeView.wow?boNo=" + free.getBoNo());
			resultMessageVO.setUrlTitle("뷰화면으로 ");
		} catch (BizPasswordNotMatchedException bizPasswordNotMatchedException) {
			resultMessageVO.setMessage("비밀번호가 다릅니다");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("수정");
			resultMessageVO.setUrl("freeView.wow?boNo=" + free.getBoNo());
			resultMessageVO.setUrlTitle("뷰화면으로");
		} catch (BizNotEffectedException bizNotEffectedException) {
			resultMessageVO.setMessage("수정실패");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("수정");
			resultMessageVO.setUrl("freeView.wow?boNo=" + free.getBoNo());
			resultMessageVO.setUrlTitle("뷰화면으로");
		} catch (BizNotFoundException bizNotFoundException) {
			resultMessageVO.setMessage("찾을 수 없습니다");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("수정");
			resultMessageVO.setUrl("freeView.wow?boNo=" + free.getBoNo());
			resultMessageVO.setUrlTitle("뷰화면으로");
		}
		mav.addObject("resultMessageVO", resultMessageVO);
		mav.setViewName("common/message");
		return mav;
	}

	// return type은 다 string
	// regist랑 delete는 post
	// delte랑 form,regist 작성해보세여

	@RequestMapping("/free/freeDelete.wow")
	public String freeDelete(@ModelAttribute("freeBoard") FreeBoardVO freeBoard, Model model) {
		logger.info("FreeBoardVO={}", freeBoard);
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
			freeBoardService.removeBoard(freeBoard);
			resultMessageVO.setMessage("정상적으로 삭제했습니다.");
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("삭제");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		} catch (BizPasswordNotMatchedException bizPasswordNotMatchedException) {
			resultMessageVO.setMessage("패스워드가 다릅니다");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("삭제");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		} catch (BizNotEffectedException bizNotEffectedException) {
			resultMessageVO.setMessage("삭제 실패");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("삭제");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		} catch (BizNotFoundException bizNotFoundException) {
			resultMessageVO.setMessage("글을 찾을 수 없습니다");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("삭제");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로");
		}
		model.addAttribute("resultMessageVO", resultMessageVO);
		return "common/message";
	}

	@RequestMapping("/free/freeForm.wow")
	public String freeForm(@ModelAttribute("free")FreeBoardVO free,Model model) {
		return "free/freeForm";
	}

	//검사하고 나서 BindingResult는 항상 검사대상 객체 뒤에 
	@RequestMapping(value = "/free/freeRegist.wow", method = RequestMethod.POST)
	public String freeRegist(@ModelAttribute("free")@Validated({Default.class,RegistType.class}) FreeBoardVO free
			,BindingResult errors
			,Model model
			, HttpServletRequest req){
		if(errors.hasErrors()) {
			return "free/freeForm";
		}
		
		logger.info("free={}", free);
		ResultMessageVO resultMessageVO = new ResultMessageVO();
		try {
			freeBoardService.registBoard(free);
			resultMessageVO.setMessage("정상적으로 등록했습니다.");
			resultMessageVO.setResult(true);
			resultMessageVO.setTitle("등록");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로 ");
		} catch (BizNotEffectedException e) {
			resultMessageVO.setMessage("등록실패");
			resultMessageVO.setResult(false);
			resultMessageVO.setTitle("등록");
			resultMessageVO.setUrl("freeList.wow");
			resultMessageVO.setUrlTitle("목록으로 ");
		}
		model.addAttribute("resultMessageVO",resultMessageVO);
		return "common/message";
	}

}
