# Git

### Git 이란?

- 분산버전관리 시스템
- SW 개발에서 코드를 관리하고 협업하기 위해 사용

<br/>

:bulb: GitHub?

- git 으로 관리되는 소스 코드를 저장하고 공유하는 저장소

:bulb: 오픈소스 라이센스

- SW를 공개하고 수정, 배포, 사용을 허용하는 조건을 정의하는 규약
- 소스 코드의 공개 여부, 수정 및 배포에 대한 제한, 상업적 이용 가능 여부 등을 명시

<br/>

### Git 기능

- 분산 버전 관리
- 변경 사항 추적
- 브랜치
- 원격 저장소 지원
- 이력 관리
- 병합 및 충돌 해결
- 태그

<br/>

:bulb: Git Repository

- Git 으로 관리되는 프로젝트 저장소
- 프로젝트의 모든 파일, 폴더, 버전 정보, 이력 등을 저장
- Local (내 컴퓨터에 있는 저장소) / Remote (원격 저장소)

:bulb: Git Branch

- 코드 변경 사항을 병렬적으로 개발 할 수 있는 기능
- 새로운 브랜치를 생성해 기존 소스 코드와는 별개로 작업을 진행
- 브랜치 간에는 서로 영향을 주지 않음
- 여러 개발자가 동시에 작업할 때 충돌 방지

<br/>

### Git 설치하기 (Window기준)

https://git-scm.com/downloads 접속 후 다운로드

Git Bash / Git GUI 체크 후 설치

<br/>

### Git 명령어

| 명령어       | 의미                                                         | 사용                                                         |
| ------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| git init     | Git 저장소를 초기화 하고 로컬 저장소 생성 <br />.git 디렉토리 생성 | git init                                                     |
| git status   | git 저장소의 상태 확인 <br />현재 작업 중인 파일의 변경 사항이나 커밋되지 않은 변경 내역 확인 | git status                                                   |
| git add      | 파일을 stage 영역에 추가<br />-A : 작업 디렉토리 전체의 변경된 파일 추가 | git add < file ><br />git add -A                             |
| git commit   | 변경 내용을 저장하고 커밋<br />변경된 내용에 대한 스냅샷을 생성하고 스냅샷에 대한 메시지를 작성 | git commit -m "commit message"<br />git commit < file >      |
| git push     | 로컬 저장소에서 변경 내용을 원격 저장소로 업로드             | git push < 원격저장소명 > < local_branchname > : <remote_branchname> |
| git pull     | 원격 저장소에서 변경 사항을 가져옴                           | git pull < 원격저장소명 > < branchname >                     |
| git clone    | 원격 저장소를 로컬로 복제                                    | git clone < 원격저장소 URL > < local_name >                  |
| git log      | Git 저장소의 커밋 히스토리 확인<br />가장 최근 것부터 역순으로 출력 | git log<br />git log -n // 최근 n 개의 커밋 출력             |
| git reset    | 이전 커밋 상태로 되돌리기                                    | git reset HEAD // 가장 최근에 스테이징한 변경 내역을 언스테이징<br />git reset HEAD < file ><br />git reset --soft < commit > // 지정 커밋으로 rest, 변경 유지<br />git reset --hard < commit > // 지정 커밋으로 reset, 변경 삭제 |
| git stash    | 변경 사항을 임시로 저장<br />git stash pop 을 통해 다시 적용<br />list 스택에 저장된 목록 확인<br />drop 가장 최근에 저장된 stash 삭제 | git stash<br />git stash list<br />git stash drop<br />git stash pop |
| git diff     | 변경 내용 비교                                               | git diff                                                     |
| git branch   | 현재 저장소의 모든 브랜치 출력                               | git branch -r // 원격 브랜치만 표시<br />git branch -a // 모든 브랜치를 표시<br />git branch -list // 브랜치 이름만 출력<br />git branch -v // 브랜치 이름과 마지막 커밋 메시지 출력<br />git branch -d < branchname > // 브랜치 삭제 |
| git checkout | 브랜치를 전환, 커밋을 확인하기 위해 작업 디렉토리를 변경<br />branchname으로 지정한 브랜치로 변경 | git checkout -b < branchname >  // 브랜치를 생성후 전환      |

<br/>

### Git Branch 전략 세우기

프로젝트 후 후술

<br/>

> Reference
>
> Fastcampus : Signature Backend