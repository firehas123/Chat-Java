import java.util.*; 
public class TestJava{
	public static void main(String args[]){
		TestJava classObj = new TestJava();
		classObj.input();
		classObj.input();
	}
	public void input(){
		Scanner inp = new Scanner(System.in);
		String s = inp.nextLine();
		System.out.println(s);
		inp.close();
	}
}