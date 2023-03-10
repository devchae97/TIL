package employees;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import module.EmpDataSet;
import module.EmpVO;

public class EmpStart {

	Scanner scan = new Scanner(System.in);
	
	public EmpStart() {
		// 현재 등록되어 있는 사원목록
		EmpDataSet.dataSet(); // 초기 데이터 세팅
		do {
			try {
				System.out.print(menu());
				int inMenu = Integer.parseInt(scan.nextLine());
				if(inMenu==6) {
					empStop();
				}else if(inMenu==5) {
					empDel();
				}else if(inMenu==4) {
					empEdit();
				}else if(inMenu==3) {
					empInsert();
				}else if(inMenu==2) {
					getNameSearch();
				}else if(inMenu==1) {
					empListOutPut();
				}else {
					throw new Exception();
				}
			}catch(Exception e) {
				System.out.println("메뉴 재입력");
			}
		}while(true);
	}
	
	// 사원목록
	public void empListOutPut() {
		titlePrint(); // 제목 출력
		// 맵의 모든 value(vo객체)를 구해 목록 출력
		Collection<EmpVO> emp = EmpDataSet.empList.values();
		Iterator<EmpVO> i = emp.iterator();
		
		while(i.hasNext()) {
			EmpVO v = i.next();
			System.out.println(v.toString());
		}
	}
	
	public void titlePrint() {
		System.out.println("사원번호\t사원명\t부서명\t연락처");
	}
	
	// 사원 검색
	public void getNameSearch() {
		System.out.print("검색할 사원명 입력 \n사원명 : ");
		String searchName = scan.nextLine();
		
		// vo
		Collection<EmpVO> list = EmpDataSet.empList.values();
		Iterator<EmpVO> ii = list.iterator();
		int cnt = 0;
		while(ii.hasNext()) {
			EmpVO v = ii.next();
			if(v.getEmpName().equals(searchName)) {
				System.out.println(v.toString());
				cnt++;
			}
		}
		System.out.println(cnt + "명 검색완료");
	}
	
	// 사원등록
	public void empInsert() {
		EmpVO vo = new EmpVO(); // 입력받은 사원정보를 저장할 vo
		System.out.print("사원번호 : ");
		vo.setEmpNo(Integer.parseInt(scan.nextLine()));
		System.out.print("사원명 : ");
		vo.setEmpName(scan.nextLine());
		System.out.print("부서명 : ");
		vo.setDepartment(scan.nextLine());
		System.out.print("전화번호 : ");
		vo.setTel(scan.nextLine());
		
		// 컬렉션에 vo 추가
		EmpDataSet.empList.put(vo.getEmpNo(), vo);
		System.out.println("사원정보가 등록되었습니다.");
	}
	
	// 사원수정
	public void empEdit() {
		// 어떤 사원을 수정할 것인지 입력받기
		System.out.print("수정할 사원의 사원번호 입력 \n사원번호 : ");
		int editEmpNo = Integer.parseInt(scan.nextLine());
		System.out.print("수정을 원하는 항목 선택 \n[1.부서명, 2.연락처]\n항목선택 : ");
		String editMenu = scan.nextLine();
		
		if(editMenu.equals("1")) {
			departmentEdit(editEmpNo);
		}else if(editMenu.equals("2")) {
			telEdit(editEmpNo);
		}else {
			System.out.println("항목 재선택");
		}
	}
	
	// 부서명수정 메소드
	public void departmentEdit(int empNo) {
		EmpVO vo = EmpDataSet.empList.get(empNo);
		System.out.print("새로운 부서명 입력/n부서명 : ");
		String editDepartName = scan.nextLine();
		vo.setDepartment(editDepartName); // 부서명 변경
		System.out.println(vo.getEmpName() + "님의 부서명을 " + vo.getDepartment() + "로 변경완료");
	}
	
	// 연락처수정 메소드
	public void telEdit(int empNo) {
		EmpVO vo = EmpDataSet.empList.get(empNo);
		System.out.print("새로운 연락처를 입력\n연락처 : ");
		vo.setTel(scan.nextLine());
		System.out.println(vo.getEmpName() + "님의 연락처를 " + vo.getTel() + "로 변경완료");
	}
	
	// 사원삭제
	public void empDel() {
		System.out.print("삭제할 사원의 사원번호를 입력\n사원번호 : ");
		int empNo = Integer.parseInt(scan.nextLine());
		EmpDataSet.empList.remove(empNo); // 객체 지워짐
		System.out.println(empNo + "번 사원의 정보 삭제완료");
	}
	
	// 종료
	public void empStop() {
		try {
			// 사원정보가 있는 empList를 파일로 저장 후 종료
			File f = new File("d://testFolder","employee.txt");
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(EmpDataSet.empList);
			oos.close();
			fos.close();
			
		}catch(Exception e) {
			System.out.println("저장 실패" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String menu() {
		return "MENU [1.사원목록, 2.사원검색, 3.사원등록, 4.사원수정, 5.사원삭제, 6.종료]\nMENU 선택 : ";
	}
	
	public static void main(String[] args) {
		new EmpStart();
	}
}
