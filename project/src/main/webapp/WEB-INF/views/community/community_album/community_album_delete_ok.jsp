<%@page import="java.io.File"%>
<%@ page language="java" contentType="plain; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ page import="org.json.simple.JSONObject"%>

<%
	int flag = (Integer) request.getAttribute("flag");
	
	JSONObject json = new JSONObject();
	json.put("flag", flag);
	
	out.println(json);
%>