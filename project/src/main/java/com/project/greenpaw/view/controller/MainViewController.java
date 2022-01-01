package com.project.greenpaw.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainViewController {
	
	@RequestMapping(value={"/","/main.do"})
	public String mainView() {
		return "/main";
	}
	
	@RequestMapping("/sign_in.do")
	public String signInView() {
		return "/login/sign_in";
	}
	
	@RequestMapping("/join_us.do")
	public String joinUsView() {
		return "/login/join_us";
	}
		
	@RequestMapping("/lostAnimals.do")
	public String lostAnimalsView() {
		return "/lostAnimals/lostAnimals";
	}
	
	@RequestMapping("/invalid_access.do")
	public String invalidView() {
		return "/login/invalid_access";
	}
	
}
