package com.project.greenpaw.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.project.greenpaw.user.UserDaoInterface;
import com.project.greenpaw.user.UserTO;


@Controller
@SessionAttributes({"authType", "mail", "nickname"})
public class LoginController{
	
	@Autowired
	private UserDaoInterface daoInterface;
	
	@RequestMapping("/login_ok.do")
	public ModelAndView login(HttpServletRequest request, UserTO to, ModelAndView modelAndView) {
		
		//1. 사용자 입력정보 추출
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		//2. 데이터 처리
		password = daoInterface.sha256(password); // 비밀번호 암호화
		
		//System.out.println("암호화 후: "+password);
		//System.out.println("암호화 후 길이: "+password.length());
		
		to.setMail(email);
		to.setPassword(password);
		
		//3. DB 처리
		int result = daoInterface.logInOk(to); //로그인 검증(ID, 비밀번호 일치 확인)

		//4. ModelAndView 전송, session에 유저 정보 저장

		int flag = 2;
		
		if( result == 1 ){ //로그인 성공
			flag = 0;
			to = daoInterface.getSessionData(to);
			
			modelAndView.addObject("authType", to.getAuthType());
			modelAndView.addObject("mail", to.getMail());
			modelAndView.addObject("nickname", to.getNickname());

		}else if( result == 0){ //로그인 실패
			flag = 1;
		}
		
		modelAndView.addObject("flag", flag);
		
		modelAndView.setViewName("/login/login_ok");
		
		return modelAndView;
	}
	
	@RequestMapping("/logout_ok.do")
	public String logoutOk(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "/login/logout_ok";
	}

}
