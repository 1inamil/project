<%@page import="com.project.greenpaw.community.common.BoardTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.project.greenpaw.community.common.PageTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	PageTO pages = (PageTO) request.getAttribute("pages");
	ArrayList<BoardTO> result = (ArrayList<BoardTO>)request.getAttribute("result");
	String startDate = (String)request.getAttribute("startDate");
	String endDate = (String)request.getAttribute("endDate");
	String authType = (String)request.getAttribute("authType");
	String keyword = (String)request.getAttribute("keyword");
	String category = (String)request.getAttribute("category");
	String searchType = (String)request.getAttribute("searchType");
	
	StringBuilder sbHtml = new StringBuilder();
	
	int recordPerPage = pages.getRecordPerPage();
	int totalRecord = pages.getTotalRecord();
	int totalPage = pages.getTotalPage();
	int blockPerPage = pages.getBlockPerPage();
	int startBlock = pages.getStartBlock();
	int endBlock = pages.getEndBlock();
	int cPage = pages.getCpage();
	
	if (!startDate.equals("") && endDate.equals("")) {
		out.println("<script>");
		out.println("alert('날짜를 다시 입력해주세요')");
		out.println("history.back()");
		out.println("</script>");
	}
	
	if (startDate.equals("") && !endDate.equals("")) {
		out.println("<script>");
		out.println("alert('날짜를 다시 입력해주세요')");
		out.println("history.back()");
		out.println("</script>");
	}
	
	//리스트 생성
	for (BoardTO to : result) {
		String seq = to.getSeq();
		String categoryName = to.getCategorySeq();
		String title = to.getTitle();
		String nickname = to.getNickname();
		String createdAt = to.getCreatedAt();
		String hit = to.getHit();
		String isPrivate = to.isPrivate() ? "비공개" : "공개";
	
		switch (categoryName) {
		case "1":
			categoryName = "자유게시판";
			break;
		case "2":
			categoryName = "성장앨범";
			break;
		case "3":
			categoryName = "아이자랑";
			break;
		case "4":
			categoryName = "후기";
			break;
		case "5":
			categoryName = "나눔거래";
			break;
		case "6":
			categoryName = "질문과 답변";
			break;
		case "7":
			categoryName = "공지사항";
			break;
		}
	
		// 테이블 상단
		sbHtml.append("<li class='item'>");
		sbHtml.append("    <div class='list-content' id='list_" + seq + "'>");
		sbHtml.append("       <input type='checkbox' name='board_seq' class='select' value='" + seq + "' />");
		sbHtml.append("       <div class='seq'>" + seq + "</div>");
		sbHtml.append("       <div class='category'>" + categoryName + "</div>");
		sbHtml.append("       <div class='postTitle'>" + title + "</div>");
		sbHtml.append("       <div class='nickname'>" + nickname + "</div>");
		sbHtml.append("       <div class='createdAt'>" + createdAt + "</div>");
		sbHtml.append("       <div class='hit'>" + hit + "</div>");
		sbHtml.append("       <div class='isPrivate'>" + isPrivate + "</div>");
		sbHtml.append("       <div><button class='btn_other' onclick='viewPost(" + seq + ")'>글보기</button></div>");
		sbHtml.append("    </div>");
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

<link rel="stylesheet" href="./css/menu.css" />
<link rel='stylesheet' href='./css/manager_board.css' />
<script src='./js/menu_logeIn.js' defer></script>
<%
	if (authType != null && !authType.equals("관리자")) {
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
<!-- jquery & ui -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>

<script src='./js/manager_board.js' defer></script>

<style>
/*필터 색*/
.filter_wrap a:link {
	color: #26a69a;
}

.filter_wrap a:visited {
	color: #26a69a;
}

.filter_wrap a:active {
	color: #00766c;
	font-weight: bolder;
}

.filter_wrap a:hover {
	color: #00766c;
	font-weight: bolder;
}
</style>

</head>
<body>
	<main>
		<div class="main">
			<%@ include file="../nav_bar.jsp"%>
			<div class="main-menu"></div>
			<div class="main-body">
				<!-- 검색창 -->
				<div class="search_wrap">
					<form action="./manager_board.do" method="get" class="searchform">
						<!-- 카테고리 선택 -->
						<select name="category" class="selectbox">
							<option value="" name="" selected>카테고리</option>
							<!-- <option value="7" name="notice">공지사항</option> -->
							<option value="5" name="fleaMarket">나눔 / 거래</option>
							<option value="1" name="freeTalk">자유게시판</option>
							<option value="3" name="coletPeopleKnowMyBabyntent">아이자랑</option>
							<option value="4" name="review">후기 공유</option>
							<option value="6" name="QnA">Q & A</option>
						</select>
						<!-- 검색 타입 선택 -->
						<select name="searchType" class="selectbox">
							<option value="" name="" selected>전체</option>
							<option value="title" name="title">제목</option>
							<option value="content" name="content">내용</option>
						</select>
						<!-- 검색 폼 -->
						<input type="text" id="search-input" name="keyword"
							placeholder="검색" /> <input type="submit" value="검색"
							id="search-btn" />
					</form>
				</div>
				<!-- 날짜 검색창 -->
				<div class="date_wrap">
					<form action="./manager_board.do" method="get">
						<input type="hidden" name="category" value="${param.category }" />
						<input type="date" name="startDate" id="startDate"
							value="${param.startDate}" /> ~ <input type="date"
							name="endDate" id="endDate" value="${param.endDate}" /> <input
							type="submit" value="검색" id="date-btn" />
					</form>
				</div>

				<!--테이블-->

				<div class="inner_content">
					<div class="manage_type">
						<sapn>게시글 관리</sapn>
						<br />
						<sapn>검색결과 <%=totalRecord%>건</sapn>
						<br />
					</div>
					<div class="filter_wrap">
						<input type="checkbox" id="all_checked_filter" /> <span>번호</span>
						<span>카테고리</span> <span>제목</span> <span>작성자</span> <span>작성일</span>
						<span>조회수</span> <span>차단여부</span> <span>글보기</span>
					</div>
					<!------ 리스트 -------->
					<div class="list_wrap">
						<section class="list">
							<ul>
								<%=sbHtml%>
							</ul>
						</section>
						<button class="btn_other" id="removeBtn">삭제</button>
						<button class="btn_other" id="hiddenBtn">차단 설정 / 해제</button>
						<!--페이지-->
						<div class="search_pagination">
							<nav class="pagination">
								<ul>
									<%
									//이전
									if (startBlock == 1) {
										out.println("");
									} else {
										out.println("<li class='prev'><a href='?category=" + category + "&searchType=" + searchType + "&keyword=" + keyword
										+ "&page=" + (startBlock - blockPerPage) + "'>◀</a></li>");
									};
									//숫자
									for (int i = startBlock; i <= endBlock; i++) {
										if (cPage == i) {
											out.println("<li><strong>" + i + "</strong></li>");
										} else {
											out.println("<li><a href='?category=" + category + "&searchType=" + searchType + "&keyword=" + keyword
											+ "&page=" + i + "'>" + i + "</a></li>");
										};
									};

									//다음
									if (endBlock == totalPage) {
										out.println("");
									} else {
										out.println("<li class='next'><a href='?category=" + category + "&searchType=" + searchType + "&keyword=" + keyword
										+ "&page=" + (startBlock + blockPerPage) + "'>▶</a></li>");
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
