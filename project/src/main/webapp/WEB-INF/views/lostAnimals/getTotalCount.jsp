<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONObject"%>

<%
	JSONObject results = (JSONObject)request.getAttribute("results");
		
	out.println(results);
%>