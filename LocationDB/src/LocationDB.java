

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LocationDB {
	
	public static void main(String[] args) {
	
		ApiExplorer api = new ApiExplorer();
		Document doc = null;
		ArrayList<String[]> locCode = new ArrayList<String[]>();
		
		try {
			String sidoResult = api.getSidoInfo();
			
			doc = Jsoup.parse(sidoResult);
			Elements sidoItem = doc.getElementsByTag("item");
			
			for(Element sidoElement: sidoItem){
				String sido = sidoElement.select("orgdownNm").text(); //시도 text
				String sidoCode = sidoElement.select("orgCd").text(); //시도 code
				//System.out.println(sido +", "+ sidoCode);
				
				String gugunResult = api.getGugunInfo(sidoCode); //구군 매서드 호출
				
				doc = Jsoup.parse(gugunResult);
				Elements gugunItem = doc.getElementsByTag("item");
				
				for(Element gugunElement: gugunItem) {	
					String gugun = gugunElement.select("orgdownNm").text(); //구군 text
					String gugunCode = gugunElement.select("orgCd").text(); //구군 code
					
					//System.out.println(sido+","+sidoCode+","+gugun+","+gugunCode);
				
					String result = sido + "," + sidoCode + "," + gugun + "," + gugunCode;
					
					//System.out.println(result);
					
					locCode.add(result.split(","));
				}
			};
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
		int totalUpdate = locationToDB(locCode);
		System.out.println(totalUpdate);
		
	}
	
	static int locationToDB(ArrayList<String[]> locCode) {
		
		String url = "jdbc:mysql://localhost:3306/greenpaw";
	    String user = "greenpaw";
	    String password = "123456";

	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    
	    int count = 0;
	    
	    try {
			conn = DriverManager.getConnection(url, user, password);			
			
			String sql =  "insert into location_code values (?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			for(String[] arr : locCode) {
				
				pstmt.setString(1, arr[0]);
				pstmt.setString(2, arr[1]);
				pstmt.setString(3, arr[2]);
				pstmt.setString(4, arr[3]);
				
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
