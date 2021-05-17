package com.study.free.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

@Mapper
public interface IFreeBoardDao {
  public int getBoardCount(FreeBoardSearchVO searchVO);
  public List<FreeBoardVO> getBoardList(FreeBoardSearchVO searchVO) ;
  public FreeBoardVO getBoard(int boNo);
  public void increaseHit(int boNo);
  public void insertBoard(FreeBoardVO board);
  public void updateBoard(FreeBoardVO board);
  public void deleteBoard(FreeBoardVO board);
}
