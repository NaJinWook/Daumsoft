<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
<style>
/*datepicer 버튼 롤오버 시 손가락 모양 표시*/
.ui-datepicker-trigger {
	cursor: pointer;
}
/*datepicer 버튼 아이콘 */
.ui-datepicker-trigger {
	width: 25px;
	height: 25px;
}
</style>
<script type="text/javascript" language="javascript">
	$(function() {
	    //모든 datepicker에 대한 공통 옵션 설정
	    $.datepicker.setDefaults({
	        dateFormat: 'yymmdd' //Input Display Format 변경
	        ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
	        ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
	        ,changeYear: true //콤보박스에서 년 선택 가능
	        ,changeMonth: true //콤보박스에서 월 선택 가능                
	        ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
	        ,buttonImage: "../../../resources/images/calendar.png" //버튼 이미지 경로
	        ,buttonImageOnly: true //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
	        ,buttonText: "날짜 선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트                
	        ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
	        ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
	        ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
	        ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
	        ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
	        ,minDate: "-1Y" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
	        ,maxDate: "today" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)                    
	    });
	  	//input을 datepicker로 선언
	    $("#startDate").datepicker();                    
	    $("#endDate").datepicker();
	    
	    //From의 초기값을 오늘 날짜로 설정
	    $('#startDate').datepicker('setDate', '20190101'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
	    //To의 초기값을 내일로 설정
	    $('#endDate').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, -1M:한달후, -1Y:일년후)
	    
	     $("#logo").click(function(){
	    	location.href = "main"
	    });
	});

	function Enter_Check() {
		// 엔터키의 코드는 13입니다.
		if (event.keyCode == 13) {
			search(); // 실행할 이벤트
		}
	}
	function search() {
		var keyword = $.trim($("#keyword").val());
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		if (keyword == "") {
			alert('검색어를 입력하지 않았습니다.');
			return;
		} else if (startDate >= endDate) {
			alert('기간을 다시 확인해주세요.');
			return;
		}
		analysis();
	}
	function analysis() {
		var source = $("#source").val();
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var keyword = $("#keyword").val();
		var period = $("#period").val();
		var param = {
			"source" : source,
			"startDate" : startDate,
			"endDate" : endDate,
			"keyword" : keyword,
			"period" : period
		};
		$.ajax({
			url : "view",
			data : param,
			statusCode : {
				200 : function(result) {
					$("#print").html(result);
				},
				500 : function(res) {
					alert("500 error");
				},
				404 : function() {
					alert("404 error");
				}
			},
			beforeSend : function() {
				$("#loading").show();
			},
			complete : function() {
				$("#loading").hide();
			}
		});
	}
</script>
</head>
<body>
<body>
	<form action="view" method="get">
		<div class="layout">
			<div class="search_section">
				<div class="logo">
					<img src="../../../resources/images/logo.png" id="logo" />
				</div>
				<div class="search">
					<div class="option">
						<label><span style="font-weight: bold; color: gray;">출력주기</span></label>
						<select id="period" name="period" class="select">
							<option value="0" selected>일별</option>
							<option value="1">주별</option>
							<option value="2">월별</option>
						</select> <label><span
							style="font-weight: bold; color: gray; padding-left: 20px;">문서범위</span></label>
						<select id="source" name="source" class="select">
							<option value="blog" selected>블로그</option>
							<option value="twitter">트위터</option>
							<option value="news">뉴스</option>
							<option value="facebook">페이스북</option>
							<option value="insta">인스타그램</option>
						</select> 
						<label>
							<span
								style="font-weight: bold; color: gray; padding-left: 20px;">기간
							</span>
						</label>
						<input type="text" id="startDate" name="startDate" disabled/> 
						<span style="font-weight: bold;">~</span>
						<input type="text" id="endDate" name="endDate" disabled/>
					</div>
					<div class="keyword">
						<input type="text" name="keyword" id="keyword" placeholder="검색어를 입력하세요." autocomplete="off" onkeydown="JavaScript:Enter_Check()"/> 
						<input type="button" value="검색" id="search_btn" onclick="search();"/>
					</div>
					<img src="../../../resources/images/loading.gif" id="loading" style="display: none;margin-left:180px;padding:20px;"/>
					<div id="print"></div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>