import java.net.*;  
import java.io.*;  
import java.util.*;  
public class ChatWorker implements Runnable{
	//data memebers
	Info user1,user2;
	boolean chk = false;
	int time; // time for checking if person is not available for 30 seconds then connection timed out
	//constructor
	public ChatWorker(){}
    public ChatWorker(Info obj1,Info obj2){
	 this.user1= obj1;
	 this.user2= obj2;
	}
	//methods
	public void run() {
		try{
			
			myRun(user1.s,user2.s);
		}
		catch(Exception e){
			// if exception caught then 
			System.out.println("Chat Ended");
		}
	}	
	public void myRun(Socket c1,Socket c2)throws Exception{
		//will communicate between both clients via the sockets given by the server
	try{
		new Thread(new ChatWorkerReader(c1,c2)).start(); //c1 reader thread
		new Thread(new ChatWorkerReader(c2,c1)).start(); // c2 reader thread
	}
	catch(Exception e){
		// meaning chat has ended 
		throw new Exception();
		}
	}
}
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
	static synchronized void read()
	{
		System.out.println("Entering chat worker class");
		try{
			
		DataInputStream din=new DataInputStream(reader.getInputStream());   
		DataOutputStream dout=new DataOutputStream(writer.getOutputStream());   
		while(true){
		String str=din.readUTF();  
		dout.writeUTF(str); 
		System.out.println("Reader Port "+reader.getPort ());
		System.out.println("Writer Port "+writer.getPort());
		System.out.println("Message is: "+str);
		}
		}
		catch(Exception e){
			System.out.println("Exception in chatWorker Read method");
		}
		System.out.println("Leaving chat worker class");
	
	}		
	
}