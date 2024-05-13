import java.net.*;  
import java.io.*;  
public class Client{
	public static void main(String args[])throws Exception{
		Socket s=new Socket("localhost",3333);
		new UIchat().chat(s);
	}
}