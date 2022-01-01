package com.project.greenpaw.community.thumbnailboard;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.greenpaw.community.common.BoardListTO;
import com.project.greenpaw.community.common.BoardTO;

@Service
public class ThumbnailDaoImpl implements ThumbnailDaoInterface {
	
	@Autowired
	private ThumbDAO dao; 

	@Override
	public ArrayList<BoardTO> getList(String categorySeq, int cpage, int recordPerPage) {
		// TODO Auto-generated method stub
		return dao.getList(categorySeq, cpage, recordPerPage);
	}

	@Override
	public BoardListTO boardList(BoardListTO listTO, String categorySeq, String field, String keyword) {
		// TODO Auto-generated method stub
		return dao.boardList(listTO, categorySeq, field, keyword);
	}

	@Override
	public ArrayList<BoardTO> getSearchList(String categorySeq, String field, String keyword, int cpage,
			int recordPerPage) {
		// TODO Auto-generated method stub
		return dao.getSearchList(categorySeq, field, keyword, cpage, recordPerPage);
	}

	@Override
	public ArrayList<BoardTO> listSort(String categorySeq, String field, String keyword, String sort, int cpage,
			int recordPerPage) {
		// TODO Auto-generated method stub
		return dao.listSort(categorySeq, field, keyword, sort, cpage, recordPerPage);
	}

	@Override
	public BoardTO thumbview(BoardTO to) {
		// TODO Auto-generated method stub
		return dao.thumbview(to);
	}

	@Override
	public int thumbdelete(BoardTO to) {
		// TODO Auto-generated method stub
		return dao.thumbdelete(to);
	}

	@Override
	public BoardTO thumbmodify(BoardTO to) {
		// TODO Auto-generated method stub
		return dao.thumbmodify(to);
	}

	@Override
	public int thumbmodifyOK(BoardTO to) {
		// TODO Auto-generated method stub
		return dao.thumbmodifyOK(to);
	}

}
