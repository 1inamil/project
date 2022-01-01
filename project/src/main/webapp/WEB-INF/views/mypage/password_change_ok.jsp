<%@page import="javax.websocket.Session"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="org.json.simple.JSONObject"%>

<%
	int flag = (Integer)request.getAttribute("flag");
	
	JSONObject json = new JSONObject();
	json.put("flag", flag);
	System.out.println("password change ok flag: "+json.get("flag"));
	
	out.println(json);
	
%>