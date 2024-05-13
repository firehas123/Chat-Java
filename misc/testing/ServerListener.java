import java.net.*;  
import java.io.*;  
import java.util.*; 
class ServerListener implements Runnable{
	//data members
	Socket s;
	//constructor
	public ServerListener(Socket s){this.s = s;}
	public ServerListener (){} //default constructor
	public void run(){
		boolean chk = true;
		while(chk)
		if(serverResponse(s).equals("proceed")){
			// server has pinged
			//run an thread that will create 
		}
		
	}
	public String serverResponse(Socket ss){
		String str ="";
		try{
		DataInputStream din=new DataInputStream(s.getInputStream());   
		str=din.readUTF();  
		
		}
		catch(Exception e){	
		}
		return str;
	}
}