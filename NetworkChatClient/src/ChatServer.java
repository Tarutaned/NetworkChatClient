import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer
{
	private int LISTEN_PORT = 4321;
	private int TOTAL_CONNECTIONS = 0;
	
	public ChatServer()
	{
		ServerSocket s;
		try
		{
			s = new ServerSocket(LISTEN_PORT);
			while (true)
			{
				Socket incoming = s.accept();
				new ChatWindow(incoming);
				TOTAL_CONNECTIONS++;

			} // end while
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
