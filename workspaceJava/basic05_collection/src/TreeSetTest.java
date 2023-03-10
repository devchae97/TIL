import java.util.Iterator;
import java.util.TreeSet;

public class TreeSetTest {

	public TreeSetTest() {
		
	}
	
	public void start() {
		// TreeSet : 입력순서를 유지하지 않는다
		//			 중복데이터를 허용하지 않는다
		//			 객체를 오름차순으로 정렬한다
		
		TreeSet<Integer> ts = new TreeSet<Integer> ();
		
		int[] data = {52,9,47,63,67,87,23,52,9,47,63,67};
		
		for(int d:data) {
			ts.add(d);
		}
		
		Iterator<Integer> ii = ts.iterator();
		while(!ii.hasNext()) {
			System.out.println();
		}		
	}
	
	public static void main(String[] args) {
		new TreeSetTest().start();
	}
}
