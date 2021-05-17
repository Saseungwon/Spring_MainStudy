package com.study.free.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.free.dao.IFreeBoardDao;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

@Service
public class FreeBoardServiceImpl implements IFreeBoardService {
	@Inject
	IFreeBoardDao freeBoardDao;
	
	@Override
	public List<FreeBoardVO> getBoardList(FreeBoardSearchVO searchVO) {
		int totalRowCount = freeBoardDao.getBoardCount(searchVO);
		searchVO.setTotalRowCount(totalRowCount);
		searchVO.pageSetting();
		
		return freeBoardDao.getBoardList(searchVO);
	}

	@Override
	public FreeBoardVO getBoard(int boNo) throws BizNotFoundException {
		FreeBoardVO free = freeBoardDao.getBoard(boNo);
		if (free == null) {
			throw new BizNotFoundException();
		}
		return free;
	}

	@Override
	public void increaseHit(int boNo) throws BizNotEffectedException {
		freeBoardDao.increaseHit(boNo);
	}
	
	@Override
	public void registBoard(FreeBoardVO free) throws BizNotEffectedException {
		freeBoardDao.insertBoard(free);
	}

	@Override
	public void modifyBoard(FreeBoardVO free)
		throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		FreeBoardVO board = freeBoardDao.getBoard(free.getBoNo());
		if (board == null)
			throw new BizNotFoundException();
		// board는 db안에 있는거, free는 edit에서 입력한 값으로 저장된 거

		if (board.getBoPass().equals(free.getBoPass())) {
			freeBoardDao.updateBoard(free);
		} else {
			throw new BizPasswordNotMatchedException();
		}
	}

	@Override
	public void removeBoard(FreeBoardVO free)
		throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		FreeBoardVO board = freeBoardDao.getBoard(free.getBoNo());
		if (board == null)
			throw new BizNotFoundException();
		// board는 db안에 있는거, free는 edit에서 입력한 값으로 저장된 거

		if (board.getBoPass().equals(free.getBoPass())) {
			freeBoardDao.deleteBoard(free);
		} else {
			throw new BizPasswordNotMatchedException();
		}
	}
}
