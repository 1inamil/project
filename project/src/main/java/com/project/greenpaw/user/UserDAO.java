package com.project.greenpaw.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Repository("userDao")
public class UserDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<UserTO> userMapper = BeanPropertyRowMapper.newInstance(UserTO.class);
		
	//회원 가입시 닉네임 중복 체크 => 중복시 flag = 0
	public int getNickname(UserTO user){
	
		//System.out.println("getUser() : "+user.getNickname());
		
		int flag = 2; // nickname 중복시 flag = 0
		int result = 2;
		
		String sql = "select count(*) from user where nickname = ?";
		Object[] args = {user.getNickname()};		
		
		try {
			result = jdbcTemplate.queryForObject(sql, args, Integer.class);
		} catch (EmptyResultDataAccessException e) {
			// TODO Auto-generated catch block
			flag = 1;
		}
		if(result == 1) flag = 0;
		return flag;
	}
		
	//회원 가입시 이메일 중복체크
	public int getMail(UserTO user){
		
		//System.out.println("getMail() : "+user.getMail());
		int flag = 2; // 이메일 중복시 flag = 0
		int result = 2;
		
		String sql = "select count(*) from user where mail = ?";
		Object[] args = {user.getMail()};		
		
		try {
			result = jdbcTemplate.queryForObject(sql, args, Integer.class);
		} catch (EmptyResultDataAccessException e) {
			// TODO Auto-generated catch block
			flag = 1; //이메일 검색 결과가 null이면 flag = 1
		}
		
		if(result == 1) flag = 0;
		
		//System.out.println("result: " + result);
		//System.out.println("flag: "+flag);
		
		return flag;
	}
		
	//패스워드 암호화
	public String sha256(String password) {
		
		StringBuffer EncryptPs = null;
	
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			EncryptPs = new StringBuffer();
			
			for(int i = 0; i<hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				
				if(hex.length() ==1) {
					EncryptPs.append('0');
					
				}else {
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
	
		
	//서비스 회원가입
	public int joinUser(UserTO user) {
	
		System.out.println("joinUser() 호출 : "+user.getNickname());
		
		int flag = 0;
		
		String sql = "insert into user values("
				+ "?, 0, ?, ?,"
				+ "'회원', ?, ?, ?,"
				+ "now(), now())";
		
		Object[] args = {user.getNickname(), user.getMail(), user.getPassword(), 
				user.getProfileUrl(), user.getHintPassword(), user.getAnswerPassword()};
		
		flag = jdbcTemplate.update(sql, args);
		
		return flag;
	}
	
	
	//서비스 로그인 ok
	public int logInOk(UserTO to) {
		
		int result = 2;
		
		String sql = "Select count(*) from user where mail =? and password =?";
		Object[] args = {to.getMail(), to.getPassword()};
		
		result = jdbcTemplate.queryForObject(sql, args, Integer.class);
			
		//확인
		System.out.println(result+" (1: 로그인 성공 / 0: 로그인 실패)");
		
		return result;
	};
	
	//세션에 넣을 데이터 가져오기 -> 로그인시 계정 정보 넣기
	public UserTO getSessionData(UserTO to) {
	
			String sql = "Select mail, nickname, auth_type from user where mail =? and password =?";
			Object[] args = {to.getMail(), to.getPassword()};
			
			//to = jdbcTemplate.queryForObject(sql, args, new UserRowMapper());
			to = jdbcTemplate.queryForObject(sql, args, userMapper);
			
			return to;
	}
	
	//비밀번호 분실시 비밀번호 재설정
	public int forgotPassword(UserTO to) {
		
		int result = 2;
		String sql = "update user set password = ?, updated_at = now() "
				+ "where mail =? and hint_password =? and answer_password =?";
		Object args[] = {to.getPassword(), to.getMail(), 
				to.getHintPassword(), to.getAnswerPassword()};
		
		result = jdbcTemplate.update(sql, args);
		
		return result;
	}
	
	
	//카카오정보가 db에 있는지 확인
	public int kakaoIdCheck(UserTO to) {
		
		int result = 0;

		String sql = "select count(*) from user where mail=? and from_social=1";
		Object[] args = {to.getMail()};
		result = jdbcTemplate.queryForObject(sql, args, Integer.class);
		
		return result;
	}
	
	//카카오로그인이 처음일 때 가입
	public int kakaoJoin(UserTO to) {
		
		int result = 0;	
		
		String sql = "insert into user (from_social,mail,nickname,auth_type,hint_password,answer_password,created_at,updated_at) values(1,?,?,'회원','','',now(),now())";
		Object[] args = {to.getMail(), to.getNickname()};

		result = jdbcTemplate.update(sql, args); //1: 성공
			
		return result;	
	}
	
	//카카오 닉네임이 중복 되는지 확인
	public int kakaoNicknamecheck(UserTO to) {
	
		int result=0;
		
		String sql = "select count(*) as count from user where nickname=?";
		Object[] args = {to.getNickname()};
		
		result = jdbcTemplate.queryForObject(sql, args, Integer.class);

		return result;
	}
	
	//카카오 유저 닉네임 가져오기
	public UserTO loginInformation(String email){
		
		UserTO to = new UserTO();
	
		String sql = "select nickname, auth_type from user where mail=? and from_social=1";
		Object[] args = {email};

		to = jdbcTemplate.queryForObject(sql, args, userMapper);
			
		return to;
	}
	
	//탈퇴시 소셜인지 아닌지 확인 -> 소셜:1 / 일반: 0
	public int socialCheck(String nickname) {
		
		int result = 0;
		
		UserTO to = new UserTO();
		
		String sql = "select from_social from user where mail=?";
		Object[] args = {nickname};
		result = jdbcTemplate.queryForObject(sql, args, Integer.class);
		
		System.out.println(result);

		return result;
		
	}
}
