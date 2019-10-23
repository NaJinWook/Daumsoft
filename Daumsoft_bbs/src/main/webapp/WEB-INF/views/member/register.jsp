<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
<style>
input:focus {
	outline: none;
}
</style>
<script>
	$(function() {
		$("#alert-success").hide();
		$("#alert-danger").hide();
		$("#userName").keyup(function(){
			var userName = $.trim($("#userName").val());
			if(userName.length <= 1){
				$("#userName").css("border", "2px solid red");
			} else {
				$("#userName").css("border", "2px solid green");
			}
		});
		$("#userPwd, #userPwd2").keyup(function() {
			var userPwd = $.trim($("#userPwd").val());
			var userPwd2 = $.trim($("#userPwd2").val());
			if (userPwd != "" || userPwd2 != "") {
				if (userPwd == userPwd2) {
					$("#alert-success").show();
					$("#alert-danger").hide();
					$("#reg_Btn").removeAttr("disabled");
				} else {
					$("#alert-success").hide();
					$("#alert-danger").show();
					$("#reg_Btn").attr("disabled", "disabled");
				}
			}
		});
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
						alert("이미 존재하는 아이디입니다.");
						$("#userId").css("border", "2px solid red");
						$("#userId").val("");
						$("#userId").focus();
					} else {
						alert("사용 가능한 아이디입니다.");
						$("#userId").css("border", "2px solid green");
						$("#userId").attr("readonly", true);
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
						alert("이미 존재하는 닉네임입니다.");
						$("#userNikname").css("border", "2px solid red");
						$("#userNikname").val("");
						$("#userNikname").focus();
					} else {
						alert("사용 가능한 닉네임입니다.");
						$("#userNikname").css("border", "2px solid green");
						$("#userNikname").attr("readonly", true);
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
			} else if (userName == "" || userName.length <= 1) {
				alert("2자 이상의 이름을 입력하세요.");
				$("#userName").focus();
				return;
			} else if (userNikname == "") {
				alert("닉네임을 입력하세요.");
				$("#userNikname").focus();
				return;
			} else if (userPwd == "" || userPwd.length < 4) {
				alert("4자 이상의 비밀번호를 입력하세요.");
				$("#userPwd").focus();
				return;
			} else if (userPwd2 == "") {
				alert("비밀번호를 입력하세요.");
				$("#userPwd2").focus();
				return;
			}
			
			if(!$("#userId").attr("readonly")){
				alert("아이디 중복검사를 해주세요.");
				return;
			} else if(!$("#userNikname").attr("readonly")) {
				alert("닉네임 중복검사를 해주세요.");
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
<form name="registerForm" action="/member/rgCommit" method="post">
    <div class="join_layout">
      <div class="join_section" id="join_header">
        <span  id="join_title">회원가입</span>
      </div>
      <div class="join_section">
        <div class="join_info" id="join_Id">
          <label for="userId">아이디</label>
        </div>
        <div class="join_info">
          <input type="text" class="info_txt" id="userId" name="userId" maxlength="20" autocomplete="off" placeholder="영어 및 숫자만 최대 12자까지">
          <button type="button" class="overlapCheck_Btn" id="idCheck">중복검사</button>
        </div>
      </div>
      <div class="join_section">
        <div class="join_info" id="join_Name">
          <label for="userName">이름</label>
        </div>
        <div class="join_info">
          <input type="text" class="info_txt" id="userName" name="userName" maxlength="20">
        </div>
      </div>
      <div class="join_section">
        <div class="join_info" id="join_Nikname">
          <label for="userNikname">닉네임</label>
        </div>
        <div class="join_info">
          <input type="text" class="info_txt" id="userNikname" name="userNikname" maxlength="20" autocomplete="off" placeholder="게시물 작성시 표시되는 필명">
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
          <div style="font-size:13px;color:blue;" id="alert-success">비밀번호가 일치합니다.</div>
		  <div style="font-size:13px;color:red;" id="alert-danger">비밀번호가 일치하지 않습니다.</div>
        </div>
      </div>
      <div class="join_section">
        <button type="button" id="reg_Btn">가입하기</button>
      </div>
    </div>
</form>
</body>
</html>