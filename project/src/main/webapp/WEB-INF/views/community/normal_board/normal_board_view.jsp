<%@page import="com.project.greenpaw.community.common.BoardTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	BoardTO to = (BoardTO) request.getAttribute("to");
	String sessionNickname = (String) request.getAttribute("sessionNickname");
	String category = (String) request.getAttribute("category");
	String authType = (String) request.getAttribute("authType");
	int isSeq = (Integer) request.getAttribute("isSeq");
	
	String subTitle = to.getSubTitle();
	String title =to.getTitle();
	String nickname= to.getNickname();
	String createDate =to.getCreatedAt();
	String hit = to.getHit();
	String likeCount = to.getLikeCount();
	String content = to.getContent();
	int comment = 0;
	
%>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>GreenPaw</title>
<link rel="stylesheet" href="./css/reset.css" />
<link rel="preconnect" href="https://fonts.googleapis.com" />
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
<script
  src="https://code.jquery.com/jquery-3.6.0.js"
  integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
  crossorigin="anonymous"></script>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet" />
<!--h1 나눔명조 사용-->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/earlyaccess/nanummyeongjo.css"/>
<% 
		if(category.equals("1")){
			out.println("<link rel='stylesheet' href='./css/normal_board_view.css' />");
		}
		
		if(category.equals("6")){
			out.println("<link rel='stylesheet' href='./css/QnA_view.css' />");
		}
		
		if(category.equals("7")){
			out.println("<link rel='stylesheet' href='./css/notice_view.css' />");
		}
%>	
	

<link rel="stylesheet" href="./css/menu.css" />
<script src='./js/menu_logeIn.js' defer></script>
<%
	if(isSeq == 0){
		out.println("<script>");
		out.println("alert('삭제된 게시글입니다.')");
		out.println("location.href='./normal_board.do?category="+category+"'");
		out.println("</script>");
	}
%>

</head>
<body>
	<main>
		<div class="main">
		<%@ include file="../../nav_bar.jsp" %>
			<div class="main-menu">
			<input type="hidden" id="seq" name="seq" value="${param.seq }"/>
			<input type="hidden" id="page" name="page" value="${param.page }"/>
			</div>
			<div class="main-body">
				<!--데이터 넣기-->
                <div class="view-title">
                <%-- <a>[<%= categoryName%>]</a><br/> --%>
                <input type="hidden" id="category" name="category" value="${param.category }"/>
                
                  <p class="sub_title"><%=subTitle %></p>
                    <h1><%=title %></h1>
                    <div class="author">
                      <div class="autor-info">
                      	<%if(category.equals("7")){ %>
                        <span class="by">by </span><a class="nickname" id="nickname" value="관리자">관리자</a>
                        <%} else{%>
                         <span class="by">by </span><a class="nickname" id="nickname" value="<%=nickname %>"><%=nickname %></a>
                        <%} %>
                        <span class="space"></span>
                        <span class="date"><%=createDate %></span>
                      </div>
                      <div class="content-info">
                        <span>조회수 <%=hit %></span>
                       <%--  <span>좋아요 <%=likeCount %></span> --%>
                        <span>댓글 0</span>
                        
                      </div>
                    </div>

                </div>
                <div id="content" class="view-content">
                  <%=content %>
                </div>
                <!-- 댓글 -->
                <%if(category !=null && category.equals("6") ){ %>
                <!-- ajax로 보내기.. -->
                <div class="comment-wrap" id="comment-wrap">
	                	<textarea class="comment" name="comment" id="comment"></textarea>
	                	<input class="commentBtn" type="submit" value="댓글쓰기"/>
                </div>
                <%} %>
			</div>
      <div class="go-link">
        <ul class="link-menu">
          <li><a href="./normal_board.do?category=${param.category }&page=${param.page}">-List</a></li>
          
          <%if(nickname != null && nickname.equals(sessionNickname)){%>
          <li><a href="./subTitle_modify.do?category=${param.category }&page=${param.page}&seq=${param.seq }">-Modify</a></li>
          <li><a href="#" id="delete" >-Delete</a></li>
          <% }%>
          
        </ul>
      </div>
		</div>
		<!-- 메인-->
	</main>
	<script type="text/javascript" src="./js/delete_normal_view.js"></script>
</body>
</html>