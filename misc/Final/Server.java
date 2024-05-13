import java.net.*;  
import java.io.*;  
import java.util.*;  
									// SERVER CLASS		
public class Server{
	ArrayList <Info> data = new ArrayList <Info> (); // array list for storing data
	//main method
	public static void main(String args[]) throws Exception{
		Server obj = new Server();
		ServerSocket ss=new ServerSocket(6666);
		String UserID="";
		while(true){
			Socket s=ss.accept();//establishes connection
			//taking id and storing it with the associated socket
			UserID = obj.getUserID(s);
			// broadcasting to all users
			// as new user has joined and others have to know 
			obj.broadcastToAllUsersConnected();
			//creating a thread that will handle user to user communication
			new Thread(new ClientThread(obj,s,UserID)).start();
		}
	}
	public void broadcastToAllUsersConnected(){
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
	
	public String getUserID(Socket s) throws Exception{
	DataInputStream dis=new DataInputStream(s.getInputStream());
	String  id=(String)dis.readUTF(); 
	data.add(new Info(id,s.getPort(),s));
	return id;
	} 
	
}

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
			//take input from the user that what are his intentions?	
			String inputFromUser = getInput();
			if(inputFromUser.equals("Chat")){
				// user wants to send a message to another user
				
				//first getting ID of the receiver to whom message is to be sent
				String ReceiverID =  getInput();
				// starting GUI on the receiver side
				sendReceiverMessageToOpenGUI(getSocket(ReceiverID));
			}
		 }
	 }
	 public void sendReceiverMessageToOpenGUI(Socket s){
		 try{
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());   	  
		dout.writeUTF("Start Chat");  
		dout.flush();   
		}
		catch(Exception e){
			
		}
	 }
	 public Socket getSocket(String ID){
		 Socket s = new Socket();
		 for(int i=0 ; true ;i++){
			 if(obj.data.get(i).id.equals(ID))
				return  obj.data.get(i).s; 
		 }
		
	 }
	 public String getInput(){
		String str2 = "";
		 try{
		DataInputStream din=new DataInputStream(s.getInputStream());   
		str2=din.readUTF();  
				
		}
		catch(Exception e){
			
		}
		return str2;
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