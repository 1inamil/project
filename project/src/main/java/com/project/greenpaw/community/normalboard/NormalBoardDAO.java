package com.project.greenpaw.community.normalboard;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.greenpaw.community.common.BoardTO;
import com.project.greenpaw.community.common.CategoryTO;
import com.project.greenpaw.community.common.PageTO;

@Repository
public class NormalBoardDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<CategoryTO> categoryRowMapper = BeanPropertyRowMapper.newInstance(CategoryTO.class);
	private RowMapper<BoardTO> boardRowMapper = BeanPropertyRowMapper.newInstance(BoardTO.class);
	
	public CategoryTO getCategoryName(CategoryTO to) {

		String sql="select seq, name from board_category where seq=?";
		Object[] args = {to.getSeq()};
		to = jdbcTemplate.queryForObject(sql, args, categoryRowMapper);
		
		return to;
	}
	
	public BoardTO normalBoardView(BoardTO to){
		
		String sql = "update board set hit = hit + 1 where seq=?";
		Object[] args = {to.getSeq()};
		jdbcTemplate.update(sql, args);

		sql = "select * from board where seq=?";
		to = jdbcTemplate.queryForObject(sql, args, boardRowMapper);
		
		return to;
	}
	
	public int normalBoardDelete(BoardTO to) {
		
		int flag = 2; //데이터 오류

		// 본인확인용 컬럼 추가 
		String sql = "delete from board where seq=? and nickname=?";
		Object[] args = {to.getSeq(), to.getNickname()};	
		flag = jdbcTemplate.update(sql, args);
		
		return flag; 
	}
	
	//수정시 쓰기에 뜨도록
	public BoardTO modify(BoardTO to) {

		String sql ="select * from board where seq=?";
		Object[] args = {to.getSeq()};
		to = jdbcTemplate.queryForObject(sql, args, boardRowMapper);
		
		return to;
	}
	
	public int modifyOk(BoardTO to) {
		
		int flag = 2;
		
		String sql = "update board set title=?, sub_title=?, family_member_type=?,"
					+ " content=?, updated_at=now(),is_private=? where seq=? and nickname=?";

		Object[] args = {to.getTitle(), to.getSubTitle(), to.getFamilyMemberType(), to.getContent(),
				to.isPrivate(), to.getSeq(), to.getNickname()};

		flag = jdbcTemplate.update(sql, args);
		
		return flag;
	}
	
	//해당 글이 board에 남아있는지 확인
	public int isSeq(String seq) {

		int flag = 2;
		
		String sql = "select count(*) as count from board where seq=?";
		Object[] args = { seq };	
		flag = jdbcTemplate.queryForObject(sql, args, Integer.class);
		
		return flag;
		
	}
	
	public int writeOk(BoardTO post) {
		int flag = 0;

		String sql = "insert into board values (0, ?, ?, ?, ?, " + "?, ?, ?, ?, " + "0, 0, now(), now(), ?) ";
		Object[] args = {post.getCategorySeq(), post.getSubTitle(), post.getSaleStatus(), post.getFamilyMemberType(),
				post.getTitle(), post.getContent(), post.getThumbUrl(), post.getNickname(), post.isPrivate()};

		flag = jdbcTemplate.update(sql, args);
		return flag;
	}

	public String sha256(String password) {

		StringBuffer EncryptPs = null;

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			EncryptPs = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);

				if (hex.length() == 1) {
					EncryptPs.append('0');

				} else {
					EncryptPs.append(hex);
				}
			}

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return EncryptPs.toString();
	}

	// 일반 게시판 전체리스트(카테고리 별 1페이지에 나오는 것)
	public ArrayList<BoardTO> getList(String categorySeq, int page, int perPage) {

		ArrayList<BoardTO> datas = new ArrayList<BoardTO>();
		
		String sql = "select * from board where category_seq=? and is_private=0 order by seq desc limit ?, ?";
		Object[] args = {categorySeq, (page-1)*perPage, perPage};
		datas = (ArrayList<BoardTO>) jdbcTemplate.query(sql, args, boardRowMapper);
		return datas;
	}

	public PageTO boardList(PageTO pageTO, String category, String field, String keyword, String subtitle) {

		if (field == "type" || field.equals("type")) {
			field = "family_member_type";
		}

		int cpage = pageTO.getCpage();
		int recordPerPage = pageTO.getRecordPerPage();
		int blockPerPage = pageTO.getBlockPerPage();

		pageTO.setStartBlock(((cpage - 1) / blockPerPage) * blockPerPage + 1);
		pageTO.setEndBlock(((cpage - 1) / blockPerPage) * blockPerPage + blockPerPage);
		
		//갯수 세기
		String countSql = "select count(*) from board where category_seq=? and " + field + " like ? and sub_title like ? and is_private=0";
		Object[] countArgs = {category, "%" + keyword + "%", "%" + subtitle + "%"};
		int count = jdbcTemplate.queryForObject(countSql, countArgs, Integer.class);
		
		//결과 담기
		ArrayList<BoardTO> boardLists = new ArrayList<BoardTO>();
		String sql = "select category_seq, sub_title, sale_status, family_member_type, title, content, thumb_url, "
				+ "nickname, like_count, hit, date_format(created_at, '%Y-%m-%d') created_at, updated_at "
				+ "from board where category_seq=? and "
				+ field + " like ? and sub_title like ? and is_private=0 order by seq desc "
				+ "limit ?, ?";
		Object[] args = {category, "%"+keyword+"%", "%"+subtitle+"%", recordPerPage*(cpage-1), recordPerPage};
		
		boardLists = (ArrayList<BoardTO>) jdbcTemplate.query(sql, args, boardRowMapper);

		pageTO.setTotalRecord(count);
		pageTO.setTotalPage(((pageTO.getTotalRecord() - 1) / recordPerPage) + 1);
		pageTO.setPageList(boardLists);

		if (pageTO.getEndBlock() >= pageTO.getTotalPage()) {
			pageTO.setEndBlock(pageTO.getTotalPage());
		}

		return pageTO;
	}

	// 검색
	public ArrayList<BoardTO> getSearchList(String categorySeq, String field, String keyword, PageTO pages) {

		if (field == "type" || field.equals("type")) {
			field = "family_member_type";
		}

		ArrayList<BoardTO> datas = new ArrayList<BoardTO>();

		String sql = "select * from board where category_seq=? and " + field
					+ " like ? and is_private=0  order by seq desc limit ?, ?";

		Object[] args = {categorySeq, "%"+keyword +"%", (pages.getCpage() - 1) * pages.getRecordPerPage(), pages.getRecordPerPage() };
		
		datas = (ArrayList<BoardTO>) jdbcTemplate.query(sql, args, boardRowMapper);

		return datas;
	}

	// 정렬기능
	public ArrayList<BoardTO> listSort(String categorySeq, String field, String keyword, String sort, PageTO pages) {

		if (sort == null && sort.equals("")) { sort = "seq"; }

		ArrayList<BoardTO> datas = new ArrayList<BoardTO>();
		
		String sql = "select * from board where category_seq=? and " + field + " like ? and is_private=0  order by "
					+ sort + " desc limit ?, ?";
		
		Object[] args = {categorySeq, "%"+keyword+"%", (pages.getCpage()-1)*pages.getRecordPerPage(), pages.getRecordPerPage()};
		
		datas = (ArrayList<BoardTO>)jdbcTemplate.query(sql, args, boardRowMapper);

		return datas;
	}

	// 말머리만 클릭시
	public ArrayList<BoardTO> getSubTitleList(String category, String subtitle, PageTO pages) {

		ArrayList<BoardTO> datas = new ArrayList<BoardTO>();

		String sql = "select * from board where category_seq=? and sub_title=? and is_private=0  order by seq desc limit ?, ?";
		Object[] args = {category, subtitle, (pages.getCpage() - 1) * pages.getRecordPerPage(), pages.getRecordPerPage()};
		datas = (ArrayList<BoardTO>) jdbcTemplate.query(sql, args, boardRowMapper);
				
		return datas;
	}
	
}
