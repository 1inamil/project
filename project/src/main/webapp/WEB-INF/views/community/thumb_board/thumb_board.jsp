<%@page import="com.project.greenpaw.community.common.BoardListTO"%>
<%@page import="com.project.greenpaw.community.common.BoardTO"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%
	BoardListTO listTO = (BoardListTO) request.getAttribute("listTO");

	int totalRecord = listTO.getTotalRecord();
	int totalPage = listTO.getTotalPage();
	
	int blockPerPage = listTO.getBlockPerPage();
	int startBlock = listTO.getStartBlock();
	int endBlock = listTO.getEndBlock();
	
	ArrayList<BoardTO> boardLists = listTO.getBoardLists();

	StringBuilder sbHtml = new StringBuilder();
	
	String seq = "";
	String subTitle = "";
	String saleStatus = "";
	String title = "";
	String familyMemberType = "";
	String content = "";
	String thumbUrl = "";
	String nickname = "";
	String likeCount = "";
	String hit = "";
	String createdAt = "";
	
	int cpage = listTO.getCpage();
	String categorySeq = (String)request.getAttribute("categorySeq");
	String keyword = (String)request.getAttribute("keyword");
	String field = (String)request.getAttribute("field");
	String sort = (String)request.getAttribute("sort");
	
	for( BoardTO to : boardLists ) {
		seq = to.getSeq();
		subTitle = to.getSubTitle();
		saleStatus = to.getSaleStatus();
		title = to.getTitle();
		familyMemberType = to.getFamilyMemberType();
		content = to.getContent();
		thumbUrl = to.getThumbUrl();
		nickname = to.getNickname();
		likeCount = to.getLikeCount();
		hit = to.getHit();
		createdAt = to.getCreatedAt();
		
		sbHtml.append("<li class='item'>");
		sbHtml.append("			<div class='family-type'>");
		sbHtml.append("				<span>"+familyMemberType+"</span>");
		sbHtml.append("			</div>");
		sbHtml.append("				<div class='thumb'><a href='./thumb_view.do?cpage="+cpage+"&seq="+seq+"&type="+familyMemberType+"'>");
		sbHtml.append( "						<img src='" + thumbUrl + "'>" );
		sbHtml.append("				</a></div>");
		sbHtml.append("			<div class='list-title_wrap'>");
		sbHtml.append("				<div class='list-sale_Status'>");
		sbHtml.append("					<a class='sale_Status-item'>"+saleStatus+"</a>");
		sbHtml.append("				</div>");
		sbHtml.append("				<div class='list-title'><a href='./thumb_view.do?cpage="+cpage+"&seq="+seq+"&type="+familyMemberType+"'>");
		sbHtml.append("					<span class='list-title-title'>"+title+"</span>");
		sbHtml.append("					<span class='list-title-content'>"+content+"</span>");
		sbHtml.append("				</a></div>");
		sbHtml.append("			</div>");
		sbHtml.append("			<div class='list-info'>");
		sbHtml.append("				<span class='meta-item view-count' title='?????????'>");
		sbHtml.append("					<span class='stats-count'>????????? "+hit+"</span>");
		sbHtml.append("				</span>");
		sbHtml.append("				<span class='meta-item comment-count' title='??????'>");
		sbHtml.append("					<span class='stats-count'>?????? 10</span>");
		sbHtml.append("				</span>");
		sbHtml.append("			</div>");
		sbHtml.append("			<div class='list-writer'>");
		sbHtml.append("				<div class='writer-info'>");
		sbHtml.append("					<a>" + nickname + "</a>");
		sbHtml.append("					<span>" + createdAt + "</span>");
		sbHtml.append("				</div>");
		sbHtml.append("			 </div>");
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
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="./css/thumbnail_board.css" />
    <link rel="stylesheet" href="./css/menu.css" />
    <!-- ?????????-->
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
	<style>
	/*?????? ???*/
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
	<script src='./js/menu_logeIn.js' defer></script>
</head>
  <body>
  	<main>
    <div class="main">
    	<%@ include file="../../nav_bar.jsp" %>
    <div class="main-menu">
    </div>
    <div class="main-body">
        <!--??????-->
        <div class="main-body-banner">
            <div class="banner" style="cursor:pointer;" onclick="location.href='./thumbnail_board.do?categorySeq=${param.categorySeq}'"></div>
        </div>
        <!-- ????????? -->
        <div class="search_wrap">
            <form action="./thumbnail_board.do" method="get" class="searchform">
                 <input type="hidden" name="categorySeq" value="${param.categorySeq }" />
                 <select name="field" class="selectbox">
                    <option value="title" name="title">??????</option>
					<option value="content" name="content">??????</option>
                </select>
                <input type="text" id="search-input" name="keyword" placeholder="??????"/>
                <input type="submit" value="??????" id="search-btn"/>
            </form>
        </div>

        <!--?????????-->
       
        <div class="inner_content">
                <div class="filter_wrap">
               		<div>
                		<a class="search_filter" href="./thumbnail_board.do?categorySeq=${param.categorySeq}&field=${param.field}&keyword=${param.keyword}&sort=seq">?????? ???</a>

                		<a class="search_filter" href="./thumbnail_board.do?categorySeq=${param.categorySeq}&field=${param.field}&keyword=${param.keyword}&sort=hit">?????? ???</a>
                		
                		<a class="search_filter" href="./thumbnail_board.do?categorySeq=${param.categorySeq}&field=type&keyword=pet">??????</a>
						
						<a class="search_filter" href="./thumbnail_board.do?categorySeq=${param.categorySeq}&field=type&keyword=plant">??????</a>
                	</div>
                </div>
                <div class="list_wrap">
                    <!------ ????????? -------->
                    <section class="list">
                        <ul>
                            <%=sbHtml %>
                        </ul>
                    </section>
                    <a type="button" href="./thumb_write.do?categorySeq=<%=categorySeq %>" id="write-btn">?????????</a>
                <!--?????????-->
                <div class="search_pagination">
                    <nav class="pagination">
                  		<ul>
					<%
						//??????
						if(startBlock == 1){
							out.println("");
						}else{
							out.println("<li class='prev'><a href='?categorySeq="+categorySeq+"&field="+field+"&keyword="+keyword+"&sort="+sort+"&cpage="+(startBlock-blockPerPage)+"'>???</a></li>");

						};
						//??????
						for(int i = startBlock; i <= endBlock; i++){
							if(cpage == i){
								out.println("<li><strong>"+i+"</strong></li>");
							}else{
								out.println("<li><a href='?categorySeq="+categorySeq+"&field="+field+"&keyword="+keyword+"&sort="+sort+"&cpage="+i+"'>"+i+"</a></li>");
							};
						};
												                   
						//??????
						if (endBlock == totalPage) {
							out.println("");
						} else {
							out.println("<li class='next'><a href='?categorySeq="+categorySeq+"&field="+field+"&keyword="+keyword+"&sort="+sort+"&cpage="+ (startBlock + blockPerPage)+"'>???</a></li>");
						};
					%>
						</ul>
                    </nav>
                </div>
                <!--????????? ???-->
            </div>
        </div>
    </div>
</div> 
<!-- ??????-->
    </main>

  </body>
</html>