package com.project.greenpaw.community.thumbnailboard;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.greenpaw.community.common.BoardListTO;
import com.project.greenpaw.community.common.BoardTO;

@Repository
public class ThumbDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<BoardTO> boardRowMapper = BeanPropertyRowMapper.newInstance(BoardTO.class);
	
	/* categorySeq에 따른 전체 조회 메서드 */
	public ArrayList<BoardTO> getList(String categorySeq, int cpage,int recordPerPage) {

		ArrayList<BoardTO> datas = new ArrayList<BoardTO>();

		String sql = "select * from board where category_seq=? and is_private=0 order by seq desc limit ?, ?";
		Object[] args = {categorySeq, (cpage-1)*recordPerPage, recordPerPage};
		datas = (ArrayList<BoardTO>)jdbcTemplate.query(sql, args, boardRowMapper);
		
		return datas;
	}
	
	/* 페이지 메서드 */
	public BoardListTO boardList(BoardListTO listTO, String categorySeq, String field,String keyword) {

		if(field =="type" || field.equals("type")) { field = "family_member_type"; }
		
		int cpage = listTO.getCpage();
		int recordPerPage = listTO.getRecordPerPage();
		int blockPerPage = listTO.getBlockPerPage();
		
		listTO.setStartBlock( ( ( cpage -1 ) / blockPerPage ) * blockPerPage + 1 );
		listTO.setEndBlock( ( ( cpage -1 ) / blockPerPage ) * blockPerPage + blockPerPage );
		
		ArrayList<BoardTO> boardLists = new ArrayList<BoardTO>();
		
		String sql = "select category_seq, seq, sub_title, sale_status, family_member_type, "
				+ "title, content, thumb_url, nickname, hit, date_format(created_at, '%Y.%m.%d') created_at, updated_at "
				+ "from board where category_seq=? and "+field+" like ? and is_private=0 "
				+ "order by seq desc limit ?,?";
		
		Object[] args = {categorySeq, "%"+keyword+"%", (cpage-1)*recordPerPage, recordPerPage};
		boardLists = (ArrayList<BoardTO>)jdbcTemplate.query(sql, args, boardRowMapper);
		
		String countSql = "select count(*) from board where category_seq=? and "+ field +" like ? and is_private=0 ";
		Object[] countArgs = {categorySeq, "%"+keyword+"%"};
		int count = jdbcTemplate.queryForObject(countSql, countArgs, Integer.class);

		listTO.setTotalRecord(count);
		listTO.setTotalPage( ( ( listTO.getTotalRecord() -1 ) / recordPerPage ) + 1 );
		listTO.setBoardLists( boardLists );
			
		if( listTO.getEndBlock() >= listTO.getTotalPage() ) {
			listTO.setEndBlock( listTO.getTotalPage() );
		}
		
		return listTO;
	}
	
	/* field와 keyword를 통한 검색 메서드 */
	public ArrayList<BoardTO> getSearchList(String categorySeq, String field, String keyword, int cpage,int recordPerPage){
		
		if(field == "type" || field.equals("type")) {field = "family_member_type";}
		
		 ArrayList<BoardTO> datas = new  ArrayList<BoardTO>();

		 String sql = "select * from board where category_seq=? and "+field+" like ? and is_private=0 "
		 		+ "order by seq desc limit ?,?";
		 Object[] args = {categorySeq, "%"+keyword+"%", (cpage-1)*recordPerPage, recordPerPage};
		 datas = (ArrayList<BoardTO>)jdbcTemplate.query(sql, args, boardRowMapper);

		 return datas;
	}
	
	/* 정렬 메서드 */
	public ArrayList<BoardTO> listSort(String categorySeq,String field,String keyword,String sort,int cpage,int recordPerPage){
		
		if(sort == null && sort.equals("")) { sort = "seq";	}
		
		ArrayList<BoardTO> datas = new ArrayList<BoardTO>();
		
		String sql = "select * from board where category_seq=? and "+field+" like ? and is_private=0 "
				+ "order by "+sort+" desc limit ?, ?";
			
		Object[] args = {categorySeq, "%"+keyword+"%", (cpage-1)*recordPerPage, recordPerPage};
		datas = (ArrayList<BoardTO>)jdbcTemplate.query(sql, args, boardRowMapper);
		
		return datas;
	
	}
	
	public BoardTO thumbview(BoardTO to) {
		//조회수 증가

		String seq = to.getSeq();
		String sql = "update board set hit=hit+1 where seq=?";
		jdbcTemplate.update(sql, seq);

		sql ="select * from board where seq=?";
		Object[] args = {seq};
		to = jdbcTemplate.queryForObject(sql, args, boardRowMapper);
		
		return to;
	}
	
	public int thumbdelete(BoardTO to) {
		int flag = 1;
		
		String sql = "delete from board where nickname=? and category_seq=? and seq=?";
		Object[] args = {to.getNickname(), to.getCategorySeq(), to.getSeq()};
		
		int result = jdbcTemplate.update(sql, args);
			
		if( result ==1 ) { flag = 0; }

		return flag;
	}
	
	public BoardTO thumbmodify(BoardTO to) {

		String sql ="select * from board where nickname=? and seq=? and category_seq=?";
		Object[] args = {to.getNickname(), to.getSeq(), to.getCategorySeq()};
		to = jdbcTemplate.queryForObject(sql, args, boardRowMapper);
		return to;
	}
	
	public int thumbmodifyOK(BoardTO to) {

		int flag = 2;
		
		String sql ="update board set sale_status=?,family_member_type=?,"
					+"title=?,content=?,thumb_url=?,nickname=?,updated_at=now() "
					+"where seq=? and nickname=?";
	
		Object[] args = {to.getSaleStatus(), to.getFamilyMemberType(), to.getTitle(), to.getContent(),
				to.getThumbUrl(),to.getNickname(), to.getSeq(), to.getNickname()};
		
		flag = jdbcTemplate.update(sql, args);
		
		return flag;
	}
}
