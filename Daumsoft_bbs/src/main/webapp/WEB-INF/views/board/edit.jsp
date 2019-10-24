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
			} else if(CKEDITOR.instances.contents.getData().length < 1){
				alert("내용을 입력해주세요.");
				return;
			} 
			document.updateForm.submit();
		});
		$("#delBtn_0").click(function(e){
			e.preventDefault();
			fn_delFile($(this));
			fn_fileAdd();
		});
		$("#delBtn_1").click(function(e){
			e.preventDefault();
			fn_delFile($(this));
			fn_fileAdd();
		});
		$("#delBtn_2").click(function(e){
			e.preventDefault();
			fn_delFile($(this));
			fn_fileAdd();
		});
		$("#update_cancel").click(function() {
			history.back();
		});
		$("#list_back").click(function() {
			location.href = "/board/list?curPage=${curPage}&search_option=${search_option}&keyword=${keyword}";
		});
		function fn_delFile(obj){
			obj.parent().remove();
		}
		function fn_fileAdd(){
			var str = "<input type='file' id='uploadFile' name='uploadFile' style='padding-left:5px;display:block;'/>";
			$("#file_add").append(str);
		}
	});
</script>
</head>
<body>
	<div id="info">${dto.bno}</div>
	<div id="bbs">
		<form name="updateForm" method="post" action="/board/update" enctype="multipart/form-data">
			<div id="title_section">
				<input type="text" id="title" name="title" maxlength="45"
					placeholder="제목을 입력하세요" value="${dto.title}" autocomplete="off" />
			</div>
			<div id="contents_section">
				<textarea id="contents" name="contents">${dto.contents}</textarea>
				<script>
				//id가 description인 태그에 ckeditor를 적용시킴
		    	 CKEDITOR.replace("contents", {
		    		 height:450
		         });
				</script>
			</div>
			<div id="writer_section">
				<c:forEach items="${f_dto}" var="file" varStatus="status">
				<div id="file_add"></div>
				<div>
					${file.fileName}
					<button type="button" id="delBtn_${status.index}">삭제</button>
					<input type="hidden" name="fileNo" value="${file.fileNo}" />
				</div>
				</c:forEach>
				<c:forEach var="i" begin="1" end="${loop}">
					<input type="file" id="uploadFile" name="uploadFile" style="padding-left:5px;display:block;" multiple/>
				</c:forEach>
				<input type="hidden" name="writer" value="${dto.writer}"/>
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