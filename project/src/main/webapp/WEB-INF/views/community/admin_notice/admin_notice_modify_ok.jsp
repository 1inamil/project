<%@page import="com.project.greenpaw.community.common.BoardTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONObject"%>
   
<%
	int flag = (Integer) request.getAttribute("flag");
	String category = (String) request.getAttribute("category");
	String seq = (String) request.getAttribute("seq");
	
	JSONObject json = new JSONObject();
	// 일단 성공한 척 넣기
	json.put("flag", flag);
	json.put("category",category);
	json.put("seq",seq);
	
	out.println(json);
%>