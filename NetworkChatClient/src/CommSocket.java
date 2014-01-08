import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class CommSocket
{
	private Socket			clientSocket	= null;
	private PrintWriter		out				= null;
	private BufferedReader	in				= null;
	private String			serverName;
	private int				serverPort;
	private String			name;
	private boolean			isReady;

	// =========================================
	// constructors
	// =========================================
	public CommSocket(String serverName, int serverPort)
	{
		this.serverName = serverName;
		this.serverPort = serverPort;
	}

	public CommSocket(Socket i)
	{
		clientSocket = i;
		isReady = true;
	}

	// =========================================
	// isReady()
	// =========================================
	public boolean isReady()
	{
		if (clientSocket != null)
		{
			if (clientSocket.isConnected())
			{
				// System.out.println("COMMSOCKET = Ready");
				// it's ready
				return isReady;
			}
		}
		// else return false
		// System.out.println("COMMSOCKET = NOT Ready");
		return isReady;

	} // end isReady()

	// =========================================
	// close()
	// tries to shut things down nicely : )
	// =========================================
	public void close()
	{
		isReady = false;
		try
		{
			if (out != null)
			{
				out.close();
			}

			if (in != null)
			{
				in.close();
			}

			if (clientSocket != null)
			{
				clientSocket.close();
			}

			out = null;
			in = null;
			clientSocket = null;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	} // close()

	// =========================================
	// setOutputStream()
	// =========================================
	private boolean setOutputStream()
	{
		if (clientSocket != null)
		{
			if (clientSocket.isConnected())
			{
				try
				{
					out = new PrintWriter(clientSocket.getOutputStream(), true);
					// out.println("\nOutput Stream Established");
					return true;
				}
				catch (IOException e)
				{
					e.printStackTrace();
					return false;
				}
			} // end if
		}
		return false;
	} // end setOutputStream

	// =========================================
	// setInputStream()
	// =========================================
	private boolean setInputStream()
	{
		if (clientSocket != null)
		{
			try
			{
				in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));
				return true;

			}
			catch (IOException e)
			{

				e.printStackTrace();
				return false;
			}

		}
		return false;
	}

	// =========================================
	// connectTo(Server, Port)
	// =========================================
	public boolean connect()
	{
		try
		{
			clientSocket = new Socket(serverName, serverPort);
			serverName = clientSocket.getInetAddress().toString();
			isReady = true;
			return true;
		}
		catch (NumberFormatException e)
		{

			// e.printStackTrace();
		}
		catch (UnknownHostException e)
		{

			// e.printStackTrace();
		}
		catch (IOException e)
		{

			// e.printStackTrace();
		}
		return false;

	} // end connectTo()

	// =========================================
	// getMessage()
	// =========================================
	public String getMessage()
	{
		String returnString;
		if (in != null)
		{
			try
			{
				returnString = in.readLine();
				return returnString;
			}
			catch (IOException e)
			{
				// e.printStackTrace();
				// we need to kill the connection
				close();
				return null;
			}
		}
		else
		{
			setInputStream();
			return getMessage();
		}

	} // end getMessage()

	// =========================================
	// transmit()
	// =========================================
	public boolean transmit(String txMessage)
	{
		if (clientSocket != null)
		{
			if (out != null)
			{
				// just send the message
				out.println(txMessage);
				return true;
			}
			else
			{
				// start the outputstream
				setOutputStream();
				this.transmit(txMessage);
			}
		}

		return true;

	}

	// =========================================
	// Getters & Setters
	// =========================================
	public String getName()
	{
		return name;
	}

	public void setName(String theName)
	{
		name = theName;
	}

	public String getRemoteHostIP()
	{
		if (clientSocket != null) { return clientSocket.getInetAddress()
				.toString(); }
		return null;
	}

	public String getRemoteHostPort()
	{
		if (clientSocket != null) { return Integer.toString(clientSocket
				.getPort()); }
		return null;
	}

} // end class
