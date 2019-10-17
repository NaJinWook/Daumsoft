<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
<script>
$(function(){
	$("#main_logo").click(function(){
		location.href="/main";
	});
	$("#main_search_btn").click(function(){
		document.mainForm.submit();
	});
});
</script>
</head>
<body>
	<div class="main_layout">
		<div class="main_section">
			<div class="main_logo">
				<img src="../../../resources/images/logo2.png" id="main_logo" />
			</div>
			<form name="mainForm" method="get" action="/search">
			<div class="main_keyword">
				<input type="text" name="keyword" id="main_keyword" placeholder="검색어를 입력하세요." autocomplete="off" onkeydown="if(event.keyCode == 13) { search();}" autofocus="autofocus"/>
				<input type="button" id="main_search_btn" />
			</div>
			</form>
		</div>
		<div class="line"></div>
	</div>
</body>
</html>