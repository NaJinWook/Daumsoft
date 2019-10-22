<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
<script>
$(function() {
	$("#idCheck").click(function() {
		var userId = $.trim($("#userId").val());
		var query = {
			userId : userId
		};
		$.ajax({
			type : "post",
			url : "/member/idCheck",
			data : query,
			success : function(result) {
				if (result == 1) {
					alert("이미 존재하는 아이디 입니다.");
				} else {
					alert("사용 가능한 아이디 입니다.");
				}
			}
		});
	});
	$("#niknameCheck").click(function() {
		var userNikname = $.trim($("#userNikname").val());
		var query = {
				userNikname : userNikname
		};
		$.ajax({
			type : "post",
			url : "/member/niknameCheck",
			data : query,
			success : function(result) {
				if (result == 1) {
					alert("이미 존재하는 닉네임 입니다.");
				} else {
					alert("사용 가능한 닉네임 입니다.");
				}
			}
		});
	});
	$("#reg_Btn").click(function() {
		var userId = $.trim($("#userId").val());
		var userName = $.trim($("#userName").val());
		var userNikname = $.trim($("#userNikname").val());
		var userPwd = $.trim($("#userPwd").val());
		var userPwd2 = $.trim($("#userPwd2").val());
		
		if (userId == "") {
			alert("아이디를 입력하세요.");
			$("#userId").focus();
			return;
		} else if (userName == "") {
			alert("이름을 입력하세요.");
			$("#userName").focus();
			return;
		} else if (userNikname == "") {
			alert("닉네임을 입력하세요.");
			$("#userNikname").focus();
			return;
		} else if (userPwd == "") {
			alert("비밀번호를 입력하세요.");
			$("#userPwd").focus();
			return;
		} else if (userPwd2 == "") {
			alert("비밀번호를 입력하세요.");
			$("#userPwd2").focus();
			return;
		} else if (userPwd != userPwd2) {
			alert("회원 정보를 올바르게 입력해주세요.");
			return;
		}
		var shaPw = CryptoJS.SHA256($('#userPwd').val()).toString(); 
		$("#userPwd").val(shaPw);
		document.registerForm.submit();
	});
});
</script>
</head>
<body>
<c:if test="${param.message == 'nologin'}">
	<script>
		alert("로그인 후 이용 가능합니다.");
	</script>
</c:if>
<form name="registerForm" action="/member/rgCommit" method="post" autocomplete="off">
    <div class="join_layout">
      <div class="join_section" id="join_header">
        <span  id="join_title">회원가입</span>
      </div>
      <div class="join_section">
        <div class="join_info" id="join_Id">
          <label for="userId">아이디</label>
        </div>
        <div class="join_info">
          <input type="text" class="info_txt" id="userId" name="userId" maxlength="20">
          <button type="button" class="overlapCheck_Btn" id="idCheck">중복검사</button>
        </div>
      </div>
      <div class="join_section">
        <div class="join_info" id="join_Name">
          <label for="userName">이름</label>
        </div>
        <div class="join_info">
          <input type="text" class="info_txt" id="userName" name="userName" maxlength="40">
        </div>
      </div>
      <div class="join_section">
        <div class="join_info" id="join_Nikname">
          <label for="userNikname">닉네임</label>
        </div>
        <div class="join_info">
          <input type="text" class="info_txt" id="userNikname" name="userNikname" maxlength="20">
          <button type="button" class="overlapCheck_Btn" id="niknameCheck">중복검사</button>
        </div>
      </div>
      <div class="join_section">
        <div class="join_info" id="join_Pwd">
          <label for="userPwd">비밀번호</label>
        </div>
        <div class="join_info">
          <input type="password" class="info_txt" id="userPwd" name="userPwd" maxlength="20">
        </div>
      </div>
      <div class="join_section">
        <div class="join_info" id="join_Pwd2">
          <label for="userPwd2">비밀번호 재확인</label>
        </div>
        <div class="join_info">
          <input type="password" class="info_txt" id="userPwd2" name="userPwd2" maxlength="20">
        </div>
      </div>
      <div class="join_section">
        <button type="button" id="reg_Btn">가입하기</button>
      </div>
    </div>
</form>
</body>
</html>