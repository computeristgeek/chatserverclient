import java.io.*;
import java.net.*;
public class echo3 extends Thread {
	private static ServerSocket echoServer = null;
	private static int MAX_USERS=1000;
	private static PrintWriter arrPS[] = new PrintWriter[MAX_USERS];
	private static boolean arrConnected[] = new boolean[MAX_USERS];
	private int user_num;

	public echo3(int user_num)
	{
		this.user_num=user_num;
	}
	public static void main(String args[]) {
		try
		{
			echoServer = new ServerSocket(8085);
			echo3 arr[]=new echo3[MAX_USERS];
			for(int i=0;i<MAX_USERS;i++)
			{
				arr[i]=new echo3(i);
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
		BufferedReader is;
		PrintWriter os;
		Socket clientSocket = null;
		try
		{
			arrConnected[user_num]=false;
			clientSocket = echoServer.accept();
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			arrPS[user_num] = new PrintWriter(clientSocket.getOutputStream());
			arrConnected[user_num]=true;
			System.out.println("User no."+user_num+" Has Joined");
			System.out.println("IP:"+clientSocket.getInetAddress());
			for(int i=0;i<MAX_USERS;i++)
			{
				if(arrConnected[i])
				{
					arrPS[i].println("User no."+user_num+" Has Joined");
					arrPS[i].flush();
				}
			}
			while (true)
			{
				try{sleep(500);}catch(Exception ex){System.out.println(ex.getMessage());}
				line = is.readLine();
				for(int i=0;i<MAX_USERS;i++)
				{
					if(line!=null && arrConnected[i])
					{
						arrPS[i].println("User no."+user_num+":"+line);
						arrPS[i].flush();
					}
					else if(line==null && arrConnected[i])
					{
						arrPS[i].println("User no."+user_num+" Has Exited");
						arrPS[i].flush();
					}
				}
				if(line!=null)
					System.out.println("User no."+user_num+":"+line);
				else
				{
					arrConnected[user_num]=false;
					clientSocket = echoServer.accept();
					is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					arrPS[user_num] = new PrintWriter(clientSocket.getOutputStream());
					arrConnected[user_num]=true;
					System.out.println("User no."+user_num+" Has Joined");
					System.out.println("IP:"+clientSocket.getInetAddress());
					for(int i=0;i<MAX_USERS;i++)
					{
						if(arrConnected[i])
						{
							arrPS[i].println("User no."+user_num+" Has Joined");
							arrPS[i].flush();
						}
					}
				}
				try{sleep(500);}catch(Exception ex){System.out.println(ex.getMessage());}
			}
		}   
		catch (IOException e)
		{
			arrConnected[user_num]=false;
			System.out.println(e);
		}
	}
}
