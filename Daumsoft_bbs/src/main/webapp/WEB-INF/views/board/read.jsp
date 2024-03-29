<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
<script>
	$(function() {
		$("#list_btn").click(function() {
			location.href = "/board/list?curPage=${map.curPage}&search_option=${map.search_option}&keyword=${map.keyword}&postNum=${map.postNum}";
		});
		$("#edit_btn").click(function() {
			document.editForm.submit();
		});
		$("#remove_btn").click(function() {
			if (confirm("정말로 삭제하시겠습니까?")) {
				document.removeForm.submit();
			}
		});
	});
</script>
<style>
#file_icon{
	width:20px;
	height:20px;
	padding-top: 5px;
	padding-left: 5px;
}
</style>
</head>
<body>
	<div id="info">${map.dto.bno}</div>
	<div id="bbs">
		<div id="curTitle">
			<span style="padding: 10px; line-height: 30px; font-weight: bold;">제목</span>
			<span style="margin-left:16px;">${map.dto.title}</span>
		</div>
		<div id="curName">
			<span style="padding: 10px; line-height: 30px; font-weight: bold;">닉네임</span>
			<c:choose>
				<c:when test="${map.dto.writer eq '운영자'}">
					<span style="color: rgb(255, 78, 57); font-weight: bold;">
					<img src="../../../resources/img/admin.png" style="width: 15px; height: 15px;" />${map.dto.writer}</span>
				</c:when>
				<c:otherwise>
					<span>${map.dto.writer}</span>
				</c:otherwise>
			</c:choose>
		</div>
		<div id="curInfo">
			<div id="info_date">
				<span style="line-height: 30px; font-weight: bold;">등록일</span> <span
					style="padding-right: 5px;"> <fmt:formatDate
						pattern="yyyy-MM-dd HH:mm:ss" value="${map.dto.regDate}" />
				</span>
				<c:if test="${map.dto.regDate != map.dto.editDate}">
					<span
						style="line-height: 30px; font-weight: bold; padding-left: 20px;">수정일</span>
					<span style="padding-right: 5px;"> <fmt:formatDate
							pattern="yyyy-MM-dd HH:mm:ss" value="${map.dto.editDate}" />
					</span>
				</c:if>
			</div>
		</div>
		<div id="curOption">
			<div id="option_btn">
				<input type="button" id="list_btn" value="목록" style="cursor: pointer;" />
				<c:if test="${member.verify == 2 || (member.verify == 1 && map.dto.writer ne '운영자') || member.userNikname == map.dto.writer}">
					<input type="button" id="edit_btn" value="수정" style="cursor: pointer;" />
					<input type="button" id="remove_btn" value="삭제" style="cursor: pointer;" />
				</c:if>
			</div>
		</div>
		<form name="editForm" action="/board/edit" method="post">
			<input type="hidden" name="bno" value="${map.dto.bno}" />
			<input type="hidden" name="writer" value="${map.dto.writer}"/>
			<input type="hidden" name="curPage" value="${map.curPage}" />
			<input type="hidden" name="search_option" value="${map.search_option}" />
			<input type="hidden" name="keyword" value="${map.keyword}" />
		</form>
		<form name="removeForm" action="/board/delete" method="post">
			<input type="hidden" name="bno" value="${map.dto.bno}" />
			<input type="hidden" name="curPage" value="${map.curPage}" />
			<input type="hidden" name="search_option" value="${map.search_option}" />
			<input type="hidden" name="keyword" value="${map.keyword}" />
		</form>
		<div id="curContents">${map.dto.contents}</div>
		<div id="file_upload">
		<c:forEach items="${map.f_dto}" var="list">
			<div>
				<a href="/fileDownload.do?fileNo=${list.fileNo}">
					<img src="../../resources/img/file_icon.PNG" id="file_icon"/>
				${list.fileName}
				</a>
			</div>
		</c:forEach>
		</div>
	</div>
</body>
</html>