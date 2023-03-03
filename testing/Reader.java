import java.util.Scanner;
import java.io.*;  
import java.net.*;
public class Reader implements Runnable{
	static Socket s;
	public Reader(Socket s){
		this.s = s;
	}
	public Reader(){
		
	}
	public void run(){
			new Reader().read();
	}
	static synchronized void read()
	{
		
		try{
		DataInputStream din=new DataInputStream(s.getInputStream());   
		while(true){
		String str2=din.readUTF();  
		System.out.println("Message Received: "+str2);  
		}
		}
		catch(Exception e){
			
		}
		
	}
}