<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
<script>
	$(function() {
		$("#write_commit").click(function() {
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
			document.sendForm.submit();
		});
		$("#write_cancel").click(function() {
			location.href = "/board/list?curPage=${curPage}&search_option=${search_option}&keyword=${keyword}";
		});
	});
</script>
</head>
<body>
	<div id="info">게시글 쓰기</div>
	<div id="bbs">
		<form name="sendForm" method="post" action="/board/insert">
			<div id="title_section">
				<input type="text" id="title" name="title" maxlength="45"
					placeholder="제목을 입력하세요" autocomplete="off" />
			</div>
			<div id="contents_section">
				<textarea id="contents" name="contents"></textarea>
			</div>
			<div id="writer_section">
				<input type="text" id="writer" name="writer"
					placeholder="이름을 입력하세요" maxlength="7" autocomplete="off" />
			</div>
			<div id="btn_section">
				<button type="button" id="write_commit">등록</button>
				<button type="button" id="write_cancel">취소</button>
			</div>
		</form>
	</div>
</body>
</html>