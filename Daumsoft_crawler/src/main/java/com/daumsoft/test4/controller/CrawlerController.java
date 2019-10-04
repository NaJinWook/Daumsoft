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
		try {
			if(crawlerService.count(1) == 0) {
				crawlerService.getData("https://www.insight.co.kr/section/life-style");
			}
			if(crawlerService.count(2) == 0) {
				crawlerService.getData("https://www.insight.co.kr/section/weird-news");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "home";
	}
	
	@Scheduled(cron = "0 0/10 * * * ?")
	public void auto() {
		try {
			if(crawlerService.count(1) != 0) {
				crawlerService.addData("https://www.insight.co.kr/section/life-style");
			}
			if(crawlerService.count(2) != 0) {
				crawlerService.addData("https://www.insight.co.kr/section/weird-news");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
