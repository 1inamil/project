package com.project.greenpaw.community.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.greenpaw.albumboard.AlbumDaoInterface;
import com.project.greenpaw.albumboard.AlbumListTO;
import com.project.greenpaw.albumboard.AlbumTO;

@Controller
public class CommunityAlbumBoardController {
	
	@Autowired
	private AlbumDaoInterface daoInterface;
	
	private String mappingPath = "/community/community_album";

	@RequestMapping("/community_album_list.do")
	public ModelAndView communityBoardView(HttpServletRequest request, ModelAndView modelAndView, AlbumListTO listTO) {
		
		HttpSession session = request.getSession();
		String nickname = (String)session.getAttribute("nickname");
		
		//listTO에 값 넣기
		listTO.setNickname(nickname);	
		if(request.getParameter("cpage") != null){
			listTO.setCpage(Integer.parseInt(request.getParameter("cpage")));
		}
		String type = request.getParameter("type"); //type데이터 가져오기
		String catSeq = "3"; //성장앨범 게시판
		Boolean isPrivate = false;
		
		listTO.setFamilyMemberType(type);
		listTO.setCatSeq(catSeq);
		listTO.setIsPrivate(isPrivate);
		
		//데이터 가져오기	
		listTO = daoInterface.communityAlbumList(listTO);
		
		modelAndView.addObject("listTO", listTO);
		modelAndView.setViewName( mappingPath + "/community_album_list");
		
		return modelAndView;
	}
	
	@RequestMapping("/community_album_write.do")
	public String communityAlbumWrite() {
		return mappingPath + "/community_album_write";
	}
	
	@RequestMapping("/community_album_write_ok.do")
	public ModelAndView communityWriteOk(HttpServletRequest request, AlbumTO to, ModelAndView modelAndView) {
    	// 넘어온 파라미터로 디비에 insert 
    	String title = request.getParameter("title");
    	String content = request.getParameter("content");
    	String thumbUrl = request.getParameter("thumbUrl");
    	String nickname = (String)request.getSession().getAttribute("nickname");
    	String isPrivateString = request.getParameter("isPrivate");
    	Boolean isPrivate = false;
    	
    	String familyMemberType = request.getParameter("familyMemberType");
    	
    	int hit = 0;
    	int likeCount = 0;
    	String catSeq = "2"; //성장앨범 게시판
    	String saleStatus = null;
    	String subTitle = null;
    	
    	
    	// 삭제해야하는 파일 배열
    	String[] removedImg = request.getParameterValues("removedImg[]") ;
    	if ( removedImg != null ){
        //System.out.println("removedImg length : "+ removedImg.length);

    	    for( String fileUrl : removedImg ){
    	        String[] splitedFileUrl = fileUrl.split("/");
    	        String fileName = splitedFileUrl[splitedFileUrl.length-1];
    	
    	        String filePath =  request.getRealPath("upload")+"/";
    	        filePath += fileName;
    	        //System.out.println("filePath : "+filePath);
    	
    	        File file = new File(filePath);
    	        if ( file.exists()){
    	            file.delete(); // 파일이 있으면 삭제 
    	        }
    	    }
    	}

    	/* System.out.println(title);
    	System.out.println(content);
    	System.out.println(thumbUrl);
    	System.out.println(nickname);
    	System.out.println(familyMemberType);
    	 */
    	 System.out.println("is private String: "+isPrivateString);
    	 System.out.println("is private Boolean: "+isPrivate);
    	
    	to.setTitle(title);
    	to.setContent(content);
    	to.setThumbUrl(thumbUrl);
    	to.setNickname(nickname);
    	to.setFamilyMemberType(familyMemberType);
    	to.setIsPrivate(isPrivate);
    	to.setHit(hit);
    	to.setCatSeq(catSeq);
    	to.setLikeCount(likeCount);
    	to.setSaleStatus(saleStatus);
    	to.setSubTitle(subTitle);
    	
    	int flag = daoInterface.writeOk(to);

    	modelAndView.addObject("flag", flag);
    	modelAndView.setViewName( mappingPath + "/community_album_write_ok");
    	
    	return modelAndView;
		
	}
	
	@RequestMapping("/community_album_view.do")
	public ModelAndView communityAlbumView(HttpServletRequest request, ModelAndView modelAndView, AlbumTO to) {

		String nickname = (String)request.getSession().getAttribute("nickname");
		String seq = request.getParameter("seq");
		String cpage = request.getParameter("cpage");
		String type = request.getParameter("type");
		String catSeq = "3"; //성장앨범 게시판
		
		to.setSeq(seq);
		to.setNickname(nickname);
		to.setCatSeq(catSeq);
		
		to = daoInterface.CommunityAlbumView(to);
		
		request.getSession().setAttribute("contentOwner", to.getNickname());
		
		modelAndView.addObject("to", to);
		modelAndView.addObject("cpage", cpage);
		modelAndView.setViewName( mappingPath + "/community_album_view");
		
		return modelAndView;
	}
	
	@RequestMapping("/community_album_delete_ok.do")
	public ModelAndView communityAlbumDeleteOk(HttpServletRequest request, AlbumTO to, ModelAndView modelAndView) {
		
		String seq = request.getParameter("seq");
		String nickname = (String) request.getSession().getAttribute("nickname");
		String catSeq = "3"; //아이자랑 게시판
		
		String[] imageList = request.getParameterValues("imageList[]");
		System.out.println("imageList : " + imageList);
		if (imageList != null) {
			System.out.println("imageList length : " + imageList.length); 
		
			for (String fileUrl : imageList) {
		String[] splitedFileUrl = fileUrl.split("/");
		String fileName = splitedFileUrl[splitedFileUrl.length - 1];
		System.out.println("fileName : " +fileName);
		
		// 각자 바꿔주기~! (아니면 realPath 쓰기)
		String filePath = request.getRealPath("upload") + "/";
		filePath += fileName;
		//System.out.println("filePath : "+filePath);
		
		File file = new File(filePath);
		if (file.exists()) {
			file.delete(); // 파일이 있으면 삭제 
			System.out.println("파일 삭제 성공");
		} 
			}
		}
		
		to.setSeq(seq);
		to.setCatSeq(catSeq);
		to.setNickname(nickname);
		
		int flag = daoInterface.deleteOk(to);
		
		modelAndView.addObject("flag", flag);
		modelAndView.setViewName(mappingPath + "/community_album_delete_ok");
		
		return modelAndView;
	}
	
	@RequestMapping("/community_album_modify.do")
	public ModelAndView communityAlbumModify(HttpServletRequest request, ModelAndView modelAndView, AlbumTO to) {

		String nickname = (String)request.getSession().getAttribute("nickname");
		String seq = request.getParameter("seq");
		String cpage = request.getParameter("cpage");
		String type = request.getParameter("type");
		String catSeq = "3"; //아이자랑 게시판
		
		to.setSeq(seq);
		to.setNickname(nickname);
		to.setCatSeq(catSeq);
		
		to = daoInterface.CommunityAlbumView(to);
		
		modelAndView.addObject("to", to);
		modelAndView.addObject("cpage", cpage);
		modelAndView.addObject("type", type);
		modelAndView.setViewName(mappingPath + "/community_album_modify");
		
		return modelAndView;
	}
	
	@RequestMapping("/community_album_modify_ok")
	public ModelAndView communityAlbumModifyOk(HttpServletRequest request, ModelAndView modelAndView, AlbumTO to) {
       	
    	// 넘어온 파라미터로 디비에 update 
    	String title = request.getParameter("title");
    	String content = request.getParameter("content");
    	String thumbUrl = request.getParameter("thumbUrl");
    	String nickname = (String)request.getSession().getAttribute("nickname");
    	Boolean isPrivate = false;       	
    	String familyMemberType = request.getParameter("familyMemberType");
    	String seq = request.getParameter("seq");
    	int hit = 0;
    	int likeCount = 0;
    	String catSeq = "3"; //아이자랑 게시판
    	String saleStatus = null;
    	String subTitle = request.getParameter("familyMemberType");
    	

    	// 삭제해야하는 파일 배열
    	String[] removedImg = request.getParameterValues("removedImg[]");
    	if (removedImg != null) {
    		//System.out.println("removedImg length : "+ removedImg.length);

    		for (String fileUrl : removedImg) {
    			String[] splitedFileUrl = fileUrl.split("/");
    			String fileName = splitedFileUrl[splitedFileUrl.length - 1];

    			// 각자 바꿔주기~! (아니면 realPath 쓰기)
    			String filePath = request.getRealPath("upload") + "/";
    			filePath += fileName;
    			//System.out.println("filePath : "+filePath);

    			File file = new File(filePath);
    			if (file.exists()) {
    		file.delete(); // 파일이 있으면 삭제 
    			}
    		}
    	}

    	to.setTitle(title);
    	to.setContent(content);
    	to.setThumbUrl(thumbUrl);
    	to.setNickname(nickname);
    	to.setFamilyMemberType(familyMemberType);
    	to.setIsPrivate(isPrivate);
    	to.setHit(hit);
    	to.setCatSeq(catSeq);
    	to.setLikeCount(likeCount);
    	to.setSaleStatus(saleStatus);
    	to.setSubTitle(subTitle);
    	to.setSeq(seq);

    	// 글 수정 성공시 location.href 용 url 만들기
    	String type = request.getParameter("type");
    	String cpage = request.getParameter("cpage");

    	String redirectUrl = "./community_album_view.do?type=" + type + "&cpage=" + cpage + "&seq=" + seq;
    	//System.out.println("redirectUrl : " + redirectUrl);

    	int flag = daoInterface.modifyOk(to);
    	
    	modelAndView.addObject("flag", flag);
    	modelAndView.addObject("seq", seq);
    	modelAndView.addObject("redirectUrl", redirectUrl);
    	modelAndView.setViewName(mappingPath + "/community_album_modify_ok");
    	
		return modelAndView;

	}
	
}
