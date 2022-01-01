<%@page import="com.project.greenpaw.albumboard.AlbumListTO"%>
<%@page import="com.project.greenpaw.albumboard.AlbumTO"%>
<%@ page language="Java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>

<%
	AlbumListTO listTO = (AlbumListTO) request.getAttribute("listTO");
	StringBuffer sbHtml = new StringBuffer();
	
	int cpage = listTO.getCpage();
	int totalRecord = listTO.getTotalRecord();
	int totalPages = listTO.getTotalPages();
	int recordPerPage = listTO.getRecordPerPage();
	int startBlock = listTO.getStartBlock();
	int endBlock = listTO.getEndBlock();
	int blockPerPage = listTO.getBlockPerPage();
	String type = listTO.getFamilyMemberType();
	ArrayList<AlbumTO> lists = listTO.getBoardLists();
	
	for(int i = 0; i<lists.size(); i++){
		AlbumTO to = new AlbumTO();
		to = lists.get(i);
		
		String nickname = to.getNickname();
		String seq = to.getSeq();
		String title = to.getTitle();
		String content = to.getContent();
		String thumbUrl = to.getThumbUrl();
		String familyMember = to.getFamilyMemberType();
		Boolean isPrivate = to.getIsPrivate();
		String wDate = to.getCreatedAt();
		String mDate = to.getUpdatedAt();
		int hit = to.getHit();
		
	    sbHtml.append("<li class='album_content'>");
	    sbHtml.append("<a href='./community_album_view.do?cpage="+cpage+"&seq="+seq+"&type="+type+"'>");
	    sbHtml.append("<div class='a_photo' style=\"background-image:url('"+ thumbUrl + "' )\";></div>");
	    sbHtml.append("<div class='a_text_box'>");
	    sbHtml.append("<p class='a_wdate'>"+wDate+"</p>");
	    sbHtml.append("<p class='a_title'>"+title+"</p>");
	    sbHtml.append("<p class='a_writer'>by. "+nickname+" | view "+ hit +"</p>");
	    sbHtml.append("</div>");
	    sbHtml.append("</a>");
	    sbHtml.append("</li>");
	
	}

%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>GreenPaw</title>
<!-- jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<!-- swiper cdn : 상단 배너 만드는 라이브러리 시작 -->
<link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css"/>
<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
<!-- swiper cdn : 상단 배너 만드는 라이브러리 끝 -->
<link rel="stylesheet" href="./css/reset.css" />
<link rel="preconnect" href="https://fonts.googleapis.com" />
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet" />

<link rel="stylesheet" href="./css/menu.css" />
<link rel="stylesheet" href="./css/community_main.css" />

<script src='./js/community_main.js' defer></script>
<script src='./js/menu_logeIn.js' defer></script>

</head>
<body>
    <div class="main">
      <!-- nav bar include -->
 	 <%@ include file="../nav_bar.jsp" %>
      <div class="content_wrap">
        <div class="center_wrap">
          <div class="main_box_bg">
            <div class="content_outer">
              <div class="main_body">
              	<div class="swiper">
              		<div class="swiper-wrapper">
						<div class="swiper-slide carousel" style="background-image: url('./images/notice.png');" onclick="location.href='<%=request.getContextPath() %>/normal_board.do?category=7'"><a></a></div>
						<div class="swiper-slide carousel" style="background-image: url('./images/fleaMarket.png');" onclick="location.href='<%=request.getContextPath() %>/thumbnail_board.do?categorySeq=5'"></div>
						<div class="swiper-slide carousel" style="background-image: url('./images/freeTalk.png');" onclick="location.href='<%=request.getContextPath() %>/normal_board.do?category=1'"><a></a></div>
						<div class="swiper-slide carousel" style="background-image: url('./images/letPeopleKnowMyBaby.png');"  onclick="location.href='<%=request.getContextPath() %>/community_album_list.do?catSeq=3'"></div>
						<div class="swiper-slide carousel" style="background-image: url('./images/review.png');" onclick="location.href='<%=request.getContextPath() %>/review_board.do?categorySeq=4'"><a></a></div>
						<div class="swiper-slide carousel" style="background-image: url('./images/QnA.png');" onclick="location.href='<%=request.getContextPath() %>/normal_board.do?category=6'"><a></a></div>
              		</div>
              		<div class="swiper-pagination"></div>
              		<div class="swiper-button-next"></div>
      				<div class="swiper-button-prev"></div>
              	</div>
				<ul class="category">
				<%if(session.getAttribute("authType") != null && session.getAttribute("authType").equals("관리자")){%>
					<li id="category1" onclick="location.href='<%=request.getContextPath() %>/admin_noticeboard.do?category=7'"><a>공지사항</a></li>
				<%}else{ %>
					<li id="category1" onclick="location.href='<%=request.getContextPath() %>/normal_board.do?category=7'"><a>공지사항</a></li>
					<%} %>
					<li id="category2" onclick="location.href='<%=request.getContextPath() %>/thumbnail_board.do?categorySeq=5'" ><a>나눔 / 거래</a></li>
					<li id="category3" onclick="location.href='<%=request.getContextPath() %>/normal_board.do?category=1'"><a>자유게시판</a></li>
					<li id="category4" onclick="location.href='<%=request.getContextPath() %>/community_album_list.do?catSeq=3'"><a>아이자랑</a></li>
					<li id="category5" onclick="location.href='<%=request.getContextPath() %>/review_board.do?categorySeq=4'"><a>후기 공유</a></li>
					<li id="category6" onclick="location.href='<%=request.getContextPath() %>/normal_board.do?category=6'" ><a>Q & A</a></li>
				</ul>
				<p class="t1">🐾 아이자랑 게시판 <a href="<%=request.getContextPath() %>/community_album_list.do?catSeq=3'" class="t2">더보기</a></p>
              	<ul class="album_wrap">
					<%=sbHtml %>
				</ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
