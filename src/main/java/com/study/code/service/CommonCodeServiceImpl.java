package com.study.code.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import com.study.code.dao.ICommonCodeDao;
import com.study.code.vo.CodeVO;
import com.study.common.util.ConnectionProvider;
import com.study.common.util.MybatisSqlSessionFactory;
import com.study.exception.DaoException;
import com.study.free.dao.IFreeBoardDao;

@Service
public class CommonCodeServiceImpl implements ICommonCodeService {

	@Inject
	ICommonCodeDao codeDao;
	
	@Override
	public List<CodeVO> getCodeListByParent(String parentCode) {
			List<CodeVO> codeList=codeDao.getCodeListByParent(parentCode);
			return codeList;
		}
	

}
