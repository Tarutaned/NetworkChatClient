import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer
{
	ServerSocket server;
	boolean running = true;
	private int LISTEN_PORT = 4321;
	private int TOTAL_CONNECTIONS = 0;
	
	public void Start()
	{
		running=true;
		while(running)
		{
		try
		{
			server = new ServerSocket(LISTEN_PORT);
			while (true)
			{
				Socket incoming = server.accept();
				new ChatWindow(incoming);
				TOTAL_CONNECTIONS++;
			} // end while
		}
		catch (IOException e)
		{
			//e.printStackTrace();
			// if the port is in use, try the next one
			LISTEN_PORT++;
		}
		}	// end while(running)
	}// end constructor
	
	public void Stop()
	{
		running = false;
	}
	
	public int getPort()
	{
		return LISTEN_PORT;
	}
	public int getNumConnections()
	{
		return TOTAL_CONNECTIONS;
	}
}// end class
