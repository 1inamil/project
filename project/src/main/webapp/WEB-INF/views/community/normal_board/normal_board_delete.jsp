<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONObject"%>
<%
	int flag = (Integer) request.getAttribute("flag");
	String category = (String) request.getAttribute("category");
	String pages = (String) request.getAttribute("page");
	
	JSONObject json = new JSONObject();
	json.put("flag", flag);
	json.put("category", category);
	json.put("page", pages);
	
	out.println(json);

%>