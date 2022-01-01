package com.project.greenpaw.community.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.greenpaw.albumboard.AlbumDAO;
import com.project.greenpaw.albumboard.AlbumListTO;

@Controller
public class CommunityMainController {
	
	@Autowired
	public AlbumDAO daoInterface;
	
	@RequestMapping("/community_main.do")
	public ModelAndView communityMainView(HttpServletRequest request, AlbumListTO listTO, ModelAndView modelAndView) {
		
		HttpSession session = request.getSession();
		
		String mail = (String)session.getAttribute("mail");
		String nickname = (String)session.getAttribute("nickname");
		String authType = (String)session.getAttribute("authType");

		//listTO에 값 넣기
		listTO.setNickname(nickname);	
		if(request.getParameter("cpage") != null){
			listTO.setCpage(Integer.parseInt(request.getParameter("cpage")));
		}
		String type = request.getParameter("type"); //type데이터 가져오기
		String catSeq = "3"; //성장앨범 게시판
		
		listTO.setFamilyMemberType(type);
		listTO.setCatSeq(catSeq);
		listTO.setIsPrivate(false);
		listTO.setFamilyMemberType(type);

		//데이터 가져오기	
		listTO = daoInterface.communityAlbumList(listTO);
		
		modelAndView.addObject("listTO", listTO);
		modelAndView.setViewName("/community/community_main");
		
		return modelAndView;
	}
	
}
