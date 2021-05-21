package com.study.code.service;

import java.util.List;

import com.study.code.vo.CodeVO;

public interface ICommonCodeService {
	List<CodeVO> getCodeListByParent(String parentCode) ;
	
}
