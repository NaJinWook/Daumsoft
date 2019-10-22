package com.ds.bbs.service.member;

import java.util.Map;

import com.ds.bbs.model.member.dto.MemberDTO;

public interface MemberService {
	public MemberDTO regIdCheck(String userId); //회원가입시 아이디 중복여부확인
	public MemberDTO regNiknameCheck(String userNikname); //회원가입시 닉네임 중복여부확인
	public void registerMember(MemberDTO memberDto); //회원가입
	public MemberDTO loginCheck(String userId, String Pwd); //로그인
//	public MemberDTO reg_niknameCheck(String user_nikname); //회원가입시 닉네임 중복여부확인
//	public void logout(HttpSession session); //로그아웃
//	public void user_update(MemberDTO memberDto); //회원정보수정

}
