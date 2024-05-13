import java.net.*;  
import java.io.*;  
import java.util.*;
public class Server{
	public static void main(String args[]) throws Exception{
		ServerSocket ss=new ServerSocket(6666);
		Socket s=ss.accept();
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());     
		dout.writeUTF("Start Chat");
		dout.flush();
		System.out.println("msg 1 sent");
		try{
			Thread.sleep(0000);
		}
		catch(Exception e){}
		dout.writeUTF("end chat");
		dout.flush();  
dout.writeUTF("end chat1");
		dout.flush();	
dout.writeUTF("end cha2");
		dout.flush();	
dout.writeUTF("end cha3");
		dout.flush();	
dout.writeUTF("end chat4");
		dout.flush();	
dout.writeUTF("end chat5");
		dout.flush();		
	}
}