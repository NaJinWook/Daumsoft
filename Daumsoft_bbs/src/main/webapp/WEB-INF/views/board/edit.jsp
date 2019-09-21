<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
<script>
	$(function() {
		$("#update_commit").click(function() {
			var title = $.trim($("#title").val());
			var contents = $.trim($("#contents").val());
			var writer = $.trim($("#writer").val());
			if (title == "") {
				alert("제목을 입력하세요.");
				$("#title").focus();
				return;
			} else if (contents == "") {
				alert("내용을 입력하세요.");
				$("#contents").focus();
				return;
			} else if (writer == "") {
				alert("이름을 입력하세요.");
				$("#writer").focus();
				return;
			}
			document.updateForm.submit();
		});
		$("#update_cancel").click(function() {
			history.back();
		});
		$("#list_back").click(function() {
			location.href = "/board/list?curPage=${curPage}&search_option=${search_option}&keyword=${keyword}";
		});
	});
</script>
</head>
<body>
	<div id="info">${dto.bno}</div>
	<div id="bbs">
		<form name="updateForm" method="post" action="/board/update">
			<div id="title_section">
				<input type="text" id="title" name="title" maxlength="45"
					placeholder="제목을 입력하세요" value="${dto.title}" autocomplete="off" />
			</div>
			<div id="contents_section">
				<textarea id="contents" name="contents">${dto.contents}</textarea>
			</div>
			<div id="writer_section">
				<input type="text" id="writer" name="writer"
					placeholder="이름을 입력하세요" maxlength="7" value="${dto.writer}"
					autocomplete="off" />
			</div>
			<div id="btn_section">
				<button type="button" id="list_back">돌아가기</button>
				<button type="button" id="update_commit">수정</button>
				<button type="button" id="update_cancel">취소</button>
				<input type="hidden" name="bno" value="${dto.bno}" />
				<input type="hidden" name="curPage" value="${curPage}" />
				<input type="hidden" name="search_option" value="${search_option}" />
				<input type="hidden" name="keyword" value="${keyword}" />
			</div>
		</form>
	</div>
</body>
</html>