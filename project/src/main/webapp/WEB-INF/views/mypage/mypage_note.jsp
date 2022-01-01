<%@ page import="org.json.simple.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int flag = (Integer)request.getAttribute("flag");
	JSONObject json = new JSONObject();
	json.put("flag", flag);
	//System.out.println("mypage note flag: "+json.get("flag"));
	
	out.println(json);
%>