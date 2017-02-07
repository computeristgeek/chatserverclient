import java.io.*;
import java.net.*;
public class echo3 extends Thread {
	//static server because only one socket per server should be running
	//for the same port
	private static ServerSocket echoServer = null;
	//max number of allowed users on this server (number is arbitrary)
	private static int MAX_USERS=1000;
	//
	private static PrintWriter arrPS[] = new PrintWriter[MAX_USERS];
	//state of the users' connections
	private static boolean arrConnected[] = new boolean[MAX_USERS];
	//the user index
	private int user_num;

	//the constructor takes a number per thread per instance
	public echo3(int user_num)
	{
		this.user_num=user_num;
	}

	//attempts to listen on port 8085
	//creates an array of threads of echo3 with number of MAX_USERS
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
	//each thread runs this
	public void run()
	{
		String line;
		BufferedReader is;
		PrintWriter os;
		Socket clientSocket = null;
		try
		{
			//initialize thread/user as disconnected
			arrConnected[user_num]=false;
			//wait until a connection is made, get its socket
			clientSocket = echoServer.accept();
			//convert clientSocket's input stream into bufferedreader
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			//set array of Print Writer Output Streams to client's outputstream
			arrPS[user_num] = new PrintWriter(clientSocket.getOutputStream());
			//set thread/user as connected
			arrConnected[user_num]=true;
			//print user number and ip to server's terminal
			System.out.println("User no."+user_num+" Has Joined");
			System.out.println("IP:"+clientSocket.getInetAddress());
			//loop over all connected users and tell them that this user has joined
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
				//sleep for 0.5 a second
				try{sleep(500);}catch(Exception ex){System.out.println(ex.getMessage());}
				//read from client's inputstream a line
				line = is.readLine();
				for(int i=0;i<MAX_USERS;i++)
				{
					//if the line contains data, broadcast
					if(line!=null && arrConnected[i])
					{
						arrPS[i].println("User no."+user_num+":"+line);
						arrPS[i].flush();
					}
					//else if the line is null and the user is still noted as connected
					//broadcast that he left
					else if(line==null && arrConnected[i])
					{
						arrPS[i].println("User no."+user_num+" Has Exited");
						arrPS[i].flush();
					}
				}
				//internally tell the server what the user said
				if(line!=null)
					System.out.println("User no."+user_num+":"+line);
				//if disconnected, tell the server and wait for another user
				else
				{
					System.out.println("User no."+user_num+" Has Exited");
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
