package com.project.greenpaw.community.thumbnailboard;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.project.greenpaw.community.common.BoardListTO;
import com.project.greenpaw.community.common.BoardTO;

@Service
public interface ThumbnailDaoInterface {

	/* categorySeq에 따른 전체 조회 메서드 */
	ArrayList<BoardTO> getList(String categorySeq, int cpage, int recordPerPage);

	/* 페이지 메서드 */
	BoardListTO boardList(BoardListTO listTO, String categorySeq, String field, String keyword);

	/* field와 keyword를 통한 검색 메서드 */
	ArrayList<BoardTO> getSearchList(String categorySeq, String field, String keyword, int cpage, int recordPerPage);

	/* 정렬 메서드 */
	ArrayList<BoardTO> listSort(String categorySeq, String field, String keyword, String sort, int cpage,
			int recordPerPage);

	BoardTO thumbview(BoardTO to);

	int thumbdelete(BoardTO to);

	BoardTO thumbmodify(BoardTO to);

	int thumbmodifyOK(BoardTO to);

}