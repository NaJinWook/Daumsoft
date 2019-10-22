package com.ds.bbs.controller.member;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ds.bbs.model.member.dto.MemberDTO;
import com.ds.bbs.service.member.MemberService;

@Controller
@RequestMapping("/member*")
public class MemberController {
	@Inject
	MemberService memberService;
	
	//회원가입창 이동
	@RequestMapping(value = "/register", method=RequestMethod.GET)
	public String register() throws Exception {
		return "member/register";
	}
	
	//회원가입시 아이디 중복여부확인
	@ResponseBody
	@RequestMapping(value = "/idCheck", method = RequestMethod.POST, produces = "application/json")
	public int regIdCheck(HttpServletRequest request) throws Exception {
		String userId = request.getParameter("userId");
		System.out.println("ajax로 받은 id값 : " + userId);
		MemberDTO idCheck = memberService.regIdCheck(userId);
		int result = 0;
		if(idCheck != null) {
			result = 1;
		}
		System.out.println("ajax로 반환할 값 : " + result);
		return result;
	}
	
	//회원가입시 닉네임 중복 여부확인
	@ResponseBody
	@RequestMapping(value = "/niknameCheck", method = RequestMethod.POST, produces = "application/json")
	public int regNiknameCheck(HttpServletRequest request) throws Exception {
		String userNikname = request.getParameter("userNikname");
		System.out.println("ajax로 받은 nikname값 : " + userNikname);
		MemberDTO niknameCheck = memberService.regNiknameCheck(userNikname);
		System.out.println("받아온 값  : " + niknameCheck);
		int result = 0;
		if(niknameCheck != null) {
			result = 1;
		}
		System.out.println("ajax로 반환할 값 : " + result);
		return result;
	}
	
	//회원가입 완료
	@RequestMapping(value = "/rgCommit", method = RequestMethod.POST)
	public String register_commit(MemberDTO memberDto) throws Exception {
		memberService.registerMember(memberDto);
		return "member/rgCommit";
	}
	
	//로그인 검사
	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	public String loginCheck(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		session = request.getSession(true);
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		MemberDTO login = memberService.loginCheck(userId, userPwd);
		if(login != null) {
			session.setAttribute("member", login);
		}
		return "redirect:/board/list";
	}
	
	//로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) throws Exception {
		session.invalidate();
		return "board/home"; //페이지 이동
	}
}
