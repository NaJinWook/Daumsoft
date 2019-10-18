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
<style>
.hl {
	font-weight: bold;
	font-family: '돋움', dotum, helvetica, '나눔바른고딕 옛한글',
		'NanumBarunGothic YetHangul', '새굴림', sans-serif;
}
</style>
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
							<c:if test="${sort == '' || keyword == null}">
								<a href="#" class="menu_selected" title="sort">정렬기준
								<img class="spim" src="../../../resources/images/spim_btn.png" />
							</a>
							</c:if>
							<c:if test="${sort == 'desc'}">
								<a href="#" class="menu_selected" title="sort">최신순
								<img class="spim" src="../../../resources/images/spim_btn.png" />
							</a>
							</c:if>
							<c:if test="${sort == 'asc'}">
								<a href="#" class="menu_selected" title="sort">오래된순
								<img class="spim" src="../../../resources/images/spim_btn.png" />
							</a>
							</c:if>
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
						<c:if test="${oName == '' || keyword == null}">
							<a href="#" class="menu_selected" title="press">언론사
							<img class="spim" src="../../../resources/images/spim_btn.png" />
						</a>
						</c:if>
						<a href="#" class="menu_selected" title="press">${oName}
							<img class="spim" src="../../../resources/images/spim_btn.png" />
						</a>
							<div class="menu_list" id="menu_press">
								<ul>
									<li class="menu_btn"><a href="/search?keyword=${keyword}&sort=${sort}">전체</a></li>
									<li class="menu_btn"><a href="/search?keyword=${keyword}&sort=${sort}&oName=연합뉴스">연합뉴스</a></li>
									<li class="menu_btn"><a href="/search?keyword=${keyword}&sort=${sort}&oName=뉴시스">뉴시스</a></li>
									<li class="menu_btn"><a href="/search?keyword=${keyword}&sort=${sort}&oName=뉴스1">뉴스1</a></li>
									<li class="menu_btn"><a href="/search?keyword=${keyword}&sort=${sort}&oName=아시아경제">아시아경제</a></li>
									<li class="menu_btn"><a href="/search?keyword=${keyword}&sort=${sort}&oName=머니투데이">머니투데이</a></li>
									<li class="menu_btn"><a href="/search?keyword=${keyword}&sort=${sort}&oName=이데일리">이데일리</a></li>
									<li class="menu_btn"><a href="/search?keyword=${keyword}&sort=${sort}&oName=파이낸셜뉴스">파이낸셜뉴스</a></li>
								</ul>
							</div>
						</li>
					</ul>
					<div class="contents">
						<div class="section_head">
							<c:if test="${not empty pager && totalRecordsCount != 0}">
								<h2>뉴스</h2>
								<span class="title_num">${pager.startRecord+1}-${pager.startRecord+10} / ${totalRecordsCount}건</span>
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
										<dd>${map.get(data.id).get("content").get(0)}...</dd>
									<dd>
										<a href="#" alt="언론사">${data.oName}</a>
										<a href="${data.url}" alt="url" target="_blank">${data.url}</a>
									</dd>
								</dl>
							</div>
						</c:forEach>
						<c:if test="${not empty pager}">
							<div class="paging">
								<c:if test="${pager.currentGroup > 0}">
									<a class="group_btn" href="/search?keyword=${keyword}&sort=${sort}&oName=${oName}&currentPage=1">&lt&lt</a>
									<a class="group_btn" href="/search?keyword=${keyword}&sort=${sort}&oName=${oName}&currentPage=${pager.startPageGroup-1}">&lt</a>
								</c:if>
								<c:forEach var="num" begin="${pager.startPageGroup}" end="${pager.endPageGroup}">
									<c:choose>
										<c:when test="${num == currentPage}">
											<span class="cur_page_btn">${num}</span>
										</c:when>
										<c:otherwise>
											<a class="page_btn" href="/search?keyword=${keyword}&sort=${sort}&oName=${oName}&currentPage=${num}">${num}</a>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<c:if test="${pager.endPageGroup < pager.totalPageCount}">
									<a class="group_btn" href="/search?keyword=${keyword}&sort=${sort}&oName=${oName}&currentPage=${pager.endPageGroup+1}">&gt</a>
									<a class="group_btn" href="/search?keyword=${keyword}&sort=${sort}&oName=${oName}&currentPage=${pager.totalPageCount}">&gt&gt</a>	
								</c:if>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${not empty pager && totalRecordsCount != 0}">
			<a href="#" class="top" onclick="window.scrollTo(0,0); document.querySelector('#wrap a').focus(); return false;">맨위로</a>
		</c:if>
	</div>
	<c:if test="${totalRecordsCount != 0}">
		<div style="width:100%;height:100px"></div>
	</c:if>
</body>
</html>