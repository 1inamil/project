<%@page import="com.project.greenpaw.user.UserDAO"%>
<%@page import="com.project.greenpaw.user.UserTO"%>
<%@ page import="org.json.simple.JSONObject"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int flag = (Integer)request.getAttribute("flag");
	
	JSONObject json = new JSONObject();
	json.put("flag", flag);
	System.out.println("forgot password ok flag: "+json.get("flag"));
	
	out.println(json);
	
%>