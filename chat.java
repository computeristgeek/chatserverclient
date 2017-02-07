import javax.swing.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class chat extends JFrame{
	//textfield for sending
	JTextField t=new JTextField(150);
	//add an enter button
	JButton b=new JButton("enter");
	//large textarea for messages
	JTextArea tf=new JTextArea();
	String m="" ;
	boolean flag;
	public String s="";
	public static int x;
	Client client;
	
	public void chat_form(){
		//disable textarea,use CYAN
		tf.setEditable(false);
		tf.setSelectedTextColor(Color.CYAN);
		//sort into familiar messenger layout
		JPanel p2=new JPanel();
		p2.setLayout(new BorderLayout(5,10));
		p2.add(t,BorderLayout.CENTER);
		p2.add(b,BorderLayout.EAST);
		setLayout(new BorderLayout(5,10));
		add(tf,BorderLayout.CENTER);
		add(p2,BorderLayout.SOUTH);
		
	}
	public void enter_value_out(){
		//increment message with string s
		m=m+s+"\n";
		tf.setText(m);
		s="";
	}
	public void enter_value_in(){
		// create an object from enter
		ActionListener w =new enter();
		// wait for button press
		b.addActionListener(w);
	}
	public  chat(){
		setTitle("chat");
		setSize(500,500);
		setLocation(410,100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	 	client= new Client(this);
		client.sendMessage("something");
	}
	private class enter implements ActionListener{
		public void actionPerformed(ActionEvent e){
			//m= m + t.getText()+"\n";
			//get the text in textfield
			String temp = t.getText();
			//send the message through client
			client.sendMessage(temp);
			//tf.setText(m);
			//empty the textfield
			t.setText("");
		}
	}
	   public static void main(String args[]) {
		//create a chat window

		chat c = new chat();
	   }

}
