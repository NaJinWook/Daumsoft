<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
<!-- ckeditor 사용을 위해 js 파일 연결 -->
<script src="/resources/ckeditor/ckeditor.js"></script>
<!-- 순서에 유의 -->
<script type="text/javascript" src="/resources/js/jsbn.js"></script>
<script type="text/javascript" src="/resources/js/rsa.js"></script>
<script type="text/javascript" src="/resources/js/prng4.js"></script>
<script type="text/javascript" src="/resources/js/rng.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/reset.css">
<link rel="stylesheet" type="text/css" href="/resources/css/style.css">
<link rel="stylesheet" type="text/css" href="/resources/css/style2.css">
<%
	pageContext.setAttribute("br", "<br/>");
	pageContext.setAttribute("cn", "\r\n");
%>