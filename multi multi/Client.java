import java.net.*;  
import java.io.*;  
import java.util.*;  
public class Client{
	public static void main(String args[]) throws Exception{
		//main
		//data members
		Client obj = new Client();
		Socket s=new Socket("localhost",6666); // connection established with the server
		// creating reading thread which will take server input
		new Thread(new Reader(s)).start();
		//try1
		System.out.println("Waiting for other user to join");
		
	try{ 	// telling user about the id
		System.out.println("Enter your ID:");
		obj.sendServerMessage(s);
		System.out.println(""); // for new line (console formatting)
		
	}catch(Exception e){
		e.printStackTrace();
	}
	// will allow to chat with multip users
	while(true){	 
	// telling server to chat with the user 
	System.out.println("Enter ID to chat with");
	try{
		obj.sendServerMessage(s);
		System.out.println("");
	}
	catch(Exception e){
		e.printStackTrace();
	}
	}
	}//end of main
	
	// methods for the class
	public String sendServerMessage(Socket s) throws Exception{
	//taking input from user 
	Input inp = new Input();
	String id = inp.inp.nextLine();
	//now notifying server about the id
	DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
	dout.writeUTF(id);  
	
	return id;
	}
}