package com.study.free.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.common.util.ConnectionProvider;
import com.study.common.util.MybatisSqlSessionFactory;
import com.study.common.vo.PagingVO;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.exception.DaoException;
import com.study.free.dao.IFreeBoardDao;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

@Service
public class FreeBoardServiceImpl implements IFreeBoardService {
	
	//FreeBoardServiceImpl 은 IFreeBoardDao 객체인 
	//freeBoardDao에 의존한다.
	
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
		int cnt = freeBoardDao.increaseHit(boNo);
		if (cnt < 1) {
			throw new BizNotEffectedException();
		}
	}

	@Override
	public void modifyBoard(FreeBoardVO free)
			throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		FreeBoardVO board = freeBoardDao.getBoard(free.getBoNo());
		if (board == null)
			throw new BizNotFoundException();
		if (board.getBoPass().equals(free.getBoPass())) {
			System.out.println("modify");
			int cnt = freeBoardDao.updateBoard(free);
			if (cnt < 1)
				throw new BizNotEffectedException();
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
		if (board.getBoPass().equals(free.getBoPass())) {
			int cnt = freeBoardDao.deleteBoard(free);
			if (cnt < 1)
				throw new BizNotEffectedException();
		} else {
			throw new BizPasswordNotMatchedException();
		}
	}

	@Override
	public void registBoard(FreeBoardVO free) throws BizNotEffectedException {
		int cnt = freeBoardDao.insertBoard(free);
		if (cnt < 1) {
			throw new BizNotEffectedException();
		}
	}

}
