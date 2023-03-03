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
										
public class Client {
	//main function 
	public static void main(String argsp[]) throws Exception{
		Socket s=new Socket("localhost",6666); // connection established with the server
		//creating a thread that will listen to server all the time and will act accordingly
		new Thread(new DecisionMaker(s)).start();
		//destruction of the socket upon ending the program should be written here
	}
	//main end
}

class DecisionMaker implements Runnable{
	//data members
	Socket s;
	ArrayList <ClientGUI> obj;
	String myID = "";
	Input inp = new Input();
	//constructors
	public DecisionMaker(){}
	public DecisionMaker(Socket s){this.s = s; obj = new ArrayList <ClientGUI> ();}
	//methods
	public void run(){
		//first sending server my ID
		myID = sendServerMyID(inp);
		new Thread(new ReceivingMessagesFromServer(obj,s)).start(); // this will receive msgs from server
		new Thread(new UserToChatWith(obj,s,inp)).start();
	}

	public String sendServerMyID(Input inp){
		String id;
		System.out.println("Enter your ID: ");
		id = inp.inp.nextLine();
		try{
			DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
			dout.writeUTF(id); 
		}catch(Exception e){}

		return id;		
	}
	
}

// class for storing clientGUI information 

class ClientGUI{
	public GUI obj;
	public String ReceiverID;
}

class ReceivingMessagesFromServer implements Runnable{
	//data members
	ArrayList <ClientGUI> obj;
	Socket MySocket;
	//constructors
	public ReceivingMessagesFromServer(){}
	public ReceivingMessagesFromServer(ArrayList <ClientGUI> obj, Socket s){this.obj=obj;this.MySocket=s;}
	//methods
	public void run(){
		String s = "";
		while(true){
		s = getInputFromServer();
		System.out.println("Client getting messages from server : "+ s);
			if(s.equals("Broadcasting")){
				// server is wants to send us a message
				ListenFromServer();
			}
			else if(s.equals("Receiving Message")){
				//getting Senders ID
				String SenderID = getInputFromServer();
				System.out.println("Client getting messages from server : "+ SenderID);
				//getting message
				String msg  = getInputFromServer();
				System.out.println("Client getting messages from server : "+ msg);
				//sending the message to the respective GUI
				SendingMessageToTheRespectiveGUI(SenderID,msg);
			}
			else if(s.equals("Invoke User")){
				//someone wants to chat with us hence open the GUI
				// first getting the receiver ID
				String SenderID = getInputFromServer();
				System.out.println("Client getting messages from server : "+ SenderID);
				
				//GUI code
				GUI chatClass = new GUI(MySocket,SenderID);
			    ClientGUI GUIobj = new ClientGUI();
				GUIobj.obj = chatClass.getInstance();
				GUIobj.ReceiverID = SenderID;
				obj.add(GUIobj);
				Thread t1 = new Thread(chatClass);
				t1.start();
				// GUI code end
			}
		}
	}
	
	public void SendingMessageToTheRespectiveGUI(String id,String msg){
		int n = obj.size();
		for(int i=0;i<n;i++){
			if(id.equals(obj.get(i).ReceiverID)){
				obj.get(i).obj.displayMessage.append(id+": "+msg+"\n");
			}
		}
	}
	
	public void ListenFromServer(){
		try{
		DataInputStream din=new DataInputStream(MySocket.getInputStream());   
		String str2=din.readUTF(); 
		System.out.println(str2);
		}
		catch(Exception e){}
	}
	
	public String getInputFromServer(){
		String str2 = "";
		try{
		DataInputStream din=new DataInputStream(MySocket.getInputStream());   
		str2=din.readUTF(); 
		}
		catch(Exception e){}
		return str2;
	}
	
}

class UserToChatWith implements Runnable{
	//data members
	ArrayList <ClientGUI> obj;
	Socket s;
	Input inp;
	//constructors
	public UserToChatWith(){}
	public UserToChatWith(ArrayList <ClientGUI> obj,Socket s,Input inp){this.obj=obj;this.s=s;this.inp = inp;}
	//methods 
	public void run(){
		
		while(true){
			System.out.println("Enter ID to chat With: ");
			
			String IDtoChatWith = inp.inp.nextLine();
			
			//notifying the respective user
			try{
				DataOutputStream dout=new DataOutputStream(s.getOutputStream());
				dout.writeUTF("Invoke User"); 
				dout.writeUTF(IDtoChatWith);
				dout.flush();   
			}catch(Exception e){
				
			} 
	
			GUI chatClass = new GUI(s,IDtoChatWith);
			ClientGUI GUIobj = new ClientGUI();
			GUIobj.obj = chatClass.getInstance();
			GUIobj.ReceiverID = IDtoChatWith;
			obj.add(GUIobj);
			Thread t1 = new Thread(chatClass);
			t1.start();
		}
	}
}

class GUI implements Runnable{
	Socket s;	
	public String ReceiverID;
	public JTextArea displayMessage;
	public GUI(){}
	public GUI(Socket s,String ID){this.s=s;this.ReceiverID=ID;}
	public void run(){
		//GUI opening method copied
	//start of the GUI method
		//data members
	JFrame frame;
	 JTextField messageBox  = new JTextField();
	 JScrollPane scrollPane;
	 displayMessage = new JTextArea();
		//code
		frame = new JFrame(ReceiverID);
		frame.setVisible(true);
		frame.setBounds(100, 100, 450, 376);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//
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
	
	public GUI getInstance(){
		return this;
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
			System.out.println("Exception caught in ClientWrite");
			e.printStackTrace();
			System.out.println("Receiver ID : "+receiverID);
		}
		/*String displayMessageTextString = messageBox.getText();
					displayMessage.append(displayMessageTextString+"\n");
					messageBox.setText("");*/
	}
}