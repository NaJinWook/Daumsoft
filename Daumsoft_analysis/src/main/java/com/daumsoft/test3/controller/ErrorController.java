package com.daumsoft.test3.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
	// 공통 Error Page 처리
	@RequestMapping(value = "/throwable")
	public String throwable(HttpServletRequest request, Model model) {
		model.addAttribute("msg", "예외가 발생하였습니다.");
		return "include/error";
	}

	@RequestMapping(value = "/exception")
	public String exception(HttpServletRequest request, Model model) {
		model.addAttribute("msg", "예외가 발생하였습니다.");
		return "include/error";
	}

	@RequestMapping(value = "/400")
	public String pageError400(HttpServletRequest request, Model model) {
		model.addAttribute("msg", "400 Error : 잘못된 요청입니다.");
		return "include/error";
	}

	@RequestMapping(value = "/403")
	public String pageError403(HttpServletRequest request, Model model) {
		model.addAttribute("msg", "403 Error : 접근이 금지되었습니다.");
		return "include/error";
	}

	@RequestMapping(value = "/404")
	public String pageError404(HttpServletRequest request, Model model) {
		model.addAttribute("msg", "404 Error : 잘못된 요청입니다.");
		return "include/error";
	}

	@RequestMapping(value = "/405")
	public String pageError405(HttpServletRequest request, Model model) {
		model.addAttribute("msg", "405 Error : 요청된 메소드가 혀용되지 않습니다.");
		return "include/error";
	}

	@RequestMapping(value = "/500")
	public String pageError500(HttpServletRequest request, Model model) {
		model.addAttribute("msg", "500 Error : 서버에 오류가 발생하였습니다.");
		return "include/error";
	}

	@RequestMapping(value = "/503")
	public String pageError503(HttpServletRequest request, Model model) {
		model.addAttribute("msg", "503 Error : 서비스를 사용할 수 없습니다.");
		return "include/error";
	}
}
