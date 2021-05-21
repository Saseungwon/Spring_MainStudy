package com.study.login.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import com.study.common.util.ConnectionProvider;
import com.study.common.util.MybatisSqlSessionFactory;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.login.vo.UserVO;
import com.study.member.dao.IMemberDao;

import com.study.member.vo.MemberVO;

@Service
public class LoginServiceImpl implements ILoginService {

	@Inject
	IMemberDao memberDao;
	@Override
	public UserVO loginCheck(String id, String pw) throws BizNotFoundException, BizPasswordNotMatchedException {

		MemberVO member = memberDao.getMember(id);
		if (member == null) {
			throw new BizNotFoundException("member없다");
		} else {
			if (pw.equals(member.getMemPass())) {
				UserVO user = new UserVO();
				user.setUserId(member.getMemId());
				user.setUserName(member.getMemName());
				user.setUserPass(member.getMemPass());
				user.setUserRole("member");
				return user;
			} else {
				throw new BizPasswordNotMatchedException();
			}
		}

	}
}
