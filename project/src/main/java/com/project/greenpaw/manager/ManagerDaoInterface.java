package com.project.greenpaw.manager;

import java.util.ArrayList;

import com.project.greenpaw.community.common.BoardListTO;

public interface ManagerDaoInterface {

	/* 전체 조회 메서드 */
	ArrayList<ManagerTO> getList(int cpage, int recordPerPage);

	/* 페이지 메서드 */
	BoardListTO boardList(BoardListTO listTO, String field, String keyword);

	/* field와 keyword를 통한 검색 메서드 */
	ArrayList<ManagerTO> getSearchList(String field, String keyword, int cpage, int recordPerPage);

	int modifyOK(String nickname, String status);

	/* ---------- 날짜 계산 ---------- */
	ArrayList<ManagerTO> dateSearch(String startDate, String endDate, int cpage, int recordPerPage);

	/* ---------- 총 회원 계산 ---------- */
	int getCount();

	/* ---------- 기간 검색 건수 계산 ---------- */
	BoardListTO getPagingByDate(BoardListTO listTO, String field, String keyword, String startDate, String endDate);

}