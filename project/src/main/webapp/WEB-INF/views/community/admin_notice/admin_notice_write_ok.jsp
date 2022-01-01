<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONObject"%>
<%
	int flag = (Integer) request.getAttribute("flag");	
	String category = (String) request.getAttribute("category");	

	JSONObject json = new JSONObject();
	// 일단 성공한 척 넣기
	json.put("flag", flag);
	json.put("category", category);
	
	out.println(json);
%>