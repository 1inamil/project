<%@ page language="java" contentType="plain; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.io.File"%>
    
<%
	int flag = (Integer) request.getAttribute("flag");
	//int seq = (Integer) request.getAttribute("seq");
	
	JSONObject json = new JSONObject();
	json.put("flag", flag);
	//json.put("seq", seq);
	
	out.println(json);
%>
