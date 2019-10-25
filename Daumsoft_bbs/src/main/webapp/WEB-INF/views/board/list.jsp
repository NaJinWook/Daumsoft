<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
<style>
#file_icon{
	width:14px;
	height:14px;
}
</style>
<script>
	$(function(){
		$("#allCheck").click(function(){
			var chk = $("#allCheck").prop("checked");
			if(chk) {
				$(".chBox").prop("checked", true);
			} else {
				$(".chBox").prop("checked", false);
			}
		});
		$(".chBox").click(function(){
			$("#allCheck").prop("checked", false);
		});
		$("#selectDelete_btn").click(function(){
			var confirm_val = confirm("게시글을 삭제하시겠습니까?");
			if(confirm_val) {
				var checkArr = new Array();
				$("input[class='chBox']:checked").each(function(){
					checkArr.push($(this).attr("data-bno"));
				});
				$.ajax({
					url : "/board/deleteList",
					type : "post",
					data : { chbox : checkArr },
					success : function(result){
						if(result == 1) {
							location.href="/board/list?curPage=${map.curPage}&search_option=${map.search_option}&keyword=${map.keyword}&sort=${map.sort}&postNum=${map.postNum}";
						} else {
							alert('에러');
						}
					}
				});
			}
		});
		$("#search_btn").click(function() {
			var keyword = $.trim($("#search_keyword").val());
			if (keyword == "") {
				alert("검색할 내용을 입력하세요.");
				$("#search_keyword").focus();
				return;
			}
			document.searchForm.submit();
		});
		$("#write_btn").click(function() {
			document.writeForm.submit();
		});
		$("#postView").change(function(){
			var postNum = $(this).val();
			location.href = "/board/list?curPage=1&search_option=${map.search_option}&keyword=${map.keyword}&postNum="+postNum;
		});
	});
	function list(page) {
		location.href = "${path}/board/list?curPage=" + page
				+ "&search_option=${map.search_option}"
				+ "&keyword=${map.keyword}&postNum=${map.postNum}";
	}
</script>
</head>
<body>
	<div id="info"><a href="/board/list">자유게시판</a></div>
	<div id="bbs">
		<span style="font-size: 13px; padding-left: 5px;">${map.count}개의 게시물이 있습니다.</span>
		<div style="padding-left: 5px;margin-top:10px;">
			<select id="postView" name="postView" onchange="postView()">
				<option value="10" <c:if test="${map.postNum == '10'}">selected</c:if>>10개씩 보기</option>
				<option value="20" <c:if test="${map.postNum == '20'}">selected</c:if>>20개씩 보기</option>
				<option value="30" <c:if test="${map.postNum == '30'}">selected</c:if>>30개씩 보기</option>
			</select> 
		</div>
		<button type="button" id="write_btn">글 작성</button>
		<form name="writeForm" action="/board/write">
			<input type="hidden" name="curPage" value="${map.curPage}"/>
			<input type="hidden" name="search_option" value="${map.search_option}"/>
			<input type="hidden" name="keyword" value="${map.keyword}"/>
		</form>
		<c:if test="${map.count != 0}">
		<table>
			<thead>
				<tr>
					<c:if test="${member.verify == 2}">
						<th class="bbs_title" scope="col"></th>
					</c:if>
					<th class="bbs_title" scope="col" width="10%">번호</th>
					<th class="bbs_title" scope="col" width="45%">제목</th>
					<th class="bbs_title" scope="col" width="15%">작성자</th>
					<th class="bbs_title" scope="col" width="20%">
						<c:if test="${map.sort == 'regDate' || map.sort == null}">
							<a style="text-decoration:none;color:black;"
							href="/board/list?curPage=${map.curPage}&search_option=${map.search_option}&keyword=${map.keyword}&sort=editDate&postNum=${map.postNum}">등록일
							<img src="../../../resources/img/circleArrow_icon.png" style="width:13px;height:13px;"/></a>
						</c:if>
						<c:if test="${map.sort == 'editDate'}">
							<a style="text-decoration:none;color:black;" 
							href="/board/list?curPage=${map.curPage}&search_option=${map.search_option}&keyword=${map.keyword}&sort=regDate&postNum=${map.postNum}">수정일
							<img src="../../../resources/img/circleArrow_icon.png" style="width:13px;height:13px;"/></a>
						</c:if>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${map.list}" var="list">
					<tr>
						<c:if test="${member.verify == 2}">
							<td class="bbs_contents" scope="col">
								<input type="checkbox" name="chBox" class="chBox" data-bno="${list.bno}"/>
							</td>
						</c:if>

						<td class="bbs_contents" scope="col" width="10%">${list.bno}</td>
						<td class="bbs_contents" scope="col" width="45%"><a
							href="/board/read?bno=${list.bno}&curPage=${map.curPage}&search_option=${map.search_option}&keyword=${map.keyword}&postNum=${map.postNum}">${list.title}</a>
							<c:if test="${list.fileCount != 0}">
								<span style="font-size:13px;color:rgb(255,78,57);font-weight:bold;">
									<img src="../../resources/img/file_icon.PNG" id="file_icon"/>
									+${list.fileCount}
								</span>
							</c:if>
						</td>
						<c:choose>
							<c:when test="${list.writer eq '운영자'}">
								<td class="bbs_contents" scope="col" width="15%" style="color:rgb(255,78,57);font-weight:bold;">
								<img src="../../../resources/img/admin.png" style="width:15px;height:15px;"/>${list.writer}</td>
							</c:when>
							<c:otherwise>
								<td class="bbs_contents" scope="col" width="15%">${list.writer}</td>
							</c:otherwise>
						</c:choose>
						<td class="bbs_contents" scope="col" width="20%">
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${list.regDate}" />
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</c:if>
		<c:if test="${member.verify == 2}">
			<div style="margin-left:30px;padding-top:10px;color:#666;font-size:14px;">
				<input type="checkbox" name="allCheck" id="allCheck"/> <label for="allCheck">전체선택</label>
				<button type="button" style="float:right;" id="selectDelete_btn">삭제</button>
			</div>
		</c:if>
	</div>
	<div class="paging">
		<c:if test="${map.pager.curBlock > 1}">
			<button type="button" class="start_end_btn">
				<a href="javascript:list('1')">START</a>
			</button>
		</c:if>
		<c:if test="${map.pager.curBlock > 1}">
			<button type="button" class="prev_next_btn">
				<a href="javascript:list('${map.pager.prevPage}')">PREV</a>
			</button>
		</c:if>
		<c:forEach var="num" begin="${map.pager.blockBegin}"
			end="${map.pager.blockEnd}">
			<c:choose>
				<c:when test="${num == map.pager.curPage}">
					<button type="button" class="pg_btn"
						style="background-color: #444;">
						<span style="color: #fff;">${num}</span>
					</button>
				</c:when>
				<c:otherwise>
					<button type="button" class="pg_btn">
						<a href="javascript:list('${num}')">${num}</a>
					</button>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${map.pager.curBlock <= map.pager.totBlock}">
			<button type="button" class="prev_next_btn">
				<a href="javascript:list('${map.pager.nextPage}')">NEXT</a>
			</button>
		</c:if>
		<c:if test="${map.pager.curPage <= map.pager.totPage}">
			<button type="button" class="start_end_btn">
				<a href="javascript:list('${map.pager.totPage}')">END</a>
			</button>
		</c:if>
	</div>
	<form name="searchForm" action="/board/list">
		<div class="search_section">
			<select id="sbox" name="search_option">
				<option value="all"
					<c:if test="${map.search_option == 'all'}">selected</c:if>>제목+내용</option>
				<option value="title"
					<c:if test="${map.search_option == 'title'}">selected</c:if>>제목</option>
				<option value="contents"
					<c:if test="${map.search_option == 'contents'}">selected</c:if>>내용</option>
				<option value="writer"
					<c:if test="${map.search_option == 'writer'}">selected</c:if>>작성자</option>
			</select>
			<input type="text" id="search_keyword" name="keyword" value="${map.keyword}" placeholder="검색어를 입력하세요." autocomplete="off" />
			<input type="button" id="search_btn" value="검색" />
		</div>
	</form>
</body>
</html>