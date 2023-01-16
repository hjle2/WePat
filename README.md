#  WePat - 반려동물 일정 관리 SNS

## 🐾 프로젝트 진행 기간
2023.01.09(월) ~ 2022.02.17(금) (39일간 진행)  
SSAFY 8기 1학기 공통프로젝트 - WePat
</br>

## 🐶 WePat - 배경
마음으로 낳고 지갑으로 키우는 우리 집 반려 동물들.</br>
집사들은 반려동물이 건강하고 씩씩하게 자라길 바라지만,</br>
이 재롱둥이들은 참 아픈 데도 많고 챙길 것도 많습니다.</br>

온 가족이 함께 분담해서 보살피자고 약속해도,</br>
조금이라도 소통이 어그러지면 밥을 더 주거나, 오늘치 산책을 나가지 못하거나, 중요한 약 복용시간을 놓치기도 하고,</br>
심지어 병원에 데려갈 사람과 찾아올 사람을 헷갈려 아이가 병원에서 몇시간이고 떨기도 합니다.</br>

우리(We)가 함께 우리 집 막내를 쓰다듬듯(Pat) 세심히 보살펴줄 수 있는, </br>
반려동물 일정 관리 SNS, WePat을 통해 이러한 불편함과 미안함을 씻은 듯이 없애버리세요!</br>


</br>

## 🐱 WePat - 개요
*- 반려 동물의 일정을 온가족이 편리하게 관리하고 공유하자 -*



WePat은 우리<span style="color:#CCD5AE">We</span>가 함께</br>
반려동물을 쓰다듬듯<span style="color:#CCD5AE">Pat</span> 보살핀다는 뜻입니다. </br>

WePat은 가족과의 반려동물 일정 공유 및,</br>
반려 동물 일정 인증사진을 어플 사용자들과 익명으로 회람하는 경험을 위해</br>
SNS를 겸한 공유 캘린더로 개발되었습니다.</br>

가족별 공유캘린더를 통해, </br>
반려 동물의 밥과 산책같은 데일리 루틴부터 반려동물 적금 가계부, 병원 및 미용 등의 정기적인 이벤트 등을 쉽게 관리 및 공유하고</br>
일정 체크와 인증샷 첨부, 댓글 등의 소통 기능을 제공합니다.
 
아울러 각 가족 캘린더에 업로드된 인증샷을 SNS 탭을 통해서 무작위로 열람하며, </br>
다른 집 재롱둥이들의 예쁜 모습으로 힐링하고 우리 집 재롱둥이의 귀한 사진 자랑할 기회 또한 제공합니다.</br>

</br>

## 🐹 주요 기능
</br>

## 👍 주요 기술
---

**Backend - Spring Boot**
- Java 11
- Gradle 7.6
- IntelliJ IDE
- Springboot 2.7.7
- Spring Web
- lombok 1.18.24
- logback
- WebSocket
- Swagger 3.0.0
- Firestore

**Frontend**

- Visual Studio Code IDE
- vue 3.2.13
- vuex 4.0.2

**CI/CD**
- Firebase 8.1.0
- Node.js 8.19.3

## 👍 프로젝트 파일 구조
---
### Back
```
wepet
  ├── config
  │   ├── auth
  │   ├── filter
  │   ├── interceptor
  │   └── listener
  ├── controller
  ├── service
  ├── repository
  ├── dto
  │   └── simple
  └── utils
```
### Front

front
  ├── node_modules
  ├── public
  └── src
      ├── api
      ├── assets
      ├── components
      │   ├── accounts
      │   ├── calendar
      │   ├── errors
      │   ├── financial
      │   ├── myprofile
      │   ├── petprofile
      │   ├── sns
      │   └── settings
      ├── router
      ├── store
      └── views         
           ├── accounts
           ├── calendar
           ├── errors
           ├── financial
           ├── myprofile
           ├── petprofile
           ├── sns
           └── settings

## 👍 협업 툴
---
- Git
- Notion
- JIRA
- MatterMost
- Webex
- Discord
- Figma

## 👍 협업 환경
---
- Gitlab
  - 코드의 버전을 관리
  - 이슈 발행, 해결을 위한 토론
- JIRA
  - 매주 목표량을 설정하여 Sprint 진행
  - 업무의 할당량을 정하여 Story Point를 설정하고, In-Progress -> Done 순으로 작업
  - 큰 기능 Epic을 구현하기 위해 필요한 세부적인 기능을 Story로 등록하여 관리
  - 해야 할 업무를 백로그에 등록, 해당 이슈를 Spring의 일정 안에 완료할 수 있도록 등록
- 회의
  - 매주 2회 목표, 진행중인 업무, 달성량, 완료 여부 업무 브리핑
  - 각자 위치에서 건네야 할 말이 생기면 팀원의 위치로 이동하여 전달
- Notion
  - 모든 회의 내용은 회의록으로 기록하여 보관
  - 회의가 길어지지 않도록 다음날 제시할 안건을 미리 기록
  - 기술확보 시, 다른 팀원들도 추후 따라할 수 있도록 보기 쉽게 작업 순서와 자료를 정리하여 공유
