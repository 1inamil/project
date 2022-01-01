package com.project.greenpaw.lostanimals;

import java.util.ArrayList;

public interface LostAnimalDaoInterface {

	//시도 코드 가져오기
	String[] getCode(String sido, String gugun);
	
	//시도 코드로 api(DB백업) 데이터 가져오기
	ArrayList<TotalCountTO> getTotalCountFromDB(ArrayList<TotalCountTO> list);

}