import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Server {
    public static void main(String[] args) throws IOException{
       for(int i=0;i<2;i++){
		   System.out.println("value at index "+i+" is "+args[0]);
	   }  
    }
}