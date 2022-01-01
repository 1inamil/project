<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String cmd = (String) request.getAttribute("cmd");
	String authType = (String) request.getAttribute("authType");
	String category = (String) request.getAttribute("category");
	String[] deleteSeqs = (String[]) request.getAttribute("deleteSeqs");
	String[] privateSeqs = (String[]) request.getAttribute("privateSeqs");

	/* ******* 관리자 상태 확인 *********** */
	if (authType != null && !authType.equals("관리자")) {
		out.println("<script>");
		out.println("alert('관리자만 사용가능합니다.');");
		out.println("location.href='" + request.getContextPath() + "/community_main.do'");
		out.println("</script>");
	}
	/* ******* 삭제 선택 없을 시 *********** */
	if (deleteSeqs == null && cmd.equals("일괄삭제")) {
		out.println("<script>");
		out.println("alert('하나 이상 선택해주세요.');");
		out.println("history.back();");
		out.println("</script>");
	}
	
	/* ******* 공개 선택 없을 시 *********** */
	if (privateSeqs == null && cmd.equals("일괄공개")) {
		out.println("<script>");
		out.println("alert('하나 이상 선택해주세요.');");
		out.println("history.back();");
		out.println("</script>");
	}

	out.println("<script>");
	out.println("location.href='./admin_noticeboard.do?category=" + category + "'");
	out.println("</script>");

%>

