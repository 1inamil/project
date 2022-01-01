package com.project.greenpaw.manager;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.greenpaw.community.common.BoardListTO;

@Service
public class ManagerDaoImpl implements ManagerDaoInterface {
	
	@Autowired
	private ManagerDAO dao;

	@Override
	public ArrayList<ManagerTO> getList(int cpage, int recordPerPage) {
		// TODO Auto-generated method stub
		return dao.getList(cpage, recordPerPage);
	}

	@Override
	public BoardListTO boardList(BoardListTO listTO, String field, String keyword) {
		// TODO Auto-generated method stub
		return dao.boardList(listTO, field, keyword);
	}

	@Override
	public ArrayList<ManagerTO> getSearchList(String field, String keyword, int cpage, int recordPerPage) {
		// TODO Auto-generated method stub
		return dao.getSearchList(field, keyword, cpage, recordPerPage);
	}

	@Override
	public int modifyOK(String nickname, String status) {
		// TODO Auto-generated method stub
		return dao.modifyOK(nickname, status);
	}

	@Override
	public ArrayList<ManagerTO> dateSearch(String startDate, String endDate, int cpage, int recordPerPage) {
		// TODO Auto-generated method stub
		return dao.dateSearch(startDate, endDate, cpage, recordPerPage);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dao.getCount();
	}

	@Override
	public BoardListTO getPagingByDate(BoardListTO listTO, String field, String keyword, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		return dao.getPagingByDate(listTO, field, keyword, startDate, endDate);
	}

}
