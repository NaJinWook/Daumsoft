<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
<script>
	$(function(){
		$("#search_btn").click(function() {
			var keyword = $.trim($("#search_keyword").val());
			if (keyword == "") {
				alert("검색할 내용을 입력하세요.");
				$("#search_keyword").focus();
				return;
			}
			document.searchForm.submit();
		});
	});
	function list(page) {
		location.href = "${path}/board/list?curPage=" + page
				+ "&search_option=${map.search_option}"
				+ "&keyword=${map.keyword}";
	}
</script>
</head>
<body>
	<div id="info"><a href="/board/list">자유게시판</a></div>
	<div id="bbs">
		<span style="font-size: 13px; padding-left: 5px;">${map.count}개의
			게시물이 있습니다.</span>
		<button type="button" id="write_btn">
			<a href="/board/write">글 작성</a>
		</button>
		<c:if test="${map.count != 0}">
		<table>
			<thead>
				<tr>
					<th class="bbs_title" scope="col" width="10%">번호</th>
					<th class="bbs_title" scope="col" width="45%">제목</th>
					<th class="bbs_title" scope="col" width="20%">작성자</th>
					<th class="bbs_title" scope="col" width="15%">등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${map.list}" var="list">
					<tr>
						<td class="bbs_contents" scope="col" width="10%">${list.bno}</td>
						<td class="bbs_contents" scope="col" width="45%"><a
							href="/board/read?bno=${list.bno}&curPage=${map.curPage}&search_option=${map.search_option}&keyword=${map.keyword}">${list.title}</a>
						</td>
						<td class="bbs_contents" scope="col" width="20%">${list.writer}</td>
						<td class="bbs_contents" scope="col" width="15%"><fmt:formatDate
								pattern="yyyy-MM-dd HH:mm:ss" value="${list.regDate}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
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
	<form name="searchForm" action="${path}/board/list">
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