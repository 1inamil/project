package com.project.greenpaw.mypage;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.greenpaw.user.UserDaoInterface;

@Repository("mypageDao")
public class MypageDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private UserDaoInterface userDaoInterface;
	
	private RowMapper<MypageTO> mypageMapper = BeanPropertyRowMapper.newInstance(MypageTO.class);
	
	//세션에 없는 회원 정보 가져오기
	public MypageTO getUserInfo(MypageTO to) {

		String sql = "select auth_type, created_at, from_social from user where nickname =? and mail = ?";
		Object[] args = {to.getNickname(), to.getMail()};
		to = jdbcTemplate.queryForObject(sql, args, mypageMapper);
		
		return to;
	}
	
	//작성 게시글 수
	public MypageTO getPostCount(MypageTO to) {
			
		int totalCounts = 0;
		int eachCount = 0;
		ArrayList<Integer> counts = new ArrayList<Integer>();

		//총 게시글 수
		String sql = "select count(seq) as count from board where nickname = ?";
		Object[] args = {to.getNickname()};
		totalCounts = jdbcTemplate.queryForObject(sql, args, Integer.class);
		
		to.setTotalPostCount(totalCounts);

		//각 게시판 별 게시글 수
		for(int i = 1; i<=6; i++) {
			sql = "select count(seq) as count from board where nickname = ? and category_seq=?";
			Object[] varArgs = {to.getNickname(), i};
			eachCount = jdbcTemplate.queryForObject(sql, varArgs, Integer.class);
			counts.add(eachCount);
		}
			
		to.setPostCat1(counts.get(0));
		to.setPostCat2(counts.get(1));
		to.setPostCat3(counts.get(2));
		to.setPostCat4(counts.get(3));
		to.setPostCat5(counts.get(4));
		to.setPostCat6(counts.get(5));
						
		return to;
		
	}
	
	//작성 댓글 수
	public MypageTO getCommentCount(MypageTO to) {
		
		int totalCounts = 0;
		int eachCount = 0;
		ArrayList<Integer> counts = new ArrayList<Integer>();
		
		//총 댓글 수 구하기
		String sql = "select count(seq) as count from comment where nickname = ?";
		Object[] args = {to.getNickname()};
		totalCounts = jdbcTemplate.queryForObject(sql, args, Integer.class);
		to.setTotalCommentCount(totalCounts);
		
		//각 게시판별 댓글 수
		for(int i = 1; i<=6; i++) {
			sql = "select count(seq) as count from comment where nickname = ? and board_seq=?";
			Object[] varArgs = { to.getNickname(), i};
			eachCount = jdbcTemplate.queryForObject(sql, varArgs, Integer.class);
			counts.add(eachCount);
		}
		
		to.setCommentCount1(counts.get(0));
		to.setCommentCount2(counts.get(1));
		to.setCommentCount3(counts.get(2));
		to.setCommentCount4(counts.get(3));
		to.setCommentCount5(counts.get(4));
		to.setCommentCount6(counts.get(5));
			
		return to;
	}
	
	//비밀번호 변경
	public int changePassword(MypageTO to) {

		int flag = 0;
		
		String EncryptOldPs = userDaoInterface.sha256(to.getOldPassword());
		String EncryptNewPs = userDaoInterface.sha256(to.getNewPassword());
		
		/*
		 * System.out.println("oldPs: "+to.getOldPassword());
		 * System.out.println("newPs: "+to.getNewPassword());
		 * System.out.println("EncryptOldPs: "+EncryptOldPs);
		 * System.out.println("EncryptNewPs: "+EncryptNewPs);
		 */
				
		String sql = "update user set password = ?, updated_at = now() where password = ? and nickname = ? and mail = ?";
		Object[] args = {EncryptNewPs, EncryptOldPs, to.getNickname(), to.getMail()};	
		
		try {
			flag = jdbcTemplate.update(sql, args);
		} catch (EmptyResultDataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return flag;
	}
	
	//회원 탈퇴
	public int deleteAccount(MypageTO to) {

		int flag = 2;
		
		//비밀번호 암호화
		String EncryptPs = userDaoInterface.sha256(to.getOldPassword());
		
		String sql = "update user set auth_type = '탈퇴', updated_at=now() where password = ? and nickname = ? and mail = ?";
		Object[] args = {EncryptPs, to.getNickname(), to.getMail()};
		
		try {
			flag = jdbcTemplate.update(sql, args);
		} catch (EmptyResultDataAccessException e) {
			// TODO Auto-generated catch block
			flag = 0;
		}
		
		System.out.println("1: 회원탈퇴, 0: 탈퇴 실패 => "+ flag);
		
		return flag;
	}
	
	//카카오 계정 탈퇴
	public int deleteKaKaoAccount(MypageTO to) {
		
		int flag = 2;
		
		String sql = "update user set auth_type = '탈퇴', updated_at=now() where from_social = 1 and nickname = ? and mail = ?";
		Object[] args = {to.getNickname(), to.getMail()};
		try {
			flag = jdbcTemplate.update(sql, args);
		} catch (EmptyResultDataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = 0;
		}
		
		return flag;
	}
	
	//메모 불러오기
	public MypageTO getNote(MypageTO to) {
		
		String sql = "select note from note where nickname = ?";
		Object[] args = {to.getNickname()};
		try {
			to.setNote(jdbcTemplate.queryForObject(sql, args, String.class));
		} catch (EmptyResultDataAccessException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		
		return to;
	}
	
	//메모 업데이트
	public int editNote(MypageTO to) {
		
		int result = 2;
		
		String sql = "delete from note where nickname = ?";
		try {
			jdbcTemplate.update(sql, to.getNickname());
		} catch (EmptyResultDataAccessException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

		if(!to.getNote().equals("")) {
			sql = "insert into note values(?, ?)";
			Object[] args = {to.getNickname(), to.getNote()};
			result = jdbcTemplate.update(sql, args);
		}
		
		return result;
	}
}
