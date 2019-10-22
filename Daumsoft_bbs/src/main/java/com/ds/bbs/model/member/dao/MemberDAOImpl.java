package com.ds.bbs.model.member.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.ds.bbs.model.member.dto.MemberDTO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	@Inject
	SqlSession sqlSession;
	
	//회원가입시 아이디 중복여부확인
	@Override
	public MemberDTO regIdCheck(String userId) {
		return sqlSession.selectOne("member.regIdCheck", userId);
	}

	//회원가입시 닉네임 중복여부확인
	@Override
	public MemberDTO regNiknameCheck(String userNikname) {
		System.out.println("여기는 DAO 받은 nikname : " + userNikname);
		return sqlSession.selectOne("member.regNiknameCheck", userNikname);
	}
	
	//회원 가입
	@Override
	public void registerMember(MemberDTO memberDto) {
		sqlSession.insert("member.register", memberDto);
	}
	
	//회원 로그인
	@Override
	public MemberDTO loginCheck(MemberDTO memberDto) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", memberDto.getUserId());
		map.put("userPwd", memberDto.getUserPwd());
		return sqlSession.selectOne("member.loginCheck", map);
	}
}
