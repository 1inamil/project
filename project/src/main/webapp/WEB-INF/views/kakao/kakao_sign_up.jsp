<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	int flag = (Integer) request.getAttribute("flag");

	//성공
	if(flag == 1){
		
		out.println("<script>");
		out.println("location.href='"+contextPath+"/kakaologin_ok.do'");
		out.println("</script>");
		
	} else if(flag == 0){
		out.println("<script>");
		out.println("alert('시스템 에러')");
		out.println("history.back()");
		out.println("</script>");
	}
%>
