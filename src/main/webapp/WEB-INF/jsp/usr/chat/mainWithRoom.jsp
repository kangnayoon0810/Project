<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="핏 채팅" />

<%@ include file="/WEB-INF/jsp/common/header.jsp"%>

<div class="chat-room">
    <div class="message-list">
        <c:forEach var="msg" items="${messages}">
            <div class="message-box <c:if test='${msg.sender == memberId}'>me</c:if>">
                <div class="message-content">${msg.content}</div>
                <div class="message-time">${msg.regDate}</div>
            </div>
        </c:forEach>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/common/footer.jsp"%>