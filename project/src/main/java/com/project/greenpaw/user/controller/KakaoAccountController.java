package com.project.greenpaw.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.project.greenpaw.user.UserDaoInterface;
import com.project.greenpaw.user.UserTO;

@Controller
@SessionAttributes({"mail","nickname","kakao_email","kakao_nickname","authType"})
public class KakaoAccountController {
	
	@Autowired
	private UserDaoInterface daoInterface;

	@RequestMapping("/kakao_check.do")
	public ModelAndView checkAccount(HttpServletRequest request, UserTO to, ModelAndView modelAndView, Model model) {
		
		//계정정보가 db에 있는지 확인, 없으면 추가~
	 	String mail = request.getParameter("kakaomail");
	    String nickname = request.getParameter("kakaonickname");
	    
	    //to에 값 전달
	    to.setMail(mail); 
	    to.setNickname(nickname); 
	     
	    int kakaocheck = daoInterface.kakaoIdCheck(to);
	    System.out.println("카카오 계정 존재 1, 없음 0: "+kakaocheck);
	   
	     if(kakaocheck == 0){   //없음->아이디 새로 만듬
	    	 System.out.println("카카오 계정 신규 등록");
	    	 model.addAttribute("kakao_email", mail); //모델과 세션에 넣기
	    	 model.addAttribute("kakao_nickname", nickname);
	 		
	     } else if(kakaocheck == 1){//가입되어있음
	    	 System.out.println("가입한 카카오 계정으로 로그인");
	    	 model.addAttribute("mail", mail); 
	     }
	    
	    modelAndView.addObject("kakaocheck", kakaocheck);
	    modelAndView.setViewName("/kakao/kakao_check");
	    
	    return modelAndView;
	}
	
	@RequestMapping("/kakao_nickname.do")
	public String kakaoNicknameSetting() {
		return "/kakao/kakao_nickname";
	}
	
	@RequestMapping("/kakao_sign_up.do")
	public ModelAndView kakaoSignUp(@ModelAttribute("kakao_email") String mail,HttpServletRequest request, 
			SessionStatus sessionStatus, Model model, UserTO to, ModelAndView modelAndView) {
		
		String nickname = request.getParameter("nickname");
		
		System.out.println(mail);
		System.out.println(nickname);
		
		to.setMail(mail);
		to.setNickname(nickname);
		
		int flag = daoInterface.kakaoJoin(to);
		
		if(flag == 1){
			request.getSession().removeAttribute("kakao_email");
			request.getSession().removeAttribute("kakao_nickname");
			model.addAttribute("mail", mail);
								
		} 
		modelAndView.setViewName("/kakao/kakao_sign_up");
		modelAndView.addObject("flag", flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/kakaologin_ok.do")
	public String kakaoUserInfo(@ModelAttribute("mail") String mail, UserTO to, Model model) {
		
		System.out.println(mail);
	    to = daoInterface.loginInformation(mail);
	    System.out.println("로그인한 카카오 계정 닉네임: "+to.getNickname()+" "+to.getAuthType());
	    model.addAttribute("nickname", to.getNickname());
	    model.addAttribute("authType", to.getAuthType());
	   
	   return "/kakao/kakaologin_ok";
	}
	

}
