<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>

<%--
  게시글 상세 페이지 (detail.jsp)

  필요한 request attribute:
    - post        : Post 객체 (postId, title, author, createdDate, viewCount, replyCount, content)
    - commentList : List<Comment> (author, content, createdDate)
--%>

<c:set var="pageTitle" value="${post.title}" />

<!DOCTYPE html>
<html lang="ko">
<%@ include file="/WEB-INF/views/common/head.jsp" %>
<body>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<main id="main" tabindex="-1" class="shell page">

    <a href="${pageContext.request.contextPath}/posts" class="back-link">
      <i class="pi pi-chevron-left" aria-hidden="true"></i>
      전체 글로
    </a>

    <article class="card article-card">
      <h1 class="page-title article-title">${post.title}</h1>

      <div class="post-head">
        <div class="author">
          <span class="author-face" aria-hidden="true">${fn:substring(post.author, 0, 1)}</span>
          <div>
            <div class="author-name">${post.author}</div>
            <div class="author-date">${post.createdDate}</div>
          </div>
        </div>
        <div class="stat-row">
          <span aria-label="조회 ${post.viewCount}회">
            <i class="pi pi-eye" aria-hidden="true"></i>${post.viewCount}
          </span>
          <span aria-label="댓글 ${fn:length(commentList)}개">
            <i class="pi pi-comment" aria-hidden="true"></i>${fn:length(commentList)}
          </span>
        </div>
      </div>

      <hr class="rule" />

      <%-- white-space: pre-line 스타일이 styles.css에 있어야 줄바꿈이 그대로 보입니다 --%>
      <div class="post-body">${post.content}</div>

      <div class="post-actions">
        <button type="button" class="p-button p-button-danger" onclick="document.getElementById('deleteDialog').showModal()">
          <i class="pi pi-trash" aria-hidden="true"></i>
          <span>글 삭제</span>
        </button>
        <a href="${pageContext.request.contextPath}/posts/${post.id}/edit" class="p-button p-button-secondary">
          <i class="pi pi-pencil" aria-hidden="true"></i>
          <span>글 수정</span>
        </a>
      </div>
    </article>

    <section class="card comments-card">
      <div class="section-heading">
        <div>
          <h2 class="section-title">댓글 ${fn:length(commentList)}개</h2>
          <p>답변이나 참고 자료를 나누면 더 빨리 해결할 수 있어요.</p>
        </div>
      </div>

      <ul class="comment-list">
        <c:forEach var="comment" items="${commentList}">
          <li class="comment">
            <span class="comment-face" aria-hidden="true">${fn:substring(comment.author, 0, 1)}</span>
            <div>
              <div class="author-name">
                ${comment.author}
                <span class="author-date comment-when">${comment.createdDate}</span>
              </div>
              <p class="comment-text">${comment.content}</p>
            </div>
          </li>
        </c:forEach>

      </ul>

      <c:if test="${empty commentList}">
        <ui:contentState
          compact="true"
          icon="pi-comments"
          title="아직 댓글이 없어요"
          description="첫 댓글을 남겨보세요!" />
      </c:if>

      <%-- TODO: 백엔드 Controller 완성 후 실제로 동작함 --%>
      <form class="comment-form field" method="post" action="${pageContext.request.contextPath}/posts/${post.id}/comments">
        <label class="field-label" for="comment">댓글 작성</label>
        <textarea id="comment" name="content" class="p-inputtext" rows="3" placeholder="해결 방법이나 참고 자료를 알려주세요"></textarea>
        <div class="row-end">
          <button type="submit" class="p-button">댓글 등록</button>
        </div>
      </form>
    </section>

    <%-- 삭제 확인 모달 --%>
    <dialog id="deleteDialog">
      <h2>이 글을 삭제할까요?</h2>
      <p>댓글 ${fn:length(commentList)}개도 함께 사라지고, 되돌릴 수 없어요.</p>
      <div class="dialog-footer">
        <button type="button" class="p-button p-button-help" onclick="document.getElementById('deleteDialog').close()">취소</button>
        <%-- TODO: 백엔드 Controller 완성 후 실제로 동작함 --%>
        <form method="post" action="${pageContext.request.contextPath}/posts/${post.id}/delete" style="display:inline">
          <button type="submit" class="p-button p-button-danger">삭제</button>
        </form>
      </div>
    </dialog>

</main>

<script>
  document.getElementById('deleteDialog').addEventListener('click', function (e) {
    if (e.target === this) {
      this.close();
    }
  });
</script>
</body>
</html>
