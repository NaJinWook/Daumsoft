<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
<script>
	$(function() {
 		//id가 description인 태그에 ckeditor를 적용시킴
		CKEDITOR.replace("contents", {
			height : 450,
			filebrowserImageUploadUrl : '/community/imageUpload'
		});
		CKEDITOR.on('dialogDefinition', function(ev) {
			var dialogName = ev.data.name;
			var dialogDefinition = ev.data.definition;

			switch (dialogName) {
			case 'image': //Image Properties dialog
				//dialogDefinition.removeContents('info');
				dialogDefinition.removeContents('Link');
				dialogDefinition.removeContents('advanced');
				break;
			}
		});
		$("#write_commit").click(function() {
			var title = $.trim($("#title").val());
			var contents = $.trim($("#contents").val());
			if (title == "") {
				alert("제목을 입력해주세요.");
				$("#title").focus();
				return;
			} else if (CKEDITOR.instances.contents.getData().length < 1) {
				alert("내용을 입력해주세요.");
				return;
			}
			document.sendForm.submit();
		});
		$("#write_cancel")
				.click(
						function() {
							location.href = "/board/list?curPage=${curPage}&search_option=${search_option}&keyword=${keyword}&postNum=${postNum}";
						});
	});
</script>
</head>
<body>
	<div id="info">게시글 쓰기</div>
	<div id="bbs">
		<form name="sendForm" method="post" action="/board/insert" enctype="multipart/form-data">
			<div id="title_section">
				<input type="text" id="title" name="title" maxlength="45"
					placeholder="제목을 입력하세요" autocomplete="off" />
					<input style="visibility:hidden; width:0px" />
			</div>
			<div id="contents_section">
				<textarea id="contents" name="contents"></textarea>
				<input type="hidden" name="writer" value="${member.userNikname}" />
			</div>
			<div id="writer_section">
				<input type="file" name="uploadFile" style="padding-left:5px;display:block;" multiple/>
				<input type="file" name="uploadFile" style="padding-left:5px;display:block;" multiple/>
				<input type="file" name="uploadFile" style="padding-left:5px;display:block;" multiple/>
			</div>
			<div id="btn_section">
				<button type="button" id="write_commit">등록</button>
				<button type="button" id="write_cancel">취소</button>
			</div>
		</form>
	</div>
</body>
</html>