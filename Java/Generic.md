# Generic

제네릭(Generic) : 자바에서 데이터 타입을 일반화하는 방법

제네릭 사용시 컬렉션, 메서드, 클래스 등에서 사용하는 데이터 타입을 런타임 시에 결정 가능

:bulb: 제네릭은 < > 기호를 사용하여 표시

<br/>

### 왜 제네릭을 사용해야 하는지?

재사용성이 높아진다. (제네릭 타입을 선언 시 어떤 데이터 타입이 들어올 지 결정하지 않으므로, 다양한 데이터 타입에 대해 일반적으로 적용할 수 있는 메서드나 클래스를 작성 가능)

<br/>

```java
public class ObjectArr<T> { // 제네릭 타입 선언 시 밑의 예시인 String, Movie 등을 적용 가능
    private T[] array;
    private int size;

    public ObjectArr(int size){
        array = (T[])new Object[size];
    }

    public void set(int index, T value){
        array[index] = value;
        size++;
    }

    public T get(int index){
        return array[index];
    }

    public int size(){
        return size;
    }
}
```

```java
import fc.java.model2.ObjectArr;
public class GenericTest {
    public static void main(String[] args) {
        ObjectArr<String> array = new ObjectArr<>(5); // String type
        array.set(0, "Hello");
        array.set(1, "World");
        array.set(2, "Java");
        array.set(3, "Generic");

        for(int i = 0; i < array.size(); i++){
            System.out.print(array.get(i)); // HelloWorldJavaGeneric
        }
    }
}
```

```java
import fc.java.model2.Movie;
import fc.java.model2.ObjectArr;
public class GenericTest {
    public static void main(String[] args) {
        ObjectArr<Movie> mList = new ObjectArr<>(5); // Movie type
        mList.set(0, new Movie("괴물","봉준호","2006","한국"));
        mList.set(1, new Movie("기생충","봉준호","2019","한국"));
        mList.set(2, new Movie("완벽한 타인","이재규","2018","한국"));

        for(int i = 0; i < mList.size(); i++){
            System.out.println(mList.get(i)); // toString()
        }
    }
}
```

<br/>

### 제네릭 타입이란?

제네릭 타입 : 클래스, 인터페이스, 메서드 등에서 사용될 수 있는 타입 매개변수(parameter)

```java
ArrayList<String> list = new ArrayList<>();
```

:bulb: 위 코드에서 < String >은 제네릭 타입 매개변수로, ArrayList에서 사용할 요소의 타입으로 대체. 이를 통해 ArrayList는 요소의 타입을 명시적으로 지정 가능하며 타입 안정성(type safety)를 보장 가능

```java
import fc.java.model2.Movie;
import java.util.*;
public class ArrayListGeneric {
    public static void main(String[] args) {
        List list = new ArrayList<>(); // Object[], 길이 10
        list.add(new Movie("괴물","봉준호","2006","한국"));
        list.add("Hello");

        System.out.println(list.get(0)); // Movie{title='괴물', director='봉준호', year='2006', country='한국'}
        System.out.println(list.get(1)); // Hello
    }
}
// Object[]로, 타입 안정성을 보장하지 못하는 상태
```

```java
import fc.java.model2.Movie;
import java.util.*;
public class ArrayListGeneric {
    public static void main(String[] args) {
        List<Movie> list = new ArrayList<>(); // Movie[]
        list.add(new Movie("괴물","봉준호","2006","한국"));
        // list.add("Hello"); // <Movie> 사용 시 add 불가
        list.add(new Movie("기생충","봉준호","2019","한국"));

        System.out.println(list.get(0));
        System.out.println(list.get(1)); 
    }
}
// Movie[]로, 타입 안정성을 보장
```

<br/>

### 제네릭 멀티 타입 파라미터

제네릭 멀티 타입 파라미터 : 제네릭 타입을 여러 개 선언하여 사용하는 것

```java
public class Pair<K,V> { // 유연성, 재사용성, 타입의 안정성 보장
    private K key;
    private V value;

    public Pair(K key, V value){
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
```

```java
import fc.java.model2.Pair;
import java.util.*;
public class PairGenericTest {
    public static void main(String[] args) {
        Pair<String, Integer> pair = new Pair<>("Hello", 1);
        System.out.println(pair.getKey()); // Hello
        System.out.println(pair.getValue()); // 1

        // 검색을 빠르게 할 수 있는 자료구조 (HashMap, Hashtable)
        Map<String, Integer> maps = new HashMap<>();
        maps.put("kor", 60);
        maps.put("eng", 80);
        maps.put("math", 70);

        System.out.println(maps.get("kor")); // 60
        System.out.println(maps.get("eng")); // 80
        System.out.println(maps.get("math")); // 70
    }
}
```

<br/>

### 제네릭 제한된 타입 파라미터

제한된 타입 파라미터 : 특정한 타입으로 제한된 제네릭 타입 파라미터, 제네릭 클래스나 메서드에서 사용할 수 있는 타입을 제한 가능

```java
public class AverageCalculator<T extends Number>{ }
```

:bulb: 위 코드에서 < T extends Number > 는 T가 Number 클래스 또는 Number 클래스의 하위 클래스인 타입만 사용가능 하다는 의미

:bulb: Number 클래스 : Short, Integer, Long, Float, Double, Byte 의 숫자 타입 클래스가 Number 클래스를 상속

```java
public class AverageCalculator<T extends Number> {
    private T[] numbers;

    public AverageCalculator(T[] numbers){
        this.numbers = numbers;
    }

    public double calculateAverage(){
        double sum = 0.0;
        for(T number : numbers){
            sum += number.doubleValue();
        }
        return sum/numbers.length;
    }
}
```

```java
import fc.java.model2.AverageCalculator;
public class GenericLimitTest {
    public static void main(String[] args) {
        Integer[] integers = {1,2,3,4,5};
        Double[] doubles = {1.0, 2.0, 3.0, 4.0, 5.0};

        AverageCalculator<Integer> integerCalculator = new AverageCalculator<>(integers);
        double integerAverage = integerCalculator.calculateAverage();
        System.out.println(integerAverage); // 3.0

        AverageCalculator<Double> doubleCalculator = new AverageCalculator<>(doubles);
        double doubleAverage = doubleCalculator.calculateAverage();
        System.out.println(doubleAverage); // 3.0
    }
}
```

<br/>

---

Quiz.

1. 자바에서 컬렉션 프레임워크에 저장하는 데이터 타입을 일반화하는 방법을 무엇이라고 하는지 쓰시오

   → 제네릭

2. ArrayList에 영화 객체(Movie)만을 저장하기 위해서 < > 기호에 사용하는 데이터 타입을 쓰시오

   → ArrayList< Movie > list = new ArrayList<>()

3. 키에는 문자열 값에는 숫자를 저장하기 위해서 HashMap< Key, Value >을 만들 때 Key와 Value에 들어가는 타입을 쓰시오

   → < String, Integer >

4. 자바 제네릭을 사용하는 장점을 2가지 쓰시오

   → 코드의 재사용성이 높아진다, 타입의 안정성을 보장한다.

<br/>

> Reference
>
> Fastcampus : Signature Backend