package com.project.greenpaw.mypage;

public interface MypageDaoInterface {

	MypageTO getUserInfo(MypageTO to);

	MypageTO getPostCount(MypageTO to);

	MypageTO getCommentCount(MypageTO to);

	int changePassword(MypageTO to);

	int deleteAccount(MypageTO to);

	int deleteKaKaoAccount(MypageTO to);

	MypageTO getNote(MypageTO to);

	int editNote(MypageTO to);

}