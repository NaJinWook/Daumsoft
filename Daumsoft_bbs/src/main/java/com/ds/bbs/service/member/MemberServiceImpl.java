package com.ds.bbs.service.member;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.ds.bbs.model.member.dao.MemberDAO;
import com.ds.bbs.model.member.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {
	@Inject
	MemberDAO memberDao;

	@Override
	public MemberDTO regIdCheck(String userId) {
		return memberDao.regIdCheck(userId);
	}

	@Override
	public MemberDTO regNiknameCheck(String userNikname) {
		return memberDao.regNiknameCheck(userNikname);
	}
	
	@Override
	public void registerMember(MemberDTO memberDto) {
		
		memberDao.registerMember(memberDto);
	}

	@Override
	public MemberDTO loginCheck(String userId, String userPwd) {
		MemberDTO memberDto = new MemberDTO();
		memberDto.setUserId(userId);
		memberDto.setUserPwd(userPwd);
		return memberDao.loginCheck(memberDto);
	}
}
