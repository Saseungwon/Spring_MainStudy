package com.study.code.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.code.vo.CodeVO;

@Mapper
public interface ICommonCodeDao {
	public List<CodeVO> getCodeListByParent(String parentCode);
	
}
