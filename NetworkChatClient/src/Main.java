import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main
{
	private static final int	LISTEN_PORT	= 4321;

	public static void main(String[] args)
	{
		
		 java.awt.EventQueue.invokeLater(new Runnable() {
             public void run() {
		MainWindow theApp = new MainWindow();
		theApp.setVisible(true);
             }
		 });
	}
}
