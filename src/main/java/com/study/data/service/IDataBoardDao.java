package com.study.data.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.data.vo.DataBoardVO;
import com.study.data.vo.DataSearchVO;

@Mapper
public interface IDataBoardDao {
  public int getBoardCount(DataSearchVO searchVO);
  public List<DataBoardVO> getBoardList(DataSearchVO searchVO) ;
  public DataBoardVO getBoard(int boNo);
  public void increaseHit(int boNo);
  public int insertBoard(DataBoardVO board);
  public void updateBoard(DataBoardVO board);
  public void deleteBoard(DataBoardVO board);
}
