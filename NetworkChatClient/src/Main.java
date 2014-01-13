import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main
{
	private static final int	LISTEN_PORT	= 4321;

	public static void main(String[] args)
	{
		//new ChatWindow();
		new MainWindow();

		int i = 1;
		ServerSocket s;
		try
		{
			s = new ServerSocket(LISTEN_PORT);
			while (true)
			{
				Socket incoming = s.accept();
				System.out.println("Spawning Incomming Connection " + i);
				new ChatWindow(incoming);
				i++;

			} // end while
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
