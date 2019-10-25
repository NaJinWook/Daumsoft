<%@ page language="java" contentType="text/html; charset=UTF-8" 
 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
<style>
#nav{
  background-color: rgb(23, 32, 40);
  width: 100%;
  height: 50px;
}
</style>
<script>
	$(function() {
		$("#home_login").click(function() {
			$("#modal").fadeIn();
		});
		$("#modal_close_btn").click(function() {
			$("#modal").fadeOut();
		});
		$("#register_btn").click(function() {
			location.href = "/member/register";
		});
		$("#login_btn").click(function() {
			var login_id = $.trim($("#login_id").val());
			var login_pwd = $.trim($("#login_pwd").val());
			if(login_id == ""){
				alert("아이디를 입력해주세요.");
				$("#login_id").focus();
				return;
			} else if(login_pwd == ""){
				alert("비밀번호를 입력해주세요.");
				$("#login_pwd").focus();
				return;
			}
			var rsa = new RSAKey();
	        rsa.setPublic($('#RSAModulus').val(),$('#RSAExponent').val());
	        $("#LOGIN_USER_PW").val(rsa.encrypt(login_pwd));
	        $("#login_pwd").val("");
			
			document.loginForm.submit();
		});
		$("#home_logout").click(function() {
			location.href = "/member/logout";
		});
	});
	function login(){
		$("#login_btn").click();
	}
</script>
 </head>
 <body>
   <div id="top">
   	<a href="/board/">
   		<img id="logo" src="../../../resources/img/logo.png" alt="다음소프트" />
   	</a>
   	<c:if test="${member == null}">
    	<a style="float:right;padding-right:10px;padding-top:10px;text-decoration: none; color:#fff; font-weight:bold;" id="home_login" href="#">로그인</a>
   	</c:if>
   	<c:if test="${member != null}">
   		<span style="float:right;padding-right:10px;padding-top:10px;text-decoration: none; color:#fff; font-weight:bold;">${member.userNikname} |
    		<a style="text-decoration: none; color:#fff; font-weight:bold;" id="home_logout" href="#">로그아웃</a>
    	</span>
   	</c:if>
   </div>
   
   <!-- 아래는 로그인 모달창 -->
   <div id="nav"></div>
       <div id="modal">
     <div class="modal_content">
       <div style="text-align:right;">
         <button type="button" id="modal_close_btn">X</button>
       </div>
       <form name="loginForm" action="/member/loginCheck" method="post">
       	<input type="hidden" id="RSAModulus" value="${RSAModulus}"/>
    	<input type="hidden" id="RSAExponent" value="${RSAExponent}"/>  
        <div class="login_info">
          <div class="login_section">
            <p class="login_label"><label for="login_id" id="label_id">아이디</label></p>
            <input class="login_input" id="login_id" type="text" name="login_id" autocomplete="off">
          </div>
          <div class="login_section">
            <p class="login_label"><label for="login_pwd" id="label_pwd">비밀번호</label></p>
            <input style="letter-spacing: 5px;" id="login_pwd" type="password" name="login_pwd" onkeydown="if(event.keyCode == 13) { login();}">
            <input type="hidden" id="LOGIN_USER_PW" name="LOGIN_USER_PW">
          </div>
          <div class="login_section">
            <button class="groupBtn" id="login_btn" type="button">로그인</button>
            <button class="groupBtn" id="register_btn" type="button">회원가입</button>
          </div>
        </div>
       </form>
     </div>
     <div class="modal_layer"></div>
   </div>
 </body>
</html>