import java.util.Scanner;
import java.io.*;  
import java.net.*;
public class Writer implements Runnable{
	static Socket s;
	public Writer(Socket s){
		this.s = s;
	}
	public Writer(){
		
	}
	public void run(){
		new Writer().write();
	}
	static synchronized void write()
	{
		try{
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());   
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); 
		while(true){
		String str=br.readLine();  
		dout.writeUTF(str);  
		dout.flush();   
		}
		}
		catch(Exception e){
			
		}
	}
}