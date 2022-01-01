package totalCounts;

import java.util.ArrayList;

public class DoUpdate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int update = 0;
		
		//API에 검색어로 넣을 ArrayList 만들기
		UpdateTotalCounts utc = new UpdateTotalCounts();
		ArrayList<TotalCountTO> list = new ArrayList<TotalCountTO>();
		list = utc.getTotalCount();
		
		//API에서 검색해 옴! (구군별 유기동물 숫자 가져옴)
		GetTotalCountsFromAPI api = new GetTotalCountsFromAPI();
		ArrayList<TotalCountTO> datas = new ArrayList<TotalCountTO>();
		datas = api.getTotalCounts(list);
		
		//DB에 업데이트
		update = utc.totalCountsToDB(datas);
		
		System.out.println(update);
		
	}

}
