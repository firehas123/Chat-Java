import java.net.*;  
import java.io.*;  
import java.util.*;
import javax.swing.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import org.w3c.dom.events.EventTarget;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
										//MAIN CLASS
public class Client{
	public static void main(String args[]) throws Exception{
		//main
		//data members
		Socket s=new Socket("localhost",6666); // connection established with the server
		Input obj = new Input(); // Scanner class for taking input
		String ChatWith=""; // for taking input from the user to whom he/she wants to chat with
		String myId="";
		boolean ClientMainLoop =  true;
		//sending server my ID;
		System.out.println("Enter your ID");
		myId = obj.inp.nextLine();
		// ****** sendig server my ID ********
		try{
			DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
			dout.writeUTF(myId); 
		}catch(Exception e){}
		// ***************
		
		while(ClientMainLoop){
			//starting a thread that will listen to the server 
			new Thread(new ListenFromServer(s)).start(); // thread that will listen from the sever
			//taking input from the user
			try{
				Thread.sleep(1000);
			}
			catch(Exception e){}
			System.out.println("Enter User ID you want to chat with");
			ChatWith = obj.inp.nextLine();
			if(ChatWith.equals("end"))
				ClientMainLoop = false;
			else //starting GUI  that will inititate chat between both users
				new Thread(new OpenGUI(s,ChatWith));
			
		}
		
	}
}

class ListenFromServer implements Runnable{
	//data memebers
	Socket s;
	//constructors
	public ListenFromServer(){}
	public ListenFromServer(Socket s){this.s=s;}
	//methods
	public void run(){
		try{
		DataInputStream din=new DataInputStream(s.getInputStream());   
		String str2=din.readUTF(); 
		if(str2.equals("Start Chat")){ // server has pinged us to start our chat
		//starting chat
		//getting reciver ID
		String receiverID = din.readUTF();
		new Thread(new OpenGUI(s,receiverID)).start();
		}
		else{
		//message broadcast  so just display msg
		System.out.println(str2);
		}
		
		}catch(Exception e){}
	}
}

class OpenGUI implements Runnable{
	//data members
	Socket s;	
	String ReceiverID;
	//constructors
	public OpenGUI(){}
	public OpenGUI(Socket s,String receiverID){this.s =s; this.ReceiverID=receiverID;}
	//methods
	public void run(){
		//GUI opening method copied
	//start of the GUI method
		//data members
	JFrame frame;
	 JTextField messageBox  = new JTextField();
	 JScrollPane scrollPane;
	JTextArea displayMessage = new JTextArea();
		//code
		frame = new JFrame(ReceiverID);
		frame.setVisible(true);
		frame.setBounds(100, 100, 450, 376);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//
		new Thread(new ClientRead(s,displayMessage)).start();
		//
		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		submitButton.setForeground(Color.BLACK);
		submitButton.setBackground(Color.GRAY);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//sending messae
				new Thread(new ClientWrite(s,messageBox,displayMessage,ReceiverID)).start();
			}
		});
		submitButton.setBounds(331, 305, 103, 32);
		frame.getContentPane().add(submitButton);
		
		messageBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					//sending messae
					new Thread(new ClientWrite(s,messageBox,displayMessage,ReceiverID)).start();
				}
			}
		});
		messageBox.setBounds(0, 306, 333, 31);
		frame.getContentPane().add(messageBox);
		messageBox.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 434, 305);
		frame.getContentPane().add(scrollPane);
		
		
		displayMessage.setEditable(false);
		scrollPane.setViewportView(displayMessage);	

		//end of the GUI method
	}

	
}

class ClientRead implements Runnable{
	//data members
	Socket s;
	JTextArea textArea;
	public ClientRead(){}
	public ClientRead(Socket s,JTextArea textArea){this.s = s; this.textArea= textArea;}
	public void run(){
		try{
		DataInputStream din=new DataInputStream(s.getInputStream());   
		while(true){
		String str2=din.readUTF();  
		textArea.append("Receiver: "+str2+"\n");
		}
		}
		catch(Exception e){
			
		}
	}
}

 class ClientWrite implements Runnable{
	//data members
	Socket s;
	JTextArea textArea;
	JTextField msgbx;
	String receiverID;
	public ClientWrite(){}
	public ClientWrite(Socket s,JTextField msgbx,JTextArea textArea,String receiverID){this.s = s; this.textArea= textArea;this.msgbx=msgbx;this.receiverID=receiverID;}
	public void run(){
		try{
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
		dout.writeUTF("Chat");
		dout.writeUTF(receiverID);
		String str=msgbx.getText();  
		textArea.append("you: "+str+"\n");
		msgbx.setText("");
		dout.writeUTF(str);  
		
		dout.flush();   
		
		}
		catch(Exception e){
			
		}
		/*String displayMessageTextString = messageBox.getText();
					displayMessage.append(displayMessageTextString+"\n");
					messageBox.setText("");*/
	}
}

