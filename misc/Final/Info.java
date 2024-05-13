import java.net.*;  
import java.io.*;  
import java.util.*;  
//class for storing information
public class Info{
	public String id;
	public Socket s;
	//constructor
	public Info(){}
	public Info(String i,Socket ss){
		id= i;
		s =ss;
	}
}