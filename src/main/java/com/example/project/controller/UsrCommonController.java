package com.example.project.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.dto.FileDto;
import com.example.project.dto.Req;
import com.example.project.service.FileService;
import com.example.project.service.ProfileService;

@Controller
public class UsrCommonController {


	private FileService fileService;
	private ProfileService profileService;
	private Req req;

	public UsrCommonController(FileService fileService, ProfileService profileService, Req req) {
		this.fileService = fileService;
		this.profileService = profileService;
		this.req = req;
	}
	
	@PostMapping("/usr/common/upload")
	public String upload(@RequestParam("profileImg") MultipartFile file) {
	    if (file == null || file.isEmpty()) {
	        return "redirect:/usr/profile/myPage?msg=empty";
	    }

	    int loginedMemberId = req.getLoginedMember().getId();

	    try {
	        // 1. 파일 저장
	        int fileId = fileService.saveFile(file);

	        // 2. 프로필 이미지 ID 갱신
	        this.profileService.updateProfileImageId(loginedMemberId, fileId);

	        return "redirect:/usr/profile/myPage?msg=success";

	    } catch (IOException e) {
	        e.printStackTrace();
	        return "redirect:/usr/profile/myPage?msg=fail";
	    }
	}

	
	@GetMapping("/usr/common/view")
	public String view(Model model) {
		
		List<FileDto> files = fileService.getFiles();
		
		model.addAttribute("files", files);
		
		return "usr/common/view";
	}
	
	@GetMapping("/usr/common/file/{fileId}")
	@ResponseBody
	public Resource fileLoad(Model model, @PathVariable("fileId") int id) throws IOException {
		
		FileDto fileDto = fileService.getFileById(id);
		
		return new UrlResource("file:" + fileDto.getSavedPath());
	}

}