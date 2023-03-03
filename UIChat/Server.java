import java.net.*;  
import java.io.*;  
public class Server{
	public static void main(String args[]) throws Exception {
		ServerSocket ss=new ServerSocket(3333);  
		Socket s=ss.accept(); 	
		Socket s1 = ss.accept();
		// threading between two clients
		new Thread(new ChatWorker(s,s1)).start();
	}
}
class ChatWorker implements Runnable{
	Socket s1,s2;
	public ChatWorker(Socket s1,Socket s2){this.s1=s1;this.s2=s2;}
	public ChatWorker(){}
	public void run(){
		new Thread(new ServerListener(s1,s2)).start();
		new Thread(new ServerListener(s2,s1)).start();
	}
}
class ServerListener implements Runnable{
	Socket s1,s2; //s1 be reader and s2 be writer
	public ServerListener(Socket s1,Socket s2){this.s1=s1;this.s2=s2;}
	public ServerListener(){}
	public void run(){
		try{
		DataInputStream din=new DataInputStream(s1.getInputStream());
		DataOutputStream dout=new DataOutputStream(s2.getOutputStream()); 
		while(true){
				dout.writeUTF(din.readUTF());  
				dout.flush();   				
			}
		}
		catch(Exception e){}
	}
}