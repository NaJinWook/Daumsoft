<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
<script type="text/javascript" language="javascript">
	$(function() {
		$("#logo").click(function() {
			location.href = "main";
		});
		$(".menu > a[title='sort']").click(function() {
			$(".menu_list").css("display", "none");
			$("#menu_sort").css("display", "block");
		});
		$(".menu > a[title='date']").click(function() {
			$(".menu_list").css("display", "none");
			$("#menu_date").css("display", "block");
		});
		$(".menu > a[title='press']").click(function() {
			$(".menu_list").css("display", "none");
			$("#menu_press").css("display", "block");
		});
		$(".contents").click(function() {
			$(".menu_list").css("display", "none");
		});
		$("#search_btn").click(function(){
			document.searchForm.submit();
		});
	});
</script>
</head>
<body>
<body>
	<div style="width:100%;height:10px;border-top:2px solid rgb(59, 157, 255);"></div>
	<div class="layout">
		<div class="search_section">
			<div class="logo">
				<img src="../../../resources/images/logo.png" id="logo" />
			</div>
			<form name="searchForm" method="get" action="/search">
			<div class="keyword">
					<input type="text" value="${keyword}" name="keyword" id="keyword" placeholder="검색어를 입력하세요." autocomplete="off" onkeydown="if(event.keyCode == 13) { search();}" />
					<input type="button" id="search_btn"/>
			</div>
			</form>
			<div id="print">
				<div class="nav">
					<ul>
						<li class="menu">
							<a href="#" class="menu_selected" title="sort">정렬
								<img class="spim" src="../../../resources/images/spim_btn.png" />
							</a>
							<div class="menu_list" id="menu_sort">
								<ul>
									<li class="menu_btn"><a href="/search?keyword=${keyword}&sort=desc&oName=${oName}">최신순</a></li>
									<li class="menu_btn"><a href="/search?keyword=${keyword}&sort=asc&oName=${oName}">오래된순</a></li>
								</ul>
							</div>
						</li>
						<li class="menu">
						<a href="#" class="menu_selected" title="date">기간 
							<img class="spim" src="../../../resources/images/spim_btn.png" />
						</a>
							<div class="menu_list" id="menu_date">
								<ul>
									<li class="menu_btn"><a href="#">전체</a></li>
									<li class="menu_btn"><a href="#">1일</a></li>
									<li class="menu_btn"><a href="#">1주</a></li>
								</ul>
							</div>
						</li>
						<li class="menu">
						<a href="#" class="menu_selected" title="press">언론사
							<img class="spim" src="../../../resources/images/spim_btn.png" />
						</a>
							<div class="menu_list" id="menu_press">
								<ul>
									<li class="menu_btn"><a href="/search?keyword=${keyword}&sort=${sort}&oName=중앙일보">중앙일보</a></li>
									<li class="menu_btn"><a href="/search?keyword=${keyword}&sort=${sort}&oName=연합뉴스">연합뉴스</a></li>
								</ul>
							</div>
						</li>
					</ul>
					<div class="contents">
						<div class="section_head">
							<h2>뉴스</h2>
							<c:if test="${totalRecordsCount != 0}">
								<span class="title_num">${(rows*(curPage-1))+1}-${rows*curPage} / ${totalRecordsCount}건</span>
							</c:if>
						</div>
						<c:if test="${empty dataList}">
							<div class="result">
								<p style="font-weight: 700; font-size: 14px;">
									<span style="color: #eb0c00";>'${keyword}'</span>에 대한 검색결과가 없습니다.
								</p>
								<ul style="margin-top: 15px;">
									<li>단어의 철자가 정확한지 확인해 보세요.</li>
									<li>한글을 영어로 혹은 영어를 한글로 입력했는지 확인해 보세요.</li>
									<li>검색어의 단어 수를 줄이거나, 보다 일반적인 검색어로 다시 검색해 보세요.</li>
									<li>두 단어 이상의 검색어인 경우, 띄어쓰기를 확인해 보세요.</li>
									<li>검색 옵션을 변경해서 다시 검색해 보세요.</li>
								</ul>
							</div>
						</c:if>
						<c:forEach items="${dataList}" var="data" varStatus="status">
							<div class="result">
								<dl>
									<dt>
										<a href="${data.url}" target="_blank">${data.title}</a>
										<span>${data.regDate}</span>
									</dt>
									<dd>${data.content}</dd>
									<dd>
										<a href="#" alt="언론사">${data.oName}</a>
										<a href="${data.url}" alt="url" target="_blank">${data.url}</a>
									</dd>
								</dl>
							</div>
						</c:forEach>
						<div class="paging">
						<c:forEach var="i" begin="${pager.startPageGroup}" end="${pager.endPageGroup}">
							<a href="/search?keyword=${keyword}&sort=${sort}&oName=${oName}&currentPage=${i}">${i}</a>
						</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>