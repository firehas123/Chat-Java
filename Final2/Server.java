import java.net.*;  
import java.io.*;  
import java.util.*;  
public class Server{
	public static void main(String argsp[]) throws Exception{
		ArrayList <Info> data = new ArrayList <Info> (); // array list for storing data
		ServerSocket ss=new ServerSocket(6666); 
		while(true){
			Socket s=ss.accept();//establishes connection
			System.out.println("port number : "+s.getPort());
			new Thread(new ClientThread(data,s)).start();
		}
	}
}

class ClientThread implements Runnable{
	//data members
	Socket s;
	ArrayList <Info> data;
	//constructors
	public ClientThread(){}
	public ClientThread(ArrayList <Info> data,Socket s){this.data= data;this.s=s;}
	//methods
	public void run(){
		String ID = addDataofTheUser(); // first getting ID and adding it to the arraylist
		System.out.println("New User joined :  " + ID);
		broadcast(); //broadcasting to everyone that a new user has joined
		new Thread(new ListenFromClient(s,data,ID)).start();
	}
	public String addDataofTheUser(){
		String str2 = "";
		try{
		
		DataInputStream din=new DataInputStream(s.getInputStream());   
		str2=din.readUTF(); 
		}
		catch(Exception e){}
		// after reading now adding it to the arraylist
		data.add(new Info(str2,s));
		return str2;
	}
	
	public void broadcast(){
		
		
		int n= data.size();
				for(int i=0;i<n;i++){
					//notifying users that a message is been broadcasted
					notifyingAllUsers(data.get(i).s);
					//message broadcasted
					sendMessagetoTheUser(data.get(i).s,data);
				}
	}
	
	public void notifyingAllUsers(Socket s){
		try{
			DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
			dout.writeUTF("Broadcasting");
			dout.flush(); 			
		}catch(Exception e){}
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

class ListenFromClient implements Runnable{
	//data members
	Socket s;
	ArrayList <Info> data;
	String SenderID = "";
	//constructors
	public ListenFromClient(){}
	public ListenFromClient(Socket s,ArrayList <Info> data,String id){this.s=s;this.data=data;this.SenderID=id;}
	//methods
	public void run(){
		String input = "";
		while(true){
			input = takeInput();
			System.out.println("Server taking input from :"+SenderID+ " Message: "+input);
			// now taking action accordingly
			if(input.equals("Chat")){
				//user wants to send msg to someone
				//getting ID of the receiver
				String ID = "";
				ID = takeInput();
				System.out.println("Server taking input from :"+SenderID+ " Message: "+ID);
				//getting message to be sent to the user
				String Message = "";
				Message = takeInput();
				System.out.println("Server taking input from :"+SenderID+ " Message: "+Message);
				SendMessageToTheReceiver(ID,Message);
			}
			else if(input.equals("Invoke User")){
				// notifying the respective user
				// first getting the id
				// of the user to alerting
				String ReceiverID = takeInput();
				System.out.println("Server taking input from :"+SenderID+ " Message: "+input +" in else if section");
				System.out.println("ReceiverID : "+ ReceiverID);
				Socket ReceiverSocket = getSocketByID(ReceiverID);
				System.out.println("Port Number: "+ ReceiverSocket.getPort());
				AlertUser(ReceiverSocket);
			}
			else{
				System.out.println("Invalid Input from the user");
			}
		}
	}
	
	public void AlertUser(Socket sss){
		//sending the ID to the receiver
		try{
				DataOutputStream dout=new DataOutputStream(sss.getOutputStream());
				dout.writeUTF("Invoke User");
				dout.writeUTF(SenderID);
				dout.flush();   
			}catch(Exception e){}
	}
	
	public void SendMessageToTheReceiver(String ID,String Msg){
		Socket ReceiverSocket  = getSocketByID(ID);
		SendMessage(ReceiverSocket,Msg);
	}
	
	public Socket getSocketByID(String ID){
		for(int i=0;true;i++){
			if(ID.equals(data.get(i).id))
				return data.get(i).s;
		}
	}
	
	public void SendMessage(Socket s,String Msg){
		//alerting the respective user
		try{
			DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
			dout.writeUTF("Receiving Message"); 
			dout.flush(); 
		}catch(Exception e){}
		// message from?
		try{
			DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
			dout.writeUTF(SenderID); 
			dout.flush(); 
		}catch(Exception e){}
		//sending the message
		try{
			DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
			dout.writeUTF(Msg); 
			dout.flush(); 
		}catch(Exception e){}
	}
	
	public String takeInput(){
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