<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="${board.getBoardName() }" />

<%@ include file="/WEB-INF/jsp/common/header.jsp"%>

<script src="/resource/article.js"></script>

<section class="list-section">
	<div class="list-box">
		<div class="search-box">
			<div class="memberCategory">
				<c:if test="${memberCategory == 1 }">
					<div>회원 커뮤니티</div>
				</c:if>
				<c:if test="${memberCategory == 2 }">
					<div>트레이너 커뮤니티</div>
				</c:if>
			</div>
			<form action="list" method="get">
				<div class="search-bar">
					<div class="search">
						<div>
							<div>
								<input type="hidden" name="boardId" value="${board.getId() }"/>
								<input type="text" name="keyWord" value="${keyWord }" placeholder="검색어를 입력하세요" />
							</div>
						</div>
					</div>
					<div class="search-btn">
						<button><i class="fa-solid fa-magnifying-glass"></i></button>
					</div>
				</div>
			</form>
		</div>
		<div class="table-box">
			<div class="article-box2">
				<c:if test="${memberCategory == 1 }">
					<c:forEach items="${articles }" var="article">
						<div class="profile-box">
							<div><img src="${article.authorMember.profileImageUrl}" alt="프로필" /></div>
							<div class="nickname">${article.getNickName() }</div>
						</div>
						<style>
							p > img {
								width: 200px;
								height: 200px;
							}
						</style>
						<div class="content-box">
							<div class="content">${article.getContent() }</div>
						</div>
						<div class="cnt-box">
							<div class="like">
								<button>
									<i class="fa-regular fa-heart"></i>
									<span id="likePointCnt"></span>
									${article.getLikeCnt() }
								</button>
							</div>
							<a class="comments" href="/usr/article/detail?id=${article.getId() }"><i class="fa-regular fa-comment"></i></a>
							<span id="commentCnt">&nbsp;${article.getCommentCnt() }</span>
							<div class="views">조회수 ${article.getViewCnt() }</div>
						</div>
						<div class="date-box">
							<div class="date">${article.getRegDate().substring(2, 16) }</div>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${memberCategory == 2 }">
					<c:forEach items="${articles }" var="article">
						<div class="profile-box">
							<div><img src="${article.authorMember.profileImageUrl}" alt="프로필" /></div>
							<div class="nickname">${article.getNickName() }</div>
						</div>
						<div class="content-box">
							<div class="content">${article.getContent() }</div>
						</div>
						<div class="cnt-box">
							<div class="like">
								<button>
									<i class="fa-regular fa-heart"></i>
									<span id="likePointCnt"></span>
									${article.getLikeCnt() }
								</button>
							</div>
							<a class="comments" href="/usr/article/detail?id=${article.getId() }"><i class="fa-regular fa-comment"></i></a>
							<span id="commentCnt">&nbsp;${article.getCommentCnt() }</span>
							<div class="views">조회수 ${article.getViewCnt() }</div>
						</div>
						<div class="date-box">
							<div class="date">${article.getRegDate().substring(2, 16) }</div>
						</div>
					</c:forEach>
				</c:if>
				
			<div class="bt-bar">
				<c:if test="${req.getLoginedMember().getAuthLevel() == 1 && board.getId() != 2 }">
					  <!-- 회원이 트레이너 커뮤니티가 아닐 때만 -->
					  <a href="/usr/article/write?boardId=${board.getId() }&memberCategory=${memberCategory }" class="write-btn-fixed"><i class="fa-solid fa-pen"></i>&nbsp;&nbsp;글쓰기</a>
				</c:if>
				<c:if test="${req.getLoginedMember().getAuthLevel() == 2 && board.getId() != 1 }">
					  <!-- 트레이너가 회원 커뮤니티가 아닐 때만 -->
					  <a href="/usr/article/write?boardId=${board.getId() }&memberCategory=${memberCategory }" class="write-btn-fixed"><i class="fa-solid fa-pen"></i>&nbsp;&nbsp;글쓰기</a>
				</c:if>
			</div>
				
			</div>
		</div>
		
			<%-- <div class="mb-8 mt-4 w-2/3 mx-auto text-center">
				<div class="join">
					<c:set var="queryString" value="?boardId=${board.getId() }&keyWord=${keyWord }&searchType=${searchType }" />
					
					<c:if test="${startPage != 1 }">
						<a class="join-item btn btn-sm" href="${queryString }&cPage=1"><i class="fa-solid fa-angles-left"></i></a>
						<a class="join-item btn btn-sm" href="${queryString }&cPage=${startPage - 1 }"><i class="fa-solid fa-caret-left"></i></a>
					</c:if>
					<c:forEach begin="${startPage }" end="${endPage }" var="i">
						<a class="join-item btn-sm btn ${cPage == i ? 'btn-active' : '' }" href="${queryString }&cPage=${i }">${i }</a>
					</c:forEach>
					<c:if test="${endPage != pagesCnt }">
		 				<a class="join-item btn btn-sm" href="${queryString }&cPage=${endPage + 1 }"><i class="fa-solid fa-caret-right"></i></a>
						<a class="join-item btn btn-sm" href="${queryString }&cPage=${pagesCnt }"><i class="fa-solid fa-angles-right"></i></a>
					</c:if>
				</div>
			</div> --%>
		
	</div>
</section>


<%@ include file="/WEB-INF/jsp/common/footer.jsp"%>