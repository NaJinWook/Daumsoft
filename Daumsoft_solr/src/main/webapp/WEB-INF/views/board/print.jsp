<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp"%>
<script type="text/javascript" language="javascript">
	$(function() {
		if(${sort == ''}) {
			$("#relData").css("font-weight","bold");
			$("#relData").css("color","rgb(59, 157, 255)");
		} else if(${sort == 'desc'}) {
			$("#newData").css("font-weight","bold");
			$("#newData").css("color","rgb(59, 157, 255)");
		} else if(${sort == 'asc'}) {
			$("#oldData").css("font-weight","bold");
			$("#oldData").css("color","rgb(59, 157, 255)");
		}
		
		if(${regDate == 'all'}) {
			$("#date").css("font-weight","bold");
			$("#date").css("color","rgb(59, 157, 255)");
		} else if(${regDate == '1D'}) {
			$("#day").css("font-weight","bold");
			$("#day").css("color","rgb(59, 157, 255)");
		} else if(${regDate == '7D'}) {
			$("#week").css("font-weight","bold");
			$("#week").css("color","rgb(59, 157, 255)");
		} else if(${regDate == '1M'}) {
			$("#month").css("font-weight","bold");
			$("#month").css("color","rgb(59, 157, 255)");
		}
		
		if(${oName == ''}) {
			$("#0").css("font-weight","bold");
			$("#0").css("color","rgb(59, 157, 255)");
		} else if(${oName == '연합뉴스'}) {
			$("#1").css("font-weight","bold");
			$("#1").css("color","rgb(59, 157, 255)");
		} else if(${oName == '뉴시스'}) {
			$("#2").css("font-weight","bold");
			$("#2").css("color","rgb(59, 157, 255)");
		} else if(${oName == '뉴스1'}) {
			$("#3").css("font-weight","bold");
			$("#3").css("color","rgb(59, 157, 255)");
		} else if(${oName == '아시아경제'}) {
			$("#4").css("font-weight","bold");
			$("#4").css("color","rgb(59, 157, 255)");
		} else if(${oName == '머니투데이'}) {
			$("#5").css("font-weight","bold");
			$("#5").css("color","rgb(59, 157, 255)");
		} else if(${oName == '이데일리'}) {
			$("#6").css("font-weight","bold");
			$("#6").css("color","rgb(59, 157, 255)");
		} else if(${oName == '파이낸셜뉴스'}) {
			$("#7").css("font-weight","bold");
			$("#7").css("color","rgb(59, 157, 255)");
		}
		
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

a.on {
	color: red;
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
									<li class="menu_btn"><a id="relData" href="/search?keyword=${keyword}&sort=&oName=${oName}&regDate=${regDate}">관련도순</a></li>
									<li class="menu_btn"><a id="newData" href="/search?keyword=${keyword}&sort=desc&oName=${oName}&regDate=${regDate}">최신순</a></li>
									<li class="menu_btn"><a id="oldData" href="/search?keyword=${keyword}&sort=asc&oName=${oName}&regDate=${regDate}">오래된순</a></li>
								</ul>
							</div>
						</li>
						<li class="menu">
							<c:if test="${regDate == 'all' || keyword == null}">
								<a href="#" class="menu_selected" title="date">기간
									<img class="spim" src="../../../resources/images/spim_btn.png" />
								</a>
							</c:if>
							<c:if test="${regDate == '1D'}">
								<a href="#" class="menu_selected" title="date">1일
									<img class="spim" src="../../../resources/images/spim_btn.png" />
								</a>
							</c:if>
							<c:if test="${regDate == '7D'}">
								<a href="#" class="menu_selected" title="date">1주
									<img class="spim" src="../../../resources/images/spim_btn.png" />
								</a>
							</c:if>
							<c:if test="${regDate == '1M'}">
								<a href="#" class="menu_selected" title="date">1개월
									<img class="spim" src="../../../resources/images/spim_btn.png" />
								</a>
							</c:if>
							<div class="menu_list" id="menu_date">
								<ul>
									<li class="menu_btn"><a id="date" href="/search?keyword=${keyword}&sort=${sort}&oName=${oName}&regDate=all">전체</a></li>
									<li class="menu_btn"><a id="day" href="/search?keyword=${keyword}&sort=${sort}&oName=${oName}&regDate=1D">1일</a></li>
									<li class="menu_btn"><a id="week" href="/search?keyword=${keyword}&sort=${sort}&oName=${oName}&regDate=7D">1주</a></li>
									<li class="menu_btn"><a id="month" href="/search?keyword=${keyword}&sort=${sort}&oName=${oName}&regDate=1M">1개월</a></li>
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
									<li class="menu_btn"><a id="0" href="/search?keyword=${keyword}&sort=${sort}&oName=&regDate=${regDate}">전체</a></li>
									<li class="menu_btn"><a id="1" href="/search?keyword=${keyword}&sort=${sort}&oName=연합뉴스&regDate=${regDate}">연합뉴스</a></li>
									<li class="menu_btn"><a id="2" href="/search?keyword=${keyword}&sort=${sort}&oName=뉴시스&regDate=${regDate}">뉴시스</a></li>
									<li class="menu_btn"><a id="3" href="/search?keyword=${keyword}&sort=${sort}&oName=뉴스1&regDate=${regDate}">뉴스1</a></li>
									<li class="menu_btn"><a id="4" href="/search?keyword=${keyword}&sort=${sort}&oName=아시아경제&regDate=${regDate}">아시아경제</a></li>
									<li class="menu_btn"><a id="5" href="/search?keyword=${keyword}&sort=${sort}&oName=머니투데이&regDate=${regDate}">머니투데이</a></li>
									<li class="menu_btn"><a id="6" href="/search?keyword=${keyword}&sort=${sort}&oName=이데일리&regDate=${regDate}">이데일리</a></li>
									<li class="menu_btn"><a id="7" href="/search?keyword=${keyword}&sort=${sort}&oName=파이낸셜뉴스&regDate=${regDate}">파이낸셜뉴스</a></li>
								</ul>
							</div>
						</li>
					</ul>
					<div class="contents">
						<c:if test="${totalRecordsCount > 0}">
							<div class="relation_keyword">
								<div class="tit_relate">
									<span>패싯 분석</span>
									<img id="wtf" src="../../../resources/images/wtf.PNG" />
								</div>
								<div class="list_relate">
									<ul>
										<c:forEach items="${facetList}" var="list">
											<li><a id="facet" href="/search?keyword=${list}">${list}</a></li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</c:if>
						<div class="section_head">
							<c:if test="${totalRecordsCount > 0}">
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
										<span>${transDateList[status.index]}</span>
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
							<div class="paging" style="margin-bottom:100px;">
								<c:if test="${pager.currentGroup > 0}">
									<a class="group_btn" href="/search?keyword=${keyword}&sort=${sort}&oName=${oName}&regDate=${regDate}&currentPage=1">&lt&lt</a>
									<a class="group_btn" href="/search?keyword=${keyword}&sort=${sort}&oName=${oName}&regDate=${regDate}&currentPage=${pager.startPageGroup-1}">&lt</a>
								</c:if>
								<c:forEach var="num" begin="${pager.startPageGroup}" end="${pager.endPageGroup}">
									<c:choose>
										<c:when test="${num == currentPage}">
											<span class="cur_page_btn">${num}</span>
										</c:when>
										<c:otherwise>
											<a class="page_btn" href="/search?keyword=${keyword}&sort=${sort}&oName=${oName}&regDate=${regDate}&currentPage=${num}">${num}</a>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<c:if test="${pager.endPageGroup < pager.totalPageCount}">
									<a class="group_btn" href="/search?keyword=${keyword}&sort=${sort}&oName=${oName}&regDate=${regDate}&currentPage=${pager.endPageGroup+1}">&gt</a>
									<a class="group_btn" href="/search?keyword=${keyword}&sort=${sort}&oName=${oName}&regDate=${regDate}&currentPage=${pager.totalPageCount}">&gt&gt</a>	
								</c:if>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${totalRecordsCount > 0}">
			<a href="#" class="top" onclick="window.scrollTo(0,0); document.querySelector('#wrap a').focus(); return false;">맨위로</a>
		</c:if>
	</div>
</body>
</html>