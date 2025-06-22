package com.example.project.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.project.dto.ChatMessage;
import com.example.project.dto.ChatRoomSummary;
import com.example.project.dto.Req;
import com.example.project.service.ChatService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsrChatMainController {

	private ChatService chatService;
	private Req req;

	public UsrChatMainController(ChatService chatService, Req req) {
		this.chatService = chatService;
		this.req = req;
	}

	// 내 프로필 → 핏 채팅 → 채팅 리스트만
	@GetMapping("/usr/chat/mainListOnly")
	public String showChatList(Model model) {

		int loginedMemberId = req.getLoginedMember().getId();
		if (loginedMemberId == 0) {
			return "redirect:/usr/member/login";
		}

		List<ChatRoomSummary> chatList = chatService.getChatListByMemberId(loginedMemberId);

		model.addAttribute("chatList", chatList);
		model.addAttribute("memberId", loginedMemberId);

		return "usr/chat/mainListOnly";
	}

	// 상대방 프로필 → 핏 채팅 or 리스트에서 상대방 선택
	@GetMapping("/usr/chat/room")
	public String showChatRoom(HttpServletRequest request, Model model) {

		int loginedMemberId = req.getLoginedMember().getId();
		if (loginedMemberId == 0) {
			return "redirect:/usr/member/login";
		}
		model.addAttribute("memberId", loginedMemberId);

		String roomId = request.getParameter("roomId");
		String partnerNickName = request.getParameter("partnerNickName");
		String partnerProfileIdStr = request.getParameter("partnerProfileId");

		if (roomId != null) {
			List<ChatMessage> messages = chatService.getMessagesByRoomId(roomId);
			model.addAttribute("roomId", roomId);
			model.addAttribute("partnerNickName", partnerNickName);
			model.addAttribute("partnerProfileId", partnerProfileIdStr != null ? Integer.parseInt(partnerProfileIdStr) : 0);
			model.addAttribute("messages", messages);
		}

		return "usr/chat/mainWithRoom";
	}

}
