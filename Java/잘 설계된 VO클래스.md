

# 잘 설계된 VO클래스

<br/>

 ### 정보은닉

정보은닉  (information hiding) : 다른 객체에게 자신의 정보를 숨기고 자신의 동작, 기능, 연산만을 통해 접근을 허용하는 것으로, 클래스 외부에서 특정 정보에 접근을 막는다

```java
public class PersonVO {
    private String name;
    private int age;
    private String phone;
}
// 객체지향 프로그래밍에서는 객체를 설계 할 때 일반적으로 상태정보를 정보은닉시킨다
```

```java
import fc.java.model.PersonVO;
public class PersonInfoHide {
	public static void main(String[] args){
		PersonVO p = new PersonVO();
		// p.name = "신짱구" // private이므로 p.name형식으로 직접접근 불가능
        // p.int = 20; // 에러
        // p.phone = "010-1111-1111"; // 에러
	}
}
```

<br/>

### setter, getter 메서드

정보 은닉된 정보에 접근하는 방법으로 setter, getter 메서드를 사용

```java
public class PersonVO {
    private String name;
    private int age;
    
    public void setName(String name){ // setter 메서드, 위 멤버변수 name에 매개변수 String name을 할당
        this.name = name; // this는 접근 가능
    }
    public void setAge(int age){ // setter 메서드
        this.age = age;
    }
    public String getName(){ // getter 메서드, 멤버변수 private String name을 return
        return this.name; // getter의 this는 생략가능
    }
    public int getAge(){ // getter 메서드
        return this.age;
    }
}
```

```java
import fc.java.model.PersonVO;
public class PersonInfoHide {
	public static void main(String[] args){
		PersonVO p = new PersonVO();
		p.setName("신짱구"); // setter 메서드 호출
        p.setAge(20);
        
        System.out.println(vo.getName() + "\t" + vo.getAge();); // 신짱구	20
	}
}
```

:bulb: setter 메서드 내부에 조건문을 통해 원하는 범위의 입력값만을 할당할 수도 있다.

<br/>

### 생성자를 이용한 객체 초기화

정보 은닉된 정보에 접근하는 방법으로 생성자 메서드를 통해 접근 가능 (객체 초기화)

```java
public class PersonVO {
    private String name;
    private int age;
    
    // 생략된 생성자 메서드 > 기본생성자 (default Constructor)
    public PersonVO(){
        // 객체를 생성하는 코드는 내부에서 만들어지며, 객체를 초기화 한다
        this.name = "신짱구";
        this.age = 20;
    }
    
    public String getName(){
        return this.name;
    }
    public int getAge(){
        return this.age;
    }
}
```

```java
import fc.java.model.PersonVO;
public class ConstructorInit {
    public static void main(String[] args) {
        PersonVO vo = new PersonVO();
        // setter로 값을 설정해 주지 않아도 초기화 시 값을 getter 메서드로 접근 가능
        System.out.println(vo.getName() + "\t" + vo.getAge()); // 신짱구	20
    }
}
```

:bulb: 객체 생성 시 원하는 값으로 초기화 하려면, 생성자 메서드를 오버로딩 (Overloading) 하여 초기화 가능

```java
public class PersonVO {
    private String name;
    private int age;
    
    public PersonVO(){ // 생성자 오버로딩 시 Default는 자동생성 되지 않기에 사용한다면 명시 필요
		
    }
    
    public PersonVO(String name, int age){ // 사용자 정의 생성자, 생성자 메서드 오버로딩
        this.name = name;
        this.age = age;
    }

    public String getName(){
        return this.name;
    }
    public int getAge(){
        return this.age;
    }
}
```

```java
import fc.java.model.PersonVO;
public class ConstructorOverloading {
    public static void main(String[] args) {
        PersonVO vo = new PersonVO(); // 생성자 오버로딩 후 기본메서드 명시가 없을 경우 에러
        PersonVO vo1 = new PersonVO("신짱아", 10);
        PersonVO vo2 = new PersonVO("신형만", 50);
        System.out.println(vo.getName() + "\t" + vo.getAge()); // null	0	null
        System.out.println(vo1.getName() + "\t" + vo1.getAge()); // 신짱아	10
        System.out.println(vo2.getName() + "\t" + vo2.getAge()); // 신형만	50
    }
}
```

:bulb: 사용자 정의 생성자가 있으면 Default 생성자를 자동으로 만들지 않는다. 필요하다면 기본 생성자를 별도로 명시해 줄 것

<br/>

### toString() 메서드로 객체 값 출력

객체가 가지고 있는 값 전체를 문자열 형태로 넘겨주기

```java
public class PersonVO {
    private String name;
    private int age;
    
    public PersonVO(){
    }
    public PersonVO(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName(){
        return this.name;
    }
    public int getAge(){
        return this.age;
    }
    
    public String toString(){ // toString() 메서드 생성
        return this.name + "\t" + this.age;
    }
}
```

```java
import fc.java.model.PersonVO;
public class ToStringPrint {
    public static void main(String[] args) {
        PersonVO vo = new PersonVO("신짱구", 20);
        System.out.println(vo.getName() + "\t" + vo.getAge()); // 신짱구	20
        System.out.println(vo.toString()); // 신짱구	20
        System.out.println(vo); // 신짱구	20, 자동으로 toString() 메서드를 호출
    }
}
```

<br/>

### 잘 설계된 VO클래스 실습

1. 모든 생태정보를 정보은닉 하기 (private)

2. 디폴트 생성자를 반드시 만들기

3. 생성자 메서드를 오버로딩 하여 객체 초기화 하기

4. setter 메서드 만들기 (값을 저장하는 용도)

5. getter 메서드 만들기 (값을 얻는 용도)

6. toString() 메서드를 만들기 (객체가 가지고 있는 전체 값 출력 용도)

<br/>

```java
public class MovieVO {
    // 1. 모든 생태정보를 정보은닉 하기 (private)
    private String title;
    private int day;
    private String major;

    // 2. 디폴트 생성자를 반드시 만들기
    public MovieVO() {
    }
    // 3. 생성자 메서드를 오버로딩 하여 객체 초기화 하기
    public MovieVO(String title, int day, String major) {
        this.title = title;
        this.day = day;
        this.major = major;
    }
    // 4.,5. Getter & Setter 만들기
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
    // 6. toString() 메서드를 만들기 (객체가 가지고 있는 전체 값 출력 용도)
    @Override
    public String toString() {
        return "MovieVO{" +
                "title='" + title + '\'' +
                ", day=" + day +
                ", major='" + major + '\'' +
                '}';
    }
}
```

```java
import fc.java.model.MovieVO;
public class BestVOModeling {
    public static void main(String[] args) {
        MovieVO vo = new MovieVO("아바타", 20221214, "제이크 설리");
        System.out.println(vo); // MovieVO{title='아바타', day=20221214, major='제이크 설리'}
    }
}
```

<br/>

---

Quiz.

1. 다른 객체에게 자신의 정보를 숨기고 클래스 외부에서 특정 정보에 접근을 막는다는 의미를 무엇이라고 하는지 쓰시오

   → 정보은닉

2. 정보은닉된 정보에 접근하는 방법 중 값을 저장하는 메서드를 무엇이라고 하는지 쓰시오

   → setter 메서드

3. 정보은닉된 정보에 접근하는 방법 중 값을 얻어오는 메서드를 무엇이라고 하는지 쓰시오

   → getter 메서드

4. 정보은닉된 정보에 접근하는 방법 중 객체를 초기화를 통해 접근하는 메서드 는 무엇인지 쓰시오

   → 생성자 메서드

5. 객체가 가지고 있는 값 전체를 문자열 형태로 넘겨주기 위해서 만드는 메서드는 무엇인지 쓰시오

   → toString()

<br/>

> Reference
>
> Fastcampus : Signature Backend