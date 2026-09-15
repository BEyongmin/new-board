<%@ tag description="빈 상태 / 에러 상태를 보여주는 재사용 UI 조각 (React ContentState 컴포넌트 대응)" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%--
  사용법 (list.jsp, detail.jsp 등에서):
    <%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>   ← 파일 맨 위에 한 번만 선언

    <ui:contentState
      icon="pi-inbox"
      title="아직 등록된 게시글이 없어요"
      description="첫 번째 글을 남겨보세요!" />

  React ContentState의 props와 1:1로 대응:
    title, description, icon, compact, tone
--%>

<%@ attribute name="icon" required="false" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="description" required="false" %>
<%@ attribute name="compact" required="false" type="java.lang.Boolean" %>
<%@ attribute name="tone" required="false" %>

<%-- React의 기본값(icon = 'pi-info-circle', tone = 'neutral')과 동일한 역할 --%>
<c:set var="resolvedIcon" value="${empty icon ? 'pi-info-circle' : icon}" />
<c:set var="resolvedTone" value="${empty tone ? 'neutral' : tone}" />

<%-- React의 [a, b, c].filter(Boolean).join(' ') 를 EL 삼항연산자 이어붙이기로 대체 --%>
<div
  class="content-state${compact ? ' content-state--compact' : ''}${resolvedTone == 'danger' ? ' content-state--danger' : ''}"
  role="${resolvedTone == 'danger' ? 'alert' : 'status'}"
>
  <span class="content-state-icon" aria-hidden="true">
    <i class="pi ${resolvedIcon}"></i>
  </span>
  <h2>${title}</h2>
  <c:if test="${not empty description}">
    <p>${description}</p>
  </c:if>
</div>
