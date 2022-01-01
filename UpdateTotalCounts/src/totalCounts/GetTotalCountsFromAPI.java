package totalCounts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GetTotalCountsFromAPI {
	String encServiceKey = "=L62gx5%2F6HyMBJ%2BrA0GNMzjbisl%2F9WAn67zhy4GIHFnRiR0qXOSLA7E7PdY1mraAxSLpZlLDods0z6f4Zdv5smQ%3D%3D";
	String animalRequest ="http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic";
	
	/* 유기동물 정보 받아오는 매서드 */
	public ArrayList<TotalCountTO> getTotalCounts(ArrayList<TotalCountTO> list) {
		
		//list => datas
		ArrayList<TotalCountTO> datas = new ArrayList<TotalCountTO>();
		
	     for(TotalCountTO to : list) {
	    	 
	    	 StringBuilder urlBuilder = new StringBuilder(animalRequest); /*URL*/
	
	    	 try {
			 	urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + encServiceKey); /*Service Key*/
				 urlBuilder.append("&"+ URLEncoder.encode("org_cd","UTF-8") + "=" + to.getGugunCode()); //구군 코드
				 urlBuilder.append("&"+ URLEncoder.encode("&bgnde","UTF-8") + "=" + to.getSearchStart()); //검색 시작일
				 urlBuilder.append("&"+ URLEncoder.encode("&endde","UTF-8") + "=" + to.getSearchEnd()); //검색 종료일
    
    
				 URL url = new URL(urlBuilder.toString()); //각 지역별 요청 url
				 //System.out.println(url);
				 	    
				 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				 conn.setRequestMethod("GET");
				 conn.setRequestProperty("Content-type", "application/json");
				 
				 //System.out.println("Response code: " + conn.getResponseCode()); //연결 확인
				 	      
				 BufferedReader br;
				 if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
					 br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				 } else {
					 br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				 }
				 
				 StringBuilder sbResult = new StringBuilder(); //결과 담기
				 String line;
				 
				 while ((line = br.readLine()) != null) {
					 sbResult.append(line);
				 }
				 
				 br.close();
				 conn.disconnect();
				 
				 String xml = sbResult.toString(); //결과 String
				 //System.out.println(result);
				 
				 Document doc = Jsoup.parse(xml); //결과를 xml로 parsing
				 String totalCount = doc.selectFirst("totalCount").text().toString(); //xml에서 갯수만 가져오기
				 //System.out.println(totalCount);
				 
				 to.setTotalCounts(totalCount);
				 datas.add(to);
				 
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     
	     }
	     
	     return datas;
	   
    };
	


}
