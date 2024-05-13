import java.net.*;  
import java.io.*;  
class MyServer{  
public static void main(String args[])throws Exception{  
ServerSocket ss=new ServerSocket(3333);  
Socket s=ss.accept();  
  new Thread(new Reader(s)).start();
  new Thread(new Writer(s)).start();  
}
}  