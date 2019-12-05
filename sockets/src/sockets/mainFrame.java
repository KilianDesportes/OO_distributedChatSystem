package sockets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

public class mainFrame extends JFrame {

	private JMenuBar menuBar = new JMenuBar();
	private JMenu jmenu_options = new JMenu("Options");
	private JMenu jmenu_help = new JMenu("Help");

	private JMenuItem jmenuitem_pseudo = new JMenuItem("Change pseudo");
	private JMenuItem jmenuitem_disconnect = new JMenuItem("Disconnect");
	private JMenuItem jmenuitem_quit = new JMenuItem("Quit");
	private JMenuItem jmenuitem_refresh = new JMenuItem("Force Refresh");


	private JPanel jpanel_msgs = new JPanel();

	public mainFrame(){

		this.setSize(300,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		this.setLayout(new BorderLayout());


		String msg_test = "Salut, comment ca va aujourd'hui?";

		JScrollPane scrollPane = new JScrollPane(jpanel_msgs);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(50, 30, 300, 50);

		jpanel_msgs.add(new panelConversation("Michel",msg_test));

		jpanel_msgs.add(new JSeparator());

		jpanel_msgs.add(new panelConversation("Nom",msg_test));

		jpanel_msgs.add(new JSeparator());

		jpanel_msgs.add(new panelConversation("Jacques",msg_test));

		jpanel_msgs.add(new JSeparator());

		jpanel_msgs.add(new panelConversation("Mister",msg_test));

		jpanel_msgs.add(new JSeparator());

		jpanel_msgs.add(new panelConversation("Meh",msg_test));


		this.add(jpanel_msgs);


		this.jmenuitem_quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);

			}
		});

		this.jmenu_options.add(jmenuitem_pseudo);
		this.jmenu_options.add(jmenuitem_disconnect);
		this.jmenu_options.addSeparator();
		this.jmenu_options.add(jmenuitem_refresh);
		this.jmenu_options.addSeparator();
		this.jmenu_options.add(jmenuitem_quit);

		jmenuitem_quit.setAccelerator(KeyStroke.getKeyStroke('q'));
		jmenuitem_refresh.setAccelerator(KeyStroke.getKeyStroke('f'));

		this.menuBar.add(jmenu_options);  
		this.menuBar.add(jmenu_help);  

		this.setJMenuBar(menuBar);
		this.setVisible(true);
	}
}