#  WePat - 유튜브 콘텐츠 기반 SNS

## 🐾 프로젝트 진행 기간
2023.01.09(월) ~ 2022.02.17(금) (39일간 진행)  
SSAFY 8기 1학기 공통프로젝트 - WePet
</br>

## 🐶 WePat - 배경
마음으로 낳고 지갑으로 키우는 우리 집 반려 동물들.
집사들은 반려동물이 건강하고 씩씩하게 자라길 바라지만,
이 재롱둥이들은 참 아픈 데도 많고 챙길 것도 많습니다.

온 가족이 함께 분담해서 보살피자고 약속해도,
조금이라도 소통이 어그러지면 밥을 더 주거나, 오늘치 산책을 나가지 못하거나, 중요한 약 복용시간을 놓치기도 하고,
심지어 병원에 데려갈 사람과 찾아올 사람을 헷갈려 아이가 병원에서 몇시간이고 떨기도 합니다.

우리(We)가 함께 우리 집 막내를 쓰다듬듯(Pat) 세심히 보살펴줄 수 있는, 
반려동물 일정 관리 SNS, WePat을 통해 이러한 불편함과 미안함을 씻은 듯이 없애버리세요!

</br>

## 🐱 WePat - 개요
*- 반려 동물의 일정을 보다 편리하게 관리하고 공유하자 -*

WePet은 '캘린더'를 통해 가정에서 함께 키우는 반려 동물의 일정을 쉽게 관리하고, 쉽게 공유할 수 있도록 기능을 제공하는 모바일 웹서비스입니다.
함께 관리하는 다른 분의 관리 일정과 반려 동물의 일정을 공간과 시간에 제약 받지 않고 쉽게 확인할 수 있습니다.
WePet은 <span style="color:#CCD5AE">We</span> 우리 함께 <span style="color:#CCD5AE">Pat</span> 다정하게 쓰다듬는다 의 의미를 갖고 있습니다.
반려 동물을 함께 관리하는 분들과 일정을 공유한다는 점에서 SNS 컨셉으로 개발되었습니다. 

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
