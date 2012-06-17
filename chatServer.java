import java.io.*;
import java.net.*;
import javax.swing.*;
public class chatServer extends JApplet implements Runnable {
	private static ServerSocket echoServer = null;
	private static int MAX_USERS=1000;
	private static PrintStream arrPS[] = new PrintStream[MAX_USERS];
	private static boolean arrConnected[] = new boolean[MAX_USERS];
	private static Thread arr[]=new Thread[MAX_USERS];
	private static int curr_num=0;
	private int user_num;

	public void init() {
		try
		{
			this.user_num=curr_num;
			curr_num++;
			echoServer = new ServerSocket(8086);

			for(int i=0;i<MAX_USERS;i++)
			{
				arr[i]=new Thread(this);
				arr[i].start();
			}
		}
		catch (IOException e)
		{
			System.out.println(e);
		}   

	}
	public void run()
	{
		String line;
		DataInputStream is;
		PrintStream os;
		Socket clientSocket = null;
		try
		{
			arrConnected[user_num]=false;
			clientSocket = echoServer.accept();
			is = new DataInputStream(clientSocket.getInputStream());
			arrPS[user_num] = new PrintStream(clientSocket.getOutputStream());
			arrConnected[user_num]=true;
			for(int i=0;i<MAX_USERS;i++)
			{
				if(arrConnected[i])
					arrPS[i].println("User no."+user_num+" Has Joined");
			}
			while (true)
			{
				try{arr[user_num].sleep(500);}catch(Exception ex){System.out.println(ex.getMessage());}
				line = is.readLine();
				for(int i=0;i<MAX_USERS;i++)
				{
					if(line!=null && arrConnected[i])
						arrPS[i].println("User no."+user_num+":"+line);
					else if(line==null && arrConnected[i])
						arrPS[i].println("User no."+user_num+" Has Exited");
				}
				if(line!=null)
					System.out.println("User no."+user_num+":"+line);
				else
				{
					arrConnected[user_num]=false;
					clientSocket = echoServer.accept();
					is = new DataInputStream(clientSocket.getInputStream());
					arrPS[user_num] = new PrintStream(clientSocket.getOutputStream());
					arrConnected[user_num]=true;
				}
				try{arr[user_num].sleep(500);}catch(Exception ex){System.out.println(ex.getMessage());}
			}
		}   
		catch (IOException e)
		{
			arrConnected[user_num]=false;
			System.out.println(e);
		}
	}
}
