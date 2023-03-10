package mysql_jdbc;

import java.util.Scanner;

public class DeleteTest extends DBConn{
	
	Scanner scan = new Scanner(System.in);
	SelectTest st = new SelectTest();
	
	public DeleteTest() {
		
	}
	
	public void start() {
		// 삭제할 회원번호를 입력받아 회원삭제
		try {
			st.start();
			System.out.print("삭제할 회원번호 : ");
			int mem_id = scan.nextInt();
			
			getConn();
			
			sql = "delete from member where mem_id=?";
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_id);
			
			int result = pstmt.executeUpdate();
			
			if(result>0) {
				System.out.println(mem_id+"번 회원 삭제완료");
			}else {
				System.out.println("회원삭제 실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			getClose();
		}
	}
	
	public static void main(String[] args) {
		new DeleteTest().start();
	}
}
