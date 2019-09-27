<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="../include/comm.jsp"%>
<script>
$(function(){
	alert('${msg}');
	$("#logo").click(function(){
		location.href = "/main"
	});
});
</script>
<style>

</style>
</head>
<body>
	<div id="error_body" style="height: 60vh; width: 45%; margin: 0 auto; color: black;">
		<div style="height: 30%;">
		<img src="../../../resources/images/logo.png" id="logo" />
			<a href="/main"style="float: right; padding-top: 20px; padding-right: 50px; font-size: 13px; text-decoration: none; color:black;">
				홈으로
			</a>
		</div>
		<div style="height: 60%; width: 100%;">
			<p
				style="font-weight: bold; font-size: 20px; padding: 5px; padding-left: 100px;">
				죄송합니다.<br>요청하신 페이지를 찾을 수 없습니다.<br><br>
			</p>
			<p style="font-size: 16px; padding: 5px; padding-left: 100px;">
				방문하시려는 페이지의 주소가 잘못 입력되었거나,<br>
				<br>페이지의 주소가 변경 혹은 삭제되어 요청하신 페이지를 찾을 수 없습니다.<br>
				<br>입력하신 주소가 정확한지 다시 한번 확인 해 주시기 바랍니다.<br>
				<br>감사합니다.
			</p>
		</div>
		<div style="height: 10%; font-size: 9px; text-align: center;">
			Copyright © <span style="font-weight: bold;"><a href="/main" style="text-decoration: none; color:black;">Daumsoft</a></span> Corp. All Rights Reserved.
		</div>
	</div>
</body>
</html>