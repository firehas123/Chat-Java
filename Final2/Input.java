import java.util.Scanner;

public class Input {
	public Scanner inp;
	public Input() {
		inp = new Scanner(System.in);
	}
	public void close(){
		inp.close();
	}
}