package Update;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataReader {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/greenpaw";
		String user ="greenpaw";
		String password="123456";
		
		BufferedReader br = null;
		Connection conn =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			br = new BufferedReader(new FileReader("C:\\jproject\\workspace\\addrdata.csv")); //파일 경로 입력\
			
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,password);
			
			String sql = "update location_code set x=?, y=? where sidoShort=? and gugun=?";
			pstmt = conn.prepareStatement(sql);
			
			int count = 0;
			
			String data =null;
			
			while((data = br.readLine()) !=null) {
				String[] datas = data.split(",");
				for(int i=1; i<=7; i++) {
					pstmt.setString(1, datas[5]);
					pstmt.setString(2, datas[6]);
					pstmt.setString(3, datas[4]);
					pstmt.setString(4, datas[2]);
					
					
				}
				int result = pstmt.executeUpdate();
				count +=result;
			}
			
			System.out.println("개수:"+count);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.print("[에러]"+e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("[에러]"+e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.print("[에러]"+e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.print("[에러]"+e.getMessage());
		}
		
		
		
	}
	
}
