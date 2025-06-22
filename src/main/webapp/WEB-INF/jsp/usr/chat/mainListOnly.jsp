<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="핏 채팅" />

<%@ include file="/WEB-INF/jsp/common/header.jsp"%>

<link rel="stylesheet" href="/resource/chat.css" />


<div class="fit-chating">
	<div class="chat-and-room">
		<div class="chat-room">
			<div class="do-chat">
			</div>
		</div>
		<div class="chat-list">
			<p>채팅 목록</p>
			<c:forEach var="chat" items="${chatList}">
				<div class="chat-item" data-room-id="${chat.roomId}" data-partner-nick-name="${chat.partnerNickName}" data-partner-profile-id="${chat.partnerProfileId}">
		            <div class="chat-content">
			            <div class="chat-profile">
			                <img src="/usr/profile/image/${chat.partnerProfileId}" alt="프로필 이미지" />
			            </div>
			            <div class="chat-info">
			                <div class="chat-nickname">${chat.partnerNickName}</div>
			                <div class="chat-preview">${chat.lastMessage}</div>
			            </div>
		            </div>
		            <div class="chat-side">
			            <div class="chat-date-cnt">
				            <div class="chat-time">${chat.lastMessageTime}</div>
				            <div class="chat-conting">
				            	<div class="chat-cnt">${chat.lastMessageTime}</div>
					        </div>
			            </div>
		            </div>
		        </div>
			</c:forEach>
	    </div>
	</div>
</div>

<script>
  document.querySelectorAll('.chat-item').forEach(item => {
    item.addEventListener('click', function () {
      const roomId = this.dataset.roomId;
      const partnerNickName = encodeURIComponent(this.dataset.partnerNickName); // 여기만 수정!
      const partnerProfileId = this.dataset.partnerProfileId;

      const url = `/usr/chat/room?roomId=${roomId}&partnerNickName=${partnerNickName}&partnerProfileId=${partnerProfileId}`;
      location.href = url;
    });
  });
</script>




<%@ include file="/WEB-INF/jsp/common/footer.jsp"%>