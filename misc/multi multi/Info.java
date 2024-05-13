import java.net.*;  
import java.io.*;  
import java.util.*;  
//class for storing information
public class Info{
	public int  pn;
	public String id;
	public String chatWith;
	public Socket s;
	boolean chatOn; // to check if user is chatting with someone else or not
	//constructor
	public Info(){}
	public Info(String i,int p,Socket ss){
		id= i;
		pn= p;
		s =ss;
		chatOn=false;
	}
}