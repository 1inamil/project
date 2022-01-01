<%@page import="com.project.greenpaw.user.UserTO"%>
<%@page import="com.project.greenpaw.user.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	
	int flag = (Integer)request.getAttribute("flag");
	String authType = (String)session.getAttribute("authType");
	//UserTO to = (UserTO)session.getAttribute("to");
	//String authType = to.getAuthType();
	
	System.out.println(authType);
	
	out.println("<script type='text/javascript'>");
	if(flag == 0 && !authType.equals("탈퇴") && !authType.equals("차단")){
		//성공
		out.println("alert('🐾환영합니다🌿');");
		out.println("location.href='main.do';");
	
	}else if(flag == 0 && authType.equals("탈퇴")){
		out.println("alert('탈퇴한 회원입니다.')");
		session.invalidate(); //세션 폐기
		out.println("location.href='main.do';");
	
	}else if(flag == 0 && authType.equals("차단")){
		out.println("alert('차단된 회원입니다.')");
		session.invalidate(); //세션 폐기
		out.println("location.href='main.do';");
		
	}else if(flag == 1){
		//비밀번호 오류
		out.println("alert('이메일과 비밀번호를 확인해주세요.')");
		out.println("history.back();");
	
	}else{
		//시스템 에러
		out.println("alert('시스템 에러')");
		out.println("history.back();");
	}
	
	out.println("</script>");
%>