# 완전 탐색 (Brute Force)

- 문제를 해결하기 위해 확인해야 하는 모든 경우를 전부 탐색하는 방법

:warning: 전부 탐색하기에 시간 복잡도가 일반적으로 높다.

<br/>

그 중에서도 백 트래킹을 통해야 하는 상황을 해결하기

:bulb: backtracking : 해를 찾는 도중 해가 아니어서 막히면, 되돌아가서 다시 해를 찾아가는 기법

<br/>

- 일반적인 종류

  1. N개 중 중복을 허용해서 M개를 순서 있게 나열
  2. N개중 중복 없이 M개를 순서 있게 나열

  3. N개 중 중복을 허용해서 M개를 고르기

  4. N개 중 중복 없이 M개를 고르기

<br/>

1. N개 중 중복을 허용해서 M개를 순서 있게 나열

>  BOJ 15651 - N과 M(3) : https://www.acmicpc.net/problem/15651

완전탐색의 시간, 공간 복잡도

- 시간 : O(N^M) => 아래 1번 문제 최악의 경우 7^7 => 82만으로 1초에 1억번 연산이 가능하다 가정 시 완전 탐색으로 해결이 가능
- 공간 : O(M) : M개의 선택을 저장하는데 필요한 공간

```java
public class Main{
    static StringBuilder sb = new StringBuilder();
    
    static void input() {
        // FastReader scan = new FastReader(); // 입력 소요되는 시간 줄이기 위함, 설명은 알고리즘 이외로 일단 생략
        Scanner scan = new Scanner(System.in);
        N = scan.nextInt();
        M = scan.nextInt();
        selected = new int[M + 1];
    }
    
	static int N, M;
    static int[] selected;

    // Recurrence Function (재귀 함수)
    // 만약 M 개를 전부 고름   => 조건에 맞는 탐색을 한 가지 성공한 것!
    // 아직 M 개를 고르지 않음 => k 번째부터 M번째 원소를 조건에 맞게 고르는 모든 방법을 시도한다.
    static void rec_func(int k) {
        if (k == M + 1) { // 다 골랐다!
            // selected[1...M] 배열이 새롭게 탐색된 결과
            for (int i = 1; i <= M; i++) sb.append(selected[i]).append(' ');
            sb.append('\n');
        } else {
            for (int cand = 1; cand <= N; cand++) {
                // k 번째에 cand 가 올 수 있으면
                selected[k] = cand;

                // k+1 번부터 M 번까지 잘 채워주는 함수를 호출해준다.
                rec_func(k + 1);
                selected[k] = 0;
            }
        }
    }
    
    public static void main(String[] args) {
        input();

        // 1 번째 원소부터 M 번째 원소를 조건에 맞는 모든 방법을 찾아줘
        rec_func(1);
        System.out.println(sb.toString());
    }
}
```

<br/>

2. N개 중 중복 없이 M개를 순서 있게 나열하기

> BOJ 15649 - N과 M(1) : https://www.acmicpc.net/problem/15649

- 시간 : O(N!/(N-M)!) => 8!/0! => 40,320
- 공간 : O(M)

```java
import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();

    static void input() {
        Scanner scan = new Scanner();
        N = scan.nextInt();
        M = scan.nextInt();
        selected = new int[M + 1];
    }

    static int N, M;
    static int[] selected;
    // Recurrence Function (재귀 함수)
    // 만약 M 개를 전부 고름   => 조건에 맞는 탐색을 한 가지 성공한 것!
    // 아직 M 개를 고르지 않음 => k 번째부터 M번째 원소를 조건에 맞게 고르는 모든 방법을 시도한다.
    static void rec_func(int k) {
        if (k == M + 1) { // 1 ~ M 번째를 전부 다 골랐다!
            // selected[1...M] 배열이 새롭게 탐색된 결과
            for (int i = 1; i <= M; i++) sb.append(selected[i]).append(' ');
            sb.append('\n');
        } else {
            for (int cand = 1; cand <= N; cand++) {
                boolean isUsed = false;
                for (int i=1;i<k;i++)
                    if (cand == selected[i])
                        isUsed = true;
                // k 번째에 cand 가 올 수 있으면
                if (!isUsed) {
                    selected[k] = cand;
                    rec_func(k + 1);
                    selected[k] = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        input();

        // 1 번째 원소부터 M 번째 원소를 조건에 맞는 모든 방법을 찾아줘
        rec_func(1);
        System.out.println(sb.toString());

    }
}
```

<br/>

3. N개 중 중복을 허용해서 M개를 고르기

> BOJ 15652 - N과 M(4) : https://www.acmicpc.net/problem/15652

고른 수열이 비 내림차순

- 시간 : O(N^M) => 8^8 => 1677만보단 작음
- 공간 : O(M)

```java
import java.io.*;
import java.util.*;

public class Main {
    static StringBuilder sb = new StringBuilder();

    static void input() {
        Scanner scan = new Scanner();
        N = scan.nextInt();
        M = scan.nextInt();
        selected = new int[M + 1];
    }

    static int N, M;
    static int[] selected;
    // Recurrence Function (재귀 함수)
    // 만약 M 개를 전부 고름   => 조건에 맞는 탐색을 한 가지 성공한 것!
    // 아직 M 개를 고르지 않음 => k 번째부터 M번째 원소를 조건에 맞게 고르는 모든 방법을 시도한다.
    static void rec_func(int k) {
        if (k == M + 1) { // 1 ~ M 번째를 전부 다 골랐다!
            // selected[1...M] 배열이 새롭게 탐색된 결과
            for (int i = 1; i <= M; i++) sb.append(selected[i]).append(' ');
            sb.append('\n');
        } else {
            int start = selected[k-1];
            if (start == 0) start = 1;
            for (int cand = start; cand <= N; cand++) {
                // k 번째에 cand 가 올 수 있으면
                selected[k] = cand;
                rec_func(k + 1);
                selected[k] = 0;
            }
        }
    }

    public static void main(String[] args) {
        input();

        // 1 번째 원소부터 M 번째 원소를 조건에 맞는 모든 방법을 찾아줘
        rec_func(1);
        System.out.println(sb.toString());

    }
}
```

<br/>

> Reference
>
> Fastcampus : Signature Backend