<%@page import="com.project.greenpaw.albumboard.AlbumListTO"%>
<%@page import="com.project.greenpaw.albumboard.AlbumTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
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
		//String type = to.getFamilyMemberType();

		sbHtml.append("<li class='album_content'>");
        sbHtml.append("<a href='/greenpaw/community_album_view.do?cpage="+cpage+"&seq="+seq+"&type="+type+"'>");
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
    <link rel="stylesheet" href="./css/reset.css" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="./css/menu.css" />
    <link rel="stylesheet" href="./css/community_album.css" />
    <script src="./js/personal_album.js" defer></script>
	<script src='./js/menu_logeIn.js' defer></script>

  </head>
  <body>
    <div class="main">
    <!-- nav bar include -->
 	 <%@ include file="../../nav_bar.jsp" %>
      <div class="content_wrap">
        <div class="center_wrap">
          <div class="banner"></div>
          <ul class="album_wrap">
        	<!-- 게시물 -->	
 			 <%= sbHtml %>
        	<!-- /게시물 -->
          </ul>
	       	<!-- paging -->
	        <div class="paging">
<%
	       		//forward
	       		if(startBlock == 1){
	       			out.println("<span class='forward'><a>&lt;&lt;</a></span>");
	       		}else{
	       			out.println("<span class='forward'><a href='/greenpaw/community_album_list.do?cpage="
	       						+ (startBlock-blockPerPage) +"&type="+type+"'>&lt;&lt;</a></span>");
	       			
	       		};
	       	
	       		//숫자
	       		for(int i = startBlock; i <= endBlock; i++){
	       			if(cpage == i){
	       				out.println("<span class='numbers'><a style='color:tomato'>"+i+"</a></span>");       			
	       			}else{
		       			out.println("<span class='numbers'><a href='/greenpaw/community_album_list.do?cpage="+i+"&type="+type+"'>"
	       						+ i +"</a></span>");   				
	       			};
	       		};
	       		
	       		if(endBlock == totalPages){
	       			out.println("<span class='backward'><a>&gt;&gt;</a></span>");       			
	       		}else{
	       			out.println("<span class='backward'><a href='/greenpaw/community_album_list.do?cpage="
	       						+ (startBlock + blockPerPage)+"&type="+type+"'>&gt;&gt;</a></span>");       			
	       		};
	
%>
	        </div>
          </div>
        <!-- 우측 메뉴바 -->
        <div class="album_right">
          <ul class="menu">
            <li class="menu_list">
              <a href="/greenpaw/community_album_list.do" id="all">all</a>
            </li>
            <li class="menu_list">
              <a href="/greenpaw/community_album_list.do?type=plant" id="plantList">plant</a>
            </li>
            <li class="menu_list">
              <a href="/greenpaw/community_album_list.do?type=pet" id="petList">pet</a>
            </li>
            <li class="new">
              <a href="/greenpaw/community_album_write.do?cpage=<%=cpage %>">new</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </body>
</html>
