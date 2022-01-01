package com.project.greenpaw.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MypageDaoImpl implements MypageDaoInterface {
		
	@Autowired
	private MypageDAO mypageDao;
	
	@Override
	public MypageTO getUserInfo(MypageTO to) {
		// TODO Auto-generated method stub
		return mypageDao.getUserInfo(to);
	}

	@Override
	public MypageTO getPostCount(MypageTO to) {
		// TODO Auto-generated method stub
		return mypageDao.getPostCount(to);
	}

	@Override
	public MypageTO getCommentCount(MypageTO to) {
		// TODO Auto-generated method stub
		return mypageDao.getCommentCount(to);
	}

	@Override
	public int changePassword(MypageTO to) {
		// TODO Auto-generated method stub
		return mypageDao.changePassword(to);
	}

	@Override
	public int deleteAccount(MypageTO to) {
		// TODO Auto-generated method stub
		return mypageDao.deleteAccount(to);
	}

	@Override
	public int deleteKaKaoAccount(MypageTO to) {
		// TODO Auto-generated method stub
		return mypageDao.deleteKaKaoAccount(to);
	}

	@Override
	public MypageTO getNote(MypageTO to) {
		// TODO Auto-generated method stub
		return mypageDao.getNote(to);
	}

	@Override
	public int editNote(MypageTO to) {
		// TODO Auto-generated method stub
		return mypageDao.editNote(to);
	}

}
