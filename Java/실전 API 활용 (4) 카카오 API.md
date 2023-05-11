# 실전 API 활용 (4) 카카오 API

<br/>

- 목표 : 카카오 REST Open API를 사용하여 주소를 입력받아 해당 주소의 위도와 경도 정보 출력하는 미니 프로젝트
- 요구사항
  1. 카카오 개발자 계정을 생성 (https://developers.kakao.com), 새 애플리케이션을 만들어 앱 키를 발급
  2. Maven 프로젝트 생성, Apache HttpClient와 Google Gson 라이브러리 추가
  3. 주소를 입력받아 위도와 경도를 반환하는 getAddressCoordinate 메서드를 포함하는 kakaoApi 클래스 작성
  4. 키보드로부터 주소를 입력받아 getAddressCoordinate 메서드를 호출하고 출력하는 메인클래스 작성
- 평가기준
  - 카카오 REST API를 올바르게 사용하여 주소를 입력받고 결과를 반환하는지 확인
  - getAddressCoordinate 메서드의 정상적인 작동
  - 메인클래스의 구현
  - 코딩 스타일, 가독성 및 코드 주석

<br/>

: 클래스

- KakaoApi (getAddressCoordinate 메서드 포함)
- KakaoMapMain

```java
import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class KakaoApi {
    private static final String KAKAO_API_KEY = ""; // REST API 키 입력
    public static double[] getAddressCoordinate(String address) throws IOException {
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        String encodedAddress = URLEncoder.encode(address, "UTF-8");
        String requestUrl = apiUrl + "?query=" + encodedAddress;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        // httpclient 객체 생성
        HttpGet httpGet = new HttpGet(requestUrl);
        // 요청 URL 만들기
        httpGet.setHeader("Authorization", "KakaoAK " + KAKAO_API_KEY);
        // httpGet에 인증키(Header)을 넣어주기

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            // responseBody에 결과 JSON 데이터
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
            JsonArray documents = jsonObject.getAsJsonArray("documents");

            if (documents.size() > 0) { // 데이터가 있다면
                JsonObject document = documents.get(0).getAsJsonObject();
                double latitude = document.get("y").getAsDouble();
                double longitude = document.get("x").getAsDouble();
                return new double[]{latitude, longitude};
            } else {
                return null;
            }
        }
    }
}
```

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KakaoMapMain {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("주소를 입력하세요:");
            String address = reader.readLine();

            double[] coordinates = KakaoApi.getAddressCoordinate(address);

            if (coordinates != null) {
                System.out.println("주소: " + address);
                System.out.println("위도: " + coordinates[0]);
                System.out.println("경도: " + coordinates[1]);
            } else {
                System.out.println("주소를 찾을 수 없습니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


```

<br/>

> Reference
>
> Fastcampus : Signature Backend