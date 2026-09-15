<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%--
  공통 <head> 조각 (head.jsp)

  사용법: include 하기 전에 pageTitle 변수를 먼저 설정하세요.
    <c:set var="pageTitle" value="질문과 해결 방법" />
    <%@ include file="/WEB-INF/views/common/head.jsp" %>

  pageTitle을 안 정하면 "개발 미션 게시판"만 출력됩니다.
--%>
<head>
  <meta charset="UTF-8" />
  <link rel="icon" type="image/svg+xml" href="${pageContext.request.contextPath}/favicon.svg" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />

  <link rel="preconnect" href="https://cdn.jsdelivr.net" crossorigin />
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard-dynamic-subset.min.css" />
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/primeicons/primeicons.css" />
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/primereact@10.9.8/resources/themes/lara-light-blue/theme.css" />

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />

  <meta name="description" content="개발 미션 중 생긴 질문과 해결 방법을 나누는 게시판" />
  <title><c:if test="${not empty pageTitle}">${pageTitle} · </c:if>개발 미션 게시판</title>
</head>
