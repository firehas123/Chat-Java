import java.util.Scanner;

public class MyScanner {
	Scanner obScanner;
	public MyScanner() {
		obScanner = new Scanner(System.in);
	}
  static class inputClass{
	  public static String getString() {
		  return new MyScanner().obScanner.nextLine();
	  }
	  public static int getInt() {
		  return new MyScanner().obScanner.nextInt();
	  }
  }
}
