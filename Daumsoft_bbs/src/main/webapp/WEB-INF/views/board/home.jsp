<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
</head>
<body>
<c:if test="${param.message == 'nologin'}">
	<script>
		alert("로그인 정보가 일치하지 않습니다.");
	</script>
</c:if>
	<div id="info">Daumsoft</div>
	<div id="bbs">
		<img id="ds" src="../../../resources/img/ds.png" alt="다음소프트"
			style="width: 100%" />
	</div>
	<button type="button" id="bbs_btn">
		<a href="/board/list">게시판 입장</a>
	</button>
</body>
</html>