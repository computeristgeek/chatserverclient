import javax.swing.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class chat extends JFrame{
	JTextField t=new JTextField(150);
	JButton b=new JButton("enter");
	JTextArea tf=new JTextArea();
	String m="" ;
	boolean flag;
	public String s="";
	public static int x;
	Client client;
	
	public void chat_form(){

		tf.setEditable(false);
		tf.setSelectedTextColor(Color.CYAN);
		JPanel p2=new JPanel();
		p2.setLayout(new BorderLayout(5,10));
		p2.add(t,BorderLayout.CENTER);
		p2.add(b,BorderLayout.EAST);
		setLayout(new BorderLayout(5,10));
		add(tf,BorderLayout.CENTER);
		add(p2,BorderLayout.SOUTH);
		
	}
	public void enter_value_out(){
		m=m+s+"\n";
		tf.setText(m);
		s="";
	}
	public void enter_value_in(){
		ActionListener w =new enter();
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
			String temp = t.getText();
			client.sendMessage(temp);
			//tf.setText(m);
			t.setText("");
		}
	}
	   public static void main(String args[]) {

		chat c = new chat();
	   }

}
