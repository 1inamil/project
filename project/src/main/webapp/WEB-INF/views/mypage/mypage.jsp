<%@page import="com.project.greenpaw.mypage.MypageTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String contextPath = request.getContextPath();
	
	int flag = 1; //비로그인 상태
	if(session.getAttribute("mail") != null){
		flag = 0; //로그인 상태
		//System.out.println(flag);
	}
	
	String[] noteArr = (String[])request.getAttribute("noteArr");
	
	StringBuilder sbHtml = new StringBuilder();
	for(int i = 0; i<noteArr.length; i++){
		sbHtml.append("<li class='tag-item'>" + noteArr[i] + "<span class='del-btn' idx='" + i + "'>x</span></li>");
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
    <link rel="stylesheet" href="./css/mypage.css" />
<%
	if(flag == 1){ //비로그인 시
		out.println("<script type='text/javascript'>");
		out.println("alert('로그인 후 이용 가능합니다.');");
		out.println("location.href='/sign_in.do';");
		out.println("</script>");
		out.println("<script src='./js/menu_logOut.js' defer></script>");
	}else{ // 로그인시
		out.println("<script src='./js/menu_logeIn.js' defer></script>");
	}
%>
    <!-- jquery cdn -->
    <script
      src="https://code.jquery.com/jquery-3.6.0.js"
      integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
      crossorigin="anonymous"></script>
    <script src="./js/mypage.js"></script>
  </head>
  <body>
    <div class="main">
      <!-- nav bar include -->
 	 <%@ include file="../nav_bar.jsp" %>
      <div class="outer">
        <div class="content_wrap">
          <h1 class="hello"><span class="nickname">${to.nickname}</span>님, 안녕하세요!</h1>
          <div class="wrap">
            <div class="left">
              <div class="tab"><span class="tab_innertext">${to.nickname}님의 회원정보 입니다.</span></div>
              <div class="content">
                <ul class="user_info">
                  <li><span class="list">이메일</span>${to.mail}</li>
                  <li><span class="list">닉네임</span>${to.nickname}</li>
                  <li><span class="list">회원 등급</span>${to.authType}</li>
                  <li><span class="list">가입 일자</span>${to.createdAt}</li>
                  <li><a href="<%=contextPath %>/password_change.do">비밀번호 변경</a><a href="<%=contextPath %>/delete_account.do">회원 탈퇴</a></li>
                </ul>
              </div>
            </div>
            <div class="right">
              <div class="tab"><span class="tab_innertext">${to.nickname}님의 활동 정보 입니다.</span></div>
              <div class="content">
                <div class="act_info">
                  <div>
                    <p><span class="list">총 게시물 수</span>${to.totalPostCount}개</p>
                    <ul class="detail_act_info">
                      <li><span class="list">자유게시판</span>${to.postCat1}개</li>
                      <li><span class="list">성장앨범</span>${to.postCat2}개</li>
                      <li><span class="list">아이자랑</span>${to.postCat3}개</li>
                      <li><span class="list">후기 공유</span>${to.postCat4}개</li>
                      <li><span class="list">나눔거래</span>${to.postCat5}개</li>
                      <li><span class="list">질문과 답변</span>${to.postCat6}개</li>
                    </ul>
                  </div>
                  <div>
                    <p><span class="list">총 댓글 수</span>${to.totalCommentCount}개</p>
                    <ul class="detail_act_info">
                      <li><span class="list">성장앨범</span>${to.commentCount1}개</li>
                      <li><span class="list">아이자랑</span>${to.commentCount2}개</li>
                      <li><span class="list">후기 공유</span>${to.commentCount3}개</li>
                      <li><span class="list">나눔거래</span>${to.commentCount4}개</li>
                      <li><span class="list">자유게시판</span>${to.commentCount5}개</li>
                      <li><span class="list">질문과 답변</span>${to.commentCount6}개</li>
                    </ul>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="under">
            <span class="note">${to.nickname}님의 반려 동식물을 입력해주세요.</span>
            <input type="text" id="tag" class="tag" placeholder="태그 입력" />
            <ul id="tag-list"><%= sbHtml %></ul>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
