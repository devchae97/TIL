# 실전 API 과제

<br/>

### 이력서 자동 생성 프로그램

##### 요구사항

- 이력 정보와 자기소개서를 입력 받아서 Excel 파일을 생성하는 프로그램
  1. 콘솔에서 아래와 같은 정보를 입력
     - 이력사진
     - 개인이력 (이름, 이메일, 주소, 전화번호, 생년월일)
     - 학력 (졸업년도, 학교명, 전공, 졸업여부)
     - 경력 (근무기간, 근무처, 담당업무, 근속연수)
     - 자기소개
  2. 입력 받은 정보를 바탕으로 Excel 파일을 생성
     - 첫번째 시트 : 이력서 (사진, 이름, 이메일, 주소, 전화번호, 생년월일, 학력, 경력)
     - 두번째 시트 : 자기소개서

:bulb: 경력 학력 정보는 q를 누르면 종료되며, 한 줄로 입력하여 구분은 스페이스바로 하게 한다.

:bulb: 자기소개서는 빈 줄을 입력하면 입력 종료

<br/>

##### 기능 정의 및 설계

1. Model, View, Controller 클래스를 만들어 MVC 패턴을 구성한다.
2. 사용자로부터 정보를 입력받기 위해 View 클래스에서 Scanner를 사용하여 입력받는다.
3. 입력 받은 정보를 이용하여 Model 클래스에 저장한다.
4. Controller 클래스에서 Model에 저장된 정보를 이용하여 엑셀 파일을 생성한다.
5. 엑셀 파일을 생성할 때, Apache POI 라이브러리를 사용한다.
6. 엑셀 파일 생성이 완료되면 View 클래스에서 "이력서가 생성되었습니다." 라는 메세지를 출력한다.
7. 자기소개서의 경우 Scanner.nextLine() 메서드를 이용하여 여러 줄에 걸쳐 입력을 받을 수 있도록 하고, 엑셀 파일에 저장할 때는 "\n"을 이용하여 줄바꿈을 유지하도록 한다.
8. PNG 형식의 이미지를 엑셀 파일에 저장하기 위해, Apache POI 라이브러리를 사용하여 이미지를 삽입할 수 있도록 한다.

<br/>

##### 클래스 설계

- Career 클래스 : 사용자 경력 정보 저장, workPeriod, companyName, jobTitle, employmentYear 변수를 가지고 있으며 생성자, getter & setter 메서드를 포함
- Education 클래스 : 사용자 학력 정보 저장, graduationYear, schoolName, major, graduationStatus 변수를 가지고 있으며 생성자, getter & setter 메서드를 포함
- PersonInfo 클래스 : 사용자의 개인 정보 저장, photo, name, email, address, phoneNumber, birthDate 변수를 가지고 있으며 생성자, getter & setter 메서드를 포함

<br/>

- ResumeView 클래스 : 사용자로부터 이력서 작성에 필요한 정보를 입력받는 역할로 inputPersonInfo(), inputEducationList(), inputCareerList(), inputSelfIntroduction() 메서드를 통해 사용자로부터 개인정보, 학력정보, 경력정보, 자기소개서를 입력받기

<br/>

- ResumeController 클래스 (메인) : 입력받은 정보를 바탕으로 이력서를 생성하는 역할로 createResume(), createResumeSheet(), createSelfIntroductionSheet(), getWrapCellStyle(), saveWorkbookToFile() 메서드를 사용해 이력서 파일을 생성하고 저장, 이 클래스의 메인 메서드에서 ResumeController 인스턴스를 생성하고 createResume() 메서드를 호출하여 이력서 작성 프로세스를 시작

:bulb: 별도의 출력이 없이 파일 저장이기에 Controller 클래스를 메인역할로 사용, getWrapCellStyle() 메서드는 셀 자동 늘리기를 위함

<br/>

> Reference
>
> Fastcampus : Signature Backend