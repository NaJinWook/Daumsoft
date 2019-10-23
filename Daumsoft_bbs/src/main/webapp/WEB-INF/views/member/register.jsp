<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
<script>
$(function() {
	$("#alert-success").hide();
    $("#alert-danger").hide();
    $("#userName").keyup(function(){
    	var userName = $.trim($("#userName").val());
    	if(userName.length <= 1){
    		$("#name_success").css("display","none");
			$("#name_fail").css("display","inline");
    	} else {
			$("#name_fail").css("display","none");
    		$("#name_success").css("display","inline");
    	}
    });
	$("#userPwd, #userPwd2").keyup(function(){
		var userPwd = $.trim($("#userPwd").val());
		var userPwd2 = $.trim($("#userPwd2").val());
		if(userPwd != "" || userPwd2 != ""){
			if(userPwd == userPwd2){
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
					$("#id_success").css("display","none");
					$("#id_fail").css("display","inline");
					$("#userId").val("");
					$("#userId").focus();
				} else {
					alert("사용 가능한 아이디입니다.");
					$("#id_fail").css("display","none");
					$("#id_success").css("display","inline");
					$("#userId").attr("disabled", "disabled");
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
					$("#nikname_success").css("display","none");
					$("#nikname_fail").css("display","inline");
					$("#userNikname").val("");
					$("#userNikname").focus();
				} else {
					alert("사용 가능한 닉네임입니다.");
					$("#nikname_fail").css("display","none");
					$("#nikname_success").css("display","inline");
					$("#userNikname").attr("disabled", "disabled");
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
		
		if($("#id_success").css("display") == "none"){
			alert("아이디 중복 확인을 해주세요.");
			$("#userId").focus();
			return;
		} else if($("#name_success").css("display") == "none"){
			alert("이름을 올바르게 입력해주세요.");
			$("#userName").focus();
			return;
		} else if($("#nikname_success").css("display") == "none"){
			alert("닉네임 중복 확인을 해주세요.");
			$("#userNikname").focus();
			return;
		}
		
		if (userPwd == "") {
			alert("비밀번호를 입력하세요.");
			$("#userPwd").focus();
			return;
		} else if (userPwd2 == "") {
			alert("비밀번호를 입력하세요.");
			$("#userPwd2").focus();
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
          <span id="id_success" style="display:none;color:blue;font-weight:bold;">O</span>
          <span id="id_fail" style="display:none;color:red;font-weight:bold;">X</span>
        </div>
        <div class="join_info">
          <input type="text" class="info_txt" id="userId" name="userId" maxlength="20" autocomplete="off" placeholder="영어 및 숫자만 최대 12자까지">
          <button type="button" class="overlapCheck_Btn" id="idCheck">중복검사</button>
        </div>
      </div>
      <div class="join_section">
        <div class="join_info" id="join_Name">
          <label for="userName">이름</label>
          <span id="name_success" style="display:none;color:blue;font-weight:bold;">O</span>
          <span id="name_fail" style="display:none;color:red;font-weight:bold;">X</span>
        </div>
        <div class="join_info">
          <input type="text" class="info_txt" id="userName" name="userName" maxlength="40">
        </div>
      </div>
      <div class="join_section">
        <div class="join_info" id="join_Nikname">
          <label for="userNikname">닉네임</label>
          <span id="nikname_success" style="display:none;color:blue;font-weight:bold;">O</span>
          <span id="nikname_fail" style="display:none;color:red;font-weight:bold;">X</span>
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