package com.project.greenpaw.user;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<UserTO>{

	public UserTO mapRow(ResultSet rs, int rowNum) throws SQLException{
		
		ResultSetMetaData metaRs = rs.getMetaData();
		
		System.out.println("컬럼 숫자 " + metaRs.getColumnCount()); 
		//컬럼명
		for(int i = 1; i<=metaRs.getColumnCount(); i++) {
			System.out.println(metaRs.getColumnName(i));
		}
		//나온 값
		rs.beforeFirst();
		while(rs.next()) {
			for(int i = 1; i<=metaRs.getColumnCount(); i++) {
			System.out.println(rs.getString(metaRs.getColumnName(i)));
			}
		}
		
		UserTO to = new UserTO();
		
		to.setNickname(rs.getString("nickname"));		
		to.setMail(rs.getString("mail"));
		to.setPassword(rs.getString("password"));
		to.setAuthType(rs.getString("auth_type"));
		to.setProfileUrl(rs.getString("profile_url"));
		to.setFromSocial(rs.getString("from_social"));
		to.setHintPassword(rs.getString("hint_password"));
		to.setAnswerPassword(rs.getString("answer_password"));
		to.setCreatedAt(rs.getString("created_at"));
		to.setUpdatedAt(rs.getString("updated_at"));
		
		return to;
		
	}
}
