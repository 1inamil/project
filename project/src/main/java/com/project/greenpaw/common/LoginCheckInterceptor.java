package com.project.greenpaw.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.WebContentInterceptor;

public class LoginCheckInterceptor extends WebContentInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// TODO Auto-generated method stub

//		System.out.println("===========");
//		System.out.println("인터셉터 hit");
	
		HttpSession session = request.getSession();
		String contextPath = request.getContextPath();

		if(session.getAttribute("mail") != null) { //비로그인시
				return true; 
				
		}else {
			try {
				response.sendRedirect(contextPath+"/invalid_access.do");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
			
	}

}
