<%@page import="com.project.greenpaw.user.UserDAO"%>
<%@page import="com.project.greenpaw.user.UserTO"%>
<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
    
<%@page import="javax.sql.DataSource"%>

<%@page import="org.json.simple.JSONObject"%>

<%
	int flag = (Integer)request.getAttribute("flag");
	 
	JSONObject result = new JSONObject();
	result.put("flag",flag);
	
	out.println(flag);
%>
