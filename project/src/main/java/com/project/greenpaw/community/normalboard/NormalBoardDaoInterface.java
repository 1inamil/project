package com.project.greenpaw.community.normalboard;

import java.util.ArrayList;

import com.project.greenpaw.community.common.BoardTO;
import com.project.greenpaw.community.common.CategoryTO;
import com.project.greenpaw.community.common.PageTO;

public interface NormalBoardDaoInterface {

	CategoryTO getCategoryName(CategoryTO to);

	BoardTO normalBoardView(BoardTO to);

	int normalBoardDelete(BoardTO to);

	//수정시 쓰기에 뜨도록
	BoardTO modify(BoardTO to);

	int modifyOk(BoardTO to);

	//해당 글이 board에 남아있는지 확인
	int isSeq(String seq);

	int writeOk(BoardTO post);

	String sha256(String password);

	// 일반 게시판 전체리스트(카테고리 별 1페이지에 나오는 것)
	ArrayList<BoardTO> getList(String categorySeq, int page, int perPage);

	// 페이지 개수 /*수정함*/
	PageTO boardList(PageTO PageTO, String category, String field, String keyword, String subtitle);

	// 검색
	ArrayList<BoardTO> getSearchList(String categorySeq, String field, String keyword, PageTO pages);

	// 정렬기능
	ArrayList<BoardTO> listSort(String categorySeq, String field, String keyword, String sort, PageTO pages);

	// 말머리만 클릭시
	ArrayList<BoardTO> getSubTitleList(String category, String subtitle, PageTO pages);

}