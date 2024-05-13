import java.util.*;
import java.io.*;  
import java.net.*;
public class StartChat implements Runnable{
	Server obj;
	String ID1;
	String ID2;
	//constructor
	public StartChat(){}
	public StartChat(Server obj,String id1,String id2){
		this.obj=obj;
		this.ID1=id1;
		this.ID2=id2;
	}
	public void run(){
		// first getting sockets of both users
		Socket s1 = getSocketByID(ID1);
		Socket s2 = getSocketByID(ID2);
		//notifying both users
		openGUI(s1,ID2);
		openGUI(s2,ID1);
		//then creating thread for both users
		try{
		new Thread(new ChatWorkerReader(s1,s2)).start(); //c1 reader thread
		System.out.println("Start Chat printing two lines");
		System.out.println("S1: "+s1.getPort());
		System.out.println("S2: "+s2.getPort());
		new Thread(new ChatWorkerReader(s2,s1)).start(); // c2 reader thread
	}
	catch(Exception e){
		// meaning chat has ended 
		}
		// thread end 
	}
	public Socket getSocketByID(String ID){
		
		for(int i=0;i<obj.data.size();i++){
			if(ID.equals(obj.data.get(i).id))
				return obj.data.get(i).s;
		}
		return new Socket(); //this line will never execute haha jokes on you
	}
	public void openGUI(Socket s,String receiverID){ // we'll open GUI  on client side by sending client a msg
		try{
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());     
		dout.writeUTF("Start Chat");
		dout.writeUTF(receiverID);
		dout.flush();   
		}
		catch(Exception e){
			
		}
	}
}

// reader class for both users
class ChatWorkerReader implements Runnable{
	//data memebers
	static Socket reader;
	static Socket writer;
	//constructor
	public ChatWorkerReader(Socket s1,Socket s2){
		this.reader = s1;
		this.writer = s2;
	}
	public ChatWorkerReader(){}
	
	//method
	public void run(){
		new ChatWorkerReader().read();	
	}
	static void read()
	{
		try{
			
		DataInputStream din=new DataInputStream(reader.getInputStream());   
		DataOutputStream dout=new DataOutputStream(writer.getOutputStream());   
		while(true){
		String str=din.readUTF();  
		dout.writeUTF(str); 
		}
		}
		catch(Exception e){
			System.out.println("Exception in chatWorker Read method");
		}
		System.out.println("Leaving chat worker class");
	
	}		
	
}
