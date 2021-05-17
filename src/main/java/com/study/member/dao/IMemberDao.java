package com.study.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

@Mapper
public interface IMemberDao {
	public int getMemberCount(MemberSearchVO searchVO);
	public List<MemberVO> getMemberList(MemberSearchVO searchVO);
	public MemberVO getMember(String memId);
	public void updateMember(MemberVO member);
	public void deleteMember(MemberVO member);
	public void insertMember(MemberVO member);
	public String getUserRole(String userId);
}
