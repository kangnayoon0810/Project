<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="Main" />

<%@ include file="/WEB-INF/jsp/common/header.jsp"%>
<link rel="stylesheet" href="/resource/profile.css" />

<section class="myprofile-section">
	<div class="start-greetings">${req.getLoginedMember().getNickName() }<span>님 환영합니다</span></div>
	<div class="myprofile-box">
		<div class="chg-profile">
			<div class="my-img">
				<form action="/usr/common/upload" method="post" enctype="multipart/form-data">
					<label for="profileInput" class="image-label">
						<c:choose>
							<c:when test="${not empty profile.profileImageId }">
								<img id="previewImg" src="${profileImageUrl}" alt="프로필" style="width: 250px; height: 250px;" />
							</c:when>
							<c:otherwise>
								<img id="previewImg" src="/gen/default-profile.jpg" alt="기본 프로필" style="width: 250px; height: 250px; object-fit: cover;" />
							</c:otherwise>
						</c:choose>
					</label>
					<input type="file" name="profileImg" accept="image/*" id="profileInput" style="display: none;" />
					<button type="submit">업로드</button>
				</form>
			</div>
			<div class="chg-myinfo">
			</div>
		</div>
	</div>
</section>


<script>
  document.getElementById("profileInput").addEventListener("change", function(event) {
    const file = event.target.files[0];
    const preview = document.getElementById("previewImg");

    if (file) {
      const reader = new FileReader();
      reader.onload = function(e) {
        preview.src = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  });
  
  const urlParams = new URLSearchParams(window.location.search);
  const msg = urlParams.get("msg");

  if (msg === "empty") {
    alert("파일이 선택되지 않았습니다.");
  } else if (msg === "fail") {
    alert("이미지 업로드 중 오류가 발생했습니다.");
  } else if (msg === "success") {
    alert("프로필 이미지가 성공적으로 업로드되었습니다!");
  }
</script>

<%@ include file="/WEB-INF/jsp/common/footer.jsp"%>