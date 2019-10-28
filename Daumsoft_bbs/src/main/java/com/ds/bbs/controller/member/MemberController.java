package com.ds.bbs.controller.member;

import java.security.PrivateKey;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ds.bbs.cripto.CriptoClass;
import com.ds.bbs.model.member.dto.MemberDTO;
import com.ds.bbs.service.member.MemberService;

@Controller
@RequestMapping("/member*")
public class MemberController {
	@Inject
	MemberService memberService;
	
	CriptoClass cripto = new CriptoClass();
	
	//회원가입창 이동
	@RequestMapping(value = "/register", method=RequestMethod.GET)
	public String register(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		
		// 개인키 삭제
    	session.removeAttribute(CriptoClass.RSA_WEB_KEY);

		// RSA 키 생성
    	cripto.initRsa(request);
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
	public String register_commit(HttpServletRequest request) throws Exception {
		MemberDTO memberDto = new MemberDTO();
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String userNikname = request.getParameter("userNikname");
		String userPwd = null;
		String USER_PW = request.getParameter("USER_PW"); // rsa로 변환한 pw
		
		HttpSession session = request.getSession();
        
		/* **************************************암호화************************************ */
		PrivateKey privateKey = (PrivateKey) session.getAttribute(CriptoClass.RSA_WEB_KEY);
        // rsa 암호화 된 패스워드 복호화하여 평문 만들기
        userPwd = cripto.decryptRsa(privateKey, USER_PW);
        // 평문을 sha256을 통해 암호화 변경 후 DB 입력
        userPwd = cripto.transBySHA256(userPwd);

        memberDto.setUserId(userId);
		memberDto.setUserName(userName);
		memberDto.setUserNikname(userNikname);
		memberDto.setUserPwd(userPwd);
		memberService.registerMember(memberDto);
		return "redirect:/board?message=success";
	}
	
	//로그인 검사
	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	public String loginCheck(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String userId = request.getParameter("login_id");
		String userPwd = null;
		String LOGIN_USER_PW = request.getParameter("LOGIN_USER_PW");
		System.out.println("받아온 PW : " + LOGIN_USER_PW);
		
		/* **************************************암호화************************************ */
        PrivateKey privateKey = (PrivateKey) session.getAttribute(CriptoClass.RSA_WEB_KEY); // 개인키
        // rsa 암호화 된 패스워드 복호화하여 평문 만들기
        userPwd = cripto.decryptRsa(privateKey, LOGIN_USER_PW);
		System.out.println("평문 PW : " + userPwd);
		// 평문을 SHA256 형식으로 변환
		userPwd = cripto.transBySHA256(userPwd);
		
		MemberDTO login = memberService.loginCheck(userId, userPwd);
		if(login != null) {
			session.setAttribute("member", login);
		} else {
			return "redirect:/board?message=nologin";
		}
		return "redirect:/board/list";
	}
	
	//로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletRequest request) throws Exception {
		session.invalidate(); // 세션 삭제
		
		session = request.getSession();
		// 개인키 삭제
    	session.removeAttribute(CriptoClass.RSA_WEB_KEY);
		// RSA 키 생성
    	cripto.initRsa(request);
    	
		return "board/home"; //페이지 이동
	}
	
	@RequestMapping(value = "/management", method = RequestMethod.GET)
	public String management() throws Exception {
		return "member/management";
	}
}
