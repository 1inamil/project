package com.project.greenpaw.lostanimals;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TotalCountDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<TotalCountTO> totalCountRowMapper = BeanPropertyRowMapper.newInstance(TotalCountTO.class);
	
	public ArrayList<TotalCountTO> getTotalCountFromDB(ArrayList<TotalCountTO> list) {
			
		ArrayList<TotalCountTO> datas = new ArrayList<TotalCountTO>();
		
		String sql = "select sidoShort, gugun, totalCounts, x, y from location_code where sidoShort =? and gugun = ?";
		
		for(TotalCountTO to:list) {
			Object[] args = {to.getSidoShort(), to.getGugun()};
			to = jdbcTemplate.queryForObject(sql, args, totalCountRowMapper);
			datas.add(to);
			args = null;
		}
		
		return datas;
		
	}

}
