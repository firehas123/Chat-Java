import java.net.*;  
import java.io.*;  
import java.util.*;  
class Server{ 
//data members
 String receiverID="";
ArrayList <Info> data = new ArrayList <Info> ();
//main
public static void main(String args[])throws Exception{ 
Server obj1 = new Server();
ServerSocket ss=new ServerSocket(6666);
boolean outerLoop;
outerLoop= true;
int j=0;
while(outerLoop){
	Socket s=ss.accept();//establishes connection
	//taking id from the user
	obj1.fetchData(s);
	//fetching data to chat with
	obj1.data.get(j).chatWith=obj1.fetchDataToChatWith(s);
	boolean wait= true;
	System.out.println("Waiting for user to  come online "+obj1.data.get(j).chatWith);
	while(wait)
	{
		System.out.println("Users online are ");
		for (int i=0; i<obj1.data.size(); i++) 
		{ 
			System.out.println(obj1.data.get(i).id);
			if(obj1.data.get(i).id.equals(obj1.data.get(j).chatWith))
			{
				wait = false;
				break;
			}
		}
		if(wait){
		try{
			System.out.println("Connecting after 15 seconds");
			Thread.sleep(5000);  
		}
	catch(Exception e){
		
	}
		}
	}
	System.out.println(obj1.data.get(j).id+" Chatting with "+obj1.data.get(j).chatWith);
	j++;
	new Thread(new ChatWorker(obj1.data.get(j-1),obj1.getData(obj1.data.get(j-1).chatWith))).start(); // sending the ArrayList and the userID
	//pinging client that a user wants to chat with him
	/*code to write*/
}
}
//method
public void fetchData(Socket s) throws Exception{
	DataInputStream dis=new DataInputStream(s.getInputStream());
	String  id=(String)dis.readUTF(); 
	data.add(new Info(id,s.getPort(),s));
}
public String fetchDataToChatWith(Socket s) throws Exception{
	DataInputStream dis=new DataInputStream(s.getInputStream());
	String  id=(String)dis.readUTF(); 
	dis.close();
	return id;
}
public Info getData(String id){
	for(int i=0;i<data.size();i++){
		if(data.get(i).id.equals(id))
			return data.get(i);
	}
	Info obj = new Info();
	return obj;
}
public boolean DisplayMembersOnline(){
	//if no memebers are online return zero
	if(data.size()==0)
		return false;
	System.out.println("Data Members are: ");
	for(int i=0;i<data.size();i++){
		System.out.println(data.get(i).id);
	}
	return true;
}
}


