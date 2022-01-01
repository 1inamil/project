package com.project.greenpaw.manager.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.greenpaw.community.common.BoardListTO;
import com.project.greenpaw.community.common.BoardTO;
import com.project.greenpaw.community.common.PageTO;
import com.project.greenpaw.manager.ManagerBoardInterface;
import com.project.greenpaw.manager.ManagerDaoInterface;
import com.project.greenpaw.manager.ManagerTO;

@Controller
public class ManagerController {
	
	@Autowired
	private ManagerBoardInterface managerBoardDaoInterface;
	
	@Autowired
	private ManagerDaoInterface managerDaoInterface;
	
	private String mappingPath = "/manager";
	
	@RequestMapping("/manager_board.do")
	public ModelAndView managerBoardList(HttpServletRequest request, PageTO pages, BoardTO to, ModelAndView modelAndView) {
		//세션 확인
		String authType = (String) request.getSession().getAttribute("authType");

		//페이지 첫 로딩 초기값
		String category = "";
		String searchType = "title";
		String keyword = "";
		String startDate = "";
		String endDate = "";
		int cPage = 1; //기본 페이지
		int recordPerPage = 10;

		//카테고리 없어도 나올수 있도록
		if (request.getParameter("category") != null && !request.getParameter("category").equals("")) {
			category = request.getParameter("category");
		}

		if (request.getParameter("searchType") != null && !request.getParameter("searchType").equals("")) {
			searchType = request.getParameter("searchType");
		}

		if (request.getParameter("keyword") != null && !request.getParameter("keyword").equals("")) {
			keyword = request.getParameter("keyword");
		}

		if (request.getParameter("startDate") != null && !request.getParameter("startDate").equals("")) {
			startDate = request.getParameter("startDate");
		}

		if (request.getParameter("endDate") != null && !request.getParameter("endDate").equals("")) {
			endDate = request.getParameter("endDate");
		}

		if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
			cPage = Integer.parseInt(request.getParameter("page"));
		}

		//페이징
		pages.setCpage(cPage);

		pages = managerBoardDaoInterface.boardList(pages, category, searchType, keyword, recordPerPage);

		if (!startDate.equals("") && !endDate.equals("")) {
			pages = managerBoardDaoInterface.getPagingByDate(pages, category, searchType, keyword, startDate, endDate, recordPerPage);
		}

		ArrayList<BoardTO> result = null;
		//리스트
		if(searchType.equals("") && keyword.equals("")) {
			result = managerBoardDaoInterface.getList(category, cPage, recordPerPage);
		}

		//검색으로 리스트 뽑기
		if (!searchType.equals("") && keyword.equals("") || searchType.equals("") && !keyword.equals("") || !searchType.equals("") && !keyword.equals("")) {
			result = managerBoardDaoInterface.getSearchList(category, searchType, keyword, pages, recordPerPage);
		}
		
		//날짜로 리스트 뽑기
		if (!startDate.equals("") && !endDate.equals("")) {
			result = managerBoardDaoInterface.dateSearch(category, searchType, keyword, startDate, endDate, pages, recordPerPage);
		}
		
		modelAndView.addObject("result", result);
		modelAndView.addObject("pages", pages);
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("endDate", endDate);
		modelAndView.addObject("authType", authType);
		modelAndView.addObject("keyword", keyword);
		modelAndView.addObject("category", category);
		modelAndView.addObject("searchType", searchType);
		modelAndView.setViewName( mappingPath + "/manager_board");
		return modelAndView;

	}
	
	@RequestMapping("/manager_mboard.do")
	public ModelAndView managerMemberBoardList(HttpServletRequest request, PageTO pages, BoardListTO listTO, ModelAndView modelAndView) {
		
		String authTypead = (String)request.getSession().getAttribute("authType");
		
		//login
		int cpage = 1;
		String categorySeq = request.getParameter("categorySeq");
		String field = "nickname";
		String keyword ="";
		String startDate = "";
		String endDate = "";

		if(request.getParameter("cpage") != null && !request.getParameter("cpage").equals("")) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}
		// 검색 부분의 field와 keyword 가져오기
		if(request.getParameter("field") != null && !request.getParameter("field").equals("") ){
			field =request.getParameter("field");
		}
		if( request.getParameter("keyword") != null && !request.getParameter("keyword").equals("")){
			keyword =request.getParameter("keyword");
		}
		if(request.getParameter("startDate") != null && !request.getParameter("startDate").equals("") ){
			startDate = request.getParameter("startDate");
		}

		if(request.getParameter("endDate") != null && !request.getParameter("endDate").equals("") ){
			endDate = request.getParameter("endDate");
		}
		
		//MANAGER_BOARD
		int recordPerPage = 10;
		listTO.setRecordPerPage(recordPerPage);
		listTO.setCpage( cpage );
		listTO = managerDaoInterface.boardList(listTO,field,keyword);

		if (!startDate.equals("") && !endDate.equals("")) {
			listTO = managerDaoInterface.getPagingByDate(listTO, field, keyword, startDate, endDate);
		}

		String type = request.getParameter("type"); //type데이터 가져오기
		listTO.setType(type);

		ArrayList<ManagerTO> userLists = managerDaoInterface.getList(cpage, recordPerPage);
		
		if(!field.equals("") && !keyword.equals("")){ 	// 검색값을 입력했을 경우
			userLists = managerDaoInterface.getSearchList(field, keyword, cpage, recordPerPage);
		}
		
		if(!startDate.equals("") && !endDate.equals("")){
			userLists = managerDaoInterface.dateSearch(startDate, endDate, cpage, recordPerPage );
		}
		
		//총 회원수와 검색결과 건수 구하기 
		int countlist = managerDaoInterface.getCount();
		
		modelAndView.addObject("authTypead", authTypead);
		modelAndView.addObject("userLists", userLists);
		modelAndView.addObject("listTO", listTO);
		modelAndView.addObject("countlist", countlist);
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("endDate", endDate);
		modelAndView.addObject("keyword", keyword);
		modelAndView.addObject("field", field);
		modelAndView.setViewName(mappingPath + "/manager_mboard");
		
		return modelAndView;
	}
	
	@RequestMapping("/manager_modify_ok.do")
	public ModelAndView managerModifyOk(HttpServletRequest request, ModelAndView modelAndView) {
		// 넘어온 파라미터로 디비에 insert 
		String nicknames = request.getParameter("nicknames");
		String nickname = nicknames.replace("[","").replace("]","").replaceAll("\"","\'");
		String status = request.getParameter("status");
		
		int flag = managerDaoInterface.modifyOK(nickname,status);
		
		modelAndView.addObject("flag", flag);
		modelAndView.setViewName(mappingPath + "/manager_modify_ok");
		return modelAndView;
	}

	@RequestMapping("/deletePost.do")
	public ModelAndView deletePost(HttpServletRequest request, BoardTO to, ModelAndView modelAndView) {
		// ajax 에서 넘겨준 seq 배열
		String[] seqList = request.getParameterValues("seqList[]");
		String filePath = request.getRealPath("upload") + "/";

		int deletedAll = 0;
		
		if (seqList != null) {
			for (String seq : seqList) {
				// 해당 seq 글 & 파일 삭제하기
				to.setSeq(seq);
				deletedAll += managerBoardDaoInterface.deleteOk(to, filePath);
			}
		}

		int flag = 0;
		if (deletedAll == seqList.length){ flag = 1; } 
		
		modelAndView.addObject("flag", flag);
		modelAndView.setViewName(mappingPath + "/deletePost");
		return modelAndView;
		
	}
	
	@RequestMapping("/hiddenPost.do")
	public ModelAndView hiddenPost(HttpServletRequest request, BoardTO to, ModelAndView modelAndView) {
		// ajax 에서 넘겨준 seq 배열
		String[] seqList = request.getParameterValues("seqList[]");

		int blockedAll = 0;

		if (seqList != null) {
			for (String seq : seqList) {
				// 해당 seq 글 private 값 바꾸기
				to.setSeq(seq);
				blockedAll += managerBoardDaoInterface.hiddenOk(to); //수정한 총 글 개수
			}
		}

		int flag = 0;
		if (blockedAll == seqList.length){ flag = 1; }
		
		modelAndView.addObject("flag", flag);
		modelAndView.setViewName(mappingPath + "/hiddenPost");
		return modelAndView;
	}
	
}

