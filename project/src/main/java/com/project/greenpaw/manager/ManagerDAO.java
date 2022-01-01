package com.project.greenpaw.manager;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.greenpaw.community.common.BoardListTO;

@Repository
public class ManagerDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<ManagerTO> managerRowMapper = BeanPropertyRowMapper.newInstance(ManagerTO.class);
	
	//전체 조회 메서드
	public ArrayList<ManagerTO> getList(int cpage, int recordPerPage) {
		
		ArrayList<ManagerTO> datas = new ArrayList<ManagerTO>();
		
		String sql = "SELECT u.nickname, u.mail, u.created_at, u.auth_type, count(b.seq) as count"
				+ " from user u left outer join board b"
				+ " on u.nickname = b.nickname group by u.nickname order by u.created_at asc limit ?,?";
		Object[] args = {(cpage-1)*recordPerPage, recordPerPage};
		datas = (ArrayList<ManagerTO>) jdbcTemplate.query(sql, args, managerRowMapper);
		
		return datas;
	}

	//페이지 매서드
	public BoardListTO boardList(BoardListTO listTO, String field,String keyword) {
		
		if (field.equals("u.mail")) { 
			field = "mail";
		}else if(field.equals("u.nickname")) {	
			field = "nickname";	
		}
		
		int cpage = listTO.getCpage();
		int recordPerPage = listTO.getRecordPerPage();
		int blockPerPage = listTO.getBlockPerPage();
		
		ArrayList<ManagerTO> userLists = new ArrayList<ManagerTO>();
		int count = 0;
		
		String countSql = "select count(*) from user where "+ field +" like ?";
		Object[] countArgs = {"%"+keyword+"%"};
		count = jdbcTemplate.queryForObject(countSql, countArgs, Integer.class);

		listTO.setTotalRecord(count );
		listTO.setTotalPage( ( ( listTO.getTotalRecord() -1 ) / recordPerPage ) + 1 );
		
		String sql = "select nickname, mail, date_format(created_at, '%Y.%m.%d') created_at, auth_type from user where "
				+field+" like ? order by created_at asc limit ?, ?";
		Object[] args = {"%"+keyword+"%", (cpage-1)*recordPerPage, recordPerPage};		
		userLists = (ArrayList<ManagerTO>) jdbcTemplate.query(sql, args, managerRowMapper);
		
		listTO.setUserLists( userLists );
		
		listTO.setStartBlock( ( ( cpage -1 ) / blockPerPage ) * blockPerPage + 1 );
		listTO.setEndBlock( ( ( cpage -1 ) / blockPerPage ) * blockPerPage + blockPerPage );
		
		if( listTO.getEndBlock() >= listTO.getTotalPage() ) {
			listTO.setEndBlock( listTO.getTotalPage() );
		}
		
		return listTO;
	}
	
	//field와 keyword를 통한 검색 메서드
	public ArrayList<ManagerTO> getSearchList(String field, String keyword, int cpage,int recordPerPage){

		ArrayList<ManagerTO> datas = new  ArrayList<ManagerTO>();
		 
		String sql = "SELECT u.nickname, u.mail, u.created_at, u.auth_type, count(b.seq) as count"
				+ " from user u left outer join board b"
				+ " on u.nickname = b.nickname where "+field+" like ? group by u.nickname order by u.created_at asc limit ?,?";
		Object[] args = {"%"+keyword+"%", (cpage-1)*recordPerPage, recordPerPage};		
			datas = (ArrayList<ManagerTO>) jdbcTemplate.query(sql, args, managerRowMapper);
			
		return datas;
	}
	
	//user 회원 구분 변경하기
	public int modifyOK(String nickname, String status) {

		int result = 0;
		String sql ="update user set auth_type='"+status+"' where nickname in("+nickname+")";
		result = jdbcTemplate.update(sql);	
		
		return result;
	}
	
	//날짜 계산
	public ArrayList<ManagerTO> dateSearch(String startDate, String endDate, int cpage,int recordPerPage){

		ArrayList<ManagerTO> datas = new ArrayList<ManagerTO>();
		String sql = "SELECT u.nickname, u.mail, u.created_at, u.auth_type, count(b.seq) as count"
				+ " from user u left outer join board b"
				+ " on u.nickname = b.nickname where u.created_at between ? and ? group by u.nickname order by u.created_at asc limit ?,?";
		Object[] args = {startDate, endDate, (cpage-1)*recordPerPage, recordPerPage};
		datas = (ArrayList<ManagerTO>) jdbcTemplate.query(sql, args, managerRowMapper);	

		return datas;
	}
	
	//총 회원 계산
	public int getCount(){
	
		int countlist = 0;
		String sql = "select count(*) from user";
		countlist = jdbcTemplate.queryForObject(sql, Integer.class);
		
		return countlist;
	}
	
	//기간 검색 건수 계산 
	public BoardListTO getPagingByDate(BoardListTO listTO, String field, String keyword, String startDate,
			String endDate) {

		int cpage = listTO.getCpage();
		int recordPerPage = listTO.getRecordPerPage();
		int blockPerPage = listTO.getBlockPerPage();
		
		int count = 0;

		String sql = "select count(*) from user where "+field+" like ? and created_at between ? and ?";
		Object[] args = {"%" + keyword + "%", startDate, endDate};
		count = jdbcTemplate.queryForObject(sql, args, Integer.class);
		
		listTO.setTotalRecord(count);
		listTO.setTotalPage(((listTO.getTotalRecord() - 1) / recordPerPage) + 1);

		listTO.setStartBlock(((cpage - 1) / blockPerPage) * blockPerPage + 1);
		listTO.setEndBlock(((cpage - 1) / blockPerPage) * blockPerPage + blockPerPage);
		
		if (listTO.getEndBlock() >= listTO.getTotalPage()) {
			listTO.setEndBlock(listTO.getTotalPage());
		}

		return listTO;
	}


}
