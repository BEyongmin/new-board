<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%--
  공통 <header> 조각 (header.jsp)
  다른 JSP 페이지에서 아래처럼 불러와서 씁니다:
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
--%>
<header class="site-header">
  <div class="shell header-inner">
    <a href="${pageContext.request.contextPath}/posts" class="logo">
      <span class="logo-mark" aria-hidden="true">M</span>
      <span class="logo-copy"><strong>개발 미션 게시판</strong></span>
    </a>

    <div class="header-actions">
      <div class="search">
        <i class="pi pi-search" aria-hidden="true"></i>
        <form action="${pageContext.request.contextPath}/posts" method="get">
          <input
            type="search"
            name="keyword"
            value="${fn:escapeXml(keyword)}"
            class="p-inputtext"
            placeholder="질문이나 해결 방법 검색"
            aria-label="게시글 검색"
          />
        </form>
      </div>
      <a href="${pageContext.request.contextPath}/posts/write" class="p-button header-write">
        <i class="pi pi-plus" aria-hidden="true"></i>
        <span>글쓰기</span>
      </a>
    </div>
  </div>
</header>
