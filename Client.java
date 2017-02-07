//Tuesday June 21st 2011

import javax.swing.*;
import java.lang.*;
import java.io.*;
import java.net.*;

public class Client {
	//single client socket for all instances of Client
	public static Socket skt = null;
	chat frame;
	//PrintWriter pw = null;
	//constructor needs GUI JFrame
	public Client(chat frame)
	{
	     try {
		//requires IP entry, if not defaults to 192.168.0.32
		String ip=JOptionPane.showInputDialog(null,"Please enter the IP");
		//attempts connection establishment on socket 8085
		if(ip==null || ip.equals("null"))
		 skt = new Socket("192.168.0.32", 8085);
		else skt = new Socket(ip, 8085);
		//creates a bufferedreader of the connection's input stream
		 BufferedReader in = new BufferedReader(new
		    InputStreamReader(skt.getInputStream()));
		//activates GUI frame
		this.frame=frame;
		frame.chat_form();
		frame.enter_value_in();
		String message="";
		//while the message is not exit, attempt sending message
		while(!message.equals("exit"))
		{
		//wait for the input stream reader to be ready
		 while (!in.ready()) {}
		 	frame.s=in.readLine(); // Read one line and output it
			System.out.println(frame.s);
			frame.enter_value_out();
		}
		 in.close();
	      }
	      catch(Exception e) {
		 System.out.print("Whoops! It didn't work!\n");
	      }
	}
	public static void sendMessage(String message)
	{
		try{
			//get output stream and send a message through
			PrintWriter pw=null;
			try{pw=new PrintWriter(skt.getOutputStream());}catch(Exception ex){System.out.println(ex.getMessage());}
			pw.println(message);
			pw.flush();
		}
		catch(Exception ex){System.out.println(ex.getMessage());}
	}

}
