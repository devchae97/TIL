# 실전 API 활용 (2) MVC

자바 미니 프로젝트 : MVC (Model - View - Controller) 기반 애완동물 진료관리 애플리케이션 개발

![Java 7-1](https://github.com/devchae97/TIL/blob/master/img/Java%207-1.png?raw=true)

Model : 데이터와 비즈니스 로직 관리

View : 레이아웃과 화면 처리

Controller : 명령을 모델과 뷰 부분으로 라우팅

<br/>

### Model

- Customer (고객정보)
- MedicalRecord (진료기록)

```java
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String phoneNumber;
    private String ownerName;
    private String petName;
    private String address;
    private String species;
    private int birthYear;
    private List<MedicalRecord> medicalRecords; // 진료 기록을 저장하는 리스트
    public Customer(){   }

    public Customer(String phoneNumber, String ownerName, String petName, String address, String species, int birthYear) {
        this.phoneNumber = phoneNumber;
        this.ownerName = ownerName;
        this.petName = petName;
        this.address = address;
        this.species = species;
        this.birthYear = birthYear;
        this.medicalRecords = new ArrayList<>(); // 빈 리스트로 초기화
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }
    //  고객별로 진료기록을 등록
    public void addMedicalRecords(MedicalRecord records) {
        medicalRecords.add(records);
    }
}
```

```java
public class MedicalRecord {
    private String phoneNumber;
    private String date;
    private String content;

    public MedicalRecord() {
    }

    public MedicalRecord(String phoneNumber, String date, String content) {
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.content = content;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
```

<br/>

### View

- ConsoleView

```java
import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private Scanner scanner = new Scanner(System.in);

    // 전화번호를 입력받는 화면
    public String getPhoneNumber() {
        System.out.print("전화번호를 입력하세요:");
        return scanner.nextLine();
    }
    // 신규고객정보 입력 화면
    public Customer getCustomerInfo() {
        System.out.println("신규 고객 정보를 입력하세요.");
        System.out.print("전화번호:");
        String phoneNumber = scanner.nextLine();
        System.out.print("소유주 이름:");
        String ownerName = scanner.nextLine();
        System.out.print("동물 이름:");
        String petName = scanner.nextLine();
        System.out.print("주소:");
        String address = scanner.nextLine();
        System.out.print("종류:");
        String species = scanner.nextLine();
        System.out.print("출생년도(yyyy):");
        int birthYear = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()
        return new Customer(phoneNumber, ownerName, petName, address, species, birthYear);
    }
    // 진료기록을 입력 받는 화면
    public MedicalRecord getMedicalRecordInfo() {
        System.out.print("진료일을 입력하세요:");
        String date = scanner.nextLine();

        System.out.print("진료내용을 입력하세요:");
        String content = scanner.nextLine();

        return new MedicalRecord(null, date, content);
    }
    // 진료기록 조회하여 출력
    public void printMedicalRecordInfo(Customer customer) {
        List<MedicalRecord> records = customer.getMedicalRecords();
        System.out.println("[" + customer.getPetName() + "]의 진료기록");
        for (MedicalRecord record : records) {
            System.out.println("- 진료일: " + record.getDate());
            System.out.println("  진료내용: " + record.getContent());
            System.out.println("  소유주 이름: " + customer.getOwnerName());
            System.out.println("  동물 이름: " + customer.getPetName());
            System.out.println("  주소: " + customer.getAddress());
            System.out.println("  종류: " + customer.getSpecies());
            System.out.println("  출생년도: " + customer.getBirthYear());
        }
    }
    public void printMessage(String message) {
        System.out.println(message);
    }
}
```

<br/>

### Controller

- CustomerController
- MedicalRecordController

```java
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    private List<Customer> customers;
    private MedicalRecordController recordController; // 진료기록을 관리하는 컨트롤러

    public CustomerController(MedicalRecordController recordController){
        this.customers = new ArrayList<>();
        this.recordController = recordController;
    }

    // 고객정보를 등록하는 메서드
    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    // 고객정보를 삭제하는 메서드(+반드시 해당 고객의 진료기록도 함께 삭제)
    public void removeCustomer(String phoneNumber) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getPhoneNumber().equals(phoneNumber)) {
                customers.remove(i);
                recordController.removeMedicalRecord(phoneNumber); // 해당 고객의 진료 기록 삭제
                break;
            }
        }
    }

    // 고객등록 여부를 확인하는 메서드
    public Customer findCustomer(String phoneNumber) {
        for (Customer customer : customers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return customer;
            }
        }
        return null;
    }

    // 기존의 전화번호로 등록된 고객이 있는지를 확인하는 메서드(중복)
    public boolean isPhoneNumberExist(String phoneNumber) {
        for (Customer customer : customers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

}
```

```java
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordController {
    private List<MedicalRecord> records = new ArrayList<>();

    // 진료 기록을 등록하는 메서드
    public void addMedicalRecord(MedicalRecord record){
        records.add(record);
    }

    // 진료기록을 삭제하는 메서드
    public void removeMedicalRecord(String phoneNumber) {
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getPhoneNumber().equals(phoneNumber)) {
                records.remove(i);
                break;
            }
        }
    }

    // 전화번호에 해당하는 모든 진료기록을 검색하여 새로운 List<MedicalRecord>를 만들어주는 메서드
    public List<MedicalRecord> findMedicalRecords(String phoneNumber) {
        List<MedicalRecord> result = new ArrayList<>();
        for (MedicalRecord record : records) {
            if (record.getPhoneNumber().equals(phoneNumber)) {
                result.add(record);
            }
        }
        return result;
    }
}
```

<br/>

### Main

```java
package kr.pet.mvc;

import java.util.List;
import java.util.Scanner;

public class PetMain {
    public static void main(String[] args) {
        MedicalRecordController recordController = new MedicalRecordController();
        CustomerController customerController = new CustomerController(recordController);
        ConsoleView view = new ConsoleView();
        while (true) {
            System.out.println("===애완동물진료관리시스템===");
            System.out.println("1. 신규 고객 정보 입력");
            System.out.println("2. 진료 기록 저장");
            System.out.println("3. 진료 기록 조회");
            System.out.println("4. 진료 기록 삭제");
            System.out.println("5. 종료");
            System.out.print("원하는 기능을 선택하세요:");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt(); // 1~5
            switch (choice) {
                case 1:
                    Customer newCustomer = view.getCustomerInfo();
                    String phoneNumber = newCustomer.getPhoneNumber();
                    if (customerController.isPhoneNumberExist(phoneNumber)) {
                        view.printMessage("이미 등록된 전화번호입니다.");
                        continue;
                    }
                    customerController.addCustomer(newCustomer);
                    view.printMessage("고객 정보가 추가되었습니다.");
                    break;

                case 2:
                    phoneNumber = view.getPhoneNumber();
                    if (customerController.findCustomer(phoneNumber) == null) {
                        view.printMessage("해당 전화번호를 가진 고객 정보가 없습니다.");
                        break;
                    }
                    Customer customer = customerController.findCustomer(phoneNumber);
                    MedicalRecord newRecord = view.getMedicalRecordInfo();
                    newRecord.setPhoneNumber(phoneNumber);
                    recordController.addMedicalRecord(newRecord);
                    customer.addMedicalRecords(newRecord);
                    view.printMessage("진료기록이 저장되었습니다.");
                    break;

                case 3:
                    phoneNumber = view.getPhoneNumber();
                    List<MedicalRecord> records = recordController.findMedicalRecords(phoneNumber);
                    if (records.isEmpty()) {
                        view.printMessage("해당 전화번호를 가진 진료 기록이 없습니다.");
                        break;
                    }
                    customer = customerController.findCustomer(phoneNumber);
                    view.printMedicalRecordInfo(customer);
                    break;

                case 4:
                    phoneNumber = view.getPhoneNumber();
                    if (customerController.findCustomer(phoneNumber) == null) {
                        view.printMessage("해당 전화번호를 가진 고객 정보가 없습니다.");
                        break;
                    }

                    recordController.removeMedicalRecord(phoneNumber);
                    view.printMessage("진료기록 정보가 삭제되었습니다.");
                    break;

                case 5:
                    System.out.println("프로그램을 종료합니다.");
                    return;

                default:
                    System.out.println("잘못된 선택입니다.");
                    break;
            }
        }
    }
}
```

<br/>

> Reference
>
> Fastcampus : Signature Backend