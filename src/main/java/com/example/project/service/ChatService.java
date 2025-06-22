package com.example.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project.dao.BoardDao;
import com.example.project.dao.ChatDao;
import com.example.project.dto.Board;
import com.example.project.dto.ChatMessage;
import com.example.project.dto.ChatRoomSummary;

@Service
public class ChatService {

	private ChatDao chatDao;

	public ChatService(ChatDao chatDao) {
		this.chatDao = chatDao;
	}
	
	public void saveMessage(ChatMessage dto) {
        this.chatDao.insertMessage(dto);
    }
	
	public List<ChatMessage> getMessagesByRoomId(String roomId) {
        return this.chatDao.getMessagesByRoomId(roomId);
    }

	public List<ChatRoomSummary> getChatListByMemberId(int memberId) {
		return this.chatDao.getChatListByMemberId(memberId);
	}
	
}