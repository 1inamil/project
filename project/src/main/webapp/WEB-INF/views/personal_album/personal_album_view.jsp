<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	Boolean isPrivate = (Boolean) request.getAttribute("isPrivate");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>GreenPaw</title>

<!-- toast ui viewer   -->
  <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor-viewer.min.css" />

<!-- Toast Editor 2.0.0과 의존성
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.4/codemirror.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/editor/2.0.0/toastui-editor.min.css" /> -->


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
<link rel="stylesheet" href="./css/personal_album_view.css" />

<script src='./js/menu_logeIn.js' defer></script>

</head>
<body>

<!-- body 에 넣어줘야 함! 
	<script src="https://uicdn.toast.com/editor/2.0.0/toastui-editor-all.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js" integrity="sha512-VEd+nq25CkR676O+pLBnDW09R7VQX9Mdiij052gVCp5yVH3jGtH70Ho/UUv4mJDsEdTvqRCFZg0NKGiojGnUCw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
-->


<!-- toast viewer cdn -->
	 <script src="https://uicdn.toast.com/editor/latest/toastui-editor-viewer.js"></script>

	<%-- 여기는 personal_album_view.jsp?seq=<%=seq %> --%>
    <div class="main">
      <!-- nav bar include -->
 	 <%@ include file="../nav_bar.jsp" %>
 	       <div class="content_wrap">
        <div class="center_wrap">
          <div class="view_box_bg">
            <div class="content_outer">
              <div class="content_title">
              	<div>
                	<p>${title}</p>
                	<%
                		if(isPrivate == false){                			
		              		out.print("<span class='public'>✔ 커뮤니티에도 공개하신 글입니다.</span>");
                		}
                	%>
               	</div>
                <ul class="other_info">
                	<li>Written by&nbsp;${nickname}</li>
                    <li>Created on&nbsp;${wDate}</li>
                    <li>Updated on&nbsp;${mDate}</li>
                </ul>
              </div>
              <div id="viewer" class="viewer">
               ${content}
              </div>            
            	<input type="hidden" id="seq" name="seq" value="${seq}">
            </div>
          </div>
        <!-- 우측 메뉴바 -->
        <div class="album_right">
          <ul class="menu">
            <li class="menu_list">
              <a href="/greenpaw/personal_album_modify.do?type=${type}&cpage=${cpage}&seq=${seq}" id="edit">edit</a>
            </li>
            <li class="menu_list">
              <a href="#" id="delete">delete</a>
            </li>
            <li class="list">
              <a href="/greenpaw/personal_album_list.do?type=${type}&cpage=${cpage}">list</a>
            </li>
            <li class="new">
              <a href="/greenpaw/personal_album_write.do">new</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </body>
	<!-- toast viewer module -->
	<script type="text/javascript" src="./js/view_page.js"></script>
	<!-- delete module -->
	<script type="text/javascript" src="./js/delete_modal.js"></script>


</html>
 	 