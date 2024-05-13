import java.net.*;  
import java.io.*;  
import java.util.*;  
public class Server{
	//data member
	ArrayList <Info> data = new ArrayList <Info> (); // array list for storing data
	//main method
	public static void main(String args[]) throws Exception{
		Server obj = new Server();
		ServerSocket ss=new ServerSocket(6666);
		
		while(true){
			Socket s=ss.accept();//establishes connection
			System.out.println("socket details: " + s.getPort());
			String id = obj.fetchData(s);//getting id from the user
			// now informing others about the updated users list
			new Thread(new UsersCheck(obj)).start();
			new Thread(new ClientThread(obj,s,id)).start();
		}
	}
	// Server class Method
	public String fetchData(Socket s) throws Exception{
	DataInputStream dis=new DataInputStream(s.getInputStream());
	String  id=(String)dis.readUTF(); 
	data.add(new Info(id,s.getPort(),s));
	return id;
	}
}

 // class for managing user connected with the server
 class UsersCheck implements Runnable{ // we are going to broadcast the users info
	ArrayList <Info> data;
	 public UsersCheck(){} // default constructor
	 public UsersCheck(Server obj){
		this.data=obj.data;
	 }
	 public void run(){
				//new user has joined hence display it
				System.out.println("New User has Joined");
				int n= data.size();
				for(int i=0;i<n;i++){
					sendMessagetoTheUser(data.get(i).s,data);
				}	
			}
	 public void sendMessagetoTheUser(Socket s,ArrayList <Info> data){
		 try{
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());    
		String str=" New Users Online\n";
		for(int i=0;i<data.size();i++){
		str = str + data.get(i).id + "\n";
		}
		dout.writeUTF(str);  
		dout.flush();   
		}
		catch(Exception e){
			System.out.println("Exception in sendMessagetoTheUser method on server side");
			e.printStackTrace();
		}
	 }
 }
 // client thread creation 
 class ClientThread implements Runnable{
	 Server obj;
	 Socket s;
	 String id;
	 public ClientThread (){}
	 public ClientThread(Server obj,Socket UserSocket,String id){
		 this.obj = obj;
		 this.s = UserSocket;
		 this.id = id;
	 }
	 public void run(){
		 while(true){
			// to whom user wants to chat with?
			String idtoChatWith = getUserIDtoChatWith();
			// start a thread that will open GUI on both clients
			
			System.out.println("Debugging statement,  ID1 : "+this.id+" ID2: "+idtoChatWith);
			
			new Thread(new StartChat(obj,this.id,idtoChatWith)).start();
		 }
	 }
	 public String getUserIDtoChatWith(){
		 String str2 = "";
		 try{
		DataInputStream din=new DataInputStream(s.getInputStream());   
		str2=din.readUTF();  
				
		}
		catch(Exception e){
			
		}
		return str2;
	}
}
  
  
  
  
  class sleep{
	  public void soja(int time){
		   try{Thread.sleep(time*1000);}catch(Exception e){}
	  }
  }