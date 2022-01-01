<%@ page language="java" contentType="text/json; charset=utf-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@page import="org.json.JSONArray"%>
<%@page import="java.time.LocalDate"%>

<%
	JSONArray animalList = (JSONArray) request.getAttribute("animalList");
	out.print(animalList);
%>
