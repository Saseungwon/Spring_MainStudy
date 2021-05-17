package com.study.code.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.study.code.dao.ICommonCodeDao;
import com.study.code.vo.CodeVO;

@Service
public class CommonCodeServiceImpl implements ICommonCodeService {
	@Inject
	ICommonCodeDao codeDao;
	
	@Override
	public List<CodeVO> getCodeListByParent(String parentCode) {
		List<CodeVO> codeList = codeDao.getCodeListByParent(parentCode);
		return codeList;
	}

}
