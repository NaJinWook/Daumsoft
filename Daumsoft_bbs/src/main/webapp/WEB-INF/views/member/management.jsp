<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
</head>
<body>
<form action="/member/selectMember" method="GET">
	<input type="text" name="selectData" id="selectMember" />
	<input type="submit" value="검색" />
</form>
</body>
</html>