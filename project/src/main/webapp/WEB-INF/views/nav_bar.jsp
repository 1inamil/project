<!-- nav_bar.jsp 가 main 에 include 되어있어서 language="java" 빼줘야함! -->
<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String authTypeadmin = (String)session.getAttribute("authType");
	//System.out.println(request.getContextPath());
%>
     <!-- ------------------nav start---------------- -->
      <div class="nav_wrap">
        <div class="title">
            <a class="main_logo" href="<%=request.getContextPath() %>/main.do" id="greenpaw"></a>
        </div>
        <div><a href="#" id="login_status"></a></div>
        <ul class="nav_menu">
          <li class="depth1">
            <a href="#">My Page</a>
            <ul class="depth2">
              <li><a href="<%=request.getContextPath() %>/mypage.do">회원정보 관리</a></li>
            </ul>
          </li>
          <li class="depth1">
            <a href="#">With Me</a>
            <ul class="depth2">
              <li><a href="<%=request.getContextPath() %>/personal_album_list.do?">성장 앨범</a></li>
            </ul>
          </li>
          <li class="depth1">
            <a href="#">Community</a>
            <ul class="depth2">
              <li><a href="<%=request.getContextPath() %>/community_main.do?">커뮤니티</a></li>
            </ul>
          </li>
          <li class="depth1">
            <a href="#">Lost Animals</a>
            <ul class="depth2">
              <li>
                <a href="<%=request.getContextPath() %>/lostAnimals.do?">유기동물<br />찾기/입양</a>
              </li>
            </ul>
          </li>
          <%
		//관리자만 버튼
		if(authTypeadmin != null && authTypeadmin.equals("관리자")){
			out.println("<li class='depth1'>");
			out.println("<a href='#'>Admin</a>");
			out.println("<ul class='depth2'>");
			out.println("<li><a href='"+ request.getContextPath() +"/manager_mboard.do'>회원 관리</a></li>");
			out.println("<li><a href='"+ request.getContextPath() +"/manager_board.do'>게시글 관리</a></li>");
			out.println("</ul>");
			out.println("</li>");
		}
		%>
        </ul>
      </div>
      <!-- ------------------nav end---------------- -->