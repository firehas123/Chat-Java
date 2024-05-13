import java.io.*;  
import java.util.*;  
public class MyThread {
public static void main(String args[]){
	boolean chk = true;
	Thread t1= new Thread(new ThreadTesting(chk));
	t1.start();
 System.out.println("main thread Continuing..............................");
 try{Thread.sleep(2000);}catch(Exception e){}
 t1.stop();
 chk = false;	
}
}
class ThreadTesting implements Runnable{
	boolean chk = true;
	public ThreadTesting(){}
	public ThreadTesting(boolean chk){this.chk=chk;}
	public void run(){
		while(chk)
			System.out.println("test");
	}
}