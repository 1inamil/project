package com.project.greenpaw.manager;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.project.greenpaw.community.common.BoardTO;
import com.project.greenpaw.community.common.PageTO;

@Repository
public class ManagerBoardDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<BoardTO> boardRowMapper = BeanPropertyRowMapper.newInstance(BoardTO.class);

	public PageTO boardList(PageTO pageTO, String category, String searchType, String keyword, int recordPerPage) {

		if (searchType.equals("all")) {	searchType = "title"; }

		int cpage = pageTO.getCpage();
		int blockPerPage = pageTO.getBlockPerPage();
		
		int count = 0;

		String sql = "";
		if (category.equals("") && keyword != null ){
			// 카테고리 설정 없이 검색할때 
			sql = "select count(*) from board where " + searchType + " like ?";
			Object[] args = {"%" + keyword + "%"};
			count = jdbcTemplate.queryForObject(sql, args, Integer.class);

		} else if (category.equals("") || category.equals("null") || category == null) {
			// 모든 게시글 로딩 
			sql = "select count(*) from board";
			count = jdbcTemplate.queryForObject(sql, Integer.class);
				
		} else {
			// 카테고리 설정 & 검색
			sql = "select count(*) from board "
					+ "where category_seq = ? " + "and " + searchType + " like ?"; 
			
			Object[] args = {category, "%" + keyword + "%"};
			count = jdbcTemplate.queryForObject(sql, args, Integer.class);
		}

		pageTO.setTotalRecord(count);
		pageTO.setTotalPage(((pageTO.getTotalRecord() - 1) / recordPerPage) + 1);

		pageTO.setStartBlock(((cpage - 1) / blockPerPage) * blockPerPage + 1);
		pageTO.setEndBlock(((cpage - 1) / blockPerPage) * blockPerPage + blockPerPage);
		if (pageTO.getEndBlock() >= pageTO.getTotalPage()) {
			pageTO.setEndBlock(pageTO.getTotalPage());
		}
		return pageTO;
	}

	public PageTO getPagingByDate(PageTO pageTO, String category, String searchType, String keyword, 
		String startDate, String endDate, int recordPerPage) {

		int cpage = pageTO.getCpage();
		int blockPerPage = pageTO.getBlockPerPage();
	
		String sql = "";
		int count = 0;
		
		if (category.equals("") || category.equals("null") || category == null) {
			sql = "select count(*) from board where created_at between ? and ? order by seq desc";
			Object[] args = {startDate, endDate};
			count = jdbcTemplate.queryForObject(sql, args, Integer.class);
			
		} else {
			sql = "select count(*) from board "
					+ "where category_seq = ? " + "and " + searchType + " like ? "
					+ "and created_at between ? and ?";
			Object[] args = {category, "%" + keyword + "%", startDate, endDate };
			count = jdbcTemplate.queryForObject(sql, args, Integer.class);
		}
	
		pageTO.setTotalRecord(count);
		pageTO.setTotalPage(((pageTO.getTotalRecord() - 1) / recordPerPage) + 1);
	
		pageTO.setStartBlock(((cpage - 1) / blockPerPage) * blockPerPage + 1);
		pageTO.setEndBlock(((cpage - 1) / blockPerPage) * blockPerPage + blockPerPage);
		if (pageTO.getEndBlock() >= pageTO.getTotalPage()) {
			pageTO.setEndBlock(pageTO.getTotalPage());
		}
	
		return pageTO;
	}

	// 게시글 리스트 그리기
	public ArrayList<BoardTO> getList(String categorySeq, int page, int perPage) {

		ArrayList<BoardTO> datas = new ArrayList<BoardTO>();

		String sql = "select seq, category_seq, title, nickname, created_at, hit, is_private from board order by seq desc limit ?, ?";
		Object[] args = {(page - 1)*perPage, perPage};
		datas = (ArrayList<BoardTO>) jdbcTemplate.query(sql, args, boardRowMapper);
		return datas;
	}

	// 검색
	public ArrayList<BoardTO> getSearchList(String categorySeq, String searchType, String keyword, PageTO pages, int recordPerPage) {

		if (searchType.equals("all")) { searchType = "title"; }
		ArrayList<BoardTO> datas = new ArrayList<BoardTO>();
		
		String sql = "";
		if (categorySeq.equals("") || categorySeq ==null) { // 카테고리 설정 없을때
			
			sql = "select * from board where " + searchType + " like ? order by seq desc limit ?, ?";
			Object[] args = {"%"+keyword+"%", (pages.getCpage()-1) *recordPerPage, recordPerPage };
			datas = (ArrayList<BoardTO>) jdbcTemplate.query(sql, args, boardRowMapper);
		
		} else if(!categorySeq.equals("") || categorySeq != null && keyword == null ){ //카테고리 설정은 있고, 검색어는 없을 때
			
			sql = "select * from board where category_seq=? order by seq desc limit ?, ?";
			Object[] args = {categorySeq, (pages.getCpage()-1) * recordPerPage, recordPerPage };
			datas = (ArrayList<BoardTO>) jdbcTemplate.query(sql, args, boardRowMapper);
			
		}else {	// 카테고리 설정, 검색어 모두 있을때
		
			sql = "select * from board where category_seq=? " + "and " + searchType + " like ? order by seq desc limit ?, ?";
			Object[] args = {categorySeq, "%"+keyword+"%", (pages.getCpage()-1) * recordPerPage, recordPerPage };
			datas = (ArrayList<BoardTO>) jdbcTemplate.query(sql, args, boardRowMapper);
		}
			
		return datas;
	}

	//날짜 계산
	public ArrayList<BoardTO> dateSearch(String category, String searchType, String keyword, String startDate,
			String endDate, PageTO pages,int recordPerPage) {

		ArrayList<BoardTO> datas = new ArrayList<BoardTO>();

		String sql = "";

		if (category.equals("") || category.equals("null") || category == null) {
			
			sql = "select * from board where created_at between ? and ? order by seq desc limit ? , ?";
			Object[] args = {startDate, endDate, (pages.getCpage() - 1) * recordPerPage, recordPerPage };
			datas = (ArrayList<BoardTO>) jdbcTemplate.query(sql, args, boardRowMapper);
					
		} else {
			sql = "select * from board " + "where category_seq = ? " + "and " + searchType + " like ? "
					+ "and created_at between ? and ? order by seq desc limit ? , ?";
			Object[] args = {category, "%"+keyword+"%", startDate, endDate, (pages.getCpage() - 1) * recordPerPage, recordPerPage };
			datas = (ArrayList<BoardTO>) jdbcTemplate.query(sql, args, boardRowMapper);
		}
		
		return datas;
	}

	public int deleteOk(BoardTO to, String filePath) {

		int flag = 0;

		String sql = "select seq, thumb_url from board where seq =?";
		Object[] args = {to.getSeq()};
		to = jdbcTemplate.queryForObject(sql, args, boardRowMapper);

		String thumbUrl = to.getThumbUrl();
		String[] splitedFileUrl = thumbUrl.split("/");
		String fileName = splitedFileUrl[splitedFileUrl.length - 1];
		filePath += fileName;
		File file = new File(filePath);
		if (file.exists()) {
			file.delete(); // 파일이 있으면 삭제
		}

		sql = "delete from board where seq =?";
		flag = jdbcTemplate.update(sql, to.getSeq());

		return flag;
	}
	
	public int hiddenOk(BoardTO to) {

		String sql = "select seq, is_private from board where seq =?";
		Object[] args = {to.getSeq()};
		to = jdbcTemplate.queryForObject(sql, args, boardRowMapper);
		
		if(to.isPrivate() == true) { to.setIsPrivate(false);
		}else if(to.isPrivate() == false) {to.setIsPrivate(true);}
		
		sql = "update board set is_private=? where seq =?";
		int flag = jdbcTemplate.update(sql, to.isPrivate(), to.getSeq());

		return flag;
	}
}
