package com.daumsoft.test4.controller;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daumsoft.test4.service.CrawlerService;

@Component
@Controller
public class CrawlerController {
	@Inject
	CrawlerService crawlerService;
	
	@RequestMapping(value = "/main")
	public String home() {
		int count = 0;
		try {
			count = crawlerService.count();
			if(count == 0) {
				crawlerService.getData("/section/life-style?");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "home";
	}
	
	@Scheduled(cron = "0 0/1 * * * ?")
	public void auto() {
		try {
			crawlerService.addData("/section/life-style?");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
