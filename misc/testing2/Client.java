import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Client {

public static void main(String[] args)throws IOException {
try {
         String[] cmdArray = new String[2];
         // the program to open
         

         // txt file to open with notepad
         

         File dir = new File("c:/");

         String[] envArray = new String[2];
         envArray[0]="";
         envArray[1]="";
         
         Process process = Runtime.getRuntime().exec(cmdArray, envArray, dir);

      } catch (Exception ex) {
         ex.printStackTrace();
      }
}
}