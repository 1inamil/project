package com.project.greenpaw.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service("userService")
@Service
public class UserDaoImpl implements UserDaoInterface {
	
	@Autowired
	private UserDAO userDao;

	@Override
	public int getNickname(UserTO user) {
		// TODO Auto-generated method stub
		return userDao.getNickname(user);
	}

	@Override
	public int getMail(UserTO user) {
		// TODO Auto-generated method stub
		return userDao.getMail(user);
	}

	@Override
	public String sha256(String password) {
		// TODO Auto-generated method stub
		return userDao.sha256(password);
	}

	@Override
	public int joinUser(UserTO user) {
		// TODO Auto-generated method stub
		return userDao.joinUser(user);
	}

	@Override
	public int logInOk(UserTO to) {
		// TODO Auto-generated method stub
		return userDao.logInOk(to);
	}

	@Override
	public UserTO getSessionData(UserTO to) {
		// TODO Auto-generated method stub
		return userDao.getSessionData(to);
	}

	@Override
	public int forgotPassword(UserTO to) {
		// TODO Auto-generated method stub
		return userDao.forgotPassword(to);
	}

	@Override
	public int kakaoIdCheck(UserTO to) {
		// TODO Auto-generated method stub
		return userDao.kakaoIdCheck(to);
	}

	@Override
	public int kakaoJoin(UserTO to) {
		// TODO Auto-generated method stub
		return userDao.kakaoJoin(to);
	}

	@Override
	public int kakaoNicknamecheck(UserTO to) {
		// TODO Auto-generated method stub
		return userDao.kakaoNicknamecheck(to);
	}

	@Override
	public UserTO loginInformation(String email) {
		// TODO Auto-generated method stub
		return userDao.loginInformation(email);
	}

	@Override
	public int socialCheck(String nickname) {
		// TODO Auto-generated method stub
		return userDao.socialCheck(nickname);
	}

}
