package com.project.greenpaw.lostanimals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("apiDao")
public class ApiDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<ApiTO> apiRowMapper = BeanPropertyRowMapper.newInstance(ApiTO.class);

	public String[] getCode( String sido, String gugun) {
		
		//System.out.println(" DAO sido / gugun : "+ sido +" / "+gugun);

		if (gugun.contains(" ")) {
            String[] tmp = gugun.split(" ");
            //System.out.println(tmp[0]);
            switch ( tmp[0]) {
                case "용인시":
                    if (!tmp[1].equals("기흥구")) {
                        gugun = tmp[0];
                    }
                    break;
                case "창원시":
                    if (tmp[1].equals("마산회원구") || tmp[1].equals("마산합포구")) {
                        gugun = "창원시 마산합포회원구";
                    } else if (tmp[1].equals("진해구")) {
                        gugun = "창원시 진해구";
                    } else {
                        // 위의 두 경우가 아닐때 창원은 아래값으로 항상 디폴트
                        gugun = "창원시 의창성산구";
                    }
                    break;
                default:
                    gugun = tmp[0];
            }
            
            //System.out.println(" 띄어쓰기 있는 애들 gugun "+ gugun);
        }
		
		ApiTO to = null;
		
		String sql = "select sidoCode, gugunCode from location_code where sido like ? and gugun=?";
		Object[] args = {sido+"%", gugun};
		
		try {
			to = jdbcTemplate.queryForObject(sql, args, apiRowMapper);
		
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.getMessage();
			return null;
		}
		
		String[] result = new String[2];
			
		// 정상 실행했을 때 배열 초기화
		result[0] = to.getSidoCode();
		result[1] = to.getGugunCode();
			
		//System.out.println("sidocode / guguncode : "+result[0]+" / "+result[1]);
			
		return result;
	}
}
