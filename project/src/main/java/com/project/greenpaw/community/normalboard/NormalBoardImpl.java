package com.project.greenpaw.community.normalboard;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.greenpaw.community.common.BoardTO;
import com.project.greenpaw.community.common.CategoryTO;
import com.project.greenpaw.community.common.PageTO;

@Service
public class NormalBoardImpl implements NormalBoardDaoInterface {
	
	@Autowired
	private NormalBoardDAO dao;

	@Override
	public CategoryTO getCategoryName(CategoryTO to) {
		// TODO Auto-generated method stub
		return dao.getCategoryName(to);
	}

	@Override
	public BoardTO normalBoardView(BoardTO to) {
		// TODO Auto-generated method stub
		return dao.normalBoardView(to);
	}

	@Override
	public int normalBoardDelete(BoardTO to) {
		// TODO Auto-generated method stub
		return dao.normalBoardDelete(to);
	}

	@Override
	public BoardTO modify(BoardTO to) {
		// TODO Auto-generated method stub
		return dao.modify(to);
	}

	@Override
	public int modifyOk(BoardTO to) {
		// TODO Auto-generated method stub
		return dao.modifyOk(to);
	}

	@Override
	public int isSeq(String seq) {
		// TODO Auto-generated method stub
		return dao.isSeq(seq);
	}

	@Override
	public int writeOk(BoardTO post) {
		// TODO Auto-generated method stub
		return dao.writeOk(post);
	}

	@Override
	public String sha256(String password) {
		// TODO Auto-generated method stub
		return dao.sha256(password);
	}

	@Override
	public ArrayList<BoardTO> getList(String categorySeq, int page, int perPage) {
		// TODO Auto-generated method stub
		return dao.getList(categorySeq, page, perPage);
	}

	@Override
	public PageTO boardList(PageTO pages, String category, String field, String keyword, String subtitle) {
		// TODO Auto-generated method stub
		return dao.boardList(pages, category, field, keyword, subtitle);
	}

	@Override
	public ArrayList<BoardTO> getSearchList(String categorySeq, String field, String keyword, PageTO pages) {
		// TODO Auto-generated method stub
		return dao.getSearchList(categorySeq, field, keyword, null);
	}

	@Override
	public ArrayList<BoardTO> listSort(String categorySeq, String field, String keyword, String sort, PageTO pages) {
		// TODO Auto-generated method stub
		return dao.listSort(categorySeq, field, keyword, sort, null);
	}

	@Override
	public ArrayList<BoardTO> getSubTitleList(String category, String subtitle, PageTO pages) {
		// TODO Auto-generated method stub
		return dao.getSubTitleList(category, subtitle, null);
	}

}
