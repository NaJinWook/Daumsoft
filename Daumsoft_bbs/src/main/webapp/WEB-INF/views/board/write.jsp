<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
<script>
	var i = 0;
	var list = new Array();
	<c:forEach items="${whiteList}" var="item">
	   list.push("${item}");
	</c:forEach>
	$(function() {
 		//id가 description인 태그에 ckeditor를 적용시킴
		CKEDITOR.replace("contents", {
			height : 300,
			filebrowserImageUploadUrl : '/board/ckUpload'
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
		$("#write_cancel").click(function() {
			location.href = "/board/list?curPage=${curPage}&search_option=${search_option}&keyword=${keyword}&postNum=${postNum}";
		});
	});

	function fn_fileAdd() {
		var str = "<input type='file' id='uploadFile_"
				+ i
				+ "'name='uploadFile' style='padding-left:5px;display:block;' value='' onchange='checkExt(this);' multiple />";
		$(".fileDrop").append(str);
	}

	function checkExt(file) {
		var fileName = file.value;
		var fileID = '#' + file.id
		var files = $(fileID).prop("files");
		var names = $.map(files, function(val) {
			return val.name;
		});
		for (var j = 0; j < names.length; j++) {
			if (names[j].length > 50) {
				alert("파일명: " + names[j] + "\n파일명은 50자까지 가능합니다.");
				file.type = 'radio';
				file.type = 'file';
				return;
			}
			if (list.indexOf(names[j].substring(names[j].lastIndexOf('.') + 1).toLowerCase()) < 0) {
				alert("파일명: "
						+ names[j]
						+ "\n확장자가 올바르지 않습니다.\n엑셀(xls,xlsx) / 파워포인트(ppt.pptx,pdf) / 워드(txt,hwp,doc,docx,xml)파일만 첨부가능합니다.");
				file.type = 'radio';
				file.type = 'file';
				/*
				fileDoc.select();
				document.selection.clear();
				 */
				return;
			}
		}
		i++;
		fn_fileAdd();
	}
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
			<div class="fileDrop" id="writer_section">
				<input type="file" id="uploadFile_0" name="uploadFile" style="padding-left:5px;display:block;" multiple  onchange="checkExt(this);"/>
			</div>
			<div id="btn_section">
				<button type="button" id="write_commit">등록</button>
				<button type="button" id="write_cancel">취소</button>
			</div>
		</form>
	</div>
</body>
</html>