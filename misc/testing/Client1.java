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
	class Client1{  
	//data members
	
	public static void main(String args[])throws Exception{  
	Client1 clasObj = new Client1();
	Socket s=new Socket("localhost",6666);
	String IDtoChatWith="";
		//try1
	try{ 	// telling user about the id
		System.out.println("Enter your ID:");
		clasObj.sendServerMessage(s);
		System.out.println(""); // for new line (console formatting)
		
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
	
	
	//receving request from the server
	// create a thread that will wait for server input
	Thread t1= new Thread(new ServerListener());
	t1.start();
	// 
	boolean ThreadChk = true;
	while(true){
		//try2
	try{	// telling the id of the user to chat with 
		System.out.println("Enter ID to chat with:");
		IDtoChatWith = clasObj.sendServerMessage(s);
		System.out.println(""); // for new line (console formatting)
	}
	catch(Exception e){e.printStackTrace();}
	//opening UI
	clasObj.openUI(s,IDtoChatWith);
	//UI code END
	if(ThreadChk){ //checking if thread already created or not?
		//try3
	try{	//chatting started
	ThreadChk = false; // thread created don't need to create anymore
		new Thread(new Reader(s)).start();
		new Thread(new Writer(s)).start(); 
	}
	catch(Exception e){
		e.printStackTrace();
		}
	}
		
		else{
			//no users availble
			Thread.sleep(15000);
			System.out.println("Waiting for users to connect retrying after 15 seconds");
		}
		
	}
	
	}
	
	public void openUI(Socket s ,String ID){
		//data members
	JFrame frame;
	 JTextField messageBox  = new JTextField();
	 JScrollPane scrollPane;
	JTextArea displayMessage = new JTextArea();
		//code
		frame = new JFrame(ID);
		frame.setVisible(true);
		frame.setBounds(100, 100, 450, 376);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		submitButton.setForeground(Color.BLACK);
		submitButton.setBackground(Color.GRAY);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//code to write for socket programming
				new Thread(new ClientRead(s,displayMessage)).start();
				new Thread(new ClientWrite(s,messageBox,displayMessage)).start();
			}
		});
		submitButton.setBounds(331, 305, 103, 32);
		frame.getContentPane().add(submitButton);
		
		messageBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					
					//code to write for socket programming
					new Thread(new ClientRead(s,displayMessage)).start();
					new Thread(new ClientWrite(s,messageBox,displayMessage)).start();
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
		//
	}
	public String sendServerMessage(Socket s) throws Exception{
	//taking input from user 
	MyScanner inp = new MyScanner();
	String id = inp.obScanner.nextLine();
	//now notifying server about the id
	DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
	dout.writeUTF(id);  
	
	return id;
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
		//while(true){
		String str2=din.readUTF();  
		textArea.append("Receiver: "+str2+"\n");
		//}
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
	public ClientWrite(){}
	public ClientWrite(Socket s,JTextField msgbx,JTextArea textArea){this.s = s; this.textArea= textArea;this.msgbx=msgbx;}
	public void run(){
		try{
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());   
		//while(true){
		String str=msgbx.getText();  
		textArea.append("you: "+str+"\n");
		msgbx.setText("");
		dout.writeUTF(str);  
		dout.flush();   
		//}
		}
		catch(Exception e){
			
		}
		/*String displayMessageTextString = messageBox.getText();
					displayMessage.append(displayMessageTextString+"\n");
					messageBox.setText("");*/
	}
}