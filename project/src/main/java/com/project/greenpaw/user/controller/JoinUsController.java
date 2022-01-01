package com.project.greenpaw.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.greenpaw.user.UserDaoInterface;
import com.project.greenpaw.user.UserTO;

@Controller
public class JoinUsController {
	
	@Autowired
	private UserDaoInterface daoInterface;
	
	@RequestMapping("/getNickname.do")
	public ModelAndView getNickname(HttpServletRequest request, ModelAndView modelAndView, UserTO to) {
		
		//1. 사용자 입력정보 추출
		String nickname = (String)request.getParameter("nickname");
		//System.out.println("getNickname.jsp : "+nickname);
		
		//2. DB 데이터 처리
		to.setNickname(nickname);
		int flag = daoInterface.getNickname(to);
		
		//3. ModelAndView 전송
		
		modelAndView.addObject("flag", flag);
		modelAndView.setViewName("/login/getNickname");
		
		return modelAndView;
	}
	
	@RequestMapping("getMail.do")
	public ModelAndView getMail(HttpServletRequest request, ModelAndView modelAndView, UserTO to) {
		
		String mail = (String)request.getParameter("mail");
		//System.out.println("getMail.jsp : "+mail);
		
		to.setMail(mail);
		int flag = daoInterface.getMail(to);

		modelAndView.addObject("flag", flag);
		modelAndView.setViewName("/login/getMail");
		
		return modelAndView;
	}
	
	@RequestMapping("/postUser.do")
	public ModelAndView postUser(HttpServletRequest request, ModelAndView modelAndView, UserTO to) {
		
		String nickname = (String)request.getParameter("nickname");
		String mail = (String)request.getParameter("mail");
		String password = (String)request.getParameter("password");
		String hint = (String)request.getParameter("hint");
		String answer = (String)request.getParameter("answer");
		
		//System.out.println("postUser.jsp : "+mail);

		to.setNickname(nickname);
		to.setMail(mail);
		to.setPassword( daoInterface.sha256(password)); // 비번 암호화
		to.setHintPassword(hint);
		to.setAnswerPassword(answer);
		  
		int flag = daoInterface.joinUser(to);
		
		modelAndView.addObject("flag", flag);
		modelAndView.setViewName("/login/postUser");
		
		return modelAndView;
	}

}
