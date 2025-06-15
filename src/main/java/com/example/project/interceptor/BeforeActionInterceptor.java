package com.example.project.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.project.dto.FileDto;
import com.example.project.dto.Profile;
import com.example.project.dto.Req;
import com.example.project.service.FileService;
import com.example.project.service.ProfileService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class BeforeActionInterceptor implements HandlerInterceptor {

	private Req req;
	private ProfileService profileService;
	private FileService fileService;

	public BeforeActionInterceptor(Req req, ProfileService profileService, FileService fileService) {
		this.req = req;
		this.profileService = profileService;
		this.fileService = fileService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		req.init();
		
		if (req.isLogined()) {
		    int memberId = req.getLoginedMember().getId();

		    Profile profile = profileService.getProfileByMemberId(memberId);

		    // 🔥 프로필 없으면 생성
		    if (profile == null) {
		        profileService.createDefaultProfile(memberId);
		        profile = profileService.getProfileByMemberId(memberId);
		    }

		    FileDto profileImg = null;
		    if (profile.getProfileImageId() != null) {
		        profileImg = fileService.getFileById(profile.getProfileImageId());
		    }

		    String profileImageUrl = "/gen/default-profile.jpg";
		    if (profileImg != null) {
		        profileImageUrl = profileImg.getForPrintUrl();
		    }

		    request.setAttribute("profile", profile);
		    request.setAttribute("profileImageUrl", profileImageUrl);
		}

//		
//		// 🔥 로그인 상태일 경우 프로필 이미지 세팅
//				if (req.isLogined()) {
//					int memberId = req.getLoginedMember().getId();
//					Profile profile = profileService.getProfileByMemberId(memberId);
//
//					if (profile != null) {
//						FileDto profileImg = null;
//						if (profile.getProfileImageId() != null) {
//							profileImg = fileService.getFileById(profile.getProfileImageId());
//						}
//
//						String profileImageUrl = "/gen/default-profile.jpg";
//						if (profileImg != null) {
//							profileImageUrl = profileImg.getForPrintUrl();
//						}
//
//						request.setAttribute("profile", profile);
//						request.setAttribute("profileImageUrl", profileImageUrl);
//					}
//				}

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}