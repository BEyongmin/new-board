# 개발 미션 게시판 — JSP 퍼블리싱

`apps/web`(React) 화면을 자바 개발자가 그대로 이어받아 구현할 수 있도록 **동일한 디자인·마크업·스타일을 JSP/HTML로 변환**한 버전입니다. React, PrimeReact, Vite 등 프론트엔드 런타임 의존성은 없으며, 순수 HTML/CSS 파일(및 include용 `.jspf` 조각)로만 구성되어 있습니다.

## 먼저 알아둘 점

- 이 폴더는 **정적 UI 퍼블리싱만** 제공합니다. API 호출, 서버 로직, 폼 처리, 세션, 페이지 이동은 모두 자바 개발자가 구현합니다.
- 화면 간 링크는 아직 없습니다(`is-static` 클래스가 붙은 버튼/링크는 아직 동작이 연결되지 않았다는 표시입니다).
- 목록·상세·작성 화면은 각각 `list.jsp`, `detail.jsp`, `form.jsp` 파일로 분리되어 있습니다. React 버전과 달리 화면마다 독립된 `.jsp` 파일이므로, 서블릿/컨트롤러에서 이 파일들을 뷰로 그대로 forward하면 됩니다.
- 삭제 확인, 이탈 확인 Dialog는 `hidden` 속성으로 기본 숨김 처리되어 있습니다. 열기/닫기, focus 이동은 자바 개발자가 구현합니다.

## 파일 구조

```text
apps/jsp-web/
├── list.jsp                     # 목록 화면 (PostList.jsx 변환)
├── detail.jsp                   # 상세·댓글 화면 (PostDetail.jsx 변환)
├── form.jsp                     # 작성·수정 화면 (PostForm.jsx 변환)
├── fragments/
│   ├── header.jspf              # 로고·검색·글쓰기 헤더 (AppHeader.jsx 변환)
│   ├── content-state.jspf       # empty/error 상태 마크업 참고 스니펫 (ContentState.jsx 변환)
│   ├── skeleton-list.jspf       # 목록 loading skeleton (PostListSkeleton 변환)
│   └── skeleton-article.jspf    # 상세 loading skeleton (ArticleSkeleton 변환)
├── styles.css                   # React 버전과 동일한 공통 스타일 (그대로 복사)
└── favicon.svg
```

## React 버전과의 대응 관계

| React (apps/web) | JSP (apps/jsp-web) |
|---|---|
| `src/App.jsx` + `main.jsx` | 각 `.jsp` 파일의 `<head>`/`<body>` 셸 |
| `components/AppHeader.jsx` | `fragments/header.jspf` |
| `components/ContentState.jsx` | `fragments/content-state.jspf`, `fragments/skeleton-*.jspf` |
| `pages/PostList.jsx` | `list.jsp` |
| `pages/PostDetail.jsx` | `detail.jsp` |
| `pages/PostForm.jsx` | `form.jsp` |
| `styles.css` | `styles.css` (동일) |

PrimeReact 컴포넌트(Button, InputText, InputTextarea, Dialog)가 렌더링하던 클래스명(`p-button`, `p-inputtext`, `p-dialog` 등)은 `styles.css`가 이미 직접 스타일링하고 있어서, React/PrimeReact 라이브러리 없이도 순수 HTML 태그에 같은 클래스만 붙이면 동일하게 보입니다. 아이콘은 `primeicons` CDN(`pi pi-*` 클래스)을 그대로 사용합니다.

## 자바 개발자가 구현할 범위 (원본 README 과제 1~5와 동일)

- API base URL과 공통 request 처리, 실패 처리
- 정적 샘플 텍스트를 실제 데이터(DB 또는 API 응답) 기반 렌더링으로 교체 — JSP라면 `<c:forEach>`, `${...}` EL 등으로 `post-list`의 `<li>`, `comment-list`의 `<li>` 를 반복 렌더링
- 로고, 글쓰기, 게시글 제목, 뒤로 가기, 수정, 취소, 페이지 번호 등 `is-static` 요소에 실제 이동(링크/폼 제출) 연결
- 목록 검색어·공지 필터·정렬(`select`)·페이지네이션(`pager`) 동작과 URL 파라미터 동기화
- 상세 화면 게시글/댓글 조회, 댓글 등록, 게시글 삭제(확인 Dialog 포함)
- 작성/수정 폼의 controlled 처리에 준하는 서버 검증(제목 100자, 닉네임 20자, 내용 2,000자), 저장(등록/수정) 처리, 이탈 확인 Dialog
- loading(`skeleton-*`), empty, error, not-found, success 상태를 실제 조회/처리 결과에 맞게 표시

세부 API 스펙(예: `GET /posts?_page=1&_per_page=10`, `GET /posts/:id`, `GET /comments?postId=:id`, `POST /posts`, `PATCH /posts/:id`, `DELETE /posts/:id`, `POST /comments`)과 완료 기준은 저장소 루트 `README.md`의 과제 1~5, 완료 기준 절을 자바 백엔드(엔드포인트/URL 매핑)에 맞게 그대로 참고하면 됩니다.

## 실행에 대해

이 폴더에는 실행 가능한 서버 프로젝트(Tomcat, Spring Boot 등)가 포함되어 있지 않습니다. `.jsp`/`.jspf` 파일을 자바 개발자가 준비한 서블릿 컨테이너(Tomcat 등)의 뷰 경로(`WEB-INF/views` 등)에 그대로 옮겨 사용하면 됩니다.
