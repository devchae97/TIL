

public class For03 {
	
	public static void main(String[] args) {
		
		// break : 반복문의 실행을 중지
		
		for(int i=1;;i++) {
			System.out.println(i); // 1,2,3,4,5,6,7,8,9,10
			if(i>=10) {
				break;
			}
		}
		
		// continue : 반복문 내의 실행문을 한번 건너뛰기
		
		for(int i=1;i<=100;i++) {
			if(i>=10) {
				continue;
			}
			System.out.println(i); // 1,2,3,4,5,6,7,8,9
			
		}
	}
}
