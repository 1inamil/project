<%@page import="com.project.greenpaw.community.common.BoardTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	BoardTO to = (BoardTO) request.getAttribute("to");
	String cpage = (String)request.getAttribute("cpage");

	String nickname = to.getNickname();
	String title = to.getTitle();
	String saleStatus = to.getSaleStatus();
	String content = to.getContent();
	String thumbUrl = to.getThumbUrl();
	String hit = to.getHit();
	String familyMemberType = to.getFamilyMemberType();
	String createdAt = to.getCreatedAt();
	String updatedAt = to.getUpdatedAt();
	String categorySeq = to.getCategorySeq();
	String seq = to.getSeq();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>GreenPaw</title>

<!-- Toast Editor 2.0.0과 의존성 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.4/codemirror.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/editor/2.0.0/toastui-editor.min.css" />

<!-- jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<!-- toastr -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.css" integrity="sha512-3pIirOrwegjM6erE5gPSwkUzO+3cTjpnV9lexlNZqvupR64iZBnOOTiiLPb9M36zpMScbmUNIcHUqKD47M719g==" crossorigin="anonymous" referrerpolicy="no-referrer" />

<!-- sweet alert -->
<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>


<!-- css, font -->
<link rel="stylesheet" href="./css/reset.css" />
<link rel="preconnect" href="https://fonts.googleapis.com" />
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet" />
<link rel="stylesheet" href="./css/menu.css" />
<link rel="stylesheet" href="./css/review_modify.css" />
<script src='./js/menu_logeIn.js' defer></script>

</head>
<body>

	<!-- body 에 넣어줘야 함! -->
	<script src="https://uicdn.toast.com/editor/2.0.0/toastui-editor-all.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js" integrity="sha512-VEd+nq25CkR676O+pLBnDW09R7VQX9Mdiij052gVCp5yVH3jGtH70Ho/UUv4mJDsEdTvqRCFZg0NKGiojGnUCw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	<!--  끝 -->
	
    <div class="main">
      <!-- nav bar include -->
 	 <%@ include file="../../nav_bar.jsp" %>
 	   <div class="content_wrap">
        <div class="center_wrap">
          <div class="view_box_bg">
            <div class="content_outer">
              <div class="content_title">
             		 <select id="sale_Status" class="sale_Status-item">
              			<option <%=saleStatus.equals("제품 후기")? "selected":""%>>제품 후기</option>
              			<option <%=saleStatus.equals("장소 후기")? "selected":""%>>장소 후기</option>
              		 </select>
                <div class="title_outer">
               		 
	                <ul class="type">
	                  <%if(familyMemberType.equals("plant")){ %>
             		   <li class="type_select"><input type="radio" name="type" id="plant" checked value="plant" /><label for="plant">plant</label></li>
            		   <li class="type_select"><input type="radio" name="type" id="pet" value="pet" /><label for="pet">pet</label></li>
               		   <%} else if (familyMemberType.equals("pet")){%>
            		   <li class="type_select"><input type="radio" name="type" id="plant" value="plant" /><label for="plant">plant</label></li>
            		   <li class="type_select"><input type="radio" name="type" id="pet" checked value="pet" /><label for="pet">pet</label></li>
               		 <%} %>
	                  
	                </ul>
	
                	<input class="title_input" id="title" type="text" value="<%=title %>"></input>
              	</div>
                <ul class="other_info">
                	<li>Written by&nbsp;<%= nickname%></li>
                    <li>Created on&nbsp;<%= createdAt%></li>
                    <li>Updated on&nbsp;<%= updatedAt%></li>
                </ul>
              </div>
              <div id="editor" class="viewer">
                <%=content %>
              </div>    
              <input type="hidden" id="seq" name="seq" value="<%=seq %>">
              <input type="hidden" id="cpage" name="cpage" value="<%=cpage %>">
              <input type="hidden" id="type" name="type" value="<%=familyMemberType %>">     
			  <input type="hidden" id="categoryseq" name="categoryseq" value="<%=categorySeq%>">
				 <p class="btn_out">
			      	<button class='btn_save' id='btn_modify' onclick="clickSaveBtn()">Save!</button>
	             </p>            
            </div>
          </div>
        <!-- 우측 메뉴바 -->
        <div class="album_right">
          <ul class="menu">
            <li class="menu_list">
              <a href="#" id="back" onclick="history.back();">back</a>
            </li>
            <li class="menu_list">
              <a href="#" id="delete">delete</a>
            </li>
            <li class="list">
              <a href="./review_board.do?categorySeq=<%=categorySeq%>type=<%=familyMemberType%>&cpage=<%=cpage%>">list</a>
            </li>
            <li class="new">
              <a href="./review_write.do">new</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
    </div>
  </body>
  <!-- toast ui editor JS -->
  <!-- 꼭 editor 사용할 div 보다 아래에 넣어줘야 함 -->
  <script src="./js/review_modify.js" defer></script>
  
  	<!-- delete module -->
	<script type="text/javascript" src="./js/review_delete.js"></script>

  

</html>
 	 