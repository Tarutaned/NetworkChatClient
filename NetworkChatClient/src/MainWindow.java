import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

public class MainWindow extends Frame implements WindowListener, ActionListener, KeyListener

{
	private JLabel				lblComputerName, lblPort;
	private JTextField			txtComputerName, txtPort;
	private JScrollPane			jsp;
	private JButton				btnConnect;
	private GridBagConstraints	CONSTRAINTS;
	private Style				style;

	
	public MainWindow()
	{
		
		super("Network Chat Client");
		displayWindow();
	}
	
	private void displayWindow()
	{
				JMenuBar menuBar;
				JMenu File;
				JMenuItem fmOpen, fmSave, fmConnect, fmDisconnect, fmExit;
				
				
		
		
		
		
		
		this.setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel(new GridBagLayout());
		// mainPanel.setBorder(BorderFactory.createLineBorder(Color.red));

		
		
		
		CONSTRAINTS = new GridBagConstraints();
		CONSTRAINTS.fill = GridBagConstraints.HORIZONTAL;
		//StyleConstants.setForeground(style, Color.black);
		// label Computer Name
		lblComputerName = new JLabel("Host Name:");
		lblComputerName.setToolTipText("The Computer Name");
		CONSTRAINTS.gridx = 0;
		CONSTRAINTS.gridy = 0;
		mainPanel.add(lblComputerName, CONSTRAINTS);

		// text Computer Name
		txtComputerName = new JTextField();
		txtComputerName.setColumns(12);
		txtComputerName.setText("localhost");
		txtComputerName.setToolTipText("Enter the Computer Name");
		CONSTRAINTS.gridx = 1;
		CONSTRAINTS.gridy = 0;
		mainPanel.add(txtComputerName, CONSTRAINTS);

		// label Port
		lblPort = new JLabel("Port Number:");
		lblPort.setToolTipText("The port number");
		CONSTRAINTS.gridx = 0;
		CONSTRAINTS.gridy = 1;
		mainPanel.add(lblPort, CONSTRAINTS);

		// text Port
		txtPort = new JTextField();
		txtPort.setColumns(12);
		txtPort.setText("4321");
		txtPort.setToolTipText("Enter the Port number");
		CONSTRAINTS.gridx = 1;
		CONSTRAINTS.gridy = 1;
		mainPanel.add(txtPort, CONSTRAINTS);

		// buttons
		btnConnect = new JButton("Connect");
		CONSTRAINTS.gridx = 2;
		CONSTRAINTS.gridy = 0;
		mainPanel.add(btnConnect, CONSTRAINTS);

		// mainPanel.setBorder(new EmptyBorder(5,5,5,5));
		
		this.add(mainPanel, BorderLayout.CENTER);
		this.pack();
		this.setSize(400, 600);

		this.setResizable(false);
		this.setVisible(true);

	} // end displayWindow()


	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		

	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		this.dispose();

	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	
	private JMenuBar createMenu()
	{
		 JMenuBar menuBar;
	        JMenu menu, submenu;
	        JMenuItem menuItem;
	        JRadioButtonMenuItem rbMenuItem;
	        JCheckBoxMenuItem cbMenuItem;
	 
	        //Create the menu bar.
	        menuBar = new JMenuBar();
	 
	        //Build the file menu.
	        menu = new JMenu("File");
	        menuBar.add(menu);
	       
	        // add items to the file menu
	        menuItem = new JMenuItem("New Connection");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        
	        menuItem = new JMenuItem("Disconnect");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	                        
	        menuItem = new JMenuItem("Exit");
	        menuItem.addActionListener(this);
	        menu.add(menuItem);
	        
	        return menuBar;
	}
	
	
	
}
