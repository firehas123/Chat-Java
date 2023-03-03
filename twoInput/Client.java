import java.net.*;  
import java.io.*;  
import java.util.*;
public class Client{
	public static void main(String args[]) throws Exception{
		Socket s=new Socket("localhost",6666);
		/*Client obj = new Client();
		new Thread(new test(s)).start();
		*/
		try{
			DataInputStream dis=new DataInputStream(s.getInputStream());
		String  msg1 =(String)dis.readUTF(); 
		System.out.println(msg1);
		}catch(Exception e){}
		try{
			DataInputStream dis=new DataInputStream(s.getInputStream());
		String  msg1 =(String)dis.readUTF(); 
		System.out.println(msg1);
		}catch(Exception e){}
	}
	public void msg(Socket s){
		try{
			DataInputStream dis=new DataInputStream(s.getInputStream());
		String  msg1 =(String)dis.readUTF(); 
		System.out.println(msg1);
		}catch(Exception e){}
	}
}
 class test implements Runnable{
	Socket s;
	public test(){}
	public test(Socket s){this.s = s;}
	public void run(){
	
			try{
			DataInputStream dis=new DataInputStream(s.getInputStream());
		String  msg1 =(String)dis.readUTF(); 
		System.out.println("Client 1 msg");
		System.out.println(msg1);
		System.out.println("Client 2 msg");
		}catch(Exception e){}
		
	}
}