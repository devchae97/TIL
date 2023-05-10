# 실전 API (3)  크롤링

<br/>

- 목표 : 카카오 책 REST API를 이용하여 도서 제목을 입력하여 도서정보를 추출한 후 PDF 파일에 도서 목록을 저장하는 미니 프로젝트

- 요구사항
  1. 카카오 책 검색 API를 사용하여 도서 정보를 검색할 수 있는 기능을 구현
  2. 검색된 도서 정보를 PDF 파일로 저장하는 기능을 구현
  3. 사용자로부터 검색할 도서의 제목을 입력 받아, 검색 결과를 PDF 파일로 저장
  4. 프로젝트는 콘솔 환경에서 실행
- 필요한 라이브러리
  1. HTTP 요청 라이브러리 : OkHttp 혹은 Apache HttpClient
  2. JSON 파싱 라이브러리 : Jackson, Gson 등
  3. PDF 생성 라이브러리 : iText, Apache PDFBox 등

<br/>

: 클래스

- Book
- BookSearchMain
- KaKaoBookApi
- PdfGenerator

```java
public class Book {
    private String title;
    private String authors;
    private String publisher;
    private String thumbnail;

    public Book() {
    }

    public Book(String title, String authors, String publisher, String thumbnail) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", publisher='" + publisher + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
```

```java
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class BookSearchMain {
    public static void main(String[] args) {
        try {
            Scanner scanner=new Scanner(System.in);
            System.out.print("도서제목을 입력하세요:");
            String bookTitle = scanner.nextLine();
            List<Book> books = KaKaoBookApi.searchBooks(bookTitle);

            if (books.isEmpty()) {
                System.out.println("검색 결과가 없습니다.");
            } else {
                for(Book book : books){
                    System.out.println(book);
                }
                String fileName = "도서목록.pdf";
                PdfGenerator.generateBookListPdf(books, fileName);
                System.out.println(fileName + " 파일이 생성되었습니다.");
            }
        } catch (IOException e) {
            System.err.println("에러가 발생했습니다: " + e.getMessage());
        }
    }
}
```

```java
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KaKaoBookApi {
    private static final String API_KEY = ""; // Rest Key 삽입
    private static final String API_BASE_URL = "https://dapi.kakao.com/v3/search/book";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    // 책 검색 메서드
    public static List<Book> searchBooks(String title) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(API_BASE_URL).newBuilder();
        // URL 생성, URL과 연결
        urlBuilder.addQueryParameter("query", title);

        // 넘어갈 request 생성
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Authorization", "KakaoAK " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            // newCall 메서드에 request 넣고 execute 실행 시 서버로 요청, response로 응답받기
            if (!response.isSuccessful()) throw new IOException("Request failed: " + response);

            JsonObject jsonResponse = gson.fromJson(response.body().charStream(), JsonObject.class);
            // response로 받아온 데이터를 charStream으로 만들어 하나의 JsonObject로 변환
            JsonArray documents = jsonResponse.getAsJsonArray("documents");

            List<Book> books = new ArrayList<>();
            for (JsonElement document : documents) {
                JsonObject bookJson = document.getAsJsonObject();
                Book book = new Book(
                        bookJson.get("title").getAsString(),
                        bookJson.get("authors").getAsJsonArray().toString(),
                        bookJson.get("publisher").getAsString(),
                        bookJson.get("thumbnail").getAsString()
                );
                books.add(book);
            }
            return books;
        }
    }
}
```

```java
package kr.book.search;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import java.io.*;
import java.util.*;
import java.net.MalformedURLException;
public class PdfGenerator {
    public static void generateBookListPdf(List<Book> books, String fileName) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.setFontSize(12);

        // 폰트 생성 및 추가
        PdfFont font=null;
        try {
            font = PdfFontFactory.createFont("NanumGothic.ttf", PdfEncodings.IDENTITY_H, true);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        document.setFont(font);

        // 타이틀 추가
        Paragraph titleParagraph = new Paragraph("도서 목록");
        titleParagraph.setFontSize(24);
        titleParagraph.setTextAlignment(TextAlignment.CENTER);
        titleParagraph.setBold();
        document.add(titleParagraph);

        // 도서 정보 테이블 생성
        Table table = new Table(UnitValue.createPercentArray(new float[]{2, 2, 2, 2}));
        table.setWidth(UnitValue.createPercentValue(100));
        table.setMarginTop(20);

        // 테이블 헤더 추가
        table.addHeaderCell(createCell("제목", true));
        table.addHeaderCell(createCell("저자", true));
        table.addHeaderCell(createCell("출판사", true));
        table.addHeaderCell(createCell("이미지", true));

        // 도서 정보를 테이블에 추가
        for (Book book : books) {
            table.addCell(createCell(book.getTitle(), false));
            table.addCell(createCell(book.getAuthors(), false));
            table.addCell(createCell(book.getPublisher(), false));

            // 이미지 추가
            try {
                ImageData imageData = ImageDataFactory.create(book.getThumbnail());
                Image image = new Image(imageData);
                image.setAutoScale(true);
                table.addCell(new Cell().add(image).setPadding(5));
            } catch (MalformedURLException e) {
                table.addCell(createCell("이미지 불러오기 실패", false));
            }
        }

        document.add(table);
        document.close();
    }

    private static Cell createCell(String content, boolean isHeader) {
        Paragraph paragraph = new Paragraph(content);
        Cell cell = new Cell().add(paragraph);
        cell.setPadding(5);
        if (isHeader) {
            cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
            cell.setFontSize(14);
            cell.setBold();
        }
        return cell;
    }
}
```

<br/>

> Reference
>
> Fastcampus : Signature Backend