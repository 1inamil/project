<%@page import="java.io.File"%>
<%@ page language="java" contentType="plain; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>

    
<%

	String url =(String) request.getAttribute("url");
	
	JSONObject json = new JSONObject();
	json.put("url", url);
	
	out.println(json);

%>
