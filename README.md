
# 🍽️ EatToday
> "오늘 뭐 먹지?" 라는 고민을 덜어주는 식당 추천 & 예약 서비스

사용자의 취향과 위치를 기반으로 한 식당 추천, 직관적인 지도 UI, 간편 예약 및 리뷰까지 제공합니다. 실시간 채팅 기능도 도입하여 사용자와 사장님 간의 소통을 강화하였습니다.

---

## 📋 프로젝트 소개

- **프로젝트명**: EatToday
- **진행 기간**: 2023.07 ~ 2024.08 (진행 중)
- **기획 문서**: [Notion 링크](https://resolute-cloak-c53.notion.site/EatToday-b0df429aa1584c4b8dfc6742be384227?pvs=4)

---

## 🖼️ 미리보기
<img width="1446" alt="서비스 미리보기" src="https://github.com/user-attachments/assets/b717c99a-c8cb-42b6-b52b-f6d5a423b48b" />

---

## 🚀 주요 기능

### 🔍 맞춤형 식당 추천
- 사용자 취향 기반 추천 알고리즘으로 개인화된 식당 리스트 제공
- 위치 기반 추천으로 현재 위치 주변 인기 식당 탐색 가능

### 📅 예약 시스템
- 식당별 예약 가능 시간대를 실시간으로 확인
- 사용자 마이페이지에서 예약 내역 관리

### 💬 실시간 채팅 기능
- 사용자와 식당 사장님 간의 실시간 메시지 송수신
- STOMP 기반 WebSocket + DB 저장 연동 (sender/receiver/room 구분)

### ❤️ 즐겨찾기 & 리뷰 관리
- 즐겨찾기 등록 및 해제, 상태에 따른 UI 동적 변경
- 식당별 리뷰 작성, 수정, 삭제 및 평점 조회 기능 구현

### 🗺️ 지도 기반 위치 확인 및 길찾기
- 카카오맵 API를 이용한 지도 렌더링 및 마커 표시
- 현재 위치 기준 식당 길찾기 링크 제공

### 🖥️ 사용자 경험 중심 UI
- 레이어 팝업을 활용한 로그인, 회원가입, 아이디/비밀번호 찾기 기능
- 반응형 구조와 시각적 강조 요소를 활용한 접근성 높은 화면 설계

---

## 🛠️ 기술 스택

| 분류         | 사용 기술                                                      |
|--------------|----------------------------------------------------------------|
| Backend      | Spring Boot, Spring Security, Spring Data JPA                 |
| Frontend     | Thymeleaf, HTML5, CSS3, JavaScript (Vanilla JS)               |
| Database     | MySQL                                                          |
| Real-Time    | WebSocket, STOMP, SockJS                                       |
| 인증/인가    | JWT (Json Web Token)                                           |
| 외부 API     | Kakao Map API                                                  |
| 인프라/배포  | AWS EC2, AWS RDS, GitHub Actions, Docker                      |

---

## 👥 역할 분담

| 이름       | 담당 업무 요약 |
|------------|----------------|
| **박보겸** [@bogyeom0922](https://github.com/bogyeom0922) | - 프론트엔드 전체 구현 (메인, 상세, 예약, 채팅 UI) <br> - 실시간 채팅 기능 구현 (WebSocket, SockJS) <br> - 리뷰 CRUD 개발 <br> - DB 설계 및 연관관계 매핑 <br> - AWS 기반 배포 (EC2, RDS) 및 CI/CD 구성 <br> - 기획 및 문서화 (Notion 등) |
| **김대연** [@daeyeon](https://github.com/kdy4049067) | - 예약 기능 구현 (날짜/시간 선택, 예약 내역 조회) <br> - 즐겨찾기 등록/해제 기능 <br> - 식당 추천 로직 구현 <br> - DB 공동 설계 <br> - 기획 및 문서화 참여 |
| **문상휘** [@moonwhistle](https://github.com/moonwhistle) | - 회원가입 및 로그인 기능 구현 (JWT 인증) <br> - DB 공동 설계 <br> -  기획 및 문서화 참여 |
