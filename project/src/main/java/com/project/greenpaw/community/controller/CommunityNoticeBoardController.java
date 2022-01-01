package com.project.greenpaw.community.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.greenpaw.community.common.BoardTO;
import com.project.greenpaw.community.common.PageTO;
import com.project.greenpaw.community.normalboard.NormalBoardDAO;
import com.project.greenpaw.community.noticeboard.AdminNoticeDaoInterface;

@Controller
public class CommunityNoticeBoardController {
	
	@Autowired
	private AdminNoticeDaoInterface daoInterface;
	@Autowired
	private NormalBoardDAO normalBoardDaoInterface;
	
	private String mappingPath = "/community/admin_notice";
	
	@RequestMapping("/admin_noticeboard.do")
	public ModelAndView noticeBoardListView(HttpServletRequest request, ModelAndView modelAndView, 
			BoardTO to, PageTO pages) {
		
		String authType = (String)request.getSession().getAttribute("authType");

		//검색했을때..
		String category = request.getParameter("category");
		String field = "title";
		String keyword ="";
		String sort="";
		String subtitle="";
		String startDate ="";
		String endDate ="";
		
		if(request.getParameter("field") != null && !request.getParameter("field").equals("") ){
			field =request.getParameter("field");
		}
		//System.out.println("필드"+field);

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
		
		if(request.getParameter("startDate") != null && !request.getParameter("startDate").equals("") ){
			startDate = request.getParameter("startDate");
		}
		
		if(request.getParameter("endDate") != null && !request.getParameter("endDate").equals("") ){
			endDate = request.getParameter("endDate");
		}
		
		
		//페이지
		int cPage=1; //기본 페이지
		
		if(request.getParameter("page") != null && !request.getParameter("page").equals("") ){
			cPage = Integer.parseInt(request.getParameter("page"));
		}
		
		pages.setCpage(cPage); 
		int perPage = pages.getRecordPerPage();
		
		pages = daoInterface.boardList(pages,category,field,keyword,subtitle);
		
		//날짜 검색시 페이지
		if (!startDate.equals("") && !endDate.equals("")) {
	    	pages = daoInterface.boardList(pages, category, startDate, endDate);
		}
		
		//검색
		ArrayList<BoardTO> result = daoInterface.getList(category,cPage,perPage);

		if(!field.equals("") && !keyword.equals("")){
			result = daoInterface.getSearchList(category, field, keyword, pages);
		} else if(!sort.equals("")){//인기순, 시간순
			result = daoInterface.listSort(category, field, keyword, sort, pages);
		} else if(!subtitle.equals("")){ 
			result = daoInterface.getSubTitleList(category, subtitle, pages);
		} 
		
		if(!startDate.equals("") && !endDate.equals("")){
			result = daoInterface.dateSearch(startDate, endDate, pages);
		} 
		
		pages.setPageList(result);

		//일괄공개, 삭제  inputbox에 넣을 배열(좀 깔끔하게 만들고 싶다...)
		ArrayList<String> datas = daoInterface.getNoticeSeq(category,cPage,perPage);
		String seqList = datas.toString().replace("[","").replace("]","").replace(",","");
		
		modelAndView.addObject("seqList", seqList);
		modelAndView.addObject("pages", pages);
		modelAndView.addObject("startDate", startDate);
		modelAndView.addObject("endDate", endDate);
		modelAndView.addObject("category", category);
		modelAndView.addObject("authType", authType);
		modelAndView.addObject("field", field);
		modelAndView.addObject("keyword", keyword);
		modelAndView.addObject("sort", sort);
		modelAndView.addObject("subtitle", subtitle);
		modelAndView.setViewName(mappingPath + "/admin_noticeboard" );
		
		return modelAndView;
	}
	
	@RequestMapping("/admin_notice_view.do")
	public ModelAndView noticeBoardView(HttpServletRequest request, BoardTO to, ModelAndView modelAndView) {
		
		String sessionNickname = (String)request.getSession().getAttribute("nickname");
		String category = request.getParameter("category");
		String seq = request.getParameter("seq");
		String authType = (String)request.getSession().getAttribute("authType");

		to.setSeq(seq);
		int isSeq = normalBoardDaoInterface.isSeq(seq);
			
		//뷰 데이터 가져오기..
		to = normalBoardDaoInterface.normalBoardView(to);
		
		modelAndView.addObject("to", to);
		modelAndView.addObject("isSeq", isSeq);
		modelAndView.addObject("sessionNickname", sessionNickname);
		modelAndView.addObject("category", category);
		modelAndView.addObject("authType", authType);
		modelAndView.setViewName(mappingPath + "/admin_notice_view");
		return modelAndView;
	}
	
	@RequestMapping("/admin_notice_delete.do")
	public ModelAndView deleteNotice(HttpServletRequest request, ModelAndView modelAndView, BoardTO to) {
		//하나씩 삭제하는것
		String seq = request.getParameter("seq");
		String category =request.getParameter("category");
		String pages = request.getParameter("page");
				
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
				System.out.println("file path : "+filePath);
				
				File file = new File(filePath); // 위의 경로에 파일 처리 하기위한 파일객체 만들기
				if ( file.exists() ){
					file.delete(); // 파일이 있으면 지워줘 
					System.out.println("파일 삭제 성공!");
				}
			}
		}

		to.setSeq(seq);
		int flag = daoInterface.deleteOk(to);
		
		modelAndView.addObject("flag", flag);
		modelAndView.addObject("category", category);
		modelAndView.addObject("pages", pages);
		modelAndView.setViewName( mappingPath + "/admin_notice_delete");
	
		return modelAndView;
	}

	@RequestMapping("/admin_notice_privateDelete.do")
	public ModelAndView privateDeleteNotice(HttpServletRequest request, ModelAndView modelAndView, BoardTO to) {
		//일괄삭제, 일괄공개 버튼 클릭시
		String category = request.getParameter("category");
		String authType = (String) request.getSession().getAttribute("authType");

		//체크한 값이 있을때만 각 배열에 값 넣어주기 
		String[] privateSeqs = null;
		String[] deleteSeqs = null;
		if (request.getParameterValues("private") != null) {
			String[] privates = request.getParameterValues("private");
			// 받아온 값의 사이즈에 맞는 배열 new 해주기
			privateSeqs = new String[privates.length];
			for (int i = 0; i < privates.length; i++) {
				// 각 인덱스에 값 대입
				privateSeqs[i] = privates[i];
			}
		}
		
		if (request.getParameterValues("delete") != null) { //공개할 글seq
			String[] deletes = request.getParameterValues("delete");
			// 받아온 값의 사이즈에 맞는 배열 new 해주기
			deleteSeqs = new String[deletes.length];
			for (int i = 0; i < deletes.length; i++) {
				// 각 인덱스에 값 대입
				deleteSeqs[i] = deletes[i];
			}
		}
		
		String cmd = request.getParameter("cmd"); //일괄공개,일괄삭제 어떤걸 클릭했는지 체크
		String seqList = request.getParameter("seqlist"); //전체목록
		String[] seqLists = seqList.split(" "); //전체 목록 
		
		//공개 선택 있을 경우
		if (privateSeqs != null && cmd.equals("일괄공개")) {
		
			List<String> privateSeqList = Arrays.asList(privateSeqs); //공개 목록
		
			List<String> allList = new ArrayList<String>(Arrays.asList(seqLists));
			allList.removeAll(privateSeqList);
		
			daoInterface.privateNoticeAll(privateSeqList, allList);
		
		} else if (deleteSeqs != null && cmd.equals("일괄삭제")) {
			/* ******* 삭제 선택 있을 시 *********** */
		
			//받아온 값을 다시 저장
			int[] deleteSeq = new int[deleteSeqs.length];
		
			for (int i = 0; i < deleteSeqs.length; i++) {
				deleteSeq[i] = Integer.parseInt(deleteSeqs[i]);
			}
		
			//이미지 전부 삭제되도록
			//삭제할 글의 개수만큼 반복
			for (int i = 0; i < deleteSeq.length; i++) {
				//System.out.println(dao.getImgList(deleteSeq[i]));
				ArrayList<String> imglist = new ArrayList<String>();
				imglist = daoInterface.getImgList(deleteSeq[i]);
				//System.out.println(imglist);
				for (String fileUrl : imglist) {
					String[] splitedurl = fileUrl.split("/");
					String fileName = splitedurl[splitedurl.length - 1];
		
					//경로 지정
					String filePath = request.getRealPath("upload") + "/";
					filePath += fileName;
		
					File file = new File(filePath); // 위의 경로에 파일 처리 하기위한 파일객체 만들기
					
					if (file.exists()) {
						file.delete(); // 파일이 있으면 지워줘 
						System.out.println("파일 삭제 성공!");
					}
				}
			}
			//글삭제 실행
			daoInterface.deleteAll(deleteSeq);
		}
		
		modelAndView.addObject("authType", authType);
		modelAndView.addObject("deleteSeqs", deleteSeqs);
		modelAndView.addObject("privateSeqs", privateSeqs);
		modelAndView.addObject("category", category);
		modelAndView.addObject("cmd", cmd);
		
		modelAndView.setViewName(mappingPath+"/admin_notice_privateDelete");
		return modelAndView;
		
	}
	
	@RequestMapping("/admin_notice_write.do")
	public ModelAndView noticeWrite(HttpServletRequest request, ModelAndView modelAndView) {

		String category = request.getParameter("category");
		String authType = (String) request.getSession().getAttribute("authType");
		
		modelAndView.addObject("category", category);
		modelAndView.addObject("authType", authType);
		modelAndView.setViewName(mappingPath + "/admin_notice_write");

		return modelAndView;
	}
	
	@RequestMapping("/admin_notice_write_ok.do")
	public ModelAndView noticeWriteOk(HttpServletRequest request, ModelAndView modelAndView, BoardTO to) {
		
		// 넘어온 파라미터로 디비에 insert 
		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String thumbUrl = request.getParameter("thumbUrl");
		String nickname = (String)request.getSession().getAttribute("nickname");
		String familyMemberType = request.getParameter("type");

		String subTitle = ""; //subTitle있는것 없는것이 있어서, 있으면 나타나도록 수정.. 
		if (request.getParameter("subTitle") != null && !request.getParameter("subTitle").equals("") ){
			 subTitle = request.getParameter("subTitle");
		} 

		//공개여부
		String isPrivateString = request.getParameter("isPrivate");
		Boolean isPrivate = true;
		if(isPrivateString.equals("false")){ isPrivate = false; }
		 
		String saleStatus = "";
		if (request.getParameter("saleStatus") != null && !request.getParameter("saleStatus").equals("") ){
			saleStatus = request.getParameter("saleStatus");
		}

		//사진파일 바뀐부분 적용
		String[] removedImg = request.getParameterValues("removedImg[]");
		
		if(removedImg != null){
			for(String fileUrl : removedImg){
				String[] splitedFileUrl = fileUrl.split("/");
				String fileName = splitedFileUrl[splitedFileUrl.length -1];
				
				System.out.println(fileName);
				
				String filePath = request.getRealPath("upload")+"/";
				filePath += fileName;
				
				File file = new File(filePath);
				if(file.exists()){
					file.delete();
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
		to.setIsPrivate(isPrivate); //추가..

		int flag = normalBoardDaoInterface.writeOk(to); 
		
		modelAndView.addObject("flag", flag);
		modelAndView.addObject("category", category);
		modelAndView.setViewName(mappingPath+"/admin_notice_write_ok");
		
		return modelAndView;
	}
	
	@RequestMapping("/admin_notice_modify.do")
	public ModelAndView noticeModifyView(HttpServletRequest request, ModelAndView modelAndView, BoardTO to) {
		String category = "7";
		String pages = request.getParameter("page");
		String seq = request.getParameter("seq");
		
		to.setSeq(seq);
		to.setCategorySeq(category);
		
		to = normalBoardDaoInterface.modify(to);
		
		modelAndView.addObject("to", to);
		modelAndView.addObject("category", category);
		modelAndView.addObject("pages", pages);
		modelAndView.setViewName(mappingPath + "/admin_notice_modify");
		return modelAndView;
	}
	
	@RequestMapping("/admin_notice_modify_ok.do")
	public ModelAndView noticeModifyOk(HttpServletRequest request, ModelAndView modelAndView, BoardTO to) {

		// 넘어온 파라미터로 디비에 insert 
		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String thumbUrl = request.getParameter("thumbUrl");
		String familyMemberType = request.getParameter("type");
		String seq = request.getParameter("seq");

		//String nickname = "닉네임";
		String subTitle = ""; //subTitle있는것 없는것이 있어서, 있으면 나타나도록 수정.. 
		if (request.getParameter("subTitle") != null && !request.getParameter("subTitle").equals("") ){
		 subTitle = request.getParameter("subTitle");
		} 

		String saleStatus = "";
		if (request.getParameter("saleStatus") != null && !request.getParameter("saleStatus").equals("") ){
			saleStatus = request.getParameter("saleStatus");
		}

		//체크되어있으면 공개, 비체크는 비공개
		String isPrivateString = request.getParameter("isPrivate");
		Boolean isPrivate = true;
		if(isPrivateString.equals("false")){ isPrivate = false;}

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
		to.setFamilyMemberType(familyMemberType);
		to.setSaleStatus(saleStatus);
		to.setIsPrivate(isPrivate); //공개키
		to.setSeq(seq);

		int flag = daoInterface.modifyOk(to);

		modelAndView.addObject("flag", flag);
		modelAndView.addObject("seq", seq);
		modelAndView.addObject("category", category);
		modelAndView.setViewName(mappingPath +"/admin_notice_modify_ok");
		return modelAndView;
	}
}
