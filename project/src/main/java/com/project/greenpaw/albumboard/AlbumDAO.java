package com.project.greenpaw.albumboard;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AlbumDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<AlbumTO> albumRowMapper = BeanPropertyRowMapper.newInstance(AlbumTO.class);
			
	
	public AlbumListTO boardList(AlbumListTO listTO) {
		
		int cpage = listTO.getCpage();
		int recordPerPage = listTO.getRecordPerPage();
		int blockPerPage = listTO.getBlockPerPage();
		
		listTO.setStartBlock((cpage-1)/blockPerPage*blockPerPage+1);
		listTO.setEndBlock((cpage-1)/blockPerPage*blockPerPage+blockPerPage);
		
		String sql= "";
		String countSql= "";
		ArrayList<AlbumTO> boardLists = null;
		int count = 0;
		
		if(listTO.getFamilyMemberType() == null || listTO.getFamilyMemberType().equals("all")||listTO.getFamilyMemberType().equals("null")) {
			sql = "select nickname, seq, title, content, thumb_url, family_member_type, is_private,"
					+ "created_at, updated_at from board "
					+ "where nickname = ? and category_seq = ? order by seq desc "
					+ "limit ?, ?";
			
			Object[] args = {listTO.getNickname(), listTO.getCatSeq(), recordPerPage*(cpage-1), recordPerPage};
			//boardLists = (ArrayList<AlbumTO>) jdbcTemplate.query(sql, args, rowMapper);
			boardLists = (ArrayList<AlbumTO>) jdbcTemplate.query(sql, args, albumRowMapper);
			
			countSql = "select count(*) from board where nickname = ? and category_seq = ?";
			Object[] countArgs = {listTO.getNickname(), listTO.getCatSeq()};
			count = jdbcTemplate.queryForObject(countSql, countArgs, Integer.class);
			
			
		}else {
			sql = "select nickname, seq, title, content, thumb_url, family_member_type, is_private,"
					+ "created_at, updated_at from board "
					+ "where nickname = ? and family_member_type = ? and category_seq = ? order by seq desc "
					+ "limit ?, ?";
			
			Object[] args = {listTO.getNickname(), listTO.getFamilyMemberType() ,listTO.getCatSeq(), recordPerPage*(cpage-1), recordPerPage};
			boardLists = (ArrayList<AlbumTO>) jdbcTemplate.query(sql, args, albumRowMapper);
		
			countSql = "select count(*) from board where nickname = ? and family_member_type = ? and category_seq = ?";
			Object[] countArgs = {listTO.getNickname(), listTO.getFamilyMemberType() ,listTO.getCatSeq()};
			count = jdbcTemplate.queryForObject(countSql, countArgs, Integer.class);
		
		};
		
		//게시물 카운트
		listTO.setTotalRecord(count);
		listTO.setTotalPages( (listTO.getTotalRecord()-1) / recordPerPage +1 );
		listTO.setBoardLists(boardLists);
		
		if(listTO.getEndBlock() >= listTO.getTotalPages()) {
			listTO.setEndBlock(listTO.getTotalPages());
		}
		
		return listTO;
	}
	
	public int writeOk(AlbumTO to) {
		
		int flag = 0;
		
		String sql = "insert into board values ("
				+ "0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now(), ?) ";

		Object[] args = {to.getCatSeq(), to.getSubTitle(), to.getSaleStatus(), to.getFamilyMemberType(),
				to.getTitle(), to.getContent(), to.getThumbUrl(), to.getNickname(), to.getLikeCount(), 
				to.getHit(), to.getIsPrivate()};
		
		int result = jdbcTemplate.update(sql, args);
		
		if( result == 1 ) {
			flag = 1; //성공
		}
		
		return flag;
	}
	
	public AlbumTO view(AlbumTO to) {

		/*
		 * String sql =
		 * "select nickname, seq, title, content, thumb_url, family_member_type, " +
		 * "is_private, created_at, updated_at from board " +
		 * "where nickname = ? and seq=? and category_seq = ?";
		 * 
		 * Object args[] = {to.getNickname(), to.getSeq(), to.getCatSeq()};
		 * 
		 * to = jdbcTemplate.queryForObject(sql, args, albumRowMapper );
		 */
			
			String sql = "select nickname, seq, title, content, thumb_url, family_member_type, "
					+ "is_private, created_at, updated_at from board "
					+ "where seq=? and category_seq = ?";
			
			Object args[] = {to.getSeq(), to.getCatSeq()};
			
			to = jdbcTemplate.queryForObject(sql, args, albumRowMapper );
		
		return to;
	}
	
	public int deleteOk(AlbumTO to) {

		int flag = 1;

		String sql = "delete from board where nickname = ? and seq =?"; 
		Object[] args = {to.getNickname(), to.getSeq()}; 
		int result = jdbcTemplate.update(sql, args);
		
		if( result == 1 ) {	flag = 0; } //성공 
		
		return flag;
	}
	
	public int modifyOk(AlbumTO to) {
		
		int flag = 2;
		
		String sql = "update board set family_member_type=?, title=?, content=?, thumb_url=?, "
				+ "nickname=?, updated_at = now(), is_private=? "
				+ "where nickname = ? and seq=?";
			
		Object[] args = {to.getFamilyMemberType(), to.getTitle(), to.getContent(), to.getThumbUrl(),
				to.getNickname(), to.getIsPrivate(), to.getNickname(), to.getSeq()};
		
		flag = jdbcTemplate.update(sql, args);
		
		return flag;
	}
	
/*
 * Community album
 */
	
	
	public AlbumListTO communityAlbumList(AlbumListTO listTO) {

		int cpage = listTO.getCpage();
		int recordPerPage = listTO.getRecordPerPage();
		int blockPerPage = listTO.getBlockPerPage();
		
		listTO.setStartBlock((cpage-1)/blockPerPage*blockPerPage+1);
		listTO.setEndBlock((cpage-1)/blockPerPage*blockPerPage+blockPerPage);
		
		ArrayList<AlbumTO> boardLists = null;
		
		String sql= "";
		String countSql= "";
		int count = 0;
		
		if(listTO.getFamilyMemberType() == null || listTO.getFamilyMemberType().equals("all")||listTO.getFamilyMemberType().equals("null")) {
							
			sql = "select nickname, seq, title, content, thumb_url, family_member_type, is_private,"
					+ "created_at, updated_at, hit from board "
					+ "where is_private = ? and category_seq >= 2 and category_seq <= 3 "
					+ "order by created_at desc, seq desc "
					+ "limit ?, ?";
			
			Object[] args = {listTO.getIsPrivate(), recordPerPage*(cpage-1), recordPerPage};
			boardLists = (ArrayList<AlbumTO>) jdbcTemplate.query(sql, args, albumRowMapper);
			
			countSql = "select count(*) from board where is_private = ? and category_seq >= 2 and category_seq <= 3";
			Object[] countArgs = {listTO.getIsPrivate()};
			count = jdbcTemplate.queryForObject(countSql, countArgs, Integer.class);
			
		}else {
			
			sql = "select nickname, seq, title, content, thumb_url, family_member_type, is_private,"
					+ "created_at, updated_at, hit from board "
					+ "where family_member_type = ? and is_private = ? and category_seq >= 2 and category_seq <= 3 "
					+ "order by created_at desc, seq desc "
					+ "limit ?, ?";
			
			Object[] args = {listTO.getFamilyMemberType(), listTO.getIsPrivate(), recordPerPage*(cpage-1), recordPerPage};
			boardLists = (ArrayList<AlbumTO>) jdbcTemplate.query(sql, args, albumRowMapper);
			
			countSql = "select count(*) from board where family_member_type = ? and is_private = ? and category_seq >= 2 and category_seq <= 3";
			Object[] countArgs = {listTO.getFamilyMemberType(), listTO.getIsPrivate()};
			count = jdbcTemplate.queryForObject(countSql, countArgs, Integer.class);
			
		};
			
		listTO.setTotalRecord(count);
		listTO.setTotalPages((listTO.getTotalRecord()-1)/recordPerPage+1);
		listTO.setBoardLists(boardLists);
		
		if(listTO.getEndBlock() >= listTO.getTotalPages()) {
			listTO.setEndBlock(listTO.getTotalPages());
		}
			
		return listTO;
	}
	
	public AlbumTO CommunityAlbumView(AlbumTO to) {

		String sql = "update board set hit = hit+1 where seq=?";
		Object hitArgs[] = {to.getSeq()};
		jdbcTemplate.update(sql, hitArgs);
			
		sql = "select nickname, seq, title, content, thumb_url, family_member_type, "
				+ "is_private, created_at, updated_at from board "
				+ "where seq=?";
		Object[] args = {to.getSeq()};
		to = jdbcTemplate.queryForObject(sql, args, albumRowMapper);
		
		return to;
	}
	

}
