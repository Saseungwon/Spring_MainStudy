package com.study.data.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.study.attach.service.IAttachDao;
import com.study.attach.vo.AttachVO;
import com.study.data.service.IDataBoardDao;
import com.study.data.service.IDataBoardService;
import com.study.data.vo.DataBoardVO;
import com.study.data.vo.DataSearchVO;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;

@Service
public class DataBoardServiceImpl implements IDataBoardService {
	@Inject
	private IDataBoardDao dataDao;
	@Inject
	private IAttachDao attachDao;
	
	@Override
	public List<DataBoardVO> getBoardList(DataSearchVO searchVO) {
		int totalRowCount = dataDao.getBoardCount(searchVO);
		searchVO.setTotalRowCount(totalRowCount);
		searchVO.pageSetting();
		
		return dataDao.getBoardList(searchVO);
	}

	@Override
	public DataBoardVO getBoard(int boNo) throws BizNotFoundException {
		DataBoardVO data = dataDao.getBoard(boNo);
		if (data == null) {
			throw new BizNotFoundException();
		}
		/*
		// 첫번째 방법 : 각각의 DAO를 통해 결과 조회
		List<AttachVO> atts = attachDao.getAttachByParentNoList("DATA", boNo);
		data.setAttaches(atts);
		*/
		return data;
	}

	@Override
	public void increaseHit(int boNo) throws BizNotEffectedException {
		dataDao.increaseHit(boNo);
	}
	
	@Override
	public void registBoard(DataBoardVO free) throws BizNotEffectedException {
		int cnt = dataDao.insertBoard(free);
		if (cnt < 1) {
			throw new BizNotEffectedException();
		}
		// 첨부파일이 존재하는 경우 첨부파일 등록 , parentNo 설정 필요
		List<AttachVO> atchList = free.getAttaches();
		if (atchList != null) {
			for (AttachVO vo : atchList) {
				vo.setAtchParentNo(free.getBoNo());
				attachDao.insertAttach(vo);
			}
		}
	}

	@Override
	public void modifyBoard(DataBoardVO free)
		throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		DataBoardVO board = dataDao.getBoard(free.getBoNo());
		if (board == null)
			throw new BizNotFoundException();
		// board는 db안에 있는거, free는 edit에서 입력한 값으로 저장된 거

		if (board.getBoPass().equals(free.getBoPass())) {
			dataDao.updateBoard(free);
			
			// 기존 삭제된 첨부파일번호가 있는지 확인
			int[] dels = free.getDelAtchNos();
			if (dels != null) {
				attachDao.deleteAttaches(dels);
			}
			
			// 첨부파일이 존재하는 경우 첨부파일 등록 , parentNo 설정 필요
			List<AttachVO> atchList = free.getAttaches();
			if (atchList != null) {
				for (AttachVO vo : atchList) {
					vo.setAtchParentNo(free.getBoNo());
					attachDao.insertAttach(vo);
				}
			}
		} else {
			throw new BizPasswordNotMatchedException();
		}
	}

	@Override
	public void removeBoard(DataBoardVO free)
		throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		DataBoardVO board = dataDao.getBoard(free.getBoNo());
		if (board == null)
			throw new BizNotFoundException();
		// board는 db안에 있는거, free는 edit에서 입력한 값으로 저장된 거

		if (board.getBoPass().equals(free.getBoPass())) {
			dataDao.deleteBoard(free);
		} else {
			throw new BizPasswordNotMatchedException();
		}
	}
}
