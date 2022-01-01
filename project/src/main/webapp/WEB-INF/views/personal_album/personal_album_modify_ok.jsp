<%@ page language="java" contentType="plain; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.io.File"%>

<% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html; charset=utf-8"); %>

<%
	int flag = (Integer) request.getAttribute("flag");
	String seq = (String) request.getAttribute("seq");
	String redirect = (String) request.getAttribute("redirect");


	JSONObject json = new JSONObject();
	json.put("flag", flag);
	json.put("seq", seq);
	json.put("redirect", redirect);

	out.println(json);
%>
