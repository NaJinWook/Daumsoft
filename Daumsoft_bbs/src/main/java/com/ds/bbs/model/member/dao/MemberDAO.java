package com.ds.bbs.model.member.dao;

import java.util.Map;

import com.ds.bbs.model.member.dto.MemberDTO;

public interface MemberDAO {
	public MemberDTO regIdCheck(String userId); // 회원가입시 아이디 중복여부확인
	public MemberDTO regNiknameCheck(String userNikname); //회원가입시 닉네임 중복여부확인
	public void registerMember(MemberDTO memberDto); // 회원가입
	public MemberDTO loginCheck(MemberDTO memberDto); //로그인
}
