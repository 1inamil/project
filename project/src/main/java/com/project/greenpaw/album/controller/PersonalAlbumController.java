package com.project.greenpaw.album.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.project.greenpaw.albumboard.AlbumDaoInterface;
import com.project.greenpaw.albumboard.AlbumListTO;
import com.project.greenpaw.albumboard.AlbumTO;

@Controller
public class PersonalAlbumController {
	
	@Autowired
	private AlbumDaoInterface daoInterface;
	
	@RequestMapping("/personal_album_list.do")
	public ModelAndView personalAlbumListView(HttpServletRequest request, AlbumListTO listTO, ArrayList<AlbumTO> lists,
			ModelAndView modelAndView, Model model) {
		
		String nickname = (String)request.getSession().getAttribute("nickname");
			
		//listTO에 값 넣기
		listTO.setNickname(nickname);	
		
		if(request.getParameter("cpage") != null){
			listTO.setCpage(Integer.parseInt(request.getParameter("cpage")));
		}
		
		String type = request.getParameter("type"); //type데이터 가져오기
		listTO.setFamilyMemberType(type);
		String catSeq = "2"; //성장앨범 게시판
		listTO.setCatSeq(catSeq);
			
		//데이터 가져오기	
		listTO = daoInterface.boardList(listTO);	
		lists = listTO.getBoardLists();
		
		modelAndView.addObject("lists", lists);
		modelAndView.addObject("listTO", listTO);
		modelAndView.setViewName("/personal_album/personal_album_list");
		
		return modelAndView;
		
	}
	
	@RequestMapping("/personal_album_view.do")
	public ModelAndView personalAlbumView(HttpServletRequest request, ModelAndView modelAndView, Model model, AlbumTO to) {
	
		String nickname = (String)request.getSession().getAttribute("nickname");
		String seq = request.getParameter("seq");
		String cpage = request.getParameter("cpage");
		String type = request.getParameter("type");
		String catSeq = "2"; //성장앨범 게시판
	
		to.setSeq(seq);
		to.setNickname(nickname);
		to.setCatSeq(catSeq);
	
		to = daoInterface.view(to);
	
		seq = to.getSeq();
		nickname = to.getNickname();
		String title = to.getTitle();
		String content = to.getContent();
		String thumbUrl = to.getThumbUrl();
		String familyMember = to.getFamilyMemberType();
		Boolean isPrivate = to.getIsPrivate();
		String wDate = to.getCreatedAt();
		String mDate = to.getUpdatedAt();
		
		model.addAttribute("seq", seq);
		model.addAttribute("nickname", nickname);
		model.addAttribute("title", title);
		model.addAttribute("content", content);
		model.addAttribute("thumbUrl", thumbUrl);
		model.addAttribute("familyMember", familyMember);
		model.addAttribute("wDate", wDate);
		model.addAttribute("mDate", mDate);
		model.addAttribute("cpage", cpage);
		model.addAttribute("type", type);
		
		modelAndView.addObject("isPrivate", isPrivate);
		modelAndView.setViewName("/personal_album/personal_album_view");
		
		return modelAndView;
	}
	
	@RequestMapping("/personal_album_delete_ok.do")
	public ModelAndView personalAlbumDeleteOk(HttpServletRequest request,ModelAndView modelAndView, AlbumTO to) {
	
		String seq = request.getParameter("seq");
		String nickname = (String) request.getSession().getAttribute("nickname");
		String catSeq = "2"; //성장앨범 게시판
		
		String[] imageList = request.getParameterValues("imageList[]");
		//System.out.println("imageList : " + imageList);
		
		if (imageList != null) {
			System.out.println("imageList length : " + imageList.length); 
		
			for (String fileUrl : imageList) {
				
				String[] splitedFileUrl = fileUrl.split("/");
				String fileName = splitedFileUrl[splitedFileUrl.length - 1];
				//System.out.println("fileName : " +fileName);
		
				// 각자 바꿔주기~! (아니면 realPath 쓰기)
				String filePath = request.getRealPath("upload") + "/";
				filePath += fileName;
				//System.out.println("filePath : "+filePath);
		
				File file = new File(filePath);
				
				if (file.exists()) {
					file.delete(); // 파일이 있으면 삭제 
					//System.out.println("파일 삭제 성공");
				} 
			}
		}
		
		to.setSeq(seq);
		to.setCatSeq(catSeq);
		to.setNickname(nickname);
		
		int flag = daoInterface.deleteOk(to);
		
		modelAndView.setViewName("/personal_album/personal_album_delete_ok");
		modelAndView.addObject("flag", flag);
		
		return modelAndView;
	}
	
	@RequestMapping("/personal_album_write.do")
	public String personalAlbumWriteView() {
		return "/personal_album/personal_album_write";
	}
	
	@RequestMapping("save_img.do")
	public ModelAndView saveImage(HttpServletRequest request, ModelAndView modelAndView) throws IOException {
		// 1. 넘어온 이미지 파일 upload 폴더에 업로드
		// 2. 넘어온 이미지 파일을 url 로 반환

		/* String uploadPath = request.getRealPath("upload"); */
		String uploadPath = request.getSession().getServletContext().getRealPath("upload"); 

		File uploadDirectory = new File(uploadPath);

		if(!uploadDirectory.exists()){
			uploadDirectory.mkdirs();
		} 

		int maxFileSize = 1024 * 1024 * 5; // 5메가
		String encType = "utf-8";

		MultipartRequest multi = new MultipartRequest(request, uploadPath, maxFileSize, encType, new DefaultFileRenamePolicy());
		/* String originFileName = multi.getOriginalFileName("image"); */
		//System.out.println(uploadPath);
		String originFileName = multi.getFilesystemName("image");

		String wholePath = request.getRequestURL().toString();
		String servletPath = request.getServletPath();
		String tempUploadPath = wholePath.replace(servletPath, "/upload/");
		//System.out.println(wholePath.replace(servletPath, "/upload/"));
		
		String url = tempUploadPath + originFileName;
		
		modelAndView.addObject("url", url);
		modelAndView.setViewName("/personal_album/save_img");
		
		return modelAndView;
		
	}
	
	
	@RequestMapping("personal_album_write_ok.do")
	public ModelAndView personalAlbumWriteOk(ModelAndView modelAndView, HttpServletRequest request, AlbumTO to) {
		// 넘어온 파라미터로 디비에 insert 
    	String title = request.getParameter("title");
    	String content = request.getParameter("content");
    	String thumbUrl = request.getParameter("thumbUrl");
    	String nickname = (String)request.getSession().getAttribute("nickname");
    	String isPrivateString = request.getParameter("isPrivate");
    	Boolean isPrivate = true;
    	if(isPrivateString.equals("false")){
    		isPrivate = false;
    	}
    	
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
    	to.setHit(hit);
    	to.setCatSeq(catSeq);
    	to.setLikeCount(likeCount);
    	to.setSaleStatus(saleStatus);
    	to.setSubTitle(subTitle);
    	
    	int flag = daoInterface.writeOk(to);
    	
    	modelAndView.addObject("flag", flag);
    	//modelAndView.addObject("seq", to.getSeq());
    	modelAndView.setViewName("/personal_album/personal_album_write_ok");
    	
    	return modelAndView;
	}

	@RequestMapping("/personal_album_modify.do")
	public ModelAndView personalAlbumModify(HttpServletRequest request, ModelAndView modelAndView, AlbumTO to) {

		String nickname = (String) request.getSession().getAttribute("nickname");
		String seq = request.getParameter("seq");
		String cpage = request.getParameter("cpage");
		String type = request.getParameter("type");
		String catSeq = "2"; //성장앨범 게시판
		
		to.setSeq(seq);
		to.setNickname(nickname);
		to.setCatSeq(catSeq);
		
		to = daoInterface.view(to);
		
		modelAndView.addObject("to", to);
		modelAndView.addObject("cpage", cpage);
		modelAndView.addObject("type", type);
		modelAndView.setViewName("/personal_album/personal_album_modify");
		
		return modelAndView;
		
	}
	
	@RequestMapping("/personal_album_modify_ok.do")
	public ModelAndView modifyPersonalAlbumOk(HttpServletRequest request, ModelAndView modelAndView, AlbumTO to) {

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String thumbUrl = request.getParameter("thumbUrl");
		String nickname = (String) request.getSession().getAttribute("nickname");
		String isPrivateString = request.getParameter("isPrivate");
		Boolean isPrivate = true;
		if (isPrivateString.equals("false")) {
			isPrivate = false;
		}
		String seq = request.getParameter("seq");

		String familyMemberType = request.getParameter("familyMemberType");

		int hit = 0;
		int likeCount = 0;
		String catSeq = "2"; //성장앨범 게시판
		String saleStatus = null;
		String subTitle = null;

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

		String redirectUrl = "/greenpaw/personal_album_view.do?type=" + type + "&cpage=" + cpage + "&seq=" + seq;
		//System.out.println("redirectUrl : " + redirectUrl);

		int flag = daoInterface.modifyOk(to);
		
		modelAndView.addObject("flag", flag);
		modelAndView.addObject("seq", to.getSeq());
		modelAndView.addObject("redirect", redirectUrl);
		modelAndView.setViewName("/personal_album/personal_album_modify_ok");
		
		return modelAndView;
		
	}
	

}
