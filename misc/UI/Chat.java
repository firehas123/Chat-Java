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

public class Chat {

	public static void main(String[] args) {
		//data members
	JFrame frame;
	 JTextField messageBox  = new JTextField();
	 JScrollPane scrollPane;
	JTextArea displayMessage = new JTextArea();
		//code
		frame = new JFrame("hi");
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
				String displayMessageTextString = messageBox.getText();
				displayMessage.append(displayMessageTextString+"\n");
				messageBox.setText("");
			}
		});
		submitButton.setBounds(331, 305, 103, 32);
		frame.getContentPane().add(submitButton);
		
		messageBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					String displayMessageTextString = messageBox.getText();
					displayMessage.append(displayMessageTextString+"\n");
					messageBox.setText("");
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
	}


}
