<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>

<%--
  게시글 목록 페이지 (list.jsp)

  필요한 request attribute:
    - postList   : List<Post>
    - currentPage: int (아직 미구현)
    - totalPages : int (아직 미구현)

  가정한 Post 필드: postId, title, author, createdDate, viewCount, replyCount, notice(boolean)
--%>

<c:set var="pageTitle" value="질문과 해결 방법" />

<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/views/common/head.jsp" %>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<main id="main" tabindex="-1" class="shell page">

    <section class="page-intro" aria-labelledby="board-title">
      <div>
        <h1 class="page-title" id="board-title">질문과 해결 방법</h1>
        <p class="page-description">
          미션을 진행하며 생긴 질문과 해결한 방법을 나눠보세요.
        </p>
      </div>
    </section>

    <section class="board-panel" aria-label="게시글 목록">
      <div class="board-toolbar">
        <%-- TODO: 필터 기능은 백엔드 연동 시 ?filter=all / ?filter=notice 형태로 구현 예정 --%>
        <div class="tabs" role="group" aria-label="게시글 필터">
          <button type="button" class="tab is-active" aria-pressed="true">전체</button>
          <button type="button" class="tab" aria-pressed="false">공지</button>
        </div>
        <div class="toolbar-meta">
          <p class="result-count">${fn:length(postList)}개의 글</p>
          <%-- TODO: 정렬 기능은 백엔드 연동 시 ?sort=view 형태로 구현 예정 --%>
          <label class="sort-control">
            <span class="sr-only">게시글 정렬</span>
            <select>
              <option selected>최신순</option>
              <option>조회순</option>
            </select>
            <i class="pi pi-chevron-down" aria-hidden="true"></i>
          </label>
        </div>
      </div>

      <div class="card card--list">
        <ul class="post-list">
          <c:forEach var="post" items="${postList}">
            <li class="post-item${post.notice ? ' is-notice' : ''}">
              <div class="post-item-body">
                <div class="post-item-head">
                  <c:if test="${post.notice}">
                    <span class="pill-notice">공지</span>
                  </c:if>
                  <h2 class="post-item-title">
                    <a href="${pageContext.request.contextPath}/posts/${post.id}">
                      <span>${post.title}</span>
                    </a>
                  </h2>
                </div>
                <div class="post-item-meta">
                  <span class="post-author">${post.author}</span>
                  <span class="sep"></span>
                  <span>${post.createdDate}</span>
                  <span class="sep"></span>
                  <span>조회 ${post.viewCount}</span>
                </div>
              </div>
              <div class="post-item-side">
                <span class="reply-count${post.replyCount > 0 ? ' has-replies' : ''}">
                  <i class="pi pi-comment" aria-hidden="true"></i>
                  <span class="sr-only">댓글 </span>${post.replyCount}
                </span>
              </div>
            </li>
          </c:forEach>

        </ul>

        <c:if test="${empty postList}">
          <ui:contentState
            icon="pi-inbox"
            title="아직 등록된 게시글이 없어요"
            description="첫 번째 글을 남겨보세요!" />
        </c:if>
      </div>
    </section>

    <%-- TODO: 페이지네이션은 백엔드 연동 시 ?page=N 형태로 구현 예정 --%>
    <div class="pager" aria-label="페이지 이동 UI">
      <span class="is-disabled" aria-hidden="true"><i class="pi pi-chevron-left"></i></span>
      <span class="is-static" aria-current="page">1</span>
      <span class="is-static">2</span>
      <span class="is-static">3</span>
      <span class="is-static" aria-label="다음 페이지"><i class="pi pi-chevron-right" aria-hidden="true"></i></span>
  </div>

</main>
</body>
</html>
