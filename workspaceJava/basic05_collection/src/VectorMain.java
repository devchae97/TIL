import java.util.Calendar;
import java.util.Vector;

public class VectorMain {

	public VectorMain() {
		VectorTest vt = new VectorTest();
		Vector v = vt.getData();
		
		// 컬렉션의 객체 얻어오기
		int d1 = (int)v.get(0);
		Calendar d2 = (Calendar)v.elementAt(1);
		System.out.println("num = " + d1);
		System.out.println("Calendar = " + d2);
		
		ConsoleCalendarOOP d3 = (ConsoleCalendarOOP)v.lastElement();
		d3.startCalendar();
		
		String name = (String)VectorTest.vv.get(2);
		System.out.println("name = " + name);
	}
	
	public static void main(String[] args) {
		new VectorMain();
	}
}
