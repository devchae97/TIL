# 실전 API 활용 (1) 사전지식

### Excel API (POI) 활용

자바에서 Apache POI 라이브러리를 사용해 Excel 파일 읽기/쓰기

> File > New > Project > Java Maven Project > pom.xml에 POI dependency 추가

```xml
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>5.2.0</version>
</dependency>

<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.0</version>
</dependency>

<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.17.1</version>
</dependency>
```

<br/>

엑셀 예시 파일 생성

| example.xlsx | A          | B          | C          | D          | E          |
| ------------ | ---------- | ---------- | ---------- | ---------- | ---------- |
| 1            | 1          | 2          | 3          | 4          | 5          |
| 2            | A          | B          | C          | D          | E          |
| 3            | 홍길동     | 이길동     | 조길동     | 박길동     | 송길동     |
| 4            | 35.6       | 59.1       | 23.2       | 65.8       | 32.1       |
| 5            | 2022-03-20 | 2022-03-21 | 2022-03-22 | 2022-03-23 | 2022-03-24 |

<br/>

엑셀 파일 읽어오기

```java
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelExample {
    public static void main(String[] args) {
        try{
            FileInputStream file = new FileInputStream(new File("example.xlsx"));
            // 파일 읽기를 위한 스트림 생성, (읽어들일 파일객체의 경로설정)

            Workbook workbook = WorkbookFactory.create(file);
            // 실제 엑셀 파일을 핸들링하기 위한 메모리상 가상의 엑셀 workbook, Factory는 workbook을 만드는 클래스

            Sheet sheet = workbook.getSheetAt(0); // 첫번째 시트
            for(Row row : sheet){ // 행
                for(Cell cell : row){ // 열
                    System.out.print(cell.toString() + "\t");
                }
                System.out.println(); // 열 줄바꿈
            }
            file.close(); // 스트림 닫기
            System.out.println("엑셀에서 데이터 읽어오기 성공");
            
        }catch(IOException e){ // 파일이 없을 경우를 대비한 예외처리
            e.printStackTrace();
        }
    }
}
```

```java
// 출력결과 (첫 번째 행과 마지막 행의 타입이 일치하지 않은 결과를 확인가능)

// 1.0	2.0	3.0	4.0	5.0	
// A	B	C	D	E	
// 홍길동	이길동	조길동	박길동	송길동	
// 35.6	59.1	23.2	65.8	32.1	
// 20-3월-2023	21-3월-2023	22-3월-2023	23-3월-2023	24-3월-2023	
// 엑셀에서 데이터 읽어오기 성공
```

<br/>

타입을 맞춰 읽어오기

```java
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelExample {
    public static void main(String[] args) {
        try{
            FileInputStream file = new FileInputStream(new File("example.xlsx"));
            // 파일 읽기를 위한 스트림 생성, (읽어들일 파일객체의 경로. 같은 디렉터리면 이름)

            Workbook workbook = WorkbookFactory.create(file);
            // 실제 엑셀 파일을 핸들링하기 위한 메모리상 가상의 엑셀, Factory는 workbook을 만드는 클래스

            Sheet sheet = workbook.getSheetAt(0); // 첫번째 시트
            for(Row row : sheet){ // 행
                for(Cell cell : row){ // 열
                    switch(cell.getCellType()){ // 읽은 cell의 타입
                        case NUMERIC: // 숫자 타입인 경우
                            if(DateUtil.isCellDateFormatted(cell)){ // cell 타입이 날짜 타입인 경우
                                Date dateVaule = cell.getDateCellValue();
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                // 이 형태로 날짜의 포맷을 지정
                                String formattedDate = dateFormat.format(dateVaule);
                                System.out.print(formattedDate + "\t");
                            }else{
                                double numericValue = cell.getNumericCellValue();
                                if(numericValue == Math.floor(numericValue)){ // 소숫점 버리기했을 때 같다, 정수
                                    int intValue = (int) numericValue;
                                    System.out.print(intValue + "\t");
                                }else{ // 이외 경우 실수
                                    System.out.print(numericValue + "\t");
                                }
                            }
                            break;
                        case STRING:
                            String stringValue = cell.getStringCellValue();
                            System.out.print(stringValue + "\t");
                            break;
                        case BOOLEAN:
                            boolean booleanValue = cell.getBooleanCellValue();
                            System.out.print(booleanValue + "\t");
                            break;
                        case FORMULA:
                            String formulaValue = cell.getCellFormula();
                            System.out.print(formulaValue + "\t");
                        case BLANK:
                            System.out.print("\t");
                            break;
                        default:
                            System.out.print("\t");
                            break;
                    }
                }
                System.out.println(); // 열 줄바꿈
            }
            file.close(); // 스트림 닫기
            System.out.println("엑셀에서 데이터 읽어오기 성공");
        }catch(IOException e){ // 파일이 없을 경우를 대비한 예외처리
            e.printStackTrace();
        }
    }
}
```

```java
// 출력결과 (엑셀과 같은 결과가 출력)

// 1	2	3	4	5	
// A	B	C	D	E	
// 홍길동	이길동	조길동	박길동	송길동	
// 35.6	59.1	23.2	65.8	32.1	
// 2023-03-20	2023-03-21	2023-03-22	2023-03-23	2023-03-24	
// 엑셀에서 데이터 읽어오기 성공
```

<br/>

회원정보 (이름, 나이, 생년월일, 전화번호, 주소, 결혼여부) 를 Member VO에 저장하여 엑셀에 저장하기 (이름에 quit을 입력 시 종료)

```java
import kr.excel.entity.MemberVO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExcelWriter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<MemberVO> members = new ArrayList<>();
        while(true){
            System.out.print("이름을 입력하세요:");
            String name = scanner.nextLine();
            if(name.equals("quit")){
                break;
            }

            System.out.print("나이를 입력하세요:");
            int age = scanner.nextInt();
            scanner.nextLine(); // 개행문자 제거

            System.out.print("생년월일을 입력하세요:");
            String birthdate = scanner.nextLine();

            System.out.print("전화번호를 입력하세요:");
            String phone = scanner.nextLine();

            System.out.print("주소를 입력하세요:");
            String address = scanner.nextLine();

            System.out.print("결혼여부을 입력하세요 (true/false):");
            boolean isMarried = scanner.nextBoolean();
            scanner.nextLine(); // 개행문자 제거

            MemberVO member = new MemberVO(name, age, birthdate, phone, address, isMarried);
            members.add(member);
        }
        scanner.close();

        try{
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("회원정보");

            // 헤더생성
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("이름");
            headerRow.createCell(1).setCellValue("나이");
            headerRow.createCell(2).setCellValue("생년월일");
            headerRow.createCell(3).setCellValue("전화번호");
            headerRow.createCell(4).setCellValue("주소");
            headerRow.createCell(5).setCellValue("결혼여부");

            // 데이터 생성
            for (int i = 0; i < members.size(); i++){
                MemberVO member = members.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(member.getName());
                row.createCell(1).setCellValue(member.getAge());
                row.createCell(2).setCellValue(member.getBirthdate());
                row.createCell(3).setCellValue(member.getPhone());
                row.createCell(4).setCellValue(member.getAddress());
                Cell marriedCell = row.createCell(5);
                marriedCell.setCellValue(member.isMarried());
            }

            // 엑셀파일 저장
            String filename = "members.xlsx";
            FileOutputStream outputStream = new FileOutputStream(new File(filename));
            workbook.write(outputStream);
            workbook.close();
            System.out.println("엑셀 파일이 저장되었습니다:" + filename);

        }catch(IOException e){
            System.out.println("엑셀파일 저장 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }
}
```

<br/>

### PDF API (iText) 활용

| 순서 | 코드                                                         | 설명                                                         |
| ---- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1    | PdfWriter writer = new PdfWriter(new FileOutputStream("book_information.pdf")); | PdfWriter 객체를 생성해 PDF 파일을 출력하기 위한 스트림을 지정 |
| 2    | PdfDocument pdf = new PdfDocument(wirter);                   | PdfWriter 객체를 사용하여 PdfDocument 객체를 생성            |
| 3    | Document document = new Document(pdf);                       | PdfDocument 객체를 사용하여 Document 객체를 생성             |
| 4    | 폰트 생성 및 추가                                            | PdfForntFactory 클래스의 creatFont() 메서드를 사용하여 폰트를 생성 후 Document 객체의 setFont() 메서드를 사용하여 폰트를 설정 |
| 5    | 책 정보를 문단으로 생성하여 Document에 추가                  | 책 정보를 HashMap 객체에 저장하고, HashMap의 keySet() 메서드를 사용하여 모든 키를 순회하며 키와 값을 이용하여 문단을 생성 |
| 6    | document.close();                                            | Document 객체를 닫기                                         |

<br/>

dependency 주입

```xml
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itext7-core</artifactId>
    <version>7.1.15</version>
    <type>pom</type>
</dependency>
```

<br/>

책 정보를 PDF 파일에 생성 (제목, 저자, 출판사, 년도, 가격, 페이지수)

```java
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Year;
import java.util.HashMap;

public class BookInfoToPDF {
    public static void main(String[] args) {
        HashMap<String, String> bookInfo = new HashMap<>();
        bookInfo.put("title", "한글 자바");
        bookInfo.put("author", "홍길동");
        bookInfo.put("publisher", "한글 출판사");
        bookInfo.put("year", String.valueOf(Year.now().getValue()));
        bookInfo.put("price", "25000");
        bookInfo.put("pages", "400");

        try{
            // PDF 생성을 위한 PrdWriter 객체 생성
            PdfWriter writer = new PdfWriter(new FileOutputStream("book_information.pdf"));

            // PdfWriter 객체를 사용하여 PdfDocument 객체 생성
            PdfDocument pdf = new PdfDocument(writer);

            // Document 객체 생성
            Document document = new Document(pdf);

            // 폰트 생성 및 추가
            PdfFont font = PdfFontFactory.createFont("NanumGothic.ttf", PdfEncodings.IDENTITY_H, true);
            // 나눔고딕 필체, UTF-8 인코딩, 파일이 존재하는지 여부

            document.setFont(font);

            // 책 정보를 문단으로 생성하여 Document에 추가
            for(String key : bookInfo.keySet()){
                Paragraph paragraph = new Paragraph(key + ": " + bookInfo.get(key));
                document.add(paragraph);
            }

            // Document 닫기
            document.close();

            System.out.println("book_information.pdf 파일이 생성되었습니다.");

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
```

<br/>

책 이미지 포함 정보를 입력받아 Pdf 파일 생성

```java
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

public class PDFFile {
    public static void main(String[] args) throws MalformedURLException, IOException {
        String dest = "book_table.pdf";
        new PDFFile().createPdf(dest);
    }

    public void createPdf(String dest) throws MalformedURLException, IOException {
        List<Map<String, String>> books = createDummyData();

        // Initialize PDF writer and PDF document
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);

        // Initialize fonts
        PdfFont headerFont = null;
        PdfFont bodyFont = null;

        try{
            headerFont = PdfFontFactory.createFont("NanumGothic.ttf", "Identity-H", true);
            bodyFont = PdfFontFactory.createFont("NanumGothic.ttf", "Identity-H", true);
        }catch(IOException e){
            e.printStackTrace();
        }

        // Initialize table
        float[] columnWidths = {1, 2, 2, 2, 2, 2};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        // Initialize table header cells
        Cell headerCell1 = new Cell().add(new Paragraph("순번").setFont(headerFont));
        Cell headerCell2 = new Cell().add(new Paragraph("제목").setFont(headerFont));
        Cell headerCell3 = new Cell().add(new Paragraph("저자").setFont(headerFont));
        Cell headerCell4 = new Cell().add(new Paragraph("출판사").setFont(headerFont));
        Cell headerCell5 = new Cell().add(new Paragraph("출판일").setFont(headerFont));
        Cell headerCell6 = new Cell().add(new Paragraph("이미지").setFont(headerFont));

        table.addHeaderCell(headerCell1);
        table.addHeaderCell(headerCell2);
        table.addHeaderCell(headerCell3);
        table.addHeaderCell(headerCell4);
        table.addHeaderCell(headerCell5);
        table.addHeaderCell(headerCell6);

        // Add table body cells
        int rowNum = 1;
        for(Map<String, String> book : books){
            String title = book.get("title");
            String authors = book.get("authors");
            String publisher = book.get("publisher");
            String publishedDate = book.get("publishedDate");
            String thumbnail = book.get("thumbnail");

            Cell rowNumCell = new Cell().add(new Paragraph(String.valueOf(rowNum))).setFont(bodyFont);
            table.addCell(rowNumCell);
            Cell titleCell = new Cell().add(new Paragraph(String.valueOf(title))).setFont(bodyFont);
            table.addCell(titleCell);
            Cell authorsCell = new Cell().add(new Paragraph(String.valueOf(authors))).setFont(bodyFont);
            table.addCell(authorsCell);
            Cell publisherCell = new Cell().add(new Paragraph(String.valueOf(publisher))).setFont(bodyFont);
            table.addCell(publisherCell);
            Cell publishedDateCell = new Cell().add(new Paragraph(String.valueOf(publishedDate))).setFont(bodyFont);
            table.addCell(publishedDateCell);

            ImageData imageData = ImageDataFactory.create(new File(thumbnail).toURI().toURL());
            Image img = new Image(imageData);
            Cell imageCell = new Cell().add(img.setAutoScale(true));
            table.addCell(imageCell);
            rowNum++;
        }
        document.add(table);
        document.close();
    }

    private static List<Map<String, String>> createDummyData(){
        List<Map<String, String>> books = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("책 개수를 입력하세요: ");
        int bookCount = scanner.nextInt();
        scanner.nextLine(); // 개행 문자 제거
        for(int i = 1; i <= bookCount; i++){
            Map<String, String> book = new HashMap<>();

            System.out.printf("\n[ %d번째 책 정보 입력 ]\n", i);
            System.out.print("제목: ");
            book.put("title", scanner.nextLine());

            System.out.print("저자: ");
            book.put("authors", scanner.nextLine());

            System.out.print("출판사: ");
            book.put("publisher", scanner.nextLine());

            System.out.print("출판일(YYYY-MM-DD): ");
            book.put("publishedDate", scanner.nextLine());

            System.out.print("썸네일 URL: ");
            book.put("thumbnail", scanner.nextLine());

            books.add(book);
        }
        scanner.close();
        return books;
    }
}
```