package com.study.member.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.study.exception.BizDuplicateKeyException;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.member.dao.IMemberDao;
import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

@Service
public class MemberServiceImpl implements IMemberService {
	@Inject
	IMemberDao memberDao;
	
	@Override
	public List<MemberVO> getMemberList(MemberSearchVO searchVO) {
		int totalRowCount=memberDao.getMemberCount(searchVO);
		searchVO.setTotalRowCount(totalRowCount);
		searchVO.pageSetting();
		return memberDao.getMemberList(searchVO);
	}

	@Override
	public MemberVO getMember(String memId) throws BizNotFoundException {
		MemberVO member=memberDao.getMember(memId);
		if(member==null) {
			throw new BizNotFoundException();
		}
		return member;
	}

	@Override
	public void modifyMember(MemberVO member) throws BizNotEffectedException, BizNotFoundException {
		MemberVO vo=memberDao.getMember(member.getMemId());
		if(vo==null) {
			throw new BizNotFoundException();
		}
		memberDao.updateMember(member);
	}

	@Override
	public void removeMember(MemberVO member) throws BizNotEffectedException, BizNotFoundException {
		MemberVO vo=memberDao.getMember(member.getMemId());
		if(vo==null) {
			throw new BizNotFoundException();
		}
		memberDao.deleteMember(member);
	}

	@Override
	public void registMember(MemberVO member) throws BizNotEffectedException,BizDuplicateKeyException {
		MemberVO vo=memberDao.getMember(member.getMemId());
		if(vo!=null) throw new BizDuplicateKeyException();
		memberDao.insertMember(member);
	}
	
}
