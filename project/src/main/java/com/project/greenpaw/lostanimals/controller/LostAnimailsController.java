package com.project.greenpaw.lostanimals.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.greenpaw.lostanimals.LostAnimalDaoInterface;
import com.project.greenpaw.lostanimals.TotalCountTO;

@Controller
public class LostAnimailsController {

	@Autowired
	private LostAnimalDaoInterface daoInterface;
	
	@RequestMapping("/getTotalCount.do")
	public ModelAndView getTotalCount(HttpServletRequest request, ModelAndView modelAndView) {
		
		String list = request.getParameter("list");
		//System.out.println(list);

		JSONParser parser = new JSONParser();
		JSONArray listJson = null;
		try {
			listJson = (JSONArray)parser.parse(list);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

		ArrayList<TotalCountTO> addresses = new ArrayList<TotalCountTO>();
		ArrayList<TotalCountTO> datas = new ArrayList<TotalCountTO>();
		JSONArray datasJson = new JSONArray();
		
		for(int i = 0; i<listJson.size(); i++){
			JSONObject listObj = (JSONObject)listJson.get(i);
			
			TotalCountTO to = new TotalCountTO();
			
			to.setSidoShort(listObj.get("sido").toString());
			to.setGugun(listObj.get("gugun").toString());
			
			addresses.add(to);
		}

		datas = daoInterface.getTotalCountFromDB(addresses);
		
		for(TotalCountTO to : datas){	
			JSONObject dataObj = new JSONObject();
		
			dataObj.put("sido", to.getSidoShort());
			//dataObj.put("gugun", to.getGugun());
			if(to.getGugun().contains("창원")){			
				switch(to.getGugun()){
					case "창원시 마산합포회원구" :
						dataObj.put("gugun", "마산합포회원구");
						break;
					case "창원시 의창성산구" :
						dataObj.put("gugun", "의창성산구");
						break;
					case "창원시 진해구" :
						dataObj.put("gugun", "진해구");
						break;
					default:
						dataObj.put("gugun", to.getGugun());
				}
			}else{
				dataObj.put("gugun", to.getGugun());
			}
			dataObj.put("totalCounts", to.getTotalCounts());
			dataObj.put("x", to.getX());
			dataObj.put("y", to.getY());
			
			datasJson.add(dataObj);
		}
		
		//JsonObject 하나로 합치기
		JSONObject results = new JSONObject();
		results.put("datas", datasJson );
		
		//view로 전달
		modelAndView.addObject("results", results);
		modelAndView.setViewName("/lostAnimals/getTotalCount");
		
		return modelAndView;
	}
	
}
