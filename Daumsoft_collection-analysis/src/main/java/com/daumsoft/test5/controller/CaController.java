package com.daumsoft.test5.controller;

import java.io.IOException;

import javax.inject.Inject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daumsoft.test5.service.CaService;

@Controller
public class CaController {
	@Inject
	CaService caService;
	
	@RequestMapping(value="/main")
	public String main() {
		return "visualize/main";
	}
	
	@RequestMapping(value="/search")
	public String search() {

		return "";
	}
}
