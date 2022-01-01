package com.project.greenpaw.manager;

import java.util.ArrayList;

import com.project.greenpaw.community.common.BoardTO;
import com.project.greenpaw.community.common.PageTO;

public interface ManagerBoardInterface {

	PageTO boardList(PageTO pageTO, String category, String searchType, String keyword, int recordPerPage);

	PageTO getPagingByDate(PageTO pageTO, String category, String searchType, String keyword, String startDate,
			String endDate, int recordPerPage);

	// 게시글 리스트 그리기
	ArrayList<BoardTO> getList(String categorySeq, int page, int perPage);

	// 검색
	ArrayList<BoardTO> getSearchList(String categorySeq, String searchType, String keyword, PageTO pages,
			int recordPerPage);

	//날짜 계산
	ArrayList<BoardTO> dateSearch(String category, String searchType, String keyword, String startDate, String endDate,
			PageTO pages, int recordPerPage);

	int deleteOk(BoardTO to, String filePath);

	int hiddenOk(BoardTO to);

}