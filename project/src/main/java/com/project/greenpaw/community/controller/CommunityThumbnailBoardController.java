package com.project.greenpaw.community.controller;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.greenpaw.community.common.BoardListTO;
import com.project.greenpaw.community.common.BoardTO;
import com.project.greenpaw.community.normalboard.NormalBoardDaoInterface;
import com.project.greenpaw.community.thumbnailboard.ThumbnailDaoInterface;

@Controller
public class CommunityThumbnailBoardController {
	
	@Autowired
	private ThumbnailDaoInterface daoInterface;
	
	@Autowired
	private NormalBoardDaoInterface boardDaoInterface;
	
	String mappingPath = "/community/thumb_board";
	
	@RequestMapping("/thumbnail_board.do")
	public ModelAndView thumbnailBoardView(HttpServletRequest request, ModelAndView modelAndView, BoardListTO listTO) {
		
		int cpage = listTO.getCpage();
		String categorySeq = request.getParameter("categorySeq");
		String field = "title";
		String keyword ="";
		String sort="";
		int recordPerPage = listTO.getRecordPerPage();
		
		if(request.getParameter("cpage") != null && !request.getParameter("cpage").equals("")) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}
		// 검색 부분의 field와 keyword 가져오기
		if(request.getParameter("field") != null && !request.getParameter("field").equals("") ){
			field =request.getParameter("field");
		}
		if( request.getParameter("keyword") != null && !request.getParameter("keyword").equals("")){
			keyword =request.getParameter("keyword");
		}
		// 정렬에 필요한 sort 가져오기
		if(request.getParameter("sort") != null && !request.getParameter("sort").equals("")){
			sort = request.getParameter("sort");
		}

		listTO.setCpage( cpage );
		listTO = daoInterface.boardList(listTO,categorySeq,field,keyword);
		
		String type = request.getParameter("type"); //type데이터 가져오기
		listTO.setType(type);
		
		ArrayList<BoardTO> boardLists = listTO.getBoardLists();
		
		if(!field.equals("") && !keyword.equals("")){ 	// 검색값을 입력했을 경우
			boardLists = daoInterface.getSearchList(categorySeq, field, keyword, cpage, recordPerPage);
		} else if(!sort.equals("")){ 					// 정렬 기능 버튼을 눌렀을 때
			boardLists = daoInterface.listSort(categorySeq, field, keyword, sort, cpage, recordPerPage);
		} else { 										// categorySeq=5의 전체 게시글 조회
			boardLists = daoInterface.getList(categorySeq, cpage, recordPerPage);
		}
		
		listTO.setBoardLists(boardLists);
		
		modelAndView.addObject("listTO", listTO);
		modelAndView.addObject("categorySeq", categorySeq);
		modelAndView.addObject("keyword", keyword);
		modelAndView.addObject("field", field);
		modelAndView.addObject("sort", sort);
		modelAndView.setViewName(mappingPath + "/thumb_board");
		
		return modelAndView;
	}
	
	@RequestMapping("/thumb_write.do")
	public ModelAndView thumbnailBoardWrite(HttpServletRequest request, ModelAndView modelAndView) {
		String categorySeq = request.getParameter("categorySeq");
		modelAndView.addObject("categorySeq", categorySeq);
		modelAndView.setViewName( mappingPath + "/thumb_write");
		return modelAndView;
	}
	
	@RequestMapping("/write_thumb_ok.do")
	public ModelAndView thumbnailBoardWriteOk(HttpServletRequest request, ModelAndView modelAndView, BoardTO to) {

		String title = request.getParameter("title");
		String saleStatus = request.getParameter("saleStatus");
		String content = request.getParameter("content");
		String thumbUrl = request.getParameter("thumbUrl");
		String nickname = (String)request.getSession().getAttribute("nickname");
		String familyMemberType = request.getParameter("type");
		Boolean isPrivate = false;
		String categorySeq = "5";
		
		if (request.getParameter("categorySeq") != null && !request.getParameter("categorySeq").equals("") ){
			categorySeq = request.getParameter("categorySeq");
		}
		if (request.getParameter("familyMemberType") != null && !request.getParameter("familyMemberType").equals("") ){
			familyMemberType = request.getParameter("familyMemberType");
		} 
		 if (request.getParameter("saleStatus") != null && !request.getParameter("saleStatus").equals("") ){
			saleStatus = request.getParameter("saleStatus");
		}
		
		//사진파일 바뀐부분 적용
			String[] removedImg = request.getParameterValues("removedImg[]");
			if(removedImg != null){
				//System.out.println("removedImg length : "+ removedImg.length);
				
				for(String fileUrl : removedImg){
					String[] splitedFileUrl = fileUrl.split("/");
					String fileName = splitedFileUrl[splitedFileUrl.length -1];
					
					String filePath = request.getRealPath("upload")+"/";
					filePath += fileName;
					
					File file = new File(filePath);
					if(file.exists()){
						file.delete();
					}
				}
			}
		
		to.setCategorySeq(categorySeq); 
		to.setSaleStatus(saleStatus);
		to.setFamilyMemberType(familyMemberType);
		to.setTitle(title);
		to.setContent(content);
		to.setThumbUrl(thumbUrl);
		to.setNickname(nickname);
		to.setIsPrivate(isPrivate);
		
		int flag = boardDaoInterface.writeOk(to);
		
		modelAndView.addObject("flag", flag);
		modelAndView.setViewName(mappingPath + "/write_thumb_ok");
		
		return modelAndView; 
	}
	
	@RequestMapping("/thumb_view.do")
	public ModelAndView thumbnailBoardView(HttpServletRequest request, ModelAndView modelAndView, BoardTO to) {
		HttpSession session = request.getSession();
		
		String nickname = (String)session.getAttribute("nickname");
		String seq = request.getParameter("seq");
		String cpage = request.getParameter("cpage");
		String categorySeq = "5";
		String sessionNickname = (String)session.getAttribute("nickname");
		
		to.setSeq(seq);
		to.setNickname(nickname);
		to.setCategorySeq(categorySeq);
		
		to = daoInterface.thumbview(to);
		
		modelAndView.addObject("to", to);
		modelAndView.addObject("cpage", cpage);
		modelAndView.addObject("sessionNickname", sessionNickname);
		modelAndView.addObject("categorySeq", categorySeq);
		modelAndView.setViewName(mappingPath + "/thumb_view");
		
		return modelAndView;
	}
	
	@RequestMapping("/thumb_delete_ok.do")
	public ModelAndView thumbnailBoardDelete(HttpServletRequest request, ModelAndView modelAndView, BoardTO to) {
		
		String seq = request.getParameter("seq");
		String nickname = (String) request.getSession().getAttribute("nickname");
		String categorySeq = "5";
		
		String[] imageList = request.getParameterValues("imageList[]");
		System.out.println("imageList : " + imageList);
		if (imageList != null) {
			System.out.println("imageList length : " + imageList.length); 
		
			for (String fileUrl : imageList) {
				String[] splitedFileUrl = fileUrl.split("/");
				String fileName = splitedFileUrl[splitedFileUrl.length - 1];
				System.out.println("fileName : " +fileName);
		
				String filePath = request.getRealPath("upload") + "/";
				filePath += fileName;
				//System.out.println("filePath : "+filePath);
		
				File file = new File(filePath);
				if (file.exists()) {
					file.delete(); // 파일이 있으면 삭제 
				} 
			}
		}
		
		to.setSeq(seq);
		to.setCategorySeq(categorySeq);
		to.setNickname(nickname);
		
		int flag = daoInterface.thumbdelete(to);
		
		modelAndView.addObject("flag", flag);
		modelAndView.setViewName(mappingPath + "/thumb_delete_ok");
		
		return modelAndView;
	}
	
	@RequestMapping("/thumb_modify.do")
	public ModelAndView thumbnailBoardModify(HttpServletRequest request, ModelAndView modelAndView, BoardTO to) {
		String nickname = (String)request.getSession().getAttribute("nickname");
		String seq = request.getParameter("seq");
		String cpage = request.getParameter("cpage");
		String categorySeq = "5";
		
		to.setSeq(seq);
		to.setNickname(nickname);
		to.setCategorySeq(categorySeq);
		
		to = daoInterface.thumbmodify(to);
		
		modelAndView.addObject("to", to);
		modelAndView.addObject("categorySeq", categorySeq);
		modelAndView.addObject("seq", seq);
		modelAndView.addObject("cpage", cpage);
		modelAndView.setViewName(mappingPath + "/thumb_modify");
		
		return modelAndView;
	}
	
	@RequestMapping("/thumb_modify_ok.do")
	public ModelAndView thumbnailBoardModifyOk(HttpServletRequest request, ModelAndView modelAndView, BoardTO to) {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String thumbUrl = request.getParameter("thumbUrl");
		String nickname = (String)request.getSession().getAttribute("nickname");
		Boolean isPrivate = false;       	
		String familyMemberType = request.getParameter("familyMemberType");
		String seq = request.getParameter("seq");
		String categorySeq = "5";
		String saleStatus = request.getParameter("saleStatus");

		        	
		// 삭제해야하는 파일 배열
		String[] removedImg = request.getParameterValues("removedImg[]") ;
		  if ( removedImg != null ){
		  //System.out.println("removedImg length : "+ removedImg.length);
		    for( String fileUrl : removedImg ){
		      String[] splitedFileUrl = fileUrl.split("/");
		      String fileName = splitedFileUrl[splitedFileUrl.length-1];
		        	
		      // 각자 바꿔주기~! (아니면 realPath 쓰기)
		      String filePath =  request.getRealPath("upload")+"/";
		      filePath += fileName;
		      //System.out.println("filePath : "+filePath);
		        	
		       File file = new File(filePath);
		       if ( file.exists()){
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
		to.setCategorySeq(categorySeq);
		to.setSaleStatus(saleStatus);
		to.setIsPrivate(false);
		to.setSeq(seq);
		        	
		// 글 수정 성공시 location.href 용 url 만들기
		String type = request.getParameter("familyMemberType");
		String cpage = request.getParameter("cpage");
		String redirectUrl ="/greenpaw/thumb_view.do?cpage="+cpage+"&seq="+seq+"&type="+type;

		int flag = daoInterface.thumbmodifyOK(to);
		
		modelAndView.addObject("flag", flag);
		modelAndView.addObject("seq", seq);
		modelAndView.addObject("redirectUrl", redirectUrl);
		modelAndView.setViewName(mappingPath + "/thumb_modify_ok");
		
		return modelAndView;
	}

}
