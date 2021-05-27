package com.study.data.service;

import java.util.List;

import com.study.data.vo.DataBoardVO;
import com.study.data.vo.DataSearchVO;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;

public interface IDataBoardService {
	public List<DataBoardVO> getBoardList(DataSearchVO searchVO);
	public DataBoardVO getBoard(int boNo) throws BizNotFoundException;	
	public void increaseHit(int boNo) throws BizNotEffectedException;
	public void modifyBoard(DataBoardVO board) throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException;	
	public void removeBoard(DataBoardVO board) throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException; 	
	public void registBoard(DataBoardVO board) throws BizNotEffectedException;	
}
