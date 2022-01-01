package com.project.greenpaw.lostanimals.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.greenpaw.lostanimals.ApiData;
import com.project.greenpaw.lostanimals.ApiTO;
import com.project.greenpaw.lostanimals.LostAnimalDaoInterface;

@Controller
public class GetAnimalListController {
	
	@Autowired
	private LostAnimalDaoInterface daoInterface;
	
	@RequestMapping("/getAnimalList.do")
	public ModelAndView getAnimalList(HttpServletRequest request, ApiTO search, ApiData api,
			ModelAndView modelAndView) {
		
		// 지도에서 넘겨준 지역 정보
		String sido = request.getParameter("sido");
		String gugun = request.getParameter("gugun"); 
		String pageNo = "1";
		if (request.getParameter("pageNo") != null) {
			// 기본 페이지 값 : 1
			// 새로 페이지 누를때 페이지값 넘겨주기 
			pageNo = request.getParameter("pageNo");
		}
		
		String numOfRows = "5"; // 한 페이지에 보여줄 리스트 수
		if (request.getParameter("numOfRows") != null) {
			numOfRows = request.getParameter("numOfRows");
		}
		
		// 넘어온 시도를 DB 에서 코드로 받아오기 
		String sidoCode = null;
		String gugunCode = null;
		if (daoInterface.getCode(sido, gugun) != null) {
			// 정상값 넘어왔을 경우 초기화
			sidoCode = daoInterface.getCode(sido, gugun)[0];
			gugunCode = daoInterface.getCode(sido, gugun)[1];
		}
		
		// 검색 조건 설정할 class 
		// 검색 조건 세팅
		search.setSidoCode(sidoCode);
		search.setGugunCode(gugunCode);
		search.setToday(LocalDate.now()); // 2021-09-08
		search.setStartDate(LocalDate.now().minusMonths(2)); // 오늘로부터 2달전 
		search.setPageNo(pageNo);
		search.setNumOfRows(numOfRows);
		
		// 검색조건 api 로 넘겨서 리스트 뽑아오기 
		String xml = null;
		
		try { // 먼저 xml 받고
			xml = api.getAnimalListXml(search);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		} 
		
		//JSONArray animalList = api.getAnimalListToJson(xml); // xml 을 jsonArray 로 파싱
		JSONArray animalList = new JSONArray();
	    if (api.getAnimalListToJson(xml) != null){
	       animalList = api.getAnimalListToJson(xml);  // xml 을 jsonArray 로 파싱
	    }
		
		// 페이징에 이용할 totalCount 를 JSONObject 로 만들어서 Array 에 추가
		JSONObject totalCount = new JSONObject();
		totalCount.put("totalCount",api.getTotalCount(xml));
		animalList.put(totalCount);
		//System.out.println(animalList);
		
		modelAndView.setViewName("/lostAnimals/getAnimalList");
		modelAndView.addObject("animalList", animalList);
		
		return modelAndView;
		
	}
	
	@RequestMapping("/pagination.do")
	public String pagination() {
		return "/lostAnimals/pagination";
	}

}
