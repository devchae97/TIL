import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BufferedReaderTest {

	public BufferedReaderTest() {
		
	}
	
	public void start() {
		try {
			// BufferedReader : 버퍼에 입력문자를 저장 후 1줄씩 읽어올 수 있다.
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			
			// 읽은 데이터가 없을때 null 리턴
			String str = br.readLine();
			System.out.println(str);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new BufferedReaderTest().start();
	}
}
