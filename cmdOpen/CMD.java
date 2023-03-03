import java.io.*;
import java.util.*; 
public class CMD{
	public static void main(String args[]) throws Exception{
		boolean chk = true;
		Scanner sc = new Scanner(System.in);
		String input="";
		while(chk){
			System.out.println("Do you want to open another terminal window?");
			input = sc.nextLine();
			System.out.println("");
			if(input.equals("yes"))
			{
				//open another window
				System.out.println("Opening a new window");
				Runtime rt = Runtime.getRuntime();
				Runtime.getRuntime().exec(new String[]{"cmd", "/k", "start", "cmd"});
			}
			else{
				//terminate the proragm
				chk = false;
			}
		}
	}
}