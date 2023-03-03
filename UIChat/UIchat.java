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

public class UIchat {

public void chat(Socket s){
	//GUI opening method copied
	//start of the GUI method
	
		//data members
	JFrame frame;
	 JTextField messageBox  = new JTextField();
	 JScrollPane scrollPane;
	JTextArea displayMessage = new JTextArea();
		//code
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 450, 376);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//
		//new Thread(new ClientRead(s,displayMessage)).start();
		//
		JButton submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		submitButton.setForeground(Color.BLACK);
		submitButton.setBackground(Color.GRAY);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//code to write for socket programming
				
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
	
	//end of the GUI method
}

}


//both class started
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
	public ClientWrite(){}
	public ClientWrite(Socket s,JTextField msgbx,JTextArea textArea){this.s = s; this.textArea= textArea;this.msgbx=msgbx;}
	public void run(){
		try{
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());   
		
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
//both class ended
