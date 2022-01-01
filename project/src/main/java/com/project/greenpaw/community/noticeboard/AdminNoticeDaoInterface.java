package com.project.greenpaw.community.noticeboard;

import java.util.ArrayList;
import java.util.List;

import com.project.greenpaw.community.common.BoardTO;
import com.project.greenpaw.community.common.PageTO;

public interface AdminNoticeDaoInterface {

	///일괄 공개체크
	ArrayList<BoardTO> getList(String categorySeq, int page, int perPage);

	//일괄삭제 메소드
	int deleteAll(int[] deleteSeq);

	//inputbox에 넣을 숫자...
	ArrayList<String> getNoticeSeq(String categorySeq, int page, int perPage);

	//일괄공개,일괄비공개
	int privateNoticeAll(int[] privateSeqs, int[] allList);

	//String으로 감
	int privateNoticeAll(List<String> privateSeqs, List<String> allList);

	int privateNoticeAll(String privateSeqsStr, String allListStr);

	//글쓰기
	int writeOk(BoardTO post);

	//이미지파일명 가져오기
	ArrayList<String> getImgList(int seq);

	// 페이지 개수 /*수정함*/
	PageTO boardList(PageTO PageTO, String category, String field, String keyword, String subtitle);

	//페이지개수(날짜 검색 시)
	PageTO boardList(PageTO PageTO, String category, String startDate, String endDate);

	// 검색
	ArrayList<BoardTO> getSearchList(String categorySeq, String field, String keyword, PageTO pages);

	// 정렬기능
	ArrayList<BoardTO> listSort(String categorySeq, String field, String keyword, String sort, PageTO pages);

	// 말머리만 클릭시
	ArrayList<BoardTO> getSubTitleList(String category, String subtitle, PageTO pages);

	//날짜검색
	ArrayList<BoardTO> dateSearch(String startDate, String endDate, PageTO pages);

	//수정
	int modifyOk(BoardTO to);

	//삭제
	int deleteOk(BoardTO to);

}