import java.net.*;  
import java.io.*;  
import java.util.*;  

public class Driver{
	public static void main(String args[]){
		ArrayList  data = new ArrayList();
		new Thread(new Add(data)).start();
		new Thread(new disp(data)).start();
	}
}
class Threading implements Runnable{
//data members
ArrayList  data;
int val;
//constructors
public Threading(){}
public Threading(ArrayList  data,int val){this.data=data;this.val=val;}
//method
public void run(){
data.add(val);
}
}
class Add implements Runnable{
	ArrayList  data;
	public Add(){}
	public Add(ArrayList  data){this.data=data;}
	public void run(){
		for(int i=0;i<5;i++){
		 new Thread(new Threading(data,i)).start();
		}
	}
}
class disp implements Runnable{
	ArrayList data;
	public disp(){}
	public disp(ArrayList  data){this.data=data;}
	public void run(){
		try{
			Thread.sleep(1000);
		}catch(Exception e){}
		for(int i=0;i<5;i++)
			System.out.println("data : "+ data.get(i));
	}
}