

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.io.BufferedReader;
import java.io.IOException;

public class ApiExplorer {
	
	String encServiceKey = "=L62gx5%2F6HyMBJ%2BrA0GNMzjbisl%2F9WAn67zhy4GIHFnRiR0qXOSLA7E7PdY1mraAxSLpZlLDods0z6f4Zdv5smQ%3D%3D";

	String sidoRequest = "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sido";
	String sigunguRequest ="http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/sigungu";
	String animalRequest ="http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic";
	
	
	
	/*시도 정보 받아오는 매서드*/
	public String getSidoInfo() throws IOException {
		
		StringBuilder urlBuilder = new StringBuilder(sidoRequest); /*URL*/ 
	    urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + encServiceKey); /*Service Key*/
	    urlBuilder.append("&"+ URLEncoder.encode("numOfRows","UTF-8") + "=" + "100"); 
		
	    URL url = new URL(urlBuilder.toString());
	     
	     //System.out.println(url);
	        
	     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	     conn.setRequestMethod("GET");
	     conn.setRequestProperty("Content-type", "application/json");
	     
	     //System.out.println("Response code: " + conn.getResponseCode());
	     
	     BufferedReader br;
	     if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	    	 br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	     } else {
	    	 br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	     }
	     
	     StringBuilder sbResult = new StringBuilder();
	     String line;
	     
	     while ((line = br.readLine()) != null) {
	    	 sbResult.append(line);
	     }
	     
	     br.close();
	     conn.disconnect();
	     
	     String result = sbResult.toString();
	     //System.out.println(result);
	     
	     return result;
	    
	}
	
	
	/*시 별로 군구 정보 받아오는 매서드*/
	public String getGugunInfo(String sidoCode) throws IOException{
    	
	     StringBuilder urlBuilder = new StringBuilder(sigunguRequest); /*URL*/
	     
	     urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + encServiceKey); /*Service Key*/
	     urlBuilder.append("&"+ URLEncoder.encode("upr_cd","UTF-8") + "=" + sidoCode); //지역 코드
	     urlBuilder.append("&"+ URLEncoder.encode("numOfRows","UTF-8") + "=" + "100"); 
	    
	     URL url = new URL(urlBuilder.toString());
	     
	     //System.out.println(url);
	        
	     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	     conn.setRequestMethod("GET");
	     conn.setRequestProperty("Content-type", "application/json");
	     
	     //System.out.println("Response code: " + conn.getResponseCode());
	     
	     BufferedReader br;
	     if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	    	 br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	     } else {
	    	 br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	     }
	     
	     StringBuilder sbResult = new StringBuilder();
	     String line;
	     
	     while ((line = br.readLine()) != null) {
	    	 sbResult.append(line);
	     }
	     
	     br.close();
	     conn.disconnect();
	     
	     String result = sbResult.toString();
	     //System.out.println(result);
	     
	     return result;
	     
   }
	
	
	/* 유기동물 정보 받아오는 매서드 */
	public String getAnimalInfo(String loc, String startDate, String endDate) throws IOException{
    	
	     StringBuilder urlBuilder = new StringBuilder(animalRequest); /*URL*/
	     
	     urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + encServiceKey); /*Service Key*/
	     urlBuilder.append("&"+ URLEncoder.encode("upr_cd","UTF-8") + "=" + loc); //지역 코드
	     urlBuilder.append("&"+ URLEncoder.encode("&bgnde","UTF-8") + "=" + startDate); //검색 시작일
	     urlBuilder.append("&"+ URLEncoder.encode("&endde","UTF-8") + "=" + endDate); //검색 종료일
	     
	     URL url = new URL(urlBuilder.toString());
	     
	     System.out.println(url);
	        
	     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	     conn.setRequestMethod("GET");
	     conn.setRequestProperty("Content-type", "application/json");
	     
	     System.out.println("Response code: " + conn.getResponseCode());
	     
	     BufferedReader br;
	     if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	    	 br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	     } else {
	    	 br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	     }
	     
	     StringBuilder sbResult = new StringBuilder();
	     String line;
	     
	     while ((line = br.readLine()) != null) {
	    	 sbResult.append(line);
	     }
	     
	     br.close();
	     conn.disconnect();
	     
	     String result = sbResult.toString();
	     System.out.println(result);
	     
	     return result;
	     
    }
    		
}