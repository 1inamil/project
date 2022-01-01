package com.project.greenpaw.community.controller;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.greenpaw.community.common.BoardTO;
import com.project.greenpaw.community.common.PageTO;
import com.project.greenpaw.community.normalboard.NormalBoardDAO;

@Controller
public class CommunityNormalBoardController {
	
	@Autowired
	private NormalBoardDAO daoInterface;
	
	private String mappingPath = "/community/normal_board";
	
	@RequestMapping("/normal_board.do")
	public ModelAndView normalBoardMainView(HttpServletRequest request, ModelAndView modelAndView, PageTO pages) {
		
		//검색했을때..
		String category = request.getParameter("category");
		String field = "title";
		String keyword ="";
		String sort="";
		String subtitle="";
		
		if(request.getParameter("field") != null && !request.getParameter("field").equals("") ){
			field =request.getParameter("field");
		}

		if( request.getParameter("keyword") != null && !request.getParameter("keyword").equals("")){
			keyword =request.getParameter("keyword");
		}

		//정렬
		if(request.getParameter("sort") != null && !request.getParameter("sort").equals("")){
			sort = request.getParameter("sort");
		}
		
		//말머리 클릭
		if(request.getParameter("subtitle") != null && !request.getParameter("subtitle").equals("")){
			subtitle = request.getParameter("subtitle");
		}
		
		//페이지
		int cPage=1; //기본 페이지
		
		if(request.getParameter("page") != null && !request.getParameter("page").equals("") ){
			cPage = Integer.parseInt(request.getParameter("page"));
		}
		
		int perPage =pages.getRecordPerPage();
		
		pages.setCpage(cPage); 
		
		pages = daoInterface.boardList(pages,category,field,keyword,subtitle);
		
		ArrayList<BoardTO> result = daoInterface.getList(category,cPage,perPage);
		//검색
		if(!field.equals("") && !keyword.equals("")){
			result = daoInterface.getSearchList(category, field, keyword, pages);
		} else if(!sort.equals("")){//인기순, 시간순
			result = daoInterface.listSort(category, field, keyword, sort, pages);
		} else if(!subtitle.equals("")){ 
			result = daoInterface.getSubTitleList(category, subtitle, pages);
		} 

		modelAndView.addObject("result", result);
		modelAndView.addObject("pages", pages);
		modelAndView.addObject("category", category);
		modelAndView.addObject("field", field);
		modelAndView.addObject("keyword", keyword);
		modelAndView.addObject("sort", sort);
		modelAndView.addObject("subtitle", subtitle);
		modelAndView.setViewName( mappingPath + "/normal_board");
		
		return modelAndView;
		
	}
	
	@RequestMapping("/normal_board_view.do")
	public ModelAndView normalBoardView(HttpServletRequest request, ModelAndView modelAndView, BoardTO to) {

		String seq = request.getParameter("seq");
		String sessionNickname = (String)request.getSession().getAttribute("nickname");
		String category = request.getParameter("category");
		String authType = (String)request.getSession().getAttribute("authType");

		to.setSeq(seq);
		int isSeq = daoInterface.isSeq(seq);
			
		//뷰 데이터 가져오기..
		to = daoInterface.normalBoardView(to);
		
		modelAndView.addObject("to", to);
		modelAndView.addObject("category", category);
		modelAndView.addObject("sessionNickname", sessionNickname);
		modelAndView.addObject("authType", authType);
		modelAndView.addObject("isSeq", isSeq);
		modelAndView.setViewName( mappingPath + "/normal_board_view");
		
		return modelAndView;
	}
	
	@RequestMapping("/subTitle_write.do")
	public ModelAndView normalBoardWrite(ModelAndView modelAndView, HttpServletRequest request, Model model) {
		
		String category = request.getParameter("category");
		modelAndView.addObject("category", category);
		modelAndView.setViewName(mappingPath+"/subTitle_write");
		model.addAttribute("category", category);
		return modelAndView;
	}
	
	@RequestMapping("/write_nomal_ok.do")
	public ModelAndView normalBoardWriteOk(ModelAndView modelAndView, BoardTO to, HttpServletRequest request) {
	
		// 넘어온 파라미터로 디비에 insert 
		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String thumbUrl = request.getParameter("thumbUrl");
		String nickname = (String)request.getSession().getAttribute("nickname");
		String familyMemberType = request.getParameter("type");

		//String nickname = "닉네임";
		 String subTitle = ""; //subTitle있는것 없는것이 있어서, 있으면 나타나도록 수정.. 
		 if (request.getParameter("subTitle") != null && !request.getParameter("subTitle").equals("") ){
			 subTitle = request.getParameter("subTitle");
			//System.out.println("카테고리"+category);
		} 

		String saleStatus = "";
		if (request.getParameter("saleStatus") != null && !request.getParameter("saleStatus").equals("") ){
			saleStatus = request.getParameter("saleStatus");
		}

		//사진파일 
		String[] removedImg = request.getParameterValues("removedImg[]");
		if(removedImg != null){
			//System.out.println("removedImg length : "+ removedImg.length);
			
			for(String fileUrl : removedImg){
				String[] splitedFileUrl = fileUrl.split("/");
				String fileName = splitedFileUrl[splitedFileUrl.length -1];
				
				System.out.println(fileName);
				
				String filePath = request.getRealPath("upload")+"/";
				filePath += fileName;
				
				File file = new File(filePath);
				if(file.exists()){
					file.delete();
					System.out.println("삭제 성공");
				}
			}
		}

		to.setCategorySeq(category); 
		to.setTitle(title);
		to.setSubTitle(subTitle);
		to.setContent(content);
		to.setThumbUrl(thumbUrl);
		to.setNickname(nickname);
		to.setFamilyMemberType(familyMemberType);
		to.setSaleStatus(saleStatus);

		int flag = daoInterface.writeOk(to); 
		
		modelAndView.addObject("flag", flag);
		modelAndView.addObject("category", category);
		modelAndView.setViewName( mappingPath+"/write_nomal_ok");
		
		return modelAndView;
	}
	
	@RequestMapping("/subTitle_modify.do")
	public ModelAndView normalBoardModify(HttpServletRequest request, BoardTO to, ModelAndView modelAndView, Model model) {
		
		String category = request.getParameter("category");
		String page = request.getParameter("page");
		String seq = request.getParameter("seq");
		String nickname = (String)request.getSession().getAttribute("nickname");
		
		to.setSeq(seq);
		to.setCategorySeq(category);
		
		to = daoInterface.modify(to);
		
		model.addAttribute("category", category);
		model.addAttribute("page", page);
		modelAndView.addObject("to", to);
		modelAndView.addObject("category", category);
		modelAndView.addObject("nickname", nickname);
		modelAndView.setViewName(mappingPath +"/subTitle_modify");
		
		return modelAndView;
		
	}
	
	@RequestMapping("/modify_nomal_ok.do")
	public ModelAndView normalBoardModifyOk(HttpServletRequest request, BoardTO to, ModelAndView modelAndView) {

		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String thumbUrl = request.getParameter("thumbUrl");
		String nickname = (String)request.getSession().getAttribute("nickname");
		String familyMemberType = request.getParameter("type");
		String seq = request.getParameter("seq");

		 String subTitle = ""; //subTitle있는것 없는것이 있어서, 있으면 나타나도록 수정.. 
		 if (request.getParameter("subTitle") != null && !request.getParameter("subTitle").equals("") ){
			 subTitle = request.getParameter("subTitle");
		} 

		String saleStatus = "";
		if (request.getParameter("saleStatus") != null && !request.getParameter("saleStatus").equals("") ){
			saleStatus = request.getParameter("saleStatus");
		}

		//수정되면서 사라진 파일 삭제
		String[] removedImg = request.getParameterValues("removedImg[]");
		if(removedImg != null){
			
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

		//수정
		to.setCategorySeq(category); 
		to.setTitle(title);
		to.setSubTitle(subTitle);
		to.setContent(content);
		to.setThumbUrl(thumbUrl);
		to.setNickname(nickname);
		to.setFamilyMemberType(familyMemberType);
		to.setSaleStatus(saleStatus);
		to.setIsPrivate(false); //공개키
		to.setSeq(seq);

		int flag = daoInterface.modifyOk(to);
		
		modelAndView.addObject("flag", flag);
		modelAndView.addObject("seq", to.getSeq());
		modelAndView.addObject("category", category);
		modelAndView.setViewName(mappingPath+"/modify_nomal_ok");
	
		return modelAndView;
	}
	
	@RequestMapping("/normal_board_delete.do")
	public ModelAndView normalBoardDeleteOk(HttpServletRequest request, ModelAndView modelAndView, BoardTO to) {
		
		String seq = request.getParameter("seq");
		String category =request.getParameter("category");
		String page = request.getParameter("page");
		String nickname = (String)request.getSession().getAttribute("nickname");
				
		// ajax 에서 넘겨준 이미지 배열
		String[] imageList = request.getParameterValues("imageList[]");

		if (imageList != null){
			System.out.println("image List length: "+imageList.length);
			
			for(String fileUrl : imageList){
				// url 을 / 단위로 잘라서, 제일 마지막의 파일명만 가져오기 
				// ex) http://localhost8080/test/test.png 
				String[] splitedUrl = fileUrl.split("/");
				String fileName = splitedUrl[splitedUrl.length-1]; // 배열 길이의 -1 이 마지막 인덱스 == 파일명
				
				// 파일이 들어있는 경로 지정
				String filePath = request.getRealPath("upload")+"/";
				filePath += fileName; // ~~~경로/파일명 
				//System.out.println("file path : "+filePath);
				
				File file = new File(filePath); // 위의 경로에 파일 처리 하기위한 파일객체 만들기
				if ( file.exists() ){
					file.delete(); // 파일이 있으면 지워줘 
					//System.out.println("파일 삭제 성공!");
				}
			}
		}

		to.setSeq(seq);
		to.setNickname(nickname);

		int flag = daoInterface.normalBoardDelete(to);
		
		modelAndView.addObject("flag", flag);
		modelAndView.addObject("category", category);
		modelAndView.addObject("page", page);
		modelAndView.setViewName(mappingPath + "/normal_board_delete");
		
		return modelAndView;
	}

}
