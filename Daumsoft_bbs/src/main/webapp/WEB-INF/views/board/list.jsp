<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../include/comm.jsp" %>
</head>
<body>
      <div id="info">자유게시판</div>
      <div id="bbs">
      <button type="button" id="write_btn"><a href="/board/write">글 작성</a></button>
        <table>
          <thead>
            <tr>
              <th class="bbs_title" scope="col" width="10%">번호</th>
              <th class="bbs_title" scope="col" width="45%">제목</th>
              <th class="bbs_title" scope="col" width="20%">작성자</th>
              <th class="bbs_title" scope="col"width="15%">등록일</th>
           	</tr>
          </thead>
          <tbody>
          <c:forEach items="${map.list}" var="list">
            <tr>
              <td class="bbs_contents" scope="col" width="10%">${list.bno}</td>
              <td class="bbs_contents" scope="col" width="45%">
              	<a href="/board/read?bno=${list.bno}&curPage=${map.curPage}&search_option=${map.search_option}&keyword=${map.keyword}">${list.title}</a>
              </td>
              <td class="bbs_contents" scope="col"width="20%">${list.writer}</td>
 				<td class="bbs_contents" scope="col" width="15%">
					<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${list.regDate}"/> 
				</td>
           	</tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
      
      <div class="paging">
	      <c:forEach begin="1" end="${map.pageNum}" var="num">
	        <Button type="button" class="pg_btn"><a href="/board/list?curPage=${num}">${num}</a></Button>
	      </c:forEach>
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
	      <input type="text" id="search_keyword"  name="keyword" placeholder="검색어를 입력하세요." />
	      <input type="submit" id="search_btn" value="검색"/>
	  </div>
   </form>
</body>
</html>