<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String category = (String)request.getAttribute("category");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>GreenPaw</title>
<!-- toast ui editor -->
<!-- Toast Editor 2.0.0과 의존성 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.4/codemirror.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/editor/2.0.0/toastui-editor.min.css" />

<!-- jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<!-- toastr -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.css" integrity="sha512-3pIirOrwegjM6erE5gPSwkUzO+3cTjpnV9lexlNZqvupR64iZBnOOTiiLPb9M36zpMScbmUNIcHUqKD47M719g==" crossorigin="anonymous" referrerpolicy="no-referrer" />

<link rel="stylesheet" href="./css/reset.css" />
<link rel="preconnect" href="https://fonts.googleapis.com" />
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet" />
<link rel="stylesheet" href="./css/menu.css" />
<script src='./js/menu_logeIn.js' defer></script>

 <% 
 if(category.equals("1")){
 	out.println("<link rel='stylesheet' href='./css/subTitle_write.css' />");
 }

 if(category.equals("7")){
 	out.println("<link rel='stylesheet' href='./css/notice_write.css' />");
 }

 if(category.equals("6")){
 	out.println("<link rel='stylesheet' href='./css/QnA_write.css' />");
 }
%>
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
          <div class="write_box_bg">
            <div class="content_outer">
              <ul class="sub_title">
                <li class="sub_title-title"><label for="sub_title">말머리</label></li>
                <li class="title_input">
                <!-- 추가부분 -->
                  <select id="sub_title" class="sub_title-item">
                  <%if(category != null && category.equals("1")){ %>
                    <option checked>잡담</option>
                    <option>정보</option>
                    <%} else if(category != null && category.equals("6")) {%>
                    <option checked>질문</option>
                    <option>답변완료</option>
                     <%} else if(category != null && category.equals("7")) {%>
                     <option checked>일반</option>
                     <option>이벤트</option>
                     <option>중요</option>
                    <%} %>
                  </select>
                </li>
              </ul>
              <ul class="content_title">
                <li class="type_select"><input type="radio" name="type" id="plant" checked value="plant" /><label for="plant">plant</label></li>
                <li class="type_select"><input type="radio" name="type" id="pet" value="pet" /><label for="pet">pet</label></li>
                <li class="title_input">
                  <input type="text" id="title" name="title" placeholder="제목을 입력하세요." maxlength="21" oninput="checkLength(this)" />
                </li>
              </ul>
              <div class="write_body">
                <div id="editor" class="editor"></div>
              </div>
              <!-- 추가해주세요 -->
              <input type="hidden" name="category" id="category" value="${category }"/>
              <div class="btn_box">
                <button type="submit" class="btn_save" id="save_btn" onclick="clickSaveBtn()">Save!</button>
                <!-- <button type="button" class="btn_submit" id="list_btn" onclick="tmpListFunc()">목록</button> -->
              </div>
            </div>
          </div>
        </div>
        <!-- 우측 메뉴바 -->
        <div class="album_right">
          <ul class="menu">
            <li class="list">
              <a href="./normal_board.do?category=${category }">list</a>
            </li>
            <li class="new">
              <a href="./subTitle_write.do?category=${category }">new</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </body>
  <!-- toast ui editor JS -->
  <!-- 꼭 editor 사용할 div 보다 아래에 넣어줘야 함 -->
  <script src="./js/write_nomal_page.js" defer></script>
</html>
