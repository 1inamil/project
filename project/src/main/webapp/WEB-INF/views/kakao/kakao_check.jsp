<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	//String contextPath = request.getContextPath();
	int kakaocheck = (Integer)request.getAttribute("kakaocheck");
     
     //없음->아이디 새로 만듬
     if(kakaocheck == 0){
     	
     	out.println("<script>");
 		//out.println("location.href='"+contextPath+"/kakao_nickname.do'");
 		out.println("location.href='kakao_nickname.do'");
 		out.println("</script>");
 		
 		
 		//가입되어있음
     } else if(kakaocheck == 1){
     	out.println("<script>");
 		out.println("location.href='kakaologin_ok.do'");
 		out.println("</script>");
     }
%>
