package com.example.project.service;

import org.springframework.stereotype.Service;

import com.example.project.dao.MemberDao;
import com.example.project.dto.FileDto;
import com.example.project.dto.Member;

@Service
public class MemberService {

	private MemberDao memberDao;
	private FileService fileService;

	public MemberService(MemberDao memberDao, FileService fileService) {
		this.memberDao = memberDao;
		this.fileService = fileService;
	}

	public void signupMember(String name, int sex, String nickName, int phoneNumber, String loginId, String loginPw, String eMail, int authLevel) {
		this.memberDao.signupMember(name, sex, nickName, phoneNumber, loginId, loginPw, eMail, authLevel);
	}

	public Member getMemberByNickName(String nickName) {
		return this.memberDao.getMemberByNickName(nickName);
	}

	public Member getMemberByPhoneNumber(int phoneNumber) {
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
	
	public void setProfileImageUrl(Member member) {
	    // 1. member 자체가 null일 경우 바로 리턴 (예외 방지)
	    if (member == null) return;

	    // 2. profileImageId가 있을 경우
	    if (member.getProfileImageId() != null) {
	        FileDto file = fileService.getFileById(member.getProfileImageId());

	        // 2-1. 파일이 실제로 존재할 경우
	        if (file != null) {
	            member.setProfileImageUrl(file.getForPrintUrl()); // ✅ 업로드한 이미지 경로 세팅
	            return;
	        }
	    }

	    // 3. profileImageId가 null이거나, 해당 파일이 DB에 없을 경우
	    member.setProfileImageUrl("/gen/default-profile.jpg"); // ✅ 기본 이미지 fallback
	}

	public Member getMemberById(int id) {
		return this.memberDao.getMemberById(id);
	}


}