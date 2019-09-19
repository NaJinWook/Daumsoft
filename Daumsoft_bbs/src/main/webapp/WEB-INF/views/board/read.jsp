<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp" %>
<script>
	$(function() {
		$("#list_btn").click(function(){
			location.href="/board/list?curPage=${map.curPage}&search_option=${map.search_option}&keyword=${map.keyword}";
		});
		$("#edit_btn").click(function(){
			document.editForm.submit();
		});
		$("#remove_btn").click(function(){
			if(confirm("삭제하시겠습니까?")){
				document.removeForm.submit();
			}
		});
		
	});
</script>
</head>
<body>
<div id="info">${map.dto.bno}</div>
<div id="bbs">
   <div id="curTitle">
     <span style="padding:10px;line-height:30px;font-weight:bold;">제목</span>
     <span>${map.dto.title}</span>
   </div>
   <div id="curName">
     <span style="padding:10px;line-height:30px;font-weight:bold;">이름</span>
     <span>${map.dto.writer}</span>
   </div>
   <div id="curInfo">
     <div id="info_date">
       <span style="line-height:30px;font-weight:bold;">등록일</span>
       <span style="padding-right:20px;">
       	<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${map.dto.regDate}"/> 
       </span>
       <span style="line-height:30px;font-weight:bold;">수정일</span>
       <span style="padding-right:5px;">
       	<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${map.dto.editDate}"/> 
       </span>
     </div>
   </div>
   <div id="curOption">
     <div id="option_btn">
       <input type="button" id="list_btn" value="목록" style="cursor: pointer;"/>
       <input type="button" id="edit_btn" value="수정" style="cursor: pointer;"/>
       <input type="button" id="remove_btn" value="삭제" style="cursor: pointer;"/>
     </div>
   </div>
    <form name="editForm" action="${path}/board/edit">
		<input type="hidden" name="bno" value="${map.dto.bno}"/>
	</form>
	<form name="removeForm" action="${path}/board/delete">
		<input type="hidden" name="bno" value="${map.dto.bno}"/>
	</form>
   <div id="curContents">${map.dto.contents}</div>
   <div id="file_upload"></div>
</div>
</body>
</html>