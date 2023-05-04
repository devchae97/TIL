# 람다와 스트림 API

람다식 : 함수형 프로그래밍에서 사용되는 함수를 간결하게 표현하기 위한 방법 중 하나

<br/>

### 함수형 인터페이스 (Functional Interface)

함수형 인터페이스 : 단 하나의 추상 메서드를 가진 인터페이스, "@FunctionalInterface" Annotation을 통해 명시 가능

:bulb: 자바 8부터 함수형 인터페이스 사용 시 람다 표현식을 활용 가능

<br/>

함수형 인터페이스와 기본적인 구현 방법

```java
@FunctionalInterface // 함수형 인터페이스 Annotation
public interface MathOperation {
    public int operation(int x, int y); // 단 하나의 추상메서드
}
```

```java
// 기본적인 인터페이스 구현 방법
import fc.java.model2.MathOperation;
public class FunctionInterfaceTest implements MathOperation {
    public static void main(String[] args) {
        MathOperation mo = new FunctionInterfaceTest();
        int result = mo.operation(10, 20);
        System.out.println("result = " + result); // result = 30
    }

    @Override
    public int operation(int x, int y) {
        return x + y;
    }
}
```

<br/>

인터페이스를 구현한 구현체를 외부에 만들어 놓고 사용하는 방법

```java
public class MathOperationImpl implements MathOperation{
    @Override
    public int operation(int x, int y) {
        return x + y;
    }
}
```

```java
import fc.java.model2.MathOperation;
import fc.java.model2.MathOperationImpl;

public class FunctionInterfaceTest1 {
    public static void main(String[] args) {
        MathOperation mo = new MathOperationImpl();
        int result = mo.operation(10, 20);
        System.out.println("result = " + result);
    }
}
```

<br/>

인터페이스를 ''익명 내부 클래스''로 구현하는 방법

```java
import fc.java.model2.MathOperation;
public class FunctionInterfaceTest2 {
    public static void main(String[] args) {
        // MathOperation mo = new MathOperation(); // 에러, 인터페이스는 구현체 없이 단독 사용 불가
        MathOperation mo = new MathOperation(){
            @Override
            public int operation(int x, int y) {
                return x + y;
            }
        };
        int result = mo.operation(10, 20);
        System.out.println("result = " + result); // 30
    }
}
```

:bulb: 내부 클래스 : 클래스나 인터페이스 내에서 선언된 클래스, 특정 클래스와 관계를 맺는 경우 선언하는 것을 권장

:bulb: 익명 내부 클래스 : new 연산자로 객체 생성과 동시에 선언되는 내부 클래스

<br/>

- 자바에서 함수형 인터페이스를 사용하는 이유
  1. 람다 표현식 지원
  2. 메서드 참조
  3. 스트림 API와의 통합
  4. 병렬 프로그래밍
  5. 코드 재사용

<br/>

### 함수형 인터페이스 메서드 참조

이미 정의된 메서드를 직접 참조하여 람다 표현식을 더욱 간결하게 만들 수 있고, 기존 메서드를 재사용하고 코드 중복을 줄이는데 도움

- 메서드 참조의 유형

  1. 정적 메서드 참조 : 

     클래스명::메서드명

  2. 인스턴스 메서드 참조 : 

     객체참조::메서드명

  3. 특정 객체의 인스턴스 메서드 참조 : 

     클래스명::메서드명

  4. 생성자 참조 : 

     클래스명::new

<br/>

정적 메서드 참조

```java
@FunctionalInterface // 함수형 인터페이스 Annotation
public interface Converter<F,T> {
    T convert(F from);
}
```

```java
public class IntegerUtils {
    // 정적메서드, 클래스메서드
    public static int stringToInt(String s){
        return Integer.parseInt(s); // "100" -> 100
    }
}
```

```java
import fc.java.model2.Converter;
import fc.java.model2.IntegerUtils;
public class IntegerUtilsTest {
    public static void main(String[] args) {
        // 정적메서드 참조 -> 클래스명::메서드명
        Converter<String, Integer> converter = IntegerUtils::stringToInt;
        int result = converter.convert("123"); // Auto-unboxing으로 int 사용가능
        System.out.println("result = " + result); // result = 123
    }
}
```

<br/>

인스턴스 메서드 참조

```java
public class StringUtils {
    // 인스턴스 메서드
    public String reverse(String s){
        return new StringBuffer(s).reverse().toString();
    }
}
```

```java
import fc.java.model2.Converter;
import fc.java.model2.StringUtils;
public class StringUtilsTest {
    public static void main(String[] args) {
        StringUtils stringutils = new StringUtils();
        // 인스턴스 메서드 참조 -> 객체참조::메서드명
        Converter<String, String> converter = stringutils::reverse;
        String result = converter.convert("hello");
        System.out.println("result = " + result); // result = olleh
    }
}
```

:bulb: StringBuffer : 문자열의 추가, 수정, 삭제가 빈번하게 발생하는 경우 String(immutable) 대신 StringBuffer(mutable) 사용이 유리

<br/>

특정 객체의 인스턴스 메서드 참조

```java
import java.util.*;
public class SortCompareTest {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("홍길동","김길동","이길동");
        // 특정 객체의 인스턴스 메서드 참조 -> 클래스명::메서드명
        Collections.sort(names, String::compareTo);
        System.out.println(names); // [김길동, 이길동, 홍길동]
    }
}
```

<br/>

생성자 참조

```java
@FunctionalInterface
public interface PersonFactory {
    public Person create(String name, int age);
}
```

```java
import fc.java.model2.Person; // name과 age를 갖고있는 Person VO
import fc.java.model2.PersonFactory;
public class PersonFactoryTest {
    public static void main(String[] args) {
        // 생성자 참조 -> 클래스명::new
        PersonFactory personFactory = Person::new;
        Person person = personFactory.create("신짱구", 10);
        System.out.println(person); // Person{name='신짱구', age=10}
    }
}
```

```java
import fc.java.model2.Person;
import fc.java.model2.PersonFactory;
public class PersonFactoryTest {
    public static void main(String[] args) {
        // 익명 내부 클래스 (이전 방법)
        PersonFactory personfactory = new PersonFactory() {
            @Override
            public Person create(String name, int age) {
                return new Person(name, age);
            }
        };
        Person person = personfactory.create("신짱구", 10);
        System.out.println(person); // Person{name='신짱구', age=10}
    }
}
```

<br/>

### 람다식이란?

자바 람다식 : 함수형 프로그래밍에 사용되는 함수를 간결하게 표현하기 위한 방법 중 하나

자바 8부터 도입, 람다식은 익명 함수(anonymous function)의 한 형태로서, 메서드에 대한 구현을 간결하게 표현하는 방법

```java
(parameters) -> {expression}
```

:bulb: parameters : 메서드에 사용할 매개변수 / expression : 메서드의 구현체

```java
// 두 개의 정수를 더하는 메서드를 람다식으로 구현
(int x, int y) -> {return x + y};
```

:bulb: 람다식은 함수형 인터페이스와 함께 사용되며, java.util.function 패키지에 많은 함수형 인터페이스가 정의

<br/>

```java
@FunctionalInterface
public interface MathOperation {
    public int operation(int x, int y);
}
```

```java
import fc.java.model2.MathOperation;
public class LambdaExample {
    public static void main(String[] args) {
        // 이전의 익명 내부 클래스
        /*
        MathOperation add = new MathOperation(int x, int y){
            @Override
            public int operation(int x, int y) {
                return x+y;
            }
        };
        */
        
        // 람다식 : 코드를 간결, 확장, 구현이 쉽다
        // MathOperation add = (int x, int y) -> { return x+y; };
        MathOperation add = (x, y) ->  x+y; // 중괄호와 return 함께 생략 가능, 중괄호가 있으면 return도 기재
        MathOperation multi = (x, y) ->  x*y; // 확장 및 구현이 쉽다
        
        int result = add.operation(10, 20);
        System.out.println("result = " + result); // result = 30
        
        int mulresult = multi.operation(10, 20);
        System.out.println("mulresult = " + mulresult); // mulresult = 200
    }
}
```

<br/>

### 람다식의 사용방법

람다 표현식을 메서드 내에서 사용하거나 메서드의 인자로 전달하는 예제

```java
@FunctionalInterface
public interface StringOperation {
    public String apply(String s);
}
```

```java
import fc.java.model2.StringOperation;
public class LambdaApply {
    public static void main(String[] args) {
        StringOperation toUpperCase = s -> s.toUpperCase();
        StringOperation toLowerCase = s -> s.toLowerCase();

        String input = "Lambda Expressions";
        System.out.println(processString(input,toUpperCase)); // LAMBDA EXPRESSIONS
        System.out.println(processString(input,toLowerCase)); // lambda expressions
    }

    public static String processString(String input, StringOperation operation){
        return operation.apply(input);
    }
}
```

<br/>

### Stream API의 이해

스트림(Stream) : 데이터의 흐름을 다루기 위한 선언형 API

스트림을 사용하여 필터링, 매핑, 정렬 등 다양한 데이터 처리 작업을 적용할 수 있으며, 최종 결과를 배열이나 컬렉션으로 변환 가능

스트림은 데이터 처리 작업을 연속적인 파이프라인으로 나타낼 수 있어 가독성이 높고, 병렬처리를 쉽게 구현 가능

:bulb: 람다와 마찬가지로 자바 8에서 도입

<br/>

배열을 스트림으로 변환해보기 : 배열의 원소들을 스트림 형태로 변환해 처리할 수 있게 하는 것

```java
public class StreamAPITest {
    public static void main(String[] args) {
        // 배열에서 짝수의 합을 구하기 위한 기존 방법
        int[] numbers = {1,2,3,4,5};
        int even = 0;
        for(int num : numbers){
            if(num%2==0){
                even += num;
            }
        }
        System.out.println("even = " + even); // even = 6
    }
}
```

```java
import java.util.Arrays;
import java.util.stream.IntStream;
public class StreamAPITest {
    public static void main(String[] args) {
        int[] numbers = {1,2,3,4,5};

        // 배열을 스트림으로 변환하기 위해 Arrays.stream() 메서드를 사용     
        int sumOfEvens = Arrays.stream(numbers)
                        .filter(n->n%2==0) // n->n%2==0 : 람다식
                        .sum();       
        				// filter() : 스트림 요소를 순회하며 특정 조건을 만족하는 요소로 구성된 새로운 스트림 반환
        System.out.println("sumOfEvens = " + sumOfEvens); // sumOfEvens = 6

        // 스트림을 배열로 변환하기 위해 toArray() 메서드 사용, 컬렉션으로 변환하려면 collect()        
        int[] evenNumbers = Arrays.stream(numbers)
                            .filter(n->n%2==0) 
                            .toArray();
        
        for(int even : evenNumbers){
            System.out.println("even = " + even); // even = 2   even = 4
        }
    }
}
```

<br/>

:bulb: 스트림은 두 가지 유형인 중간연산, 최종연산을 가진다

중간연산은 스트림을 처리하고 다른 스트림을 반환,

최종 연산은 스트림을 처리하고 결과를 반환

| 구분             | 설명                                                         | 예시 메서드                                                  |
| ---------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 중간연산         | 스트림의 원소를 변환, 필터링, 정렬 등 중간 단계에서 처리하는 연산 | filter(), map(), sorted(), distinct(), limit(), skip() 등    |
| 최종연산         | 스트림의 최종결과를 반환하는 연산                            | forEach(), count(), collect(), reduce(), min(), max() 등     |
| 스트림 생성      | 컬렉션, 배열, 파일 등의 데이터 소스를 스트림으로 변환        | stream(), parallelStream()                                   |
| 스트림 종료      | 스트림의 자원을 해제하고 최종 결과를 반환                    | close(), toArray() 등                                        |
| 병렬 스트림      | 여러 개의 스레드를 이용하여 동시에 처리하는 스트림           | parallel(), sequential()                                     |
| 스트림 연결      | 두 개 이상의 스트림을 연결하여 처리하는 스트림               | flatMap(), concat() 등                                       |
| 스트림 요약      | 스트림의 요약 정보를 반환하는 연산                           | summaryStatistics(), averagingDouble(), summingInt() 등      |
| 스트림 분할      | 스트림을 기준에 따라 분할하여 처리하는 연산                  | partitioningBy(), groupingBy(), toMap 등                     |
| 스트림 요소 검색 | 스트림에서 특정 요소를 검색하거나 확인하는 연산              | anyMatch(), allMatch(), noneMatch(), findFirst(), findAny(), forEachOrdered() 등 |
| 스트림 합치기    | 두 개 이상의 스트림을 합쳐서 처리하는 연산                   | reduce(), collect() 등                                       |

<br/>

### Stream의 활용

스트림 API와 함수형 인터페이스를 사용해 List에 저장된 정수들의 짝수 여부를 판별하고, 짝수들만 필터링하여 정렬하고, 각 숫자를 제곱한 후 모든 숫자의 합을 계산하는 예제

```java
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
public class StreamExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Predicate<Integer> isEven = n -> n % 2 == 0;
        
        // 스트림 API를 사용해 짝수만 필터링, 정렬, 제곱, 합계를 계산
        int sumOfSquares = numbers.stream()
                .filter(isEven) // filter() 메서드로 짝수만 필터링, n->n%2==0 isEven.test(?)
                .sorted() // sorted() 메서드로 정렬
                .map(n->n*n) // map() 메서드로 각 숫자를 제곱
                .reduce(0, Integer::sum); // reduce() 메서드로 합계를 계산
        
        		// map() : 주어진 함수를 스트림의 모든 원소에 적용한 결과로 새로운 스트림을 생성
        		// reduce() : 스트림의 요소를 결합하여 하나의 결과 값을 생성하는데 사용
        		
        		// Predicate 사용 대신 메서드 참조 형태도 가능
        		//.filter(StreamExample::isEvenMethod)
        
        System.out.println("sumOfSquares = " + sumOfSquares); // sumOfSquares = 220
    }

    public static boolean isEvenMethod(int number){
        return number % 2 == 0;
    }
}
```

:bulb: Predicate < T > : 자바에서 제공된 함수형 인터페이스로 (boolean test(T t));

<br/>

정수 리스트에서 각 원소를 제곱한 값을 출력하는 예제

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
public class MapStreamExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        List<Integer> squaredNumbers = numbers.stream()
                .map(n->n*n)
                .collect(Collectors.toList());

        System.out.println("squaredNumbers = " + squaredNumbers); // squaredNumbers = [1, 4, 9, 16, 25]
    }
}
```

스트림의 문자열 원소를 대문자로 변환하는 예제

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapStreamExample2 {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("apple","banana","cherry","orange");

        List<String> upperCase = words.stream()
                .map(word->word.toUpperCase())
                .collect(Collectors.toList());
        for(String str : upperCase){
            System.out.println(str); // APPLE   BANANA  CHERRY  ORANGE
        }
    }
}
```

<br/>

---

Quiz.

1. 람다식을 사용할 수 있는 함수형 인터페이스의 조건을 쓰시오

   → 인터페이스가 오직 하나의 추상 메서드를 가지고 있어야 한다

2. 자바 8에서 도입된 람다식의 기본 구조를 쓰시오

   → (매개변수) -> { 실행문 };

3. 스트림 API를 사용할 때, 스트림의 요소들을 필터링 하기 위해 사용하는 메서드의 이름을 쓰시오

   → filter

4. 스트림 API에서 스트림의 각 요소에 주어진 함수를 적용하고, 그 결과로 새로운 스트림을 생성하는 메서드의 이름을 쓰시오

   → map

<br/>

> Reference
>
> Fastcampus : Signature Backend