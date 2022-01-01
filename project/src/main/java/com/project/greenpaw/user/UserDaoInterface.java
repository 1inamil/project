package com.project.greenpaw.user;

public interface UserDaoInterface {

	//회원 가입시 닉네임 중복 체크
	int getNickname(UserTO user);

	//회원 가입시 이메일 중복체크
	int getMail(UserTO user);

	//패스워드 암호화
	String sha256(String password);

	//서비스 회원가입
	int joinUser(UserTO user);

	//서비스 로그인 ok
	int logInOk(UserTO to);

	//세션 데이터 -> 로그인 정보 넣기
	UserTO getSessionData(UserTO to);

	//비밀번호 분실시 비밀번호 교체
	int forgotPassword(UserTO to);

	//카카오정보가 db에 있는지 확인
	int kakaoIdCheck(UserTO to);

	//카카오로그인이 처음일 때 가입
	int kakaoJoin(UserTO to);

	//카카오 닉네임이 중복 되는지 확인
	int kakaoNicknamecheck(UserTO to);

	//카카오 유저 닉네임 가져오기
	UserTO loginInformation(String email);

	//탈퇴시 소셜인지 아닌지 확인
	int socialCheck(String nickname);

}