# 🍱 KOSA 점심.모.먹? (랜덤 점심 추천 프로그램)

> 미니 프로젝트 1차 - 3조  
> JAVA 프로그래밍 기반 콘솔 애플리케이션

---

## 📌 프로젝트 개요

- **프로젝트명**: KOSA RANDOM LUNCH - 점심.모.먹?
- **핵심 목표**  
  - 랜덤 메뉴 추천 기능  
  - 정산 기능 (총 금액/개인별 금액 확인, 금액 분배, 추가 지불 기능)  
  - 흑기사 기능 (특정 인원이 전체 금액 부담)  
  - 식사 종료 및 전송 기능

---

## ⚙️ 개발 환경

- **언어**: Java 17  
- **IDE**: IntelliJ IDEA  
- **데이터베이스**: 텍스트 파일 기반
- **협업툴**: Git / GitHub  
- **프로젝트 관리**: Git Flow 전략 활용  

---

## 🛠️ 주요 기능

### 회원 관리
- 정규식을 통한 회원가입 정보 검증  
  (이름, 아이디, 비밀번호, 은행명, 계좌번호)  
- 조건 불일치 시 로그인 불가  
- 로그인 성공 시 인증 메시지 및 사용자명 출력  

### 방 생성 및 관리
- 방 이름, 인원 수, 방장 이름 입력 필수  
- 방 단위로 메뉴 추천 및 투표 진행  

### 메뉴 추천 방식
1. **투표 기반 추천**: 방 인원 투표 → 최다 득표 메뉴 출력  
2. **랜덤 추천**: 자유롭게 입력받은 메뉴 중 무작위 선택  
3. **Top10 추천**: 고정 메뉴 리스트에서 무작위 추출  

### 정산 및 흑기사 기능
- 총 금액 입력 및 개인별 금액 분배  
- 특정 인원이 추가 금액 부담 가능  
- 흑기사 모드: 특정 인원이 전체 금액 부담  

---

## 📐 시스템 설계

- **유즈케이스 다이어그램 (v1.0 / v2.0)**  
- **전체 흐름도 및 비즈니스 로직**  
- **클래스 다이어그램**  
  - Service: 4개  
  - Repository: 3개  
  - Domain: 5개  
  - Commons: 4개  

---

## 📊 프로젝트 결과

- **Regex**: 회원가입 시 입력값 검증에 활용  
- **CustomException**: 일관된 예외 처리 구현  
- **BigDecimal**: 금액 계산 시 정확성 확보  

---

## 🎬 시연
<img width="738" height="452" alt="1번" src="https://github.com/user-attachments/assets/45904cdd-9964-4824-88a6-4a3fbdda656e" /><br />
회원가입<br />
<img width="328" height="437" alt="2번" src="https://github.com/user-attachments/assets/d6072e4c-ca1a-4396-8e59-a91712308ee0" /><br />
로그인<br />
<img width="383" height="325" alt="3번" src="https://github.com/user-attachments/assets/83b01b8a-b13d-4de5-a159-0d5f1026ab26" /><br />
방 정보 입력(방 만들기)<br />
<img width="451" height="546" alt="4번" src="https://github.com/user-attachments/assets/8539d522-807c-4599-ae10-aaffd5e82e5f" /><br />
메뉴 투표하기<br />
<img width="788" height="610" alt="5번" src="https://github.com/user-attachments/assets/b0449aad-88fa-4861-9354-86e477431e8e" /><br />
입력받은 메뉴로 랜덤메뉴 선정<br />
<img width="491" height="380" alt="6번" src="https://github.com/user-attachments/assets/3065a305-3c3b-4739-a44d-4efe129f98c0" /><br />
인기메뉴 중 랜덤메뉴 선정<br />
<img width="646" height="368" alt="7번" src="https://github.com/user-attachments/assets/94373380-71a2-4d06-bdc0-0eaecd01ed03" /><br />
정산하기와 흑기사 기능<br />


---

## ❓ Q & A

궁금한 점은 언제든 질문해주세요.  

---

## 👏 마무리

> "점심 메뉴 선택의 번거로움을 줄이고, 즐거운 협업을 경험할 수 있었습니다."
