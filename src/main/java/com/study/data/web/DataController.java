package com.study.data.web;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.study.attach.vo.AttachVO;
import com.study.common.util.StudyAttachUtils;
import com.study.common.vo.ResultMessageVO;
import com.study.data.service.IDataBoardService;
import com.study.data.vo.DataBoardVO;
import com.study.data.vo.DataSearchVO;
import com.study.exception.BizException;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;

// 빈등록 어노테이션 스프링 : @Component(@Service, @Repository, @Controller)
// JSR 표준 : @Bean

@Controller
public class DataController {

	@Inject
	private IDataBoardService boardService;

	@Inject
	private StudyAttachUtils attachUtils;

	private Logger logger = LoggerFactory.getLogger(getClass());

//	@ExceptionHandler(BizNotFoundException.class)
//	public Object notFoundEx(BizNotFoundException e) {
//		ResultMessageVO resultMessageVO = new ResultMessageVO();
//		resultMessageVO.setResult(false);
//		resultMessageVO.setTitle("edit");
//		resultMessageVO.setMessage("해당 글이 존재하지 않습니다.");
//		resultMessageVO.setUrl("freeList.wow");
//		resultMessageVO.setUrlTitle("목록으로");
//		model.addAttribute("resultMessageVO", resultMessageVO);
//		
//		return "common/message";
//	}

	@RequestMapping({ "/data/list.wow", "/data/list.ygy" })
	public String list(@ModelAttribute("searchVO") DataSearchVO searchVO, Model model) {
		logger.info("searchVO={}", searchVO);
		model.addAttribute("searchVO", searchVO);
		List<DataBoardVO> list = boardService.getBoardList(searchVO);
		model.addAttribute("results", list);

		return "data/list";
	}

	// @어노테이션의 기본값 설정 value인데 의미가 불명확 path에 별칭으로 매핑
	@RequestMapping(path = "/data/view.wow", params = "boNo")
	public void view(int boNo, Model model) throws IOException, BizException {
		DataBoardVO vo = boardService.getBoard(boNo);
		model.addAttribute("board", vo);
	}

	// regist.wow : GET요청인 경우 화면으로, POST요청인 경우 등록 처리

	// @RequestMapping(path = "/data/regist.wow", method = RequestMethod.GET)
	@GetMapping("/data/regist.wow")
	public String registGet(@ModelAttribute("board") DataBoardVO board) {
		return "data/regist";
	}

	// @RequestMapping(path = "/data/regist.wow", method = RequestMethod.POST)
	@PostMapping("/data/regist.wow")
	public String registPost(DataBoardVO board,
			@RequestParam(name = "boFiles", required = false) MultipartFile[] boFiles, HttpServletResponse resp,
			HttpServletRequest req, Model model) throws BizException, Exception {
		if (boFiles != null) {
			List<AttachVO> attaches = attachUtils.getAttachListByMultiparts(boFiles, "DATA", "data/2021");
			board.setAttaches(attaches);
		}
		board.setBoIp(req.getRemoteAddr());
		boardService.registBoard(board);
		logger.debug("board={}", board);
		return "redirect:/data/view.wow?boNo=" + board.getBoNo();
	}

	@GetMapping(value = "/data/edit.wow", params = "boNo")
	public String edit(@RequestParam int boNo, Model model) throws BizException {
		logger.info("boNo=" + boNo);
		DataBoardVO data = null;
		data = boardService.getBoard(boNo);
		model.addAttribute("board", data);
		return "data/edit";
	}

	@PostMapping(value = "/data/modify.wow")
	public ModelAndView modify(@ModelAttribute("board") DataBoardVO board,
			@RequestParam(name = "boFiles", required = false) MultipartFile[] boFiles)
			throws BizException, IOException {
		logger.info("DataBoardVO={}", board);
		ModelAndView mav = new ModelAndView();

		// 첨부파일 처리 (registPost 동일)
		// 업로드 파일이 존재하는 경우 저장 후 해당 정보를 vo에 설정
		if (boFiles != null) {
			List<AttachVO> attaches = attachUtils.getAttachListByMultiparts(boFiles, "DATA", "data/2021");
			board.setAttaches(attaches);
		}

		boardService.modifyBoard(board);

		mav.setViewName("redirect:/data/view.wow?boNo=" + board.getBoNo());

		return mav;
	}

	@ExceptionHandler(BizNotFoundException.class)
	public String notFoundExceptionHandler(BizNotFoundException e, Model model) {
		logger.warn(e.getMessage(), e);

		ResultMessageVO messageVO = new ResultMessageVO();
		messageVO.setMessage("글을 찾을 수 없습니다").setResult(false).setTitle("조회실패").setUrl("freeList.wow")
				.setUrlTitle("목록으로");
		model.addAttribute("messageVO", messageVO);
		return "common/message";

	}
	
	@ExceptionHandler(BizPasswordNotMatchedException.class)
	public String passwordNotMatchedException(BizPasswordNotMatchedException e, Model model) {
		logger.warn(e.getMessage(), e);
		ResultMessageVO messageVO = new ResultMessageVO();
		messageVO.setMessage("입력하신 패스워드가 올바르지 않습니다.")
				  .setResult(false)
				  .setTitle("패스워드 오류")
				  .setUrl("list.wow")
				  .setUrlTitle("목록으로");
		model.addAttribute("messageVO", messageVO);
		return "common/message";

	}
	
	@ExceptionHandler(BizNotEffectedException.class)
	public String notEffectedException(BizNotEffectedException e, Model model) {
		logger.warn(e.getMessage(), e);
		ResultMessageVO messageVO = new ResultMessageVO();
		messageVO.setMessage("요청하신 처리를 수행하지 못했습니다.")
				  .setResult(false)
				  .setTitle("처리실패")
				  .setUrl("list.wow")
				  .setUrlTitle("목록으로");
		model.addAttribute("messageVO", messageVO);
		return "common/message";

	}


}
