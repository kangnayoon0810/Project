package com.example.project.service;

import org.springframework.stereotype.Service;

import com.example.project.dao.MemberDao;
import com.example.project.dto.Member;

@Service
public class MemberService {

	private MemberDao memberDao;

	public MemberService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public void signupMember(String name, int sex, String nickName, String phoneNumber, String loginId, String loginPw, String eMail, String address, int authLevel) {
		this.memberDao.signupMember(name, sex, nickName, phoneNumber, loginId, loginPw, eMail, address, authLevel);
	}

	public Member getMemberByNickName(String nickName) {
		return this.memberDao.getMemberByNickName(nickName);
	}

	public Member getMemberByPhoneNumber(String phoneNumber) {
		return this.memberDao.getMemberByPhoneNumber(phoneNumber);
	}

	public Member getMemberByLoginId(String loginId) {
		return this.memberDao.getMemberByLoginId(loginId);
	}

	public Member getMemberByEMail(String eMail) {
		return this.memberDao.getMemberByEMail(eMail);
	}
	
	public String getLoginId(int id) {
		return this.memberDao.getLoginId(id);
	}

	public Member getMemberByAuthLevel(int authLevel) {
		return this.memberDao.getMemberByAuthLevel(authLevel);
	}

	public String getNickName(String nickName) {
		return this.memberDao.getNickName(nickName);
	}

	public int getLastInsertId() {
		return this.memberDao.getLastInsertId();
	}

	public Member getMemberById(int id) {
		return this.memberDao.getMemberById(id);
	}

	public void modifyMember(int id, String nickName, String phoneNumber, String eMail, String address) {
		this.memberDao.modifyMember(id, nickName, phoneNumber, eMail, address);
	}

	public void modifyPassword(int id, String loginPw) {
		this.memberDao.modifyPassword(id, loginPw);
	}

	public Member getTrainerById(int authLevel) {
		return this.memberDao.getTrainerById(authLevel);
	}

	public Member findByUsername(String nickName) {
		return null;
	}
	
}