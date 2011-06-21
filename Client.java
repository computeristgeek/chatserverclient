//Tuesday June 21st 2011

import javax.swing.*;
import java.lang.*;
import java.io.*;
import java.net.*;

public class Client {

	public static Socket skt = null;
	chat frame;
	//PrintWriter pw = null;
	public Client(chat frame)
	{
	     try {
		String ip=JOptionPane.showInputDialog(null,"Please enter the IP");
		if(ip==null || ip.equals("null"))
		 skt = new Socket("192.168.0.32", 8085);
		else skt = new Socket(ip, 8085);
		 BufferedReader in = new BufferedReader(new
		    InputStreamReader(skt.getInputStream()));

		this.frame=frame;
		frame.chat_form();
		frame.enter_value_in();
		String message="";
		while(!message.equals("exit"))
		{
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
			PrintWriter pw=null;
			try{pw=new PrintWriter(skt.getOutputStream());}catch(Exception ex){System.out.println(ex.getMessage());}
			pw.println(message);
			pw.flush();
		}
		catch(Exception ex){System.out.println(ex.getMessage());}
	}

}
