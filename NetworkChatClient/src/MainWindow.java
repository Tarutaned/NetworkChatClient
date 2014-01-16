import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

public class MainWindow extends JFrame implements WindowListener,
		ActionListener, KeyListener

{
	ChatServer cs;

	public MainWindow()
	{
		super("Network Chat Client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(createMenuBar());
		this.setContentPane(createContentPane());
		this.setResizable(false);
		this.pack();
		//this.setSize(400, 600);
		this.setVisible(true);
		
		cs = new ChatServer();
		
		
	}

	public Container createContentPane()
	{
		 JLabel				lblUserList, lblPort, lblConnections;
		 JTextField			txtComputerName, txtPort;
		 JScrollPane		jsp;
		 JButton			btnConnect;
		 Style				style;
		
		 JPanel mainPanel = new JPanel(new GridBagLayout());
		 GridBagConstraints CONSTRAINTS = new GridBagConstraints();
		 CONSTRAINTS.insets = new Insets(15,15,15,15);
		 
		
		 lblUserList = new JLabel("Contact List:");
		 mainPanel.add(lblUserList, CONSTRAINTS);
		
		
		
		
		return mainPanel;
	} // end displayWindow()

	private JMenuBar createMenuBar()
	{
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		JCheckBoxMenuItem cbMenuItem;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the file menu.
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

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

}
