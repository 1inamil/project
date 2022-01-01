package com.project.greenpaw.lostanimals;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LostAnimalDaoImpl implements LostAnimalDaoInterface {

	@Autowired
	private ApiDAO apiDao;
	
	@Autowired
	private TotalCountDAO totalCountDao;
	
	@Override
	public String[] getCode(String sido, String gugun) {
		// TODO Auto-generated method stub
		return apiDao.getCode(sido, gugun);
	}

	@Override
	public ArrayList<TotalCountTO> getTotalCountFromDB(ArrayList<TotalCountTO> list) {
		// TODO Auto-generated method stub
		return totalCountDao.getTotalCountFromDB(list);
	}

}
