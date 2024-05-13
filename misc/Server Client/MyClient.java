import java.net.*;  
import java.io.*;  
class MyClient{  
public static void main(String args[])throws Exception{  
Socket s=new Socket("192.168.36.243",3333);   
	new Thread(new Reader(s)).start();
  new Thread(new Writer(s)).start(); 
}}  