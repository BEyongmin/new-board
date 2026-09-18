<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%--
  게시글 작성 / 수정 폼 (form.jsp) - 두 역할을 한 파일이 겸함
  Controller가 post attribute를 넘기면 수정 모드, 안 넘기면 작성 모드로 동작합니다.
--%>

<c:set var="pageTitle" value="${isEdit ? '새 글 작성' : '글 수정'}" />

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

    <section class="page-intro page-intro--compact">
      <div>
        <h1 class="page-title">${isEdit ? '글 수정' : '새 글 작성'}</h1>
        <p class="page-description">질문이나 해결 방법을 작성하면 목록에 바로 보여요.</p>
      </div>
    </section>

    <div class="write-layout">
      <c:choose>
        <c:when test="${not isEdit}">
          <c:set var="formAction" value="${pageContext.request.contextPath}/posts" />
        </c:when>
        <c:otherwise>
          <c:set var="formAction" value="${pageContext.request.contextPath}/posts/${postId}" />
        </c:otherwise>
      </c:choose>

      <form class="card form-card" method="post" action="${formAction}">
        <div class="field">
          <label class="field-label" for="title">
            제목
            <span class="req" aria-hidden="true">*</span>
          </label>
          <input
            type="text"
            id="title"
            name="title"
            class="p-inputtext"
            value="${fn:escapeXml(post.title)}"
            placeholder="예: 페이지네이션 쿼리는 어떻게 넘기시나요?"
            aria-describedby="title-count"
            maxlength="100"
            required
          />
          <div class="field-foot">
            <span class="field-hint" id="title-count">0 / 100자</span>
          </div>
          <c:if test="${not empty errors.title}">
            <p class="field-error">${errors.title}</p>
          </c:if>
        </div>

        <div class="field">
          <label class="field-label" for="author">
            닉네임
            <span class="req" aria-hidden="true">*</span>
          </label>
          <input
            type="text"
            id="author"
            name="author"
            class="p-inputtext"
            value="${fn:escapeXml(post.author)}"
            placeholder="목록에 표시될 이름"
            aria-describedby="author-count"
            maxlength="20"
            required
          />
          <div class="field-foot">
            <span class="field-hint" id="author-count">0 / 20자</span>
          </div>
          <c:if test="${not empty errors.author}">
            <p class="field-error">${errors.author}</p>
          </c:if>
        </div>

        <div class="field">
          <label class="field-label" for="content">
            내용
            <span class="req" aria-hidden="true">*</span>
          </label>
          <textarea
            id="content"
            name="content"
            class="p-inputtext"
            rows="12"
            placeholder="막힌 부분, 시도해본 방법, 궁금한 점을 차례로 적어보세요"
            aria-describedby="content-count"
            maxlength="2000"
            required
          >${fn:escapeXml(post.content)}</textarea>
          <div class="field-foot">
            <span class="field-hint" id="content-count">0 / 2,000자</span>
          </div>
          <c:if test="${not empty errors.content}">
            <p class="field-error">${errors.content}</p>
          </c:if>
        </div>

        <div class="form-footer">
          <button type="button" class="p-button p-button-help btn-xl" onclick="document.getElementById('leaveDialog').showModal()">
            작성 취소
          </button>
          <button type="submit" class="p-button btn-xl">
            <i class="pi pi-check" aria-hidden="true"></i>
            <span>${isEdit ? '수정 완료' : '글 등록' }</span>
          </button>
        </div>
      </form>

      <aside class="writing-guide" aria-labelledby="writing-guide-title">
        <span class="guide-icon" aria-hidden="true"><i class="pi pi-lightbulb"></i></span>
        <h2 id="writing-guide-title">답변받기 좋은 글</h2>
        <ul>
          <li>문제가 생긴 상황을 먼저 알려주세요.</li>
          <li>이미 시도한 방법을 함께 적어주세요.</li>
          <li>개인정보는 글에 남기지 마세요.</li>
        </ul>
      </aside>
    </div>

    <dialog id="leaveDialog">
      <h2>작성을 그만둘까요?</h2>
      <p>지금 나가면 입력한 내용이 사라져요.</p>
      <div class="dialog-footer">
        <button type="button" class="p-button p-button-help" onclick="document.getElementById('leaveDialog').close()">계속 작성</button>
        <a href="${pageContext.request.contextPath}/posts" class="p-button p-button-danger">내용 버리고 나가기</a>
      </div>
    </dialog>

</main>

<script>
  function bindCounter(inputId, counterId, max) {
    var input = document.getElementById(inputId);
    var counter = document.getElementById(counterId);
    var suffix = max >= 1000 ? (max.toLocaleString() + '자') : (max + '자');
    function update() {
      counter.textContent = input.value.length + ' / ' + suffix;
    }
    input.addEventListener('input', update);
    update();
  }

  bindCounter('title', 'title-count', 100);
  bindCounter('author', 'author-count', 20);
  bindCounter('content', 'content-count', 2000);

  document.getElementById('leaveDialog').addEventListener('click', function (e) {
    if (e.target === this) {
      this.close();
    }
  });
</script>
</body>
</html>
