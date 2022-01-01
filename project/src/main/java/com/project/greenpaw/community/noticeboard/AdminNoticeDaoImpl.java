package com.project.greenpaw.community.noticeboard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.greenpaw.community.common.BoardTO;
import com.project.greenpaw.community.common.PageTO;

@Service
public class AdminNoticeDaoImpl implements AdminNoticeDaoInterface {

	@Autowired
	private AdminNoticeDAO dao; 	
	
	@Override
	public ArrayList<BoardTO> getList(String categorySeq, int page, int perPage) {
		// TODO Auto-generated method stub
		return dao.getList(categorySeq, page, perPage);
	}

	@Override
	public int deleteAll(int[] deleteSeq) {
		// TODO Auto-generated method stub
		return dao.deleteAll(deleteSeq);
	}

	@Override
	public ArrayList<String> getNoticeSeq(String categorySeq, int page, int perPage) {
		// TODO Auto-generated method stub
		return dao.getNoticeSeq(categorySeq, page, perPage);
	}

	@Override
	public int privateNoticeAll(int[] privateSeqs, int[] allList) {
		// TODO Auto-generated method stub
		return dao.privateNoticeAll(privateSeqs, allList);
	}

	@Override
	public int privateNoticeAll(List<String> privateSeqs, List<String> allList) {
		// TODO Auto-generated method stub
		return dao.privateNoticeAll(privateSeqs, allList);
	}

	@Override
	public int privateNoticeAll(String privateSeqsStr, String allListStr) {
		// TODO Auto-generated method stub
		return dao.privateNoticeAll(privateSeqsStr, allListStr);
	}

	@Override
	public int writeOk(BoardTO post) {
		// TODO Auto-generated method stub
		return dao.writeOk(post);
	}

	@Override
	public ArrayList<String> getImgList(int seq) {
		// TODO Auto-generated method stub
		return dao.getImgList(seq);
	}

	@Override
	public PageTO boardList(PageTO PageTO, String category, String field, String keyword, String subtitle) {
		// TODO Auto-generated method stub
		return dao.boardList(PageTO, category, field, keyword, subtitle);
	}

	@Override
	public PageTO boardList(PageTO PageTO, String category, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return dao.boardList(PageTO, category, startDate, endDate);
	}

	@Override
	public ArrayList<BoardTO> getSearchList(String categorySeq, String field, String keyword, PageTO pages) {
		// TODO Auto-generated method stub
		return dao.getSearchList(categorySeq, field, keyword, pages);
	}

	@Override
	public ArrayList<BoardTO> listSort(String categorySeq, String field, String keyword, String sort, PageTO pages) {
		// TODO Auto-generated method stub
		return dao.listSort(categorySeq, field, keyword, sort, pages);
	}

	@Override
	public ArrayList<BoardTO> getSubTitleList(String category, String subtitle, PageTO pages) {
		// TODO Auto-generated method stub
		return dao.getSubTitleList(category, subtitle, pages);
	}

	@Override
	public ArrayList<BoardTO> dateSearch(String startDate, String endDate, PageTO pages) {
		// TODO Auto-generated method stub
		return dao.dateSearch(startDate, endDate, pages);
	}

	@Override
	public int modifyOk(BoardTO to) {
		// TODO Auto-generated method stub
		return dao.modifyOk(to);
	}

	@Override
	public int deleteOk(BoardTO to) {
		// TODO Auto-generated method stub
		return dao.deleteOk(to);
	}

}
