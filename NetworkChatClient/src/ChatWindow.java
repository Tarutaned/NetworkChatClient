import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.net.Socket;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ChatWindow extends JFrame
{
	private static final long	serialVersionUID		= 1L;
	private GridBagConstraints	CONSTRAINTS;
	private JLabel				lblHostName, lblPort;
	private JTextField			txtComputerName, txtPort;
	private JTextPane			txtDisplayArea;
	private Style				style;
	private StyledDocument		doc;
	private JTextField			txtInputArea;
	private JButton				btnConnect;
	private JButton				btnDisconnect;
	private JScrollPane			jsp;
	private CommSocket			theSocket;
	private boolean				running					= true;
	private boolean				INCOMMING_CONNECTION	= true;

	// =========================================
	// Constructor
	// =========================================
	public ChatWindow()
	{
		super("Martins Demo");

		// kill it on close
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// display the window for this chat
		displayWindow();

		// this will crash our app if input is received and buffered !!!!!
		// copied from:
		// http://stackoverflow.com/questions/9413656/java-how-to-use-timer-class-to-call-a-method-do-something-reset-timer-repeat
		final ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();
		service.scheduleWithFixedDelay(new Runnable()
		{
			@Override
			public void run()
			{
				if (running)
				{
					socketListener();
				}
			}
		}, 0, 200, TimeUnit.MILLISECONDS);

	} // end constructor

	// =========================================
	// Constructor - incoming connection
	// =========================================
	public ChatWindow(Socket i)
	{
		super("Incoming Connection");
		theSocket = new CommSocket(i);

		// kill it on close
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		INCOMMING_CONNECTION = false;
		// display the window for this chat
		displayWindow();

		writeLine("Accepting Connection from: " + i.getInetAddress(), 0);
		// this will crash our app if input is received and buffered !!!!!
		// copied from:
		// http://stackoverflow.com/questions/9413656/java-how-to-use-timer-class-to-call-a-method-do-something-reset-timer-repeat
		final ScheduledExecutorService service = Executors
				.newSingleThreadScheduledExecutor();
		service.scheduleWithFixedDelay(new Runnable()
		{
			@Override
			public void run()
			{
				if (running)
				{
					socketListener();
				}
			}
		}, 0, 200, TimeUnit.MILLISECONDS);

	}

	// =========================================
	// displayWindow()
	// puts the window up on screen and handles events
	// =========================================
	@SuppressWarnings("serial")
	public void displayWindow()
	{

		this.setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel(new GridBagLayout());
		// mainPanel.setBorder(BorderFactory.createLineBorder(Color.red));

		CONSTRAINTS = new GridBagConstraints();
		CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;

		lblHostName = new JLabel("Host Name:");
		lblPort = new JLabel("Port Number:");
		txtComputerName = new JTextField();
		txtPort = new JTextField();
		txtDisplayArea = new JTextPane();
		txtInputArea = new JTextField();
		btnConnect = new JButton("Connect");
		btnDisconnect = new JButton("Disconnect");

		style = txtDisplayArea.addStyle("ChatWindowStyle", null);
		StyleConstants.setForeground(style, Color.black);
		doc = txtDisplayArea.getStyledDocument();
		txtDisplayArea.setBounds(0, 0, 200, 700);

		if (INCOMMING_CONNECTION)
		{
			// label Computer Name
			lblHostName.setToolTipText("The Computer Name");
			CONSTRAINTS.gridx = 0;
			CONSTRAINTS.gridy = 0;
			mainPanel.add(lblHostName, CONSTRAINTS);

			// text Computer Name
			txtComputerName.setColumns(12);
			txtComputerName.setText("localhost");
			txtComputerName.setToolTipText("Enter the Computer Name");
			CONSTRAINTS.gridx = 1;
			CONSTRAINTS.gridy = 0;
			mainPanel.add(txtComputerName, CONSTRAINTS);

			// label Port
			lblPort.setToolTipText("The port number");
			CONSTRAINTS.gridx = 0;
			CONSTRAINTS.gridy = 1;
			mainPanel.add(lblPort, CONSTRAINTS);

			// text Port
			txtPort.setColumns(12);
			txtPort.setText("4321");
			txtPort.setToolTipText("Enter the Port number");
			CONSTRAINTS.gridx = 1;
			CONSTRAINTS.gridy = 1;
			mainPanel.add(txtPort, CONSTRAINTS);

			// buttons
			CONSTRAINTS.gridx = 2;
			CONSTRAINTS.gridy = 0;

			mainPanel.add(btnConnect, CONSTRAINTS);
		} // end if(INCOMMING_CONNECTION)

		CONSTRAINTS.gridx = 2;
		CONSTRAINTS.gridy = 1;
		mainPanel.add(btnDisconnect, CONSTRAINTS);

		// JTextPane ==>> Display Area <<==
		txtDisplayArea.setEditable(false);

		CONSTRAINTS.gridx = 0;
		CONSTRAINTS.gridy = 2;
		CONSTRAINTS.gridwidth = 3;
		CONSTRAINTS.gridheight = 1;
		// CONSTRAINTS.ipady = 0;
		// CONSTRAINTS.ipadx = 0;

		// txtDisplayArea.setBorder(BorderFactory.createLineBorder(Color.red));

		// setting the size was a bitch to figure out !!!
		// @SuppressWarnings("serial")
		jsp = new JScrollPane(txtDisplayArea)
		{
			@Override
			public Dimension getPreferredSize()
			{
				return new Dimension(385, 500);
			}
		};
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// jsp.setBorder(BorderFactory.createLineBorder(Color.green));

		mainPanel.add(jsp, CONSTRAINTS);

		// text - Input Area
		CONSTRAINTS.gridx = 0;
		CONSTRAINTS.gridy = 7;
		CONSTRAINTS.gridwidth = 3;
		CONSTRAINTS.gridheight = 1;
		CONSTRAINTS.ipady = 0;
		CONSTRAINTS.ipadx = 0;
		// CONSTRAINTS.anchor = GridBagConstraints.PAGE_END;
		mainPanel.add(txtInputArea, CONSTRAINTS);

		// user entered text and pressed enter
		txtInputArea.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				sendMessage();
			}
		});

		// the Connect button was pressed
		btnConnect.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				connect();
			}
		});

		// the Disconnect button was pressed
		btnDisconnect.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				disconnect();
			}
		});

		// mainPanel.setBorder(new EmptyBorder(5,5,5,5));
		this.add(mainPanel, BorderLayout.CENTER);
		this.pack();
		this.setSize(400, 600);

		this.setResizable(false);
		this.setVisible(true);
		txtInputArea.requestFocusInWindow();

		// =============
		// copied the next part directly from:
		// http://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html
		// Make textField get the focus whenever frame is activated.
		this.addWindowFocusListener(new WindowAdapter()
		{
			public void windowGainedFocus(WindowEvent e)
			{
				txtInputArea.requestFocusInWindow();
			}
		});
		// =============

	} // end displayWindow()

	// =========================================
	// writeLine()
	// =========================================
	public void writeLine(String theString, int color)
	{
		/*
		 * 0 = system! 1 = User Input 2 = Socket Input 3 = Other
		 */
		StyleContext context = new StyleContext();
		style = context.getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setFontSize(style, 14);
		StyleConstants.setSpaceAbove(style, 4);
		StyleConstants.setSpaceBelow(style, 4);

		switch (color)
		{
		case 0:
			StyleConstants.setForeground(style, Color.GRAY);
			break;
		case 1:
			StyleConstants.setForeground(style, Color.BLACK);
			break;
		case 2:
			StyleConstants.setForeground(style, Color.BLUE);
			break;
		default:
			StyleConstants.setForeground(style, Color.RED);
			break;
		}

		theString = theString + "\n";

		try
		{

			doc.insertString(doc.getLength(), theString, style);

		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}

		// scroll to the bottom
		txtDisplayArea.setCaretPosition(txtDisplayArea.getDocument()
				.getLength());

	} // end writeLine()

	// =========================================
	// connect()
	// =========================================
	private void connect()
	{
		String remoteHostName = txtComputerName.getText();
		int remoteHostPort = Integer.parseInt(txtPort.getText());
		if (remoteHostName == null || remoteHostName.equals(""))
		{
			writeLine("Enter a Computer Name", 0);
		}
		else
		{
			if (remoteHostPort <= 0)
			{
				writeLine("Enter a Port", 0);
			}
			else
			{
				// we can now connect
				// set our cursor to notify the user that they might need to
				// wait
				writeLine("Connecting to: " + remoteHostName + " : "
						+ remoteHostPort, 0);
				this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				// lets try to connect to the remote server

				theSocket = new CommSocket(remoteHostName, remoteHostPort);

				if (theSocket.connect())
				{
					writeLine(
							"We are successfuly connected to: "
									+ theSocket.getRemoteHostIP(), 0);
					writeLine("On Port: " + theSocket.getRemoteHostPort(), 0);
					running = true;
					// socketListener();
				}
				else
				{
					writeLine("We failed to connect!", 0);

				}

				this.setCursor(Cursor
						.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	} // end connect

	// =========================================
	// Disconnect()
	// =========================================
	private void disconnect()
	{
		if (theSocket != null)
		{
			theSocket.transmit("Disconnecting");
			writeLine("Disconnecting...", 0);
			writeLine("Closing socket", 0);
			theSocket.close();
			theSocket = null;
		}
		if (theSocket == null)
		{
			writeLine("The Socket is null", 0);
		}
		else
		{
			writeLine("We have now disconnected", 0);
		}
		running = false;
	}

	// =========================================
	// sendMessage()
	// =========================================
	private void sendMessage()
	{
		if (theSocket != null)
		{
			if (theSocket.isReady())
			{
				writeLine(txtInputArea.getText(), 1);
				theSocket.transmit(txtInputArea.getText());
				// clear user input area
				txtInputArea.setText("");
			}
			else
			{
				// the CommSocket is not connected
				writeLine("We are not connected!", 0);
				writeLine("theSocket is not ready", 0);
				// clear user input area
				txtInputArea.setText("");
				disconnect(); // just in case

			}
		}
		else
		{
			// the socket = null
			writeLine("We are not connected!", 0);
			writeLine("theSocket = null", 0);
			txtInputArea.setText("");

		}
	} // end sendMessage

	// =========================================
	// socketListener()
	// =========================================
	private TimerTask socketListener()
	{
		String theMessage;
		if (theSocket != null)
		{
			if (theSocket.isReady())
			{
				// blocks execution
				// get theMessage
				theMessage = theSocket.getMessage();
				if (theMessage == null)
				{
					writeLine("Our connection has ended!", 0);
					running = false;
					// if the message = null
					// the we need to check the socket again
					// just in case
					// close it nicely if we can
					if (theSocket != null)
					{
						theSocket.close();
					}
					// otherwise just set it to null
					// set it to null regardless
					theSocket = null;
				}
				else
				{
					// if the socket is OK
					// and the socket isReady
					// then display the input
					writeLine(theMessage, 2);
				}
			}
		} // end if theSocket !=null
		return null;
	} // end socketListener()

} // end class

