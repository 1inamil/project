package com.project.greenpaw.manager;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.greenpaw.community.common.BoardTO;
import com.project.greenpaw.community.common.PageTO;

@Service
public class ManagerBoardImpl implements ManagerBoardInterface {
	
	@Autowired
	private ManagerBoardDAO dao;

	@Override
	public PageTO boardList(PageTO pageTO, String category, String searchType, String keyword, int recordPerPage) {
		// TODO Auto-generated method stub
		return dao.boardList(pageTO, category, searchType, keyword, recordPerPage);
	}

	@Override
	public PageTO getPagingByDate(PageTO pageTO, String category, String searchType, String keyword, String startDate,
			String endDate, int recordPerPage) {
		// TODO Auto-generated method stub
		return dao.getPagingByDate(pageTO, category, searchType, keyword, startDate, endDate, recordPerPage);
	}

	@Override
	public ArrayList<BoardTO> getList(String categorySeq, int page, int perPage) {
		// TODO Auto-generated method stub
		return dao.getList(categorySeq, page, perPage);
	}

	@Override
	public ArrayList<BoardTO> getSearchList(String categorySeq, String searchType, String keyword, PageTO pages,
			int recordPerPage) {
		// TODO Auto-generated method stub
		return dao.getSearchList(categorySeq, searchType, keyword, pages, recordPerPage);
	}

	@Override
	public ArrayList<BoardTO> dateSearch(String category, String searchType, String keyword, String startDate,
			String endDate, PageTO pages, int recordPerPage) {
		// TODO Auto-generated method stub
		return dao.dateSearch(category, searchType, keyword, startDate, endDate, pages, recordPerPage);
	}

	@Override
	public int deleteOk(BoardTO to, String filePath) {
		// TODO Auto-generated method stub
		return dao.deleteOk(to, filePath);
	}

	@Override
	public int hiddenOk(BoardTO to) {
		// TODO Auto-generated method stub
		return dao.hiddenOk(to);
	}

}
