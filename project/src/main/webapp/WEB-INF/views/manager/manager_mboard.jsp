<%@page import="com.project.greenpaw.community.common.BoardListTO"%>
<%@page import="com.project.greenpaw.manager.ManagerTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String authTypead = (String) request.getAttribute("authTypead");
	ArrayList<ManagerTO> userLists = (ArrayList<ManagerTO>) request.getAttribute("userLists");
	BoardListTO listTO = (BoardListTO) request.getAttribute("listTO");
	int countlist = (Integer)request.getAttribute("countlist");
	String startDate = (String)request.getAttribute("startDate");
	String endDate = (String)request.getAttribute("endDate");
	String keyword = (String)request.getAttribute("keyword");
	String field = (String)request.getAttribute("field");

	StringBuilder sbHtml = new StringBuilder();
	
	if(!startDate.equals("") && endDate.equals("")){
		out.println("<script>");
		out.println("alert('날짜를 다시 입력해주세요')");
		out.println("history.back()");
		out.println("</script>");
	}
	
	if(startDate.equals("") && !endDate.equals("")){
		out.println("<script>");
		out.println("alert('날짜를 다시 입력해주세요')");
		out.println("history.back()");
		out.println("</script>");
	}
	
		/* for문 밖에서 선언하는 게 메모리 차지가 덜 한다 */
		String nickname = "";
		String mail = "";
		String createdAt = "";
		String authType="";
		String count = ""; 
	
		for( ManagerTO to : userLists ) {
			nickname = to.getNickname();
			mail = to.getMail();
			createdAt = to.getCreatedAt();
			authType = to.getAuthType();
			count = to.getCount();
			
			sbHtml.append("<li class='item'>");
			sbHtml.append("	<div class='select_box'>");
			sbHtml.append("		<span><input type='checkbox' name='selectcheck' value='"+nickname+"'/></span>");
			sbHtml.append("	</div>");
			//sbHtml.append("	<div class='list-wrap'>");
			sbHtml.append("		<div class='list-nickname'>");
			sbHtml.append("			<span class='nickname'>"+nickname+"</span>");
			sbHtml.append("		</div>");
			sbHtml.append("		<div class='list-mail'>");
			sbHtml.append("			<span class='mail'>"+mail+"</span>");
			sbHtml.append("		</div>");
			sbHtml.append("		<div class='list-createdAt'>");
			sbHtml.append("			<span class='createat'>"+createdAt+"</span>");
			sbHtml.append("		</div>");
			sbHtml.append("		<div class='list-authType'>");
			sbHtml.append("			<span class='authType'>"+authType+"</span>");
			sbHtml.append("		</div>");
			sbHtml.append("		<div class='list-count'>");
			sbHtml.append("			<span class='count'>"+count+"</span>");
			sbHtml.append("		</div>");
			//sbHtml.append("	</div>");
			sbHtml.append("</li>");
		}
		
	int totalRecord = listTO.getTotalRecord();
	int totalPage = listTO.getTotalPage();
	int blockPerPage = listTO.getBlockPerPage();
	int startBlock = listTO.getStartBlock();
	int endBlock = listTO.getEndBlock();
	int cpage = listTO.getCpage();
	
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
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
	rel="stylesheet" />
  <!-- jquery cdn -->
    <script
      src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
      crossorigin="anonymous"></script>
<link rel="stylesheet" href="./css/manager.css" />
<link rel="stylesheet" href="./css/menu.css" />
<script src='./js/menu_logeIn.js' defer></script>
<%
	if (authTypead != null && !authTypead.equals("관리자")) {
		out.println("<script>");
		out.println("alert('관리자만 사용가능합니다.')");
		out.println("location.href='" + request.getContextPath() + "/main.do'");
		out.println("</script>");
	}
%>
<!-- 아이콘-->
<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
<script src='./js/manager_mboard.js' defer></script>
<style>
	/*필터 색*/
	.filter_wrap a:link {
	color:#26a69a;
	}

	.filter_wrap a:visited {
	  color:#26a69a;
	}
	
	.filter_wrap a:active {
	  color: #00766c;
	  font-weight: bolder;
	}
	
	.filter_wrap a:hover {
	  color:#00766c;
	  font-weight: bolder;
	}
	
		

</style>

</head>
<body>
	<main>
		<div class="main">
			<%@ include file="../nav_bar.jsp" %>
			<div class="main-menu">
			</div>
			<div class="main-body">
				<!-- 검색창 -->
				<div class="search_wrap">
					<form action="./manager_mboard.do" method="get" class="searchform">
						<select name="field" class="selectbox">
							<option value="u.nickname" name="nickname">닉네임</option>
							<option value="u.mail" name="mail">메일</option>
						</select>
						<input type="text" id="search-input" name="keyword" placeholder="검색" />
						<input type="submit" value="검색" id="search-btn" />
					</form>
				</div>
				<!-- 날짜 검색창 -->
				<div class ="dateSearch">
					<form action="./manager_mboard.do" method="get">
						<input type="date" name="startDate" id="startDate" value="${param.startDate}"/>
						~
						<input type="date" name="endDate" id="endDate" value="${param.endDate}"/>
						<input type="submit" value="검색" id="date-btn" />
					</form>
				</div>
				<!-- 테이블 -->

				<div class="inner_content">
					<span>회원 관리</span>
					<p>[총 회원수 <%=countlist%>명] 검색결과 <%=totalRecord%>건</p>
					<div class="filter_wrap">
						<div class="filter_item">
							<div class="getcheckbox">
								<input type="checkbox" name="selectcheckall" value="selectall" onclick="selectAll(this)"/>
							</div>
							<div class="getnickname">
								<span>닉네임</span>
							</div>
							<div class="getmail">
								<span>이메일</span>
							</div>
							<div class="getcreatat">
								<span>가입일자</span>
							</div>
							<div class="getautytype">
								<span>구분</span>
							</div>
							<div class="getcount">
								<span>게시글수</span>
							</div>
							
							<!-- 나중에 댓글 부분 추가하기 -->
							
						</div>
					</div>
					<!------ 리스트 -------->
					<div class="list_wrap">
						<section class="list">
							<ul>
								<%=sbHtml %>
							</ul>
						</section>
						<select name="statusOption" class="selectbox">
							<option value="탈퇴" name="status">탈퇴</option>
							<option value="차단" name="status">차단</option>
							<option value="회원" name="status">회원</option>
							<option value="관리자" name="status">관리자</option>
						</select>
						<a href="#" id="change" class="change" onclick="changeClick()">변경</a>
						
						<!--페이지-->
						<div class="search_pagination">
							<nav class="pagination">
								<ul>
						<%
						//이전
						if(startBlock == 1){
							out.println("");
						}else{
							out.println("<li class='prev'><a href='?field="+field+"&keyword="+keyword+"&cpage="+(startBlock-blockPerPage)+"&startDate="+startDate+"&endDate="+endDate+"'>◀</a></li>");

						};
						//숫자
						for(int i = startBlock; i <= endBlock; i++){
							if(cpage == i){
								out.println("<li><strong>"+i+"</strong></li>");
							}else{
								out.println("<li><a href='?field="+field+"&keyword="+keyword+"&cpage="+i+"&startDate="+startDate+"&endDate="+endDate+"'>"+i+"</a></li>");
							};
						};
												                   
						//다음
						if (endBlock == totalPage) {
							out.println("");
						} else {
							out.println("<li class='next'><a href='?field="+field+"&keyword="+keyword+"&cpage="+ (startBlock + blockPerPage)+"&startDate="+startDate+"&endDate="+endDate+"'>▶</a></li>");
						};
					%>
								</ul>
							</nav>
						</div>
						<!--페이지 끝-->
					</div>
				</div>
			</div>
		</div>
		<!-- 메인-->
	</main>

</body>
</html>