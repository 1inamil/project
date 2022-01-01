package com.project.greenpaw.mypage.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.project.greenpaw.mypage.MypageDaoInterface;
import com.project.greenpaw.mypage.MypageTO;

@Controller
@SessionAttributes({"nickname", "mail","fromSocial"})
public class MypageController {

	@Autowired
	private MypageDaoInterface daoInterface;
	
	@RequestMapping("/mypage.do")
	public ModelAndView mypageView(HttpServletRequest request, MypageTO to, MypageTO dataTo, 
			ModelAndView modelAndView, Model model) {
		
		String mail = (String) request.getSession().getAttribute("mail");
		String nickname = (String) request.getSession().getAttribute("nickname");
		
		model.addAttribute(nickname);
		model.addAttribute(mail);
		
		dataTo.setMail(mail);
		dataTo.setNickname(nickname);

		//회원 정보 => BeanPropertyRowMapper로 만든거라서 다시 to 메모리에 저장시켜줌
		to.setAuthType( daoInterface.getUserInfo(dataTo).getAuthType() );
		to.setCreatedAt( daoInterface.getUserInfo(dataTo).getCreatedAt() );
		to.setFromSocial( daoInterface.getUserInfo(dataTo).getFromSocial());
		
		model.addAttribute("fromSocial", to.getFromSocial());
		
		to = daoInterface.getPostCount(dataTo); //글 등록 정보
		to = daoInterface.getCommentCount(dataTo); //댓글 등록 정보
		to = daoInterface.getNote(dataTo); 
		
		String strNotes = to.getNote();
		//System.out.println(strNotes);
	
		String[] noteArr = {};
		if(strNotes!=null){
		 	noteArr = strNotes.split(",");
		}
		
		modelAndView.addObject("noteArr", noteArr);
		modelAndView.addObject("to", to);
		modelAndView.setViewName("/mypage/mypage");
		
		return modelAndView;
	
	}
	
	@RequestMapping("/mypage_note.do")
	public ModelAndView mypageNote(@ModelAttribute("nickname") String nickname, 
			HttpServletRequest request, MypageTO to, ModelAndView modelAndView) {
		
		String note = request.getParameter("note");
		//System.out.println(note);
		//String nickname = (String)request.getSession().getAttribute("nickname");
		
		to.setNote(note);
		to.setNickname(nickname);
		
		int flag = daoInterface.editNote(to);
		
		modelAndView.addObject("flag", flag);
		modelAndView.setViewName("/mypage/mypage_note");
		
		return modelAndView;
	}
	
	@RequestMapping("password_change.do")
	public ModelAndView passwordChangeView(@ModelAttribute("mail") String mail, @ModelAttribute("nickname") String nickname, 
			MypageTO to, ModelAndView modelAndView) {

		to.setMail(mail);
		to.setNickname(nickname);
		to = daoInterface.getUserInfo(to);
		
		Boolean fromSocial = to.getFromSocial();
		//System.out.println(fromSocial);
		
		modelAndView.addObject("fromSocial", fromSocial);
		modelAndView.setViewName("/mypage/password_change");
	
		return modelAndView;
	
	}
	
	@RequestMapping("/password_change_ok.do")
	public ModelAndView passwordChangeOk(@ModelAttribute("mail")String mail, @ModelAttribute("nickname") String nickname,
			HttpServletRequest request, MypageTO to, ModelAndView modelAndView) {
		
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");

		to.setOldPassword(oldPassword);
		to.setNewPassword(newPassword);
		to.setMail(mail);
		to.setNickname(nickname);
		
		int flag = daoInterface.changePassword(to);
		
		modelAndView.addObject("flag", flag);
		modelAndView.setViewName("/mypage/password_change_ok");

		return modelAndView;
	}
	
	
	@RequestMapping("delete_account.do")
	public String deleteAccountView() {
		return "/mypage/delete_account";
	}
	
	@RequestMapping("delete_account_ok.do")
	public ModelAndView deleteAccountOk(@ModelAttribute("mail") String mail, @ModelAttribute("nickname") String nickname, 
			MypageTO to, ModelAndView modelAndView, HttpServletRequest request, SessionStatus sessionStatus) {
		
		String password = request.getParameter("pwd");
		
		to.setOldPassword(password);
		to.setMail(mail);
		to.setNickname(nickname);
		
		int flag = daoInterface.deleteAccount(to);
		
		if(flag == 1) {
			sessionStatus.setComplete();
		}
		
		modelAndView.addObject("flag", flag);
		modelAndView.setViewName("/mypage/delete_account_ok");
		
		return modelAndView;
	}
	
	@RequestMapping("kakao_delete_account_ok.do")
	public ModelAndView deleteKaKaoAccountOk(@ModelAttribute("mail") String mail, @ModelAttribute("nickname") String nickname, 
			MypageTO to, ModelAndView modelAndView, HttpServletRequest request, SessionStatus sessionStatus) {
		
		to.setMail(mail);
		to.setNickname(nickname);
		
		int flag = daoInterface.deleteKaKaoAccount(to);
		
		if(flag == 1) {
			sessionStatus.setComplete();
		}
		
		modelAndView.addObject("flag", flag);
		modelAndView.setViewName("/mypage/kakao_delete_account_ok");
		
		return modelAndView;
	}
}
