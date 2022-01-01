<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
    
<%
	int flag = (Integer) request.getAttribute("flag");

	JSONObject json = new JSONObject();
	// 일단 성공한 척 넣기
	json.put("flag", flag);
	
	out.println(json);
%>
