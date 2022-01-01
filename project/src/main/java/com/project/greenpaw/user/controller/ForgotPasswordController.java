package com.project.greenpaw.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.greenpaw.user.UserDaoInterface;
import com.project.greenpaw.user.UserTO;

@Controller
public class ForgotPasswordController {
	
	@Autowired
	private UserDaoInterface daoInterface;

	@RequestMapping("/forgot_password.do")
	public String forgotPassword() {
		return "/login/forgot_password";
	}
	
	@RequestMapping("/forgot_password_ok.do")
	public ModelAndView forgotPasswordOk(HttpServletRequest request, ModelAndView modelAndView, UserTO to) {
		
		String mail = (String)request.getParameter("mail");
		String password = (String)request.getParameter("password");
		String hint = (String)request.getParameter("hint");
		String answer = (String)request.getParameter("answer");
		
		System.out.println(mail);
		System.out.println(password);
		System.out.println(hint);
		System.out.println(answer);

		String EncryptPs = daoInterface.sha256(password);
		
		to.setMail(mail);
		to.setPassword(EncryptPs);
		to.setHintPassword(hint);
		to.setAnswerPassword(answer);

		/*
		System.out.println(to.getMail());
		System.out.println(to.getPassword());
		System.out.println(to.getHintPassword());
		System.out.println(to.getAnswerPassword());
		*/
		
		int flag = daoInterface.forgotPassword(to);
		
		modelAndView.setViewName("/login/forgot_password_ok");
		modelAndView.addObject("flag", flag);
		
		return modelAndView;
	}
	
	
}
