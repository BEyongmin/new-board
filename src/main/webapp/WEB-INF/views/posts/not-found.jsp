<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>

<c:set var="pageTitle" value="게시글을 찾을 수 없어요" />

<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/views/common/head.jsp" %>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<main id="main" tabindex="-1" class="shell page">
  <ui:contentState
      tone="danger"
      icon="pi-exclamation-triangle"
      title="게시글을 찾을 수 없어요"
      description="${errorMessage}" />
</main>
</body>
</html>