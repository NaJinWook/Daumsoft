<%@ page language="java" contentType="text/html; charset=UTF-8" 
 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>다음소프트</title>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<c:set var="path" value="${pageContext.request.contextPath}" />
	<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
	<style>
	#nav{
	  background-color: rgb(23, 32, 40);
	  width: 100%;
	  height: 50px;
	}
	</style>
  </head>
  <body>
    <div id="top">
    	<a href="/board/">
    		<img id="logo" src="../../../resources/img/logo.png" alt="다음소프트" />
    	</a>
    </div>
    <div id="nav"></div>
  </body>
</html>