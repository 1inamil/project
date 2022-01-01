<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>GreenPaw</title>
    <link rel="stylesheet" href="./css/reset.css" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="./css/lostAnimals.css" />
    <link rel="stylesheet" href="./css/menu.css" />
    <script
	  src="https://code.jquery.com/jquery-3.6.0.js"
	  integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
	  crossorigin="anonymous"></script>
    
    <script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=4cebd24dfd28cc7ed62024f347ec60d7&libraries=services"></script>
    <script src="./js/kakao_map.js" defer></script>
    <script src='./js/menu_logeIn.js' defer></script>
 
  </head>
  <body>
    <div class="main">
      <!-- nav bar include -->
 	 <%@ include file="../nav_bar.jsp" %>
       <div class="outer">
		<div id="modal" class="modal-overlay">
		</div>
        <div class="map_container">
      		<div id="map" class="map"></div>
      		<p id="centerAddr"></p>
        </div>
        <div class="list_container">
          <ul class="list" id="animalList">
          </ul>
          <div id="page_box" class="page_box"></div>
        </div>
      </div>
    </div>
  </body>
</html>