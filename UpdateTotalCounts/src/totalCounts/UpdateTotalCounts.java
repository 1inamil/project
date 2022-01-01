package totalCounts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UpdateTotalCounts {
	
	private String url = "jdbc:mysql://localhost:3306/greenpaw";
	private String user = "greenpaw";
	private String password = "123456";
	    
	
	public ArrayList<TotalCountTO> getTotalCount(){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<TotalCountTO> list = new ArrayList<TotalCountTO>();
		String startDate = "";
		String endDate = "";
		
		try {
			conn = DriverManager.getConnection(url, user, password);	
			
			String sql = "select date_sub(date_format(now(), '%Y%m%d'), interval 2 month) as startDate, date_format(now(), '%Y%m%d') as endDate";
			
			pstmt = conn.prepareStatement( sql );
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				startDate = rs.getString("startDate").replaceAll("-", "");
				endDate = rs.getString("endDate");
			}
			
			sql = "select sido, sidoCode, gugun, gugunCode from location_code";
			pstmt = conn.prepareStatement( sql );
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				TotalCountTO to = new TotalCountTO();
				
				to.setSido(rs.getString("sido"));
				to.setSidoCode(rs.getString("sidoCode"));
				to.setGugun(rs.getString("gugun"));
				to.setGugunCode(rs.getString("gugunCode"));
				to.setSearchStart(startDate);
				to.setSearchEnd(endDate);
				
				list.add(to);
			}
				
			
		} catch( SQLException e ) {
			System.out.println( "[에러] " + e.getMessage() );
		} finally {
			if( pstmt != null ) try { pstmt.close(); } catch(SQLException e) {}
			if( rs != null ) try { rs.close(); } catch(SQLException e) {}
			if( conn != null ) try { conn.close(); } catch(SQLException e) {}
		}
		
		return list;
	}
	
	public int totalCountsToDB(ArrayList<TotalCountTO> datas) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int count = 0;
		
		 
	    try {
			conn = DriverManager.getConnection(url, user, password);			
			
			String sql =  "update location_code set totalCounts = ?, updated_at = now() where gugunCode = ?";
			pstmt = conn.prepareStatement(sql);

			for(TotalCountTO to : datas) {
				
				pstmt.setString(1, to.getTotalCounts());
				pstmt.setString(2, to.getGugunCode());
				
				int result = pstmt.executeUpdate();
				
				count += result;
				
			}
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			if( pstmt != null ) try { pstmt.close(); } catch(SQLException e) {}
			if( conn != null ) try { conn.close(); } catch(SQLException e) {}
		}	
		
		return count;
	}

}
