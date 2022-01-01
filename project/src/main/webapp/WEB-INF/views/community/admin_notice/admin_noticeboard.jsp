<%@page import="com.project.greenpaw.community.common.BoardTO"%>
<%@page import="java.lang.reflect.Array"%>
<%@page import="com.project.greenpaw.community.common.PageTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%
	
	PageTO pages = (PageTO) request.getAttribute("pages");
	String seqList = (String) request.getAttribute("seqList");
	String startDate = (String) request.getAttribute("startDate");
	String endDate = (String) request.getAttribute("endDate");
	String category = (String) request.getAttribute("category");
	String authType = (String) request.getAttribute("authType");
	String field = (String) request.getAttribute("field");
	String sort = (String) request.getAttribute("sort");
	String keyword = (String) request.getAttribute("keyword");
	String subtitle = (String) request.getAttribute("subtitle");

	int recordPerPage = pages.getRecordPerPage();
	int totalRecord = pages.getTotalRecord();
	int totalPage = pages.getTotalPage();
	int blockPerPage = pages.getBlockPerPage();
	int startBlock = pages.getStartBlock();
	int endBlock = pages.getEndBlock();	
	int cPage = pages.getCpage();
	
	ArrayList<BoardTO> result = pages.getPageList();
	
	
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

	StringBuilder sbHtml = new StringBuilder();

	//리스트 생성
	for(BoardTO to : result){
		String seq = to.getSeq();
		String subTitle = to.getSubTitle();
		String saleStatus = to.getSaleStatus();
		String memberType = to.getFamilyMemberType();
		String title = to.getTitle();
		String nickname = to.getNickname();
		String likeCount = to.getLikeCount();
		String hit = to.getHit();
		String createDate = to.getCreatedAt();
		Boolean isPrivate = to.isPrivate();
		
		sbHtml.append("<li class='item'>");
		sbHtml.append("	<div class='family-type'>");
		sbHtml.append("		<span>"+memberType+"</span>");
		sbHtml.append("	</div>");
		sbHtml.append("	<div class='list-title_wrap'>");
		sbHtml.append("		<div class='list-sub_title'>");
		sbHtml.append("			<a class='sub_title-item' name='sub_title-item' href='?category="+category+"&subtitle="+subTitle+"'>"+subTitle+"</a>");
		sbHtml.append("		</div>");
		sbHtml.append("		<div class='list-title'>");
		sbHtml.append("		<a href='./admin_notice_view.do?category="+category+"&seq="+seq+"&page="+cPage+"' class='title-item'>"+title+"</a>");
		sbHtml.append("		</div>");
		sbHtml.append("	</div>");
		sbHtml.append("	<div class='list-info'>");
		sbHtml.append("		<span class='meta-item view-count' title='조회수: "+hit+"회'>");
		sbHtml.append("			<span class='stats-count'>조회수 "+hit+"</span>");
		sbHtml.append("		</span>");
		sbHtml.append("		<span class='meta-item comment-count' title='댓글: 10개'>");
		sbHtml.append("			<span class='stats-count'>댓글 0</span>");
		sbHtml.append("		</span>");
		sbHtml.append("	</div>");
		sbHtml.append("	<div class='list-writer'>");
		sbHtml.append("		<img class='writer_photo' src=''>");
		sbHtml.append("		<div class='writer-info'>");
		sbHtml.append("			<a>"+nickname+"</a>");
		sbHtml.append("			<span>"+createDate+"</span>");
		sbHtml.append("		</div>");
		sbHtml.append("	</div>");
		sbHtml.append("	<div class='batch-select'>");
		sbHtml.append("		<input type='checkbox' name='private'");
		if(isPrivate == true){
			sbHtml.append("value='"+seq+"'>공개</input><br>");
		}
		if(isPrivate == false){
			sbHtml.append("checked value='"+seq+"'>공개</input><br>");
		}
		
		sbHtml.append("		<input type='checkbox' name='delete' value='"+seq+"'>삭제</input>");
		sbHtml.append("	</div>");
		sbHtml.append("</li>");
	}
	
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
<script src='./js/menu_logeIn.js' defer></script>
	
<% 
if(category.equals("1")){
    out.println("<link rel='stylesheet' href='./css/freetalk_board.css' />");
}

if(category.equals("7")){
    out.println("<link rel='stylesheet' href='./css/notice_board.css' />");
}

if(category.equals("6")){
    out.println("<link rel='stylesheet' href='./css/QnA_board.css' />");
}
%>

<link rel="stylesheet" href="./css/menu.css" />

<%
	if(authType != null && !authType.equals("관리자")){
		out.println("<script>");
		out.println("alert('관리자만 사용가능합니다.')");
		out.println("location.href='"+request.getContextPath()+"/normal_board.do?category=7'");
		out.println("</script>");
	}
%>
<!-- 아이콘-->
<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
	
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
			<%@ include file="../../nav_bar.jsp" %>
			<div class="main-menu">
			</div>
			<div class="main-body">
				<!--배너-->
				<div class="main-body-banner">
					<div class="banner" style="cursor:pointer;" onclick="location.href='./admin_noticeboard.do?category=7'"></div>
				</div>
				<!-- 검색창 -->
				<div class="search_wrap">
					<form action="./admin_noticeboard.do" method="get" class="searchform">
						<input type="hidden" name="category" value="${param.category }" />
						<select name="field" class="selectbox">
							<option value="title" name="title">제목</option>
							<option value="content" name="content">내용</option>
						</select>
						<input type="text" id="search-input" name="keyword" placeholder="검색" />
						<input type="submit" value="검색" id="search-btn" />
					</form>
				</div>
				
				<!-- 날짜별 검색 -->
				<div class="dateSearch">
					<form action="./admin_noticeboard.do" method="get">
						<input type="hidden" name="category" value="${param.category }" />
						<input type="date" name="startDate" id="startDate" value="${ param.startDate}"/>
						~
						<input type="date" name="endDate" id="endDate" value="${param.endDate }"/>
						<input type="submit" value="검색" id="date-btn" />
					</form>
				</div>

				<!--테이블-->

				<div class="inner_content">
					<div class="filter_wrap">
						<div>
							<a class="search_filter" href="./admin_noticeboard.do?category=7&field=${param.field }&keyword=${param.keyword}&sort=seq">최근 순</a>
							<!-- <span></span> -->
							<a class="search_filter" href="./admin_noticeboard.do?category=7&field=${param.field }&keyword=${param.keyword}&sort=hit">인기 순</a>
						
							<a class="search_filter" href="./admin_noticeboard.do?category=7&field=type&keyword=pet">동물</a>
							<!-- <span></span> -->
							<a class="search_filter" href="./admin_noticeboard.do?category=7&field=type&keyword=plant">식물</a>
						</div>
					</div>
					<!------ 리스트 -------->
					<div class="list_wrap">
						<!-- 추가 -->
						<form action="./admin_notice_privateDelete.do" method="post">
							<div class="list">
								<ul>
									<%=sbHtml %>
								</ul>
							</div>
							
							<!--일괄공개삭제 전송버튼-->
	                        <div class="batch-btns">
	                        	<input type="hidden" name="category" value="${param.category }"/>
	                            <input type="hidden" name="seqlist" value="<%=seqList %>"/>
	                            <input type="submit" class="batch-btn" name="cmd" value="일괄공개" />
	                            <input type="submit" class="batch-btn" name="cmd" value="일괄삭제" />
	                        </div>
						</form>
						
						<%
						//공지사항게시판 => 로그인 + 관리자일때만 글쓰기 버튼이 뜨도록
						if(session.getAttribute("mail") != null){
							if(category != null){
								if(category.equals("7")){
									if(session.getAttribute("authType").equals("관리자")){
										out.println("<a type='button' href='./admin_notice_write.do?category="+category+"' id='write-btn'>글쓰기</a>");
									}
								}
								if(!category.equals("7")){
									out.println("<a type='button' href='./admin_notice_write.do?category="+category+"' id='write-btn'>글쓰기</a>");
						
								}
							}
						}
						%>
						
						<!--페이지-->
						<div class="search_pagination">
							<nav class="pagination">
								<ul>
									<%
									//이전
									if(startBlock == 1){
										out.println("");
									}else{
										out.println("<li class='prev'><a href='?category="+category+"&field="+field+"&keyword="+keyword+"&sort="+sort+"&subtitle="+subtitle+"&page="+(startBlock-blockPerPage)+"'>◀</a></li>");

									};
									//숫자
									for(int i = startBlock; i <= endBlock; i++){
										if(cPage == i){
											out.println("<li><strong>"+i+"</strong></li>");
										}else{
											out.println("<li><a href='?category="+category+"&field="+field+"&keyword="+keyword+"&sort="+sort+"&subtitle="+subtitle+"&startDate="+startDate+"&endDate="+endDate+"&page="+i+"'>"+i+"</a></li>");
										};
									};
												                   
									//다음
									if (endBlock == totalPage) {
										out.println("");
									} else {
										out.println("<li class='next'><a href='?category="+category+"&field="+field+"&keyword="+keyword+"&sort="+sort+"&subtitle"+subtitle+"&startDate="+startDate+"&endDate="+endDate+"&page="+ (startBlock + blockPerPage)+"'>▶</a></li>");
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
<script src="./js/notice_board.js"></script>
</body>
</html>